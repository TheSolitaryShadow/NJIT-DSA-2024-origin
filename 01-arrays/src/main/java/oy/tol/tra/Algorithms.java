package oy.tol.tra;

/**
 * A simple array of student grades to be used in testing
 * misbehaving algorithm for reversing the array.
 */
public class Algorithms {
   


   /**
    * A constructor for building IntArrays.
    * @param grades the plain Java integer array with numbers to add.
    */
   
   

   //change

   public static <T> void reverse(T [] algo) {

      int i = 0;
      while (i < algo.length/2) {
         T temp = algo[i];
         algo[i] = algo[algo.length-i-1];
         algo[algo.length-i-1] = temp;
         i++;
     }
   }

   //change
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


}
