package model;

public class Alphabet extends Contents {

    private char name;
    private Boolean correct;

    public Alphabet() {
    	name =0;
    	correct = false;
    }

    public char getName() {
        return name;
    }
    public void setName(char name) {
        this.name = name;
    }
    
    public Boolean getCorrect(){
    	return this.correct;
    }
    
    public void setCorrect(Boolean correct) {
        this.correct = correct;
    }

}