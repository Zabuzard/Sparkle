package de.zabuza.sparkle;

import de.zabuza.sparkle.freewar.EWorld;
import de.zabuza.sparkle.freewar.IFreewarInstance;
import de.zabuza.sparkle.freewar.inventory.IInventory;
import de.zabuza.sparkle.freewar.location.ILocation;
import de.zabuza.sparkle.freewar.movement.EDirection;
import de.zabuza.sparkle.freewar.movement.IMovement;
import de.zabuza.sparkle.freewar.player.IPlayer;

/**
 * Sample that demonstrates usage of the Sparkle API. It logins to an account,
 * activates an item, moves around, attacks a NPC and finally logs out from the
 * account.
 * 
 * @author Zabuza
 * 
 */
public final class SparkleSample {
	/**
	 * Starts the API sample.
	 * 
	 * @param args
	 *            Not supported
	 */
	public static void main(final String[] args) {
		IFreewarAPI api = new Sparkle();
		IFreewarInstance instance = api.login("username", "password", EWorld.FOUR);

		IPlayer player = instance.getPlayer();
		IMovement movement = instance.getMovement();
		IInventory inventory = instance.getInventory();
		ILocation location = instance.getLocation();

		String weapon = "Waldschlurchpanzer";
		if (inventory.hasItem(weapon)) {
			inventory.activateItem(weapon);
		}

		movement.move(EDirection.NORTH);
		movement.move(EDirection.SOUTH);

		String npc = "Waldschlurch";
		if (location.hasNPC(npc) && player.getLifePoints() > 10) {
			location.regularAttackNPC(npc);
		}

		api.logout(instance);
		api.shutdown();
	}
}
