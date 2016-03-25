package model;

public class Contents {

    private String soundURL;
    private String imageURL;

    public Contents() {
    }

    public String getSoundURL() {
        return this.soundURL;
    }

    public void setSoundURL(String url) {
        this.soundURL = url;
    }

    public String getImageURL() {
        return this.imageURL;
    }

    public void setImageURL(String url) {
        this.imageURL = url;
    }
}