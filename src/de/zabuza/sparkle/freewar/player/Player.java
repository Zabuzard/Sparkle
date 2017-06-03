package de.zabuza.sparkle.freewar.player;

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
import de.zabuza.sparkle.selectors.Splits;
import de.zabuza.sparkle.wait.EventQueueEmptyWait;

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
	private final WebDriver mDriver;
	/**
	 * Manager to use for switching frames.
	 */
	private final IFrameManager mFrameManager;

	/**
	 * Creates a new player using a given web driver.
	 * 
	 * @param driver
	 *            Web driver the player uses
	 * @param frameManager
	 *            Manager to use for switching frames
	 */
	public Player(final WebDriver driver, final IFrameManager frameManager) {
		this.mDriver = driver;
		this.mFrameManager = frameManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.player.IPlayer#activateSpecialSkill()
	 */
	@Override
	public boolean activateSpecialSkill() {
		switchToItemFrame();

		// Search for the special skill activation anchor
		final List<WebElement> anchorElements = this.mDriver
				.findElements(By.cssSelector(CSSSelectors.ITEM_PLAYER_SPECIAL_SKILL_ANCHOR));
		if (!anchorElements.isEmpty()) {
			final WebElement specialSkillActivationAnchor = anchorElements.iterator().next();
			specialSkillActivationAnchor.click();

			// Wait until the click gets executed
			new EventQueueEmptyWait(this.mDriver).waitUntilCondition();

			// Follow the dialog and try to activate the special skill
			final List<WebElement> dialogElements = this.mDriver
					.findElements(By.partialLinkText(Patterns.PLAYER_SPECIAL_SKILL_DIALOG_ACTIVATION));
			if (dialogElements != null && !dialogElements.isEmpty()) {
				dialogElements.iterator().next().click();
				return true;
			}
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.player.IPlayer#getAttackPoints()
	 */
	@Override
	public int getAttackPoints() {
		switchToItemFrame();
		final WebElement element = this.mDriver.findElement(By.cssSelector(CSSSelectors.ITEM_PLAYER_ATTACK_POINTS));
		final String attackPointsText = element.getText();
		final Matcher matcher = Pattern.compile(Patterns.INTEGER).matcher(attackPointsText);
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
		final WebElement element = this.mDriver.findElement(By.cssSelector(CSSSelectors.ITEM_PLAYER_ATTACK_WEAPON));
		final String[] weaponTexts = element.getText().split(Splits.ITEM_PLAYER_WEAPON);
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
		final WebElement element = this.mDriver.findElement(By.cssSelector(CSSSelectors.ITEM_PLAYER_DEFENSE_POINTS));
		final String defensePointsText = element.getText();
		final Matcher matcher = Pattern.compile(Patterns.INTEGER).matcher(defensePointsText);
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
		final WebElement element = this.mDriver.findElement(By.cssSelector(CSSSelectors.ITEM_PLAYER_DEFENSE_WEAPON));
		final String[] weaponTexts = element.getText().split(Splits.ITEM_PLAYER_WEAPON);
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
		final WebElement element = this.mDriver.findElement(By.cssSelector(CSSSelectors.ITEM_PLAYER_NAME_EXPERIENCE));
		final String[] experienceTexts = element.getText().split(Splits.ITEM_PLAYER_NAME_EXPERIENCE);
		int experience = NO_VALUE;
		if (experienceTexts.length > 1) {
			final String experienceText = experienceTexts[1];
			final Matcher matcher = Pattern.compile(Patterns.INTEGER).matcher(experienceText);
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
		final WebElement element = this.mDriver.findElement(By.cssSelector(CSSSelectors.ITEM_PLAYER_GOLD));
		String goldText = element.getText();
		// Remove thousand separator
		goldText = goldText.replaceAll("\\.", "");
		final Matcher matcher = Pattern.compile(Patterns.INTEGER).matcher(goldText);
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
		final WebElement element = this.mDriver.findElement(By.cssSelector(CSSSelectors.ITEM_PLAYER_INTELLIGENCE));
		final String intelligenceText = element.getText();
		final Matcher matcher = Pattern.compile(Patterns.INTEGER).matcher(intelligenceText);
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
		final WebElement element = this.mDriver.findElement(By.cssSelector(CSSSelectors.ITEM_PLAYER_LIFE_POINTS));
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
		final WebElement element = this.mDriver.findElement(By.cssSelector(CSSSelectors.ITEM_PLAYER_MAX_LIFE_POINTS));
		final String lifepointsText = element.getText();
		final Matcher matcher = Pattern.compile(Patterns.PLAYER_MAX_LIFE_POINTS).matcher(lifepointsText);
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
		final WebElement element = this.mDriver.findElement(By.cssSelector(CSSSelectors.ITEM_PLAYER_NAME_EXPERIENCE));
		final String[] nameTexts = element.getText().split(Splits.ITEM_PLAYER_NAME_EXPERIENCE);
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
		final WebElement element = this.mDriver.findElement(By.cssSelector(CSSSelectors.ITEM_PLAYER_SPEED));
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
		final WebElement element = this.mDriver.findElement(By.cssSelector(CSSSelectors.ITEM_PLAYER_STATUS));
		return element.getText();
	}

	/**
	 * Switches to the item frame of <tt>Freewar</tt> and waits until it is
	 * loaded. It ensures that previous queued events are processed before
	 * switching frames.
	 */
	private void switchToItemFrame() {
		this.mFrameManager.switchToFrame(EFrame.ITEM);
	}

}
