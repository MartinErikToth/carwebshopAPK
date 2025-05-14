package com.example.autoalkatreszshop;

public class Products {
  private String id;
  private String name;
  private String category;
  private double price;
  private String imageUrl;

  public Products() {}

  public Products(String name, String category, double price, String imageUrl) {
    this.name = name;
    this.category = category;
    this.price = price;
    this.imageUrl = imageUrl;
  }

  public String getId() { return id; }
  public void setId(String id) { this.id = id; }

  public String getName() { return name; }
  public String getCategory() { return category; }
  public double getPrice() { return price; }
  public String getImageUrl() { return imageUrl; }

  public void setName(String name) {
    this.name = name;
  }

  public void setCategory(String category) {
    this.category = category;
  }

  public void setPrice(double price) {
    this.price = price;
  }

  public void setImageUrl(String imageUrl) {
    this.imageUrl = imageUrl;
  }
}
