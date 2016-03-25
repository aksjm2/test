package controller;

import java.util.*;

import view.MainFrame;
import view.SoundPlayer;
import model.Database;

public class MainController {

	private GameController gamecontrol;
	private DictionaryController dictionarycontrol;
	private PictureController picturecontrol;
	private int state = 0;

	public MainController() {
		gamecontrol = new GameController();
		dictionarycontrol = new DictionaryController();
		picturecontrol = new PictureController();
	}
	
    public void quit() {
    	if(state == 2) {
    		dictionarycontrol.init();
    	}
    	state = 0;
    }

    public void exit() {
    	System.exit(0);
    }
    public GameController getGameController() {
    	state=1;
    	return gamecontrol;
    }	
    public DictionaryController getDictionaryController() {
    	state=2;
    	return dictionarycontrol;
    }
    public PictureController getPictureController() {
    	state=3;
    	return picturecontrol;
    }
    public int getState(){
    	return this.state;
    }

	public static void main(String args[])
	{
		MainFrame mf = new MainFrame();
	}

}