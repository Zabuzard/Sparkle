package de.zabuza.sparkle.freewar.location;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import de.zabuza.sparkle.freewar.frames.EFrame;
import de.zabuza.sparkle.freewar.frames.IFrameManager;
import de.zabuza.sparkle.selectors.CSSSelectors;
import de.zabuza.sparkle.selectors.Patterns;
import de.zabuza.sparkle.selectors.XPaths;
import de.zabuza.sparkle.wait.CSSSelectorPresenceWait;

/**
 * Location of a {@link de.zabuza.sparkle.freewar.IFreewarInstance
 * IFreewarInstance}. Can be used to access the current location of a player.
 * Provides access to NPCs on the location.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 * 
 */
public final class Location implements ILocation {

	/**
	 * Web driver used by this location.
	 */
	private final WebDriver m_Driver;
	/**
	 * Manager to use for switching frames.
	 */
	private final IFrameManager m_FrameManager;

	/**
	 * Creates a new location using the given driver.
	 * 
	 * @param driver
	 *            Web driver used by this location
	 * @param frameManager
	 *            Manager to use for switching frames
	 */
	public Location(final WebDriver driver, final IFrameManager frameManager) {
		m_Driver = driver;
		m_FrameManager = frameManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.sparkle.freewar.location.ILocation#chaseNPC(java.lang.String)
	 */
	@Override
	public boolean chaseNPC(final String npcName) {
		return doNPCAction(npcName, XPaths.MAIN_LOCATION_NPC_ACTION_CHASE);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.sparkle.freewar.location.ILocation#fastAttackNPC(java.lang.
	 * String)
	 */
	@Override
	public boolean fastAttackNPC(final String npcName) {
		return doNPCAction(npcName, XPaths.MAIN_LOCATION_NPC_ACTION_FAST_ATTACK);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.location.ILocation#getNPCs()
	 */
	@Override
	public String[] getNPCs() {
		switchToMainFrame();

		final List<String> npcs = new ArrayList<String>();

		final List<WebElement> npcElements = m_Driver.findElements(By.cssSelector(CSSSelectors.MAIN_LOCATION_NPC_NAME));
		for (final WebElement npcElement : npcElements) {
			npcs.add(npcElement.getText());
		}

		String[] npcsArray = new String[npcs.size()];
		return npcs.toArray(npcsArray);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.location.ILocation#getPosition()
	 */
	@Override
	public Point getPosition() {
		switchToMapFrame();

		// Get position text, has the format:
		// Position X: 508 Y: -57
		String positionText = m_Driver.findElement(By.cssSelector(CSSSelectors.MAP_POSITION_TEXT)).getText();

		// Extract x and y coordinates from text
		Matcher matcher = Pattern.compile(Patterns.INTEGER).matcher(positionText);
		Point position = new Point();
		if (matcher.find()) {
			position.x = Integer.parseInt(matcher.group());
			if (matcher.find()) {
				position.y = Integer.parseInt(matcher.group());
			}
		}
		return position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.sparkle.freewar.location.ILocation#hasNPC(java.lang.String)
	 */
	@Override
	public boolean hasNPC(final String npcName) {
		switchToMainFrame();

		List<WebElement> npcElements = m_Driver.findElements(By.cssSelector(CSSSelectors.MAIN_LOCATION_NPC_NAME));
		for (WebElement npcElement : npcElements) {
			if (npcElement.getText().equals(npcName)) {
				return true;
			}
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.sparkle.freewar.location.ILocation#regularAttackNPC(java.lang
	 * .String)
	 */
	@Override
	public boolean regularAttackNPC(String npcName) {
		if (!attackNPC(npcName)) {
			return false;
		}

		WebElement actionElement = new CSSSelectorPresenceWait(m_Driver,
				CSSSelectors.MAIN_LOCATION_NPC_ATTACK_REGULAR_ANCHOR).waitUntilCondition();
		actionElement.click();

		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.sparkle.freewar.location.ILocation#singleAttackNPC(java.lang
	 * .String)
	 */
	@Override
	public boolean singleAttackNPC(String npcName) {
		if (!attackNPC(npcName)) {
			return false;
		}

		WebElement actionElement = new CSSSelectorPresenceWait(m_Driver,
				CSSSelectors.MAIN_LOCATION_NPC_ATTACK_SINGLE_ANCHOR).waitUntilCondition();
		actionElement.click();

		return true;
	}

	/**
	 * Tries to open the attack window for a given NPC. This is used to access
	 * several attack options, for example regular and single attack.
	 * 
	 * @param npcName
	 *            The name of the NPC
	 * @return <tt>True</tt> if the attack window for the NPC could be opened,
	 *         <tt>false</tt> if not. Later can occur if for example the NPC is
	 *         not present on this location.
	 */
	private boolean attackNPC(final String npcName) {
		return doNPCAction(npcName, XPaths.MAIN_LOCATION_NPC_ACTION_ATTACK);
	}

	/**
	 * Tries to perform a given action against a given NPC.
	 * 
	 * @param npcName
	 *            The name of the NPC
	 * @param xPathNPCAction
	 *            Selector of the action to perform as xPath
	 * @return <tt>True</tt> if the action was performed, <tt>false</tt> if it
	 *         could not be performed. Later can occur if for example the NPC
	 *         was not present on this location.
	 */
	private boolean doNPCAction(final String npcName, final String xPathNPCAction) {
		if (!hasNPC(npcName)) {
			return false;
		}

		String xpath = XPaths.MAIN_LOCATION_NPC_ACTION_ANCHOR_PRE + npcName
				+ XPaths.MAIN_LOCATION_NPC_ACTION_ANCHOR_POST;
		xpath += xPathNPCAction;

		// If the NPC has the action link then click it
		List<WebElement> npcElements = m_Driver.findElements(By.xpath(xpath));
		if (!npcElements.isEmpty()) {
			WebElement npcElement = npcElements.iterator().next();
			npcElement.click();
			return true;
		}

		return false;
	}

	/**
	 * Switches to the main frame of <tt>Freewar</tt> and waits until it is
	 * loaded. It ensures that previous queued events are processed before
	 * switching frames.
	 */
	private void switchToMainFrame() {
		m_FrameManager.switchToFrame(EFrame.MAIN);
	}

	/**
	 * Switches to the map frame of <tt>Freewar</tt> and waits until it is
	 * loaded. It ensures that previous queued events are processed before
	 * switching frames.
	 */
	private void switchToMapFrame() {
		m_FrameManager.switchToFrame(EFrame.MAP);
	}
}
