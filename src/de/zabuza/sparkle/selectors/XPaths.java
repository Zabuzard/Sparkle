package de.zabuza.sparkle.selectors;

public final class XPaths {
	public static final String ITEM_INVENTORY_ITEM_ACTIVATE_ANCHOR_POST = "')]/following-sibling::a[contains(@href, 'action=activate')]";
	public static final String ITEM_INVENTORY_ITEM_ACTIVATE_ANCHOR_PRE = "//p[@class='listitemrow']//b[contains(text(), '";

	public static final String MAIN_LOCATION_NPC_ACTION_ANCHOR_POST = "')]/following-sibling::";
	public static final String MAIN_LOCATION_NPC_ACTION_ANCHOR_PRE = "//p[@class='listusersrow']//b[contains(text(), '";
	public static final String MAIN_LOCATION_NPC_ACTION_ATTACK = "a[contains(@href, 'attacknpcmenu')]";
	public static final String MAIN_LOCATION_NPC_ACTION_CHASE = "a[@class='fastchase']";
	public static final String MAIN_LOCATION_NPC_ACTION_FAST_ATTACK = "a[@class='fastattack']";

	private XPaths() {

	}
}
