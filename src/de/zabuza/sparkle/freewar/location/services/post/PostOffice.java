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
	 * Needle that matches the anchor that aborts the letter writing process if the
	 * receiver is inexistent.
	 */
	private final static String ANCHOR_NEEDLE_RECEIVER_INEXISTENT_ABORT = "Zur";
	/**
	 * Needle that matches the anchor that finishes the letter writing process if it
	 * was successful.
	 */
	private final static String ANCHOR_NEEDLE_SUCCESSFUL_FINISH = "Weiter";
	/**
	 * Needle that matches the text of the anchor which starts the letter writing
	 * process.
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
	private final WebDriver mDriver;
	/**
	 * The instance to use for accessing other data.
	 */
	private final IFreewarInstance mInstance;
	/**
	 * The point of the location this service offers actions for.
	 */
	private final Point mPoint;

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
		this.mPoint = point;
		this.mDriver = driver;
		this.mInstance = instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.sparkle.freewar.location.services.ILocationService#getPosition( )
	 */
	@Override
	public Point getPosition() {
		return this.mPoint;
	}

	/**
	 * Writes a letter to the given receiver which contains the given message.
	 * 
	 * @param receiver
	 *            The receiving player of the letter
	 * @param message
	 *            The message to send with the letter
	 * @return If not present the action was successful, if present it contains the
	 *         error code
	 */
	public Optional<EErrorCode> writeLetter(final String receiver, final String message) {
		final int gold = this.mInstance.getPlayer().getGold();
		if (gold < LETTER_SERVICE_COST) {
			return Optional.of(EErrorCode.NO_GOLD);
		}

		// Open the letter form
		final boolean wasClicked = this.mInstance.clickAnchorByContent(EFrame.MAIN, ANCHOR_NEEDLE_WRITE_LETTER);

		if (!wasClicked) {
			return Optional.of(EErrorCode.SERVICE_UNAVAILABLE);
		}

		// Wait for click to get executed
		new EventQueueEmptyWait(this.mDriver).waitUntilCondition();

		// Fill in form
		final WebElement receiverElement = this.mDriver.findElement(By.cssSelector(CSS_RECEIVER_SELECTOR));
		receiverElement.sendKeys(receiver);
		final WebElement messageElement = this.mDriver.findElement(By.cssSelector(CSS_MESSAGE_SELECTOR));
		messageElement.sendKeys(message);

		// Submit it
		final WebElement submitElement = this.mDriver.findElement(By.cssSelector(CSS_LETTER_SUBMIT_SELECTOR));
		submitElement.click();

		// Wait for click to get executed
		new EventQueueEmptyWait(this.mDriver).waitUntilCondition();

		// Check the state
		final List<WebElement> elements = this.mDriver
				.findElements(By.partialLinkText(ANCHOR_NEEDLE_RECEIVER_INEXISTENT_ABORT));
		if (elements != null && !elements.isEmpty()) {
			// Receiver is inexistent, abort the process
			elements.iterator().next().click();
			new EventQueueEmptyWait(this.mDriver).waitUntilCondition();
			return Optional.of(EErrorCode.RECEIVER_INEXISTENT);
		}

		// Finish the process and click continue
		this.mInstance.clickAnchorByContent(EFrame.MAIN, ANCHOR_NEEDLE_SUCCESSFUL_FINISH);

		return Optional.empty();
	}

}
