REPORT OF THE STUDY

1.hash function: h(k,i)=(f(k)+i)mod m,f(k)=k mod m

2.Due to the large size of the test data, using Quicksort can lead to stack overflow errors. Therefore, I set conditions based on fast sorting to reduce recursive calls.(maybe q should less than or equal to 100,but I not sure if it will work correctly.)
```Java
public static <E extends Comparable<E>> void quickSort(E [] array, int begin, int end) {
      if(begin<end){
         int q=partition(array, begin, end);
         quickSort(array,begin,q-1);
         if(q<200){
            quickSort(array,q+1,end);  
         }
      }
   }
```

3.I think my implementation is correct,the only problem is stack overflow errors,  this problem is because the depth of recursion is too deep, and the size of the stack exceeds the maximum depth allowed by the virtual machine.So I choose to reduce the depth of recursion by set conditions.

4.I use Quicksort, time complexity of O (n * log (n))

5.For me, this task is generally not difficult to understand. In terms of implementation, I am troubled by stack overflow errors, so I am concerned about whether my implementation meets the requirements.

6.Through this programming experience, I have gained a deeper understanding of the core functions of related programs and gained a deeper understanding of hash tables and binary search trees. When there are errors in testing, I learn to observe error messages and debug step by step to discover problems.