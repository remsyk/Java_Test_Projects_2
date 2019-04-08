import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

public class InfixEvaluation {
	static String fileName = "math.txt"; //file program is reading from
	static Stack<Integer> Optr = new Stack<Integer>(); //stack for operands
	static Stack<String> Opnd = new Stack<String>(); //stack for operators
	static Stack<String> Par = new Stack<String>(); //stack for parentheses

	public static void main(String[] args) throws IOException {

		BufferedReader reader = null;
		int solution =0;

		try {
			File file = new File(fileName);
			reader = new BufferedReader(new FileReader(file));//read in file with equations

			String lines; //each line of the file being read in
			while ((lines = reader.readLine()) != null) {	
				
				String Optr_s = lines; //assign string to be delimited to input stream
				String Opnd_s = lines; //assign string to be delimited to input stream
				String Par_s = lines; //assign string to be delimited to input stream
			
				Optr_s = Optr_s.replaceAll("[^0-9]+", " "); //remove all operands and parentheses (keep numbers)
				Opnd_s = Opnd_s.replaceAll("[()0-9]", " "); //remove all operators and parentheses (keep operators)
				Par_s = Par_s.replaceAll("[^)]", " "); //remove all operands and number (keep parentheses)
				
				
				List<String> Optr_l = Arrays.asList(Optr_s.trim().split(" ")); //set to array
				List<String> Opnd_l = Arrays.asList(Opnd_s.trim().split(" ")); //set to array
				List<String> Par_l = Arrays.asList(Par_s.trim().split(" ")); //set to array
				
				
				Collections.reverse(Optr_l);//since this is a stack that reads LIFO, decided to reverse the order of the string going in so it could read easier
				Collections.reverse(Opnd_l);//since this is a stack that reads LIFO, decided to reverse the order of the string going in so it could read easier
				Collections.reverse(Par_l);//since this is a stack that reads LIFO, decided to reverse the order of the string going in so it could read easier
				 
				
				for(String s: Optr_l){
					Optr.push(Integer.valueOf(s)); //push numbers to stack
				}

				for(String s: Opnd_l){
					Opnd.push(s); //push operands to stack
				}

				for(String s: Par_l){
					Par.push(s); //push parentheses to stack
				}

				
				System.out.println(Arrays.toString(Optr.toArray()));
				System.out.println(Arrays.toString(Opnd.toArray()));
				System.out.println(Arrays.toString(Par.toArray()));
				System.out.println("----------------------");
				

				while(!Opnd.isEmpty()){ //while operands stack is not empty solve equations
					
					String op = Opnd.pop();

					if(Par.pop().equals(")")){ //for every instance where there is a left parentheses have the loop solve the math problem inside and push the result to the stack
						int num = Optr.pop();

						if      (op.equals("+"))    num = Optr.pop() + num;
						else if (op.equals("-"))    num = Optr.pop() - num;
						else if (op.equals("*"))    num = Optr.pop() * num;
						else if (op.equals("/"))    num = Optr.pop() / num;
						Optr.push(num); //push result to stack
						System.out.println(String.valueOf(num)+"\n");
					}
				}

				System.out.println("Solution " + String.valueOf(solution)+ ": "  + String.valueOf(Optr.pop())+"\n"); //output printed to screen
				solution+=1;
			}	

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}

	




















