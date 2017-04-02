package de.zabuza.sparkle;

import java.io.File;
import java.util.LinkedHashSet;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
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
import de.zabuza.sparkle.webdriver.DelayedWebDriver;
import de.zabuza.sparkle.webdriver.EBrowser;
import de.zabuza.sparkle.webdriver.IHasWebDriver;

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
	private EBrowser m_Browser;
	/**
	 * The capabilities to use for the created browsers.
	 */
	private DesiredCapabilities m_Capabilities;
	/**
	 * If the API should automatically delay events to disguise usage of a bot
	 * for <tt>Freewar.de</tt>.
	 */
	private final boolean m_DelayEvents;
	/**
	 * Set of all registered instances created with
	 * {@link #login(String, String, EWorld)}. Instances get added using
	 * {@link #login(String, String, EWorld)} and removed by using
	 * {@link #shutdownInstance(IFreewarInstance)}.
	 */
	private final Set<IFreewarInstance> m_Instances;

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
	 *            If the API should automatically delays events to disguise
	 *            usage of a bot for <tt>Freewar</tt>.
	 */
	public Sparkle(final EBrowser browser, final boolean delayEvents) {
		m_Browser = browser;
		m_DelayEvents = delayEvents;
		m_Capabilities = null;
		m_Instances = new LinkedHashSet<IFreewarInstance>();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.IFreewarAPI#createCapabilities(de.zabuza.sparkle.
	 * webdriver.EBrowser, java.lang.String, java.lang.String)
	 */
	@Override
	public DesiredCapabilities createCapabilities(final EBrowser browser, final String driverPath,
			final String binaryPath) {
		DesiredCapabilities capabilities = null;

		if (browser == EBrowser.FIREFOX) {
			capabilities = DesiredCapabilities.firefox();

			// Set the driver
			if (driverPath != null) {
				System.setProperty("webdriver.gecko.driver", driverPath);
				System.setProperty("webdriver.firefox.marionette", driverPath);
				capabilities.setCapability(FirefoxDriver.MARIONETTE, true);
			}

			// Set the binary
			if (binaryPath != null) {
				File pathToBinary = new File(binaryPath);
				FirefoxBinary binary = new FirefoxBinary(pathToBinary);
				capabilities.setCapability(FirefoxDriver.BINARY, binary);
			}
		} else if (browser == EBrowser.CHROME) {
			capabilities = DesiredCapabilities.chrome();

			// Set the driver
			if (driverPath != null) {
				System.setProperty("webdriver.chrome.driver", driverPath);
			}

			// Set the binary
			if (binaryPath != null) {
				capabilities.setCapability("chrome.binary", binaryPath);
			}
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
		return m_Browser;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.IFreewarAPI#login(java.lang.String,
	 * java.lang.String, de.zabuza.sparkle.freewar.EWorld)
	 */
	@Override
	public IFreewarInstance login(final String username, final String password, final EWorld world) {
		// Validate user credentials
		if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
			return null;
		}

		WebDriver driver = createWebDriver(m_Browser);

		// Connect to login form
		String fullWorldDomain = Paths.getFullWorldDomain(world);
		String loginUrl = fullWorldDomain + Paths.LOGIN;
		driver.get(loginUrl);

		// Wait for form elements and get them
		WebElement loginSubmit = new LoginFormWait(driver).waitUntilCondition();
		WebElement loginName = driver.findElement(By.cssSelector(CSSSelectors.LOGIN_FORM_NAME));
		WebElement loginPassword = driver.findElement(By.cssSelector(CSSSelectors.LOGIN_FORM_PASSWORD));

		// Type in user credentials
		loginName.clear();
		loginName.sendKeys(username);
		new TimedWait(driver, 200).waitUntilCondition();
		loginPassword.clear();
		loginPassword.sendKeys(password);

		// Submit form and close all pop-ups if existent
		String parentWindow = driver.getWindowHandle();
		loginSubmit.click();
		// Wait until pop-up pops up
		boolean isThereAPopup;
		try {
			new LoginPopupWait(driver).waitUntilCondition();
			isThereAPopup = true;
		} catch (TimeoutException e) {
			isThereAPopup = false;
		}
		// Close all pup-ups if existent
		if (isThereAPopup) {
			for (String window : driver.getWindowHandles()) {
				if (!window.equals(parentWindow)) {
					driver.switchTo().window(window);
					driver.close();
				}
			}
			driver.switchTo().window(parentWindow);

			// Wait until element
			WebElement popupContinue = driver.findElement(By.cssSelector(CSSSelectors.LOGIN_POPUP_CONTINUE));
			popupContinue.click();
		}

		IFreewarInstance instance = new FreewarInstance(driver);
		m_Instances.add(instance);

		return instance;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.IFreewarAPI#logout(de.zabuza.sparkle.freewar.
	 * IFreewarInstance)
	 */
	@Override
	public void logout(final IFreewarInstance instance) {
		if (instance instanceof IHasWebDriver) {
			WebDriver driver = ((IHasWebDriver) instance).getWebDriver();
			// Wait for events to be processed before switching frames
			new EventQueueEmptyWait(driver).waitUntilCondition();
			driver.switchTo().defaultContent();

			// Wait for menu frame and switch to it
			new FramePresenceWait(driver, Names.FRAME_MENU).waitUntilCondition();
			driver.switchTo().frame(Names.FRAME_MENU);
			// Click logout in menu
			WebElement logout = driver.findElement(By.cssSelector(CSSSelectors.MENU_LOGOUT_ANCHOR));
			logout.click();

			// Wait for click to be executed and switch to frameset
			new EventQueueEmptyWait(driver).waitUntilCondition();
			driver.switchTo().defaultContent();

			// Switch to map and click surely logout
			driver.switchTo().frame(Names.FRAME_MAP);
			WebElement surelyLogout = new CSSSelectorPresenceWait(driver, CSSSelectors.MAP_SURELY_LOGOUT_ANCHOR)
					.waitUntilCondition();
			surelyLogout.click();

			// Wait for logout to be fully executed and then shutdown
			new LoginFormWait(driver).waitUntilCondition();
		}
		shutdownInstance(instance);
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
		m_Browser = browser;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.IFreewarAPI#setCapabilities(org.openqa.selenium.
	 * Capabilities)
	 */
	@Override
	public void setCapabilities(final DesiredCapabilities capabilities) {
		m_Capabilities = capabilities;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.IFreewarAPI#shutdown()
	 */
	@Override
	public void shutdown() {
		for (IFreewarInstance instance : m_Instances) {
			shutdownInstance(instance);
		}
	}

	/**
	 * Creates a {@link #WebDriver} that uses the given browser. If a capability
	 * object was set using {@link #setCapabilities(Capabilities)} then it will
	 * also be passed to the created browser. If {@link #m_DelayEvents} is set
	 * to <tt>true</tt>, the resulting driver will automatically delay events to
	 * disguise usage of a bot for <tt>Freewar</tt>.
	 * 
	 * @param browser
	 *            Browser to use for the driver
	 * @return Webdriver that uses the given browser
	 */
	private WebDriver createWebDriver(final EBrowser browser) {
		WebDriver driver;
		if (browser == EBrowser.FIREFOX) {
			if (m_Capabilities != null) {
				driver = new FirefoxDriver(m_Capabilities);
			} else {
				driver = new FirefoxDriver();
			}
		} else if (browser == EBrowser.CHROME) {
			if (m_Capabilities != null) {
				driver = new ChromeDriver(m_Capabilities);
			} else {
				driver = new ChromeDriver();
			}
		} else if (browser == EBrowser.SAFARI) {
			if (m_Capabilities != null) {
				driver = new SafariDriver(m_Capabilities);
			} else {
				driver = new SafariDriver();
			}
		} else if (browser == EBrowser.INTERNET_EXPLORER) {
			if (m_Capabilities != null) {
				driver = new InternetExplorerDriver(m_Capabilities);
			} else {
				driver = new InternetExplorerDriver();
			}
		} else if (browser == EBrowser.OPERA) {
			if (m_Capabilities != null) {
				driver = new OperaDriver(m_Capabilities);
			} else {
				driver = new OperaDriver();
			}
		} else if (browser == EBrowser.MS_EDGE) {
			if (m_Capabilities != null) {
				driver = new EdgeDriver(m_Capabilities);
			} else {
				driver = new EdgeDriver();
			}
		} else {
			throw new IllegalArgumentException("The given browser is not supported: " + browser);
		}

		if (m_DelayEvents) {
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
	 */
	private void shutdownInstance(final IFreewarInstance instance) {
		if (instance != null) {
			instance.shutdown();
			m_Instances.remove(instance);
		}
	}
}
