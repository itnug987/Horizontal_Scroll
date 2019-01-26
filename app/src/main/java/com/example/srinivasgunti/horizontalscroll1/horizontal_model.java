package com.example.srinivasgunti.horizontalscroll1;

public class horizontal_model {

     private String date;
     private String image;
     private String name;

    public horizontal_model(String date,String image,String name){
        this.date= date;
        this.image=image;
        this.name =name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
