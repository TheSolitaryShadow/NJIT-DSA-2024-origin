package oy.tol.tra;

/**
 * For searching a number from an array of integers.
 * 
 * @author Antti Juustila
 * @version 1.0
 */
public class Algorithms {

   

   /**
    * Finds a number from the specified array using linear search, -1 if one could not be found.
    * @param aValue The value to find.
    * @param fromArray The array to search.
    * @param fromIndex The index to start searching from.
    * @param toIndex The index to search to in the array.
    * @return The index of the number in the array, -1 if not found.
    */

   public static <T extends Comparable<T>> int binarySearch(T aValue, T [] fromArray, int fromIndex, int toIndex) {
      int middle=0;
      while (fromIndex <= toIndex) {
         middle=(fromIndex+toIndex)/2;
         if (fromArray[middle].compareTo(aValue)==0) {
            return middle;
         }
         else if (fromArray[middle].compareTo(aValue)>0) {
            toIndex=middle-1;
         }
         else {
            fromIndex=middle+1;
         }
      }
      return -1;
   }


   /**
    * Finds a String from the specified array using linear search, -1 if one could not be found.
    * @param aValue The value to find.
    * @param fromArray The array to search.
    * @param fromIndex The index to start searching from.
    * @param toIndex The index to search to in the array.
    * @return The index of the number in the array, -1 if not found.
    */
   
   public static <T extends Comparable<T>> void sort(T [] algo){
      if (algo == null || algo.length <= 1) {
         return;
     }

     for (int i = 1; i < algo.length; i++) {
         T tmp = algo[i];
         int j = i - 1;
         while (j >= 0 && algo[j].compareTo(tmp)>0) {
             algo[j + 1] = algo[j];
             j--;
         }
         algo[j + 1] = tmp;
     }
   }







   public static <E extends Comparable<E>> void fastSort(E [] array) {
      quickSort(array, 0, array.length - 1);
   }



   public static <E extends Comparable<E>> void quickSort(E [] array, int begin, int end) {
      if(begin<end){
         int q=partition(array, begin, end);
         quickSort(array,begin,q-1);
         quickSort(array,q+1,end);  
 
      }
   }

   private static <E extends Comparable<E>> int partition(E [] array, int begin, int end) {
      
      E X=array[end];
      E temp;
      int i=begin-1;
      for(int j=begin;j<=end-1;j++){
         if((array[j]).compareTo(X)<=0){
            i++;
            temp=array[i];
            array[i]=array[j];
            array[j]=temp;
         }
      }
      temp=array[i+1];
      array[i+1]=array[end];
      array[end]=temp;

      return (i+1);
   }

}

   

