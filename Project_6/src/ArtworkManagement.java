
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.*;


import net.datastructures.SinglyLinkedList;
import net.datastructures.SinglyLinkedList.Node;

public class ArtworkManagement {

	static Artwork artwork = new Artwork(); //an instances of the artwork class
	static int size; //control the for loop size for number it lines in file
	static Artwork[] artList;
	static boolean running; //resets the application
	static boolean secondSelection =false; //
	static boolean returnToMainMenu = false; //return to main menu
	static boolean returnToSubMenu = false; //return to main menu
	static SinglyLinkedList<Painting> sList;
	static Painting[] paintingList;


	public static void main(String[] args) throws IOException {		
		mainMenu();
	}

	/**Takes all of painting objects and adds them to a singly linked list*/
	public static void arrayToList(){
		sList = new SinglyLinkedList<Painting>(); //defines new linked list
		for (int i = 0; i < size; i++) {	
			sList.addFirst((Painting) paintingList[i]); //add painting objects to linked list
		}

		//System.out.println(sList.toString());
	}

/** This is the main menu it will direct you between the menus for assignment 1 and assignment 2 */
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

		arrayToList();

		while(returnToMainMenu == false){

			do{
				int selection = assign2MenuOptions(); //calls main menu and waits for user selection

				switch (selection) {

				case 1: System.out.println("Please Enter Id to add: \n"); 
				Scanner sc1 = new Scanner(System.in); //listen for user input for id
				addPainting(sc1.next());

				break;

				case 2: System.out.println("Please Enter Id to remove: \n"); 
				Scanner sc2 = new Scanner(System.in); //listen for user input for id
				removePainting(sc2.next());
				break;

				case 3: System.out.println("Please Enter Id of item to change location of:  \n"); 
				Scanner sc3 = new Scanner(System.in); //listen for user input for id 
				String id = sc3.next();
				getById_painting(id, true);
				System.out.println("Location to change to: \n");
				Scanner sc4 = new Scanner(System.in); //listens for input for location change
				paintLocation(id, sc4.next());
				break;

				case 4: displayAllPaintings();            	
				break;

				case 5: returnToMainMenu = true;
				break;

				default: returnToMainMenu = true;
				break;
				}
			}while(returnToSubMenu == false);
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
			paintingList[i] = new Painting(bufferList.get(0),bufferList.get(1),Integer.valueOf(bufferList.get(2).substring(1)),bufferList.get(3), bufferList.get(4),bufferList.get(5)); //adds each line of the buffer array as an object into the object object list
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
				returnToSubMenu=true; //resets the application
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

	/**
	 * finds if the id exists for the given id input by the user
	 * @param id
	 * @param isThere
	 * @throws IOException
	 */
	public static <E> void getById_painting(String id , boolean isThere) throws IOException{
		Node<Painting> walk = sList.head;
		boolean test = true;


		if(isThere==false){
			while (walk != null) {
				if(walk.getElement().getId().equals(id)){ // returns true if id exists in linked list
					try {
						returnToSubMenu=true; //returns to the assignment 2 menu
						throw new MyException("Id Already Exists" + "\n"); //throws my custom exception
					} catch (MyException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				walk = walk.getNext();
			}	
			System.out.println("Valid Id \n"); 

		}
		
		if(isThere){
			while (walk != null && test) {
				if(walk.getElement().getId().equals(id)){
					System.out.println("Valid Id \n");
					test=false;				
				}
				else{
				walk = walk.getNext();
				}
			}	
		
		if(walk==null){
			try {
				returnToSubMenu=true; //returns to the assignment 2 menu
				throw new MyException("Id Doesn't Exists" + "\n"); //throws my custom exception
			} catch (MyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		}
	}

	/**
	 * handles adding a new painting to the linked list
	 * 
	 * @param id
	 * @throws IOException
	 */
	@SuppressWarnings({ "unused", "resource" })
	public static <E> void addPainting(String id) throws IOException{

		getById_painting(id,false); //find if id is valid

		System.out.println("Please Enter Painting: \n"
				+ "(Example: Artist, Year, Location, Paint Type, Material)"); 
		Scanner userInput = new Scanner(System.in); //listen for user input for id

		String bufferString = userInput.nextLine();
		List<String> bufferArray = Arrays.asList(bufferString.split("\\s*,\\s*"));


		Painting userPaint = new Painting(id, bufferArray.get(0), Integer.valueOf(bufferArray.get(1)), bufferArray.get(2), bufferArray.get(3), bufferArray.get(4));

		sList.addFirst(userPaint);
		System.out.println("Item Added");
	}

	public static void displayAllPaintings(){

		System.out.println(sList.toString());

	}
/**
 * attempts to remove a node form the linked list but doen't work properly
 * @param id
 * @throws IOException
 */
	public static void removePainting(String id) throws IOException{

		//getById_painting(id,true);
		Node<Painting> walk = sList.head;

		while (walk != null) {
			
			if(walk.getElement().getId().equals(nodeBefore(id))){ //returns true when it finds the node before the intended node to be deleted
				walk.setNext(walk.getNext()); //attempts to redirect the node before the node to be deleted to the node after then intended node to be deleted

			}
			else if(walk.getElement().getId().equals(id)){ //returns true if it find node requested by id

				if(walk ==sList.head){ //if that node is the head then it calls remove first
					sList.removeFirst();
				}
				else if(walk ==sList.tail){ //if that node is last then it set the node to null
					sList.tail = null;
				}
			}

			walk = walk.getNext();
		}
	}	
	
	/**
	 * attempts to remove a node form the linked list but doen't work properly
	 */
	
	public static void removePainting2(String id) throws IOException{

		getById_painting(id,true);
		Node<Painting> walk = sList.head;
		boolean test =true;
		int times=0;
		
		while (walk != null && test) {
			if(walk.getElement().getId().equals(id)){//returns true if it find node requested by id
				sList.head=walk; //sets current node to head
				sList.removeFirst(); //calls remove first to remove node, and while it work, it deletes half the list if node is in the middle because of how the pointers work
				if(walk.getElement().getId().equals(test(id).get(0))){//returns true when it finds the node before the intended node to be deleted
					sList.addLast(walk.getElement());
				}

				test=false;
			}
			else{
				walk = walk.getNext();
				times+=1;
			}
		}
	}	
	/**
	 * the idea behind this method is to find all the nodes before the node intended to be deleted and load them into an array 
	 * @param id
	 * @return
	 */
	@SuppressWarnings("null")
	public static List<String> test(String id){

		Node<Painting> walk = sList.head;
		List<String> bufferArray = new ArrayList<>();
		
		while (walk != null) {
			bufferArray.add(walk.getElement().getId()); //loads all of the ids from the linked list to a list 
			walk = walk.getNext();
		}
		
		Collections.reverse(bufferArray);
		bufferArray.subList(bufferArray.indexOf(id), bufferArray.size()).clear();
		

		return bufferArray;
	}
	
	/**
	 * this will find the node before the requested node given by id
	 * @param id
	 * @return
	 */
	public static String nodeBefore(String id){
		
		Node<Painting> walk = sList.head;
		List<String> bufferArray = new ArrayList<>();
		

		while (walk != null) {
			bufferArray.add(walk.getElement().getId()); //loads all of the ids from the linked list to a list 
			walk = walk.getNext();
		}
		Collections.reverse(bufferArray);//reverse the array because the linked list reads in backwards
		

		//System.out.println(bufferArray.get(bufferArray.indexOf(id)-1));
		return bufferArray.get(bufferArray.indexOf(id)-1);
		
	}

	/**
	 * this method changes the location value of the pain objects inside of the likned list
	 * @param id
	 * @param location
	 * @throws IOException
	 */
	public static void paintLocation(String id, String location) throws IOException{			
		Node<Painting> walk = sList.head;
		boolean test =true;

		while (walk != null && test) {		
			
			if(walk.getElement().getId().equals(id)){	//returns true if it find node requested by id	
				walk.getElement().setLocation(location); //sets the location
				test=false;
				
			}
			else{
				walk = walk.getNext();
			}
		}
		System.out.println("Location Changed");
	}


	//this will handle the second choice for the application
	//precondition: the fileToArray method is ran so that the file item size can be known
	//postcondition: the location of requested object  is changed and displayed to screen
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

