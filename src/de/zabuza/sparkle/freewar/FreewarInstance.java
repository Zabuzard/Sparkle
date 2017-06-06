package de.zabuza.sparkle.freewar;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
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
import de.zabuza.sparkle.freewar.skills.ISkillManager;
import de.zabuza.sparkle.freewar.skills.SkillManager;
import de.zabuza.sparkle.selectors.Names;
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
	private final IChat mChat;
	/**
	 * The web driver used by this instance.
	 */
	private final WebDriver mDriver;
	/**
	 * The frame manager of this instance.
	 */
	private final IFrameManager mFrameManager;
	/**
	 * The inventory object of this instance.
	 */
	private final IInventory mInventory;
	/**
	 * The location object of this instance.
	 */
	private final ILocation mLocation;
	/**
	 * The movement object of this instance.
	 */
	private final IMovement mMovement;
	/**
	 * The player object of this instance.
	 */
	private final IPlayer mPlayer;
	/**
	 * The object that manages the skills of this instance.
	 */
	private final ISkillManager mSkillManager;
	/**
	 * If the instance should care of not being automatically logged out by
	 * <tt>Freewar</tt> due to absence.
	 */
	private boolean mStayLoggedIn;
	/**
	 * The service used to take care of not being automatically logged out by
	 * <tt>Freewar</tt> due to absence, if used.
	 */
	private StayLoggedInService mStayLoggedInService;

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
		this.mDriver = driver;
		setStayLoggedIn(stayLoggedIn);
		this.mFrameManager = new FrameManager(this.mDriver);

		this.mPlayer = new Player(this.mDriver, this.mFrameManager);
		this.mSkillManager = new SkillManager(this.mDriver, this.mFrameManager);
		this.mInventory = new Inventory(this, this.mDriver, this.mFrameManager);
		this.mLocation = new Location(this, this.mDriver, this.mFrameManager);
		this.mMovement = new Movement(this.mDriver, this.mLocation, this.mInventory, this.mFrameManager);
		this.mChat = new Chat(this.mDriver, this.mFrameManager, user);
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
		this.mFrameManager.switchToFrame(frame);
		final List<WebElement> elements = this.mDriver.findElements(By.partialLinkText(needle));
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
		return this.mChat;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.IFreewarInstance#getFrameManager()
	 */
	@Override
	public IFrameManager getFrameManager() {
		return this.mFrameManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.IFreewarInstance#getInventory()
	 */
	@Override
	public IInventory getInventory() {
		return this.mInventory;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.IFreewarInstance#getLocation()
	 */
	@Override
	public ILocation getLocation() {
		return this.mLocation;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.IFreewarInstance#getMovement()
	 */
	@Override
	public IMovement getMovement() {
		return this.mMovement;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.IFreewarInstance#getPlayer()
	 */
	@Override
	public IPlayer getPlayer() {
		return this.mPlayer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.IFreewarInstance#getSessionId()
	 */
	@Override
	public String getSessionId() {
		final Cookie sessionId = this.mDriver.manage().getCookieNamed(Names.COOKIE_SESSION_ID);
		if (sessionId == null) {
			return null;
		}

		return sessionId.getValue();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.IFreewarInstance#getSkillManager()
	 */
	@Override
	public ISkillManager getSkillManager() {
		return this.mSkillManager;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.webdriver.IHasWebDriver#getWebDriver()
	 */
	@Override
	public WebDriver getWebDriver() {
		return this.mDriver;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.IFreewarInstance#isStayLoggedIn()
	 */
	@Override
	public boolean isStayLoggedIn() {
		return this.mStayLoggedIn;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.IFreewarInstance#refresh()
	 */
	@Override
	public void refresh() {
		this.mDriver.navigate().refresh();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.IFreewarInstance#setStayLoggedIn(boolean)
	 */
	@Override
	public void setStayLoggedIn(final boolean stayLoggedIn) {
		if (this.mStayLoggedIn != stayLoggedIn) {
			manageStayLoggedInService(stayLoggedIn);
		}
		this.mStayLoggedIn = stayLoggedIn;
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
			this.mDriver.quit();
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
			this.mStayLoggedInService = new StayLoggedInService(this);
			this.mStayLoggedInService.start();
		} else {
			this.mStayLoggedInService.stopExecution();
		}
	}
}
