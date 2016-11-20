package csula.cs4660.games;

import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;
import csula.cs4660.games.models.MiniMaxState;

public class AlphaBeta {
	public static Node getBestMove(Graph graph, Node source, Integer depth, Integer alpha, Integer beta, Boolean max) {
		// TODO: implement your alpha beta pruning algorithm here
		alphaBeta(graph, source, depth, alpha, beta, max);
		int rootValue = ((MiniMaxState) source.getData()).getValue();
		for (Node node : graph.neighbors(source)) {// find the solution node
			int nodeValue = ((MiniMaxState) node.getData()).getValue();
			if (rootValue == nodeValue) {
				System.out.println(((MiniMaxState) graph.getNode(node).get().getData()).getIndex() + " "
						+ ((MiniMaxState) graph.getNode(node).get().getData()).getValue());
				return graph.getNode(node).get();
			}
		}
		return source;
	}

	private static Node alphaBeta(Graph graph, Node source, Integer depth, Integer alpha, Integer beta, Boolean max) {
		MiniMaxState state = (MiniMaxState) graph.getNode(source).get().getData();
		if (max) {
			int bestValue = Integer.MIN_VALUE;
			for (Node node : graph.neighbors(graph.getNode(source).get())) {
				int value = ((MiniMaxState) alphaBeta(graph, node, depth - 1, alpha, beta, false).getData()).getValue();
				bestValue = Math.max(bestValue, value);
				if (bestValue > alpha) {
					alpha = bestValue;
				}
				state.setValue(bestValue);
				if (beta <= alpha) {
					break;// the node be cut-off
				}
			}
			System.out.println(((MiniMaxState) graph.getNode(source).get().getData()).getIndex() + " "
					+ ((MiniMaxState) graph.getNode(source).get().getData()).getValue());
			return graph.getNode(source).get();
		} else {
			int bestValue = Integer.MAX_VALUE;
			for (Node node : graph.neighbors(graph.getNode(source).get())) {
				int value = ((MiniMaxState) alphaBeta(graph, node, depth - 1, alpha, beta, true).getData()).getValue();
				bestValue = Math.min(bestValue, value);
				if (bestValue < beta) {
					beta = bestValue;
				}
				state.setValue(bestValue);
				if (beta <= alpha) {
					break;// the node be cut-off
				}
			}
			System.out.println(((MiniMaxState) graph.getNode(source).get().getData()).getIndex() + " "
					+ ((MiniMaxState) graph.getNode(source).get().getData()).getValue());
			return graph.getNode(source).get();
		}
	}
}
