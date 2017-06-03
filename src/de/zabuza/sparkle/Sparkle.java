package de.zabuza.sparkle;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.opera.OperaDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.safari.SafariDriver;

import de.zabuza.sparkle.freewar.EWorld;
import de.zabuza.sparkle.freewar.FreewarInstance;
import de.zabuza.sparkle.freewar.IFreewarInstance;
import de.zabuza.sparkle.selectors.CSSSelectors;
import de.zabuza.sparkle.selectors.Names;
import de.zabuza.sparkle.selectors.Paths;
import de.zabuza.sparkle.wait.CSSSelectorPresenceWait;
import de.zabuza.sparkle.wait.EventQueueEmptyWait;
import de.zabuza.sparkle.wait.FramePresenceWait;
import de.zabuza.sparkle.wait.LoginFormWait;
import de.zabuza.sparkle.wait.LoginPopupWait;
import de.zabuza.sparkle.wait.TimedWait;
import de.zabuza.sparkle.webdriver.AntiTrapWebDriver;
import de.zabuza.sparkle.webdriver.DelayedWebDriver;
import de.zabuza.sparkle.webdriver.EBrowser;
import de.zabuza.sparkle.webdriver.IHasWebDriver;
import de.zabuza.sparkle.webdriver.StaleRefresherWebDriver;

/**
 * API that allows playing the MMORPG <tt>Freewar</tt>.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 * 
 */
public final class Sparkle implements IFreewarAPI {

	/**
	 * Current set browser to use at login.
	 */
	private EBrowser mBrowser;
	/**
	 * The capabilities to use for the created browsers.
	 */
	private DesiredCapabilities mCapabilities;
	/**
	 * If the API should automatically delay events to disguise usage of a bot
	 * for <tt>Freewar.de</tt>.
	 */
	private final boolean mDelayEvents;
	/**
	 * Set of all registered instances created with
	 * {@link #login(String, String, EWorld)}. Instances get added using
	 * {@link #login(String, String, EWorld)} and removed by using
	 * {@link #shutdownInstance(IFreewarInstance, boolean)}.
	 */
	private final Set<IFreewarInstance> mInstances;

	/**
	 * Creates a new API that uses the browser <tt>Firefox</tt> by default. It
	 * automatically delays events to disguise usage of a bot for
	 * <tt>Freewar</tt>.
	 */
	public Sparkle() {
		this(EBrowser.FIREFOX, true);
	}

	/**
	 * Creates a new API that uses a given browser by default. It automatically
	 * delays events to disguise usage of a bot for <tt>Freewar</tt>.
	 * 
	 * @param browser
	 *            Browser to use as default
	 */
	public Sparkle(final EBrowser browser) {
		this(browser, true);
	}

