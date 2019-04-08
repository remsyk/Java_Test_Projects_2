

import java.util.*;
import java.util.ArrayList;

import net.datastructures.LinkedBinaryTree;
import net.datastructures.Position;

public class IntLinkedBinaryTree extends LinkedBinaryTree<Integer>{
	static boolean running =true; //boolen for is program is running


	public static void main(String[] args) {
		// create a new binary tree instance
		IntLinkedBinaryTree t =   new IntLinkedBinaryTree();
		mainMenu(t);
	}	

	/**
	 * Add a new node with e to the tree rooted at position p
	 * @param p The root of the tree to which new node is added
	 * @param e The element of the new node
	 * @return If a node with e does not exist, a new node with e is added and 
	 *   reference to the node is returned. If a node with e exists, null is returned.
	 */
	public Position<Integer> add(Position<Integer> p, Integer e){

		if(p==null){
			return super.addRoot(e); //this is an empty tree create a new node with e and make it the root of the tree
		}
		Position<Integer> x = p; //x = p
		Position<Integer> y = x; //y = x

		while(x!=null){ //while (x is not null)

			if(x.getElement() == e){ //if (the element of x) is the same as e, 
				System.out.println( "The key already exists!");
				return null; //return null
			}
			else if(x.getElement()>e){ //else if (the element of x) > e
				y=x; //y = x
				x= super.left(x); //x= left child of x
			}
			else{
				y=x; //y = x
				x= super.right(x); //x=right child of x
			}
		} // end of while

		//I am not sure if it was because of the package, but there wasn't method with a constructor that took a Node as an argument
		Node<Integer> temp =new Node<Integer>(e,super.validate(y),null,null); //temp = new node with element e, y becomes the parent of temp

		if(y.getElement()>e){ //if (the element of y) > e
			super.addLeft(y,e); //temp becomes the left child of y
		}
		else{
			super.addRight(y,e); //temp becomes the right child of y
		}
		//Did not need to increment size because the package I used already did that for me
		//increment size // size is the number of elements currently in the tree 
		System.out.println("Key has been added!");
		return temp;//return temp
	}

	/**
	 * this method will delete a user specified key from the tree
	 * @param p the root of the tree from which a node is deleted
	 * @param e the integer key of the node to be deleted
	 * @return the deleted key, if e exists. If e does not exists, return null
	 */
	@SuppressWarnings("unchecked")
	public Integer remove(Position<Integer> p, Integer e){

		if(numChildren(p)==2){ //super.remove will not work if p has 2 children
			Node<Integer> current = validate(p);
			Position<Integer> pLeftChild = current.getLeft(); //only search the left child subtree
			Iterator<Position<Integer>> it = this.inorder(validate(pLeftChild)).iterator();	
			List<Integer> list = new ArrayList<Integer>(); //list to find the largest key 

			while (it.hasNext()){
				list.add(it.next().getElement()); //add all positions to a list to find the max position or right most element in the left child subtree
			}	
			
			this.set(p,Collections.max(list)); //set the key of the element we are trying to remove to the key of the largest element in the left child sub tree
			super.remove(getPosition(pLeftChild,Collections.max(list))); //set the largest key from the left child sub tree to the left child key
			System.out.println(Integer.toString(e) +" has key removed!" ); //print item has been removed

		}	
		else{
			super.remove(p);
			System.out.println(Integer.toString(e) +" has key removed!");
		}
		return e;
	}

	/**
	 * this is the main menu that is the interface through which the user operates the program
	 * @param t the tree we are working with for the program
	 */
	@SuppressWarnings("resource")
	public static void mainMenu(IntLinkedBinaryTree t){		

		do{
			System.out.println("\nChoose an option: \n"
					+  "1. Add a Key \n"
					+  "2. Remove a Key \n"
					+  "3. Print the tree \n"
					+  "4. Exit");	
			Scanner sc = new Scanner(System.in);

			int selection =  sc.nextInt();

			switch (selection) {
			case 1: System.out.println("Enter key to add: "); 
			Scanner sc2 = new Scanner(System.in); //listen for user input for id
			t.add(t.root, sc2.nextInt()); //add element to tree
			break;

			case 2: System.out.println("Enter key to remove: "); 
			Scanner sc3 = new Scanner(System.in); //listen for user input for id
			int temp3 = sc3.nextInt();
			if (t.contians(temp3)){ //if the tree contains the user specified key
				t.remove(t.getPosition(t.root,temp3),temp3); //remove the element the user defines
				break;
			}
			break;

			case 3: printAll(t); 
			break;

			case 4: System.out.println("\n" + "Goodbye!");  
			System.exit(0);//exit application
			break;

			default: running =false;
			break;
			}
		}while(running);
	}

	/**
	 * will iterate through the tree and print all element keys
	 * @param t the tree that we want to print
	 */
	public static void printAll(IntLinkedBinaryTree t){
		Iterator<Position<Integer>> it = t.inorder().iterator();	
		while (it.hasNext()){
			System.out.print(it.next().getElement() + " ");
		}
	}

	/**
	 * this will find if the tree contains a certain value
	 * @param e the key we are looking to see if exists inside of tree
	 * @return if the key is present will return true else it will be false
	 */
	public boolean contians(Integer e){
		Iterator<Position<Integer>> it = this.inorder().iterator();	
		while (it.hasNext()){//iterate through the tree
			if(it.next().getElement()==e){ //if the integer already exists print message
				System.out.println( "The key exists!");
				return true;
			}
		}
		System.out.println( "The key does not exists!");
		return false;
	}

	/**
	 * this method will find the position of the element inside of of the tree based on the value of the elements
	 * @param p the position of the root element from which to start search
	 * @param e the key value of the element we are looking to get the position of
	 * @return the position of the element we specify
	 */
	public Position<Integer> getPosition(Position<Integer> p, Integer e){
		Node<Integer> current = validate(p);
		Iterator<Position<Integer>> it = this.inorder().iterator();	
		while (it.hasNext()){//iterate through the tree
			if (current.getElement().compareTo(e) == 0) {
				return current;
			}
			else if (current.getElement().compareTo(e) > 0) { 
				current = current.getLeft();
			}
			else {
				current = current.getRight();
			}
		}
		return current;
	}

	/**
	 * Returns an iterable collection of positions of the tree, reported in inorder.
	 * @return iterable collection of the tree's positions reported in inorder
	 */
	public Iterable<Position<Integer>> inorder(Position<Integer> p) {
		List<Position<Integer>> snapshot = new ArrayList<>();
		if (!isEmpty())
			inorderSubtree(p, snapshot);   // fill the snapshot recursively
		return snapshot;
	}

	/**
	 * Adds positions of the subtree rooted at Position p to the given
	 * snapshot using an inorder traversal
	 * @param p       Position serving as the root of a subtree
	 * @param snapshot  a list to which results are appended
	 */
	private void inorderSubtree(Position<Integer> p, List<Position<Integer>> snapshot) {
		if (left(p) != null)
			inorderSubtree(left(p), snapshot);
		snapshot.add(p);
		if (right(p) != null)
			inorderSubtree(right(p), snapshot);
	}

}




















