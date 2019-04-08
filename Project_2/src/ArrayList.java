/*
 * Copyright 2014, Michael T. Goodrich, Roberto Tamassia, Michael H. Goldwasser
 *
 * Developed for use with the book:
 *
 *    Data Structures and Algorithms in Java, Sixth Edition
 *    Michael T. Goodrich, Roberto Tamassia, and Michael H. Goldwasser
 *    John Wiley & Sons, 2014
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.NoSuchElementException;

/**
 * Realization of a list by means of a dynamic array. This is a simplified version
 * of the java.util.ArrayList class.
 *
 * @author Michael T. Goodrich
 * @author Roberto Tamassia
 * @author Michael H. Goldwasser
 */
public class ArrayList<E> implements List<E> {
  // instance variables
  /** Default array capacity. */
  public static final int CAPACITY=16;     // default array capacity

  /** Generic array used for storage of list elements. */
  private E[] data;                        // generic array used for storage

  /** Current number of elements in the list. */
  private int size = 0;                    // current number of elements

  private Object iterator;

  // constructors
  /** Creates an array list with default initial capacity. */
  public ArrayList() { this(CAPACITY); }   // constructs list with default capacity

  /** Creates an array list with given initial capacity. */
  @SuppressWarnings({"unchecked"})
  public ArrayList(int capacity) {         // constructs list with given capacity
    data = (E[]) new Object[capacity];     // safe cast; compiler may give warning
  }

 
 
  // public methods
  /**
   * Returns the number of elements in the list.
   * @return number of elements in the list
   */
  public int size() { return size; }

  /**
   * Tests whether the array list is empty.
   * @return true if the array list is empty, false otherwise
   */
  public boolean isEmpty() { return size == 0; }

  /**
   * Returns (but does not remove) the element at index i.
   * @param  i   the index of the element to return
   * @return the element at the specified index
   * @throws IndexOutOfBoundsException if the index is negative or greater than size()-1
   */
  public E get(int i) throws IndexOutOfBoundsException {
    checkIndex(i, size);
    return data[i];
  }

  /**
   * Replaces the element at the specified index, and returns the element previously stored.
   * @param  i   the index of the element to replace
   * @param  e   the new element to be stored
   * @return the previously stored element
   * @throws IndexOutOfBoundsException if the index is negative or greater than size()-1
   */
  public E set(int i, E e) throws IndexOutOfBoundsException {
    checkIndex(i, size);
    E temp = data[i];
    data[i] = e;
    return temp;
  }

  /**
   * Inserts the given element at the specified index of the list, shifting all
   * subsequent elements in the list one position further to make room.
   * @param  i   the index at which the new element should be stored
   * @param  e   the new element to be stored
   * @throws IndexOutOfBoundsException if the index is negative or greater than size()
   */
  public void add(int i, E e) throws IndexOutOfBoundsException {
    checkIndex(i, size + 1);
    if (size == data.length)               // not enough capacity
      resize(2 * data.length);             // so double the current capacity
    for (int k=size-1; k >= i; k--)        // start by shifting rightmost
      data[k+1] = data[k];
    data[i] = e;                           // ready to place the new element
    size++;
  }

  /**
   * Removes and returns the element at the given index, shifting all subsequent
   * elements in the list one position closer to the front.
   * @param  i   the index of the element to be removed
   * @return the element that had be stored at the given index
   * @throws IndexOutOfBoundsException if the index is negative or greater than size()
   */
  public E remove(int i) throws IndexOutOfBoundsException {
    checkIndex(i, size);
    E temp = data[i];
    for (int k=i; k < size-1; k++)         // shift elements to fill hole
      data[k] = data[k+1];
    data[size-1] = null;                   // help garbage collection
    size--;
    return temp;
  }

  // utility methods
  /** Checks whether the given index is in the range [0, n-1]. */
  protected void checkIndex(int i, int n) throws IndexOutOfBoundsException {
    if (i < 0 || i >= n)
      throw new IndexOutOfBoundsException("Illegal index: " + i);
  }

  /** Resizes internal array to have given capacity >= size. */
  @SuppressWarnings({"unchecked"})
  protected void resize(int capacity) {
    E[] temp = (E[]) new Object[capacity];     // safe cast; compiler may give warning
    for (int k=0; k < size; k++)
      temp[k] = data[k];
    data = temp;                               // start using the new array
  }

  //---------------- nested ArrayIterator class ----------------
  /**
   * A (nonstatic) inner class. Note well that each instance contains an implicit
   * reference to the containing list, allowing it to access the list's members.
   */
  private class ArrayIterator implements Iterator<E> {
    /** Index of the next element to report. */
    private int j = 0;                   // index of the next element to report
    private boolean removable = false;   // can remove be called at this time?

    /**
     * Tests whether the iterator has a next object.
     * @return true if there are further objects, false otherwise
     */
    public boolean hasNext() { return j < size; }   // size is field of outer instance

    /**
     * Returns the next object in the iterator.
     *
     * @return next object
     * @throws NoSuchElementException if there are no further elements
     */
    public E next() throws NoSuchElementException {
      if (j == size) throw new NoSuchElementException("No next element");
      removable = true;   // this element can subsequently be removed
      return data[j++];   // post-increment j, so it is ready for future call to next
    }

