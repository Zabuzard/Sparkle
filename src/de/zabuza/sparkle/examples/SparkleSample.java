package de.zabuza.sparkle.examples;

import java.util.HashSet;

import org.openqa.selenium.remote.DesiredCapabilities;

import de.zabuza.sparkle.IFreewarAPI;
import de.zabuza.sparkle.Sparkle;
import de.zabuza.sparkle.freewar.EWorld;
import de.zabuza.sparkle.freewar.IFreewarInstance;
import de.zabuza.sparkle.freewar.chat.EChatType;
import de.zabuza.sparkle.freewar.chat.IChat;
import de.zabuza.sparkle.freewar.inventory.IInventory;
import de.zabuza.sparkle.freewar.location.ILocation;
import de.zabuza.sparkle.freewar.location.services.ILocationService;
import de.zabuza.sparkle.freewar.location.services.post.PostOffice;
import de.zabuza.sparkle.freewar.movement.EDirection;
import de.zabuza.sparkle.freewar.movement.IMovement;
import de.zabuza.sparkle.freewar.movement.network.EMoveType;
import de.zabuza.sparkle.freewar.player.IPlayer;
import de.zabuza.sparkle.webdriver.EBrowser;

/**
 * Sample that demonstrates usage of the Sparkle API. It logins to an account,
 * activates an item, moves around, attacks a NPC and finally logs out from the
 * account.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
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
		// Create the API and choose a browser
		IFreewarAPI api = new Sparkle(EBrowser.CHROME);

		// Setup some browser specific settings
		final String pathToDriver = "D:\\GitHubRepos\\Sparkle\\lib\\driver\\chromedriver.exe";
		final String pathToBinary = "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe";
		final String userProfile = "C:\\Users\\MyUserName\\AppData\\Local\\Google\\Chrome\\User Data";
		final DesiredCapabilities capabilities = api.createCapabilities(EBrowser.CHROME, pathToDriver, pathToBinary,
				userProfile);
		api.setCapabilities(capabilities);

		// Login and create an instance
		final IFreewarInstance instance = api.login("username", "password", EWorld.ONE);

		// Get some objects for interaction from the API
		final IPlayer player = instance.getPlayer();
		final IMovement movement = instance.getMovement();
		final IInventory inventory = instance.getInventory();
		final ILocation location = instance.getLocation();
		final IChat chat = instance.getChat();

		// Say hi to all
		chat.submitMessage("Hello world!", EChatType.DIRECT);

		// Equip the mighty 'Waldschlurchpanzer' if held
		final String weapon = "Waldschlurchpanzer";
		if (inventory.hasItem(weapon)) {
			inventory.activateItem(weapon);
		}

		// Move to some directions.
		movement.moveWaiting(EDirection.NORTH);
		movement.moveWaiting(EDirection.SOUTH);

		// Use some basic NPC attacking logic
		final String npc = "Waldschlurch";
		if (location.hasNPC(npc) && player.getLifePoints() > 10) {
			location.regularAttackNPC(npc);
		}

		// Use the path finder to move to a destination on the shortest path
		final HashSet<EMoveType> options = new HashSet<>();
		options.add(EMoveType.BLUE_SPHERE);
		movement.moveTo(91, 104, options);
		// Wait for the movement to finish
		while (movement.hasMovementTask()) {
			try {
				Thread.sleep(100);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}

		// Since we walked to the post, we write a letter
		if (location.hasService()) {
			final ILocationService service = location.getService().get();
			if (service instanceof PostOffice) {
				final PostOffice postOfficeService = (PostOffice) service;
				postOfficeService.writeLetter("PlayerXY", "Sparkle is cool!");
			}
		}

		// Logout and shutdown the instance
		api.logout(instance, true);
		api.shutdown(true);
	}
}
