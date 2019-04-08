
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

import net.datastructures.AdjacencyMapGraph;
import net.datastructures.Edge;
import net.datastructures.Position;
import net.datastructures.Vertex;

/**
 * <h1>Scott Blair</h1>
 * <h2>CS526_Final Project</h2>
 * @author Scott Blair
 * @version 1.0
 * @since  2018-4-24
 *
 */
public class project extends AdjacencyMapGraph<String, Object> {
	static boolean running =true; //boolen for is program is running

	/**
	 * 
	 * @param args
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public static void main(String[] args) throws NumberFormatException, IOException {
		
		ArrayList<ArrayList<String>> rawMap = fileToArray("graph_input.txt");
		Map<String, Integer> distance = fileToMap("direct_distance.txt");
		project mainMap = new project(true);

		mainMap.addAllVertex(rawMap);
		mainMap.addAllEdges(rawMap);
		
		
		
		mainMenu(mainMap,distance);
		
		}
	
	/**
	 * 
	 * @param directed
	 */
	public project(boolean directed) {
		super(directed);
		// TODO Auto-generated constructor stub
	}

	/**Converts the file into a object list of process objects
	*precondition: the file is organized how it should be
	*postcondition: the file item are converted to an array
	*@return tempList returns the list of file data
	*/
	public static ArrayList<ArrayList<String>> fileToArray(String fileName) throws NumberFormatException, IOException{
		Path path = Paths.get(fileName); //specifies path to file
		List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);//read the file line by line and loads it into a list
		ArrayList<ArrayList<String>> tempList = new ArrayList<ArrayList<String>>();
		//lines.remove(0);//removes first item in array because i dont need to know that number of items
		
