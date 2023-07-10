package com.project.lacuccina.model;

public class Orders {

    private String OrderId;
    private String Status;
    private int Price;
    private int Qtd;

    public Orders(String orderId, String status, int qtd, int price) {
        OrderId = orderId;
        Status = status;
        Price = price;
        Qtd = qtd;
    }

    public String getOrderId() {
        return OrderId;
    }

    public String getStatus() {
        return Status;
    }

    public int getPrice() {
        return Price;
    }

    public int getQtd() {
        return Qtd;
    }
}


