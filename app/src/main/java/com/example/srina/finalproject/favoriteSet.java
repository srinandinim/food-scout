package com.example.srina.finalproject;

/**
 * Created by srina on 5/20/2018.
 */

public class favoriteSet {

    int minCalories, minCarbs, minFat, minProtein, maxCalories, maxCarbs, maxFat, maxProtein, mealNum;
    String description;
    String name;

    public favoriteSet(String na, String miCal, String miCar, String miFat, String miPro, String maCal, String maCar, String maFat, String maPro, String meNu, String des){
        name = na;
        minCalories = Integer.parseInt(miCal);
        minCarbs = Integer.parseInt(miCar);
        minFat = Integer.parseInt(miFat);
        minProtein = Integer.parseInt(miPro);
        maxCalories = Integer.parseInt(maCal);
        maxCarbs = Integer.parseInt(maCar);
        maxFat = Integer.parseInt(maFat);
        maxProtein = Integer.parseInt(maPro);
        mealNum = Integer.parseInt(meNu);
        description = des;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMinCalories() {
        return minCalories;
    }

    public void setMinCalories(int minCalories) {
        this.minCalories = minCalories;
    }

    public int getMinCarbs() {
        return minCarbs;
    }

    public void setMinCarbs(int minCarbs) {
        this.minCarbs = minCarbs;
    }

    public int getMinFat() {
        return minFat;
    }

    public void setMinFat(int minFat) {
        this.minFat = minFat;
    }

    public int getMinProtein() {
        return minProtein;
    }

    public void setMinProtein(int minProtein) {
        this.minProtein = minProtein;
    }

    public int getMaxCalories() {
        return maxCalories;
    }

    public void setMaxCalories(int maxCalories) {
        this.maxCalories = maxCalories;
    }

    public int getMaxCarbs() {
        return maxCarbs;
    }

    public void setMaxCarbs(int maxCarbs) {
        this.maxCarbs = maxCarbs;
    }

    public int getMaxFat() {
        return maxFat;
    }

    public void setMaxFat(int maxFat) {
        this.maxFat = maxFat;
    }

    public int getMaxProtein() {
        return maxProtein;
    }

    public void setMaxProtein(int maxProtein) {
        this.maxProtein = maxProtein;
    }

    public int getMealNum() {
        return mealNum;
    }

    public void setMealNum(int mealNum) {
        this.mealNum = mealNum;
    }

    public String getMealValue (){
        String mealValue = "";
        if (mealNum == 1)
            mealValue = "Breakfast";
        if (mealNum == 2)
            mealValue = "Main";
        if (mealNum == 3)
            mealValue = "Dessert";
        return mealValue;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
