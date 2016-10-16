package csula.cs4660.graphs.searches;

import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;
import java.util.*;

public class BFS implements SearchStrategy {
	@Override
	public List<Edge> search(Graph graph, Node source, Node dist) {
		Map<Node, Integer> distance = new HashMap<>();
		Queue<Node> queue = new LinkedList<>();

		queue.add(source);
		source.visited = true;
		distance.put(source, 0);
		Node endNode = null;
		boolean flagFind = false;
		while (!queue.isEmpty() && !flagFind) {
			Node current = queue.poll();
			for (Node neighbor : graph.neighbors(current)) {
//				System.out.println(neighbor.getData());
				if (!neighbor.visited) {
					int newDistance = distance.get(current) + graph.distance(current, neighbor);
					distance.put(neighbor, newDistance);
					neighbor.parent = current;
					if (neighbor.equals(dist)) {
						endNode = neighbor;
						flagFind = true;
					}
					queue.add(neighbor);
					neighbor.visited = true;
//					System.out.println(neighbor.getData());
				}
			}
		}
		List<Edge> result = new ArrayList<Edge>();
		Node node = endNode;
		while (node.parent != null) {
//			System.out.println(node.parent.getData());
			result.add(new Edge(node.parent, node, graph.distance(node.parent, node)));
			node = node.parent;
		}

		if (result != null) {
			Collections.reverse(result);
//			for(Edge edge : result){
//				System.out.println(edge.getFrom().getData() + " " + edge.getTo().getData()+" "+ edge.getValue());
//			}
			return result;
		}
		return null;
	}

}