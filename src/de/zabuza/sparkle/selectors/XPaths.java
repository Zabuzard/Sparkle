package de.zabuza.sparkle.selectors;

/**
 * Utility class that provides xPath selectors.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class XPaths {

	/**
	 * The post-part of a selector that corresponds to an anchor which activates
	 * a given item.
	 */
	public static final String ITEM_INVENTORY_ITEM_ACTIVATE_ANCHOR_POST = "')]/following-sibling::a[contains(@href, 'action=activate')]";
	/**
	 * The pre-part of a selector that corresponds to an anchor which activates
	 * a given item.
	 */
	public static final String ITEM_INVENTORY_ITEM_ACTIVATE_ANCHOR_PRE = "//p[@class='listitemrow']//b[contains(text(), '";
	/**
	 * The post-part of a selector that corresponds to an anchor which activates
	 * a given equipped item.
	 */
	public static final String ITEM_INVENTORY_ITEM_EQUIPPED_ACTIVATE_ANCHOR_POST = "')]/following-sibling::a[contains(@href, 'action=activate')]";
	/**
	 * The pre-part of a selector that corresponds to an anchor which activates
	 * a given equipped item.
	 */
	public static final String ITEM_INVENTORY_ITEM_EQUIPPED_ACTIVATE_ANCHOR_PRE = "//p[@class='listitemrow']//span[@class='itemequipped' and contains(text(), '";
	/**
	 * The post-part of a selector that corresponds to an anchor which performs
	 * a given action on a given NPC.
	 */
	public static final String MAIN_LOCATION_NPC_ACTION_ANCHOR_POST = "')]/following-sibling::";

	/**
	 * The pre-part of a selector that corresponds to an anchor which performs a
	 * given action on a given NPC.
	 */
	public static final String MAIN_LOCATION_NPC_ACTION_ANCHOR_PRE = "//p[@class='listusersrow']//b[contains(text(), '";
	/**
	 * The last-part of a selector that corresponds to an anchor which performs
	 * an attack action on a given NPC.
	 */
	public static final String MAIN_LOCATION_NPC_ACTION_ATTACK = "a[contains(@href, 'attacknpcmenu')]";
	/**
	 * The last-part of a selector that corresponds to an anchor which performs
	 * a chase action on a given NPC.
	 */
	public static final String MAIN_LOCATION_NPC_ACTION_CHASE = "a[@class='fastchase']";
	/**
	 * The last-part of a selector that corresponds to an anchor which performs
	 * a fast attack action on a given NPC.
	 */
	public static final String MAIN_LOCATION_NPC_ACTION_FAST_ATTACK = "a[@class='fastattack']";

	/**
	 * Utility class. No implementation.
	 */
	private XPaths() {

	}
}
