package com.example.jamakick.mypantry;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

public class EditMealItem extends AppCompatActivity {

    //reorder flag
    private static final int flag1 = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

    TableLayout ingTable;

    ArrayAdapter<CharSequence> catAdapter;
    ArrayAdapter<CharSequence> qtyAdapter;

    private String KEY_ITM = "item";


    CheckBox addCheck;
    EditText nameEdit;
    EditText timeEdit;

    int mealID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_meal_item);
        ingTable = findViewById(R.id.ingTable);

        Spinner spinner1 = findViewById(R.id.spinner1);
        Spinner spinner2 = findViewById(R.id.spinner2);

        catAdapter = ArrayAdapter.createFromResource(this, R.array.categories,
                android.R.layout.simple_spinner_dropdown_item);

        qtyAdapter = ArrayAdapter.createFromResource(this, R.array.qtyCategories,
                android.R.layout.simple_spinner_dropdown_item);

        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        qtyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(qtyAdapter);
        spinner2.setAdapter(catAdapter);

        //invoke our dbhandler
        PantryDBHandler handler = new PantryDBHandler(this);

        String id = null;

        Bundle myData = getIntent().getExtras();
        if (myData != null ) {
            id = myData.getString(KEY_ITM);
        }

        MealItem findItem = null;
        try {
            findItem = handler.findMeal(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        try {
            mealID = findItem.getMealID();
        } catch (Exception e) {
            e.printStackTrace();
        }

        nameEdit = findViewById(R.id.editName);

        String mname = null;
        try {
            mname = findItem.getMealName();
        } catch (Exception e) {
            mname = "none";
        }

        nameEdit.setText(mname);

        timeEdit = findViewById(R.id.editTime);

        String mtime = null;
        try {
            mtime = findItem.getMealTime();
        } catch (Exception e) {
            mtime = "none";
        }

        timeEdit.setText(mtime);

        ArrayList<PantryItem> mingredients = null;
        try {
            mingredients = findItem.getMealIngredients();
        } catch (Exception e) {
            e.printStackTrace();
        }

        int ingSize = mingredients.size();

        int i;

        for (i = 0; i < ingSize; i++) {

            addRow();

            TableRow row = (TableRow) ingTable.getChildAt(i);

            EditText nameView = (EditText) row.getChildAt(0);
            EditText qtyView = (EditText) row.getChildAt(1);
            Spinner qtySpin = (Spinner) row.getChildAt(2);
            Spinner ctgSpin = (Spinner) row.getChildAt(3);

            try {
                nameView.setText(mingredients.get(i).getPitemName());
                qtyView.setText(Integer.toString(mingredients.get(i).getPitemQty()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            try {
                String qtyCtgText = mingredients.get(i).getPitemQtyName();
                String[] ctgs = getResources().getStringArray(R.array.qtyCategories);
                qtySpin.setSelection(Arrays.asList(ctgs).indexOf(qtyCtgText));
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }

            try {
                String ctgText = mingredients.get(i).getPitemCtg();
                String[] ctgs2 = getResources().getStringArray(R.array.categories);
                ctgSpin.setSelection(Arrays.asList(ctgs2).indexOf(ctgText));
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }

        }


        EditText recipeEdit= findViewById(R.id.editRecipe);

        String mrecipe = null;
        try {
            mrecipe = findItem.getMealRecipe();
        } catch (Exception e) {
            mrecipe = "none";
        }

        recipeEdit.setText(mrecipe);

        EditText noteEdit = findViewById(R.id.editNotes);

        String mnotes = null;
        try {
            mnotes = findItem.getMealNote();
        } catch (Exception e) {
            mnotes = "none";
        }

        noteEdit.setText(mnotes);

        EditText linkEdit= findViewById(R.id.editLink);

        String mlink = null;
        try {
            mlink = findItem.getMealVidLink();
        } catch (Exception e) {
            mlink = "";
        }

        linkEdit.setText(mlink);

    }

    public void addRow() {

        TableRow row = (TableRow) getLayoutInflater().inflate(R.layout.ingredient_row, ingTable, false);
        Spinner spinner1 = (Spinner) row.getVirtualChildAt(2);
        Spinner spinner2 = (Spinner) row.getVirtualChildAt(3);

        spinner1.setAdapter(qtyAdapter);
        spinner2.setAdapter(catAdapter);

        if (ingTable.getChildCount() <= 20) {

            try {
                ingTable.addView(row, ingTable.getChildCount() - 1);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else {
            Toast.makeText(this, "Max number of Ingredients is 20", Toast.LENGTH_SHORT).show();
        }


    }

    public void addRow(View v) {

        TableRow row = (TableRow) getLayoutInflater().inflate(R.layout.ingredient_row, ingTable, false);
        Spinner spinner1 = (Spinner) row.getVirtualChildAt(2);
        Spinner spinner2 = (Spinner) row.getVirtualChildAt(3);

        spinner1.setAdapter(qtyAdapter);
        spinner2.setAdapter(catAdapter);

        if (ingTable.getChildCount() <= 20) {

            try {
                ingTable.addView(row, ingTable.getChildCount() - 1);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        else {
            Toast.makeText(this, "Max number of Ingredients is 20", Toast.LENGTH_SHORT).show();
        }


    }

    public void removeRow(View v) {


        try {
            ingTable.removeViewAt(ingTable.getChildCount() - 2);
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "No Rows to Remove", Toast.LENGTH_SHORT).show();
        }

    }

    //add meal method called when add meal button pressed
    public void updateMeal(View v) {

        //find our editviews and put their contents into string variables

        nameEdit = findViewById(R.id.editName);
        String mname = null;
        try {
            mname = nameEdit.getText().toString();
        } catch (Exception e) {
            mname = "none";
        }

        timeEdit = findViewById(R.id.editTime);
        String mtime = null;
        try {
            mtime = timeEdit.getText().toString();
        } catch (Exception e) {
            mtime = "none";
        }

        ArrayList<PantryItem> mingredients = new ArrayList<>();


        int rows = ingTable.getChildCount() - 1;

        int i;

        for (i = 0; i < rows; i++) {
            TableRow row = (TableRow) ingTable.getChildAt(i);

            EditText nameView = (EditText) row.getChildAt(0);
            EditText qtyView = (EditText) row.getChildAt(1);
            Spinner qtySpin = (Spinner) row.getChildAt(2);
            Spinner ctgSpin = (Spinner) row.getChildAt(3);

            String desc = "";

            String name = null;
            try {
                name = nameView.getText().toString();
            } catch (Exception e) {
                name = "none";
            }

            int qty = 0;
            try {
                qty = Integer.parseInt(qtyView.getText().toString());
            } catch (NumberFormatException e) {
                qty = 0;
            }

            String qtyName = null;
            try {
                qtyName = qtySpin.getSelectedItem().toString();
            } catch (Exception e) {
                e.printStackTrace();
            }

            String ctg = null;
            try {
                ctg = ctgSpin.getSelectedItem().toString();
            } catch (Exception e) {
                e.printStackTrace();
            }

            PantryItem item = null;
            try {
                item = new PantryItem(name, qty, qtyName, desc, ctg);
            } catch (Exception e) {
                e.printStackTrace();
            }

            mingredients.add(item);

        }


        EditText recipeEdit= findViewById(R.id.editRecipe);
        String mrecipe = null;
        try {
            mrecipe = recipeEdit.getText().toString();
        } catch (Exception e) {
            mrecipe = "none";
        }

        EditText noteEdit = findViewById(R.id.editNotes);
        String mnotes = null;
        try {
            mnotes = noteEdit.getText().toString();
        } catch (Exception e) {
            mnotes = "none";
        }

        EditText linkEdit= findViewById(R.id.editLink);
        String mlink = null;
        try {
            mlink = linkEdit.getText().toString();
        } catch (Exception e) {
            mlink = "none";
        }


        //create a new meal item with these variables
        MealItem item1 = null;
        try {
            item1 = new MealItem(mealID, mname, mtime, mingredients, mrecipe, mnotes, mlink);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //invoke our dbhandler
        PantryDBHandler handler = new PantryDBHandler(this);

        //use our addmeal method to add our meal to our database
        try {
            handler.updateMeal(item1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //toast back to the user that it was successfully added
        Toast.makeText(this, mname + " was updated", Toast.LENGTH_SHORT).show();


//        Toast.makeText(this, nameText, Toast.LENGTH_SHORT).show();

        //return the user to the meal plan activity
        Intent intent1 = new Intent(this, MealPlanActivity.class);

        intent1.addFlags(flag1);

        startActivity(intent1);

    }


    //upon clicking the back arrow the user will be taken back to the meal plan activity
    public void toNext(View v) {


        Intent intent1 = new Intent(this, MealPlanActivity.class);

        intent1.addFlags(flag1);

        startActivity(intent1);

    }
}
