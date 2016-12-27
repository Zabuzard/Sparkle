package de.zabuza.sparkle.freewar.movement.network;

import java.awt.Point;

import de.zabuza.sparkle.freewar.inventory.EBlueSphereDestination;
import de.zabuza.sparkle.freewar.inventory.IInventory;
import de.zabuza.sparkle.freewar.inventory.ItemUtil;
import de.zabuza.sparkle.freewar.movement.EDirection;
import de.zabuza.sparkle.freewar.movement.IMovement;
import de.zabuza.sparkle.locale.ErrorMessages;

/**
 * Utility class which offers methods useful for freewar networks.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class NetworkUtil {
	/**
	 * The cost corresponding to the move type black sphere.
	 */
	private final static float COST_OF_MOVE_TYPE_BLACK_SPHERE = 1.008f;
	/**
	 * The cost corresponding to the move type blue sphere.
	 */
	private final static float COST_OF_MOVE_TYPE_BLUE_SPHERE = 1.002f;
	/**
	 * The cost corresponding to the move type home spell.
	 */
	private final static float COST_OF_MOVE_TYPE_HOME_SPELL = 1.004f;
	/**
	 * The cost corresponding to the move type icy teleporter.
	 */
	private final static float COST_OF_MOVE_TYPE_ICY_TELEPORTER = 1.007f;
	/**
	 * The cost corresponding to the move type node action.
	 */
	private final static float COST_OF_MOVE_TYPE_NODE_ACTION = 1.001f;
	/**
	 * The cost corresponding to the move type portal.
	 */
	private final static float COST_OF_MOVE_TYPE_PORTAL = 1.006f;
	/**
	 * The cost corresponding to the move type ring of the sandwinds.
	 */
	private final static float COST_OF_MOVE_TYPE_RING_OF_THE_SANDWINDS = 1.005f;
	/**
	 * The cost corresponding to the move type staff of trade.
	 */
	private final static float COST_OF_MOVE_TYPE_STAFF_OF_TRADE = 1.009f;
	/**
	 * The cost corresponding to the move type walking.
	 */
	private final static float COST_OF_MOVE_TYPE_WALKING = 1f;
	/**
	 * The cost corresponding to the move type yellow sphere.
	 */
	private final static float COST_OF_MOVE_TYPE_YELLOW_SPHERE = 1.003f;

	/**
	 * Tries to execute the movement corresponding to the given type and
	 * situation. This method does not wait until movement is possible.
	 * 
	 * @param type
	 *            Type of the movement to execute
	 * @param source
	 *            Source position
	 * @param destination
	 *            Destination position
	 * @param movement
	 *            Object to use for movement
	 * @param inventory
	 *            Object to use for accessing the inventory
	 * @return <tt>True</tt> if the player was moved according to the given type
	 *         and situation, <tt>false</tt> if that was not possible
	 * @throws IllegalArgumentException
	 *             If the given move type or situation is not supported by this
	 *             method
	 */
	public static boolean executeMovement(final EMoveType type, final Point source, final Point destination,
			final IMovement movement, final IInventory inventory) throws IllegalArgumentException {
		final boolean isAtSameYLevel = (int) source.getY() == (int) destination.getY();
		final boolean isAtSameXLevel = (int) source.getX() == (int) destination.getX();

		// Source and destination are equals
		if (isAtSameYLevel && isAtSameXLevel) {
			return true;
		}

		if (type == EMoveType.WALKING) {
			final int differenceY = (int) (source.getY() - destination.getY());
			final int differenceX = (int) (source.getX() - destination.getX());

			// If source and destination are not adjacent abort
			if (Math.abs(differenceY) > 1 || Math.abs(differenceX) > 1) {
				return false;
			}

			final boolean isSouth = differenceY < 0;
			final boolean isEast = differenceX < 0;
			if (isAtSameYLevel) {
				if (isEast) {
					return movement.move(EDirection.EAST);
				} else {
					return movement.move(EDirection.WEST);
				}
			} else if (isAtSameXLevel) {
				if (isSouth) {
					return movement.move(EDirection.SOUTH);
				} else {
					return movement.move(EDirection.NORTH);
				}
			} else {
				if (isSouth) {
					if (isEast) {
						return movement.move(EDirection.SOUTHEAST);
					} else {
						return movement.move(EDirection.SOUTHWEST);
					}
				} else {
					if (isEast) {
						return movement.move(EDirection.NORTHEAST);
					} else {
						return movement.move(EDirection.NORTHWEST);
					}
				}
			}
		} else if (type == EMoveType.BLUE_SPHERE) {
			final EBlueSphereDestination blueSphereDestination = ItemUtil
					.getBlueSphereDestinationByCoordinates(destination);
			boolean movementExecuted = false;

			// First try the compressed magic sphere
			movementExecuted = inventory.activateCompressedMagicSphere(blueSphereDestination);

			// TODO Try other blue sphere teleportation items

			return movementExecuted;
		} else {
			// TODO Add support for other movement types
			throw new IllegalArgumentException(ErrorMessages.MOVE_TYPE_EXECUTION_ILLEGAL);
		}
	}

	/**
	 * Gets the costs corresponding to the given move type.
	 * 
	 * @param moveType
	 *            The move type to get the costs from
	 * @return The costs corresponding to the given move type
	 * @throws IllegalArgumentException
	 *             If the given move type is not supported by this method
	 */
	public static float getCostOfMoveType(final EMoveType moveType) {
		if (moveType == EMoveType.WALKING) {
			return COST_OF_MOVE_TYPE_WALKING;
		} else if (moveType == EMoveType.NODE_ACTION) {
			return COST_OF_MOVE_TYPE_NODE_ACTION;
		} else if (moveType == EMoveType.BLUE_SPHERE) {
			return COST_OF_MOVE_TYPE_BLUE_SPHERE;
		} else if (moveType == EMoveType.YELLOW_SPHERE) {
			return COST_OF_MOVE_TYPE_YELLOW_SPHERE;
		} else if (moveType == EMoveType.HOME_SPELL) {
			return COST_OF_MOVE_TYPE_HOME_SPELL;
		} else if (moveType == EMoveType.RING_OF_THE_SANDWINDS) {
			return COST_OF_MOVE_TYPE_RING_OF_THE_SANDWINDS;
		} else if (moveType == EMoveType.PORTAL) {
			return COST_OF_MOVE_TYPE_PORTAL;
		} else if (moveType == EMoveType.ICY_TELEPORTER) {
			return COST_OF_MOVE_TYPE_ICY_TELEPORTER;
		} else if (moveType == EMoveType.BLACK_SPHERE) {
			return COST_OF_MOVE_TYPE_BLACK_SPHERE;
		} else if (moveType == EMoveType.STAFF_OF_TRADE) {
			return COST_OF_MOVE_TYPE_STAFF_OF_TRADE;
		} else {
			throw new IllegalArgumentException(ErrorMessages.MOVE_TYPE_COST_ILLEGAL);
		}
	}

	/**
	 * Gets the move type corresponding to the given cost.
	 * 
	 * @param cost
	 *            The cost to get the move type from
	 * @return The move type corresponding to the given cost
	 * @throws IllegalArgumentException
	 *             If the given cost is not supported by this method
	 */
	public static EMoveType getMoveTypeOfCost(final float cost) throws IllegalArgumentException {
		if (cost == COST_OF_MOVE_TYPE_WALKING) {
			return EMoveType.WALKING;
		} else if (cost == COST_OF_MOVE_TYPE_NODE_ACTION) {
			return EMoveType.NODE_ACTION;
		} else if (cost == COST_OF_MOVE_TYPE_BLUE_SPHERE) {
			return EMoveType.BLUE_SPHERE;
		} else if (cost == COST_OF_MOVE_TYPE_YELLOW_SPHERE) {
			return EMoveType.YELLOW_SPHERE;
		} else if (cost == COST_OF_MOVE_TYPE_HOME_SPELL) {
			return EMoveType.HOME_SPELL;
		} else if (cost == COST_OF_MOVE_TYPE_RING_OF_THE_SANDWINDS) {
			return EMoveType.RING_OF_THE_SANDWINDS;
		} else if (cost == COST_OF_MOVE_TYPE_PORTAL) {
			return EMoveType.PORTAL;
		} else if (cost == COST_OF_MOVE_TYPE_ICY_TELEPORTER) {
			return EMoveType.ICY_TELEPORTER;
		} else if (cost == COST_OF_MOVE_TYPE_BLACK_SPHERE) {
			return EMoveType.BLACK_SPHERE;
		} else if (cost == COST_OF_MOVE_TYPE_STAFF_OF_TRADE) {
			return EMoveType.STAFF_OF_TRADE;
		} else {
			throw new IllegalArgumentException(ErrorMessages.COST_MOVE_TYPE_ILLEGAL);
		}
	}

	/**
	 * Utility class. No implementation.
	 */
	private NetworkUtil() {

	}

}
