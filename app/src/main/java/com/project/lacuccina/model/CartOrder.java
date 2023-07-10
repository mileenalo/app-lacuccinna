package com.project.lacuccina.model;

public class CartOrder {

    private int urlImage;
    private String Title;
    private String Desc;
    private int Price;
    private String Id;
    private String IdPedido;
    private int Qtd;

    public CartOrder(int urlImage, String title, String desc, int price, String id, int qtd, String idPedido) {
        this.urlImage = urlImage;
        Title = title;
        Desc = desc;
        Price = price;
        Id = id;
        Qtd = qtd;
        IdPedido = idPedido;
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

    public int getQtd() {
        return Qtd;
    }

    public int getPrice() {
        return Price;
    }

    public String getIdpedido() {
        return IdPedido;
    }
}


