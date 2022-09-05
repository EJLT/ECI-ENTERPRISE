package com.svalero.ECIwebapp.domain;

public class Product {

    private int id;
    private static String name;
    private String category;
    private float price;
    private float existences;


    private String role;


    public Product(int id, String name, String category, float price, float existences) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.existences = existences;
        this.role = role;}

    public Product() {

    }

    public Product(String name, String category, Float price) {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public float getExistences() {
        return existences;
    }

    public void setExistences(float existences) {
        this.existences = existences;
    }
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }


}
