package oy.tol.tira.books;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

class TreeNode {

   private int hash = -1;
   WordCount keyValue;
   TreeNode left = null;
   TreeNode right = null;
   static int currentAddTreeDepth = 0;
   static int longestCollisionChain = 0;
   // OPTIONAL Handling collisions with a linked list in the tree node.
   LinkedListImplementation<WordCount> list = null;
   
   
   // Needed for searching by key; value is not then needed.

   TreeNode(String word, int count) throws NullPointerException {
      if (null == word)
         throw new NullPointerException("K cannot be null");
      keyValue = new WordCount(word, count);
      this.hash =hashCode(word);
      left = null;
      right = null;
      list = null;
   }

   TreeNode(WordCount wordCount) throws NullPointerException {
      keyValue = new WordCount(wordCount.word, wordCount.count);
      this.hash =hashCode(wordCount.word);
      left = null;
      right = null;
      list = null;
   }


   int insert(WordCount wordCount, int toInsertHash) throws RuntimeException {
      int uniqueWordCount=0;
      if (toInsertHash < this.hash) {
         if (null == left) {
            left = new TreeNode(wordCount);
            uniqueWordCount=1;
         } 
         else {
            uniqueWordCount=left.insert(wordCount, toInsertHash);
         }
      }
      else if (toInsertHash > this.hash) {
         if (null == right) {
            right = new TreeNode(wordCount);
            uniqueWordCount=1;
         } 
         else {
            uniqueWordCount=right.insert(wordCount, toInsertHash);
         }
      } 
      else { // equal hashes
         if (this.keyValue.word.equals(wordCount.word)) {
            // Key-value pair already in tree, update the value for the key.
            this.keyValue.count++;
         } 
         else {
            // OPTIONAL different key, same hash, put in the linked list.
            if (null == list) {
               list = new LinkedListImplementation<>();
               list.add(wordCount);
               uniqueWordCount++;
            } 
            else {
               WordCount newItem = wordCount;
               int index = list.indexOf(newItem);
               if (index < 0) {
                  list.add(newItem);
                  uniqueWordCount++;
               } 
               else {
                  list.get(index).count++;
               }
            }
            uniqueWordCount=1;
            if (list.size() > TreeNode.longestCollisionChain) {
               TreeNode.longestCollisionChain = list.size();
            }
            // END OPTIONAL
         }
      }
      return uniqueWordCount;
   }



   public void accept(Visitor visitor) {
      visitor.visit(this);
   }

   @Override 
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      }
      if (obj == null) {
         return false;
      }
      if (getClass() != obj.getClass()) {
         return false;
      }
      TreeNode other = (TreeNode) obj;
      if (keyValue == null) {
         if (other.keyValue != null) {
            return false;
         }
      } else if (!keyValue.equals(other.keyValue)) {
         return false;
      }
      return true;
   }

   public int hashCode(String word) {
      int hash = 0;
     for (int index = 0; index < word.length(); index++) {
         hash = 31*hash + word.charAt(index);
     }
     return hash;
  }
  
   
}
