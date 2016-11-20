package csula.cs4660.games;

import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;
import csula.cs4660.games.models.MiniMaxState;

public class MiniMax {
	public static Node getBestMove(Graph graph, Node root, Integer depth, Boolean max) {
		// TODO: implement minimax to retrieve best move
		// NOTE: you should mutate graph and node as you traverse and update
		// value

		miniMax(graph, root, depth, max);
		int rootValue = ((MiniMaxState) root.getData()).getValue();
		for (Node node : graph.neighbors(root)) {
			int nodeValue = ((MiniMaxState) node.getData()).getValue();
			if (rootValue == nodeValue) {
				System.out.println(((MiniMaxState) graph.getNode(root).get().getData()).getIndex() + " "
						+ ((MiniMaxState) graph.getNode(root).get().getData()).getValue());
				return graph.getNode(node).get();
			}
		}
		return root;
	}

	private static Node miniMax(Graph graph, Node root, Integer depth, Boolean max) {
		MiniMaxState state = (MiniMaxState) graph.getNode(root).get().getData();
		if (max) {
			int bestValue = Integer.MIN_VALUE;
			for (Node node : graph.neighbors(graph.getNode(root).get())) {
				int value = ((MiniMaxState) miniMax(graph, node, depth - 1, false).getData()).getValue();
				if (value >= bestValue) {
					bestValue = value;
				}
				state.setValue(bestValue);
			}
			System.out.println(((MiniMaxState) graph.getNode(root).get().getData()).getIndex() + " "
					+ ((MiniMaxState) graph.getNode(root).get().getData()).getValue());
			return graph.getNode(root).get();
		} else {
			int bestValue = Integer.MAX_VALUE;
			for (Node node : graph.neighbors(graph.getNode(root).get())) {
				int value = ((MiniMaxState) miniMax(graph, node, depth - 1, true).getData()).getValue();
				if (value <= bestValue) {
					bestValue = value;
				}
				state.setValue(bestValue);
			}
			System.out.println(((MiniMaxState) graph.getNode(root).get().getData()).getIndex() + " "
					+ ((MiniMaxState) graph.getNode(root).get().getData()).getValue());
			return graph.getNode(root).get();
		}
	}
}
