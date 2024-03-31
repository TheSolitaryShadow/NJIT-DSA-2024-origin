package oy.tol.tra;

/**
 * A simple array of student grades to be used in testing
 * misbehaving algorithm for reversing the array.
 */
public class Grades {
   
   private Integer [] grades = null;

   /**
    * A constructor for building IntArrays.
    * @param grades the plain Java integer array with numbers to add.
    */
   public Grades(Integer [] grades) {
      this.grades = new Integer [grades.length];
      for (int counter = 0; counter < grades.length; counter++) {
         this.grades[counter] = grades[counter];
      }
   }

  //change
   public void reverse() {
      Algorithms.reverse(grades);
   }


  
   //change
   public void sort() {
      Algorithms.sort(grades);
   }

   
 
   public Integer [] getArray() {
      return grades;
   }
}
