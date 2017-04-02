package de.zabuza.sparkle.freewar.inventory;

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
	 * Tries to activate a compressed magic sphere and teleport to the given
	 * position.
	 * 
	 * @param destination
	 *            The destination to teleport to
	 * 
	 * @return <tt>True</tt> if the item was activated, <tt>false</tt> if it
	 *         could not be activated.
	 */
	public boolean activateCompressedMagicSphere(final EBlueSphereDestination destination);

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
	 * Returns the items that the player has.
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
}
