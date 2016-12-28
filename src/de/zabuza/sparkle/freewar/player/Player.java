package de.zabuza.sparkle.freewar.player;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import de.zabuza.sparkle.freewar.frames.EFrame;
import de.zabuza.sparkle.freewar.frames.IFrameManager;
import de.zabuza.sparkle.selectors.CSSSelectors;
import de.zabuza.sparkle.selectors.Patterns;
import de.zabuza.sparkle.selectors.Splits;

/**
 * Player of a {@link de.zabuza.sparkle.freewar.IFreewarInstance
 * IFreewarInstance}. Can be used to access various properties of the player.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 * 
 */
public final class Player implements IPlayer {

	/**
	 * Web driver the player uses.
	 */
	private final WebDriver m_Driver;
	/**
	 * Manager to use for switching frames.
	 */
	private final IFrameManager m_FrameManager;

	/**
	 * Creates a new player using a given web driver.
	 * 
	 * @param driver
	 *            Web driver the player uses
	 * @param frameManager
	 *            Manager to use for switching frames
	 */
	public Player(final WebDriver driver, final IFrameManager frameManager) {
		m_Driver = driver;
		m_FrameManager = frameManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.player.IPlayer#getAttackPoints()
	 */
	@Override
	public int getAttackPoints() {
		switchToItemFrame();
		WebElement element = m_Driver.findElement(By.cssSelector(CSSSelectors.ITEM_PLAYER_ATTACK_POINTS));
		String attackPointsText = element.getText();
		Matcher matcher = Pattern.compile(Patterns.INTEGER).matcher(attackPointsText);
		int attackPoints = NO_VALUE;
		if (matcher.find()) {
			attackPoints = Integer.parseInt(matcher.group());
		}

		return attackPoints;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.player.IPlayer#getAttackWeapon()
	 */
	@Override
	public String getAttackWeapon() {
		switchToItemFrame();
		WebElement element = m_Driver.findElement(By.cssSelector(CSSSelectors.ITEM_PLAYER_ATTACK_WEAPON));
		String[] weaponTexts = element.getText().split(Splits.ITEM_PLAYER_WEAPON);
		String weaponText = NO_WEAPON;
		if (weaponTexts.length > 1) {
			weaponText = weaponTexts[1].trim();
		}
		return weaponText;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.player.IPlayer#getDefensePoints()
	 */
	@Override
	public int getDefensePoints() {
		switchToItemFrame();
		WebElement element = m_Driver.findElement(By.cssSelector(CSSSelectors.ITEM_PLAYER_DEFENSE_POINTS));
		String defensePointsText = element.getText();
		Matcher matcher = Pattern.compile(Patterns.INTEGER).matcher(defensePointsText);
		int defensePoints = NO_VALUE;
		if (matcher.find()) {
			defensePoints = Integer.parseInt(matcher.group());
		}

		return defensePoints;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.player.IPlayer#getDefenseWeapon()
	 */
	@Override
	public String getDefenseWeapon() {
		switchToItemFrame();
		WebElement element = m_Driver.findElement(By.cssSelector(CSSSelectors.ITEM_PLAYER_DEFENSE_WEAPON));
		String[] weaponTexts = element.getText().split(Splits.ITEM_PLAYER_WEAPON);
		String weaponText = NO_WEAPON;
		if (weaponTexts.length > 1) {
			weaponText = weaponTexts[1].trim();
		}
		return weaponText;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.player.IPlayer#getExperiencePoints()
	 */
	@Override
	public int getExperiencePoints() {
		switchToItemFrame();
		WebElement element = m_Driver.findElement(By.cssSelector(CSSSelectors.ITEM_PLAYER_NAME_EXPERIENCE));
		String[] experienceTexts = element.getText().split(Splits.ITEM_PLAYER_NAME_EXPERIENCE);
		int experience = NO_VALUE;
		if (experienceTexts.length > 1) {
			String experienceText = experienceTexts[1];
			Matcher matcher = Pattern.compile(Patterns.INTEGER).matcher(experienceText);
			if (matcher.find()) {
				experience = Integer.parseInt(matcher.group());
			}
		}
		return experience;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.player.IPlayer#getGold()
	 */
	@Override
	public int getGold() {
		switchToItemFrame();
		WebElement element = m_Driver.findElement(By.cssSelector(CSSSelectors.ITEM_PLAYER_GOLD));
		String goldText = element.getText();
		Matcher matcher = Pattern.compile(Patterns.INTEGER).matcher(goldText);
		int gold = NO_VALUE;
		if (matcher.find()) {
			gold = Integer.parseInt(matcher.group());
		}

		return gold;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.player.IPlayer#getIntelligence()
	 */
	@Override
	public int getIntelligence() {
		switchToItemFrame();
		WebElement element = m_Driver.findElement(By.cssSelector(CSSSelectors.ITEM_PLAYER_INTELLIGENCE));
		String intelligenceText = element.getText();
		Matcher matcher = Pattern.compile(Patterns.INTEGER).matcher(intelligenceText);
		int intelligence = NO_VALUE;
		if (matcher.find()) {
			intelligence = Integer.parseInt(matcher.group());
		}

		return intelligence;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.player.IPlayer#getLifePoints()
	 */
	@Override
	public int getLifePoints() {
		switchToItemFrame();
		WebElement element = m_Driver.findElement(By.cssSelector(CSSSelectors.ITEM_PLAYER_LIFE_POINTS));
		return Integer.parseInt(element.getText());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.player.IPlayer#getMaxLifePoints()
	 */
	@Override
	public int getMaxLifePoints() {
		switchToItemFrame();
		WebElement element = m_Driver.findElement(By.cssSelector(CSSSelectors.ITEM_PLAYER_MAX_LIFE_POINTS));
		String lifepointsText = element.getText();
		Matcher matcher = Pattern.compile(Patterns.PLAYER_MAX_LIFE_POINTS).matcher(lifepointsText);
		int maxLifepoints = NO_VALUE;
		if (matcher.find()) {
			maxLifepoints = Integer.parseInt(matcher.group(1));
		}

		return maxLifepoints;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.player.IPlayer#getName()
	 */
	@Override
	public String getName() {
		switchToItemFrame();
		WebElement element = m_Driver.findElement(By.cssSelector(CSSSelectors.ITEM_PLAYER_NAME_EXPERIENCE));
		String[] nameTexts = element.getText().split(Splits.ITEM_PLAYER_NAME_EXPERIENCE);
		String nameText = null;
		if (nameTexts.length > 0) {
			nameText = nameTexts[0].trim();
		}
		return nameText;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.player.IPlayer#getSpeed()
	 */
	@Override
	public int getSpeed() {
		switchToItemFrame();
		WebElement element = m_Driver.findElement(By.cssSelector(CSSSelectors.ITEM_PLAYER_SPEED));
		return Integer.parseInt(element.getText());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.player.IPlayer#getStatus()
	 */
	@Override
	public String getStatus() {
		switchToItemFrame();
		WebElement element = m_Driver.findElement(By.cssSelector(CSSSelectors.ITEM_PLAYER_STATUS));
		return element.getText();
	}

	/**
	 * Switches to the item frame of <tt>Freewar</tt> and waits until it is
	 * loaded. It ensures that previous queued events are processed before
	 * switching frames.
	 */
	private void switchToItemFrame() {
		m_FrameManager.switchToFrame(EFrame.ITEM);
	}

}
