package de.zabuza.sparkle.freewar.movement.network;

import de.zabuza.pathweaver.network.Node;
import de.zabuza.pathweaver.network.PathNetwork;

/**
 * A path network which consists of positions and edges that model the world of
 * 'Freewar'. The cost of a road is measured in amount of actions.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class FreewarNetwork extends PathNetwork {

	/**
	 * Exception message which is shown when the unsupported operation
	 * {@link #addEdge(Node, Node, int)} is called.
	 */
	private final static String UNSUPPORTED_ADD_EDGE = "Freewar networks only accept FreewarNode as nodes. Use addEdge(FreewarNode, FreewarNode, EMoveType) instead.";

	/**
	 * Exception message which is shown when the unsupported operation
	 * {@link #addNode(Node)} is called.
	 */
	private final static String UNSUPPORTED_ADD_NODE = "Freewar networks only accept FreewarNode as nodes. Use addFreewarNode(FreewarNode) instead.";

	/**
	 * Creates a freewar network by using the data of 'FreewarWiki'.
	 * 
	 * @return The freewar network created from the wiki
	 */
	public static FreewarNetwork createFromWiki() {
		// TODO Implement something
		return null;
	}

	/**
	 * Creates an empty freewar network.
	 */
	public FreewarNetwork() {
		super();
	}

	/**
	 * Adds a road between the given road nodes. The cost of this road is
	 * measured in seconds and computed using the distances of the road nodes.
	 * 
	 * @param source
	 *            The source node of the road
	 * @param destination
	 *            The destination node of the road
	 * @param type
	 *            The type of the road to add
	 */
	public void addEdge(final FreewarNode source, final FreewarNode destination, final EMoveType type) {
		// TODO Implement logic by using util which assigns each type a
		// different cost for distinction
		final float edgeCost = 1;
		super.addEdge(source, destination, edgeCost);
	}

	/**
	 * This method is not supported by {@link FreewarNetwork}. Use
	 * {@link #addEdge(FreewarNode, FreewarNode, EMoveType)} instead.
	 */
	@Override
	public void addEdge(final Node source, final Node destination, final float cost)
			throws UnsupportedOperationException {
		throw new UnsupportedOperationException(UNSUPPORTED_ADD_EDGE);
	}

	/**
	 * Adds a given freewar node to the network.
	 * 
	 * @param node
	 *            The node to add
	 * @return <tt>True</tt> if the node was added, i.e. was not contained
	 *         before, <tt>false</tt> otherwise
	 */
	public boolean addFreewarNode(final FreewarNode node) {
		boolean wasAdded = super.addNode(node);
		return wasAdded;
	}

	/**
	 * This method is not supported by {@link FreewarNetwork}. Use
	 * {@link #addFreewarNode(FreewarNode)} instead.
	 */
	@Override
	public boolean addNode(final Node node) throws UnsupportedOperationException {
		throw new UnsupportedOperationException(UNSUPPORTED_ADD_NODE);
	}

}
