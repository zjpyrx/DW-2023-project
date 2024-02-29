package org.group05.police.DTO;

public class ProductModel {
    public String title;
    public String asin;
    public String myformat;
    public float cost;
    public float score;

    public void setAsin(String asin) {
        this.asin = asin;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public void setMyformat(String myformat) {
        this.myformat = myformat;
    }

    public void setScore(float score) {
        this.score = score;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
