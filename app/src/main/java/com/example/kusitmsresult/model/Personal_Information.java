package com.example.kusitmsresult.model;

public class Personal_Information {
    public String route;
    public String date;
    public String time;
    public String comment;
    public String rate;
    public String state;
    public String image;
    public String LikeButton;


    public Personal_Information() {

    }
    public Personal_Information(String image, String route, String rate, String comment, String likeButton) {
        this.image = image;
        this.route = route;
        this.rate = rate;
        this.comment = comment;
        this.LikeButton =likeButton;
    }


    public String getImage() {
        return image;
    }

}
