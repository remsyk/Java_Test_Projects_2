import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Artwork {

	String id;  // unique id number
	String artist; // the name of the artist, if known 
	int year;  // the year when the artwork was created, if known
	String location;  // current location of the artwork, such as gallery, storeroom, or loan

	//constructor 
	//Input parameter: id, artist, year, location
	//Precondition: year is a positive integer
	//Postcondition: A new Artwork object has been created
	Artwork(String id, String artist, int year, String location){
		this.id = id;
		this.artist = artist;
		this.year = year;
		this.location = location;
	}
	
	//overridden constructor
	Artwork(){	}
	
	//get art object id
	//Input parameter: None
	//Output: The id of the artwork 
	String getId(){

		return this.id;
	}
	
	//get art object artist
	//Input parameter: none
	//Output: The artist of the artwork
	String getArtist(){
		return artist;
	}
	
	//get art object year
	//Input parameter: none
	//Output: The year of the artwork
	int getYear(){
		return year;
	}
	
	//get art object location
	//Input parameter: none
	//Output: The location of the artwork 
	String getLocation(){
		return location;
	}
	
	//set art object id
	//Input parameter: id
	//Output: None
	//Postcondition: The id of the artwork was set to the given id 
	public void setID(String id){

		this.id = id;
	}

	//Input parameter: artist
	//Output: None
	//Postcondition: The artist of the artwork was set to the given artist 
	public void setArtist(String artist){
		this.artist=artist;
	}
	
	//set art object year
	//Input parameter: year
	//Output: None
	//Precondition: year is a positive integer
	//Postcondition: The year of the artwork was set to the given year 
	public void setYear(int year){
		//TODO
	}
	
	//set art object location
	//Input parameter: location
	//Output: None
	//Postcondition: The location of the artwork was set to the given location 
	public void setLocation(String location){
		this.location = location;
	}
	
	//postcondition: take are object elements and return them a single string
	//input: none
	//output: string
	String artToString(){
		String artString= (getId() + "," + getArtist() + "," + Integer.valueOf (getYear()) + "," + getLocation()+"\n");
		return artString;
		
	}
	
}
