package controller;

import java.util.*;

import model.Database;
import model.Dictionary;
import model.Word;

public class DictionaryController {

    private Word word;
    private Dictionary dic;
    private Database db;
    
    public DictionaryController() {
    	word = new Word();
    	dic = new Dictionary();
    	db = new Database();
    }
    
    public Word getWord() {
    	return word;
    }
    public Dictionary getDictionary() {
    	return dic;
    }
    public void searchbyInitial(char alphabet) {
        word = db.selectRandomWordbyAlphabet(alphabet);
    }

    public void searchbyFullWord(String userTypedWord) {
       word = db.selectWordbyText(userTypedWord);
    }

    public void search(String text) {
        if(text.length() == 1){
        	char alphabet = text.charAt(0);
        	this.searchbyInitial(alphabet);
        } else if(text.length() > 1){
        	String userTypedWord = text;
        	this.searchbyFullWord(userTypedWord);
        }
    }
    public void dictionaryOpen() {
    	db.initializeDictionary(dic);
    }

    public void init() {
    	dic.getWordArr().clear();
    }

}