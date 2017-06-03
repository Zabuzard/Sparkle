package de.zabuza.sparkle.freewar.inventory;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import de.zabuza.sparkle.freewar.IFreewarInstance;
import de.zabuza.sparkle.freewar.frames.EFrame;
import de.zabuza.sparkle.freewar.frames.IFrameManager;
import de.zabuza.sparkle.freewar.inventory.services.IItemService;
import de.zabuza.sparkle.freewar.inventory.services.magicsphere.MagicSphere;
import de.zabuza.sparkle.selectors.CSSSelectors;
import de.zabuza.sparkle.selectors.ItemNames;
import de.zabuza.sparkle.selectors.Patterns;
import de.zabuza.sparkle.selectors.XPaths;
import de.zabuza.sparkle.wait.EventQueueEmptyWait;

/**
 * Inventory of a {@link de.zabuza.sparkle.freewar.IFreewarInstance
 * IFreewarInstance}. Can be used to access the inventory of a player.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 * 
 */
public final class Inventory implements IInventory {
	/**
	 * The web driver used by this inventory.
	 */
	private final WebDriver mDriver;
	/**
	 * Manager to use for switching frames.
	 */
	private final IFrameManager mFrameManager;
	/**
	 * The instance to use for accessing other elements.
	 */
	private final IFreewarInstance mInstance;
	/**
	 * Structure which holds all registered services.
	 */
	private final HashMap<String, Class<? extends IItemService>> mRegisteredServices;

