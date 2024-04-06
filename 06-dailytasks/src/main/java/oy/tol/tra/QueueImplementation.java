package oy.tol.tra;


public class QueueImplementation<E> implements QueueInterface<E> {

   private Object [] itemArray;
   private int capacity;
   private static final int MIN_QUEUE_SIZE = 10;
   private static final int MAX_QUEUE_SIZE = 100;
   private int tail = 0;
   private int head = 0;
   private int numbers=0;
   /**
    * @throws QueueAllocationException
    */

   /** 
    * @param capacity 
    * @throws QueueAllocationException 
    */
   public QueueImplementation(int capacity) throws QueueAllocationException {
      if(capacity<2){
         throw new QueueAllocationException("the size is less than 2");
      }

      try{
         itemArray = new Object[capacity];
         this.capacity=capacity;
         head=0;
         tail=0;
         numbers=0;
      }
     catch(Exception e) {
         throw new QueueAllocationException("Cannot allocate room for the internal array.");
      }

   }

   public QueueImplementation() throws QueueAllocationException {
      capacity=MAX_QUEUE_SIZE;
      try{
         itemArray = new Object[capacity];
      }
     catch(Exception e) {
         throw new QueueAllocationException("Cannot allocate room for the internal array.");
      }

   }

  
   

   @Override
   public int capacity() {
      return capacity;
   }

   @Override
   public void enqueue(E element) throws QueueAllocationException, NullPointerException {
      if (element==null) {
         throw new NullPointerException("can't store element with null value");
      }
      if (tail==head&&numbers==capacity) {
         expandCapacity(capacity+1);
      }
      itemArray[tail]=element;
      tail++;
      if (tail>=capacity) {
         tail=0;
      }
      numbers++;
   }

   private void expandCapacity(int newCapacity) {
      Object[] newArray = new Object[newCapacity];
      for (int i = 0; i < capacity; i++) {
         if(i>=head){
            newArray[i+1] = itemArray[i];
         }
         else{
            newArray[i] = itemArray[i];
         }
      }
      head++;
      itemArray = newArray;
      capacity = newCapacity;
  }

   @SuppressWarnings("unchecked")
   @Override
   public E dequeue() throws QueueIsEmptyException {
      if (head==tail&&numbers==0){
         throw new QueueIsEmptyException("Underflow");
      }
      else{
         E t=(E)itemArray[head];
         itemArray[head]=null;
         head=head+1;
         if(head>=capacity){
            head=0;
         }
         numbers--;
         return t;
      }
   }

   @SuppressWarnings("unchecked")
   @Override
   public E element() throws QueueIsEmptyException {
      if (head==tail&&numbers==0) {
         throw new QueueIsEmptyException("Underflow");
      } 
      else{
         return (E)itemArray[head];
      }
   }

   @Override
   public int size() {
      return numbers;
   } 

   @Override
   public void clear() {
      tail=head; 
      numbers=0;
      tail=0;
      head=0;
   } 

   @Override
   public boolean isEmpty() {
      boolean flag;
      flag=(numbers==0);
      return flag;
   }

   @Override
   public String toString() {
      StringBuilder builder = new StringBuilder("[");
      for (var index = 0; index <= (numbers-1); index++) {
         if (index+head>=capacity) {
            builder.append(itemArray[index+head-capacity].toString());
         }
         else{
            builder.append(itemArray[index+head].toString());
         }
         
         if (index < (numbers-1)) {
            builder.append(", ");
         }
      }
      builder.append("]");
      return builder.toString();
   }
}
