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
   
   

   /**
    * The method to reverse the internal Java int array.
    */
   public static void reverse(Integer [] algo) {

      int i = 0;
      while (i < algo.length/2) {
         Integer temp = algo[i];
         algo[i] = algo[algo.length-i-1];
         algo[algo.length-i-1] = temp;
         i++;
     }
   }

   public static void reverse(String [] algo) {

      int i = 0;
      while (i < algo.length/2) {
         String temp = algo[i];
         algo[i] = algo[algo.length-i-1];
         algo[algo.length-i-1] = temp;
         i++;
     }
   }


   /**
    * Sorts the array to ascending order.
    */
   public static void sort(Integer [] algo) {
      
      if (algo == null || algo.length <= 1) {
          return;
      }

      for (int i = 1; i < algo.length; i++) {
          int tmp = algo[i];
          int j = i - 1;
          while (j >= 0 && algo[j] > tmp) {
              algo[j + 1] = algo[j];
              j--;
          }
          algo[j + 1] = tmp;
      }
   }

   public static void sort(String [] algo) {
      
      if (algo == null || algo.length <= 1) {
          return;
      }

      for (int i = 1; i < algo.length; i++) {
          String tmp = algo[i];
          int j = i - 1;
          while (j >= 0 && algo[j].compareTo(tmp)>0) {
              algo[j + 1] = algo[j];
              j--;
          }
          algo[j + 1] = tmp;
      }
   }


}
