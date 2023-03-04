package com.example.myfirebaserecycler;

public class productModal {
    private String name,productImage,  description, price;
    public productModal() {
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public productModal(String name, String productImage, String description, String price) {
        this.name = name;
        this.productImage = productImage;
        this.description = description;
        this.price = price;
    }

    public String getName() {
        return name;
    }

}
