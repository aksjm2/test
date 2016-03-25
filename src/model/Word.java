package model;

import java.util.*;


public class Word extends Contents{

    private String name;
    private Vector<Alphabet> arr;
    private int length;
    private int type;
    private Boolean correct;
    private Database db;
    
    public Word() {
    	name = "";
    	arr = new Vector<Alphabet>();
    	length = 0;
    	type = 0;
    	correct = false;
    	db = new Database();
    }
    
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
    	this.name=name;
    }

    public Alphabet getAlphabetIndex(int index) {
    	return this.arr.elementAt(index);
    }

    public int getLength() {
    	return this.length;
    }

    public void setLength(int length) {
    	this.length=length;
    }

    public void setCorrect(Boolean correct) {
    	this.correct=correct;
    }

    public int getType() {
    	return this.type;
    }

    public void setType(int type) {
    	this.type=type;
    }

    public Vector<Alphabet> getAlphabetArr() {
    	return this.arr;
    }

    public void setAlphabetArr() {
    	for(int i=0; i<this.name.length(); i++)
    	{
    		this.appendAlphabet(db.selectGameAlphabet(this.name.charAt(i)));
    	}
    }

    public void appendAlphabet(Alphabet alphabet) {
    	arr.addElement(alphabet);
    }

    public Boolean getCorrect() {
    	return this.correct;
    }
}