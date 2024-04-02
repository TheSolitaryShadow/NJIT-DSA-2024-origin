package oy.tol.tra;

import java.util.Arrays;
import java.util.Comparator;

/**
 * For searching a number from an array of integers.
 * 
 * @author Antti Juustila
 * @version 1.0
 */

interface Isnull<K extends Comparable<K>>{
   boolean FindNull(K element);

}


public class Algorithms{


   
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
      int i=begin-1;
      for(int j=begin;j<=end-1;j++){
         if((array[j]).compareTo(X)<=0){
            i++;
            swap(array,i,j);
         }
      }
      swap(array,i+1,end);
      return (i+1);
   }

   public static <E>void swap(E[] array,int index1,int index2){
      E tmp=array[index1];
      array[index1]=array[index2];
      array[index2]=tmp;
   }


public static <K extends Comparable<K>, V> int partitionByRule(Pair<K, V> [] pairs, int count,Isnull<K> isfirstnull){
   int i;
   for(i=0;i<count;i++)
   if (isfirstnull.FindNull(pairs[i].getKey())){
      break;
   }
   return i;
}

public static <T> void sortWithComparator( T[] array, Comparator<? super T> comparator) {
        Arrays.sort(array, comparator);
    }
  
}

   

