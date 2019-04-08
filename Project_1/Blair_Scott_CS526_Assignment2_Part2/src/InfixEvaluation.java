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
import java.util.List;
import java.util.Stack;

public class InfixEvaluation {
	static String fileName = "math.txt"; //file program is reading from
	static Stack<Integer> Optr = new Stack<Integer>(); //stack for operands
	static Stack<String> Opnd = new Stack<String>(); //stack for operators
	static Stack<String> Par = new Stack<String>(); //stack for parentheses

	@SuppressWarnings("rawtypes")
	public static void main(String[] args) throws IOException {

		Path path = Paths.get(fileName); //specifies path to file
		//List<String> fileLines = Files.readAllLines(path, StandardCharsets.ISO_8859_1);//read the file line by line and loads it into a list
		BufferedReader reader = null;

		try {
			File file = new File(fileName);
			reader = new BufferedReader(new FileReader(file));

			String lines;
			while ((lines = reader.readLine()) != null) {
				
				/*
				String reverseLine = new StringBuffer(lines).reverse().toString();
				String Optr_s = reverseLine;
				String Opnd_s = reverseLine;
				String Par_s = reverseLine;
				*/
				
				String Optr_s = lines;
				String Opnd_s = lines;
				String Par_s = lines;
								 
				Optr_s = Optr_s.replaceAll("[^0-9]+", " "); //remove all operands and parentheses (keep numbers)
				Opnd_s = Opnd_s.replaceAll("[()0-9]", " "); //remove all operators and parentheses (keep operators)
				Par_s = Par_s.replaceAll("[(+-\\\\*/0-9]+", " "); //remove all operands and number (keep parentheses)

				List<String> Optr_l = Arrays.asList(Optr_s.trim().split(" ")); //set to array
				List<String> Opnd_l = Arrays.asList(Opnd_s.trim().split(" ")); //set to array
				List<String> Par_l = Arrays.asList(Par_s.trim().split(" ")); //set to array

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

				while(!Opnd.isEmpty()){

					String s = Opnd.pop();
					int num = Optr.pop();

					if      (s.equals("+"))    num = Optr.pop() + num;
					else if (s.equals("-"))    num = Optr.pop() - num;
					else if (s.equals("*"))    num = Optr.pop() * num;
					else if (s.equals("/"))    num = Optr.pop() / num;
					Optr.push(num);
				}

				System.out.println(String.valueOf(Optr.pop())+"\n");

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
