package de.zabuza.sparkle.webdriver;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import de.zabuza.sparkle.locale.ErrorMessages;
import de.zabuza.sparkle.webdriver.event.IDelayableEvent;

/**
 * Thread that delayedly executes previous added events. The delay between
 * events is randomly generated by a normal distribution function. The executor
 * can be started using {@link #start()} and stopped using
 * {@link #stopExecution()}. Once stopped, it should not be used again.<br>
 * <br>
 * The standard delay between each event is {@link #STANDARD_DELAY}, upon that a
 * delay between {@link #RND_DELAY_MIN} and {@link #RND_DELAY_MAX} is added.
 * <br>
 * Whereas the standard deviation is {@link #RND_DELAY_AVERAGE}, thus about 75%
 * of all delays are between {@link #RND_DELAY_MIN} and
 * {@link #RND_DELAY_AVERAGE}.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class DelayedEventExecutor extends Thread implements IDelayedEventQueue {

	/**
	 * The standard deviation of the normal distribution function. Thus about
	 * 75% of all delays are between {@link #RND_DELAY_MIN} and this value.
	 */
	private final static long RND_DELAY_AVERAGE = 1_500;
	/**
	 * The maximal delay of the normal distribution function.
	 */
	private final static long RND_DELAY_MAX = 5_000;
	/**
	 * The minimal delay of the normal distribution function.
	 */
	private final static long RND_DELAY_MIN = 0;
	/**
	 * The standard delay which is added between all events.
	 */
	private final static long STANDARD_DELAY = 100;

	/**
	 * If all held events are executed.
	 */
	private boolean m_AllEventsExecuted;
	/**
	 * The event queue used for executing events.
	 */
	private final Queue<IDelayableEvent> m_EventQueue;
	/**
	 * The random generator used for generating delays.
	 */
	private final Random m_Rnd;
	/**
	 * If the thread should stop execution. Once stopped, it should not be used
	 * again.
	 */
	private boolean m_StopExecution;

	/**
	 * Creates a new delayed event executor. It can be started using
	 * {@link #start()} and stopped using {@link #stopExecution()}. Once
	 * stopped, it should not be used again. Events can be added using
	 * {@link #addEvent(IDelayableEvent)}, the executor will delayedly execute
	 * added events.
	 */
	public DelayedEventExecutor() {
		m_Rnd = new Random();
		m_EventQueue = new LinkedList<>();
		m_StopExecution = false;
		m_AllEventsExecuted = true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.sparkle.webdriver.IDelayedEventQueue#addEvent(de.zabuza.sparkle
	 * .webdriver.event.IDelayableEvent)
	 */
	@Override
	public void addEvent(final IDelayableEvent event) {
		m_AllEventsExecuted = false;
		m_EventQueue.add(event);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.webdriver.IDelayedEventQueue#isEmpty()
	 */
	@Override
	public boolean isEmpty() {
		return m_EventQueue.isEmpty() && m_AllEventsExecuted;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Thread#run()
	 */
	@Override
	public void run() {
		m_StopExecution = false;
		while (!m_StopExecution) {
			IDelayableEvent event = m_EventQueue.poll();
			try {
				if (event != null) {
					event.execute();
					if (m_EventQueue.isEmpty()) {
						m_AllEventsExecuted = true;
					}
					sleep(getRandomDelay());
				}
				sleep(STANDARD_DELAY);
			} catch (InterruptedException e) {
				System.err.println(ErrorMessages.DELAYED_EVENT_EXECUTOR_INTERRUPTED);
			}
		}
	}

	/**
	 * Stops the execution of the thread. Once stopped, the object should not be
	 * used anymore.
	 */
	public void stopExecution() {
		m_StopExecution = true;
	}

	/**
	 * Generates a random delay using a normal distribution function.
	 * 
	 * @return A random delay in milliseconds between {@link #RND_DELAY_MIN} and
	 *         {@link #RND_DELAY_MAX}. Where {@link #RND_DELAY_AVERAGE} is the
	 *         standard deviation, thus about 75% of all generated values are
	 *         between {@link #RND_DELAY_MIN} and {@link #RND_DELAY_AVERAGE}.
	 */
	private long getRandomDelay() {
		// Generate gaussd(min(|x|, RND_DELAY_MAX), RND_DELAY_MIN,
		// RND_DELAY_AVERAGE) with gaussd(x, mean, sigma) being the
		// gaussian distribution. This returns a number in [RND_DELAY_MIN,
		// RND_DELAY_MAX] with RND_DELAY_AVERAGE being the standard deviation
		// where [RND_DELAY_MIN, RND_DELAY_AVERAGE] has a percentage
		// of about 75%.
		double gaussian = Math.abs(m_Rnd.nextGaussian());
		double deviation = gaussian * RND_DELAY_AVERAGE + RND_DELAY_MIN;
		long delay = (long) Math.floor(Math.min(deviation, RND_DELAY_MAX));

		return delay;
	}

}
