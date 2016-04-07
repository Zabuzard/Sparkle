package de.zabuza.sparkle.selectors;

public final class CSSSelectors {
	public static final String LOGIN_FORM_NAME = ".logininput[name=name]";
	public static final String LOGIN_FORM_PASSWORD = ".logininput[name=password]";
	public static final String LOGIN_FORM_SUBMIT = ".loginsubmit[name=submit]";
	public static final String LOGIN_POPUP_CONTINUE = "a[href^=frset]";

	public static final String MENU_LOGOUT_ANCHOR = "a[href^=logout]";
	public static final String MENU_FRAME_PRESENT = "a[href^=logout]";

	public static final String MAP_SURELY_LOGOUT_ANCHOR = "a[href^=logout]";
	public static final String MAP_POSITION_TEXT = "p.positiontext";
	public static final String MAP_FRAME_PRESENT = "p.positiontext";
	private static final String MAP_MOVE_ANCHOR_PRE = "a[onclick*='Move(\\'";
	private static final String MAP_MOVE_ANCHOR_POST = "\\')']";
	public static final String MAP_MOVE_NORTH_ANCHOR = MAP_MOVE_ANCHOR_PRE
			+ "up" + MAP_MOVE_ANCHOR_POST;
	public static final String MAP_MOVE_EAST_ANCHOR = MAP_MOVE_ANCHOR_PRE
			+ "right" + MAP_MOVE_ANCHOR_POST;
	public static final String MAP_MOVE_SOUTH_ANCHOR = MAP_MOVE_ANCHOR_PRE
			+ "down" + MAP_MOVE_ANCHOR_POST;
	public static final String MAP_MOVE_WEST_ANCHOR = MAP_MOVE_ANCHOR_PRE
			+ "left" + MAP_MOVE_ANCHOR_POST;
	public static final String MAP_MOVE_NORTHEAST_ANCHOR = MAP_MOVE_ANCHOR_PRE
			+ "upright" + MAP_MOVE_ANCHOR_POST;
	public static final String MAP_MOVE_SOUTHEAST_ANCHOR = MAP_MOVE_ANCHOR_PRE
			+ "downright" + MAP_MOVE_ANCHOR_POST;
	public static final String MAP_MOVE_SOUTHWEST_ANCHOR = MAP_MOVE_ANCHOR_PRE
			+ "downleft" + MAP_MOVE_ANCHOR_POST;
	public static final String MAP_MOVE_NORTHWEST_ANCHOR = MAP_MOVE_ANCHOR_PRE
			+ "upleft" + MAP_MOVE_ANCHOR_POST;

	public static final String ITEM_FRAME_PRESENT = "div#toplpheader";
	public static final String ITEM_PLAYER_NAME_EXPERIENCE = "div#toplpheader + p.listcaption";
	public static final String ITEM_PLAYER_MAX_LIFE_POINTS = "p#listrow_lifep";
	public static final String ITEM_PLAYER_LIFE_POINTS = "p#listrow_lifep span";
	public static final String ITEM_PLAYER_GOLD = "p#listrow_money";
	public static final String ITEM_PLAYER_INTELLIGENCE = "p#listrow_int";
	public static final String ITEM_PLAYER_SPEED = "p#listrow_int span";
	public static final String ITEM_PLAYER_ATTACK_POINTS = "p#listrow_attackp";
	public static final String ITEM_PLAYER_DEFENSE_POINTS = "p#listrow_defensep";
	public static final String ITEM_PLAYER_ATTACK_WEAPON = "p#listrow_attackw";
	public static final String ITEM_PLAYER_DEFENSE_WEAPON = "p#listrow_defensew";
	public static final String ITEM_PLAYER_STATUS = "p#listrow_status";
	public static final String ITEM_INVENTORY_OPEN_ANCHOR = "p.listcaption a[href$=openinv]";
	public static final String ITEM_INVENTORY_CLOSE_ANCHOR = "p.listcaption a[href$=closeinv]";
	public static final String ITEM_INVENTORY_ITEM_NAME = "p.listitemrow b";
	
	public static final String MAIN_FRAME_PRESENT = "table.areadescription";
	public static final String MAIN_LOCATION_NPC_NAME = "p.listusersrow b";
	public static final String MAIN_LOCATION_NPC_ATTACK_SINGLE_ANCHOR = "p.maindesc2 a[href*='slapnpc']";
	public static final String MAIN_LOCATION_NPC_ATTACK_REGULAR_ANCHOR = "p.maindesc2 a[href*='attacknpc']";

	private CSSSelectors() {

	}
}
