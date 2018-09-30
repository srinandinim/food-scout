package com.example.srina.finalproject;



public class menuItem {

    int id;
    String title;
    String restaurantChain;
    String servingSize;
    String imageLink;

    public menuItem (int id, String title, String restaurantChain, String servingSize, String imageLink){
        this.id = id;
        this.title = title;
        this.restaurantChain = restaurantChain;
        this.servingSize = servingSize;
        this.imageLink = imageLink;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getRestaurantChain() {
        return restaurantChain;
    }

    public void setRestaurantChain(String restaurantChain) {
        this.restaurantChain = restaurantChain;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public String getServingSize() {
        return servingSize;
    }

    public void setServingSize(String servingSize) {
        this.servingSize = servingSize;
    }


}
