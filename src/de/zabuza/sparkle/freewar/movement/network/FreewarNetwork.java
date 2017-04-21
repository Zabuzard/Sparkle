package de.zabuza.sparkle.freewar.movement.network;

import java.awt.Point;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import de.zabuza.pathweaver.network.DirectedWeightedEdge;
import de.zabuza.pathweaver.network.Node;
import de.zabuza.pathweaver.network.PathNetwork;
import de.zabuza.pathweaver.util.NestedMap2;
import de.zabuza.sparkle.freewar.inventory.services.magicsphere.EBlueSphereDestination;
import de.zabuza.sparkle.freewar.inventory.services.magicsphere.MagicSphere;
import de.zabuza.sparkle.freewar.movement.EDirection;
import de.zabuza.sparkle.selectors.Patterns;

/**
 * A path network which consists of positions and edges that model the world of
 * 'Freewar'. The cost of an edge is measured in amount of actions.
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
	 * Number of the pattern group for the x coordinates.
	 */
	private final static int PATTERN_COORDINATES_X_GROUP = 1;
	/**
	 * Number of the pattern group for the y coordinates.
	 */
	private final static int PATTERN_COORDINATES_Y_GROUP = 2;
	/**
	 * Exception message which is shown when the unsupported operation
	 * {@link #addEdge(Node, Node, float)} is called.
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
		final Pattern coordinatePattern = Pattern.compile(Patterns.WIKI_CONTENT_COORDINATES);
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

		// TODO Create node action edges

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
	 * Set which holds all temporary edges that are currently in the network.
	 */
	private final HashSet<DirectedWeightedEdge> m_TemporaryEdges;

	/**
	 * Creates an empty freewar network.
	 */
	public FreewarNetwork() {
		super();
		this.m_CoordinatesToNode = new NestedMap2<>();
		this.m_TemporaryEdges = new HashSet<>();
	}

	/**
	 * Adds an edge between the given nodes. The cost of this edge is dependent
	 * on the given type.
	 * 
	 * @param source
	 *            The source node of the edge
	 * @param destination
	 *            The destination node of the edge
	 * @param type
	 *            The type of the edge to add
	 * @return The edge which was created and added
	 */
	public DirectedWeightedEdge addEdge(final FreewarNode source, final FreewarNode destination, final EMoveType type) {
		final float edgeCost = NetworkUtil.getCostOfMoveType(type);
		return super.addEdge(source, destination, edgeCost);
	}

	/**
	 * This method is not supported by {@link FreewarNetwork}. Use
	 * {@link #addEdge(FreewarNode, FreewarNode, EMoveType)} instead.
	 */
	@Override
	public DirectedWeightedEdge addEdge(final Node source, final Node destination, final float cost)
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
			this.m_CoordinatesToNode.put(Integer.valueOf(node.getXCoordinate()), Integer.valueOf(node.getYCoordinate()),
					node);
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
	 * Adds a set of special edges which are relative to the given node. As they
	 * change frequently, they should be removed and re-added again for each
	 * computation task.
	 * 
	 * @param node
	 *            The node to which the edges are relative to
	 * @param options
	 *            A set which contains all movement types to allow
	 */
	public void addTemporaryEdges(final FreewarNode node, final Set<EMoveType> options) {
		if (options.contains(EMoveType.BLUE_SPHERE)) {
			final Point anatubia = MagicSphere.getBlueSphereCoordinatesByDestination(EBlueSphereDestination.ANATUBIA);
			this.m_TemporaryEdges
					.add(addEdge(node, getNodeByCoordinates(anatubia.x, anatubia.y).get(), EMoveType.BLUE_SPHERE));
			final Point buran = MagicSphere.getBlueSphereCoordinatesByDestination(EBlueSphereDestination.BURAN);
			this.m_TemporaryEdges
					.add(addEdge(node, getNodeByCoordinates(buran.x, buran.y).get(), EMoveType.BLUE_SPHERE));
			final Point casinoOfFerdolia = MagicSphere
					.getBlueSphereCoordinatesByDestination(EBlueSphereDestination.CASINO_OF_FERDOLIA);
			this.m_TemporaryEdges.add(addEdge(node, getNodeByCoordinates(casinoOfFerdolia.x, casinoOfFerdolia.y).get(),
					EMoveType.BLUE_SPHERE));
			final Point hewn = MagicSphere.getBlueSphereCoordinatesByDestination(EBlueSphereDestination.HEWN);
			this.m_TemporaryEdges.add(addEdge(node, getNodeByCoordinates(hewn.x, hewn.y).get(), EMoveType.BLUE_SPHERE));
			final Point kanobia = MagicSphere.getBlueSphereCoordinatesByDestination(EBlueSphereDestination.KANOBIA);
			this.m_TemporaryEdges
					.add(addEdge(node, getNodeByCoordinates(kanobia.x, kanobia.y).get(), EMoveType.BLUE_SPHERE));
			final Point konlir = MagicSphere.getBlueSphereCoordinatesByDestination(EBlueSphereDestination.KONLIR);
			this.m_TemporaryEdges
					.add(addEdge(node, getNodeByCoordinates(konlir.x, konlir.y).get(), EMoveType.BLUE_SPHERE));
			final Point lodradon = MagicSphere.getBlueSphereCoordinatesByDestination(EBlueSphereDestination.LODRADON);
			this.m_TemporaryEdges
					.add(addEdge(node, getNodeByCoordinates(lodradon.x, lodradon.y).get(), EMoveType.BLUE_SPHERE));
			final Point lostValley = MagicSphere
					.getBlueSphereCoordinatesByDestination(EBlueSphereDestination.LOST_VALLEY);
			this.m_TemporaryEdges
					.add(addEdge(node, getNodeByCoordinates(lostValley.x, lostValley.y).get(), EMoveType.BLUE_SPHERE));
			final Point mentoran = MagicSphere.getBlueSphereCoordinatesByDestination(EBlueSphereDestination.MENTORAN);
			this.m_TemporaryEdges
					.add(addEdge(node, getNodeByCoordinates(mentoran.x, mentoran.y).get(), EMoveType.BLUE_SPHERE));
			final Point narubia = MagicSphere.getBlueSphereCoordinatesByDestination(EBlueSphereDestination.NARUBIA);
			this.m_TemporaryEdges
					.add(addEdge(node, getNodeByCoordinates(narubia.x, narubia.y).get(), EMoveType.BLUE_SPHERE));
			final Point nawor = MagicSphere.getBlueSphereCoordinatesByDestination(EBlueSphereDestination.NAWOR);
			this.m_TemporaryEdges
					.add(addEdge(node, getNodeByCoordinates(nawor.x, nawor.y).get(), EMoveType.BLUE_SPHERE));
			final Point orewu = MagicSphere.getBlueSphereCoordinatesByDestination(EBlueSphereDestination.OREWU);
			this.m_TemporaryEdges
					.add(addEdge(node, getNodeByCoordinates(orewu.x, orewu.y).get(), EMoveType.BLUE_SPHERE));
			final Point reikan = MagicSphere.getBlueSphereCoordinatesByDestination(EBlueSphereDestination.REIKAN);
			this.m_TemporaryEdges
					.add(addEdge(node, getNodeByCoordinates(reikan.x, reikan.y).get(), EMoveType.BLUE_SPHERE));
			final Point sutrania = MagicSphere.getBlueSphereCoordinatesByDestination(EBlueSphereDestination.SUTRANIA);
			this.m_TemporaryEdges
					.add(addEdge(node, getNodeByCoordinates(sutrania.x, sutrania.y).get(), EMoveType.BLUE_SPHERE));
			final Point terasi = MagicSphere.getBlueSphereCoordinatesByDestination(EBlueSphereDestination.TERASI);
			this.m_TemporaryEdges
					.add(addEdge(node, getNodeByCoordinates(terasi.x, terasi.y).get(), EMoveType.BLUE_SPHERE));
			final Point universalBank = MagicSphere
					.getBlueSphereCoordinatesByDestination(EBlueSphereDestination.UNIVERSAL_BANK);
			this.m_TemporaryEdges.add(
					addEdge(node, getNodeByCoordinates(universalBank.x, universalBank.y).get(), EMoveType.BLUE_SPHERE));
			final Point valleyOfRuins = MagicSphere
					.getBlueSphereCoordinatesByDestination(EBlueSphereDestination.VALLEY_OF_RUINS);
			this.m_TemporaryEdges.add(
					addEdge(node, getNodeByCoordinates(valleyOfRuins.x, valleyOfRuins.y).get(), EMoveType.BLUE_SPHERE));
		}

		if (options.contains(EMoveType.YELLOW_SPHERE)) {
			this.m_TemporaryEdges.add(addEdge(node, getNodeByCoordinates(-798, -798).get(), EMoveType.YELLOW_SPHERE));
			this.m_TemporaryEdges.add(addEdge(node, getNodeByCoordinates(-785, -786).get(), EMoveType.YELLOW_SPHERE));
			this.m_TemporaryEdges.add(addEdge(node, getNodeByCoordinates(-803, -808).get(), EMoveType.YELLOW_SPHERE));
			this.m_TemporaryEdges.add(addEdge(node, getNodeByCoordinates(-347, -693).get(), EMoveType.YELLOW_SPHERE));
			this.m_TemporaryEdges.add(addEdge(node, getNodeByCoordinates(-599, -489).get(), EMoveType.YELLOW_SPHERE));
			this.m_TemporaryEdges.add(addEdge(node, getNodeByCoordinates(-823, -778).get(), EMoveType.YELLOW_SPHERE));
			this.m_TemporaryEdges.add(addEdge(node, getNodeByCoordinates(-507, -377).get(), EMoveType.YELLOW_SPHERE));
		}

		if (options.contains(EMoveType.BLACK_SPHERE)) {
			this.m_TemporaryEdges.add(addEdge(node, getNodeByCoordinates(-288, -721).get(), EMoveType.BLACK_SPHERE));
			this.m_TemporaryEdges.add(addEdge(node, getNodeByCoordinates(-922, -179).get(), EMoveType.BLACK_SPHERE));
			this.m_TemporaryEdges.add(addEdge(node, getNodeByCoordinates(-529, -169).get(), EMoveType.BLACK_SPHERE));
			this.m_TemporaryEdges.add(addEdge(node, getNodeByCoordinates(-804, -279).get(), EMoveType.BLACK_SPHERE));
		}

		if (options.contains(EMoveType.ICY_TELEPORTER)) {
			this.m_TemporaryEdges.add(addEdge(node, getNodeByCoordinates(1005, 1005).get(), EMoveType.ICY_TELEPORTER));
		}

		if (options.contains(EMoveType.PORTAL)) {
			this.m_TemporaryEdges.add(addEdge(node, getNodeByCoordinates(90, 115).get(), EMoveType.PORTAL));
			this.m_TemporaryEdges.add(addEdge(node, getNodeByCoordinates(64, 80).get(), EMoveType.PORTAL));
			this.m_TemporaryEdges.add(addEdge(node, getNodeByCoordinates(122, 100).get(), EMoveType.PORTAL));
			this.m_TemporaryEdges.add(addEdge(node, getNodeByCoordinates(72, 116).get(), EMoveType.PORTAL));
			this.m_TemporaryEdges.add(addEdge(node, getNodeByCoordinates(144, 126).get(), EMoveType.PORTAL));
			this.m_TemporaryEdges.add(addEdge(node, getNodeByCoordinates(121, 91).get(), EMoveType.PORTAL));
			this.m_TemporaryEdges.add(addEdge(node, getNodeByCoordinates(122, 116).get(), EMoveType.PORTAL));
			this.m_TemporaryEdges.add(addEdge(node, getNodeByCoordinates(62, 83).get(), EMoveType.PORTAL));
			this.m_TemporaryEdges.add(addEdge(node, getNodeByCoordinates(59, 106).get(), EMoveType.PORTAL));
			this.m_TemporaryEdges.add(addEdge(node, getNodeByCoordinates(129, 90).get(), EMoveType.PORTAL));
			this.m_TemporaryEdges.add(addEdge(node, getNodeByCoordinates(115, 100).get(), EMoveType.PORTAL));
			this.m_TemporaryEdges.add(addEdge(node, getNodeByCoordinates(111, 83).get(), EMoveType.PORTAL));
			this.m_TemporaryEdges.add(addEdge(node, getNodeByCoordinates(135, 115).get(), EMoveType.PORTAL));
			this.m_TemporaryEdges.add(addEdge(node, getNodeByCoordinates(58, 98).get(), EMoveType.PORTAL));
			this.m_TemporaryEdges.add(addEdge(node, getNodeByCoordinates(106, 93).get(), EMoveType.PORTAL));
			this.m_TemporaryEdges.add(addEdge(node, getNodeByCoordinates(110, 107).get(), EMoveType.PORTAL));
			this.m_TemporaryEdges.add(addEdge(node, getNodeByCoordinates(118, 124).get(), EMoveType.PORTAL));
			this.m_TemporaryEdges.add(addEdge(node, getNodeByCoordinates(96, 78).get(), EMoveType.PORTAL));
			this.m_TemporaryEdges.add(addEdge(node, getNodeByCoordinates(-605, -206).get(), EMoveType.PORTAL));
			this.m_TemporaryEdges.add(addEdge(node, getNodeByCoordinates(-100, -95).get(), EMoveType.PORTAL));
			this.m_TemporaryEdges.add(addEdge(node, getNodeByCoordinates(-286, -479).get(), EMoveType.PORTAL));
			this.m_TemporaryEdges.add(addEdge(node, getNodeByCoordinates(-827, -919).get(), EMoveType.PORTAL));
		}

		if (options.contains(EMoveType.RING_OF_THE_SANDWINDS)) {
			this.m_TemporaryEdges
					.add(addEdge(node, getNodeByCoordinates(98, 120).get(), EMoveType.RING_OF_THE_SANDWINDS));
			this.m_TemporaryEdges
					.add(addEdge(node, getNodeByCoordinates(98, 81).get(), EMoveType.RING_OF_THE_SANDWINDS));
			this.m_TemporaryEdges
					.add(addEdge(node, getNodeByCoordinates(121, 112).get(), EMoveType.RING_OF_THE_SANDWINDS));
			this.m_TemporaryEdges
					.add(addEdge(node, getNodeByCoordinates(72, 85).get(), EMoveType.RING_OF_THE_SANDWINDS));
			this.m_TemporaryEdges
					.add(addEdge(node, getNodeByCoordinates(123, 92).get(), EMoveType.RING_OF_THE_SANDWINDS));
			this.m_TemporaryEdges
					.add(addEdge(node, getNodeByCoordinates(123, 92).get(), EMoveType.RING_OF_THE_SANDWINDS));
			this.m_TemporaryEdges
					.add(addEdge(node, getNodeByCoordinates(65, 96).get(), EMoveType.RING_OF_THE_SANDWINDS));
			this.m_TemporaryEdges
					.add(addEdge(node, getNodeByCoordinates(100, 135).get(), EMoveType.RING_OF_THE_SANDWINDS));
			this.m_TemporaryEdges
					.add(addEdge(node, getNodeByCoordinates(45, 98).get(), EMoveType.RING_OF_THE_SANDWINDS));
		}

		if (options.contains(EMoveType.STAFF_OF_TRADE)) {
			this.m_TemporaryEdges.add(addEdge(node, getNodeByCoordinates(87, 90).get(), EMoveType.STAFF_OF_TRADE));
			this.m_TemporaryEdges.add(addEdge(node, getNodeByCoordinates(88, 89).get(), EMoveType.STAFF_OF_TRADE));
			this.m_TemporaryEdges.add(addEdge(node, getNodeByCoordinates(96, 101).get(), EMoveType.STAFF_OF_TRADE));
			this.m_TemporaryEdges.add(addEdge(node, getNodeByCoordinates(117, 113).get(), EMoveType.STAFF_OF_TRADE));
			this.m_TemporaryEdges.add(addEdge(node, getNodeByCoordinates(87, 87).get(), EMoveType.STAFF_OF_TRADE));
		}

		// TODO Implement home spell edges
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
		final FreewarNode node = this.m_CoordinatesToNode.get(Integer.valueOf(xCoordinate),
				Integer.valueOf(yCoordinate));
		if (node != null) {
			return Optional.of(node);
		}
		return Optional.empty();
	}

	/**
	 * Removes all special edges that where added by using
	 * {@link #addTemporaryEdges(FreewarNode, Set)}.
	 */
	public void removeTemporaryEdges() {
		for (final DirectedWeightedEdge edge : this.m_TemporaryEdges) {
			removeEdge(edge);
		}
		this.m_TemporaryEdges.clear();
	}

}
