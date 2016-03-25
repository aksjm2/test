package model;

import java.util.*;

public class Dictionary {

    private Vector<Word> wordArr;
    
    public Dictionary() {
    	wordArr = new Vector<Word>();
    }
    
    public void addWordArr(Word w) {
        wordArr.add(w);
    }
    public Vector<Word> getWordArr() {
        return this.wordArr;
    }
}