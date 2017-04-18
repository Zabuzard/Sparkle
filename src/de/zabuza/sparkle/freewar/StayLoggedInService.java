package de.zabuza.sparkle.freewar;

import de.zabuza.sparkle.locale.ErrorMessages;

/**
 * Service that takes care of an {@link IFreewarInstance} not being
 * automatically logged out by <tt>Freewar</tt> due to absence. The service can
 * be started using {@link #start()} and stopped using {@link #stopExecution()}.
 * After stopping the service is invalid and should not be used anymore.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 * 
 */
public final class StayLoggedInService extends Thread {

	/**
	 * Time after which the service calls <tt>alive</tt> to <tt>Freewar</tt> by
	 * using {@link IFreewarInstance#refresh()}.
	 */
	private final static long TIMEOUT_ALIVE = 1_000 * 60 * 5;

	/**
	 * Instance the service takes care about.
	 */
	private final IFreewarInstance m_Instance;
	/**
	 * If the service should stop execution.
	 */
	private boolean m_StopExecution;

	/**
	 * Creates a new service that takes care of the given instance not being
	 * automatically logged out by <tt>Freewar</tt> due to absence. The service
	 * can be started using {@link #start()} and stopped using
	 * {@link #stopExecution()}. After stopping, the service is invalid and
	 * should not be used anymore.
	 * 
	 * @param instance
	 *            Instance the service takes care of
	 */
	public StayLoggedInService(final IFreewarInstance instance) {
		m_Instance = instance;
		m_StopExecution = false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		while (!m_StopExecution) {
			try {
				sleep(TIMEOUT_ALIVE);
				m_Instance.refresh();
			} catch (final InterruptedException e) {
				if (!m_StopExecution) {
					// Not interrupted for stopping
					System.err.println(ErrorMessages.STAY_LOGGED_IN_SERVICE_INTERRUPTED);
				}
			}
		}
	}

	/**
	 * If called the service will stop execution. After stopping, the service is
	 * invalid and should not be used anymore.
	 */
	public void stopExecution() {
		m_StopExecution = true;

		// Wake up thread if currently sleeping
		interrupt();
	}
}
