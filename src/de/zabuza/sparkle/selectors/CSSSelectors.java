package de.zabuza.sparkle.selectors;

/**
 * Utility class that provides CSS selectors.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class CSSSelectors {
	/**
	 * Selector that indicates, when present, if the chat form frame has loaded.
	 */
	public static final String CHAT_FORM_FRAME_PRESENT = "input#chat_text";
	/**
	 * Selector that corresponds to an input field for entering chat messages
	 * for submission.
	 */
	public static final String CHAT_FORM_MESSAGE_INPUT = "input#chat_text";
	/**
	 * Selector that corresponds to the field for submitting clan chat messages.
	 */
	public static final String CHAT_FORM_SUBMIT_CLAN = "input.submitclan";
	/**
	 * Selector that corresponds to the field for submitting direct chat
	 * messages.
	 */
	public static final String CHAT_FORM_SUBMIT_DIRECT = "input.submitsagen";
	/**
	 * Selector that corresponds to the field for submitting global chat
	 * messages.
	 */
	public static final String CHAT_FORM_SUBMIT_GLOBAL = "input.submitglobalchat";
	/**
	 * Selector that corresponds to the field for submitting group chat
	 * messages.
	 */
	public static final String CHAT_FORM_SUBMIT_GROUP = "input.submitgroup";
	/**
	 * Selector that corresponds to the field for submitting scream chat
	 * messages.
	 */
	public static final String CHAT_FORM_SUBMIT_SCREAM = "input.submitschreien";
	/**
	 * Selector that corresponds to the field for submitting whisper chat
	 * messages.
	 */
	public static final String CHAT_FORM_SUBMIT_WHISPER = "input.submitwhisper";
	/**
	 * Selector that indicates, when present, if the chat text frame has loaded.
	 */
	public static final String CHAT_TEXT_FRAME_PRESENT = "body.framechattextbg";
	/**
	 * Selector that matches all chat messages.
	 */
	public static final String CHAT_TEXT_MESSAGE_ANCHOR = "p";
	/**
	 * Selector that corresponds to the destination selector element of an
	 * activated compressed magic sphere item.
	 */
	public static final String ITEM_COMPRESSED_MAGIC_SPHERE_SELECT = "select[name=z_pos_id]";
	/**
	 * Selector that corresponds to the submit button of an activated compressed
	 * magic sphere item.
	 */
	public static final String ITEM_COMPRESSED_MAGIC_SPHERE_SUBMIT = "input[value^=\"Noppen dr\"]";
	/**
	 * Selector that indicates, when present, if the item frame has loaded.
	 */
	public static final String ITEM_FRAME_PRESENT = "body.frameitembg";
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
	 * Selector that corresponds to an anchor which aborts the training for the
	 * currently trained skill.
	 */
	public static final String ITEM_SKILL_CUR_TRAINED_SKILL_ABORT_ANCHOR = "a[href$=action\\=abort_training]";
	/**
	 * Selector that corresponds to an anchor which closes the abort dialog of
	 * the training for the currently trained skill.
	 */
	public static final String ITEM_SKILL_CUR_TRAINED_SKILL_ABORT_CLOSE_ANCHOR = "a[href=ability\\.php]";
	/**
	 * Selector that corresponds to an anchor which confirms the abort of the
	 * training for the currently trained skill.
	 */
	public static final String ITEM_SKILL_CUR_TRAINED_SKILL_ABORT_CONFIRM_ANCHOR = "a[href*=action\\=abort_training2]";
	/**
	 * Selector that corresponds to an element containing information about a
	 * maximized skill.
	 */
	public static final String ITEM_SKILL_MAXIMIZED_SKILL = "tr";
	/**
	 * Selector that corresponds to an element containing specific data about a
	 * maximized skill.
	 */
	public static final String ITEM_SKILL_MAXIMIZED_SKILL_DATA = "td";
	/**
	 * Selector that corresponds to an anchor which closes the skill menu.
	 */
	public static final String ITEM_SKILL_MENU_CLOSE_ANCHOR = "a[href=item\\.php]";
	/**
	 * Selector that corresponds to an anchor which opens the skill menu.
	 */
	public static final String ITEM_SKILL_MENU_OPEN_ANCHOR = "p#listrow_char_mission a[href^=ability]";
	/**
	 * Selector that corresponds to an anchor which activates the special skill
	 * of the player.
	 */
	public static final String ITEM_SKILL_SPECIAL_SKILL_ANCHOR = "p#listrow_special a[href$=special]";
	/**
	 * Selector that corresponds to an anchor which confirms the start of the
	 * training for the current selected skill.
	 */
	public static final String ITEM_SKILL_START_TRAINING_CONFIRM_ANCHOR = "a[href*=action\\=train]";

	/**
	 * Selector that corresponds to an element containing information about a
	 * trainable skill.
	 */
	public static final String ITEM_SKILL_TRAINABLE_SKILL = "tr";
	/**
	 * Selector that corresponds to an element containing specific data about a
	 * trainable skill.
	 */
	public static final String ITEM_SKILL_TRAINABLE_SKILL_DATA = "td";
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
	 * The element which displays the travel on time. When this element is not
	 * present or contains no text the player is able to move, else he has to
	 * wait the given time.
	 */
	public static final String MAP_TRAVEL_ON_TIME = "div#test";
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
	 * The selector that is used to match a CSS class.
	 */
	public static final String SELECTOR_CLASS = ".";

	/**
	 * Utility class. No implementation.
	 */
	private CSSSelectors() {

	}
}
