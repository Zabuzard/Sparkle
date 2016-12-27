package de.zabuza.sparkle.freewar.inventory;

import java.awt.Point;

import de.zabuza.sparkle.locale.ErrorMessages;

/**
 * Utility class that provides methods for items.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class ItemUtil {
	/**
	 * Access id of the blue sphere teleportation destination in Anatubia.
	 */
	private static final int BLUE_SPHERE_ANATUBIA_ID = 68;
	/**
	 * X coordinate of the blue sphere teleportation destination in Anatubia.
	 */
	private static final int BLUE_SPHERE_ANATUBIA_X = 85;
	/**
	 * Y coordinate of the blue sphere teleportation destination in Anatubia.
	 */
	private static final int BLUE_SPHERE_ANATUBIA_Y = 102;
	/**
	 * Access id of the blue sphere teleportation destination in Buran.
	 */
	private static final int BLUE_SPHERE_BURAN_ID = 538;
	/**
	 * X coordinate of the blue sphere teleportation destination in Buran.
	 */
	private static final int BLUE_SPHERE_BURAN_X = 80;
	/**
	 * Y coordinate of the blue sphere teleportation destination in Buran.
	 */
	private static final int BLUE_SPHERE_BURAN_Y = 87;
	/**
	 * Access id of the blue sphere teleportation destination in Ferdolia.
	 */
	private static final int BLUE_SPHERE_CASINO_OF_FERDOLIA_ID = 1079;
	/**
	 * X coordinate of the blue sphere teleportation destination to the casino
	 * of Ferdolia.
	 */
	private static final int BLUE_SPHERE_CASINO_OF_FERDOLIA_X = 100;
	/**
	 * Y coordinate of the blue sphere teleportation destination to the casino
	 * of Ferdolia.
	 */
	private static final int BLUE_SPHERE_CASINO_OF_FERDOLIA_Y = 94;
	/**
	 * Access id of the blue sphere teleportation destination in Hewn.
	 */
	private static final int BLUE_SPHERE_HEWN_ID = 884;
	/**
	 * X coordinate of the blue sphere teleportation destination in Hewn.
	 */
	private static final int BLUE_SPHERE_HEWN_X = 92;
	/**
	 * Y coordinate of the blue sphere teleportation destination in Hewn.
	 */
	private static final int BLUE_SPHERE_HEWN_Y = 90;
	/**
	 * Access id of the blue sphere teleportation destination in Kanobia.
	 */
	private static final int BLUE_SPHERE_KANOBIA_ID = 1321;
	/**
	 * X coordinate of the blue sphere teleportation destination in Kanobia.
	 */
	private static final int BLUE_SPHERE_KANOBIA_X = 75;
	/**
	 * Y coordinate of the blue sphere teleportation destination in Kanobia.
	 */
	private static final int BLUE_SPHERE_KANOBIA_Y = 99;
	/**
	 * Access id of the blue sphere teleportation destination in Konlir.
	 */
	private static final int BLUE_SPHERE_KONLIR_ID = 2;
	/**
	 * X coordinate of the blue sphere teleportation destination in Konlir.
	 */
	private static final int BLUE_SPHERE_KONLIR_X = 101;
	/**
	 * Y coordinate of the blue sphere teleportation destination in Konlir.
	 */
	private static final int BLUE_SPHERE_KONLIR_Y = 100;

	/**
	 * Access id of the blue sphere teleportation destination in Lodradon.
	 */
	private static final int BLUE_SPHERE_LODRADON_ID = 4304;
	/**
	 * X coordinate of the blue sphere teleportation destination in Lodradon.
	 */
	private static final int BLUE_SPHERE_LODRADON_X = 114;
	/**
	 * Y coordinate of the blue sphere teleportation destination in Lodradon.
	 */
	private static final int BLUE_SPHERE_LODRADON_Y = 76;
	/**
	 * Access id of the blue sphere teleportation destination in the lost
	 * valley.
	 */
	private static final int BLUE_SPHERE_LOST_VALLEY_ID = 169;
	/**
	 * X coordinate of the blue sphere teleportation destination to the lost
	 * valley.
	 */
	private static final int BLUE_SPHERE_LOST_VALLEY_X = 81;
	/**
	 * Y coordinate of the blue sphere teleportation destination to the lost
	 * valley.
	 */
	private static final int BLUE_SPHERE_LOST_VALLEY_Y = 94;
	/**
	 * Access id of the blue sphere teleportation destination in Mentoran.
	 */
	private static final int BLUE_SPHERE_MENTORAN_ID = 290;
	/**
	 * X coordinate of the blue sphere teleportation destination in Mentoran.
	 */
	private static final int BLUE_SPHERE_MENTORAN_X = 99;
	/**
	 * Y coordinate of the blue sphere teleportation destination in Mentoran.
	 */
	private static final int BLUE_SPHERE_MENTORAN_Y = 115;
	/**
	 * Access id of the blue sphere teleportation destination in Narubia.
	 */
	private static final int BLUE_SPHERE_NARUBIA_ID = 387;
	/**
	 * X coordinate of the blue sphere teleportation destination in Narubia.
	 */
	private static final int BLUE_SPHERE_NARUBIA_X = 501;
	/**
	 * Y coordinate of the blue sphere teleportation destination in Narubia.
	 */
	private static final int BLUE_SPHERE_NARUBIA_Y = 51;
	/**
	 * Access id of the blue sphere teleportation destination in Nawor.
	 */
	private static final int BLUE_SPHERE_NAWOR_ID = 437;
	/**
	 * X coordinate of the blue sphere teleportation destination in Nawor.
	 */
	private static final int BLUE_SPHERE_NAWOR_X = 103;
	/**
	 * Y coordinate of the blue sphere teleportation destination in Nawor.
	 */
	private static final int BLUE_SPHERE_NAWOR_Y = 110;
	/**
	 * Access id of the blue sphere teleportation destination in Orewu.
	 */
	private static final int BLUE_SPHERE_OREWU_ID = 988;
	/**
	 * X coordinate of the blue sphere teleportation destination in Orewu.
	 */
	private static final int BLUE_SPHERE_OREWU_X = 108;
	/**
	 * Y coordinate of the blue sphere teleportation destination in Orewu.
	 */
	private static final int BLUE_SPHERE_OREWU_Y = 114;
	/**
	 * Access id of the blue sphere teleportation destination in Reikan.
	 */
	private static final int BLUE_SPHERE_REIKAN_ID = 87;
	/**
	 * X coordinate of the blue sphere teleportation destination in Reikan.
	 */
	private static final int BLUE_SPHERE_REIKAN_X = 97;
	/**
	 * Y coordinate of the blue sphere teleportation destination in Reikan.
	 */
	private static final int BLUE_SPHERE_REIKAN_Y = 108;
	/**
	 * Access id of the blue sphere teleportation destination in Sutrania.
	 */
	private static final int BLUE_SPHERE_SUTRANIA_ID = 816;
	/**
	 * X coordinate of the blue sphere teleportation destination in Sutrania.
	 */
	private static final int BLUE_SPHERE_SUTRANIA_X = 71;
	/**
	 * Y coordinate of the blue sphere teleportation destination in Sutrania.
	 */
	private static final int BLUE_SPHERE_SUTRANIA_Y = 92;
	/**
	 * Access id of the blue sphere teleportation destination in Terasi.
	 */
	private static final int BLUE_SPHERE_TERASI_ID = 1715;
	/**
	 * X coordinate of the blue sphere teleportation destination in Terasi.
	 */
	private static final int BLUE_SPHERE_TERASI_X = 66;
	/**
	 * Y coordinate of the blue sphere teleportation destination in Terasi.
	 */
	private static final int BLUE_SPHERE_TERASI_Y = 111;
	/**
	 * Access id of the blue sphere teleportation destination to the universal
	 * bank.
	 */
	private static final int BLUE_SPHERE_UNIVERSAL_BANK_ID = 73;
	/**
	 * X coordinate of the blue sphere teleportation destination to the
	 * universal bank.
	 */
	private static final int BLUE_SPHERE_UNIVERSAL_BANK_X = 92;
	/**
	 * Y coordinate of the blue sphere teleportation destination to the
	 * universal bank.
	 */
	private static final int BLUE_SPHERE_UNIVERSAL_BANK_Y = 105;
	/**
	 * Access id of the blue sphere teleportation destination to the valley of
	 * ruins.
	 */
	private static final int BLUE_SPHERE_VALLEY_OF_RUINS_ID = 110;
	/**
	 * X coordinate of the blue sphere teleportation destination to the valley
	 * of ruins.
	 */
	private static final int BLUE_SPHERE_VALLEY_OF_RUINS_X = 93;
	/**
	 * Y coordinate of the blue sphere teleportation destination to the valley
	 * of ruins.
	 */
	private static final int BLUE_SPHERE_VALLEY_OF_RUINS_Y = 96;

	/**
	 * Gets the access id of the corresponding blue sphere teleportation
	 * destination.
	 * 
	 * @param destination
	 *            The destination to get the access id of
	 * @return The access id that corresponds to the given blue sphere
	 *         teleportation destination
	 * @throws IllegalArgumentException
	 *             If the given blue sphere teleportation destination is not
	 *             supported by this method
	 */
	public static int getBlueSphereAccessIdByDestination(final EBlueSphereDestination destination)
			throws IllegalArgumentException {
		if (destination == EBlueSphereDestination.ANATUBIA) {
			return BLUE_SPHERE_ANATUBIA_ID;
		} else if (destination == EBlueSphereDestination.BURAN) {
			return BLUE_SPHERE_BURAN_ID;
		} else if (destination == EBlueSphereDestination.CASINO_OF_FERDOLIA) {
			return BLUE_SPHERE_CASINO_OF_FERDOLIA_ID;
		} else if (destination == EBlueSphereDestination.HEWN) {
			return BLUE_SPHERE_HEWN_ID;
		} else if (destination == EBlueSphereDestination.KANOBIA) {
			return BLUE_SPHERE_KANOBIA_ID;
		} else if (destination == EBlueSphereDestination.KONLIR) {
			return BLUE_SPHERE_KONLIR_ID;
		} else if (destination == EBlueSphereDestination.LODRADON) {
			return BLUE_SPHERE_LODRADON_ID;
		} else if (destination == EBlueSphereDestination.LOST_VALLEY) {
			return BLUE_SPHERE_LOST_VALLEY_ID;
		} else if (destination == EBlueSphereDestination.MENTORAN) {
			return BLUE_SPHERE_MENTORAN_ID;
		} else if (destination == EBlueSphereDestination.NARUBIA) {
			return BLUE_SPHERE_NARUBIA_ID;
		} else if (destination == EBlueSphereDestination.NAWOR) {
			return BLUE_SPHERE_NAWOR_ID;
		} else if (destination == EBlueSphereDestination.OREWU) {
			return BLUE_SPHERE_OREWU_ID;
		} else if (destination == EBlueSphereDestination.REIKAN) {
			return BLUE_SPHERE_REIKAN_ID;
		} else if (destination == EBlueSphereDestination.SUTRANIA) {
			return BLUE_SPHERE_SUTRANIA_ID;
		} else if (destination == EBlueSphereDestination.TERASI) {
			return BLUE_SPHERE_TERASI_ID;
		} else if (destination == EBlueSphereDestination.UNIVERSAL_BANK) {
			return BLUE_SPHERE_UNIVERSAL_BANK_ID;
		} else if (destination == EBlueSphereDestination.VALLEY_OF_RUINS) {
			return BLUE_SPHERE_VALLEY_OF_RUINS_ID;
		} else {
			throw new IllegalArgumentException(ErrorMessages.BLUE_SPHERE_DESTINATION_ILLEGAL);
		}
	}

	/**
	 * Gets coordinates by their corresponding blue sphere teleportation
	 * destination.
	 * 
	 * @param destination
	 *            The destination to get the coordinates of
	 * @return The coordinates that correspond to the given blue sphere
	 *         teleportation destination
	 * @throws IllegalArgumentException
	 *             If the given blue sphere teleportation destination is not
	 *             supported by this method
	 */
	public static Point getBlueSphereCoordinatesByDestination(final EBlueSphereDestination destination)
			throws IllegalArgumentException {
		if (destination == EBlueSphereDestination.ANATUBIA) {
			return new Point(BLUE_SPHERE_ANATUBIA_X, BLUE_SPHERE_ANATUBIA_Y);
		} else if (destination == EBlueSphereDestination.BURAN) {
			return new Point(BLUE_SPHERE_BURAN_X, BLUE_SPHERE_BURAN_Y);
		} else if (destination == EBlueSphereDestination.CASINO_OF_FERDOLIA) {
			return new Point(BLUE_SPHERE_CASINO_OF_FERDOLIA_X, BLUE_SPHERE_CASINO_OF_FERDOLIA_Y);
		} else if (destination == EBlueSphereDestination.HEWN) {
			return new Point(BLUE_SPHERE_HEWN_X, BLUE_SPHERE_HEWN_Y);
		} else if (destination == EBlueSphereDestination.KANOBIA) {
			return new Point(BLUE_SPHERE_KANOBIA_X, BLUE_SPHERE_KANOBIA_Y);
		} else if (destination == EBlueSphereDestination.KONLIR) {
			return new Point(BLUE_SPHERE_KONLIR_X, BLUE_SPHERE_KONLIR_Y);
		} else if (destination == EBlueSphereDestination.LODRADON) {
			return new Point(BLUE_SPHERE_LODRADON_X, BLUE_SPHERE_LODRADON_Y);
		} else if (destination == EBlueSphereDestination.LOST_VALLEY) {
			return new Point(BLUE_SPHERE_LOST_VALLEY_X, BLUE_SPHERE_LOST_VALLEY_Y);
		} else if (destination == EBlueSphereDestination.MENTORAN) {
			return new Point(BLUE_SPHERE_MENTORAN_X, BLUE_SPHERE_MENTORAN_Y);
		} else if (destination == EBlueSphereDestination.NARUBIA) {
			return new Point(BLUE_SPHERE_NARUBIA_X, BLUE_SPHERE_NARUBIA_Y);
		} else if (destination == EBlueSphereDestination.NAWOR) {
			return new Point(BLUE_SPHERE_NAWOR_X, BLUE_SPHERE_NAWOR_Y);
		} else if (destination == EBlueSphereDestination.OREWU) {
			return new Point(BLUE_SPHERE_OREWU_X, BLUE_SPHERE_OREWU_Y);
		} else if (destination == EBlueSphereDestination.REIKAN) {
			return new Point(BLUE_SPHERE_REIKAN_X, BLUE_SPHERE_REIKAN_Y);
		} else if (destination == EBlueSphereDestination.SUTRANIA) {
			return new Point(BLUE_SPHERE_SUTRANIA_X, BLUE_SPHERE_SUTRANIA_Y);
		} else if (destination == EBlueSphereDestination.TERASI) {
			return new Point(BLUE_SPHERE_TERASI_X, BLUE_SPHERE_TERASI_Y);
		} else if (destination == EBlueSphereDestination.UNIVERSAL_BANK) {
			return new Point(BLUE_SPHERE_UNIVERSAL_BANK_X, BLUE_SPHERE_UNIVERSAL_BANK_Y);
		} else if (destination == EBlueSphereDestination.VALLEY_OF_RUINS) {
			return new Point(BLUE_SPHERE_VALLEY_OF_RUINS_X, BLUE_SPHERE_VALLEY_OF_RUINS_Y);
		} else {
			throw new IllegalArgumentException(ErrorMessages.BLUE_SPHERE_DESTINATION_ILLEGAL);
		}
	}

	/**
	 * Gets blue sphere teleportation destinations by their corresponding
	 * coordinates.
	 * 
	 * @param coordinates
	 *            The x and y coordinates of the destination
	 * @return The blue sphere teleportation destination corresponding to the
	 *         given coordinates
	 * @throws IllegalArgumentException
	 *             If the given coordinates do not correspond to a blue sphere
	 *             teleportation destination
	 */
	public static EBlueSphereDestination getBlueSphereDestinationByCoordinates(final Point coordinates)
			throws IllegalArgumentException {
		if (coordinates.x == BLUE_SPHERE_ANATUBIA_X && coordinates.y == BLUE_SPHERE_ANATUBIA_Y) {
			return EBlueSphereDestination.ANATUBIA;
		} else if (coordinates.x == BLUE_SPHERE_BURAN_X && coordinates.y == BLUE_SPHERE_BURAN_Y) {
			return EBlueSphereDestination.BURAN;
		} else if (coordinates.x == BLUE_SPHERE_CASINO_OF_FERDOLIA_X
				&& coordinates.y == BLUE_SPHERE_CASINO_OF_FERDOLIA_Y) {
			return EBlueSphereDestination.CASINO_OF_FERDOLIA;
		} else if (coordinates.x == BLUE_SPHERE_HEWN_X && coordinates.y == BLUE_SPHERE_HEWN_Y) {
			return EBlueSphereDestination.HEWN;
		} else if (coordinates.x == BLUE_SPHERE_KANOBIA_X && coordinates.y == BLUE_SPHERE_KANOBIA_Y) {
			return EBlueSphereDestination.KANOBIA;
		} else if (coordinates.x == BLUE_SPHERE_KONLIR_X && coordinates.y == BLUE_SPHERE_KONLIR_Y) {
			return EBlueSphereDestination.KONLIR;
		} else if (coordinates.x == BLUE_SPHERE_LODRADON_X && coordinates.y == BLUE_SPHERE_LODRADON_Y) {
			return EBlueSphereDestination.LODRADON;
		} else if (coordinates.x == BLUE_SPHERE_LOST_VALLEY_X && coordinates.y == BLUE_SPHERE_LOST_VALLEY_Y) {
			return EBlueSphereDestination.LOST_VALLEY;
		} else if (coordinates.x == BLUE_SPHERE_MENTORAN_X && coordinates.y == BLUE_SPHERE_MENTORAN_Y) {
			return EBlueSphereDestination.MENTORAN;
		} else if (coordinates.x == BLUE_SPHERE_NARUBIA_X && coordinates.y == BLUE_SPHERE_NARUBIA_Y) {
			return EBlueSphereDestination.NARUBIA;
		} else if (coordinates.x == BLUE_SPHERE_NAWOR_X && coordinates.y == BLUE_SPHERE_NAWOR_Y) {
			return EBlueSphereDestination.NAWOR;
		} else if (coordinates.x == BLUE_SPHERE_OREWU_X && coordinates.y == BLUE_SPHERE_OREWU_Y) {
			return EBlueSphereDestination.OREWU;
		} else if (coordinates.x == BLUE_SPHERE_REIKAN_X && coordinates.y == BLUE_SPHERE_REIKAN_Y) {
			return EBlueSphereDestination.REIKAN;
		} else if (coordinates.x == BLUE_SPHERE_SUTRANIA_X && coordinates.y == BLUE_SPHERE_SUTRANIA_Y) {
			return EBlueSphereDestination.SUTRANIA;
		} else if (coordinates.x == BLUE_SPHERE_TERASI_X && coordinates.y == BLUE_SPHERE_TERASI_Y) {
			return EBlueSphereDestination.TERASI;
		} else if (coordinates.x == BLUE_SPHERE_UNIVERSAL_BANK_X && coordinates.y == BLUE_SPHERE_UNIVERSAL_BANK_Y) {
			return EBlueSphereDestination.UNIVERSAL_BANK;
		} else if (coordinates.x == BLUE_SPHERE_VALLEY_OF_RUINS_X && coordinates.y == BLUE_SPHERE_VALLEY_OF_RUINS_Y) {
			return EBlueSphereDestination.VALLEY_OF_RUINS;
		} else {
			throw new IllegalArgumentException(ErrorMessages.BLUE_SPHERE_COORDINATES_ILLEGAL);
		}
	}

	/**
	 * Utility class. No implementation.
	 */
	private ItemUtil() {

	}

}
