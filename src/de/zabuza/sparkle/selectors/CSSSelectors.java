package de.zabuza.sparkle.selectors;

/**
 * Utility class that provides CSS selectors.
 * 
 * @author Zabuza
 *
 */
public final class CSSSelectors {
	/**
	 * Selector that indicates, when present, if the item frame has loaded.
	 */
	public static final String ITEM_FRAME_PRESENT = "div#toplpheader";
	/**
	 * Selector that corresponds to an anchor which closes the inventory.
	 */
	public static final String ITEM_INVENTORY_CLOSE_ANCHOR = "p.listcaption a[href$=closeinv]";
	/**
	 * Selector that corresponds to an element that holds the name of an
	 * equipped item.
	 */
	public static final String ITEM_INVENTORY_ITEM_EQUIPPED_NAME = "p.listitemrow span.itemequipped";
	/**
	 * Selector that corresponds to an element that holds the name of an item.
	 */
	public static final String ITEM_INVENTORY_ITEM_NAME = "p.listitemrow b";
	/**
	 * Selector that corresponds to an anchor which opens the inventory.
	 */
	public static final String ITEM_INVENTORY_OPEN_ANCHOR = "p.listcaption a[href$=openinv]";
	/**
	 * Selector that corresponds to an element that holds the attack points of
	 * the player.
	 */
	public static final String ITEM_PLAYER_ATTACK_POINTS = "p#listrow_attackp";
	/**
	 * Selector that corresponds to an element that holds the name of the
	 * equipped attack weapon.
	 */
	public static final String ITEM_PLAYER_ATTACK_WEAPON = "p#listrow_attackw";
	/**
	 * Selector that corresponds to an element that holds the defense points of
	 * the player.
	 */
	public static final String ITEM_PLAYER_DEFENSE_POINTS = "p#listrow_defensep";
	/**
	 * Selector that corresponds to an element that holds the name of the
	 * equipped defense weapon.
	 */
	public static final String ITEM_PLAYER_DEFENSE_WEAPON = "p#listrow_defensew";
	/**
	 * Selector that corresponds to an element that holds the amount of gold the
	 * player has.
	 */
	public static final String ITEM_PLAYER_GOLD = "p#listrow_money";
	/**
	 * Selector that corresponds to an element that holds the intelligence the
	 * player has.
	 */
	public static final String ITEM_PLAYER_INTELLIGENCE = "p#listrow_int";
	/**
	 * Selector that corresponds to an element that holds the amount of life
	 * points the player has.
	 */
	public static final String ITEM_PLAYER_LIFE_POINTS = "p#listrow_lifep span";
	/**
	 * Selector that corresponds to an element that holds the maximal amount of
	 * life points the player can have.
	 */
	public static final String ITEM_PLAYER_MAX_LIFE_POINTS = "p#listrow_lifep";
	/**
	 * Selector that corresponds to an element that holds the name and amount of
	 * experience points the player has.
	 */
	public static final String ITEM_PLAYER_NAME_EXPERIENCE = "div#toplpheader + p.listcaption";
	/**
	 * Selector that corresponds to an element that holds the amount of speed
	 * the player has.
	 */
	public static final String ITEM_PLAYER_SPEED = "p#listrow_int span";
	/**
	 * Selector that corresponds to an element that holds the statuses the
	 * player has.
	 */
	public static final String ITEM_PLAYER_STATUS = "p#listrow_status";

	/**
	 * Selector that corresponds to the name input field of the login form.
	 */
	public static final String LOGIN_FORM_NAME = ".logininput[name=name]";
	/**
	 * Selector that corresponds to the password input field of the login form.
	 */
	public static final String LOGIN_FORM_PASSWORD = ".logininput[name=password]";
	/**
	 * Selector that corresponds to the submit button of the login form.
	 */
	public static final String LOGIN_FORM_SUBMIT = ".loginsubmit[name=submit]";
	/**
	 * Selector that corresponds to an anchor, in the login pop-up, which
	 * continues to the game.
	 */
	public static final String LOGIN_POPUP_CONTINUE = "a[href^=frset]";

