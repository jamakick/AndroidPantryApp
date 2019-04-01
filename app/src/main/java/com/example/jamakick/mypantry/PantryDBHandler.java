package com.example.jamakick.mypantry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import java.util.ArrayList;

public class PantryDBHandler extends SQLiteOpenHelper {

    //create all our static variables
    private static final int DATABASE_VERSION = 6;
    private static final String DATABASE_NAME = "pantryDB.db";

    private static final String TABLE_PANTRY = "Pantry";
    private static final String COLUMN_PITEMID = "pitem_id";
    private static final String COLUMN_PITEMNAME = "pitem_name";
    private static final String COLUMN_PITEMQTY = "pitem_qty";
    private static final String COLUMN_PITEMDESC = "pitem_desc";
    private static final String COLUMN_PITEMCTG = "pitem_ctg";

    private static final String TABLE_GROCERY = "Grocery";
    private static final String COLUMN_GITEMID = "gitem_id";
    private static final String COLUMN_GITEMNAME = "gitem_name";
    private static final String COLUMN_GITEMQTY = "gitem_qty";
    private static final String COLUMN_GITEMNOTE = "gitem_note";
    private static final String COLUMN_GITEMCTG = "gitem_ctg";


    //establish our super
    public PantryDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //override our oncreate to create our pantry table
        String CREATE_PANTRY_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_PANTRY + " (" +
                COLUMN_PITEMID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PITEMNAME + " TEXT, " +
                COLUMN_PITEMQTY + " TEXT, " +
                COLUMN_PITEMDESC + " TEXT, " +
                COLUMN_PITEMCTG + " TEXT);";

        db.execSQL(CREATE_PANTRY_TABLE);


        String CREATE_GROCERY_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_GROCERY + " (" +
                COLUMN_GITEMID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_GITEMNAME + " TEXT, " +
                COLUMN_GITEMQTY + " TEXT, " +
                COLUMN_GITEMNOTE + " TEXT, " +
                COLUMN_GITEMCTG + " TEXT);";

