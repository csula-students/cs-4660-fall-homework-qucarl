package csula.cs4660.graphs.searches;
import csula.cs4660.games.models.Tile;
import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 
 * Perform A* search
 * 
 */

public class AstarSearch implements SearchStrategy {
	private Map<Node, Node> relation = new HashMap<>();
	private List<Node> pri_queue = new LinkedList<Node>();
	private Map<Node, Integer> distance = new HashMap<>();
	private ArrayList<Edge> result = new ArrayList<>();
	private static final float D = 1;

	@SuppressWarnings("unchecked")
	@Override

	public List<Edge> search(Graph graph, Node source, Node dist) {
		pri_queue.add(source);
		distance.put(source, 0);
		source.visited = true;

		while (!pri_queue.isEmpty()) {
			// sort queue
			Collections.sort(pri_queue, new Comparator<Node>() { // sort by distance		
				@Override
				public int compare(Node n1, Node n2) {
					return distance.get(n1).compareTo(distance.get(n2));
				}
			});
			@SuppressWarnings("rawtypes")
			Node current = pri_queue.get(0);
			
			//find the GOAL
			if (current.equals(dist)) {
				Node child = dist;
				while (!child.equals(source)) {
					Node parent = relation.get(child);
					if (parent != null) {
						Edge ed = new Edge(parent, child, graph.distance(parent, child));
						result.add(ed);
						child = parent;
					} else {
						break;
					}
				}
				// reverse the list
				Collections.reverse(result);
				return result;
			}
			//if the current have neighbors
			if (graph.neighbors(current) != null) {
				for (@SuppressWarnings("rawtypes") Node node : graph.neighbors(current)) {
					// distance from source (g value)
					int ds = distance.get(current) + graph.distance(current, node);
						Tile n = (Tile) node.getData();
						Tile g = (Tile) dist.getData();
						int dx = Math.abs(n.getX() - g.getX());
						int dy = Math.abs(n.getY() - g.getY());
						int dg = (int) D * (dx + dy);
					
					//g value + s value
					int dist_h = ds + dg; 
					if ((distance.get(node) != null)) {
						if ((dist_h) < distance.get(node)) {
							distance.put(node, dist_h); // update distance
							relation.put(node, current); // update relationship
						}
					} else {
						distance.put(node, dist_h);
						relation.put(node, current);
					}
					if (!node.visited) {
						pri_queue.add(node);
						node.visited = true;
					}
				}
				pri_queue.remove(current);
			} else {
				pri_queue.remove(current);
			}
		}
		return null;
	}
}