package oy.tol.tra;

/**
 * An implementation of the StackInterface.
 * <p>
 * TODO: Students, implement this so that the tests pass.
 * 
 * Note that you need to implement construtor(s) for your concrete StackImplementation, which
 * allocates the internal Object array for the Stack:
 * - a default constructor, calling the StackImplementation(int size) with value of 10.
 * - StackImplementation(int size), which allocates an array of Object's with size.
 *  -- remember to maintain the capacity and/or currentIndex when the stack is manipulated.
 */
public class StackImplementation<E> implements StackInterface<E> {

   private Object [] itemArray;
   private int capacity;
   private int currentIndex = -1;
   private static final int DEFAULT_STACK_SIZE = 10;

   /**
    * Allocates a stack with a default capacity.
    * @throws StackAllocationException
    */
   public StackImplementation() throws StackAllocationException {
      this.capacity=DEFAULT_STACK_SIZE;
   }

   
   public StackImplementation(int capacity) throws StackAllocationException {
      this.capacity=capacity;
      if(capacity<2){
         throw new StackAllocationException("the size is less than 2");
      }

      try{
         itemArray = new Object[capacity];
      }
     catch(Exception e) {
         throw new StackAllocationException("Cannot allocate room for the internal array.");
      }
   }
   

   @Override
   public int capacity() {
      return capacity;
   }

   @Override
   public void push(E element) throws StackAllocationException, NullPointerException {
      if (element==null) {
         throw new NullPointerException("can't store element with null value");
      }
      if (currentIndex==capacity-1) {
         expandCapacity(capacity * 2);
      }
      currentIndex++;
      itemArray[currentIndex]=element;
   }

   private void expandCapacity(int newCapacity) {
      Object[] newArray = new Object[newCapacity];
      for (int i = 0; i < capacity; i++) {
          newArray[i] = itemArray[i];
      }
      
      itemArray = newArray;
      capacity = newCapacity;
  }

   @SuppressWarnings("unchecked")
   @Override
   public E pop() throws StackIsEmptyException {
      if (currentIndex==-1){
         throw new StackIsEmptyException("Underflow");
      }
      else{

         //change
         E returnvalue=(E)itemArray[currentIndex];
         itemArray[currentIndex]=null;
         currentIndex=currentIndex-1;
         return returnvalue;
      }
   }

   @SuppressWarnings("unchecked")
   @Override
   public E peek() throws StackIsEmptyException {
      if (currentIndex==-1) {
         throw new StackIsEmptyException("Underflow");
      } 
      else{
         return (E)itemArray[currentIndex];
      }
   }

   @Override
   public int size() {
      return (currentIndex+1);
   }

   @Override
   public void clear() {
      itemArray=new Object[capacity];
      currentIndex=-1;
   } 

   @Override
   public boolean isEmpty() {
      boolean flag;
      flag=(currentIndex==-1);
      return flag;
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder("[");
      for (var index = 0; index <= currentIndex; index++) {
         builder.append(itemArray[index].toString());
         if (index < currentIndex) {
            builder.append(", ");
         }
      }
      builder.append("]");
      return builder.toString();
   }
}
