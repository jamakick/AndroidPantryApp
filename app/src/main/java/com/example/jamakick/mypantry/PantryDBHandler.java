package com.example.jamakick.mypantry;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class PantryDBHandler extends SQLiteOpenHelper {

    //create all our static variables
    private static final int DATABASE_VERSION = 16;
    private static final String DATABASE_NAME = "pantryDB.db";

    private static final String TABLE_PANTRY = "Pantry";
    private static final String COLUMN_PITEMID = "pitem_id";
    private static final String COLUMN_PITEMNAME = "pitem_name";
    private static final String COLUMN_PITEMQTY = "pitem_qty";
    private static final String COLUMN_PITEMQTYNAME = "pitem_qtyName";
    private static final String COLUMN_PITEMDESC = "pitem_desc";
    private static final String COLUMN_PITEMCTG = "pitem_ctg";

    private static final String TABLE_GROCERY = "Grocery";
    private static final String COLUMN_GITEMID = "gitem_id";
    private static final String COLUMN_GITEMNAME = "gitem_name";
    private static final String COLUMN_GITEMQTY = "gitem_qty";
    private static final String COLUMN_GITEMQTYNAME = "gitem_qtyName";
    private static final String COLUMN_GITEMNOTE = "gitem_note";
    private static final String COLUMN_GITEMCTG = "gitem_ctg";

    private static final String TABLE_MEAL_ING = "MealIng";
    private static final String COLUMN_MEITEMID = "meitem_id";
    private static final String COLUMN_MEITEMNAME = "meitem_name";
    private static final String COLUMN_MEITEMQTY = "meitem_qty";
    private static final String COLUMN_MEITEMQTYNAME = "meitem_qtyName";
    private static final String COLUMN_MEITEMNOTE = "meitem_note";
    private static final String COLUMN_MEITEMCTG = "meitem_ctg";

    private static final String TABLE_MEAL = "Meal";
    private static final String COLUMN_MID = "mid";
    private static final String COLUMN_MNAME = "mname";
    private static final String COLUMN_MTIME = "mtime";
    private static final String COLUMN_MING = "ming";
    private static final String COLUMN_MRECIPE = "mrecipe";
    private static final String COLUMN_MNOTE = "mnote";
    private static final String COLUMN_MLINK = "mlink";


    //establish our super
    public PantryDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //override our oncreate to create our pantry, meal, and grocery tables
        String CREATE_PANTRY_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_PANTRY + " (" +
                COLUMN_PITEMID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PITEMNAME + " TEXT, " +
                COLUMN_PITEMQTY + " TEXT, " +
                COLUMN_PITEMQTYNAME + " TEXT, " +
                COLUMN_PITEMDESC + " TEXT, " +
                COLUMN_PITEMCTG + " TEXT);";

        db.execSQL(CREATE_PANTRY_TABLE);


        String CREATE_GROCERY_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_GROCERY + " (" +
                COLUMN_GITEMID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_GITEMNAME + " TEXT, " +
                COLUMN_GITEMQTY + " TEXT, " +
                COLUMN_GITEMQTYNAME + " TEXT, " +
                COLUMN_GITEMNOTE + " TEXT, " +
                COLUMN_GITEMCTG + " TEXT);";

        db.execSQL(CREATE_GROCERY_TABLE);

        String CREATE_MEAL_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_MEAL + " (" +
                COLUMN_MID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MNAME + " TEXT, " +
                COLUMN_MTIME + " TEXT, " +
                COLUMN_MING + " TEXT, " +
                COLUMN_MRECIPE + " TEXT, " +
                COLUMN_MNOTE + " TEXT, " +
                COLUMN_MLINK + " TEXT);";

        db.execSQL(CREATE_MEAL_TABLE);

        String CREATE_MEAL_ING_TABLE = "CREATE TABLE IF NOT EXISTS " +
                TABLE_MEAL_ING + " (" +
                COLUMN_MEITEMID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_MEITEMNAME + " TEXT, " +
                COLUMN_MEITEMQTY + " TEXT, " +
                COLUMN_MEITEMQTYNAME + " TEXT, " +
                COLUMN_MEITEMNOTE + " TEXT, " +
                COLUMN_MEITEMCTG + " TEXT);";

        db.execSQL(CREATE_MEAL_ING_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //on upgrade we replace our tables with new ones
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PANTRY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_GROCERY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEAL);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEAL_ING);

        onCreate(db);
    }


    public void addPantryItem(PantryItem item) {

        PantryItem oldItem = new PantryItem();

        try {
            oldItem = findPantryItem(item.getPitemName(), item.getPitemQtyName(), item.getPitemCtg());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (oldItem != null) {

            int newQty = oldItem.getPitemQty() + item.getPitemQty();
            String newNote = oldItem.getPitemDesc() + "\n" + item.getPitemDesc();

            item.setPitemDesc(newNote);
            item.setPitemQty(newQty);

            ContentValues values = new ContentValues();

            SQLiteDatabase db = this.getWritableDatabase();

            values.put(COLUMN_PITEMID, item.getPitemID());
            values.put(COLUMN_PITEMNAME, item.getPitemName());
            values.put(COLUMN_PITEMQTY, item.getPitemQty());
            values.put(COLUMN_PITEMQTYNAME, item.getPitemQtyName());
            values.put(COLUMN_PITEMDESC, item.getPitemDesc());
            values.put(COLUMN_PITEMCTG, item.getPitemCtg());

            String whereClause = COLUMN_PITEMNAME + " = ? AND " + COLUMN_PITEMQTYNAME + " = ? AND "
                    + COLUMN_PITEMCTG + " = ?";
            String[] whereArgs = new String[] {item.getPitemName(), item.getPitemQtyName(),
                    item.getPitemCtg()};

            db.update(TABLE_PANTRY, values, whereClause, whereArgs);


        }

        else {

            String sqlQuery = "INSERT INTO Pantry (pitem_name, pitem_qty, pitem_qtyName, pitem_desc, pitem_ctg) VALUES (\"" +
                    item.getPitemName() + "\", \"" + item.getPitemQty() + "\", \"" + item.getPitemQtyName() + "\", \"" + item.getPitemDesc()
                    + "\", \"" + item.getPitemCtg() + "\")";

            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(sqlQuery);

        }
    }

    public void updatePantryItem(PantryItem item) {

        PantryItem oldItem = new PantryItem();

        try {
            oldItem = findPantryItem(item.getPitemName(), item.getPitemQtyName(), item.getPitemCtg());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (oldItem != null) {

            ContentValues values = new ContentValues();

            SQLiteDatabase db = this.getWritableDatabase();

            values.put(COLUMN_PITEMID, item.getPitemID());
            values.put(COLUMN_PITEMNAME, item.getPitemName());
            values.put(COLUMN_PITEMQTY, item.getPitemQty());
            values.put(COLUMN_PITEMQTYNAME, item.getPitemQtyName());
            values.put(COLUMN_PITEMDESC, item.getPitemDesc());
            values.put(COLUMN_PITEMCTG, item.getPitemCtg());

            String whereClause = COLUMN_PITEMNAME + " = ? AND " + COLUMN_PITEMQTYNAME + " = ? AND "
                    + COLUMN_PITEMCTG + " = ?";
            String[] whereArgs = new String[] {item.getPitemName(), item.getPitemQtyName(),
                    item.getPitemCtg()};

            db.update(TABLE_PANTRY, values, whereClause, whereArgs);


        }
    }

    public void updateGroceryItem(PantryItem item) {

        PantryItem oldItem = new PantryItem();

        try {
            oldItem = findPantryItem(item.getPitemName(), item.getPitemQtyName(), item.getPitemCtg());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (oldItem != null) {

            ContentValues values = new ContentValues();

            SQLiteDatabase db = this.getWritableDatabase();

            values.put(COLUMN_GITEMID, item.getPitemID());
            values.put(COLUMN_GITEMNAME, item.getPitemName());
            values.put(COLUMN_GITEMQTY, item.getPitemQty());
            values.put(COLUMN_GITEMQTYNAME, item.getPitemQtyName());
            values.put(COLUMN_GITEMNOTE, item.getPitemDesc());
            values.put(COLUMN_GITEMCTG, item.getPitemCtg());

            String whereClause = COLUMN_GITEMNAME + " = ? AND " + COLUMN_GITEMQTYNAME + " = ? AND "
                    + COLUMN_GITEMCTG + " = ?";
            String[] whereArgs = new String[] {item.getPitemName(), item.getPitemQtyName(),
                    item.getPitemCtg()};

            db.update(TABLE_GROCERY, values, whereClause, whereArgs);


        }
    }

    public void updateMeal(MealItem item) {

        SQLiteDatabase db = this.getWritableDatabase();


        MealItem oldItem = new MealItem();

        try {
            oldItem = findMeal(item.getMealID());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (oldItem != null) {

            ContentValues values = new ContentValues();

            ArrayList<PantryItem> ings = item.getMealIngredients();

            String insertIngs = "";

            int i;


            for (i = 0; i < ings.size(); i++) {
                PantryItem addItem = ings.get(i);
                long id = addMealIng(addItem);
                PantryItem newItem = null;
                newItem = findMealIng(Math.toIntExact(id));
                insertIngs += newItem.getPitemID() + ",";
            }

            values.put(COLUMN_MNAME, item.getMealName());
            values.put(COLUMN_MTIME, item.getMealTime());
            values.put(COLUMN_MING, insertIngs);
            values.put(COLUMN_MRECIPE, item.getMealRecipe());
            values.put(COLUMN_MNOTE, item.getMealNote());
            values.put(COLUMN_MLINK, item.getMealVidLink());

            String whereClause = COLUMN_MID + " = ?";
            String[] whereArgs = new String[] {Integer.toString(item.getMealID())};

            db.update(TABLE_MEAL, values, whereClause, whereArgs);

        }
    }


    public void addGroceryItem(PantryItem item) {

        PantryItem oldItem = new PantryItem();

        try {
            oldItem = findGroceryItem(item.getPitemName(), item.getPitemQtyName(), item.getPitemCtg());
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (oldItem != null) {

            int newQty = oldItem.getPitemQty() + item.getPitemQty();
            String newNote = oldItem.getPitemDesc() + "\n" + item.getPitemDesc();

            item.setPitemDesc(newNote);
            item.setPitemQty(newQty);

            ContentValues values = new ContentValues();

            SQLiteDatabase db = this.getWritableDatabase();

            values.put(COLUMN_GITEMID, item.getPitemID());
            values.put(COLUMN_GITEMNAME, item.getPitemName());
            values.put(COLUMN_GITEMQTY, item.getPitemQty());
            values.put(COLUMN_GITEMQTYNAME, item.getPitemQtyName());
            values.put(COLUMN_GITEMNOTE, item.getPitemDesc());
            values.put(COLUMN_GITEMCTG, item.getPitemCtg());

            String whereClause = COLUMN_GITEMNAME + " = ? AND " + COLUMN_GITEMQTYNAME + " = ? AND "
                    + COLUMN_GITEMCTG + " = ?";
            String[] whereArgs = new String[] {item.getPitemName(), item.getPitemQtyName(),
            item.getPitemCtg()};

            db.update(TABLE_GROCERY, values, whereClause, whereArgs);

        }

        else {

            String sqlQuery = "INSERT INTO Grocery (gitem_name, gitem_qty, gitem_qtyName, gitem_note, gitem_ctg) VALUES (\"" +
                    item.getPitemName() + "\", \"" + item.getPitemQty() + "\", \"" + item.getPitemQtyName() + "\", \"" + item.getPitemDesc()
                    + "\", \"" + item.getPitemCtg() + "\")";

            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(sqlQuery);

        }
    }

    public long addMealIng(PantryItem item) {

        ContentValues values = new ContentValues();

        SQLiteDatabase db = this.getWritableDatabase();

        values.put(COLUMN_MEITEMNAME, item.getPitemName());
        values.put(COLUMN_MEITEMQTY, item.getPitemQty());
        values.put(COLUMN_MEITEMQTYNAME, item.getPitemQtyName());
        values.put(COLUMN_MEITEMNOTE, item.getPitemDesc());
        values.put(COLUMN_MEITEMCTG, item.getPitemCtg());

        long id = db.insert(TABLE_MEAL_ING, null, values);

        return id;

    }

    public void addMeal(MealItem meal, boolean checked) {

        ArrayList<PantryItem> ings = meal.getMealIngredients();

        String insertIngs = "";

        int i;


        for (i = 0; i < ings.size(); i++) {
            PantryItem addItem = ings.get(i);
            long id = addMealIng(addItem);
            PantryItem newItem = null;
            newItem = findMealIng(Math.toIntExact(id));
            insertIngs += newItem.getPitemID() + ",";

            if (checked) {
                addGroceryItem(addItem);
            }
        }

        //insert a meal item

        String sqlQuery = "INSERT INTO Meal (mname, mtime, ming, mrecipe, mnote, mlink) VALUES (\"" +
                meal.getMealName() + "\", \"" + meal.getMealTime() + "\", \""
                + insertIngs + "\", \"" + meal.getMealRecipe() + "\", \"" + meal.getMealNote()
                + "\", \"" + meal.getMealVidLink() + "\")";

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(sqlQuery);
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
                String tmpQtyName = myCursor.getString(3);
                String tmpDesc = myCursor.getString(4);
                String tmpCtg = myCursor.getString(5);
                myItem = new PantryItem(tmpID, tmpName, Integer.parseInt(tmpQty), tmpQtyName, tmpDesc, tmpCtg);

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
                String tmpQtyName = myCursor.getString(3);
                String tmpDesc = myCursor.getString(4);
                String tmpCtg = myCursor.getString(5);
                myItem = new PantryItem(tmpID, tmpName, Integer.parseInt(tmpQty), tmpQtyName, tmpDesc, tmpCtg);

                items.add(myItem);

                //moves to the next row
                myCursor.moveToNext();
            }
        }
        myCursor.close();

        return items;
    }

    public ArrayList<MealItem> getMeals() {

        //get all of our meal items, store them in an arraylist of mealitems
        //and return it to the user

        ArrayList<MealItem> items = new ArrayList<>();


        String sqlQuery = "SELECT * FROM " + TABLE_MEAL;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor myCursor = db.rawQuery(sqlQuery, null);

        MealItem myItem = null;

        //we do a select all on the table, and then create items and add them to the arraylist
        if (myCursor.moveToFirst()) {
            while (!myCursor.isAfterLast()) {
                int tmpID = myCursor.getInt(0);
                String tmpName = myCursor.getString(1);
                String tmpTime = myCursor.getString(2);
                String tmpIng = myCursor.getString(3);

                String[] ings = tmpIng.split(",");
                ArrayList<PantryItem> pantryItems = new ArrayList<>();

                int i;

                for (i = 0; i < ings.length; i++) {
                    pantryItems.add(findMealIng(i));
                }

                String tmpRecipe = myCursor.getString(4);
                String tmpNote = myCursor.getString(5);
                String tmpLink = myCursor.getString(6);


                myItem = new MealItem(tmpID, tmpName, tmpTime, pantryItems,
                        tmpRecipe, tmpNote, tmpLink);

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
            String tmpQtyName = myCursor.getString(3);
            String tmpDesc = myCursor.getString(4);
            String tmpCtg = myCursor.getString(5);

            myCursor.close();
            myItem = new PantryItem(tmpID, tmpName, Integer.parseInt(tmpQty), tmpQtyName, tmpDesc, tmpCtg);
        }



        return myItem;
    }

    public MealItem findMeal(int id) {
        //here we find an item in our meals based on the id given and return it
        String sqlQuery = "SELECT * FROM " + TABLE_MEAL +
                " WHERE " + COLUMN_MID + " = " +
                id;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor myCursor = db.rawQuery(sqlQuery, null);

        MealItem myItem = null;

        if (myCursor.moveToFirst()) {
            int tmpID = myCursor.getInt(0);
            String tmpName = myCursor.getString(1);
            String tmpTime = myCursor.getString(2);
            String tmpIng = myCursor.getString(3);

            String[] ings = tmpIng.split(",");
            ArrayList<PantryItem> pantryItems = new ArrayList<>();

            int i;

            for (i = 0; i < ings.length; i++) {
                pantryItems.add(findMealIng(Integer.parseInt(ings[i])));
            }

            String tmpRecipe = myCursor.getString(4);
            String tmpNote = myCursor.getString(5);
            String tmpLink = myCursor.getString(6);

            myItem = new MealItem(tmpID, tmpName, tmpTime, pantryItems,
                    tmpRecipe, tmpNote, tmpLink);
            myCursor.close();
        }



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
            String tmpQtyName = myCursor.getString(3);
            String tmpDesc = myCursor.getString(4);
            String tmpCtg = myCursor.getString(5);

            myCursor.close();
            myItem = new PantryItem(tmpID, tmpName, Integer.parseInt(tmpQty), tmpQtyName, tmpDesc, tmpCtg);
        }



        return myItem;
    }

    public PantryItem findMealIng(int id) {
        //here we find an item in our pantry based on the id given and return it
        String sqlQuery = "SELECT * FROM " + TABLE_MEAL_ING +
                " WHERE " + COLUMN_MEITEMID + " = " +
                id;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor myCursor = db.rawQuery(sqlQuery, null);

        PantryItem myItem = null;

        if (myCursor.moveToFirst()) {
            int tmpID = myCursor.getInt(0);
            String tmpName = myCursor.getString(1);
            String tmpQty = myCursor.getString(2);
            String tmpQtyName = myCursor.getString(3);
            String tmpDesc = myCursor.getString(4);
            String tmpCtg = myCursor.getString(5);

            myCursor.close();
            myItem = new PantryItem(tmpID, tmpName, Integer.parseInt(tmpQty), tmpQtyName, tmpDesc, tmpCtg);

        }



        return myItem;
    }

    public PantryItem findGroceryItem(String name, String qtyName, String ctg) {
        //here we find an item in our pantry based on the id given and return it
        String sqlQuery = "SELECT * FROM " + TABLE_GROCERY +
                " WHERE UPPER(" + COLUMN_GITEMNAME + ") = \"" +
                name.toUpperCase() + "\" AND " + COLUMN_GITEMQTYNAME + " = \"" + qtyName + "\" AND "
                + COLUMN_GITEMCTG + " = \"" + ctg + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor myCursor = db.rawQuery(sqlQuery, null);

        PantryItem myItem = null;

        if (myCursor.moveToFirst()) {
            int tmpID = myCursor.getInt(0);
            String tmpName = myCursor.getString(1);
            String tmpQty = myCursor.getString(2);
            String tmpQtyName = myCursor.getString(3);
            String tmpDesc = myCursor.getString(4);
            String tmpCtg = myCursor.getString(5);

            myCursor.close();
            myItem = new PantryItem(tmpID, tmpName, Integer.parseInt(tmpQty), tmpQtyName, tmpDesc, tmpCtg);
        }



        return myItem;
    }

    public PantryItem findPantryItem(String name, String qtyName, String ctg) {
        //here we find an item in our pantry based on the id given and return it
        String sqlQuery = "SELECT * FROM " + TABLE_PANTRY +
                " WHERE UPPER(" + COLUMN_PITEMNAME + ") = \"" +
                name.toUpperCase() + "\" AND " + COLUMN_PITEMQTYNAME + " = \"" + qtyName + "\" AND "
                + COLUMN_PITEMCTG + " = \"" + ctg + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor myCursor = db.rawQuery(sqlQuery, null);

        PantryItem myItem = null;

        if (myCursor.moveToFirst()) {
            int tmpID = myCursor.getInt(0);
            String tmpName = myCursor.getString(1);
            String tmpQty = myCursor.getString(2);
            String tmpQtyName = myCursor.getString(3);
            String tmpDesc = myCursor.getString(4);
            String tmpCtg = myCursor.getString(5);

            myCursor.close();
            myItem = new PantryItem(tmpID, tmpName, Integer.parseInt(tmpQty), tmpQtyName, tmpDesc, tmpCtg);
        }



        return myItem;
    }

    public PantryItem findMealIng(String name, String qtyName) {
        //here we find an item in our pantry based on the id given and return it
        String sqlQuery = "SELECT * FROM " + TABLE_MEAL_ING +
                " WHERE " + COLUMN_MEITEMNAME + " = \"" +
                name + "\" AND " + COLUMN_MEITEMQTYNAME + " = \"" + qtyName + "\"";

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor myCursor = db.rawQuery(sqlQuery, null);

        PantryItem myItem = null;

        if (myCursor.moveToFirst()) {
            int tmpID = myCursor.getInt(0);
            String tmpName = myCursor.getString(1);
            String tmpQty = myCursor.getString(2);
            String tmpQtyName = myCursor.getString(3);
            String tmpDesc = myCursor.getString(4);
            String tmpCtg = myCursor.getString(5);

            myCursor.close();
            myItem = new PantryItem(tmpID, tmpName, Integer.parseInt(tmpQty), tmpQtyName, tmpDesc, tmpCtg);
        }



        return myItem;
    }

    public void deleteItem(int id) {

        //this will delete an item from our pantry table based on its id

        String sqlQuery = "DELETE FROM Pantry WHERE pitem_id = " + id;

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL(sqlQuery);


    }

    public void deleteGroceryItem(int id) {

        //this will delete an item from our pantry table based on its id

        String sqlQuery = "DELETE FROM Grocery WHERE gitem_id = " + id;

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL(sqlQuery);


    }

    public void deleteMeal(int id) {

        //this will delete a meal from our meal plan table based on its id

        String sqlQuery = "DELETE FROM Meal WHERE mid = " + id;

        SQLiteDatabase db = this.getWritableDatabase();

        db.execSQL(sqlQuery);


    }


}