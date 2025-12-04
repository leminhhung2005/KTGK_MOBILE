package com.example.ktgk_lap_trinh_di_dong.model;

public class CartItem {
    private int productId;
    private String name;
    private double price;
    private String image;
    private String description;
    private int quantity;

    public CartItem() {}

    public CartItem(int productId, String name, double price, String image, String description, int quantity) {
        this.productId = productId;
        this.name = name;
        this.price = price;
        this.image = image;
        this.description = description;
        this.quantity = quantity;
    }

    // Getters and Setters
    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getTotalPrice() {
        return price * quantity;
    }
}

