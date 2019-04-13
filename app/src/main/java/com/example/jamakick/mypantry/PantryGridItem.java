package com.example.jamakick.mypantry;

public class PantryGridItem {

    //same class as our pantryItem without the unnecessary variables for our gridview

    private int itemID;
    private String itemName;
    private int itemQty;
    private String qtyName;
    private String itemCtg;

    public PantryGridItem (int id, String name, int qty, String qtyN, String ctg) {

        itemID = id;
        itemName = name;
        itemQty = qty;
        qtyName = qtyN;

        itemCtg = ctg;
    }

    public PantryGridItem (String name, int qty, String qtyN, String ctg) {

        itemName = name;
        itemQty = qty;
        qtyName = qtyN;
        itemCtg = ctg;
    }

    public PantryGridItem (String name, String qtyN, String ctg) {

        itemName = name;
        qtyName = qtyN;
        itemCtg = ctg;
    }

    public PantryGridItem (int id, String name, String qtyN, String ctg) {

        itemID = id;
        itemName = name;
        qtyName = qtyN;
        itemCtg = ctg;
    }

    @Override
    //override our to string method to print out a little nicer
    public String toString() {
        String output = "";
        output += itemName + "\n\n" + itemQty + " " + qtyName + "\n\n" + itemCtg;

        return output;
    }

    public int getItemID() {
        return itemID;
    }

    public void setItemID(int itemID) {
        this.itemID = itemID;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getItemQty() {
        return itemQty;
    }

    public void setItemQty(int itemQty) {
        this.itemQty = itemQty;
    }

    public String getQtyName() {
        return qtyName;
    }

    public void setQtyName(String qtyName) {
        this.qtyName = qtyName;
    }

    public String getItemCtg() {
        return itemCtg;
    }

    public void setItemCtg(String itemCtg) {
        this.itemCtg = itemCtg;
    }
}
