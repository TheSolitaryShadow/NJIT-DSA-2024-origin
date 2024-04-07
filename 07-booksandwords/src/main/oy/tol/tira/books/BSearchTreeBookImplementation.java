package oy.tol.tira.books;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

public class BSearchTreeBookImplementation implements Book {

    // This is the BST implementation, KeyValueHashTable has the hash table
    // implementation

    private static final int MAX_WORDS = 100000;
    private static final int MAX_WORD_LEN = 100;

    private WordCount[] words = null;
    private TreeNode wordsroot=null;

    private String bookFile = null;
    private String wordsToIgnoreFile = null;
    private WordFilter filter = null;
    private int uniqueWordCount = 0;
    private int totalWordCount = 0;
    private int ignoredWordsTotal = 0;
    private long loopCount = 0;
    private int maxTreeDepth = 0;


    @Override
    public void setSource(String fileName, String ignoreWordsFile) throws FileNotFoundException {
        // Check if both files exist. If not, throw an exception.
        boolean success = false;
        if (checkFile(fileName)) {
            bookFile = fileName;
            if (checkFile(ignoreWordsFile)) {
                wordsToIgnoreFile = ignoreWordsFile;
                success = true;
            }
        }
        if (!success) {
            throw new FileNotFoundException("Cannot find the specified files");
        }
    }

    @Override
    public void report() {
        if (wordsroot == null) {
            System.out.println("*** No words to report! ***");
            return;
        }
        System.out.println("Listing words from a file: " + bookFile);
        System.out.println("Ignoring words from a file: " + wordsToIgnoreFile);
        System.out.println("Sorting the results...");
        sortWords();
        System.out.println("...sorted.");

        for (int index = 0; index < 100; index++) {
            if (index>=words.length) {
                break;
            }
            String word = String.format("%-20s", words[index].word).replace(' ', '.');
            System.out.format("%4d. %s %6d%n", index + 1, word, words[index].count);
        }
        System.out.println("Count of words in total: " + totalWordCount);
        System.out.println("Count of unique words:    " + uniqueWordCount);
        System.out.println("Count of words to ignore:    " + filter.ignoreWordCount());
        System.out.println("Ignored words count:      " + ignoredWordsTotal);
        System.out.println("How many times the inner loop rolled: " + loopCount);
        System.out.println("The depth of the tree: " + maxTreeDepth);
    }

    @Override
    public void countUniqueWords() throws IOException, OutOfMemoryError {
        if (bookFile == null || wordsToIgnoreFile == null) {
            throw new IOException("No file(s) specified");
        }
        // Reset the counters
        uniqueWordCount = 0;
        totalWordCount = 0;
        loopCount = 0;
        ignoredWordsTotal = 0;
        filter = new WordFilter();
        filter.readFile(wordsToIgnoreFile);
        FileReader reader = new FileReader(bookFile, StandardCharsets.UTF_8);
        int c;
        int[] array = new int[MAX_WORD_LEN];
        int currentIndex = 0;
        while ((c = reader.read()) != -1) {
            if (Character.isLetter(c)) {
                array[currentIndex] = c;
                currentIndex++;
            } else {
                if (currentIndex > 0) {
                    String word = new String(array, 0, currentIndex).toLowerCase(Locale.ROOT);
                    currentIndex = 0;
                    addToWords(new WordCount(word, 1));
                }
            }
        }
        if (currentIndex > 1) {
            String word = new String(array, 0, currentIndex).toLowerCase(Locale.ROOT);
            addToWords(new WordCount(word, 1));
        }
        reader.close();
    }

    private void addToWords(WordCount wordCount) throws OutOfMemoryError {
        // Filter out too short words or words in filter list.
        if (!filter.shouldFilter(wordCount.word) && wordCount.word.length() >= 2) {
            if(wordsroot==null){
                wordsroot=new TreeNode(wordCount);
                uniqueWordCount=1;
            }else{
                uniqueWordCount+=wordsroot.insert(wordCount, hashCode(wordCount.word));
            }
            totalWordCount++;
        } 
        else {
            ignoredWordsTotal++;
        }
    }



    public void sortWords() {
        TreeToArrayVisitor visitor = new TreeToArrayVisitor(uniqueWordCount);
        wordsroot.accept(visitor);
        words = visitor.getArray();
        int length=partitionByRule(words, words.length);
        Arrayreallocate(length);
        Algorithms.fastSort(words);
        maxTreeDepth=calculateDepth(wordsroot);

    }


    public static int partitionByRule(WordCount[] pairs,int count){
        int left = 0;
        int right = count - 1;
        while (left <= right) {
            while (left <= right && !(pairs[left].word=="")) {
                left++;
            }

            while (left <= right && (pairs[right].word=="")) {
                right--;
            }

            if (left < right) {
                Algorithms.swap(pairs, left, right);
                left++;
                right--;
            }
        }
        return left;
    }


    private void Arrayreallocate(int newSize) throws OutOfMemoryError {
        WordCount[] newWords = new WordCount[newSize];
        for (int index = 0; index<newSize; index++) {
            if (words[index].word !=""){
                newWords[index] = words[index];
            }
        }
        words = newWords;
    }


    @Override
    public void close() {
        bookFile = null;
        wordsToIgnoreFile = null;
        words = null;
        if (filter != null) {
            filter.close();
        }
        filter = null;
    }

    @Override
    public int getUniqueWordCount() {
        return uniqueWordCount;
    }

    @Override
    public int getTotalWordCount() {
        return totalWordCount;
    }

    @Override
    public String getWordInListAt(int position) {
        if (words != null && position >= 0 && position < uniqueWordCount) {
            return words[position].word;
        }
        return null;
    }

    @Override
    public int getWordCountInListAt(int position) {
        if (words != null && position >= 0 && position < uniqueWordCount) {
            return words[position].count;
        }
        return -1;
    }
    public int hashCode(String word) {
        int hash = 0;
		 for (int index = 0; index < word.length(); index++) {
			  hash = 31*hash + word.charAt(index);
		 }
		 return hash;
    }

    private boolean checkFile(String fileName) {
        if (fileName != null) {
            File file = new File(fileName);
            if (file.exists() && !file.isDirectory()) {
                return true;
            }
        }
        return false;
    }

    private int calculateDepth(TreeNode node) {
        if (node == null) {
            return 0;
        }
        int leftDepth = calculateDepth(node.left);
        int rightDepth = calculateDepth(node.right);
        return 1 + Math.max(leftDepth, rightDepth);
    }

}
