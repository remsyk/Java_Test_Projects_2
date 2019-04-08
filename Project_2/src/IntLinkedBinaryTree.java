

import java.util.*;

import net.datastructures.LinkedBinaryTree;
import net.datastructures.Position;

public class IntLinkedBinaryTree extends LinkedBinaryTree<Integer>{

	
	// define instance variables and methods, including a constructor(s) as needed
	
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
		return temp;//return temp
	}

	
	public static void main(String[] args) {
		
		// create a new binary tree instance
		IntLinkedBinaryTree t =   new IntLinkedBinaryTree();
		
		// add some integers
		 t.add(t.root, 100);
		 t.add(t.root, 50);
		 t.add(t.root, 150);
		 t.add(t.root, 70);
		 
		 t.add(t.root, 15); // test with more integers 
		 t.add(t.root, 90); // test with more integers
		 t.add(t.root, 42); // test with more integers
		 t.add(t.root, 75); // test with more integers

		// print all integers in the tree in increasing order
		// after adding above four integers, the following should be printed
		// 50 70 100 150
		
		Iterator<Position<Integer>> it = t.inorder().iterator();	
		System.out.println();
		while (it.hasNext()){
			System.out.print(it.next().getElement() + " ");
		}
		System.out.println();
		
	}





}
