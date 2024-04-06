package oy.tol.tira.books;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Locale;

class HashTableBookImplementation implements Book {

    private static final int MAX_WORDS = 100000;
    private static final int MAX_WORD_LEN = 100;
    private WordCount[] words = null;
    private String bookFile = null;
    private String wordsToIgnoreFile = null;
    private WordFilter filter = null;
    private int uniqueWordCount = 0;
    private int totalWordCount = 0;
    private int ignoredWordsTotal = 0;
    private long loopCount = 0;
    private int collisionCount = 0;
    private int reallocationCount = 0;
    private static final double LOAD_FACTOR = 0.45;



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
        if (words == null) {
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
        System.out.println("Hash table had " + collisionCount + " collisions when filling the hash table.");
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
        // Create an array for the words.
        words = new WordCount[MAX_WORDS];
        for (int index = 0; index < MAX_WORDS; index++) {
            words[index] = new WordCount();
        }
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
            boolean handled = false;
            if (((double)uniqueWordCount * (1.0 + LOAD_FACTOR)) >= words.length) {
                reallocate((int)((double)(words.length) * (1.0 / LOAD_FACTOR)));
            }
            int hashcode=hashCode(wordCount.word);
            int index=hashcode%words.length;
            if(index<0){
                index+=words.length;
            }
           for(int i=0;i<words.length&& !handled;i++){
                loopCount++;
                if(words[(index+i)%words.length].word==""){
                    words[(index+i)%words.length]=wordCount;
                    uniqueWordCount += 1;
                    totalWordCount += 1;
                    handled = true;
                    if (uniqueWordCount >= MAX_WORDS) {
                        throw new OutOfMemoryError("No room for more words in array");
                    }
                    if(i!=0){
                        collisionCount+=i;
                    }
                    break;
                }
                else if(words[(index+i)%words.length].word.equals(wordCount.word)){
                    words[(index+i)%words.length].count++;
                    totalWordCount++;
                    handled = true;
                    break;
                }
            }
            if (!handled) {
                throw new OutOfMemoryError("No room for more words in array");
            }
        } 
        else {
            ignoredWordsTotal++;
        }
    }


    public void sortWords() {
        int length=partitionByRule(words, words.length);
        Arrayreallocate(length);
        Algorithms.fastSort(words);


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



    private void reallocate(int newSize) throws OutOfMemoryError {
        if (newSize < MAX_WORDS) {
            newSize = MAX_WORDS;
        }
        reallocationCount++;
        WordCount[] oldWords = words;
        this.words = new WordCount[(int)((double)newSize * (1.0 + LOAD_FACTOR))];
        for (int index = 0; index < (int)((double)newSize * (1.0 + LOAD_FACTOR)); index++) {
            words[index] = new WordCount();
        }
        uniqueWordCount = 0;
        collisionCount = 0;
        for (int index = 0; index < oldWords.length; index++) {
            if (oldWords[index].word != "") {
                totalWordCount--;
                addToWords(oldWords[index]);
            }
        }
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

    
 
}
