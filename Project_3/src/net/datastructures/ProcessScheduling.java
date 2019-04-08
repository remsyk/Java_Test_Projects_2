package net.datastructures;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Stack;

import net.datastructures.Entry;
import net.datastructures.AbstractPriorityQueue.PQEntry;
import net.datastructures.HeapAdaptablePriorityQueue.AdaptablePQEntry;

public abstract class ProcessScheduling<K,V> extends HeapPriorityQueue<K,V>
implements AdaptablePriorityQueue<K,V> {
			
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws IOException  {
		
		ArrayList<Process> D = (ArrayList<Process>)fileToArray("input_txt.txt").clone(); //Read all processes from the input file and store them in an appropriate data structure, D
		ArrayList<Process> D2 = (ArrayList<Process>)D.clone(); //Read all processes from the input file and store them in an appropriate data structure, D

		int dSize = D.size();
		int currentTime = 0; //currentTime = 0
		int maxWaitTime = 10;
	    //Entry<Integer,Integer> lastMinValue = null;
		boolean running = false; //running = false
		HeapAdaptablePriorityQueue<Integer,Process> Q = new HeapAdaptablePriorityQueue<Integer,Process>(); //create an empty priority queue Q
		int totalWaitTime=0;
		double averageWaitTime= 0;
		int finishTime =0;
		Process p = new Process();
		Process p2 = new Process();

		

		printAll(D);

		while (!D.isEmpty()){//While D is not empty // while loop runs once for every time unit until D is empty
			
			//System.out.println(Integer.valueOf(currentTime));
			
			p = D.get(getMinArrival(D)); //Get (don’t remove) a process p from D that has the earliest arrival time	

			
			if(p.getArrivalTime()<=currentTime){//If the arrival time of p <= currentTime
				Q.insert(p.getPr(),p); //Remove p from D and insert it into Q
				D.remove(p); //Remove p from D and insert it into Q
			}
			
			waitTimeSetQ(Q,currentTime); //sets the wait time variable for the Q processes
			waitTimeSet(D2,currentTime);  //sets the wait times for the D2 arraylist for the purpose of managing the total wait time


			if(!Q.isEmpty() && running==false){//If Q is not empty and the flag running is false
				p2=Q.min().getValue(); //this is simply set for the purposes of printing out when a processes if finished later on in the code
				finishTime = currentTime + Q.min().getValue().getDuration();
				Q.removeMin().getValue().printRemoveNotice(currentTime);//prints removal notice for when a item is removed from Queue; //Remove a process with the smallest priority from Q
				running=true; //Set a flag running to true
			}
		
			currentTime = currentTime + 1; //currentTime = currentTime + 1

			if(currentTime == finishTime ){//If currently running process has finished
				
				System.out.println("Process= " + Integer.valueOf (p2.getId()) + " finished at time " + Integer.valueOf(currentTime));

				running=false; //Set a flag running to false
				
				for(Entry<Integer, Process> e: Q.heap){
					
					if ((currentTime - e.getValue().getArrivalTime()) >=maxWaitTime){
						e.getValue().setPr( e.getValue().getPr() -1); //decrements the priority of any process that is higher than the max
						System.out.println("Priority of process " + Integer.valueOf(e.getValue().getId()) + " decremented");	
					}
				}
			}
		}
		
		System.out.println("D is empty");
		
		// At this time all processes in D have been moved to Q.
		while(!Q.isEmpty()){//If there are any remaining processes in Q 
			if(!Q.isEmpty() && running==false){//If Q is not empty and the flag running is false
				p2=Q.min().getValue(); //this is simply set for the purposes of printing out when a processes if finished later on in the code
				finishTime = currentTime + Q.min().getValue().getDuration();
				Q.removeMin().getValue().printRemoveNotice(currentTime);//prints removal notice for when a item is removed from Queue; //Remove a process with the smallest priority from Q
				
				running=true; //Set a flag running to true
			}
		
			currentTime = currentTime + 1; //currentTime = currentTime + 1

			if(currentTime == finishTime ){//If currently running process has finished
				
				System.out.println("Process= " + Integer.valueOf (p2.getId()) + " finished at time " + Integer.valueOf(currentTime));

				running=false; //Set a flag running to false
				
				for(Entry<Integer, Process> e: Q.heap){
					
					if ((currentTime - e.getValue().getArrivalTime()) >=maxWaitTime){
						e.getValue().setPr( e.getValue().getPr() -1); //decrements the priority of any process that is higher than the max
						System.out.println("Priority of process " + Integer.valueOf(e.getValue().getId()) + " decremented");	
					}
				}
			}
		}	
		
		System.out.println("Process= " + Integer.valueOf (p2.getId()) + " finished at time " + Integer.valueOf(currentTime) +"\n");
		averageWaitTime= totalWaitTime(D2) /dSize;
		System.out.println("Total wait time= "+ Integer.valueOf(totalWaitTime(D2))); //print total wait time
		System.out.println("Average wait time= "+ Double.valueOf(averageWaitTime)); //print average wait time

	}
	
	/**Converts the file into a object list of process objects
	*precondition: the file is organized how it should be
	*postcondition: the file item are converted to an array
	*/
	public static ArrayList<Process> fileToArray(String fileName) throws NumberFormatException, IOException{
		Path path = Paths.get(fileName); //specifies path to file
		List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);//read the file line by line and loads it into a list
		ArrayList<Process> processList = new ArrayList<Process>();

		for(String list:lines){
			List<String> bufferList = new ArrayList<String>(Arrays.asList(list.split(" "))); //converting  each line of the file as its own array
			processList.add( new Process(Integer.valueOf (bufferList.get(0)),Integer.valueOf (bufferList.get(1)), Integer.valueOf (bufferList.get(2)),Integer.valueOf (bufferList.get(3)))); //adds each line of the buffer array as an object into the art object list
		}
		return processList;
	}
	
	/**Prints all the processes inside of D
	*input: Process arrayList
	*/
	public static void printAll(ArrayList<Process> p){
		for (Process i: p){
			System.out.println(i.toString());
		}
	}
	
	/**Finds the process with a specified Id
	*input: id, process arrayList
	*output: process of requested id
	*/
	public static int getById (ArrayList<Process> p, int j){
		List<Integer> bufferList = new ArrayList<Integer>();
		int index =0;
		for(Process i: p){
			bufferList.add(i.getId());// load a list of ID numbers from the fitetoarray function
			index = bufferList.indexOf(j);//finds index of requested id from id list
		}
		return index;
	}

	public static List<Integer> waitTimeArrayCheck (ArrayList<Process> p, int maxTime){
		List<Integer> idList = new ArrayList<Integer>();
		
		for (Process i: p){
			if(i.getRemoved()){
				idList.remove(i);
			}
			 if(i.getWaitTime()>=maxTime && i.getRemoved() ==false){
				idList.add(i.getId());
			}
			
			if(i.getRemoved()){
				idList.remove(i);
			}
		}

		return idList;
	}
	/**
	 * handels calculate wait time for D2 by adding the wwia time to D2 to later be summed up for the total
	 * @param p
	 * @param currentTime
	 */
	public static void waitTimeSet (ArrayList<Process> p, int currentTime){
		for (Process i: p){
			i.setWaitTime(currentTime - i.getArrivalTime());
		}
	}
	
	/**
	 * handles 
	 * @param Q
	 * @param currentTime
	 */
	public static void waitTimeSetQ (HeapAdaptablePriorityQueue<Integer,Process> Q, int currentTime){
		for(Entry<Integer, Process> e: Q.heap){
			e.getValue().setWaitTime(currentTime - e.getValue().getArrivalTime());
		}
	}
	/**
	 * handle counting the total wait time ussing D2
	 * @param p
	 * @return the total wait time 
	 */
	public static int totalWaitTime (ArrayList<Process> p){
		List<Integer> idList = new ArrayList<Integer>();
		int total =0;
		for (Process i: p){
			total = total + i.getWaitTime();
		}
		return total;
	}

	/**
	 * will find the process with the smallest arrival time
	 * @param p
	 * @return index of process with the smallest array list time
	 * input: process object array list
	 */
	public static int getMinArrival(ArrayList<Process> p){
		List<Integer> bufferList = new ArrayList<Integer>();
		int index =0;
		for(Process i: p){
			bufferList.add(i.getArrivalTime());// load a list of ID numbers from the fitetoarray function
			index = bufferList.indexOf(Collections.min(bufferList));//finds index of requested id from id list
		}
		return index;
	}
	
}






















