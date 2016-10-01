package de.zabuza.sparkle.freewar.movement;

import java.awt.Point;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import de.zabuza.sparkle.freewar.frames.EFrame;
import de.zabuza.sparkle.freewar.frames.IFrameManager;
import de.zabuza.sparkle.freewar.location.ILocation;
import de.zabuza.sparkle.selectors.CSSSelectors;
import de.zabuza.sparkle.selectors.Classes;

/**
 * Movement of a {@link de.zabuza.sparkle.freewar.IFreewarInstance
 * IFreewarInstance}. Can be used to move the player through the world.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 * 
 */
public final class Movement implements IMovement {

	/**
	 * The web driver used by this movement.
	 */
	private final WebDriver m_Driver;
	/**
	 * Manager to use for switching frames.
	 */
	private final IFrameManager m_FrameManager;
	/**
	 * The location object used by this movement.
	 */
	private final ILocation m_Location;

	/**
	 * Creates a new movement object that uses the given driver.
	 * 
	 * @param driver
	 *            Web driver this object should use
	 * @param location
	 *            Location object this object should use
	 * @param frameManager
	 *            Manager to use for switching frames
	 */
	public Movement(final WebDriver driver, final ILocation location, final IFrameManager frameManager) {
		m_Driver = driver;
		m_Location = location;
		m_FrameManager = frameManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.movement.IMovement#move(de.zabuza.sparkle.
	 * freewar .movement.EDirection)
	 */
	@Override
	public boolean move(final EDirection direction) {
		Point positionBefore = m_Location.getPosition();

		switchToMapFrame();
		String selector;
		if (direction == EDirection.NORTH) {
			selector = CSSSelectors.MAP_MOVE_NORTH_ANCHOR;
		} else if (direction == EDirection.EAST) {
			selector = CSSSelectors.MAP_MOVE_EAST_ANCHOR;
		} else if (direction == EDirection.SOUTH) {
			selector = CSSSelectors.MAP_MOVE_SOUTH_ANCHOR;
		} else if (direction == EDirection.WEST) {
			selector = CSSSelectors.MAP_MOVE_WEST_ANCHOR;
		} else if (direction == EDirection.NORTHEAST) {
			selector = CSSSelectors.MAP_MOVE_NORTHEAST_ANCHOR;
		} else if (direction == EDirection.SOUTHEAST) {
			selector = CSSSelectors.MAP_MOVE_SOUTHEAST_ANCHOR;
		} else if (direction == EDirection.SOUTHWEST) {
			selector = CSSSelectors.MAP_MOVE_SOUTHWEST_ANCHOR;
		} else {
			selector = CSSSelectors.MAP_MOVE_NORTHWEST_ANCHOR;
		}

		// Get move anchor and click it
		WebElement moveAnchor = m_Driver.findElement(By.cssSelector(selector));

		// Desired position is not reachable
		if (Classes.hasClass(moveAnchor, Classes.MAP_FIELD_NOCANGO)) {
			return false;
		}

		// If position is reachable then click it
		moveAnchor.click();

		Point positionAfter = m_Location.getPosition();
		// Player moved when positions alter

		return !positionBefore.equals(positionAfter);
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
