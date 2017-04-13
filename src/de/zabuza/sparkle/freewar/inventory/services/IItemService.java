package de.zabuza.sparkle.freewar.inventory.services;

import org.openqa.selenium.WebDriver;

import de.zabuza.sparkle.freewar.IFreewarInstance;
import de.zabuza.sparkle.freewar.frames.IFrameManager;

/**
 * Interface for services of items. A service offers certain item depending
 * actions. In order to access the actions the service needs to be casted to its
 * actual class which can be accessed by {@link Class#getClass()}. The framework
 * will automatically build services on demand. For this each implementing class
 * must have a public constructor with arguments {@link String},
 * {@link IFreewarInstance}, {@link WebDriver} and {@link IFrameManager}. The
 * framework will use this constructor and provide all those parameters to the
 * class.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 * 
 */
public interface IItemService {

	/**
	 * Gets the name of the item this service offers actions for.
	 * 
	 * @return The name of the item this service offers actions for
	 */
	public String getItemName();
}