	/**
	 * Selector that indicates, when present, if the main frame has loaded.
	 */
	public static final String MAIN_FRAME_PRESENT = "table.areadescription";
	/**
	 * Selector that corresponds to an anchor that performs a regular attack on
	 * a NPC.
	 */
	public static final String MAIN_LOCATION_NPC_ATTACK_REGULAR_ANCHOR = "p.maindesc2 a[href*='attacknpc']";
	/**
	 * Selector that corresponds to an anchor that performs a single attack on a
	 * NPC.
	 */
	public static final String MAIN_LOCATION_NPC_ATTACK_SINGLE_ANCHOR = "p.maindesc2 a[href*='slapnpc']";
	/**
	 * Selector that corresponds to an element that holds the name of a NPC.
	 */
	public static final String MAIN_LOCATION_NPC_NAME = "p.listusersrow b";

	/**
	 * Selector that indicates, when present, if the map frame has loaded.
	 */
	public static final String MAP_FRAME_PRESENT = "p.positiontext";
	/**
	 * The post-part of a selector that corresponds to an anchor which moves in
	 * a given direction.
	 */
	public static final String MAP_MOVE_ANCHOR_POST = "\\')']";
	/**
	 * The pre-part of a selector that corresponds to an anchor which moves in a
	 * given direction.
	 */
	public static final String MAP_MOVE_ANCHOR_PRE = "a[onclick*='Move(\\'";
	/**
	 * Selector that corresponds to an anchor which moves to east.
	 */
	public static final String MAP_MOVE_EAST_ANCHOR = MAP_MOVE_ANCHOR_PRE + "right" + MAP_MOVE_ANCHOR_POST;
	/**
	 * Selector that corresponds to an anchor which moves to north.
	 */
	public static final String MAP_MOVE_NORTH_ANCHOR = MAP_MOVE_ANCHOR_PRE + "up" + MAP_MOVE_ANCHOR_POST;
	/**
	 * Selector that corresponds to an anchor which moves to northeast.
	 */
	public static final String MAP_MOVE_NORTHEAST_ANCHOR = MAP_MOVE_ANCHOR_PRE + "upright" + MAP_MOVE_ANCHOR_POST;
	/**
	 * Selector that corresponds to an anchor which moves to northwest.
	 */
	public static final String MAP_MOVE_NORTHWEST_ANCHOR = MAP_MOVE_ANCHOR_PRE + "upleft" + MAP_MOVE_ANCHOR_POST;
	/**
	 * Selector that corresponds to an anchor which moves to south.
	 */
	public static final String MAP_MOVE_SOUTH_ANCHOR = MAP_MOVE_ANCHOR_PRE + "down" + MAP_MOVE_ANCHOR_POST;
	/**
	 * Selector that corresponds to an anchor which moves to southeast.
	 */
	public static final String MAP_MOVE_SOUTHEAST_ANCHOR = MAP_MOVE_ANCHOR_PRE + "downright" + MAP_MOVE_ANCHOR_POST;
	/**
	 * Selector that corresponds to an anchor which moves to southwest.
	 */
	public static final String MAP_MOVE_SOUTHWEST_ANCHOR = MAP_MOVE_ANCHOR_PRE + "downleft" + MAP_MOVE_ANCHOR_POST;
	/**
	 * Selector that corresponds to an anchor which moves to west.
	 */
	public static final String MAP_MOVE_WEST_ANCHOR = MAP_MOVE_ANCHOR_PRE + "left" + MAP_MOVE_ANCHOR_POST;
	/**
	 * Selector that corresponds to an element that holds the position of the
	 * current location.
	 */
	public static final String MAP_POSITION_TEXT = "p.positiontext";
	/**
	 * Selector that corresponds to an anchor that surely logs outs from the
	 * current session.
	 */
	public static final String MAP_SURELY_LOGOUT_ANCHOR = "a[href^=logout]";

	/**
	 * Selector that indicates, when present, if the menu frame has loaded.
	 */
	public static final String MENU_FRAME_PRESENT = "a[href^=logout]";
	/**
	 * Selector that corresponds to an anchor which logs out from the current
	 * session.
	 */
	public static final String MENU_LOGOUT_ANCHOR = "a[href^=logout]";

	/**
	 * Utility class. No implementation.
	 */
	private CSSSelectors() {

	}
}
