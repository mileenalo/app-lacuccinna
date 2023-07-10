package com.project.lacuccina.model;

public class Food {
    private int urlImage;
    private String Title;
    private String Desc;
    private int Price;
    private String Id;

    public Food(int urlImage, String title, String desc, int price, String id) {
        this.urlImage = urlImage;
        Title = title;
        Desc = desc;
        Price = price;
        Id = id;
    }

    public int getUrlImage() {
        return urlImage;
    }

    public String getTitle() {
        return Title;
    }

    public String getDesc() {
        return Desc;
    }

    public String getId() {
        return Id;
    }

    public int getPrice() {
        return Price;
    }
}


