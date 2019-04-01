package com.example.jamakick.mypantry;

import java.util.ArrayList;

public class MealItem {

    private int mealID;
    private String mealName;
    private String mealTime;
    private ArrayList<String> mealIngredients;
    private String mealRecipe;
    private String mealNote;
    private String mealVidLink;

    public MealItem(int id, String name, String time, ArrayList<String> ing,
                    String recipe, String note, String link) {
        mealID = id;
        mealTime = time;
        mealName = name;
        mealIngredients = ing;
        mealRecipe = recipe;
        mealNote = note;
        mealVidLink = link;

    }

    public MealItem(String name, String time, ArrayList<String> ing,
                    String recipe, String note, String link) {
        mealName = name;
        mealTime = time;
        mealIngredients = ing;
        mealRecipe = recipe;
        mealNote = note;
        mealVidLink = link;

    }

    @Override
    public String toString() {
        String output = "";
        output += mealID + " " + mealName + "\n\n" + mealTime;

        return output;
    }


    public ArrayList<String> getMealIngredients() {
        return mealIngredients;
    }

    public void setMealIngredients(ArrayList<String> mealIngredients) {
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
