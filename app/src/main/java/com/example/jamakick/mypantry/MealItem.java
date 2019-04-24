package com.example.jamakick.mypantry;

import java.util.ArrayList;

public class MealItem {

    //create mealitem class to hold the information for an individual meal
    private int mealID;
    private String mealName;
    private String mealTime;
    private ArrayList<PantryItem> mealIngredients;
    private String mealRecipe;
    private String mealNote;
    private String mealVidLink;


    public MealItem () {

    }
    //constructor with and without an id
    public MealItem(int id, String name, String time, ArrayList<PantryItem> ing,
                    String recipe, String note, String link) {
        mealID = id;
        mealTime = time;
        mealName = name;
        mealIngredients = ing;
        mealRecipe = recipe;
        mealNote = note;
        mealVidLink = link;

    }

    public MealItem(String name, String time, ArrayList<PantryItem> ing,
                    String recipe, String note, String link) {
        mealName = name;
        mealTime = time;
        mealIngredients = ing;
        mealRecipe = recipe;
        mealNote = note;
        mealVidLink = link;

    }

    //override our to string method
    @Override
    public String toString() {
        String output = "";
        output += mealName + "\n\n" + mealTime;

        return output;
    }

    //getters and setters
    public ArrayList<PantryItem> getMealIngredients() {
        return mealIngredients;
    }

    public String printMealIngredients() {
        String returnString = "";
        int i;
        for (i = 0; i < mealIngredients.size(); i++) {
            returnString += mealIngredients.get(i).getPitemName();
            returnString += ", " + mealIngredients.get(i).getPitemQty();
            returnString += " " + mealIngredients.get(i).getPitemQtyName();
            returnString += ", " + mealIngredients.get(i).getPitemCtg();
        }

        return returnString;
    }

    public void setMealIngredients(ArrayList<PantryItem> mealIngredients) {
        this.mealIngredients = mealIngredients;
    }

    public String getMealRecipe() {
        return mealRecipe;
    }

    public void setMealRecipe(String mealRecipe) {
        this.mealRecipe = mealRecipe;
    }

    public String getMealNote() {
        return mealNote;
    }

    public void setMealNote(String mealNote) {
        this.mealNote = mealNote;
    }

    public String getMealVidLink() {
        return mealVidLink;
    }

    public void setMealVidLink(String mealVidLink) {
        this.mealVidLink = mealVidLink;
    }

    public String getMealTime() {
        return mealTime;
    }

    public void setMealTime(String mealTime) {
        this.mealTime = mealTime;
    }

    public String getMealName() {
        return mealName;
    }

    public void setMealName(String mealName) {
        this.mealName = mealName;
    }

    public int getMealID() {
        return mealID;
    }

    public void setMealID(int mealID) {
        this.mealID = mealID;
    }
}
