package de.zabuza.sparkle.webdriver;

import java.net.URL;

import org.openqa.selenium.WebDriver.Navigation;

import de.zabuza.sparkle.webdriver.event.BackEvent;
import de.zabuza.sparkle.webdriver.event.ForwardEvent;
import de.zabuza.sparkle.webdriver.event.RefreshEvent;
import de.zabuza.sparkle.webdriver.event.ToStringEvent;
import de.zabuza.sparkle.webdriver.event.ToUrlEvent;

public class DelayedNavigation implements Navigation {

	private final Navigation m_Navigation;
	private final IDelayedEventQueue m_Queue;

	public DelayedNavigation(final Navigation navigation, final IDelayedEventQueue queue) {
		m_Navigation = navigation;
		m_Queue = queue;
	}

	@Override
	public void back() {
		m_Queue.addEvent(new BackEvent(m_Navigation));
	}

	@Override
	public void forward() {
		m_Queue.addEvent(new ForwardEvent(m_Navigation));
	}

	@Override
	public void refresh() {
		m_Queue.addEvent(new RefreshEvent(m_Navigation));
	}

	@Override
	public void to(final String url) {
		m_Queue.addEvent(new ToStringEvent(m_Navigation, url));
	}

	@Override
	public void to(final URL url) {
		m_Queue.addEvent(new ToUrlEvent(m_Navigation, url));
	}

}
