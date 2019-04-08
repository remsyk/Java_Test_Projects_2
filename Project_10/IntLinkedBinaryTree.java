

import java.util.*;

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

		// implement this method
		
	}
	
	
	public static void main(String[] args) {
		
		// create a new binary tree instance
		IntLinkedBinaryTree t =   new IntLinkedBinaryTree();
		
		// add some integers
		// t.add(t.root, 100);
		// t.add(t.root, 50);
		// t.add(t.root, 150);
		// t.add(t.root, 70);
		// test with more integers 
		
		// print all integers in the tree in increasing order
		// after adding above four integers, the following should be printed
		// 50 70 100 150
		
		// Iterator<Position<Integer>> it = t.inorder().iterator();	
		// System.out.println();
		// while (it.hasNext()){
		//	System.out.print(it.next().getElement() + " ");
		// }
		// System.out.println();
		
	}

}
