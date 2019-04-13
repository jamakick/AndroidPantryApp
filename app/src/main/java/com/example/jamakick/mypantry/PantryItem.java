package com.example.jamakick.mypantry;

public class PantryItem {

    //create our member variables
    private int pitemID;
    private String pitemName;
    private int pitemQty;
    private String pitemQtyName;
    private String pitemDesc;
    private String pitemCtg;

    //create our constructor both with and without id arg
    public PantryItem(int id, String name, int qty, String qtyName, String desc, String ctg) {
        pitemID = id;
        pitemName = name;
        pitemQty = qty;
        pitemQtyName = qtyName;
        pitemDesc = desc;
        pitemCtg = ctg;
    }

    public PantryItem (String name, int qty, String qtyName, String desc, String ctg) {
        pitemName = name;
        pitemQty = qty;
        pitemQtyName = qtyName;
        pitemDesc = desc;
        pitemCtg = ctg;
    }

    @Override
    //override our to string method to print out a little nicer
    public String toString() {
        String output = "";
        output += pitemName + "\n\n" + pitemQty + " " + pitemQtyName + "\n\n" + pitemCtg;

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

    public int getPitemQty() {
        return pitemQty;
    }

    public void setPitemQty(int pitemQty) {
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

    public String getPitemQtyName() {
        return pitemQtyName;
    }

    public void setPitemQtyName(String pitemQtyName) {
        this.pitemQtyName = pitemQtyName;
    }
}