    /**
     * Removes the element returned by most recent call to next.
     * @throws IllegalStateException if next has not yet been called
     * @throws IllegalStateException if remove was already called since recent next
     */
    public void remove() throws IllegalStateException {
      if (!removable) throw new IllegalStateException("nothing to remove");
      ArrayList.this.remove(j-1);  // that was the last one returned
      j--;                         // next element has shifted one cell to the left
      removable = false;           // do not allow remove again until next is called
    }
  } //------------ end of nested ArrayIterator class ------------

  /**
   * Returns an iterator of the elements stored in the list.
   * @return iterator of the list's elements
   */
  @Override
  public Iterator<E> iterator() {
    return new ArrayIterator();     // create a new instance of the inner class
  }

  /**
   * Produces a string representation of the contents of the indexed list.
   * This exists for debugging purposes only.
   *
   * @return textual representation of the array list
   */
  public String toString() {
    StringBuilder sb = new StringBuilder("(");
    for (int j = 0; j < size; j++) {
      if (j > 0) sb.append(", ");
      sb.append(data[j]);
    }
    sb.append(")");
    return sb.toString();
  }
  
  public void printList(){
	  System.out.println();
	  for (int i=0; i<size; i++){
		  System.out.print(get(i) + " ");
	  }
	  System.out.println();
  }
  
  
 /**
  * Adds all elements in l to the end of this list, in the order that they are in l.
  *  Input: An ArrayList l.
  *  Output: None
  *  Postcondition: All elements in the list l have been added to this list. 
  * @param l
  */
  @SuppressWarnings("rawtypes")
  public void addAll(ArrayList<E> l){
	for(E e: l){
		add(size,e);
	}
  }
  
  /**
   * Removes the first occurrence of the element e from this list.
   * Input: The element to be removed.
   * Output: Returns true if the element e exists in this list, and returns false otherwise. 
   * @param e
   * @return
   */
  public boolean remove2(E e){
	for (int i=0; i < size-1; i++){
		if(data[i].equals(e)){
			remove(i);
		}			
	}
	  return true;
  }
  
/**
 * Removes from this list all of the elements whose index is between fromIndex, inclusive,
 * and toIndex, exclusive
 * Input:
 * fromIndex: index of the first element to be removed
 * toIndex: index after the last element to be removed
 * Output: None
 * Precondition: fromIndex and toIndex must be valid indexes. 
 * @param fromIndex
 * @param toIndex
 */
  public void removeRange(int fromIndex, int toIndex){
	  
	  for(int i =fromIndex; i<=toIndex;i++){
		  remove(fromIndex);
	  }
  }

  /**
   * Trims the capacity of this list to be the list's current size.
   * Input: None
   * Output: None
   * Postcondition: The capacity of this list has been trimmed to the list's current size. 
   */
  public void trimToSize(){
	  int empytyIndexes=0;
	  for(int i=0; i<size; i++){
		  if(data[i]==null){
			  empytyIndexes+=1;
		  }
	  }
	  size = size -empytyIndexes;
  }
  
  

  public static void main(String[] args) {
	  
	 ArrayList<String> stringList = new ArrayList<>(10);
	
	  System.out.println("\n <--Adding Some Words!-->");
	  stringList.add(0, "Java");
	  stringList.add(0, "with");
	  stringList.add(0, "Structure");
	  stringList.add(0, "Data");
	  stringList.printList();
	  
	  System.out.println("\n <--Setting Python-->");
	  stringList.set(2, "Python");
	  stringList.printList();

	  System.out.println("\n <--Adding More Words!-->");
	  stringList.add(0, "the");
	  stringList.add(0, "sun");
	  stringList.add(0, "rises");
	  stringList.add(0, "at");
	  stringList.add(0, "night");
	  stringList.add(0, "day");
	  stringList.printList();
	 
	  
	  System.out.println("\n <--Adding all of List to End of List-->");
	  ArrayList<String> testList = new ArrayList<>(3);
	  testList.add(0,"test3");
	  testList.add(0,"test2");
	  testList.add(0,"test1");
	  stringList.addAll(testList);
	  stringList.printList();
	  
	  
	  System.out.println("\n <--Removing night-->");
	  stringList.remove2("night");
	  stringList.printList();
	  
	  System.out.println("\n <--Removing Range 2,4-->");
	  stringList.removeRange(2,4);
	  stringList.printList();
	  
	  System.out.println("\n <--Triming to Size--> \n");
	  System.out.println("Before: " + stringList.size());
	  stringList.trimToSize();
	  System.out.println("After: " + stringList.size());


  }

@Override
public boolean contains(Object o) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public Object[] toArray() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public <T> T[] toArray(T[] a) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public boolean add(E e) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean remove(Object o) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean containsAll(Collection<?> c) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean addAll(Collection<? extends E> c) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean addAll(int index, Collection<? extends E> c) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean removeAll(Collection<?> c) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public boolean retainAll(Collection<?> c) {
	// TODO Auto-generated method stub
	return false;
}

@Override
public void clear() {
	// TODO Auto-generated method stub
	
}

@Override
public int indexOf(Object o) {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public int lastIndexOf(Object o) {
	// TODO Auto-generated method stub
	return 0;
}

@Override
public ListIterator<E> listIterator() {
	// TODO Auto-generated method stub
	return null;
}

@Override
public ListIterator<E> listIterator(int index) {
	// TODO Auto-generated method stub
	return null;
}

@Override
public List<E> subList(int fromIndex, int toIndex) {
	// TODO Auto-generated method stub
	return null;
} 
}































