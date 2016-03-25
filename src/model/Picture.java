package model;

import java.util.*;

public class Picture extends Contents{

    public Picture() {
    }

    public void setDefaultImageURL() {
    	this.setImageURL("image/reward/default.jpg");
    }
    public void setRewardImageURL(String url) {
    	this.setImageURL(url);
    }
}