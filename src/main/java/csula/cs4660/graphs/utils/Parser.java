package csula.cs4660.graphs.utils;

import csula.cs4660.games.models.Tile;
import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Graph;
import csula.cs4660.graphs.Node;
import csula.cs4660.graphs.representations.Representation;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

/**
 * 
 * A quick parser class to read different format of files
 * 
 */

public class Parser {
	public static Graph readRectangularGridFile(Representation.STRATEGY graphRepresentation, File file) {
		Graph graph = new Graph(Representation.of(graphRepresentation));
		// TODO: implement the rectangular file read and add node with edges to
		// graph

		ArrayList<Edge> edges = new ArrayList<>();
		try {
			//Use scanner to read file
			Scanner read = new Scanner(file);
			int row = 0;
			ArrayList<Node> nodes_prev = new ArrayList<>();
			ArrayList<Node> nodes = new ArrayList<>();
			while (read.hasNext()) {
				//find node
				if (nodes != null) {
					// copy nodes to nodes_prev
					nodes_prev.clear();
					nodes_prev.addAll(nodes);
					nodes = new ArrayList<>();
				}
				String line = read.nextLine().trim();
				int count = -1;
				//read file line by line
				for (int i = 0; i < line.length(); i += 2) {
					// count only " " and "##" and node name like "@1"
					char c = line.charAt(i);
					if (c == '+') {
						// skip the first and last line
						row--; // for node to start from 0
					} else if (c == '-') {
						// skip
					} else if (c == '|') {
						// skip and the next one should start from index 0
						i--;
						//add nodes to graph
					} else if ((c == '#') || (c == ' ')) {
						count++;
						char c1 = line.charAt(i + 1);
						String tileValue = Character.toString(c) + "" + Character.toString(c1);
						Tile tile = new Tile(count, row, tileValue);
						Node n = new Node(tile);
						nodes.add(n);
						graph.addNode(n);
						//count it as a node
					} else if (c == '@') { 
						count++;
						//the node name c1
						char c1 = line.charAt(i + 1);
						String tileValue = Character.toString(c) + "" + Character.toString(c1);
						Tile tile = new Tile(count, row, tileValue);
						Node n = new Node(tile);
						nodes.add(n);
						graph.addNode(n);
					}
				}
				//find edges
				for (int i = 0; i < nodes.size() - 1; i++) {
					// check if the node on the right is a " "
					Tile tile_current = (Tile) nodes.get(i).getData();
					Tile tile_next = (Tile) nodes.get(i + 1).getData();
					if (!(tile_next.getType().equals("##"))) {
						if (!tile_current.getType().equals("##")) {
							Edge edge = new Edge(nodes.get(i), nodes.get(i + 1), 1);
							Edge edge2 = new Edge(nodes.get(i + 1), nodes.get(i), 1);
							graph.addEdge(edge);
							graph.addEdge(edge2);
						}
					}

					if (row > 0) {
						Tile t = (Tile) nodes.get(i).getData();
						if (!t.getType().equals("##")) {
							Tile pt = (Tile) nodes_prev.get(i).getData();
							if (!pt.getType().equals("##")) {
								Edge edge = new Edge(nodes_prev.get(i), nodes.get(i), 1);
								Edge edge2 = new Edge(nodes.get(i), nodes_prev.get(i), 1);
								graph.addEdge(edge);
								graph.addEdge(edge2);
							}
						}
					}
				}
				row++;
			}
			read.close();
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}
		return graph;
	}

	public static String converEdgesToAction(Collection<Edge> edges) {
		// TODO: convert a list of edges to a list of action
		String action = "";
		for (Edge ed : edges) {
			Tile t_from = (Tile) ed.getFrom().getData();
			Tile t_to = (Tile) ed.getTo().getData();
			int x_from = t_from.getX();
			int y_from = t_from.getY();
			int x_to = t_to.getX();
			int y_to = t_to.getY();
			
			 System.out.println(x_from + "," + y_from + "->" +
			 x_to + "," + y_to );
			
			if (x_from - x_to == -1) {
				action = action + "E";
			} else if (x_from - x_to == 1) {
				action = action + "W";
			} else if (y_from - y_to == -1) {
				action = action + "S";
			} else if (y_from - y_to == 1) {
				action = action + "N";
			}
		}
		if (!action.equals("")) {
			System.out.print(action);
			return action;
		}
		return "";

	}

}