        db.execSQL(CREATE_GROCERY_TABLE);

//        //EXAMPLE INSERTS TO POPULATE A FEW ITEMS ON LOAD
//        String sqlQuery = "INSERT INTO Pantry (pitem_name, pitem_qty, pitem_desc, pitem_ctg) VALUES (" +
//                "'Potatoes', '5 pounds', 'They are potatoes', 'Vegetables'), (" +
//                "'Apples', '2 dozen', 'Using for apple pies', 'Fruits')";
//
//        db.execSQL(sqlQuery);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //on upgrade we replace our table with a new one
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PANTRY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROCERY);

        onCreate(db);
    }


    public void addPantryItem(PantryItem item) {
//        ContentValues values = new ContentValues();
//        values.put(COLUMN_PITEMNAME, item.getPitemName());
//        values.put(COLUMN_PITEMQTY, item.getPitemQty());
//        values.put(COLUMN_PITEMDESC, item.getPitemDesc());
//        values.put(COLUMN_PITEMCTG, item.getPitemCtg());
//
//        SQLiteDatabase db = this.getWritableDatabase();
//        db.insert(TABLE_PANTRY, null, values);

        //insert our values for our pantry item into the pantry table

        String sqlQuery = "INSERT INTO Pantry (pitem_name, pitem_qty, pitem_desc, pitem_ctg) VALUES (\"" +
                item.getPitemName() + "\", \"" + item.getPitemQty() + "\", \"" + item.getPitemDesc()
                + "\", \"" + item.getPitemCtg() + "\")";

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sqlQuery);
        db.close();
    }

    public void addGroceryItem(PantryItem item) {

        String sqlQuery = "INSERT INTO Grocery (gitem_name, gitem_qty, gitem_note, gitem_ctg) VALUES (\"" +
                item.getPitemName() + "\", \"" + item.getPitemQty() + "\", \"" + item.getPitemDesc()
                + "\", \"" + item.getPitemCtg() + "\")";

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sqlQuery);
        db.close();
    }

    public ArrayList<PantryItem> getItems() {

        //get all of our pantry items, store them in an arraylist of pantryitems
        //and return it to the user

        ArrayList<PantryItem> items = new ArrayList<>();


        String sqlQuery = "SELECT * FROM " + TABLE_PANTRY;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor myCursor = db.rawQuery(sqlQuery, null);

        PantryItem myItem = null;

        //we do a select all on the table, and then create items and add them to the arraylist
        if (myCursor.moveToFirst()) {
            while (!myCursor.isAfterLast()) {
                int tmpID = myCursor.getInt(0);
                String tmpName = myCursor.getString(1);
                String tmpQty = myCursor.getString(2);
                String tmpDesc = myCursor.getString(3);
                String tmpCtg = myCursor.getString(4);
                myItem = new PantryItem(tmpID, tmpName, tmpQty, tmpDesc, tmpCtg);

                items.add(myItem);

                //moves to the next row
                myCursor.moveToNext();
            }
        }
        myCursor.close();

        return items;
    }

    public ArrayList<PantryItem> getGroceryItems() {

        //get all of our pantry items, store them in an arraylist of pantryitems
        //and return it to the user

        ArrayList<PantryItem> items = new ArrayList<>();


        String sqlQuery = "SELECT * FROM " + TABLE_GROCERY;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor myCursor = db.rawQuery(sqlQuery, null);

        PantryItem myItem = null;

        //we do a select all on the table, and then create items and add them to the arraylist
        if (myCursor.moveToFirst()) {
            while (!myCursor.isAfterLast()) {
                int tmpID = myCursor.getInt(0);
                String tmpName = myCursor.getString(1);
                String tmpQty = myCursor.getString(2);
                String tmpDesc = myCursor.getString(3);
                String tmpCtg = myCursor.getString(4);
                myItem = new PantryItem(tmpID, tmpName, tmpQty, tmpDesc, tmpCtg);

                items.add(myItem);

                //moves to the next row
                myCursor.moveToNext();
            }
        }
        myCursor.close();

        return items;
    }

    public PantryItem findPantryItem(int id) {
        //here we find an item in our pantry based on the id given and return it
        String sqlQuery = "SELECT * FROM " + TABLE_PANTRY +
                " WHERE " + COLUMN_PITEMID + " = " +
                id;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor myCursor = db.rawQuery(sqlQuery, null);

        PantryItem myItem = null;

        if (myCursor.moveToFirst()) {
            int tmpID = myCursor.getInt(0);
            String tmpName = myCursor.getString(1);
            String tmpQty = myCursor.getString(2);
            String tmpDesc = myCursor.getString(3);
            String tmpCtg = myCursor.getString(4);

            myCursor.close();
            myItem = new PantryItem(tmpID, tmpName, tmpQty, tmpDesc, tmpCtg);
        }

        db.close();

        return myItem;
    }

    public PantryItem findGroceryItem(int id) {
        //here we find an item in our pantry based on the id given and return it
        String sqlQuery = "SELECT * FROM " + TABLE_GROCERY +
                " WHERE " + COLUMN_GITEMID + " = " +
                id;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor myCursor = db.rawQuery(sqlQuery, null);

        PantryItem myItem = null;

        if (myCursor.moveToFirst()) {
            int tmpID = myCursor.getInt(0);
            String tmpName = myCursor.getString(1);
            String tmpQty = myCursor.getString(2);
            String tmpDesc = myCursor.getString(3);
            String tmpCtg = myCursor.getString(4);

            myCursor.close();
            myItem = new PantryItem(tmpID, tmpName, tmpQty, tmpDesc, tmpCtg);
        }

        db.close();

        return myItem;
    }

    public void deleteItem(int id) {

        //this will delete an item from our pantry table based on its id

        String sqlQuery = "DELETE FROM Pantry WHERE pitem_id = " + id;

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL(sqlQuery);

        db.close();
    }

    public void deleteGroceryItem(int id) {

        //this will delete an item from our pantry table based on its id

        String sqlQuery = "DELETE FROM Grocery WHERE gitem_id = " + id;

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL(sqlQuery);

        db.close();
    }


}