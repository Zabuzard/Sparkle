package de.zabuza.sparkle.webdriver;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;

import de.zabuza.sparkle.locale.ErrorMessages;
import de.zabuza.sparkle.webdriver.event.IDelayableEvent;

public final class DelayedEventExecutor extends Thread implements IDelayedEventQueue {

	private final static long STANDARD_DELAY = 100;
	private final static long RND_DELAY_MIN = 0;
	private final static long RND_DELAY_AVERAGE = 1_500;
	private final static long RND_DELAY_MAX = 5_000;

	private final Queue<IDelayableEvent> m_EventQueue;
	private boolean m_StopExecution;
	private final Random m_Rnd;

	public DelayedEventExecutor() {
		m_Rnd = new Random();
		m_EventQueue = new LinkedList<>();
		m_StopExecution = false;
	}

	@Override
	public void addEvent(final IDelayableEvent event) {
		m_EventQueue.add(event);
	}

	public void stopExecution() {
		m_StopExecution = true;
	}

	@Override
	public void run() {
		m_StopExecution = false;
		while (!m_StopExecution) {
			IDelayableEvent event = m_EventQueue.poll();
			try {
				if (event != null) {
					event.execute();
					sleep(getRandomDelay());
				}
				sleep(STANDARD_DELAY);
			} catch (InterruptedException e) {
				System.err
						.println(ErrorMessages.DELAYED_EVENT_EXECUTOR_INTERRUPTED);
			}
		}
	}

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

	@Override
	public boolean isEmpty() {
		return m_EventQueue.isEmpty();
	}

}
