package de.zabuza.sparkle.freewar.inventory;

import java.util.Optional;

import org.openqa.selenium.WebDriver;

import de.zabuza.sparkle.freewar.IFreewarInstance;
import de.zabuza.sparkle.freewar.frames.IFrameManager;
import de.zabuza.sparkle.freewar.inventory.services.IItemService;

/**
 * Interface for inventories of
 * {@link de.zabuza.sparkle.freewar.IFreewarInstance IFreewarInstance}s. Can be
 * used to access the inventory of a player.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 * 
 */
public interface IInventory {
	/**
	 * Constant for a non valid value.
	 */
	public final static int NO_VALUE = -1;

	/**
	 * Tries to activate a given item.
	 * 
	 * @param item
	 *            The name of the item to activate
	 * @return <tt>True</tt> if the item was activated, <tt>false</tt> if it
	 *         could not be activated.
	 */
	public boolean activateItem(final String item);

	/**
	 * Tries to close the inventory, if not already closed. The inventory can
	 * not be closed if the player only has few items.
	 * 
	 * @return <tt>True</tt> If the inventory is closed after calling the
	 *         method, <tt>false</tt> if the inventory remains opened.
	 */
	public boolean closeInventory();

	/**
	 * Gets the size of the players inventory which is the amount of items the
	 * player carries.
	 * 
	 * @return The size of the players inventory which is the amount of item the
	 *         player carries or {@link #NO_VALUE} if unknown.
	 */
	public int getInventorySize();

	/**
	 * Returns the items that the player has. The size is not necessarily backed
	 * with {@link #getInventorySize()} as it is not ensured that the result
	 * contains duplicates of the same item though the player might have some.
	 *
	 * @return The items that the player has.
	 */
	public String[] getItems();

	/**
	 * If the player has a given item.
	 *
	 * @param item
	 *            The name of the item in question
	 * @return <tt>True</tt> if the player has the given item, <tt>false</tt> if
	 *         not.
	 */
	public boolean hasItem(final String item);

	/**
	 * Returns whether the inventory currently is opened.
	 * 
	 * @return <tt>True</tt> if the inventory currently is opened,
	 *         <tt>false</tt> if closed.
	 */
	public boolean isInventoryOpened();

	/**
	 * Opens the inventory if not already opened.
	 */
	public void openInventory();

	/**
	 * Gets the {@link IItemService} that is registered for the given item.
	 * Services can be registered using {@link #registerService(String, Class)}.
	 * 
	 * @param itemName
	 *            The name of the item to get the registered service for
	 * @return If present, the {@link IItemService} that is registered for the
	 *         given item. If not, there is no service registered.
	 * @throws IllegalStateException
	 *             If the registered service is non valid and does not declare
	 *             an appropriate constructor as defined in
	 *             {@link #registerService(String, Class)}.
	 */
	public Optional<IItemService> getService(final String itemName) throws IllegalStateException;

	/**
	 * If there is a {@link IItemService} registered for the given item.
	 * Services can be registered using {@link #registerService(String, Class)}.
	 * 
	 * @param itemName
	 *            The name of the item in question
	 * @return <tt>True</tt> if the given item has a registered
	 *         {@link IItemService}, <tt>false</tt> if not.
	 */
	public boolean hasService(final String itemName);

	/**
	 * Registers the given service for the given item. It can be accessed by
	 * {@link #getService(String)}. The framework will automatically build
	 * instances of services on demand. For this each implementing class must
	 * have a public constructor with arguments {@link String},
	 * {@link IFreewarInstance}, {@link WebDriver} and {@link IFrameManager}.
	 * The framework will use this constructor and provide all those parameters
	 * to the class. If there was already a service registered to the given item
	 * it will be overridden.
	 * 
	 * @param itemName
	 *            The name of the item to register the service for
	 * @param service
	 *            The class of the service to register for the given item
	 * @throws IllegalArgumentException
	 *             If the service to register is non valid and does not declare
	 *             an appropriate constructor
	 */
	public void registerService(final String itemName, final Class<? extends IItemService> service)
			throws IllegalArgumentException;
}
