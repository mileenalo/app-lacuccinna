package com.project.lacuccina.model;

public class ViewOrder {

    private int urlImage;
    private String Title;
    private String Desc;
    private int Price;
    private String Id;
    private String IdPedido;
    String ObsItem;
    private int Qtd;

    public ViewOrder(int urlImage, String title, String desc, int price, String id, int qtd, String idPedido, String obsItem) {
        this.urlImage = urlImage;
        Title = title;
        Desc = desc;
        Price = price;
        Id = id;
        Qtd = qtd;
        IdPedido = idPedido;
        ObsItem = obsItem;
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

    public String getObsItem() {
        return ObsItem;
    }
}


