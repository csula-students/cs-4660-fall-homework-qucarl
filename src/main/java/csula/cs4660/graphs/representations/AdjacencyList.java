package csula.cs4660.graphs.representations;

import csula.cs4660.graphs.Edge;
import csula.cs4660.graphs.Node;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Adjacency list is probably the most common implemaentation to store the unknown
 * loose graph
 *
 * TODO: please implement the method body
 */
public class AdjacencyList implements Representation {
    @SuppressWarnings("rawtypes")
	private HashMap<Node, Collection<Edge>> adjacencyList;

//    @SuppressWarnings({ "unchecked", "rawtypes" })
//	public static void main(String[] args){
//        File file1 = new File("C:/Users/Gang/Desktop/CS4660/cs-4660-fall-homework-qucarl/src/test/resources/homework-1/graph-1.txt");
//        AdjacencyList a = new AdjacencyList(file1);
//        a.adjacent(new Node(0), new Node(7));
//    }

    public AdjacencyList(File file) {
    	adjacencyList = new HashMap(); 
		try{
    	Scanner read = new Scanner(file);
    	int NodeNumber = Integer.parseInt(read.nextLine().trim());
    	while(read.hasNext()){
    		String eachLine = read.nextLine().trim();
//    		System.out.println(eachLine);
    		String[] currentLine = eachLine.split(":");
			Node fromNode = new Node(Integer.parseInt(currentLine[0]));
			Node toNode = new Node(Integer.parseInt(currentLine[1]));
			int edgeValue = Integer.parseInt(currentLine[2]);
//			System.out.println(fromNode);
//			System.out.println(toNode);
//			System.out.println(edgeValue);
//			System.out.println(adjacencyList.containsKey(fromNode));
			if(!adjacencyList.containsKey(fromNode)){
		    	List<Edge> edges = new ArrayList();
				Edge edge = new Edge(fromNode, toNode, edgeValue);
				edges.add(edge);
				adjacencyList.put(fromNode, edges);
			}else{
				ArrayList<Edge> edges = (ArrayList)adjacencyList.get(fromNode); 
				Edge edge = new Edge(fromNode, toNode, edgeValue); 
				edges.add(edge);
//				adjacencyList.replace(fromNode, edges);
			}
    	}
    	read.close();
    	} catch (IOException e){
    		e.printStackTrace();
    	}
    }

    public AdjacencyList() {

    }

    @Override
    public boolean adjacent(Node x, Node y) {
    	if(adjacencyList.containsKey(x)){
    		ArrayList<Edge> edges = (ArrayList)adjacencyList.get(x);
    		for(int i=0;i<edges.size();i++){
//    			System.out.println(edges.get(i).getTo());
    			if(edges.get(i).getTo().equals(y)){
    				return true;
    			}
    		}
    	}
    	System.out.println("false");
        return false;
    }

    @Override
    public List<Node> neighbors(Node x) {
		ArrayList<Node> nodes = new ArrayList<Node>();
    	if(adjacencyList.containsKey(x)){
    		ArrayList<Edge> edges = (ArrayList<Edge>) adjacencyList.get(x);
    		for(int i=0; i < adjacencyList.get(x).size(); i++){
    			nodes.add(edges.get(i).getTo());
    		}
    	}
		return nodes;
    }

    @Override
    public boolean addNode(Node x) {
    	if(adjacencyList.containsKey(x)){
    		return false;
    	}else{
    		List<Edge> edges = new ArrayList();
    		adjacencyList.put(x, edges);
    		return true;
    	}
    }

    @Override
    public boolean removeNode(Node x) {
    	if(adjacencyList.containsKey(x)){
//    		for(Collection<Edge> edges : adjacencyList.values()){
//    			for(Edge edge : edges){
//    				if(edge.getTo().equals(x)){
//    					edges.remove(edge);
//    					
//    				}
//    			}
//    		}
        	adjacencyList.forEach((key, value)->{
        		ArrayList<Edge> edge = (ArrayList<Edge>) value;
        		for(int i = 0; i< edge.size(); i++){
        			if(edge.get(i).getTo().equals(x)){
        				edge.remove(i);
        			}
        		}
        	});
    		adjacencyList.remove(x);
    		return true;
    	}else{
    		return false;
    	}
    }

    @Override
    public boolean addEdge(Edge x) {
    		if(adjacencyList.get(x.getFrom()).contains(x)){
    			return false;
    		}else{
    			adjacencyList.get(x.getFrom()).add(x);
    			return true;
    		}
    }

    @Override
    public boolean removeEdge(Edge x) {
    	if(adjacencyList.containsKey(x.getFrom())){
    		if(adjacencyList.get(x.getFrom()).contains(x)){
    			adjacencyList.get(x.getFrom()).remove(x);
    			return true;
    		}
    	}
		return false;
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