		int i=0;
		for(String line: lines){
			line = line.replaceAll("\\s+"," ");
			tempList.add(i, new ArrayList<String>(Arrays.asList(line.split(" "))));
			i+=1;
		}
		return tempList;
	}
		
	/**
	 * this method takes in a string and finds the vertex that has an element that is the same as the string and returns that vertex
	 * @param v specified string value of a given vertex in the tree
	 * @return the vertex with the specified string 
	 */
	public Vertex<String> getVertex(String v){
		v =v.toUpperCase();
		Iterator<Vertex<String>>itr =  this.vertices().iterator();	
		ArrayList <Vertex<String>> vList = new ArrayList<Vertex<String>>();
		String aList = "ABCDEFGHIJKLMNOPQRSTZ"; //there was not full alphabet in the input file 
		
		while(itr.hasNext()){			
			vList.add(itr.next());
		}
		return vList.get(aList.indexOf(v));
	}
	
	/**this method populates the tree with all of the edges
	 *converts the raw map {@link #modMap(ArrayList) modMap}
	 * @param rawMap is the 2D array list generated from the input file
	 */
	public void addAllEdges(ArrayList<ArrayList<String>> rawMap){		
		for(ArrayList<String> line: modMap(rawMap)){
			this.insertEdge(this.getVertex(line.get(0)), this.getVertex(line.get(1)), Integer.valueOf(line.get(2)));
		}
	}
	
	/**this method populates the tree with all of the vertexes
	 * 
	 * @param rawMap is the 2D array list generated from the input file
	 */
	public void addAllVertex(ArrayList<ArrayList<String>> rawMap){
		for(int i=0; i<rawMap.size();i++){
			if(i>0){
				this.insertVertex(rawMap.get(i).get(0));
			}
		}
	}

	/**
	 * this method modifies the raw map which looks like the input file, and just keeps all non empty indexes 
	 * @param rawMap is the 2D array list generated from the input file
	 * @return modified 2D array with now empty indexes 
	 */
	public static ArrayList<ArrayList<String>> modMap(ArrayList<ArrayList<String>> rawMap){
		ArrayList<ArrayList<String>> modMap = new ArrayList<ArrayList<String>>();
		
		for(int i=0; i<rawMap.size();i++){ //reads first dimension of array
			for(int j=0; j<rawMap.get(i).size();j++){ //reads second dimension of array
				ArrayList<String> line = new ArrayList<String>();
				if(i>0 && j>0){
					if(Integer.valueOf(rawMap.get(i).get(j))>0){
						line.add(rawMap.get(i).get(0)); 
						line.add(rawMap.get(0).get(j));
						line.add(rawMap.get(i).get(j));
						modMap.add(line);
					}
				}
			}
		}
		return modMap;
	}

	/**
	 * this handles the second file for the assignment and turns into a map to read from
	 * @param fileName specified file path
	 * @return a Map of the keys for all the nodes and their corresponding distances
	 * @throws NumberFormatException
	 * @throws IOException
	 */
	public static Map<String, Integer> fileToMap(String fileName) throws NumberFormatException, IOException{
		Path path = Paths.get(fileName); //specifies path to file
		List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);//read the file line by line and loads it into a list
		Map<String, Integer> tempDict = new HashMap<String, Integer>();
		
		for(String line: lines){
			ArrayList<String> bufferList =  new ArrayList<String>(Arrays.asList(line.split(" ")));
			tempDict.put(bufferList.get(0), Integer.valueOf(bufferList.get(1)));
		}
		return tempDict;
	}
	
	/**this method finds the minimum value in a map
	 * 
	 * @param distance Map that contains min value
	 * @return min value in map
	 */
	public static int minDistance(Map<String, Integer> distance){
		return Collections.min(distance.values());
	}
	
	/**this method find the key from a value, in a map
	 * 
	 * @param hm map with key needed
	 * @param value the corresponding value to the key
	 * @return the key of the corresponding value in the map
	 */
	public static Object getKeyFromValue(Map<String, Integer> hm, Object value) {
	    for (Object o : hm.keySet()) {
	      if (hm.get(o).equals(value)) {
	        return o;
	      }
	    }
	    return null;
	  }

	/**
	 * this method generates the test data for the algorithm 1 showing all the information for each node
	 *|{@link #getAdjacentNodes(Vertex) getAdjacentNodes} string of adjacent nodes to current node
	 *|{@link #toStringAdjacentDistance(Vertex, Map) toStringAdjacentDistance} shows all the distance values for adjacent nodes
	 *|{@link #minAdjacentDistance(Vertex, Map) minAdjacentDistance} finds min distance of adjacent nodes based off file 2
	 *|{@link #Algorithm1_Adjacency(String, String) Algorithm1_Adjacency} handles finding the best path based of adjacency
	 * @param distance map with all the distances for each node
	 * @return formatted output of test information 
	 */
	public String toStringAlgorithm1(Map<String, Integer> distance){
	    StringBuilder sb = new StringBuilder();
		Iterator<Vertex<String>>itr =  this.vertices().iterator();	

		while(itr.hasNext()){	
			Vertex<String> current = itr.next();
			sb.append("\nCurrent Node= "+ current.getElement() +"\n");
			sb.append( "Adjacent nodes: "+ this.getAdjacentNodes(current) + "\n");
			sb.append( this.toStringAdjacentDistance(current,distance));
			sb.append("Shortest Path: "+ this.Algorithm1_Adjacency(current.getElement(), this.minAdjacentDistance(current,distance)) + "\n");
		}
	    return sb.toString();		
	}
	
	/**
	 * this method generates the test data for the algorithm 1 showing all the information for each node
	 * |{@link #getAdjacentNodes(Vertex) getAdjacentNodes} string of adjacent nodes to current node
	 * |{@link #toStringAdjacentDistance2(Vertex, Map) toStringAdjacentDistance2} shows all the distance+weight values for adjacent nodes
	 * |{@link #minAdjacentDistance(Vertex, Map) minAdjacentDistance} finds min distance of adjacent nodes based off file 2
	 * |{@link #Algorithm1_Adjacency(String, String) Algorithm1_Adjacency} handles finding the best path based of adjacency
	 * @param distance map with all the distances for each node
	 * @return formatted output of test information 
	 
	 */
	public String toStringAlgorithm2(Map<String, Integer> distance){
	    StringBuilder sb = new StringBuilder();
		Iterator<Vertex<String>>itr =  this.vertices().iterator();	

		while(itr.hasNext()){	
			Vertex<String> current = itr.next();
			sb.append("\nCurrent Node= "+ current.getElement() +"\n");
			sb.append( "Adjacent nodes: "+ this.getAdjacentNodes(current) + "\n");
			sb.append( this.toStringAdjacentDistance2(current,distance));
			sb.append("Shortest Path: "+ this.Algorithm1_Adjacency(current.getElement(), this.minAdjacentDistance(current,distance)) + "\n");
		}
	    return sb.toString();		
	}
	
	/**
	 * this method finds the adjacent nodes to a specified vertex
	 * @param v specified vertex for which we want to get adjacent nodes
	 * @return adjacent nodes to specified vertex
	 */
	public String getAdjacentNodes(Vertex<String> v){
		StringBuilder sb = new StringBuilder();

		for (Edge<Object> e: outgoingEdges(v))
			sb.append(opposite(v,e).getElement());

		return sb.toString();		

	}
	
	/**
	 * this method generates a string consisting of all distances based off the second input file map for the test data {@link #toStringAlgorithm1(LinkedList, Map) toStringAlgorithm1}
	 * |{@link #getAdjacentNodes(Vertex) getAdjacentNodes} string of adjacent nodes to current node
	 * |{@link #getKeyFromValue(Map, Object) getKeyFromValue} gets a key from a value
	 * |{@link #minDistance(Map) minDistance} gets the min distance based off map of distances
	 * @param v vertex from which to find all adjacent node distances 
	 * @param distance the map of all distance for the nodes
	 * @return string of all the distances for adjacent nodes
	 
	 */
	public String toStringAdjacentDistance(Vertex<String> v, Map<String, Integer> distance){
		String adjacentNodes = this.getAdjacentNodes(v);
		StringBuilder sb = new StringBuilder();
		Map<String, Integer> tempDict = new HashMap<String, Integer>();
		List<String> tempList = new ArrayList<String>(Arrays.asList(adjacentNodes.split("")));
		
		for(String s: tempList){
			tempDict.put(s, distance.get(s));
			sb.append(s + ": dd("+s+")"+"= "+distance.get(s)+"\n");
		}
		sb.append(getKeyFromValue(tempDict,minDistance(tempDict))+ " is selected \n" );

		return sb.toString();		
	}
	
	/**
	 * this method get the key of the adjacent node with the minimum distance
	 * |{@link #getAdjacentNodes(Vertex) getAdjacentNodes} string of adjacent nodes to current node
	 * |{@link #getKeyFromValue(Map, Object) getKeyFromValue} gets a key from a value
	 * |{@link #minDistance(Map) minDistance} gets the min distance based off map of distances
	 * @param v node from which we want to find the adjacent node with the minimum distance
	 * @param distance the map of all the nodes and their distances
	 * @return the key of the adjacent node with the minimum distance
	 *
	 */
	public String minAdjacentDistance(Vertex<String> v, Map<String, Integer> distance){
		String adjacentNodes = this.getAdjacentNodes(v);
		StringBuilder sb = new StringBuilder();
		Map<String, Integer> tempDict = new HashMap<String, Integer>();
		List<String> tempList = new ArrayList<String>(Arrays.asList(adjacentNodes.split("")));
		
		for(String s: tempList){
			tempDict.put(s, distance.get(s));
			sb.append(s + ":dd("+s+")"+"= "+distance.get(s)+"\n");
		}

		return (String) getKeyFromValue(tempDict,minDistance(tempDict));		
	}
	
	/**
	 * this method generates a string consisting of all distances based off the second input file plus the EdgeWeight map for the test data {@link #toStringAlgorithm1(LinkedList, Map) toStringAlgorithm1}
	 * |{@link #getAdjacentNodes(Vertex) getAdjacentNodes} string of adjacent nodes to current node
	 * |{@link #getKeyFromValue(Map, Object) getKeyFromValue} gets a key from a value
	 * @param v vertex from which to find all adjacent node distances 
	 * @param distance the map of all distance for the nodes
	 * @return string of all the distances plus edge weight for adjacent nodes
	 */
	public String toStringAdjacentDistance2(Vertex<String> v, Map<String, Integer> distance){
		String adjacentNodes = this.getAdjacentNodes(v);
		StringBuilder sb = new StringBuilder();
		Map<String, Integer> tempDict = new HashMap<String, Integer>();
		List<String> tempList = new ArrayList<String>(Arrays.asList(adjacentNodes.split("")));
		for(String s: tempList){
			int total = 0;
			total = distance.get(s) + (int) this.getEdge(v, this.getVertex(s)).getElement();
			tempDict.put(s, distance.get(s));
			sb.append(s + " :w("+ v.getElement().toString() + "," + s + ")" +"+dd("+s+")"+"= "+distance.get(s)+"\n");
		}

		return sb.toString();		
		
	}
	
	/**
	 * this method is used for constructing the output for an algorithm 1 type path that uses the distances from the second file for its decision making 
	 * @param shortestPath output path from the algorithm 
	 * @param distance the map of all the nodes and their distances
	 * @return output string of the shortest path and the length of that path in relation to the distances in the second file
	 */ 
	public String toStringAlgorithm1(LinkedList<String> shortestPath, Map<String, Integer> distance){
		StringBuilder sb = new StringBuilder();
		int sum=0;

		for(int i=0; i<shortestPath.size()-1;i++){ //-1 size because i do not need the distance for the last element
			sum= distance.get(shortestPath.get(i)) +sum;
		}
		
		sb.append("Shortest Path: " + shortestPath.toString() +"\n");
		sb.append("Shortest Path Length: " + Integer.valueOf(sum));
		return sb.toString();		
		
	}
	
	/**
	 * this method is used for constructing the output for an algorithm 1 type path that uses the edge weight from the first file for its decision making 
	 *|{@link #getVertex(String) getVertex} gets the vertex for a given string value of that vertex
	 * @param shortestPath output path from the algorithm 
	 * @return output string of the shortest path and the length of that path in relation to the edge weight in the first file
	 */ 
	public String toStringAlgorithm1(LinkedList<String> shortestPath){
		StringBuilder sb = new StringBuilder();
		int sum=0;

			for(int i=0; i<shortestPath.size();i++){
				if(i<shortestPath.size()-1) //-1 because of how i get the next index element for the getEdge() i need two vertex
					sum = (int) this.getEdge(this.getVertex(shortestPath.get(i)), this.getVertex(shortestPath.get(i+1))).getElement()+sum;
			}

		sb.append("Shortest Path: " + shortestPath.toString().toUpperCase() +"\n");
		sb.append("Shortest Path Length: " + Integer.valueOf(sum));
		return sb.toString();		
		
	}
	
	/**
	 * this method is used for constructing the output for an algorithm 2 type path that uses the distances from the second file and the edge weight from the first file for its decision making 
	 * @param shortestPath output path from the algorithm 
	 * @param distance the map of all the nodes and their distances
	 * @return output string of the shortest path and the length of that path in relation to the distances  and edge weight in both files
	 */ 
	public String toStringAlgorithm2(LinkedList<String> shortestPath,Map<String, Integer> distance){
		StringBuilder sb = new StringBuilder();
		int sum=0;

			for(int i=0; i<shortestPath.size();i++){
				if(i<shortestPath.size()-1) //-1 because of how i get the next index element for the getEdge() i need two vertex
					sum = (int) this.getEdge(this.getVertex(shortestPath.get(i)), this.getVertex(shortestPath.get(i+1))).getElement()+ distance.get(shortestPath.get(i))+sum; //add up the distances
			}

		sb.append("Shortest Path: " + shortestPath.toString().toUpperCase() +"\n");
		sb.append("Shortest Path Length: " + Integer.valueOf(sum));
		return sb.toString();		
		
	}
	
	/**
	 * This if the first algorithm that was built for this assignment it works off of adjacency and nothing else. 
	 * Since it operates in this way it can only be used for nodes that have certain degree of separation or less.
	 * |{@link #getAdjacentNodes(Vertex) getAdjacentNodes} string of adjacent nodes to current node
	 * |{@link #getVertex(String) getVertex} gets the vertex for a given string value of that vertex
	 * @param start the first node in the path from which the user want to traverse
	 * @param end the last node in the path to which the user is trying to get
	 * @return the path of all the node from start to end
	 * <br>
	 *>>>>>>>>>>>>>>>>PSEUDOCODE<<<<<<<<<<<<<<<<<<
	 * <pre>
	 * {@code
	 * add start node to path;
	 * for(adjacent nodes of starting node);
	 * 		if(start node is adjacent node to end):
	 * 			add end node to path;
	 * 			return path;
	 * 		else if(start and end share an adjacent node);
	 * 			add that node to path;
	 * 			add end node to path;
	 * 			return path;
	 * 		for(every adjacent node of every adjacent node of start node);
	 * 			if(those adjacent nodes share an adjacent node with the end node);
	 * 				add the first adjacent node to path;
	 * 				add adjacent node shared with end node to path;
	 * 	add end node to path;
	 * return path;
	 * }
	 * </pre>
	 * *>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	 */
	public LinkedList<String> Algorithm1_Adjacency(String start, String end){
		List<String> startAdjList = new ArrayList<String>(Arrays.asList(this.getAdjacentNodes(this.getVertex(start)).split("")));//list of all adjacent nodes to start node
		List<String> endAdjList = new ArrayList<String>(Arrays.asList(this.getAdjacentNodes(this.getVertex(end)).split("")));//list of all adjacent nodes to end node
        LinkedList<String> path = new LinkedList<String>();
        path.add(start);

		for (String s: startAdjList){
			List<String> test = new ArrayList<String>(Arrays.asList(this.getAdjacentNodes(this.getVertex(s)).split("")));
			if (endAdjList.contains(start)) {//if start and end are neighbors end finish
        		path.addLast(end);
        		return path;
        	}	
			else if (endAdjList.contains(s)) {
				path.add(s);
				path.add(end);
		        return path;
			}
			for (String w: test){ //if adjacent of adjacent share a node with the adjacent to the end node, this represents how far this algorithm can go, since it is not recursive
				if (endAdjList.contains(w)) {
					path.add(s);
					path.add(w);
				}
			}
		}

        path.add(end);
        return path;
	}	

	/**
	 * This algorithm finds the path from the starting to the ending node using the distances defined in the second file. It uses recursion to traverse across that the tree and selects the smallest distance between two nodes to make its next step
	 * built into this algorithm is the ability to not move backwards which causes stackOverFlow Errors and to avoid nodes with only one edge.
	 * |{@link #getVertex(String) getVertex} gets the vertex for a given string value of that vertex
	 * |{@link #getKeyFromValue(Map, Object) getKeyFromValue} gets a key from a value
	 * |{@link #minDistance(Map) minDistance} gets the min distance based off map of distances
	 * @param start the first node in the path from which the user want to traverse
	 * @param end the last node in the path to which the user is trying to get
	 * @param distance the map of all distance for the nodes
	 * @param path the list of nodes from start to end
	 * @return the path of all the node from start to end
	 
	 * 
	 * <br>
	 *>>>>>>>>>>>>>>>>PSEUDOCODE<<<<<<<<<<<<<<<<<<
	 * <pre>
	 * {@code
	 * add start node to path;
	 * for(adjacent nodes of starting node);
	 * 		add all adjacent nodes to a adjacentNodes list;
	 * 		if(start node is adjacent node to end node):
	 * 			add end node to path;
	 * 			return path;
	 * 		else if(start and end share an adjacent node);
	 * 			add that nodes to sharedNodes list;
	 * 			also add that node to path
	 * 		if (the number of edges for a node is 1);
	 * 			remove that node from the adjacentNodes list;
	 * if(sharedNodes list is NOT empty);
	 * 		find the node with the min value in sharedList and add it to path;	
	 * if(sharedNodes list is empty and the minimum value in adjacentNodes list is the same as the previous node);
	 * 		remove that node from adjacentNodes list;
	 * 		recursively run the method with the start node being the min value in adjacentNodes list and save the path;
	 * 	if(sharedNodes list is empty and the minimum value in adjacentNodes list is NOT the same as the previous node);
	 *  		recursively run the method with the start node being the min value in adjacentNodes list and save the path;
	 * 	add end node to path;
	 * return path;
	 * }
	 * </pre>
	 * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	 * 
	 */
	public LinkedList<String> Algorithm1_Distance(String start, String end, Map<String, Integer> distance, LinkedList<String> path){
		List<String> startAdjList = new ArrayList<String>(Arrays.asList(this.getAdjacentNodes(this.getVertex(start)).split("")));//list of all adjacent nodes to start node
		List<String> endAdjList = new ArrayList<String>(Arrays.asList(this.getAdjacentNodes(this.getVertex(end)).split("")));//list of all adjacent nodes to end node
        Map<String, Integer>  adjacent= new HashMap<String, Integer>(); //map of all adjacent nodes to start node
        Map<String, Integer> sharedAdj = new HashMap<String, Integer>(); //map of all node start or adjacents to start share with end
        String prev=null;

        path.add(start);

		for (String s: startAdjList){
			adjacent.put(s, distance.get(s));
			if (endAdjList.contains(start)) { //if start and end are neighbors end finish
        		path.addLast(end);
        		return path;
        	}	
			else if (endAdjList.contains(s)) {
				sharedAdj.put(s, distance.get(s)); //add this so that we can find min later
				//path.add(s);
			}
			if(this.inDegree(this.getVertex(s))==1){ //if node only has 1 edge remove it from traverse list
				adjacent.remove(s);			
			}
			
		}
		if(!sharedAdj.isEmpty()){
			path.add((String) getKeyFromValue(distance,minDistance(sharedAdj)));
		}
		
		if(path.size()>2){
			prev = path.get(path.size()-2);
		}
		if(path.size()==2){
			prev=path.getFirst();
		}
		
		if(sharedAdj.isEmpty() && getKeyFromValue(distance,minDistance(adjacent)).equals(prev)){
			adjacent.remove(prev); //keeps traverse from going backwards
			try{
				Algorithm1_Distance((String) getKeyFromValue(distance,minDistance(adjacent)),end,distance,path); //recursively calls the method again with new start
			}catch (NoSuchElementException e) { //exception handling for when the node is unreachable by this method
				System.err.println("Unreachable Node!");
			}
			return path;
		}
		else if(sharedAdj.isEmpty() && !getKeyFromValue(distance,minDistance(adjacent)).equals(prev)){
			try{
				Algorithm1_Distance((String) getKeyFromValue(distance,minDistance(adjacent)),end,distance,path); //recursively calls the method again with new start
			}catch (NoSuchElementException e) { //exception handling for when the node is unreachable by this method
				System.err.println("Unreachable Node!");
			}
			return path;
		}

		path.add(end);
        return path;
	}	
	
	/** 
	 * This algorithm finds the path from the starting to the ending node using the edge weight defined in the first file. It uses recursion to traverse across that the tree and selects the smallest weight between two nodes to make its next step
	 * built into this algorithm is the ability to not move backwards which causes stackOverFlow Errors and to avoid nodes with only one edge.
	 * |{@link #getVertex(String) getVertex} gets the vertex for a given string value of that vertex
	 * |{@link #getKeyFromValue(Map, Object) getKeyFromValue} gets a key from a value
	 * |{@link #minDistance(Map) minDistance} gets the min distance based off map of distances
	 * @param start the first node in the path from which the user want to traverse
	 * @param end the last node in the path to which the user is trying to get
	 * @param distance the map of all distance for the nodes
	 * @param path the list of nodes from start to end
	 * @return the path of all the node from start to end
	 
	 * 
	 * 
	 *  <br>
	 *>>>>>>>>>>>>>>>>PSEUDOCODE<<<<<<<<<<<<<<<<<<
	 * <pre>
	 * {@code
	 * add start node to path;
	 * for(adjacent nodes of starting node);
	 * 		add all adjacent nodes to a adjacentNodes list;
	 * 		if(start node is adjacent node to end node):
	 * 			add end node to path;
	 * 			return path;
	 * 		else if(start and end share an adjacent node);
	 * 			add that nodes to sharedNodes list;
	 * 			also add that node to path
	 * 		if (the number of edges for a node is 1);
	 * 			remove that node from the adjacentNodes list;
	 * if(sharedNodes list is NOT empty);
	 * 		find the node with the min value in sharedList and add it to path;	
	 * if(sharedNodes list is empty and the minimum value in adjacentNodes list is the same as the previous node);
	 * 		remove that node from adjacentNodes list;
	 * 		recursively run the method with the start node being the min edge weight of adjacentNodes list and save the path;
	 * 	if(sharedNodes list is empty and the minimum value in adjacentNodes list is NOT the same as the previous node);
	 *  		recursively run the method with the start node being the min edge weight in adjacentNodes list and save the path;
	 * 	add end node to path;
	 * return path;
	 * }
	 * </pre>
	 * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	 * 
	 */
	public LinkedList<String> Algorithm1_EdgeWeight(String start, String end,Map<String, Integer> distance, LinkedList<String> path ){
    	List<String> startAdjList = new ArrayList<String>(Arrays.asList(this.getAdjacentNodes(this.getVertex(start)).split("")));//list of all adjacent nodes to start node
        Map<String, Integer>  adjacent= new HashMap<String, Integer>(); //map of all adjacent nodes to start node
        Map<String, Integer> sharedAdj = new HashMap<String, Integer>(); //map of all node start or adjacents to start share with end
        String prev=null;
        path.add(start);

		for (String s: startAdjList){
			adjacent.put(s, (Integer) this.getEdge(this.getVertex(start), this.getVertex(s)).getElement());
			if (end.contains(start)) { //if start and end are neighbors end finish
        		path.addLast(end);
        		return path;
        	}
			else if (end.contains(s)) {
				sharedAdj.put(s, (Integer) this.getEdge(this.getVertex(start), this.getVertex(s)).getElement()); //add this so that we can find min later
			}
			
			if(this.inDegree(this.getVertex(s))==1){ //if node only has 1 edge remove it from traverse list
				adjacent.remove(s);			
			}
			
		}
		if(!sharedAdj.isEmpty()){
			//path.add((String) getKeyFromValue(distance,minDistance(sharedAdj)));
		}
		
		if(path.size()>2){
			prev = path.get(path.size()-2);
		}
		if(path.size()==2){
			prev=path.getFirst();
		}
		
		if(sharedAdj.isEmpty() && getKeyFromValue(adjacent,minDistance(adjacent)).equals(prev)){
			adjacent.remove(prev); //keeps traverse from going backwards
			try{
				Algorithm1_EdgeWeight((String) getKeyFromValue(adjacent,minDistance(adjacent)),end,adjacent,path); //recursively calls the method again with new start
			}catch (NoSuchElementException e) { //exception handling for when the node is unreachable by this method
				System.err.println("Unreachable Node!");
			}
			return path;
		}
		else if(sharedAdj.isEmpty() && !getKeyFromValue(adjacent,minDistance(adjacent)).equals(prev)){
			try{
				Algorithm1_EdgeWeight((String) getKeyFromValue(adjacent,minDistance(adjacent)),end,adjacent,path); //recursively calls the method again with new start
			}catch (NoSuchElementException e) { //exception handling for when the node is unreachable by this method
				System.err.println("Unreachable Node!");
			}	        return path;
		}
		path.add(end);
		return path;
	}	

	/**
	 * This algorithm finds the path from the starting to the ending node using the edge weight and distance. It uses recursion to traverse across that the tree and selects the smallest weight + distance between two nodes to make its next step
	 * built into this algorithm is the ability to not move backwards which causes stackOverFlow Errors and to avoid nodes with only one edge.
	 * |{@link #getVertex(String) getVertex} gets the vertex for a given string value of that vertex
	 * |{@link #getKeyFromValue(Map, Object) getKeyFromValue} gets a key from a value
	 * |{@link #minDistance(Map) minDistance} gets the min distance based off map of distances
	 * @param start the first node in the path from which the user want to traverse
	 * @param end the last node in the path to which the user is trying to get
	 * @param distance the map of all distance for the nodes
	 * @param path the list of nodes from start to end
	 * @return the path of all the node from start to end
	 * 
	 * 
	 *  <br>
	 *>>>>>>>>>>>>>>>>PSEUDOCODE<<<<<<<<<<<<<<<<<<
	 * <pre>
	 * {@code
	 * add start node to path;
	 * for(adjacent nodes of starting node);
	 * 		add all adjacent nodes to a adjacentNodes list;
	 * 		if(start node is adjacent node to end node):
	 * 			add end node to path;
	 * 			return path;
	 * 		else if(start and end share an adjacent node);
	 * 			add that nodes to sharedNodes list;
	 * 			also add that node to path
	 * 		if (the number of edges for a node is 1);
	 * 			remove that node from the adjacentNodes list;
	 * if(sharedNodes list is NOT empty);
	 * 		find the node with the min value in sharedList and add it to path;	
	 * if(sharedNodes list is empty and the minimum value in adjacentNodes list is the same as the previous node);
	 * 		remove that node from adjacentNodes list;
	 * 		recursively run the method with the start node being the min edge weight +distance  of adjacentNodes list and save the path;
	 * 	if(sharedNodes list is empty and the minimum value in adjacentNodes list is NOT the same as the previous node);
	 *  		recursively run the method with the start node being the min edge weight + distance in adjacentNodes list and save the path;
	 * 	add end node to path;
	 * return path;
	 * }
	 * </pre>
	 * >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>
	 * 
	 */
	public LinkedList<String> Algorithm2_EdgeWeight_Distance(String start, String end,Map<String, Integer> distance, LinkedList<String> path ){  
    	List<String> startAdjList = new ArrayList<String>(Arrays.asList(this.getAdjacentNodes(this.getVertex(start)).split("")));//list of all adjacent nodes to start node
		List<String> endAdjList = new ArrayList<String>(Arrays.asList(this.getAdjacentNodes(this.getVertex(end)).split("")));//list of all adjacent nodes to end node
        Map<String, Integer>  adjacent= new HashMap<String, Integer>(); //map of all adjacent nodes to start node
        Map<String, Integer> sharedAdj = new HashMap<String, Integer>(); //map of all node start or adjacents to start share with end
        String prev=null;
        path.add(start);

		for (String s: startAdjList){
			adjacent.put(s, ((Integer) this.getEdge(this.getVertex(start), this.getVertex(s)).getElement())+distance.get(s));
			if (endAdjList.contains(start)) { //if start and end are neighbors end finish
				path.addLast(end); //add end to path
        		return path;
        	}
			else if (endAdjList.contains(s)) {
				sharedAdj.put(s, ((Integer) this.getEdge(this.getVertex(start), this.getVertex(s)).getElement())+distance.get(s)); //add this so that we can find min later
			}
			
			if(this.inDegree(this.getVertex(s))==1){ //if node only has 1 edge remove it from traverse list
				adjacent.remove(s);

			}
		}
		
		if(!sharedAdj.isEmpty()){
			path.add((String) getKeyFromValue(sharedAdj,minDistance(sharedAdj)));
		}
		
		if(path.size()>2){
			prev = path.get(path.size()-2);
		}
		if(path.size()==2){
			prev=path.getFirst();
		}
		
		
		if(sharedAdj.isEmpty() && getKeyFromValue(adjacent,minDistance(adjacent)).equals(prev)){
			adjacent.remove(prev); //keeps traverse from going backwards
			try{
				Algorithm2_EdgeWeight_Distance((String) getKeyFromValue(adjacent,minDistance(adjacent)),end,distance,path); //recursively calls the method again with new start
			}catch (NoSuchElementException e) { //exception handling for when the node is unreachable by this method
				System.err.println("Unreachable Node!");
			}
			return path;
		}
		else if(sharedAdj.isEmpty() && !getKeyFromValue(adjacent,minDistance(adjacent)).equals(prev)){
			try{
				Algorithm2_EdgeWeight_Distance((String) getKeyFromValue(adjacent,minDistance(adjacent)),end,distance,path); //recursively calls the method again with new start
			}catch (NoSuchElementException e) { //exception handling for when the node is unreachable by this method
				System.err.println("Unreachable Node!");
			}	        
			return path;
		}
		path.add(end);
        return path;
	}	

	/**
	 * This method is the sub menu of menu option 1
	 * @param mainMap the main node tree that holds all the nodes
	 * @param distance the mpa generated from the second input file
	 */
	public static void menuOption1(project mainMap,Map<String, Integer> distance){

		boolean run = true;
		do{
			LinkedList<String> path =new LinkedList<String>();

		System.out.println("\nAlgorithm1>Choose an option: \n" 
				+  "1. Print All Adjacent Shortests Paths \n" //multiple options because instructions on assignment were so unclear
				+  "2. Algorithm 1 : Adjacency (Traverses by way of adjacent nodes) \n" //multiple options because instructions on assignment were so unclear
				+  "3. Algorithm 1 : Distance (Traverses by way of smallest distances from input file 2) \n" //multiple options because instructions on assignment were so unclear
				+  "4. Algorithm 1 : EdgeWeight (Traverses by way of smallest edge weights from in file 1) \n" //multiple options because instructions on assignment were so unclear
				+  "5. Exit");	
		Scanner sc = new Scanner(System.in);
		int selection =  sc.nextInt();

		switch (selection) {
		case 1: System.out.println(mainMap.toStringAlgorithm1(distance));	
		break;

		case 2: System.out.println("Start: "); 
		Scanner start = new Scanner(System.in); 
		System.out.println("End: "); 
		Scanner end = new Scanner(System.in);
		if(!start.hasNext("[abcdefghijklmnopqrstzABCDEFGHIJKLMNOPQRSTZ]") || !end.hasNext("[abcdefghijklmnopqrstzABCDEFGHIJKLMNOPQRSTZ]")){ //there was not full alphabet in the input file 
			System.out.println("Invalid Input!");
			break;
		}
		else{
			System.out.println(mainMap.toStringAlgorithm1(mainMap.Algorithm1_Adjacency(start.next().toUpperCase(), end.next().toUpperCase())));
			break;
		}
		case 3: System.out.println("Start: "); 
		Scanner start1 = new Scanner(System.in);
		System.out.println("End: "); 
		Scanner end1 = new Scanner(System.in);
		if(!start1.hasNext("[abcdefghijklmnopqrstzABCDEFGHIJKLMNOPQRSTZ]") || !end1.hasNext("[abcdefghijklmnopqrstzABCDEFGHIJKLMNOPQRSTZ]")){ //there was not full alphabet in the input file 
			System.out.println("Invalid Input!");
			break;
		}
		else{
			System.out.println(mainMap.toStringAlgorithm1(mainMap.Algorithm1_Distance(start1.next().toUpperCase(), end1.next().toUpperCase(),distance,path)));
			break;
		}

		case 4: System.out.println("Start: "); 
		Scanner start2 = new Scanner(System.in); 
		System.out.println("End: "); 
		Scanner end2 = new Scanner(System.in);
		if(!start2.hasNext("[abcdefghijklmnopqrstzABCDEFGHIJKLMNOPQRSTZ]") || !end2.hasNext("[abcdefghijklmnopqrstzABCDEFGHIJKLMNOPQRSTZ]")){ //there was not full alphabet in the input file 
			System.out.println("Invalid Input!");
			break;
		}
		else{
			System.out.println(mainMap.toStringAlgorithm1(mainMap.Algorithm1_EdgeWeight(start2.next().toUpperCase(), end2.next().toUpperCase(),distance,path)));
			break;
		}
		case 5: run=false;
				running =true;  
				break;

		default: run =true;
		break;
		}
		}while(run);
	}
	
	/**
	 * This method is the sub menu of menu option 2
	 * @param mainMap the main node tree that holds all the nodes
	 * @param distance the mpa generated from the second input file
	 */
	public static void menuOption2(project mainMap,Map<String, Integer> distance){
		boolean run = true;
		do{
		LinkedList<String> path =new LinkedList<String>();

		System.out.println("\nAlgorithm2>Choose an option: \n"
				+  "1. Print All Adjacent Shortests Paths \n"
				+  "2. Algorithm 2 : EdgeWeight_Distance (Traverses by way of EdgeWieght + Distance ) \n"
				+  "3. Exit");	
		Scanner sc = new Scanner(System.in);
		int selection =  sc.nextInt();

		switch (selection) {
		case 1: System.out.println(mainMap.toStringAlgorithm2(distance));	
		break;

		case 2: System.out.println("Start: "); 
				Scanner start = new Scanner(System.in); //listen for user input for id
				System.out.println("End: "); 
				Scanner end = new Scanner(System.in);
				if(!start.hasNext("[abcdefghijklmnopqrstzABCDEFGHIJKLMNOPQRSTZ]") || !end.hasNext("[abcdefghijklmnopqrstzABCDEFGHIJKLMNOPQRSTZ]")){ //there was not full alphabet in the input file 
					System.out.println("Invalid Input!");
					break;
				}
				else{
					System.out.println(mainMap.toStringAlgorithm1(mainMap.Algorithm2_EdgeWeight_Distance(start.next().toUpperCase(), end.next().toUpperCase(),distance,path),distance));
					break;
				}
				
		case 3: run=false;
				running =true;  
				break;

		default: run =true;
		break;
		}
		}while(run);
	}

	
	/**
	 * this the main menu where the user specifies what action they want to take upon start up
	 * @param mainMap the main node tree that holds all the nodes
	 * @param distance the mpa generated from the second input file
	 */
	@SuppressWarnings("resource")
	public static void mainMenu(project mainMap,Map<String, Integer> distance){		
		do{
			System.out.println("\nChoose an option: \n"
					+  "1. Algorithm 1 \n"
					+  "2. Algorithm 2 \n"
					+  "3. Print Map \n"
					+  "4. Exit");	
			Scanner sc = new Scanner(System.in);
			int selection =  sc.nextInt();

			switch (selection) {
			case 1: menuOption1(mainMap,distance);
			break;

			case 2: menuOption2(mainMap,distance);
			break;

			case 3: System.out.println(mainMap.toString());
			break;

			case 4: System.out.println("\n" + "Goodbye!");  
			System.exit(0);//exit application
			break;

			default: running =true;
			break;
			}
		}while(running);
	}

}















