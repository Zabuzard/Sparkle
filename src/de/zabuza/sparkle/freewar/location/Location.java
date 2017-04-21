package de.zabuza.sparkle.freewar.location;

import java.awt.Point;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import de.zabuza.sparkle.freewar.IFreewarInstance;
import de.zabuza.sparkle.freewar.frames.EFrame;
import de.zabuza.sparkle.freewar.frames.IFrameManager;
import de.zabuza.sparkle.freewar.location.services.ILocationService;
import de.zabuza.sparkle.freewar.location.services.post.PostOffice;
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
	 * The instance to use for accessing other elements.
	 */
	private final IFreewarInstance m_Instance;
	/**
	 * Structure which holds all registered services.
	 */
	private final HashMap<Point, Class<? extends ILocationService>> m_RegisteredServices;

	/**
	 * Creates a new location using the given driver.
	 * 
	 * @param instance
	 *            The instance to use for accessing other elements
	 * @param driver
	 *            Web driver used by this location
	 * @param frameManager
	 *            Manager to use for switching frames
	 */
	public Location(final IFreewarInstance instance, final WebDriver driver, final IFrameManager frameManager) {
		this.m_Instance = instance;
		this.m_Driver = driver;
		this.m_FrameManager = frameManager;
		this.m_RegisteredServices = new HashMap<>();
		registerBuiltInServices();
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

		final List<String> npcs = new ArrayList<>();

		final List<WebElement> npcElements = this.m_Driver
				.findElements(By.cssSelector(CSSSelectors.MAIN_LOCATION_NPC_NAME));
		for (final WebElement npcElement : npcElements) {
			npcs.add(npcElement.getText());
		}

		final String[] npcsArray = new String[npcs.size()];
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
		final String positionText = this.m_Driver.findElement(By.cssSelector(CSSSelectors.MAP_POSITION_TEXT)).getText();

		// Extract x and y coordinates from text
		final Matcher matcher = Pattern.compile(Patterns.INTEGER).matcher(positionText);
		final Point position = new Point();
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
	 * @see de.zabuza.sparkle.freewar.location.ILocation#getService()
	 */
	@Override
	public Optional<ILocationService> getService() throws IllegalStateException {
		final Point location = getPosition();
		if (!this.m_RegisteredServices.containsKey(location)) {
			return Optional.empty();
		}

		final Class<? extends ILocationService> clazz = this.m_RegisteredServices.get(location);
		try {
			final Constructor<? extends ILocationService> constructor = clazz.getConstructor(Point.class,
					IFreewarInstance.class, WebDriver.class, IFrameManager.class);
			final ILocationService instance = constructor.newInstance(location, this.m_Instance, this.m_Driver,
					this.m_FrameManager);
			return Optional.of(instance);
		} catch (final NoSuchMethodException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			throw new IllegalStateException();
		}
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

		final List<WebElement> npcElements = this.m_Driver
				.findElements(By.cssSelector(CSSSelectors.MAIN_LOCATION_NPC_NAME));
		for (final WebElement npcElement : npcElements) {
			if (npcElement.getText().equals(npcName)) {
				return true;
			}
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.location.ILocation#hasService()
	 */
	@Override
	public boolean hasService() {
		final Point location = getPosition();
		return this.m_RegisteredServices.containsKey(location);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.sparkle.freewar.location.ILocation#registerService(java.awt.
	 * Point, java.lang.Class)
	 */
	@Override
	public void registerService(final Point location, final Class<? extends ILocationService> service)
			throws IllegalArgumentException {
		try {
			service.getConstructor(Point.class, IFreewarInstance.class, WebDriver.class, IFrameManager.class);
			this.m_RegisteredServices.put(location, service);
		} catch (final NoSuchMethodException e) {
			throw new IllegalArgumentException();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.sparkle.freewar.location.ILocation#regularAttackNPC(java.lang
	 * .String)
	 */
	@Override
	public boolean regularAttackNPC(final String npcName) {
		if (!attackNPC(npcName)) {
			return false;
		}

		final WebElement actionElement = new CSSSelectorPresenceWait(this.m_Driver,
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
	public boolean singleAttackNPC(final String npcName) {
		if (!attackNPC(npcName)) {
			return false;
		}

		final WebElement actionElement = new CSSSelectorPresenceWait(this.m_Driver,
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
		final List<WebElement> npcElements = this.m_Driver.findElements(By.xpath(xpath));
		if (!npcElements.isEmpty()) {
			final WebElement npcElement = npcElements.iterator().next();
			npcElement.click();
			return true;
		}

		return false;
	}

	/**
	 * Registers all already built-in services.
	 */
	private void registerBuiltInServices() {
		// Forest of the Lonely Tree
		registerService(new Point(91, 104), PostOffice.class);

		// Wilisia
		registerService(new Point(112, 83), PostOffice.class);

		// Laree
		registerService(new Point(54, 76), PostOffice.class);
	}

	/**
	 * Switches to the main frame of <tt>Freewar</tt> and waits until it is
	 * loaded. It ensures that previous queued events are processed before
	 * switching frames.
	 */
	private void switchToMainFrame() {
		this.m_FrameManager.switchToFrame(EFrame.MAIN);
	}

	/**
	 * Switches to the map frame of <tt>Freewar</tt> and waits until it is
	 * loaded. It ensures that previous queued events are processed before
	 * switching frames.
	 */
	private void switchToMapFrame() {
		this.m_FrameManager.switchToFrame(EFrame.MAP);
	}
}
