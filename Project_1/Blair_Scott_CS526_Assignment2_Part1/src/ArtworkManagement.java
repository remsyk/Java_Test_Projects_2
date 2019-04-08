
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

import net.datastructures.SinglyLinkedList;
import net.datastructures.SinglyLinkedList.Node;

public class ArtworkManagement {
	
static Artwork artwork = new Artwork(); //an instances of the artwork class
static int size; //control the for loop size for number it lines in file
static Artwork[] artList;
static boolean running; //resets the application
static boolean secondSelection =false; //
static String fileName = "artwork_info.txt"; //file program is reading from
static boolean returnToMainMenu = false; //return to main menu
static SinglyLinkedList<Painting> sList;
static Painting[] paintingList;


	public static void main(String[] args) throws IOException {
				
		mainMenu();
		
	}
	

	public static void arrayToList(){
		sList = new SinglyLinkedList<Painting>();
		for (int i = 0; i < size; i++) {	
			sList.addFirst((Painting) artList[i]);
		}
		
		System.out.println(sList.toString());
	}
	
	
	public static void mainMenu() throws IOException{
		while(true){
			running = true;

			do{
				int selection = mainMenuOptions(); //calls main menu and waits for user selection
				
		        switch (selection) {
		            case 1: assign1Menu();
		            		returnToMainMenu = false;
		            		break;
		            
		            case 2: assign2Menu();
		            		returnToMainMenu = false;
		                    break;
		      
		            case 3: System.out.println("\n" + "Goodbye!");  
		            		System.exit(0);//exit application
		                    break;
		            
		            default: running = false;
		                     break;
		        }
			}while(running);
		}
	}
	
	//This is the menu for assignment 1 when user
	public static void assign1Menu() throws IOException{
			
		fileToArray_art("artwork_info.txt"); //called so that int size is set and array is built from file

		
		while(returnToMainMenu == false){
			
			do{
				int selection = assign1MenuOptions(); //calls main menu and waits for user selection
				
		        switch (selection) {
		            case 1: System.out.println("Please Enter Id: "); 
		            		Scanner sc2 = new Scanner(System.in); //listen for user input for id
		            		getById_art(sc2.next(),size);
		                    break;
		            
		            case 2: 
		            		System.out.println("Please Enter Id: "); 
            				Scanner sc4 = new Scanner(System.in); //listen for user input for id 
		            		setupForSetLocation (sc4.next(), size); //call function to start changing location
		                    break;
		           
		            case 3: returnToMainMenu = true;
		                    break;
		            
		            default: returnToMainMenu = true;
		                     break;
		        }
			}while(returnToMainMenu == false);
		}
	}
	
	//This is the menu for assignment 2 when user
	public static void assign2Menu() throws IOException{
		
		fileToArray_painting("painting_info.txt"); //called so that int size is set and array is built from file

				
		while(returnToMainMenu == false){
			do{
				int selection = assign2MenuOptions(); //calls main menu and waits for user selection
				
		        switch (selection) {
		        
		            case 1: System.out.println("Add a Painting \n"); 
		            		System.out.println("Please Enter Id \n"); 
		            		Scanner sc1 = new Scanner(System.in); //listen for user input for id
		            		getById_painting(sc1.next(),sList.size());
		                    break;
		            
		            case 2: System.out.println("Remove a Painting \n"); 
            				//Scanner sc2 = new Scanner(System.in); //listen for user input for id 
		                    break;
		                    
		            case 3: System.out.println("Update a Painting Location \n"); 
        					//Scanner sc3 = new Scanner(System.in); //listen for user input for id 
        					break;
        					
		            case 4: System.out.println("Display all Paintings \n"); 
        					//Scanner sc4 = new Scanner(System.in); //listen for user input for id 	            	
        					break;
		           	           
		            case 5: returnToMainMenu = true;
		                    break;
		            
		            default: returnToMainMenu = true;
		                     break;
		        }
			}while(returnToMainMenu == false);
		}
	}
	
	
	//Converts the file into a object list of art objects
	//precondition: the file is organized how it should be
	//postcondition: the file item are converted to an array
	@SuppressWarnings("rawtypes")
	public static Artwork[] fileToArray_art(String fileName) throws IOException{
		Path path = Paths.get(fileName); //specifies path to file
		List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);//read the file line by line and loads it into a list
		lines.remove(0);//removes first item in array because i dont need to know that number of items
		size = lines.size(); //sets size equal to the number of lines in file
		artList = new Artwork[size]; //specifies that size of the artList which is the list of art objects

		
		int i =0;
		for(String list:lines){
			List<String> bufferList = new ArrayList<String>(Arrays.asList(list.split(","))); //converting  each line of the file as its own array
			artList[i] = new Artwork(bufferList.get(0),bufferList.get(1),Integer.valueOf(bufferList.get(2).substring(1)),bufferList.get(3)); //adds each line of the buffer array as an object into the art object list
			i++;
		}
					