	/**
	 * Creates a new inventory that uses a given web driver.
	 * 
	 * @param instance
	 *            The instance to use for accessing other elements
	 * @param driver
	 *            Web driver to use
	 * @param frameManager
	 *            Manager to use for switching frames
	 */
	public Inventory(final IFreewarInstance instance, final WebDriver driver, final IFrameManager frameManager) {
		this.mInstance = instance;
		this.mDriver = driver;
		this.mFrameManager = frameManager;
		this.mRegisteredServices = new HashMap<>();
		registerBuiltInServices();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.sparkle.freewar.inventory.IInventory#activateItem(java.lang
	 * .String)
	 */
	@Override
	public boolean activateItem(final String item) {
		if (!hasItem(item)) {
			return false;
		}
		String xpath = XPaths.ITEM_INVENTORY_ITEM_ACTIVATE_ANCHOR_PRE + item
				+ XPaths.ITEM_INVENTORY_ITEM_ACTIVATE_ANCHOR_POST;

		// If item has an activation link then click it
		List<WebElement> itemElements = this.mDriver.findElements(By.xpath(xpath));
		if (!itemElements.isEmpty()) {
			final WebElement itemElement = itemElements.iterator().next();
			itemElement.click();
			return true;
		}

		// If item was not found then search for equipped items
		xpath = XPaths.ITEM_INVENTORY_ITEM_EQUIPPED_ACTIVATE_ANCHOR_PRE + item
				+ XPaths.ITEM_INVENTORY_ITEM_EQUIPPED_ACTIVATE_ANCHOR_POST;

		itemElements = this.mDriver.findElements(By.xpath(xpath));
		if (!itemElements.isEmpty()) {
			final WebElement itemElement = itemElements.iterator().next();
			itemElement.click();
			return true;
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.inventory.IInventory#closeInventory()
	 */
	@Override
	public boolean closeInventory() {
		if (!isInventoryOpened()) {
			return true;
		}
		// Only close inventory if it can be closed. Closing is not possible if
		// the player only has few items
		final List<WebElement> closeAnchors = this.mDriver
				.findElements(By.cssSelector(CSSSelectors.ITEM_INVENTORY_CLOSE_ANCHOR));
		if (!closeAnchors.isEmpty()) {
			final WebElement closeAnchor = closeAnchors.iterator().next();
			closeAnchor.click();
			return true;
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.inventory.IInventory#getInventorySize()
	 */
	@Override
	public int getInventorySize() {
		switchToItemFrame();
		final WebElement element = this.mDriver.findElement(By.xpath(XPaths.ITEM_INVENTORY_SIZE));
		final String inventorySizeText = element.getText();
		final Matcher matcher = Pattern.compile(Patterns.INTEGER).matcher(inventorySizeText);
		int inventorySize = NO_VALUE;
		if (matcher.find()) {
			inventorySize = Integer.parseInt(matcher.group());
		}

		return inventorySize;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.inventory.IInventory#getItems()
	 */
	@Override
	public String[] getItems() {
		openInventory();

		final List<String> items = new ArrayList<>();

		// Add unequipped items
		final List<WebElement> unequippedItemElements = this.mDriver
				.findElements(By.cssSelector(CSSSelectors.ITEM_INVENTORY_ITEM_NAME));
		for (final WebElement itemElement : unequippedItemElements) {
			items.add(itemElement.getText());
		}

		// Add equipped items
		final List<WebElement> equippedItemElements = this.mDriver
				.findElements(By.cssSelector(CSSSelectors.ITEM_INVENTORY_ITEM_EQUIPPED_NAME));
		for (final WebElement itemElement : equippedItemElements) {
			items.add(itemElement.getText());
		}

		final String[] itemsArray = new String[items.size()];
		return items.toArray(itemsArray);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.inventory.IInventory#getService(java.lang.
	 * String)
	 */
	@Override
	public Optional<IItemService> getService(final String itemName) throws IllegalStateException {
		if (!this.mRegisteredServices.containsKey(itemName)) {
			return Optional.empty();
		}

		final Class<? extends IItemService> clazz = this.mRegisteredServices.get(itemName);
		try {
			final Constructor<? extends IItemService> constructor = clazz.getConstructor(String.class,
					IFreewarInstance.class, WebDriver.class, IFrameManager.class);
			final IItemService instance = constructor.newInstance(itemName, this.mInstance, this.mDriver,
					this.mFrameManager);
			return Optional.of(instance);
		} catch (final NoSuchMethodException | InstantiationException | IllegalAccessException
				| IllegalArgumentException | InvocationTargetException e) {
			throw new IllegalStateException();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.sparkle.freewar.inventory.IInventory#hasItem(java.lang.String)
	 */
	@Override
	public boolean hasItem(final String item) {
		openInventory();

		List<WebElement> itemElements = this.mDriver
				.findElements(By.cssSelector(CSSSelectors.ITEM_INVENTORY_ITEM_NAME));
		for (final WebElement itemElement : itemElements) {
			if (itemElement.getText().equals(item)) {
				return true;
			}
		}
		// If item was not found then search for equipped items
		itemElements = this.mDriver.findElements(By.cssSelector(CSSSelectors.ITEM_INVENTORY_ITEM_EQUIPPED_NAME));
		for (final WebElement itemElement : itemElements) {
			if (itemElement.getText().equals(item)) {
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.inventory.IInventory#hasService(java.lang.
	 * String)
	 */
	@Override
	public boolean hasService(final String itemName) {
		return this.mRegisteredServices.containsKey(itemName);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.inventory.IInventory#isInventoryOpened()
	 */
	@Override
	public boolean isInventoryOpened() {
		switchToItemFrame();
		// If inventory can not be opened assume it is already open
		return this.mDriver.findElements(By.cssSelector(CSSSelectors.ITEM_INVENTORY_OPEN_ANCHOR)).isEmpty();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.inventory.IInventory#openInventory()
	 */
	@Override
	public void openInventory() {
		if (isInventoryOpened()) {
			return;
		}
		final WebElement openAnchor = this.mDriver.findElement(By.cssSelector(CSSSelectors.ITEM_INVENTORY_OPEN_ANCHOR));
		openAnchor.click();
		// It is necessary that this method blocks until the
		// click event was executed
		new EventQueueEmptyWait(this.mDriver).waitUntilCondition();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.sparkle.freewar.inventory.IInventory#registerService(java.lang.
	 * String, java.lang.Class)
	 */
	@Override
	public void registerService(final String itemName, final Class<? extends IItemService> service)
			throws IllegalArgumentException {
		try {
			service.getConstructor(String.class, IFreewarInstance.class, WebDriver.class, IFrameManager.class);
			this.mRegisteredServices.put(itemName, service);
		} catch (final NoSuchMethodException e) {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * Registers all already built-in services.
	 */
	private void registerBuiltInServices() {
		registerService(ItemNames.COMPRESSED_MAGIC_SPHERE, MagicSphere.class);
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
