package de.zabuza.sparkle.freewar;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import de.zabuza.sparkle.freewar.chat.Chat;
import de.zabuza.sparkle.freewar.chat.IChat;
import de.zabuza.sparkle.freewar.frames.EFrame;
import de.zabuza.sparkle.freewar.frames.FrameManager;
import de.zabuza.sparkle.freewar.frames.IFrameManager;
import de.zabuza.sparkle.freewar.inventory.IInventory;
import de.zabuza.sparkle.freewar.inventory.Inventory;
import de.zabuza.sparkle.freewar.location.ILocation;
import de.zabuza.sparkle.freewar.location.Location;
import de.zabuza.sparkle.freewar.movement.IMovement;
import de.zabuza.sparkle.freewar.movement.Movement;
import de.zabuza.sparkle.freewar.player.IPlayer;
import de.zabuza.sparkle.freewar.player.Player;
import de.zabuza.sparkle.webdriver.IHasWebDriver;

/**
 * Instance of an logged in account from the MMORPG <tt>Freewar</tt>. The
 * instance can be used to control and play the game.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 * 
 */
public final class FreewarInstance implements IFreewarInstance, IHasWebDriver {
	/**
	 * The chat object of this instance.
	 */
	private final IChat m_Chat;
	/**
	 * The web driver used by this instance.
	 */
	private final WebDriver m_Driver;
	/**
	 * The frame manager of this instance.
	 */
	private final IFrameManager m_FrameManager;
	/**
	 * The inventory object of this instance.
	 */
	private final IInventory m_Inventory;
	/**
	 * The location object of this instance.
	 */
	private final ILocation m_Location;
	/**
	 * The movement object of this instance.
	 */
	private final IMovement m_Movement;
	/**
	 * The player object of this instance.
	 */
	private final IPlayer m_Player;
	/**
	 * If the instance should care of not being automatically logged out by
	 * <tt>Freewar</tt> due to absence.
	 */
	private boolean m_StayLoggedIn;
	/**
	 * The service used to take care of not being automatically logged out by
	 * <tt>Freewar</tt> due to absence, if used.
	 */
	private StayLoggedInService m_StayLoggedInService;

	/**
	 * Creates a new FreewarInstance that uses a given driver. It takes
	 * automatically care of not being logged out by <tt>Freewar</tt> due to
	 * absence.
	 * 
	 * @param driver
	 *            The driver this instance should use
	 * @param user
	 *            The name of the user of this instance
	 */
	public FreewarInstance(final WebDriver driver, final String user) {
		this(driver, user, true);
	}

	/**
	 * Creates a new FreewarInstance that uses a given driver. It can be set if
	 * the instance should take care of not being logged out by <tt>Freewar</tt>
	 * due to absence.
	 * 
	 * @param driver
	 *            The driver this instance should use
	 * @param user
	 *            The name of the user of this instance
	 * @param stayLoggedIn
	 *            If <tt>true</tt> the instance cares of not being automatically
	 *            logged out. If <tt>false</tt> the instance can be logged out
	 *            by <tt>Freewar</tt> due to absence.
	 */
	public FreewarInstance(final WebDriver driver, final String user, final boolean stayLoggedIn) {
		this.m_Driver = driver;
		setStayLoggedIn(stayLoggedIn);
		this.m_FrameManager = new FrameManager(this.m_Driver);
		this.m_Player = new Player(this.m_Driver, this.m_FrameManager);
		this.m_Inventory = new Inventory(this, this.m_Driver, this.m_FrameManager);
		this.m_Location = new Location(this, this.m_Driver, this.m_FrameManager);
		this.m_Movement = new Movement(this.m_Driver, this.m_Location, this.m_Inventory, this.m_FrameManager);
		this.m_Chat = new Chat(this.m_Driver, this.m_FrameManager, user);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.sparkle.freewar.IFreewarInstance#clickAnchorByContent(de.zabuza
	 * .sparkle.freewar.frames.EFrame, java.lang.String)
	 */
	@Override
	public boolean clickAnchorByContent(final EFrame frame, final String needle) {
		this.m_FrameManager.switchToFrame(frame);
		final List<WebElement> elements = this.m_Driver.findElements(By.partialLinkText(needle));
		if (elements != null && !elements.isEmpty()) {
			elements.iterator().next().click();
			return true;
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.IFreewarInstance#getChat()
	 */
	@Override
	public IChat getChat() {
		return this.m_Chat;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.IFreewarInstance#getFrameManager()
	 */
	@Override
	public IFrameManager getFrameManager() {
		return this.m_FrameManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.IFreewarInstance#getInventory()
	 */
	@Override
	public IInventory getInventory() {
		return this.m_Inventory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.IFreewarInstance#getLocation()
	 */
	@Override
	public ILocation getLocation() {
		return this.m_Location;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.IFreewarInstance#getMovement()
	 */
	@Override
	public IMovement getMovement() {
		return this.m_Movement;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.IFreewarInstance#getPlayer()
	 */
	@Override
	public IPlayer getPlayer() {
		return this.m_Player;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.webdriver.IHasWebDriver#getWebDriver()
	 */
	@Override
	public WebDriver getWebDriver() {
		return this.m_Driver;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.IFreewarInstance#isStayLoggedIn()
	 */
	@Override
	public boolean isStayLoggedIn() {
		return this.m_StayLoggedIn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.IFreewarInstance#refresh()
	 */
	@Override
	public void refresh() {
		this.m_Driver.navigate().refresh();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.IFreewarInstance#setStayLoggedIn(boolean)
	 */
	@Override
	public void setStayLoggedIn(boolean stayLoggedIn) {
		if (this.m_StayLoggedIn != stayLoggedIn) {
			manageStayLoggedInService(stayLoggedIn);
		}
		this.m_StayLoggedIn = stayLoggedIn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.IFreewarInstance#shutdown()
	 */
	@Override
	public void shutdown(final boolean doQuitDriver) {
		// Shutdown stay-logged-in service if used
		setStayLoggedIn(false);

		if (doQuitDriver) {
			this.m_Driver.quit();
		}
	}

	/**
	 * Manages the stay logged in service. If the instance should care of
	 * staying logged in, it will create a corresponding service to ensure such,
	 * if not already done so. If the instance should not take care of this, it
	 * will stop an eventual previous created service.
	 * 
	 * @param stayLoggedIn
	 *            If the instance should care of staying logged in
	 */
	private void manageStayLoggedInService(final boolean stayLoggedIn) {
		if (stayLoggedIn) {
			this.m_StayLoggedInService = new StayLoggedInService(this);
			this.m_StayLoggedInService.start();
		} else {
			this.m_StayLoggedInService.stopExecution();
		}
	}
}
