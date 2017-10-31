package de.zabuza.sparkle.selectors;

/**
 * Utility class that provides xPath selectors.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class XPaths {

	/**
	 * The post-part of a selector that corresponds to an anchor which activates a
	 * given item.
	 */
	public static final String ITEM_INVENTORY_ITEM_ACTIVATE_ANCHOR_POST = "')]/following-sibling::a[contains(@href, 'action=activate')]";
	/**
	 * The pre-part of a selector that corresponds to an anchor which activates a
	 * given item.
	 */
	public static final String ITEM_INVENTORY_ITEM_ACTIVATE_ANCHOR_PRE = "//p[@class='listitemrow']//b[contains(text(), '";
	/**
	 * The post-part of a selector that corresponds to an anchor which activates a
	 * given equipped item.
	 */
	public static final String ITEM_INVENTORY_ITEM_EQUIPPED_ACTIVATE_ANCHOR_POST = "')]/following-sibling::a[contains(@href, 'action=activate')]";
	/**
	 * The pre-part of a selector that corresponds to an anchor which activates a
	 * given equipped item.
	 */
	public static final String ITEM_INVENTORY_ITEM_EQUIPPED_ACTIVATE_ANCHOR_PRE = "//p[@class='listitemrow']//span[@class='itemequipped' and contains(text(), '";
	/**
	 * The selector that corresponds to the element that contains the current
	 * inventory size.
	 */
	public static final String ITEM_INVENTORY_SIZE = "//p[@class='listcaption' and contains(., 'Inventar')]";
	/**
	 * Selector that corresponds to the element containing all information about the
	 * currently trained skill.
	 */
	public static final String ITEM_SKILL_CUR_TRAINED_SKILL = "//p[@class='listcaption' and contains(text(), 'Aktuelles Training')]/following-sibling::p[@class='listrow']";
	/**
	 * The post-part of a selector that corresponds to an element which contains the
	 * maximal level of the skill with the given name.
	 */
	public static final String ITEM_SKILL_MAXIMAL_LEVEL_POST = "')]/parent::td/following-sibling::td/following-sibling::td";
	/**
	 * Selector that corresponds to the element containing all information about the
	 * maximized skills.
	 */
	public static final String ITEM_SKILL_MAXIMIZED_SKILLS = "//p[@class='listcaption' and contains(text(), 'Deine maximierten')]/following-sibling::table[@class='abilitymenu']";
	/**
	 * Selector that indicates, if present, whether the skill menu is opened or not.
	 */
	public static final String ITEM_SKILL_MENU_OPENED = "//p[@class='listcaption' and contains(text(), 'Deine trainierbaren')]";
	/**
	 * The post-part of a selector that corresponds to an anchor which starts the
	 * training of the skill with the given name.
	 */
	public static final String ITEM_SKILL_START_TRAINING_ANCHOR_POST = "')]";
	/**
	 * The pre-part of a selector that corresponds to an anchor which starts the
	 * training of the skill with given name.
	 */
	public static final String ITEM_SKILL_START_TRAINING_ANCHOR_PRE = "//a[contains(text(), '";
	/**
	 * The pre-part of a selector that corresponds to an element which contains the
	 * maximal level of the skill with the given name.
	 */
	public static final String ITEM_SKILL_TRAINABLE_SKILL_MAXIMAL_LEVEL_PRE = "//a[contains(text(), '";
	/**
	 * Selector that corresponds to the element containing all information about the
	 * trainable skills.
	 */
	public static final String ITEM_SKILL_TRAINABLE_SKILLS = "//p[@class='listcaption' and contains(text(), 'Deine trainierbaren')]/following-sibling::table[@class='abilitymenu']";
	/**
	 * The post-part of a selector that corresponds to an anchor which performs a
	 * given action on a given NPC.
	 */
	public static final String MAIN_LOCATION_NPC_ACTION_ANCHOR_POST = "')]/following-sibling::";
	/**
	 * The pre-part of a selector that corresponds to an anchor which performs a
	 * given action on a given NPC.
	 */
	public static final String MAIN_LOCATION_NPC_ACTION_ANCHOR_PRE = "//p[@class='listusersrow']//b[contains(text(), '";
	/**
	 * The last-part of a selector that corresponds to an anchor which performs an
	 * attack action on a given NPC.
	 */
	public static final String MAIN_LOCATION_NPC_ACTION_ATTACK = "a[contains(@href, 'attacknpcmenu')]";
	/**
	 * The last-part of a selector that corresponds to an anchor which performs a
	 * chase action on a given NPC.
	 */
	public static final String MAIN_LOCATION_NPC_ACTION_CHASE = "a[@class='fastchase']";
	/**
	 * The last-part of a selector that corresponds to an anchor which performs a
	 * fast attack action on a given NPC.
	 */
	public static final String MAIN_LOCATION_NPC_ACTION_FAST_ATTACK = "a[@class='fastattack']";

	/**
	 * Utility class. No implementation.
	 */
	private XPaths() {

	}
}
