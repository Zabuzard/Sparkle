package de.zabuza.sparkle;

import org.openqa.selenium.remote.DesiredCapabilities;

import de.zabuza.sparkle.freewar.EWorld;
import de.zabuza.sparkle.freewar.IFreewarInstance;
import de.zabuza.sparkle.webdriver.EBrowser;

/**
 * Interface for APIs that allow playing the MMORPG <tt>Freewar</tt>.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 * 
 */
public interface IFreewarAPI {
	/**
	 * Creates the capabilities to use with a browser for the given arguments.
	 * 
	 * @param browser
	 *            Browser to create capabilities for
	 * @param driverPath
	 *            Path to the driver or <tt>null</tt> if not set
	 * @param binaryPath
	 *            Path to the binary or <tt>null</tt> if not set
	 * @param userProfile
	 *            The name or the path to the user profile, depending on the
	 *            browser, or <tt>null</tt> if not set
	 * @return The capabilities to use or <tt>null</tt> if there are no
	 */
	public DesiredCapabilities createCapabilities(final EBrowser browser, final String driverPath,
			final String binaryPath, final String userProfile);

	/**
	 * Gets the browser to use at logging in to accounts with
	 * {@link #login(String, String, EWorld)}. Once
	 * {@link #login(String, String, EWorld)} was used it will stick to the
	 * browser set at method call. By that multiple instances using different
	 * browsers can be created.
	 * 
	 * @return The current set browser
	 */
	public EBrowser getBrowser();

	/**
	 * Hijacks the given session of a logged in account in <tt>Freewar</tt>.
	 * This allows a login without knowing the password which is especially for
	 * service applications. The resulting instance is the same as if regularly
	 * using {@link #login(String, String, EWorld)} provided the session is
	 * valid. If the session is not valid the method itself will not throw any
	 * exceptions as the session may become valid in the future by external
	 * force, however most further methods will likely throw exceptions. The API
	 * is capable of holding multiple {@link IFreewarInstance}s. If logging in
	 * different accounts of the same world, make sure to use different
	 * browsers. Else <tt>Freewar</tt> will automatically logout the first
	 * account. Set different browsers using {@link #setBrowser(EBrowser)}
	 * before login.
	 * 
	 * @param sessionId
	 *            The id of the session to hijack, can be obtained by using
	 *            {@link IFreewarInstance#getSessionId()} or by other external
	 *            applications. It is maintained by <tt>Freewar</tt> and saved
	 *            in a cookie
	 * @param username
	 *            Username the session is valid for
	 * @param world
	 *            World the session is valid for
	 * @return Instance of the account after login or <tt>null</tt> if arguments
	 *         are invalid
	 */
	public IFreewarInstance hijackSession(final String sessionId, final String username, final EWorld world);

	/**
	 * Login to an account in <tt>Freewar</tt>. The API is capable of holding
	 * multiple {@link IFreewarInstance}s. If logging in different accounts of
	 * the same world, make sure to use different browsers. Else
	 * <tt>Freewar</tt> will automatically logout the first account. Set
	 * different browsers using {@link #setBrowser(EBrowser)} before login.
	 * 
	 * @param username
	 *            Username of the account
	 * @param password
	 *            Password of the account
	 * @param world
	 *            World the account is registered at
	 * @return Instance of the account after login or <tt>null</tt> if arguments
	 *         are invalid
	 */
	public IFreewarInstance login(final String username, final String password, final EWorld world);

	/**
	 * Logs out from an account in <tt>Freewar</tt>. After logout the given
	 * instance is invalid and should not be used anymore.
	 * 
	 * @param instance
	 *            Instance to logout
	 * @param doQuitDriver
	 *            <tt>True</tt> if the browser driver of the given instance
	 *            should be quit at logout, <tt>false</tt> if not. If not quit
	 *            the browser can still be accessed by the user even after API
	 *            shutdown.
	 */
	public void logout(final IFreewarInstance instance, final boolean doQuitDriver);

	/**
	 * Sets the browser to use at logging in to accounts with
	 * {@link #login(String, String, EWorld)}. Once
	 * {@link #login(String, String, EWorld)} was used it will stick to the
	 * browser set at method call. By that multiple instances using different
	 * browsers can be created.
	 * 
	 * @param browser
	 *            Browser to use
	 */
	public void setBrowser(final EBrowser browser);

	/**
	 * Sets the capabilities to use for browsers. Using this one can set for
	 * example browser profiles to use, or the path to the binary of the
	 * browser.
	 * 
	 * @param capabilities
	 *            The capabilities to set
	 */
	public void setCapabilities(final DesiredCapabilities capabilities);

	/**
	 * Shuts the API down, closing all remaining connections. This method does
	 * not necessarily logout from remaining {@link IFreewarInstance}s.
	 * 
	 * @param doQuitDriver
	 *            <tt>True</tt> if the browser drivers of instances should be
	 *            quit at shutdown, <tt>false</tt> if not. If not quit the
	 *            browsers can still be accessed by the user even after API
	 *            shutdown.
	 */
	public void shutdown(final boolean doQuitDriver);
}
