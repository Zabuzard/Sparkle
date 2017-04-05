package de.zabuza.sparkle.freewar.location.services;

import java.awt.Point;

import org.openqa.selenium.WebDriver;

import de.zabuza.sparkle.freewar.IFreewarInstance;
import de.zabuza.sparkle.freewar.frames.IFrameManager;

/**
 * Interface for services of locations. A service offers certain location
 * depending actions. In order to access the actions the service needs to be
 * casted to its actual class which can be accessed by {@link Class#getClass()}.
 * The framework will automatically build services on demand. For this each
 * implementing class must have a public constructor with arguments
 * {@link Point}, {@link IFreewarInstance}, {@link WebDriver} and
 * {@link IFrameManager}. The framework will use this constructor and provide
 * all those parameters to the class.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 * 
 */
public interface ILocationService {

	/**
	 * Gets the position of the location this service offers actions for in
	 * coordinates.
	 * 
	 * @return The position of the location this service offers actions for in
	 *         coordinates
	 */
	public Point getPosition();
}
