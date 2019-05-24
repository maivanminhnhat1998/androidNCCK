package com.example.administrator.androidncck;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.PropertyName;

import java.io.Serializable;

public class ProductModel implements Serializable {
    @Exclude
    private String TextId;
    private int id;
    private String product_name;
    private String producer;
    private long price;
    private  String description;

    public ProductModel(){

    }
    public ProductModel(int id, String product_name, String producer, long price, String mieuTa) {
        this.id = id;
        this.product_name = product_name;
        this.producer = producer;
        this.price = price;
        this.description = mieuTa;
    }

    public String getTextId() {
        return TextId;
    }

    public void setTextId(String textId) {
        TextId = textId;
    }
    @PropertyName("id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @PropertyName("product_name")
    public String getProduct_name() {
        return product_name;
    }

    public void setProduct_name(String product_name) {
        this.product_name = product_name;
    }
    @PropertyName("producer")
    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public long getPrice() {
        return price;
    }

    public void setPrice(long price) {
        this.price = price;
    }
    @PropertyName("description")

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
