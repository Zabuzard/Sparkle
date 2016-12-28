package de.zabuza.sparkle.freewar.movement.network;

import de.zabuza.pathweaver.network.Node;

/**
 * Class for nodes that represent positions in the world of 'Freewar'.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class FreewarNode extends Node {
	/**
	 * The coordinate in x-direction of this node.
	 */
	private final int mXCoordinate;
	/**
	 * The coordinate in y-direction of this node.
	 */
	private final int mYCoordinate;

	/**
	 * Creates a new freewar node with given ID and a position in x- and
	 * y-coordinates.
	 * 
	 * @param id
	 *            The id of this node
	 * @param xCoordinate
	 *            The coordinate in x-direction of this node
	 * @param yCoordinate
	 *            The coordinate in y-direction of this node
	 */
	public FreewarNode(final int id, final int xCoordinate, final int yCoordinate) {
		super(id);
		mXCoordinate = xCoordinate;
		mYCoordinate = yCoordinate;
	}

	/**
	 * Gets the coordinate in x-direction of this node.
	 * 
	 * @return The coordinate in x-direction of this node
	 */
	public int getXCoordinate() {
		return mXCoordinate;
	}

	/**
	 * Gets the coordinate in y-direction of this node.
	 * 
	 * @return The coordinate in y-direction of this node
	 */
	public int getYCoordinate() {
		return mYCoordinate;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.zabuza.pathweaver.network.Node#toString()
	 */
	@Override
	public String toString() {
		return "<" + getXCoordinate() + ", " + getYCoordinate() + ">";
	}
}
