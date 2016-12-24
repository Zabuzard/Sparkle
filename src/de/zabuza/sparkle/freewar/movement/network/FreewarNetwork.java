package de.zabuza.sparkle.freewar.movement.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.zabuza.pathweaver.network.Node;
import de.zabuza.pathweaver.network.PathNetwork;
import de.zabuza.pathweaver.util.NestedMap2;
import de.zabuza.sparkle.freewar.movement.EDirection;

/**
 * A path network which consists of positions and edges that model the world of
 * 'Freewar'. The cost of a road is measured in amount of actions.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class FreewarNetwork extends PathNetwork {

	/**
	 * Mask which indicates the end of the content in the wiki coordinate site.
	 */
	private final static String MASK_CONTENT_END = "id=\"wpSummaryLabel\"";

	/**
	 * Mask which indicates the start of the content in the wiki coordinate
	 * site.
	 */
	private final static String MASK_CONTENT_START = "id=\"mw-content-text\"";

	/**
	 * Coordinate pattern for the wiki content.
	 */
	private final static String PATTERN_COORDINATES = "(-?\\d+),(-?\\d+);?";
	/**
	 * Number of the pattern group for the x coordinates.
	 */
	private final static int PATTERN_COORDINATES_X_GROUP = 1;
	/**
	 * Number of the pattern group for the y coordinates.
	 */
	private final static int PATTERN_COORDINATES_Y_GROUP = 2;
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
	 * Url of the coordinate data of the wiki.
	 */
	private final static String WIKI_COORDINATE_SITE = "http://www.fwwiki.de/index.php?title=Koordinaten_(Liste)&action=edit";

	/**
	 * Creates a freewar network by using the data of 'FreewarWiki'.
	 * 
	 * @return The freewar network created from the wiki
	 * @throws IOException
	 *             If the data from the wiki is invalid or can not be found
	 */
	public static FreewarNetwork createFromWiki() throws IOException {
		final FreewarNetwork network = new FreewarNetwork();

		// Get and contents of FreewarWiki: Koordinaten (Liste)
		final URL coordinateSite;
		coordinateSite = new URL(WIKI_COORDINATE_SITE);

		String content = null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(coordinateSite.openStream()));
			StringBuilder contentBuilder = new StringBuilder();
			boolean isInsideContent = false;
			String line = br.readLine();
			while (br.ready() && line != null) {
				if (!isInsideContent && line.contains(MASK_CONTENT_START)) {
					isInsideContent = true;
				}
				if (isInsideContent) {
					contentBuilder.append(line);
				}
				if (isInsideContent && line.contains(MASK_CONTENT_END)) {
					isInsideContent = false;
					break;
				}

				// Read next line
				line = br.readLine();
			}

			content = contentBuilder.toString();
		} finally {
			if (br != null) {
				br.close();
			}
		}

		// Parse the coordinates
		int idToUse = 0;
		final Pattern coordinatePattern = Pattern.compile(PATTERN_COORDINATES);
		final Matcher coordinateMatcher = coordinatePattern.matcher(content);
		while (coordinateMatcher.find()) {
			final int xCoordinate = Integer.parseInt(coordinateMatcher.group(PATTERN_COORDINATES_X_GROUP));
			final int yCoordinate = Integer.parseInt(coordinateMatcher.group(PATTERN_COORDINATES_Y_GROUP));

			// Create and add the node to the network
			network.addFreewarNode(new FreewarNode(idToUse, xCoordinate, yCoordinate));
			idToUse++;
		}

		// Create ordinary edges from every node to each of its
		// neighbors if possible
		for (final Node node : network.getNodes()) {
			final FreewarNode nodeAsFreewarNode = (FreewarNode) node;
			createWalkingEdge(nodeAsFreewarNode, EDirection.NORTHWEST, network);
			createWalkingEdge(nodeAsFreewarNode, EDirection.NORTH, network);
			createWalkingEdge(nodeAsFreewarNode, EDirection.NORTHEAST, network);
			createWalkingEdge(nodeAsFreewarNode, EDirection.WEST, network);
			createWalkingEdge(nodeAsFreewarNode, EDirection.EAST, network);
			createWalkingEdge(nodeAsFreewarNode, EDirection.SOUTHWEST, network);
			createWalkingEdge(nodeAsFreewarNode, EDirection.SOUTH, network);
			createWalkingEdge(nodeAsFreewarNode, EDirection.SOUTHEAST, network);
		}

		// TODO Create special edges

		return network;
	}

	/**
	 * Creates a walking edge from the given source to the destination, given by
	 * its direction, if it is existent.
	 * 
	 * @param source
	 *            The Source node
	 * @param direction
	 *            Direction of the destination
	 * @param network
	 *            Network which holds all nodes and edges
	 */
	private static void createWalkingEdge(final FreewarNode source, final EDirection direction,
			final FreewarNetwork network) {
		final int xSource = source.getXCoordinate();
		final int ySource = source.getYCoordinate();

		final int xDestination;
		final int yDestination;
		if (direction == EDirection.NORTHWEST) {
			xDestination = xSource - 1;
			yDestination = ySource - 1;
		} else if (direction == EDirection.NORTH) {
			xDestination = xSource;
			yDestination = ySource - 1;
		} else if (direction == EDirection.NORTHEAST) {
			xDestination = xSource + 1;
			yDestination = ySource - 1;
		} else if (direction == EDirection.WEST) {
			xDestination = xSource - 1;
			yDestination = ySource;
		} else if (direction == EDirection.EAST) {
			xDestination = xSource + 1;
			yDestination = ySource;
		} else if (direction == EDirection.SOUTHWEST) {
			xDestination = xSource - 1;
			yDestination = ySource + 1;
		} else if (direction == EDirection.SOUTH) {
			xDestination = xSource;
			yDestination = ySource + 1;
		} else if (direction == EDirection.SOUTHEAST) {
			xDestination = xSource + 1;
			yDestination = ySource + 1;
		} else {
			throw new AssertionError("Unsupported direction.");
		}

		// Get the destination if existent
		final Optional<FreewarNode> destination = network.getNodeByCoordinates(xDestination, yDestination);
		if (destination.isPresent()) {
			// Add a walking edge from source to destination
			network.addEdge(source, destination.get(), EMoveType.WALKING);
		}
	}

	/**
	 * Structure which allows a fast access to nodes by their coordinates.
	 */
	private final NestedMap2<Integer, Integer, FreewarNode> m_CoordinatesToNode;

	/**
	 * Creates an empty freewar network.
	 */
	public FreewarNetwork() {
		super();
		m_CoordinatesToNode = new NestedMap2<>();
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
		final float edgeCost = NetworkUtil.getCostOfMoveType(type);
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
		if (wasAdded) {
			m_CoordinatesToNode.put(node.getXCoordinate(), node.getYCoordinate(), node);
		}
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

	/**
	 * Gets the freewar node represented by the given coordinates, if there is
	 * such a node in the network.
	 * 
	 * @param xCoordinate
	 *            The x-coordinate of the nodes position
	 * @param yCoordinate
	 *            The y-coordinate of the nodes position
	 * @return The freewar node represented by the given coordinates, if there
	 *         is such a node in the network
	 */
	public Optional<FreewarNode> getNodeByCoordinates(final int xCoordinate, final int yCoordinate) {
		final FreewarNode node = m_CoordinatesToNode.get(xCoordinate, yCoordinate);
		if (node != null) {
			return Optional.of(node);
		} else {
			return Optional.empty();
		}
	}

}
