package de.zabuza.sparkle.freewar;

import org.openqa.selenium.WebDriver;

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
 * @author Zabuza
 * 
 */
public final class FreewarInstance implements IFreewarInstance, IHasWebDriver {
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
	 */
	public FreewarInstance(final WebDriver driver) {
		this(driver, true);
	}

	/**
	 * Creates a new FreewarInstance that uses a given driver. It can be set if
	 * the instance should take care of not being logged out by <tt>Freewar</tt>
	 * due to absence.
	 * 
	 * @param driver
	 *            The driver this instance should use
	 * @param stayLoggedIn
	 *            If <tt>true</tt> the instance cares of not being automatically
	 *            logged out. If <tt>false</tt> the instance can be logged out
	 *            by <tt>Freewar</tt> due to absence.
	 */
	public FreewarInstance(final WebDriver driver, final boolean stayLoggedIn) {
		m_Driver = driver;
		setStayLoggedIn(stayLoggedIn);
		m_FrameManager = new FrameManager(m_Driver);
		m_Movement = new Movement(m_Driver, m_FrameManager);
		m_Player = new Player(m_Driver, m_FrameManager);
		m_Inventory = new Inventory(m_Driver, m_FrameManager);
		m_Location = new Location(m_Driver, m_FrameManager);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.IFreewarInstance#getInventory()
	 */
	@Override
	public IInventory getInventory() {
		return m_Inventory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.IFreewarInstance#getLocation()
	 */
	@Override
	public ILocation getLocation() {
		return m_Location;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.IFreewarInstance#getMovement()
	 */
	@Override
	public IMovement getMovement() {
		return m_Movement;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.IFreewarInstance#getPlayer()
	 */
	@Override
	public IPlayer getPlayer() {
		return m_Player;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.webdriver.IHasWebDriver#getWebDriver()
	 */
	@Override
	public WebDriver getWebDriver() {
		return m_Driver;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.IFreewarInstance#isStayLoggedIn()
	 */
	@Override
	public boolean isStayLoggedIn() {
		return m_StayLoggedIn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.IFreewarInstance#refresh()
	 */
	@Override
	public void refresh() {
		m_Driver.navigate().refresh();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.IFreewarInstance#setStayLoggedIn(boolean)
	 */
	@Override
	public void setStayLoggedIn(boolean stayLoggedIn) {
		if (m_StayLoggedIn != stayLoggedIn) {
			manageStayLoggedInService(stayLoggedIn);
		}
		m_StayLoggedIn = stayLoggedIn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.IFreewarInstance#shutdown()
	 */
	@Override
	public void shutdown() {
		// Shutdown stay-logged-in service if used
		setStayLoggedIn(false);

		m_Driver.quit();
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
			m_StayLoggedInService = new StayLoggedInService(this);
			m_StayLoggedInService.start();
		} else {
			m_StayLoggedInService.stopExecution();
		}
	}
}
