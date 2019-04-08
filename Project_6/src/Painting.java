
public class Painting extends Artwork {

	String paintType;  // oil, watercolor, etc. 
	String material;  // material it is drawn on, such as paper, canvas, wood, etc. 
	
	//Input parameter: id, artist, year, location, paintType, material
	//Precondition: year is a positive integer 
	//Postcondition: A new Painting object with given attributes has been created.
	Painting (String id, String artist, int year, String location, String paintType, String material){
		this.id = id;
		this.artist = artist;
		this.year = Math.abs(year);
		this.location = location;
		this.paintType = paintType;
		this.material = material;
	}
	
	//Input parameter: None
	//Output: The paintType of the painting
	String getPaintType(){
		return this.paintType;
	}
	
	//Input parameter: None
	//Output: The material of the painting 
	String getMaterial(){
		return this.material;
	}
	
	//Input parameter: paintType
	//Output: None
	//Postcondition: The paintType of the painting was set to the given paintType.
	public void setPaintType(String paintType){
		this.paintType = paintType;
	}
	
	//Input parameter: material
	//Output: None
	//Postcondition: The material of the painting was set to the given material.
	public void setMaterial(String material){
		this.material=material;
	}
	
	//postcondition: take are object elements and return them a single string
		//input: none
		//output: string
	public String toString(){
		String artString= (getId() + "," + getArtist() + "," + Integer.valueOf (getYear()) + "," + getLocation()+ "," + getPaintType()+ "," + getMaterial() +"\n");
		return artString;
			
	}
}





