		return artList;
	}
	
	@SuppressWarnings("rawtypes")
	public static Painting[] fileToArray_painting(String fileName) throws IOException{
		Path path = Paths.get(fileName); //specifies path to file
		List<String> lines = Files.readAllLines(path, StandardCharsets.UTF_8);//read the file line by line and loads it into a list
		lines.remove(0);//removes first item in array because i dont need to know that number of items
		size = lines.size(); //sets size equal to the number of lines in file
		paintingList = new Painting[size]; //specifies that size of the artList which is the list of art objects

		
		int i =0;
		for(String list:lines){
			List<String> bufferList = new ArrayList<String>(Arrays.asList(list.split(","))); //converting  each line of the file as its own array
			paintingList[i] = new Painting(bufferList.get(0),bufferList.get(1),Integer.valueOf(bufferList.get(2).substring(1)),bufferList.get(3), bufferList.get(4),bufferList.get(5)); //adds each line of the buffer array as an object into the art object list
			i++;
		}
					
		return paintingList;
	}

	//prints to screen the art requested by id
	//precondition: the fileToArray method is ran so that the file item size can be known
	//postcondition: the request art object is printed to the screen
	public static void getById_art(String id, int size) throws IOException{
		
		List<String> bufferList = new ArrayList<String>();
		int index =0;
		for (int i = 0; i < size; i++) {	
			bufferList.add(artList[i].getId());// load a list of ID numbers from the fitetoarray function
			index = bufferList.indexOf(id); //finds index of requested id from id list
		}
		if(index == -1){
			
			try {
				returnToMainMenu=true; //resets the application
				throw new MyException("Id Not Found!" + "\n"); //throws my custom exception
			} catch (MyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}		
		else{
			System.out.println( artList[index].toString()); //prints to the screen the requested art object from id
			returnToMainMenu=true; //resets the application
		}
	}
	
	//prints to screen the art requested by id
		//precondition: the fileToArray method is ran so that the file item size can be known
		//postcondition: the request art object is printed to the screen
	public static <E> void getById_painting(String id, int size) throws IOException{
			
		SinglyLinkedList<Painting> bufferList = sList;
		Node<E> head;
		Node<E> walk = head;
		while (walk != null) {
			walk = walk.getNext();
			sList.last();
		}
	}
				
			
		
	
	
	//this will handle the second choice for the application
	//precondition: the fileToArray method is ran so that the file item size can be known
	//postcondition: the location of requested object is changed and displayed to screen
	public static void setupForSetLocation(String id, int size) throws IOException{
		
		List<String> bufferList = new ArrayList<String>();
		int index =0;
		for (int i = 0; i < size; i++) {	
			bufferList.add(artList[i].getId());// load a list of ID numbers from the fitetoarray function		
			index = bufferList.indexOf(id);//finds index of requested id from id list
		}
		if(index == -1){
			
			try {
				running=false;// restarts application of id is not found
				throw new MyException("Id Not Found!" + "\n"); //throws exception if id input is not found
			} catch (MyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		else{
			System.out.println(artList[index].toString()); //prints to the screen the requested art object from id
			
			System.out.println("Enter Location: "); //requests user to enter in location of art item
    		Scanner sc3 = new Scanner(System.in); //listens for user input
    		
			artList[index].setLocation(sc3.next()); //changes location of art object by user request
			System.out.println(artList[index].toString()); //prints to screen the updated list
			running=false;

		}	
	}
	

	//precondition: none
	//postcondition: the user will have options to choose from for the application
	//input: user selection
	//output: menu selected
	static int assign1MenuOptions(){
	
		System.out.println("Choose an option: \n"
						+  "1. Artwork Information \n"
						+  "2. Update Location \n"
						+  "3. Main Menu");	
		
		Scanner sc = new Scanner(System.in);
		
		return sc.nextInt();
		
		}
	
	//precondition: none
	//postcondition: the user will have options to choose from for the application
	//input: user selection
	//output: menu selected
	static int mainMenuOptions(){
		
		System.out.println("Choose an option: \n"
						+  "1. Assignment 1 \n"
						+  "2. Assignment 2 \n"
						+  "3. Exit");	
		
		Scanner sc = new Scanner(System.in);
		
		return sc.nextInt();
		
		}
	
	//precondition: none
	//postcondition: the user will have options to choose from for the application
	//input: user selection
	//output: menu selected
	static int assign2MenuOptions(){
		
		System.out.println("Choose an option: \n"
						+  "1. Add a Painting \n"
						+  "2. Remove a Painting \n"
						+  "3. Update a Painting Location \n"
						+  "4. Display all Paintings \n"
						+  "5. Main Menu");	
		
		Scanner sc = new Scanner(System.in);
		
		return sc.nextInt();
		
		}

	
}

