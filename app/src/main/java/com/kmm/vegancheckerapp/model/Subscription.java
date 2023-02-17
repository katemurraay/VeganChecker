package com.kmm.vegancheckerapp.model;

public class Subscription {
private int subscriptionID, userID;
private String dateCreated, subscriptionType, status, paymentMethod;
private double totalCost, VAT;

    public Subscription(int subscriptionID, int userID, String dateCreated, String subscriptionType, String status, String paymentMethod, double totalCost, double vat) {
        this.subscriptionID = subscriptionID;
        this.userID = userID;
        this.dateCreated = dateCreated;
        this.subscriptionType = subscriptionType;
        this.status = status;
        this.paymentMethod = paymentMethod;
        this.totalCost = totalCost;
        VAT = vat;
    }


public Subscription(){
        subscriptionID = 0;
        userID = 0;
        dateCreated ="";
        subscriptionType ="";
        status = "";
        paymentMethod="";
        totalCost =0.00;
        VAT = 0.00;
}
    // <editor-fold default state="collapsed" desc="Getters and Setters">
    public int getSubscriptionID() {
        return subscriptionID;
    }

    public void setSubscriptionID(int subscriptionID) {
        this.subscriptionID = subscriptionID;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public double getVAT() {
        return VAT;
    }

    public void setVAT(double VAT) {
        this.VAT = VAT;
    }
    // </editor-fold>
}
