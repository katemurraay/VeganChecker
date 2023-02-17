package com.kmm.vegancheckerapp.model;

import java.io.Serializable;

public class Product implements Serializable {

    private int productID;


    private String productName;


    private  String availableFrom;
    private String barcode;
    private String productType;
    private String productBrand;
    private String productProduction;
    private String validatedBy;
    private String category;



    private User uploadedBy;
   private String[] productIngredients;


    public Product(int id, String productName, String availableFrom, String barcode, String type, String brand, String[] ingredients, String production, String validatedBy, User uploadedBy, String category){
        this.productID= id;
        this.productName = productName;
        this.availableFrom = availableFrom;
        this.barcode = barcode;
        this.productType = type;
        this.productBrand = brand;
        this.productIngredients = ingredients;
        this.productProduction =production;
        this.validatedBy = validatedBy;
        this.uploadedBy = uploadedBy;
        this.category =category;


    }




    public Product(){
        productID= 0;
        productName ="";
        availableFrom ="";
        barcode = "";
        productType ="";
        productBrand="";
        productIngredients= null;
        productProduction ="";
        validatedBy = "";
        uploadedBy = new User();
        category ="";

    }
    // <editor-fold defaultstate="collapsed" desc="Getters and Setters">
    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductBrand() {
        return productBrand;
    }

    public void setProductBrand(String productBrand) {
        this.productBrand = productBrand;
    }

    public String[] getProductIngredients() {
        return productIngredients;
    }

    public void setProductIngredients(String[] productIngredients) {
       this.productIngredients = productIngredients;
            }

    public String getProductProduction() {
        return productProduction;
    }

    public void setProductProduction(String productProduction) {
        this.productProduction = productProduction;
    }





    public String getAvailableFrom() {
        return availableFrom;
    }

    public void setAvailableFrom(String availableFrom) {
        this.availableFrom = availableFrom;
    }






    public String getValidatedBy() {
        return validatedBy;
    }

    public void setValidatedBy(String validatedBy) {
        this.validatedBy = validatedBy;
    }

    public User getUploadedBy() {
        return uploadedBy;
    }

    public void setUploadedBy(User uploadedBy) {
        this.uploadedBy = uploadedBy;
    }



    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    // </editor-fold>




}





