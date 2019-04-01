package com.example.jamakick.mypantry;

public class PantryItem {

    //create our member variables
    private int pitemID;
    private String pitemName;
    private String pitemQty;
    private String pitemDesc;
    private String pitemCtg;

    //create our constructor both with and without id arg
    public PantryItem(int id, String name, String qty, String desc, String ctg) {
        pitemID = id;
        pitemName = name;
        pitemQty = qty;
        pitemDesc = desc;
        pitemCtg = ctg;
    }

    public PantryItem (String name, String qty, String desc, String ctg) {
        pitemName = name;
        pitemQty = qty;
        pitemDesc = desc;
        pitemCtg = ctg;
    }

    @Override
    //override our to string method to print out a little nicer
    public String toString() {
        String output = "";
        output += pitemID + " " + pitemName + "\n\n" + pitemQty + "\n\n" + pitemCtg;

        return output;
    }

    //getters and setters for our variables
    public int getPitemID() {
        return pitemID;
    }

    public void setPitemID(int pitemID) {
        this.pitemID = pitemID;
    }

    public String getPitemName() {
        return pitemName;
    }

    public void setPitemName(String pitemName) {
        this.pitemName = pitemName;
    }

    public String getPitemQty() {
        return pitemQty;
    }

    public void setPitemQty(String pitemQty) {
        this.pitemQty = pitemQty;
    }

    public String getPitemDesc() {
        return pitemDesc;
    }

    public void setPitemDesc(String pitemDesc) {
        this.pitemDesc = pitemDesc;
    }

    public String getPitemCtg() {
        return pitemCtg;
    }

    public void setPitemCtg(String pitemCtg) {
        this.pitemCtg = pitemCtg;
    }
}
