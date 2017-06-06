package de.zabuza.sparkle.freewar.skills;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import de.zabuza.sparkle.freewar.frames.EFrame;
import de.zabuza.sparkle.freewar.frames.IFrameManager;
import de.zabuza.sparkle.freewar.player.Player;
import de.zabuza.sparkle.selectors.CSSSelectors;
import de.zabuza.sparkle.selectors.Patterns;
import de.zabuza.sparkle.selectors.XPaths;
import de.zabuza.sparkle.wait.EventQueueEmptyWait;

/**
 * Object that manages the skills of a {@link Player}.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 * 
 */
public final class SkillManager implements ISkillManager {

	/**
	 * Web driver the skill manager uses.
	 */
	private final WebDriver mDriver;
	/**
	 * Manager to use for switching frames.
	 */
	private final IFrameManager mFrameManager;

	/**
	 * Creates a new skill manager using a given web driver.
	 * 
	 * @param driver
	 *            Web driver the skill manager uses
	 * @param frameManager
	 *            Manager to use for switching frames
	 */
	public SkillManager(final WebDriver driver, final IFrameManager frameManager) {
		this.mDriver = driver;
		this.mFrameManager = frameManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.sparkle.freewar.skills.ISkillManager#abortTrainingOfSkill()
	 */
	@Override
	public boolean abortTrainingOfSkill() {
		openSkillMenu();

		final List<WebElement> elements = this.mDriver.findElements(By.xpath(XPaths.ITEM_SKILL_CUR_TRAINED_SKILL));
		if (elements.isEmpty()) {
			closeSkillMenu();
			return false;
		}
		final WebElement skillElement = elements.iterator().next();
		final WebElement abortAnchor = skillElement
				.findElement(By.cssSelector(CSSSelectors.ITEM_SKILL_CUR_TRAINED_SKILL_ABORT_ANCHOR));
		abortAnchor.click();
		new EventQueueEmptyWait(this.mDriver).waitUntilCondition();

		// Navigate through the dialog
		final WebElement confirmAnchor = this.mDriver
				.findElement(By.cssSelector(CSSSelectors.ITEM_SKILL_CUR_TRAINED_SKILL_ABORT_CONFIRM_ANCHOR));
		confirmAnchor.click();
		new EventQueueEmptyWait(this.mDriver).waitUntilCondition();

		final WebElement closeDialogAnchor = this.mDriver
				.findElement(By.cssSelector(CSSSelectors.ITEM_SKILL_CUR_TRAINED_SKILL_ABORT_CLOSE_ANCHOR));
		closeDialogAnchor.click();
		new EventQueueEmptyWait(this.mDriver).waitUntilCondition();

		closeSkillMenu();
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.sparkle.freewar.skills.ISkillManager#activateSpecialSkill()
	 */
	@Override
	public boolean activateSpecialSkill() {
		switchToItemFrame();

		// Search for the special skill activation anchor
		final List<WebElement> anchorElements = this.mDriver
				.findElements(By.cssSelector(CSSSelectors.ITEM_SKILL_SPECIAL_SKILL_ANCHOR));
		if (!anchorElements.isEmpty()) {
			final WebElement specialSkillActivationAnchor = anchorElements.iterator().next();
			specialSkillActivationAnchor.click();

			// Wait until the click gets executed
			new EventQueueEmptyWait(this.mDriver).waitUntilCondition();

			// Follow the dialog and try to activate the special skill
			final List<WebElement> dialogElements = this.mDriver
					.findElements(By.partialLinkText(Patterns.ITEM_SKILL_SPECIAL_SKILL_DIALOG_ACTIVATION));
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
	 * @see de.zabuza.sparkle.freewar.skills.ISkillManager#closeSkillMenu()
	 */
	@Override
	public void closeSkillMenu() {
		if (!isSkillMenuOpened()) {
			return;
		}
		final WebElement closeAnchor = this.mDriver
				.findElement(By.cssSelector(CSSSelectors.ITEM_SKILL_MENU_CLOSE_ANCHOR));
		closeAnchor.click();
		// It is necessary that this method blocks until the
		// click event was executed
		new EventQueueEmptyWait(this.mDriver).waitUntilCondition();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.sparkle.freewar.skills.ISkillManager#getCurrentlyTrainedSkill()
	 */
	@Override
	public Skill getCurrentlyTrainedSkill() {
		openSkillMenu();

		final List<WebElement> elements = this.mDriver.findElements(By.xpath(XPaths.ITEM_SKILL_CUR_TRAINED_SKILL));
		if (elements.isEmpty()) {
			closeSkillMenu();
			return null;
		}
		final WebElement skillElement = elements.iterator().next();
		final String skillText = skillElement.getText();

		// Extract the data
		final Pattern skillDataPattern = Pattern.compile(Patterns.ITEM_SKILL_CUR_TRAINED_SKILL);
		final Matcher skillDataMatcher = skillDataPattern.matcher(skillText);
		if (!skillDataMatcher.matches()) {
			closeSkillMenu();
			return null;
		}
		final String name = skillDataMatcher.group(1);
		final int level = Integer.parseInt(skillDataMatcher.group(2)) - 1;
		final String rawDays = skillDataMatcher.group(3);
		final String rawHours = skillDataMatcher.group(4);
		final String rawMinutes = skillDataMatcher.group(5);

		TimeUnit smallestTimeUnit = TimeUnit.DAYS;
		final int days;
		if (rawDays == null || rawDays.isEmpty()) {
			days = 0;
		} else {
			days = Integer.parseInt(rawDays);
			smallestTimeUnit = TimeUnit.DAYS;
		}

		final int hours;
		if (rawHours == null || rawHours.isEmpty()) {
			hours = 0;
		} else {
			hours = Integer.parseInt(rawHours);
			smallestTimeUnit = TimeUnit.HOURS;
		}

		final int minutes;
		if (rawMinutes == null || rawMinutes.isEmpty()) {
			minutes = 0;
		} else {
			minutes = Integer.parseInt(rawMinutes);
			smallestTimeUnit = TimeUnit.MINUTES;
		}

		// Compute training ending time
		long timeAhead = 0;
		timeAhead += TimeUnit.MILLISECONDS.convert(days, TimeUnit.DAYS);
		timeAhead += TimeUnit.MILLISECONDS.convert(hours, TimeUnit.HOURS);
		timeAhead += TimeUnit.MILLISECONDS.convert(minutes, TimeUnit.MINUTES);
		// Add a small time buffer to compensate the lost unit which is not
		// displayed
		timeAhead += TimeUnit.MILLISECONDS.convert(1, smallestTimeUnit);
		final long trainingEndTime = System.currentTimeMillis() + timeAhead;

		// Get maximal level
		final List<WebElement> trainableSkillsElements = this.mDriver
				.findElements(By.xpath(XPaths.ITEM_SKILL_TRAINABLE_SKILLS));
		if (trainableSkillsElements.isEmpty()) {
			closeSkillMenu();
			return null;
		}
		final WebElement trainableSkills = trainableSkillsElements.iterator().next();
		final String maximalLevelSelector = XPaths.ITEM_SKILL_TRAINABLE_SKILL_MAXIMAL_LEVEL_PRE + name
				+ XPaths.ITEM_SKILL_MAXIMAL_LEVEL_POST;
		final WebElement maximalLevelElement = trainableSkills.findElements(By.xpath(maximalLevelSelector)).iterator()
				.next();
		final int maximalLevel = Integer.parseInt(maximalLevelElement.getText());

		final Skill skill = new Skill(name, level, maximalLevel, trainingEndTime);

		closeSkillMenu();
		return skill;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.skills.ISkillManager#getSkills()
	 */
	@Override
	public Map<String, Skill> getSkills() {
		final Map<String, Skill> nameToSkill = new HashMap<>();

		// Add currently trained skill
		final Skill currentlyTrainedSkill = getCurrentlyTrainedSkill();
		if (currentlyTrainedSkill != null) {
			nameToSkill.put(currentlyTrainedSkill.getName(), currentlyTrainedSkill);
		}

		// Add trainable skills
		openSkillMenu();
		final List<WebElement> trainableSkillsTableElements = this.mDriver
				.findElements(By.xpath(XPaths.ITEM_SKILL_TRAINABLE_SKILLS));
		if (!trainableSkillsTableElements.isEmpty()) {
			final WebElement trainableTableSkills = trainableSkillsTableElements.iterator().next();
			final List<WebElement> trainableSkills = trainableTableSkills
					.findElements(By.cssSelector(CSSSelectors.ITEM_SKILL_TRAINABLE_SKILL));
			boolean isHeader = true;
			for (final WebElement trainableSkill : trainableSkills) {
				// Skip the header
				if (isHeader) {
					isHeader = false;
					continue;
				}

				final Iterator<WebElement> data = trainableSkill
						.findElements(By.cssSelector(CSSSelectors.ITEM_SKILL_TRAINABLE_SKILL_DATA)).iterator();

				final String name = data.next().getText();
				final int level = Integer.parseInt(data.next().getText());
				final int maximalLevel = Integer.parseInt(data.next().getText());

				// Skip element if it is the currently trained skill
				if (nameToSkill.containsKey(name)) {
					continue;
				}

				final Skill skill = new Skill(name, level, maximalLevel);
				nameToSkill.put(name, skill);
			}
		}

		// Add maximized skills
		final List<WebElement> maximizedSkillsTableElements = this.mDriver
				.findElements(By.xpath(XPaths.ITEM_SKILL_MAXIMIZED_SKILLS));
		if (!maximizedSkillsTableElements.isEmpty()) {
			final WebElement maximizedTableSkills = maximizedSkillsTableElements.iterator().next();
			final List<WebElement> maximizedSkills = maximizedTableSkills
					.findElements(By.cssSelector(CSSSelectors.ITEM_SKILL_MAXIMIZED_SKILL));
			boolean isHeader = true;
			for (final WebElement maximizedSkill : maximizedSkills) {
				// Skip the header
				if (isHeader) {
					isHeader = false;
					continue;
				}

				final Iterator<WebElement> data = maximizedSkill
						.findElements(By.cssSelector(CSSSelectors.ITEM_SKILL_MAXIMIZED_SKILL_DATA)).iterator();

				final String name = data.next().getText();
				final int maximizedLevel = Integer.parseInt(data.next().getText());

				final Skill skill = new Skill(name, maximizedLevel, maximizedLevel);
				nameToSkill.put(name, skill);
			}
		}

		closeSkillMenu();
		return nameToSkill;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.skills.ISkillManager#isSkillMenuOpened()
	 */
	@Override
	public boolean isSkillMenuOpened() {
		switchToItemFrame();
		// The menu is opened if its presence selector is present
		return !this.mDriver.findElements(By.xpath(XPaths.ITEM_SKILL_MENU_OPENED)).isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.skills.ISkillManager#openSkillMenu()
	 */
	@Override
	public void openSkillMenu() {
		if (isSkillMenuOpened()) {
			return;
		}
		final WebElement openAnchor = this.mDriver
				.findElement(By.cssSelector(CSSSelectors.ITEM_SKILL_MENU_OPEN_ANCHOR));
		openAnchor.click();
		// It is necessary that this method blocks until the
		// click event was executed
		new EventQueueEmptyWait(this.mDriver).waitUntilCondition();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.sparkle.freewar.skills.ISkillManager#startTrainingOfSkill(java.
	 * lang.String)
	 */
	@Override
	public boolean startTrainingOfSkill(final String skillName) {
		// Can not start a training if there is one or it is
		// maximized already
		final Map<String, Skill> skills = getSkills();
		if (!skills.containsKey(skillName)) {
			return false;
		}
		final Skill skill = skills.get(skillName);
		if (skill.isCurrentlyTrained() || skill.isLevelMaximized()) {
			return false;
		}

		openSkillMenu();
		final String startSelector = XPaths.ITEM_SKILL_START_TRAINING_ANCHOR_PRE + skillName
				+ XPaths.ITEM_SKILL_START_TRAINING_ANCHOR_POST;
		final List<WebElement> startAnchorElements = this.mDriver.findElements(By.xpath(startSelector));
		if (startAnchorElements.isEmpty()) {
			closeSkillMenu();
			return false;
		}
		final WebElement startAnchor = startAnchorElements.iterator().next();
		startAnchor.click();
		new EventQueueEmptyWait(this.mDriver).waitUntilCondition();

		final List<WebElement> confirmAnchorElements = this.mDriver
				.findElements(By.cssSelector(CSSSelectors.ITEM_SKILL_START_TRAINING_CONFIRM_ANCHOR));
		if (confirmAnchorElements.isEmpty()) {
			closeSkillMenu();
			return false;
		}
		final WebElement confirmAnchor = confirmAnchorElements.iterator().next();
		confirmAnchor.click();
		new EventQueueEmptyWait(this.mDriver).waitUntilCondition();

		closeSkillMenu();
		return true;
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
