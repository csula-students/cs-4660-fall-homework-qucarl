package csula.cs4660.graphs.representations;

import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Node;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

/**
 * Object oriented representation of graph is using OOP approach to store nodes
 * and edges
 *
 * TODO: Please fill the body of methods in this class
 */
public class ObjectOriented implements Representation {
    private Collection<Node> nodes;
    private Collection<Edge> edges;

    public ObjectOriented(File file) {
    	nodes = new ArrayList<Node>();
    	edges = new ArrayList<Edge>();
    	
    		try{
        	Scanner read = new Scanner(file);
        	int NodeNumber = Integer.parseInt(read.nextLine().trim());
        	while(read.hasNext()){
        		String eachLine = read.nextLine().trim();
        		String[] currentLine = eachLine.split(":");
    			Node fromNode = new Node(Integer.parseInt(currentLine[0]));
    			Node toNode = new Node(Integer.parseInt(currentLine[1]));
    			int edgeValue = Integer.parseInt(currentLine[2]);
    			nodes.add(fromNode);
    			Edge edge = new Edge(fromNode, toNode, edgeValue);
    			edges.add(edge);
    			}
        	read.close();
        	} catch (IOException e){
        		e.printStackTrace();
        	}
        
    }

    public ObjectOriented() {

    }

    @Override
    public boolean adjacent(Node x, Node y) {
    	for(Edge edge : edges){
    		if(edge.getFrom().equals(x) && edge.getTo().equals(y)){
    			return true;
    		}
    	}
        return false;
    }

    @Override
    public List<Node> neighbors(Node x) {
    		List<Node> neighbors = new ArrayList<Node>();
    		for(Edge edge : edges){
    			if(edge.getFrom().equals(x)){
    				neighbors.add(edge.getTo());
    			}
    		}
    		return neighbors;
    }

    @Override
    public boolean addNode(Node x) {
    	if(nodes.contains(x)){
    		return false;
    	}else{
    		nodes.add(x);
    		return true;
    	}
    }

    @Override
    public boolean removeNode(Node x) {
    	if(nodes.contains(x)){
    		nodes.remove(x);
        	List<Edge> removeEdge = new ArrayList<Edge>();
        	for(Edge edge : edges){
        		if(edge.getFrom().equals(x) || edge.getTo().equals(x)){
        			removeEdge.add(edge);
        		}
        	}
        	edges.removeAll(removeEdge);
            return true;
    	}else{
    		return false;
    	}

    }

    @Override
    public boolean addEdge(Edge x) {
    	if(edges.contains(x)){
    		return false;
    	}else{
    		edges.add(x);
    		return true;
    	}
    }

    @Override
    public boolean removeEdge(Edge x) {
    	if(edges.contains(x)){
    		edges.remove(x);
    		return true;
    	}else{
    		return false;
    	}
    }

    @Override
    public int distance(Node from, Node to) {
        return 0;
    }

    @Override
    public Optional<Node> getNode(int index) {
        return null;
    }
}
