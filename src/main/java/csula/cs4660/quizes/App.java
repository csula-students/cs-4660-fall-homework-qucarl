package csula.cs4660.quizes;

import csula.cs4660.graphs.Node;
import csula.cs4660.graphs.representations.Representation;
import csula.cs4660.graphs.searches.BFS;
import csula.cs4660.graphs.searches.DijkstraSearch;
import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Graph;
import csula.cs4660.quizes.models.State;

import java.util.*;

/**
 * Here is your quiz entry point and your app
 */
public class App {
	public static void main(String[] args) {
		State initialState = Client.getState("10a5461773e8fd60940a56d2e9ef7bf4").get();
		State goalState = Client.getState("e577aa79473673f6158cc73e0e5dc122").get();
//		System.out.println(Client.getState("4b2a41cdcc99bb9fdeeda9d4aec30970").get());
//		System.out.println(goalState);
		Node startNode = new Node(initialState);
		Node goalNode = new Node(goalState);

		Graph graph = new Graph(Representation.of(Representation.STRATEGY.ADJACENCY_LIST));

		Queue nodeQueue = new LinkedList<Node>();
		List<String> visitedId = new ArrayList<String>();
		Node nodeParent = new Node(initialState);
		graph.addNode(nodeParent);
		nodeQueue.add(nodeParent);
		visitedId.add(initialState.getId());
		while (!nodeQueue.isEmpty()) {
			// stateParent = (State) stateQueue.poll();
			nodeParent = (Node) nodeQueue.poll();
//			System.out.println(nodeParent.getData());
			State stateParent = (State) nodeParent.getData();
			// if (!nodeParent.visited) {
			for (State childState : stateParent.getNeighbors()) {
//				System.out.println(Client.getState(childState.getId()).get());
				if (!visitedId.contains(childState.getId())) {
					Node childNode = new Node(Client.getState(childState.getId()).get());
					// if (!childNode.visited) {
					graph.addNode(childNode);
					nodeQueue.add(childNode);
					int cost = Client.stateTransition(stateParent.getId(), childState.getId()).get().getEvent()
							.getEffect();
					Edge edge = new Edge(nodeParent, childNode, cost);
					graph.addEdge(edge);
					visitedId.add(childState.getId());
//					childNode.visited = true;
					// System.out.println(edge.getFrom()+ "->" + edge.getTo() +
					// ":" + edge.getValue());
				}
			}
			// nodeParent.visited = true;
			// }
		}

		List<Edge> BFS = graph.search(new BFS(), startNode, goalNode);
		List<Edge> DijkstraSearch = graph.search(new DijkstraSearch(), startNode, goalNode);
		System.out.println("BFS:");
		for (Edge edge : BFS) {
			State fromState = (State) edge.getFrom().getData();
			State toState = (State) edge.getTo().getData();
			System.out.println("From " + fromState.getLocation().getName() + " to " + toState.getLocation().getName());

		}
		System.out.println("");
		System.out.println("DijkstraSearch:");
		for (Edge edge : DijkstraSearch) {
			State fromState = (State) edge.getFrom().getData();
			State toState = (State) edge.getTo().getData();
			System.out.println("From " + fromState.getLocation().getName() + " to " + toState.getLocation().getName());

		}

	}
	// // to get a state, you can simply call `Client.getState with the id`
	// State initialState =
	// Client.getState("10a5461773e8fd60940a56d2e9ef7bf4").get();
	// System.out.println(initialState);
	// // to get an edge between state to its neighbor, you can call
	// stateTransition
	// System.out.println(Client.stateTransition(initialState.getId(),
	// initialState.getNeighbors()[0].getId()));
	//
	// Queue<State> frontier = new LinkedList<>();
	// Set<State> exploredSet = new HashSet<>();
	// Map<State, State> parents = new HashMap<>();
	// frontier.add(initialState);
	//
	// while (!frontier.isEmpty()) {
	// State current = frontier.poll();
	// exploredSet.add(current);
	//
	// // for every possible action
	// for (State neighbor:
	// Client.getState(current.getId()).get().getNeighbors()) {
	// // state transition
	// if (neighbor.getId().equals("e577aa79473673f6158cc73e0e5dc122")) {
	// // construct actions from endTile
	// System.out.println("found solution with depth of " + findDepth(parents,
	// current, initialState));
	// }
	// if (!exploredSet.contains(neighbor)) {
	// parents.put(neighbor, current);
	// frontier.add(neighbor);
	// }
	// }
	// }
	//
	// System.out.println("Not found solution");
	// }
	//
	// public static int findDepth(Map<State, State> parents, State current,
	// State start) {
	// State c = current;
	// int depth = 0;
	//
	// while (!c.equals(start)) {
	// depth ++;
	// c = parents.get(c);
	// }
	//
	// return depth;
	// }
}
