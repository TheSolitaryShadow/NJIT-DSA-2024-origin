package oy.tol.tira.books;




public class WordCount implements Comparable<WordCount>{
    
    String word;
    int count;

    
    public WordCount() { 
        word = "";
        count = 0;
    }

    public WordCount(String word, int count) {
        this.word = word;
        this.count = count;
    }

    @Override
    public int compareTo(WordCount other) {
        return (other.count)-count;
    }
    
    public String getWord() {
        return word;
    }

    public void setWord(String word){
        this.word=word;
    }

    public int getCount() {
        return count;
    }
    public void setCount(int count){
        this.count=count;
    }

    
}
