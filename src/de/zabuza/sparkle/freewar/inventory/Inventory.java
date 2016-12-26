package de.zabuza.sparkle.freewar.inventory;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import de.zabuza.sparkle.freewar.frames.EFrame;
import de.zabuza.sparkle.freewar.frames.IFrameManager;
import de.zabuza.sparkle.selectors.CSSSelectors;
import de.zabuza.sparkle.selectors.ItemNames;
import de.zabuza.sparkle.selectors.XPaths;
import de.zabuza.sparkle.wait.CSSSelectorPresenceWait;

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
	private final WebDriver m_Driver;
	/**
	 * Manager to use for switching frames.
	 */
	private final IFrameManager m_FrameManager;

	/**
	 * Creates a new inventory that uses a given web driver.
	 * 
	 * @param driver
	 *            Web driver to use
	 * @param frameManager
	 *            Manager to use for switching frames
	 */
	public Inventory(final WebDriver driver, final IFrameManager frameManager) {
		m_Driver = driver;
		m_FrameManager = frameManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.inventory.IInventory#
	 * activateCompressedMagicSphere(de.zabuza.sparkle.freewar.inventory.
	 * EBlueSphereDestination)
	 */
	@Override
	public boolean activateCompressedMagicSphere(final EBlueSphereDestination destination) {
		if (hasItem(ItemNames.COMPRESSED_MAGIC_SPHERE) && activateItem(ItemNames.COMPRESSED_MAGIC_SPHERE)) {
			try {
				final WebElement element = new CSSSelectorPresenceWait(m_Driver,
						CSSSelectors.ITEM_COMPRESSED_MAGIC_SPHERE_SELECT).waitUntilCondition();
				final Select selectElement = new Select(element);
				final int accessId = ItemUtil.getBlueSphereAccessIdByDestination(destination);
				// Select the destination
				selectElement.selectByValue(accessId + "");
				final List<WebElement> submitButtons = m_Driver
						.findElements(By.cssSelector(CSSSelectors.ITEM_COMPRESSED_MAGIC_SPHERE_SUBMIT));
				if (submitButtons != null && !submitButtons.isEmpty()) {
					final WebElement submitButton = submitButtons.iterator().next();
					// Click the teleportation button
					submitButton.click();
					return true;
				}
			} catch (TimeoutException e) {
				return false;
			}
		}

		return false;
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
		List<WebElement> itemElements = m_Driver.findElements(By.xpath(xpath));
		if (!itemElements.isEmpty()) {
			WebElement itemElement = itemElements.iterator().next();
			itemElement.click();
			return true;
		}

		// If item was not found then search for equipped items
		xpath = XPaths.ITEM_INVENTORY_ITEM_EQUIPPED_ACTIVATE_ANCHOR_PRE + item
				+ XPaths.ITEM_INVENTORY_ITEM_EQUIPPED_ACTIVATE_ANCHOR_POST;

		itemElements = m_Driver.findElements(By.xpath(xpath));
		if (!itemElements.isEmpty()) {
			WebElement itemElement = itemElements.iterator().next();
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
		List<WebElement> closeAnchors = m_Driver.findElements(By.cssSelector(CSSSelectors.ITEM_INVENTORY_CLOSE_ANCHOR));
		if (!closeAnchors.isEmpty()) {
			WebElement closeAnchor = closeAnchors.iterator().next();
			closeAnchor.click();
			return true;
		}

		return false;
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

		List<WebElement> itemElements = m_Driver.findElements(By.cssSelector(CSSSelectors.ITEM_INVENTORY_ITEM_NAME));
		for (WebElement itemElement : itemElements) {
			if (itemElement.getText().equals(item)) {
				return true;
			}
		}
		// If item was not found then search for equipped items
		itemElements = m_Driver.findElements(By.cssSelector(CSSSelectors.ITEM_INVENTORY_ITEM_EQUIPPED_NAME));
		for (WebElement itemElement : itemElements) {
			if (itemElement.getText().equals(item)) {
				return true;
			}
		}
		return false;
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
		return m_Driver.findElements(By.cssSelector(CSSSelectors.ITEM_INVENTORY_OPEN_ANCHOR)).isEmpty();
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
		WebElement openAnchor = m_Driver.findElement(By.cssSelector(CSSSelectors.ITEM_INVENTORY_OPEN_ANCHOR));
		openAnchor.click();
	}

	/**
	 * Switches to the item frame of <tt>Freewar</tt> and waits until it is
	 * loaded. It ensures that previous queued events are processed before
	 * switching frames.
	 */
	private void switchToItemFrame() {
		m_FrameManager.switchToFrame(EFrame.ITEM);
	}
}
