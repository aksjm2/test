package controller;

import model.Database;
import model.Picture;


public class PictureController {

    public String url;
    public Picture rewardPicture;
    public Database db;

    public PictureController() {
    	url = "";
    	rewardPicture = new Picture();
    	db = new Database();
    }
    public void uploadPicture() {
    	System.out.println("Request upload Picture");
    }
    public void deletePicture() {
    	db.updateRewardToDefault(rewardPicture);
    }
    public void managePicture() {
    	db.selectRewardImageURL(rewardPicture);
    }
    public void selectFileDirectory(String url) {
    	rewardPicture.setImageURL(url);
    	db.updateRewardImage(rewardPicture);
    }
    public Picture getRewardPicture(){
    	return rewardPicture;
    }
}