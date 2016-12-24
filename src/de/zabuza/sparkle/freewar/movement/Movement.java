package de.zabuza.sparkle.freewar.movement;

import java.awt.Point;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import de.zabuza.pathweaver.network.Path;
import de.zabuza.pathweaver.network.algorithm.shortestpath.DijkstraShortestPathComputation;
import de.zabuza.pathweaver.network.algorithm.shortestpath.IShortestPathComputation;
import de.zabuza.sparkle.freewar.frames.EFrame;
import de.zabuza.sparkle.freewar.frames.IFrameManager;
import de.zabuza.sparkle.freewar.location.ILocation;
import de.zabuza.sparkle.freewar.movement.network.FreewarNetwork;
import de.zabuza.sparkle.freewar.movement.network.FreewarNode;
import de.zabuza.sparkle.selectors.CSSSelectors;
import de.zabuza.sparkle.selectors.Classes;
import de.zabuza.sparkle.wait.EventQueueEmptyWait;

/**
 * Movement of a {@link de.zabuza.sparkle.freewar.IFreewarInstance
 * IFreewarInstance}. Can be used to move the player through the world.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 * 
 */
public final class Movement implements IMovement {
	/**
	 * Timeout in milliseconds to wait for the move waiting method cycle to
	 * check its condition.
	 */
	private static final long MOVE_WAITING_TIMEOUT = 500;

	/**
	 * The shortest path computation object to use for routing.
	 */
	private final IShortestPathComputation m_Computation;
	/**
	 * The web driver used by this movement.
	 */
	private final WebDriver m_Driver;
	/**
	 * Manager to use for switching frames.
	 */
	private final IFrameManager m_FrameManager;
	/**
	 * The location object used by this movement.
	 */
	private final ILocation m_Location;
	/**
	 * The current movement task to use.
	 */
	private MovementTask m_MovementTask;
	/**
	 * The network to use for routing.
	 */
	private final FreewarNetwork m_Network;

	/**
	 * Creates a new movement object that uses the given driver.
	 * 
	 * @param driver
	 *            Web driver this object should use
	 * @param location
	 *            Location object this object should use
	 * @param frameManager
	 *            Manager to use for switching frames
	 */
	public Movement(final WebDriver driver, final ILocation location, final IFrameManager frameManager) {
		m_Driver = driver;
		m_Location = location;
		m_FrameManager = frameManager;
		try {
			m_Network = FreewarNetwork.createFromWiki();
		} catch (IOException e) {
			throw new IllegalStateException("An error while creating the network occurred.");
		}
		m_Computation = new DijkstraShortestPathComputation(m_Network);
		m_MovementTask = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.movement.IMovement#cancelMovementTask()
	 */
	@Override
	public void cancelMovementTask() {
		if (m_MovementTask != null) {
			m_MovementTask.cancelTask();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.movement.IMovement#canMove()
	 */
	@Override
	public boolean canMove() {
		switchToMapFrame();

		final String css = CSSSelectors.MAP_TRAVEL_ON_TIME;
		final List<WebElement> travelOnElements = m_Driver.findElements(By.cssSelector(css));
		if (!travelOnElements.isEmpty()) {
			WebElement travelOnElement = travelOnElements.iterator().next();
			return travelOnElement.getText().isEmpty();
		} else {
			return true;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.movement.IMovement#hasMovementTask()
	 */
	@Override
	public boolean hasMovementTask() {
		return m_MovementTask != null && !m_MovementTask.hasTerminated();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.movement.IMovement#move(de.zabuza.sparkle.
	 * freewar .movement.EDirection)
	 */
	@Override
	public boolean move(final EDirection direction) {
		Point positionBefore = m_Location.getPosition();

		switchToMapFrame();
		String selector;
		if (direction == EDirection.NORTH) {
			selector = CSSSelectors.MAP_MOVE_NORTH_ANCHOR;
		} else if (direction == EDirection.EAST) {
			selector = CSSSelectors.MAP_MOVE_EAST_ANCHOR;
		} else if (direction == EDirection.SOUTH) {
			selector = CSSSelectors.MAP_MOVE_SOUTH_ANCHOR;
		} else if (direction == EDirection.WEST) {
			selector = CSSSelectors.MAP_MOVE_WEST_ANCHOR;
		} else if (direction == EDirection.NORTHEAST) {
			selector = CSSSelectors.MAP_MOVE_NORTHEAST_ANCHOR;
		} else if (direction == EDirection.SOUTHEAST) {
			selector = CSSSelectors.MAP_MOVE_SOUTHEAST_ANCHOR;
		} else if (direction == EDirection.SOUTHWEST) {
			selector = CSSSelectors.MAP_MOVE_SOUTHWEST_ANCHOR;
		} else {
			selector = CSSSelectors.MAP_MOVE_NORTHWEST_ANCHOR;
		}

		// Get move anchor and click it
		WebElement moveAnchor = m_Driver.findElement(By.cssSelector(selector));

		// Desired position is not reachable
		if (Classes.hasClass(moveAnchor, Classes.MAP_FIELD_NOCANGO)) {
			return false;
		}

		// If position is reachable then click it
		moveAnchor.click();

		// Wait for movement to be executed if delayed executor is being used
		new EventQueueEmptyWait(m_Driver).waitUntilCondition();

		Point positionAfter = m_Location.getPosition();

		// Player moved when positions alter
		return !positionBefore.equals(positionAfter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.movement.IMovement#moveTo(int, int)
	 */
	@Override
	public void moveTo(final int xCoordinate, final int yCoordinate) {
		final Point sourcePos = m_Location.getPosition();
		final Optional<FreewarNode> source = m_Network.getNodeByCoordinates((int) sourcePos.getX(),
				(int) sourcePos.getY());
		if (!source.isPresent()) {
			throw new AssertionError();
		}
		final Optional<FreewarNode> destination = m_Network.getNodeByCoordinates(xCoordinate, yCoordinate);
		if (!destination.isPresent()) {
			m_MovementTask = MovementTask.createCanceledTask();
			return;
		}

		final Optional<Path> path = m_Computation.computeShortestPath(source.get(), destination.get());
		if (!path.isPresent()) {
			m_MovementTask = MovementTask.createCanceledTask();
			return;
		}

		m_MovementTask = new MovementTask(path.get(), m_Location, this);
		m_MovementTask.start();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.movement.IMovement#moveWaiting(de.zabuza.
	 * sparkle.freewar.movement.EDirection)
	 */
	@Override
	public boolean moveWaiting(final EDirection direction) {
		// Wait for the player to be able to move
		while (!canMove()) {
			try {
				TimeUnit.MILLISECONDS.sleep(MOVE_WAITING_TIMEOUT);
			} catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}
		switchToMapFrame();
		return move(direction);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.sparkle.freewar.movement.IMovement#wasTaskSuccessful()
	 */
	@Override
	public boolean wasTaskSuccessful() {
		return m_MovementTask != null && m_MovementTask.hasTerminated() && !m_MovementTask.wasCanceled();
	}

	/**
	 * Switches to the map frame of <tt>Freewar</tt> and waits until it is
	 * loaded. It ensures that previous queued events are processed before
	 * switching frames.
	 */
	private void switchToMapFrame() {
		m_FrameManager.switchToFrame(EFrame.MAP);
	}
}
