
public class MyException extends Exception {
	
	
	public MyException() { 
		super("MyException"); 
		}
	
	//my custom exception
	//input: specified user string
	//postcondition: displays the exception
	//precondition: none
    public MyException(String message){
        super(message + " (MyException)");
    }

}
