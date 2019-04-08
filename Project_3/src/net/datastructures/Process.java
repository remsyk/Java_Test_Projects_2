package net.datastructures;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import net.datastructures.Entry;

public class Process{

	int pr; // priority of the process 
	int id; // process id
	int arrivalTime; //the time when the process arrives at the system
	int duration; // execution of the process takes this amount of time 
	int waitTime;
	boolean removed;
	//constructor 
	//Input parameter: pr, id, arrival time, duration
	//Precondition: all positive integers less than 100
	//Postcondition: A new process object is created
	Process(int id,int pr, int duration, int arrivalTime){
		this.pr = pr;
		this.id = id;
		this.arrivalTime = arrivalTime;
		this.duration = duration;
	}
	
	//overridden constructor
	Process(){}
	
    
	/**set process pr
	*Input parameter: pr
	*Output: None
	*/
	public void setPr(int pr){
		this.pr = pr;
	}
	
	/**set process id
	*Input parameter: id
	*Output: None
	*/
	public void setId(int id){
		this.id = id;
	}
	
	/**set process arrival time
	*Input parameter: arrival time
	*Output: None
	*/
	public void setArrivalTime(int arrivalTime){
		this.arrivalTime = arrivalTime;
	}
	
	/**set process duration
	*Input parameter: duration
	*Output: None
	*/
	public void setDuration(int duration){
		this.duration = duration;
	}
	
	/**set process waitTime
	*Input parameter: waitTime
	*Output: None
	*/
	public void setWaitTime(int waitTime){
		this.waitTime = waitTime;
	}
	
	/**get process waitTime
	*Input parameter: none
	*Output: int waitTime
	*/
	int getWaitTime(){
		return this.waitTime;
	}
	
	/**get process pr
	*Input parameter: none
	*Output: int priority
	*/
	int getPr(){
		return this.pr;
	}
	
	/**get process id
	*Input parameter: none
	*Output: None
	*/
	int getId(){
		return this.id;
	}
	
	/**get process arrival time
	*Input parameter: none
	*Output: None
	*/
	int getArrivalTime(){
		return this.arrivalTime;
	}
	
	/**set process duration
	*Input parameter: none
	*Output: None
	*/
	int getDuration(){
		return this.duration;
	}
	
	/**postcondition: take are object elements and return them as single string
	*input: none
	*output: string
	*/
	public String toString(){
		return ("Id= " + Integer.valueOf (getId()) + "," 
				+"Priority= "+ Integer.valueOf (getPr()) + "," 
				+"Duration= " +Integer.valueOf (getDuration()) + "," 
				+"Arrival Time= " +Integer.valueOf (getArrivalTime())+"\n");
	}
	
	/**postcondition: prints the notice for the removal of a process of the Queue 
	*input: wait time for process and previous finish time from previous process
	*output: string
	*@return finishTime from current process
	*/
	public void  printRemoveNotice (int currentTime){
		System.out.println("\nProcess removed from queue is: id= "+ Integer.valueOf (getId()) + ", at time " + Integer.valueOf (currentTime) + ", wait time= "+ (currentTime-getArrivalTime())); 
		
		
		System.out.println("Process id= " + Integer.valueOf (getId()) + "\n" 
						+"\tPriority= "+ Integer.valueOf (getPr()) + "\n" 
						+"\tArrival= " +Integer.valueOf (getArrivalTime())+"\n"
						+"\tDuration= " +Integer.valueOf (getDuration())); 
	}
	
	/**
	 * sets try if process has been removed from Q 
	 * input: boolean removed
	 * @param removed
	 */
	public void setRemoved(boolean removed){
		this.removed =removed;
	}
	
	/**
	 * 
	 * @return if value is removes from Q
	 */
	public boolean getRemoved(){
		return this.removed;
	}

	
	
}



	












