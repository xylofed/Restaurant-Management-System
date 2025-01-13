package org.example;

public class Kategorie {

    private String productId;
    private String name;
    private String type;
    private Double price;
    private String status;

    public Kategorie(String productId, String name, String type, Double price, String status) {
        this.productId = productId;
        this.name = name;
        this.type = type;
        this.price = price;
        this.status = status;
    }
    public String getProductId() {
        return productId;
    }
    public String getName() {
        return name;
    }
    public Double getPrice() {
        return price;
    }
    public String getType() {
        return type;
    }
    public String getStatus() {
        return status;
    }
}
