package de.zabuza.sparkle.freewar.location.services.post;

import java.awt.Point;
import java.util.List;
import java.util.Optional;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import de.zabuza.sparkle.freewar.IFreewarInstance;
import de.zabuza.sparkle.freewar.frames.EFrame;
import de.zabuza.sparkle.freewar.frames.IFrameManager;
import de.zabuza.sparkle.freewar.location.services.ILocationService;
import de.zabuza.sparkle.wait.EventQueueEmptyWait;

/**
 * Service for post office locations. Offers methods to write letters to other
 * players.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class PostOffice implements ILocationService {

	/**
	 * Needle that matches the anchor that aborts the letter writing process if
	 * the receiver is inexistent.
	 */
	private final static String ANCHOR_NEEDLE_RECEIVER_INEXISTENT_ABORT = "Zur";
	/**
	 * Needle that matches the anchor that finishes the letter writing process
	 * if it was successful.
	 */
	private final static String ANCHOR_NEEDLE_SUCCESSFUL_FINISH = "Weiter";
	/**
	 * Needle that matches the text of the anchor which starts the letter
	 * writing process.
	 */
	private final static String ANCHOR_NEEDLE_WRITE_LETTER = "Einen Brief schreiben";
	/**
	 * CSS selector which matches the letter submit action.
	 */
	private final static String CSS_LETTER_SUBMIT_SELECTOR = "input[value=\"Brief abschicken\"]";
	/**
	 * CSS selector which matches the message input field.
	 */
	private final static String CSS_MESSAGE_SELECTOR = "#brief";
	/**
	 * CSS selector which matches the receiver input field.
	 */
	private final static String CSS_RECEIVER_SELECTOR = "#name";
	/**
	 * The cost to use the letter service in gold.
	 */
	private final static int LETTER_SERVICE_COST = 3;

	/**
	 * The web driver to use for accessing elements.
	 */
	private final WebDriver m_Driver;
	/**
	 * The instance to use for accessing other data.
	 */
	private final IFreewarInstance m_Instance;
	/**
	 * The point of the location this service offers actions for.
	 */
	private final Point m_Point;

	/**
	 * Creates a post office service.
	 * 
	 * @param point
	 *            The point of the location this service offers actions for
	 * @param instance
	 *            The instance to use for accessing other data
	 * @param driver
	 *            The web driver to use for accessing elements
	 * @param frameManager
	 *            The frame manager to use for changing frames
	 */
	public PostOffice(final Point point, final IFreewarInstance instance, final WebDriver driver,
			@SuppressWarnings("unused") final IFrameManager frameManager) {
		this.m_Point = point;
		this.m_Driver = driver;
		this.m_Instance = instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.sparkle.freewar.location.services.ILocationService#getPosition(
	 * )
	 */
	@Override
	public Point getPosition() {
		return this.m_Point;
	}

	/**
	 * Writes a letter to the given receiver which contains the given message.
	 * 
	 * @param receiver
	 *            The receiving player of the letter
	 * @param message
	 *            The message to send with the letter
	 * @return If not present the action was successful, if present it contains
	 *         the error code
	 */
	public Optional<EErrorCode> writeLetter(final String receiver, final String message) {
		final int gold = this.m_Instance.getPlayer().getGold();
		if (gold < LETTER_SERVICE_COST) {
			return Optional.of(EErrorCode.NO_GOLD);
		}

		// Open the letter form
		final boolean wasClicked = this.m_Instance.clickAnchorByContent(EFrame.MAIN, ANCHOR_NEEDLE_WRITE_LETTER);

		if (!wasClicked) {
			return Optional.of(EErrorCode.SERVICE_UNAVAILABLE);
		}

		// Wait for click to get executed
		new EventQueueEmptyWait(this.m_Driver).waitUntilCondition();

		// Fill in form
		final WebElement receiverElement = this.m_Driver.findElement(By.cssSelector(CSS_RECEIVER_SELECTOR));
		receiverElement.sendKeys(receiver);
		final WebElement messageElement = this.m_Driver.findElement(By.cssSelector(CSS_MESSAGE_SELECTOR));
		messageElement.sendKeys(message);

		// Submit it
		final WebElement submitElement = this.m_Driver.findElement(By.cssSelector(CSS_LETTER_SUBMIT_SELECTOR));
		submitElement.click();

		// Wait for click to get executed
		new EventQueueEmptyWait(this.m_Driver).waitUntilCondition();

		// Check the state
		final List<WebElement> elements = this.m_Driver
				.findElements(By.partialLinkText(ANCHOR_NEEDLE_RECEIVER_INEXISTENT_ABORT));
		if (elements != null && !elements.isEmpty()) {
			// Receiver is inexistent, abort the process
			elements.iterator().next().click();
			new EventQueueEmptyWait(this.m_Driver).waitUntilCondition();
			return Optional.of(EErrorCode.RECEIVER_INEXISTENT);
		}

		// Finish the process and click continue
		this.m_Instance.clickAnchorByContent(EFrame.MAIN, ANCHOR_NEEDLE_SUCCESSFUL_FINISH);

		return Optional.empty();
	}

}
