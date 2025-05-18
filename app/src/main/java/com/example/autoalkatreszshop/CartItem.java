package com.example.autoalkatreszshop;

public class CartItem {
  private String productName;
  private String productCategory;
  private double productPrice;
  private String productImageUrl;

  public CartItem(String productName, String productCategory, double productPrice, String productImageUrl) {
    this.productName = productName;
    this.productCategory = productCategory;
    this.productPrice = productPrice;
    this.productImageUrl = productImageUrl;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public String getProductCategory() {
    return productCategory;
  }

  public void setProductCategory(String productCategory) {
    this.productCategory = productCategory;
  }

  public double getProductPrice() {
    return productPrice;
  }

  public void setProductPrice(double productPrice) {
    this.productPrice = productPrice;
  }

  public String getProductImageUrl() {
    return productImageUrl;
  }

  public void setProductImageUrl(String productImageUrl) {
    this.productImageUrl = productImageUrl;
  }
}
