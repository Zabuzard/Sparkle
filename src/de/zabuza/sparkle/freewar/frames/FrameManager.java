package de.zabuza.sparkle.freewar.frames;

import org.openqa.selenium.WebDriver;

import de.zabuza.sparkle.selectors.CSSSelectors;
import de.zabuza.sparkle.selectors.Names;
import de.zabuza.sparkle.wait.CSSSelectorPresenceWait;
import de.zabuza.sparkle.wait.EventQueueEmptyWait;

/**
 * Object that manages frames of a given
 * {@link de.zabuza.sparkle.freewar.IFreewarInstance IFreewarInstance}.
 * 
 * @author Zabuza
 * 
 */
public final class FrameManager implements IFrameManager {

	/**
	 * The web driver used by this inventory.
	 */
	private final WebDriver m_Driver;

	/**
	 * Creates a new frame manager that uses a given web driver.
	 * 
	 * @param driver
	 *            Web driver to use
	 */
	public FrameManager(final WebDriver driver) {
		m_Driver = driver;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.sparkle.freewar.frames.IFrameManager#switchToFrame(de.zabuza
	 * .sparkle.freewar.frames.EFrame)
	 */
	@Override
	public void switchToFrame(final EFrame frame) {
		String frameName;
		String frameLoaded;
		if (frame == EFrame.ITEM) {
			frameName = Names.FRAME_ITEM;
			frameLoaded = CSSSelectors.ITEM_FRAME_PRESENT;
		} else if (frame == EFrame.MENU) {
			frameName = Names.FRAME_MENU;
			frameLoaded = CSSSelectors.MENU_FRAME_PRESENT;
		} else if (frame == EFrame.MAP) {
			frameName = Names.FRAME_MAP;
			frameLoaded = CSSSelectors.MAP_FRAME_PRESENT;
		} else {
			frameName = Names.FRAME_MAIN;
			frameLoaded = CSSSelectors.MAIN_FRAME_PRESENT;
		}
		switchToFrame(frameName, frameLoaded);
	}

	/**
	 * Switches to the given frame of <tt>Freewar</tt> and waits until it is
	 * loaded. It ensures that previous queued events are processed before
	 * switching frames.
	 * 
	 * @param frameName
	 *            The name of the frame to switch to
	 * @param frameLoaded
	 *            CSSCondition that represents an element that, when present,
	 *            indicates that the frame has loaded.
	 */
	private void switchToFrame(final String frameName, final String frameLoaded) {
		// Wait for events to be processed before switching frames
		new EventQueueEmptyWait(m_Driver).waitUntilCondition();
		m_Driver.switchTo().defaultContent();
		m_Driver.switchTo().frame(frameName);

		// Wait for frame to be fully present
		new CSSSelectorPresenceWait(m_Driver, frameLoaded).waitUntilCondition();
	}

}