	/**
	 * Creates a new API that uses a given browser by default. It can be set if
	 * the API should automatically delays events to disguise usage of a bot for
	 * <tt>Freewar</tt>.
	 * 
	 * @param browser
	 *            Browser to use as default
	 * @param delayEvents
	 *            If the API should automatically delay events to disguise usage
	 *            of a bot for <tt>Freewar</tt>.
	 */
	public Sparkle(final EBrowser browser, final boolean delayEvents) {
		this.mBrowser = browser;
		this.mDelayEvents = delayEvents;
		this.mCapabilities = null;
		this.mInstances = new LinkedHashSet<>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.IFreewarAPI#createCapabilities(de.zabuza.sparkle.
	 * webdriver.EBrowser, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	public DesiredCapabilities createCapabilities(final EBrowser browser, final String driverPath,
			final String binaryPath, final String userProfile) {
		DesiredCapabilities capabilities = null;

		if (browser == EBrowser.FIREFOX) {
			capabilities = DesiredCapabilities.firefox();
			final FirefoxOptions options = new FirefoxOptions();

			// Set the driver
			if (driverPath != null) {
				System.setProperty("webdriver.gecko.driver", driverPath);
				System.setProperty("webdriver.firefox.marionette", driverPath);
				capabilities.setCapability(FirefoxDriver.MARIONETTE, true);
			}

			// Set the binary
			if (binaryPath != null) {
				final File pathToBinary = new File(binaryPath);
				final FirefoxBinary binary = new FirefoxBinary(pathToBinary);
				options.setBinary(binary);
			}

			// Set the user profile
			if (userProfile != null) {
				final FirefoxProfile profile = new ProfilesIni().getProfile(userProfile);
				options.setProfile(profile);
			}

			options.addTo(capabilities);
		} else if (browser == EBrowser.CHROME) {
			capabilities = DesiredCapabilities.chrome();
			final ChromeOptions options = new ChromeOptions();

			// Set the driver
			if (driverPath != null) {
				System.setProperty("webdriver.chrome.driver", driverPath);
			}

			// Set the binary
			if (binaryPath != null) {
				options.setBinary(binaryPath);
			}

			// Set the user profile
			if (userProfile != null) {
				options.addArguments("user-data-dir=" + userProfile);
			}
			options.addArguments("disable-infobars");

			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
		} else if (browser == EBrowser.SAFARI) {
			capabilities = DesiredCapabilities.internetExplorer();

			// Set the driver
			if (driverPath != null) {
				System.setProperty("webdriver.safari.driver", driverPath);
			}

			// Set the binary
			if (binaryPath != null) {
				capabilities.setCapability("safari.binary", binaryPath);
			}
		} else if (browser == EBrowser.INTERNET_EXPLORER) {
			capabilities = DesiredCapabilities.internetExplorer();

			// Set the driver
			if (driverPath != null) {
				System.setProperty("webdriver.ie.driver", driverPath);
			}

			// Set the binary
			if (binaryPath != null) {
				capabilities.setCapability("ie.binary", binaryPath);
			}
		} else if (browser == EBrowser.OPERA) {
			capabilities = DesiredCapabilities.internetExplorer();

			// Set the driver
			if (driverPath != null) {
				System.setProperty("webdriver.opera.driver", driverPath);
			}

			// Set the binary
			if (binaryPath != null) {
				capabilities.setCapability("opera.binary", binaryPath);
			}

			// Set the user profile
			if (userProfile != null) {
				capabilities.setCapability("opera.profile", userProfile);
			}
		} else if (browser == EBrowser.MS_EDGE) {
			capabilities = DesiredCapabilities.internetExplorer();

			// Set the driver
			if (driverPath != null) {
				System.setProperty("webdriver.edge.driver", driverPath);
			}

			// Set the binary
			if (binaryPath != null) {
				capabilities.setCapability("edge.binary", binaryPath);
			}
		} else {
			throw new IllegalArgumentException("The given browser is not supported: " + browser);
		}

		return capabilities;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.IFreewarAPI#getBrowser()
	 */
	@Override
	public EBrowser getBrowser() {
		return this.mBrowser;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.IFreewarAPI#hijackSession(java.lang.String,
	 * java.lang.String, de.zabuza.sparkle.freewar.EWorld)
	 */
	@Override
	public IFreewarInstance hijackSession(final String sessionId, final String username, final EWorld world) {
		// Validate user credentials
		if (sessionId == null || sessionId.isEmpty() || username == null || username.isEmpty() || world == null) {
			return null;
		}

		final WebDriver driver = createWebDriver(this.mBrowser);

		// Connect to login form as cookies are disabled in most browsers at the
		// blank starting page
		final String fullWorldDomain = Paths.getFullWorldDomain(world);
		final String loginUrl = fullWorldDomain + Paths.LOGIN;
		driver.get(loginUrl);

		// Wait for the page to load
		new LoginFormWait(driver).waitUntilCondition();

		// Apply the session cookie
		final String domain = Paths.getHostDomain(world);
		final Cookie sessionCookie = new Cookie(Names.COOKIE_SESSION_ID, sessionId, domain, null, null);
		driver.manage().addCookie(sessionCookie);

		// Connect to the in-game page
		final String inGameUrl = fullWorldDomain + Paths.IN_GAME;
		driver.get(inGameUrl);

		// If the session is valid then the instance should be ready now
		final IFreewarInstance instance = new FreewarInstance(driver, username);
		this.mInstances.add(instance);

		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.IFreewarAPI#login(java.lang.String,
	 * java.lang.String, de.zabuza.sparkle.freewar.EWorld)
	 */
	@Override
	public IFreewarInstance login(final String username, final String password, final EWorld world) {
		// Validate arguments
		if (username == null || username.isEmpty() || password == null || password.isEmpty() || world == null) {
			return null;
		}

		final WebDriver driver = createWebDriver(this.mBrowser);

		// Connect to login form
		final String fullWorldDomain = Paths.getFullWorldDomain(world);
		final String loginUrl = fullWorldDomain + Paths.LOGIN;
		driver.get(loginUrl);

		// Wait for form elements and get them
		final WebElement loginSubmit = new LoginFormWait(driver).waitUntilCondition();
		final WebElement loginName = driver.findElement(By.cssSelector(CSSSelectors.LOGIN_FORM_NAME));
		final WebElement loginPassword = driver.findElement(By.cssSelector(CSSSelectors.LOGIN_FORM_PASSWORD));

		// Type in user credentials
		loginName.clear();
		loginName.sendKeys(username);
		new TimedWait(driver, 200).waitUntilCondition();
		loginPassword.clear();
		loginPassword.sendKeys(password);

		// Submit form and close all pop-ups if existent
		final String parentWindow = driver.getWindowHandle();
		loginSubmit.click();
		// Wait until pop-up pops up
		boolean isThereAPopup;
		try {
			new LoginPopupWait(driver).waitUntilCondition();
			isThereAPopup = true;
		} catch (final TimeoutException e) {
			isThereAPopup = false;
		}
		// Close all pup-ups if existent
		if (isThereAPopup) {
			for (final String window : driver.getWindowHandles()) {
				if (!window.equals(parentWindow)) {
					driver.switchTo().window(window);
					driver.close();
				}
			}
			driver.switchTo().window(parentWindow);

			// Wait until element
			final WebElement popupContinue = driver.findElement(By.cssSelector(CSSSelectors.LOGIN_POPUP_CONTINUE));
			popupContinue.click();
		}

		final IFreewarInstance instance = new FreewarInstance(driver, username);
		this.mInstances.add(instance);

		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.IFreewarAPI#logout(de.zabuza.sparkle.freewar.
	 * IFreewarInstance)
	 */
	@Override
	public void logout(final IFreewarInstance instance, final boolean doQuitDriver) {
		if (instance instanceof IHasWebDriver) {
			final WebDriver driver = ((IHasWebDriver) instance).getWebDriver();
			// Wait for events to be processed before switching frames
			new EventQueueEmptyWait(driver).waitUntilCondition();
			driver.switchTo().defaultContent();

			// Wait for menu frame and switch to it
			new FramePresenceWait(driver, Names.FRAME_MENU).waitUntilCondition();
			driver.switchTo().frame(Names.FRAME_MENU);
			// Click logout in menu
			final WebElement logout = driver.findElement(By.cssSelector(CSSSelectors.MENU_LOGOUT_ANCHOR));
			logout.click();

			// Wait for click to be executed and switch to frameset
			new EventQueueEmptyWait(driver).waitUntilCondition();
			driver.switchTo().defaultContent();

			// Switch to map and click surely logout
			driver.switchTo().frame(Names.FRAME_MAP);
			final WebElement surelyLogout = new CSSSelectorPresenceWait(driver, CSSSelectors.MAP_SURELY_LOGOUT_ANCHOR)
					.waitUntilCondition();
			surelyLogout.click();

			// Wait for logout to be fully executed and then shutdown
			new LoginFormWait(driver).waitUntilCondition();
		}
		shutdownInstance(instance, doQuitDriver);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.sparkle.IFreewarAPI#setBrowser(de.zabuza.sparkle.webdriver.
	 * EBrowser)
	 */
	@Override
	public void setBrowser(final EBrowser browser) {
		this.mBrowser = browser;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.IFreewarAPI#setCapabilities(org.openqa.selenium.
	 * Capabilities)
	 */
	@Override
	public void setCapabilities(final DesiredCapabilities capabilities) {
		this.mCapabilities = capabilities;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.IFreewarAPI#shutdown()
	 */
	@Override
	public void shutdown(final boolean doQuitDriver) {
		for (final IFreewarInstance instance : this.mInstances) {
			shutdownInstance(instance, doQuitDriver);
		}
	}

	/**
	 * Creates a {@link WebDriver} that uses the given browser. If a capability
	 * object was set using {@link #setCapabilities(DesiredCapabilities)} then
	 * it will also be passed to the created browser. If {@link #mDelayEvents}
	 * is set to <tt>true</tt>, the resulting driver will automatically delay
	 * events to disguise usage of a bot for <tt>Freewar</tt>.
	 * 
	 * @param browser
	 *            Browser to use for the driver
	 * @return Webdriver that uses the given browser
	 */
	private WebDriver createWebDriver(final EBrowser browser) {
		WebDriver driver;
		if (browser == EBrowser.FIREFOX) {
			if (this.mCapabilities != null) {
				driver = new FirefoxDriver(this.mCapabilities);
			} else {
				driver = new FirefoxDriver();
			}
		} else if (browser == EBrowser.CHROME) {
			if (this.mCapabilities != null) {
				driver = new ChromeDriver(this.mCapabilities);
			} else {
				driver = new ChromeDriver();
			}
		} else if (browser == EBrowser.SAFARI) {
			if (this.mCapabilities != null) {
				driver = new SafariDriver(this.mCapabilities);
			} else {
				driver = new SafariDriver();
			}
		} else if (browser == EBrowser.INTERNET_EXPLORER) {
			if (this.mCapabilities != null) {
				driver = new InternetExplorerDriver(this.mCapabilities);
			} else {
				driver = new InternetExplorerDriver();
			}
		} else if (browser == EBrowser.OPERA) {
			if (this.mCapabilities != null) {
				driver = new OperaDriver(this.mCapabilities);
			} else {
				driver = new OperaDriver();
			}
		} else if (browser == EBrowser.MS_EDGE) {
			if (this.mCapabilities != null) {
				driver = new EdgeDriver(this.mCapabilities);
			} else {
				driver = new EdgeDriver();
			}
		} else {
			throw new IllegalArgumentException("The given browser is not supported: " + browser);
		}

		// Wrap a stale refresher driver around
		driver = new StaleRefresherWebDriver(driver);

		// Wrap an anti trap driver around
		driver = new AntiTrapWebDriver(driver);

		// Wrap a delayed web driver around if desired
		if (this.mDelayEvents) {
			driver = new DelayedWebDriver(driver);
		}

		return driver;

	}

	/**
	 * Shutdowns a given instance and remove it from the internal list of
	 * instances. By shutting down an instance, it does not get logged out
	 * automatically. If wished, call it before shutting down. After shutdown,
	 * an instance is invalid and should not be used anymore.
	 * 
	 * @param instance
	 *            Instance to shutdown
	 * @param doQuitDriver
	 *            <tt>True</tt> if the browser driver of this instance should be
	 *            quit at shutdown, <tt>false</tt> if not. If not quit the
	 *            browser can still be accessed by the user even after API
	 *            shutdown.
	 * 
	 */
	private void shutdownInstance(final IFreewarInstance instance, final boolean doQuitDriver) {
		if (instance != null) {
			instance.shutdown(doQuitDriver);
			this.mInstances.remove(instance);
		}
	}
}
