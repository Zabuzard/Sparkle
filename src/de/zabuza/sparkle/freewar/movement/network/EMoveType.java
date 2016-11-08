package de.zabuza.sparkle.freewar.movement.network;

/**
 * Movement types that connect {@link FreewarNode}s with each other.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 * 
 */
public enum EMoveType {
	/**
	 * Teleportation by using a black sphere item.
	 */
	BLACK_SPHERE,
	/**
	 * Teleportation by using a blue sphere item.
	 */
	BLUE_SPHERE,
	/**
	 * Teleportation by using the home spell item.
	 */
	HOME_SPELL,
	/**
	 * Teleportation by using the icy teleporter item.
	 */
	ICY_TELEPORTER,
	/**
	 * Movement which is done by using an action at the current node.
	 */
	NODE_ACTION,
	/**
	 * Teleportation by using a portal item.
	 */
	PORTAL,
	/**
	 * Teleportation by using the ring of the sandwinds item.
	 */
	RING_OF_THE_SANDWINDS,
	/**
	 * Teleportation by using the staff of trade item.
	 */
	STAFF_OF_TRADE,
	/**
	 * Walking movement type.
	 */
	WALKING,
	/**
	 * Teleportation by using a yellow sphere item.
	 */
	YELLOW_SPHERE
}
