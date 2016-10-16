package csula.cs4660.graphs.representations;

import csula.cs4660.graphs.Edge;

import csula.cs4660.graphs.Node;

import java.io.BufferedReader;

import java.io.File;

import java.io.FileReader;

import java.io.IOException;

import java.util.*;

/**
 * 
 * Adjacency matrix in a sense store the nodes in two dimensional array
 *
 * 
 * 
 * TODO: please fill the method body of this class
 * 
 */

public class AdjacencyMatrix implements Representation {
	private Node[] nodes = {};
	private int[][] adjacencyMatrix = {};
	
	 public AdjacencyMatrix(File file) {
	 try {
	 Scanner read = new Scanner(file);
	 int NodeNumber = Integer.parseInt(read.nextLine().trim());
	 nodes = new Node[NodeNumber];
	 adjacencyMatrix = new int[NodeNumber][NodeNumber];
	 // System.out.println(adjacencyMatrix[0][0]);
	 // System.out.println(nodes[0]);
	 while (read.hasNext()) {
	 boolean nodeExist = false;
	 String eachLine = read.nextLine().trim();
	 // System.out.println(eachLine);
	 String[] currentLine = eachLine.split(":");
	 int fromNode = Integer.parseInt(currentLine[0]);
	 int toNode = Integer.parseInt(currentLine[1]);
	 int edgeValue = Integer.parseInt(currentLine[2]);
	 // System.out.println(fromNode);
	 // System.out.println(toNode);
	 // System.out.println(edgeValue);
	 // System.out.println(adjacencyList.containsKey(fromNode));
	 for (int i = 0; i < nodes.length; i++) {
	 nodes[i] = new Node(i);
	 }
	
	 // for(int i = 0; i< nodes.length; i++){
	 // if((int)nodes[i].getData() == fromNode){
	 // nodeExist = true;
	 // }
	 // }
	 // if(nodeExist == false){
	 // for(int i = 0; i< nodes.length; i++){
	 // if((int)nodes[i].getData() == 0){
	 // nodes[i]=new Node(fromNode);
	 // }
	 // }
	 // }
	 if (adjacencyMatrix[fromNode][toNode] == 0) {
	 adjacencyMatrix[fromNode][toNode] = edgeValue;
	 }
	 }
	 read.close();
	 } catch (IOException e) {
	 e.printStackTrace();
	 }
	 }
	
	 public AdjacencyMatrix() {
	
	 }
	 @Override
	 public boolean adjacent(Node x, Node y) {
	 int nodeFrom = (int) x.getData();
	 int nodeTo = (int) y.getData();
	 if (adjacencyMatrix[nodeFrom][nodeTo] == 0) {
	 return false;
	 } else {
	 return true;
	 }
	 }
	
	 @Override
	 public List<Node> neighbors(Node x) {
	 boolean inArray = false;
	 int positionX = 0;
	 for (int index = 0; index < nodes.length; index++) {
	 if (nodes[index].equals(x)) {
	 inArray = true;
	 positionX = index;
	 break;
	 }
	 }
	 if (!inArray)
	 return null;
	 ArrayList<Node> neighbors = new ArrayList<Node>();
	 for (int index = 0; index < adjacencyMatrix[positionX].length; index++) {
	 if (adjacencyMatrix[positionX][index] > 0)
	 neighbors.add(nodes[index]);
	 }
	 return neighbors;
	 }

	@Override
	public boolean addNode(Node x) {
		for (int index = 0; index < nodes.length; index++) {
			if (nodes[index].equals(x))
				return false;
		}
		Node[] newNodes = new Node[nodes.length + 1];
		int[][] newMatrix = new int[nodes.length + 1][nodes.length + 1];
		for (int index = 0; index < nodes.length; index++) {
			newNodes[index] = nodes[index];
		}
		newNodes[nodes.length] = x;
		for (int i = 0; i < adjacencyMatrix.length; i++) {
			for (int j = 0; j < adjacencyMatrix[0].length; j++) {
				newMatrix[i][j] = adjacencyMatrix[i][j];
			}
			newMatrix[i][adjacencyMatrix[0].length] = 0;
		}
		Arrays.fill(newMatrix[adjacencyMatrix.length], 0);
		nodes = newNodes;
		adjacencyMatrix = newMatrix;
		return true;
	}

	@Override
	public boolean removeNode(Node x) {
		boolean nodeExist = false;
		int nodeIndex = -1;
		for (int i = 0; i < nodes.length; i++) {
			if (nodes[i].equals(x)) {
				nodeExist = true;
				nodeIndex = i;
				break;

			}
		}
		if (nodeExist) {
			Node[] newNodes = new Node[nodes.length - 1];
			for (int m = 0; m < nodeIndex; m++) {
				newNodes[m] = nodes[m];
			}
			for (int m = nodeIndex; m < nodes.length - 1; m++) {
				newNodes[m] = nodes[m + 1];
			}
			nodes = newNodes;
			for (int j = 0; j < nodes.length; j++) {
				adjacencyMatrix[nodeIndex][j] = 0;
				adjacencyMatrix[j][nodeIndex] = 0;
			}
			return true;
		}
		return false;
	}

	// @Override
	// public boolean removeNode(Node x) {
	// // Node doesn't exist
	// if (!Arrays.asList(nodes).contains(x))
	// return false;
	//
	//
	// // Create new copy of nodes but exclude "x"
	// nodes = Arrays.stream(nodes).filter(n -> !n.equals(x))
	// .toArray(Node[]::new);
	//
	//
	//
	// int removed = (int) x.getData();
	// for (int index = 0; index < adjacencyMatrix.length; index++) {
	// adjacencyMatrix[removed][index] = 0;
	// adjacencyMatrix[index][removed] = 0;
	// }
	// return true;
	// }

	@Override
	public boolean addEdge(Edge x) {
		boolean fromInArray = false;
		boolean toInArray = false;
		int fromPos = 0;
		int toPos = 0;
		for (int index = 0; index < nodes.length; index++) {
			if (nodes[index].equals(x.getFrom())) {
				fromInArray = true;
				fromPos = index;
			}
			if (nodes[index].equals(x.getTo())) {
				toInArray = true;
				toPos = index;
			}
		}
		if (fromInArray && toInArray && (adjacencyMatrix[fromPos][toPos] == 0)) {
			adjacencyMatrix[fromPos][toPos] = x.getValue();
			return true;
		}
		return false;
	}
	// if (adjacencyMatrix[(int) x.getFrom().getData()][(int)
	// x.getTo().getData()] == x.getValue()) {
	// return false;
	// } else {
	// adjacencyMatrix[(int) x.getFrom().getData()][(int) x.getTo().getData()] =
	// x.getValue();
	// return true;
	// }
	// }

	@Override
	public boolean removeEdge(Edge x) {
		if (adjacencyMatrix[(int) x.getFrom().getData()][(int) x.getTo().getData()] == 0) {
			return false;
		} else {
			adjacencyMatrix[(int) x.getFrom().getData()][(int) x.getTo().getData()] = 0;
			return true;
		}
	}

	@Override
	public int distance(Node from, Node to) {
		boolean fromInArray = false;
		boolean toInArray = false;
		int fromPos = 0;
		int toPos = 0;
		for (int index = 0; index < nodes.length; index++) {
			if (nodes[index].equals(from)) {
				fromInArray = true;
				fromPos = index;
			}
			if (nodes[index].equals(to)) {
				toInArray = true;
				toPos = index;
			}
		}
		return adjacencyMatrix[fromPos][toPos];
	}

	@Override
	public Optional<Node> getNode(int index) {
		return null;
	}
}
