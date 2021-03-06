package com.example.jamakick.mypantry;

import android.content.Intent;
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

public class CreateMeal extends AppCompatActivity {

    //reorder flag
    private static final int flag1 = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

    TableLayout ingTable;

    ArrayAdapter<CharSequence> catAdapter;
    ArrayAdapter<CharSequence> qtyAdapter;

    //oncreate we only set the view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meal);

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
    public void addMeal(View v) {

        CheckBox addCheck = findViewById(R.id.addCheck);

        //find our editviews and put their contents into string variables

        EditText nameEdit = findViewById(R.id.editName);

        String mname = null;
        try {
            mname = nameEdit.getText().toString();
        } catch (Exception e) {
            mname = "none";
        }

        EditText timeEdit = findViewById(R.id.editTime);

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


            String qtyName = qtySpin.getSelectedItem().toString();

            String ctg = ctgSpin.getSelectedItem().toString();


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
            item1 = new MealItem(mname, mtime, mingredients, mrecipe, mnotes, mlink);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //invoke our dbhandler
        PantryDBHandler handler = new PantryDBHandler(this);

        //use our addmeal method to add our meal to our database
        handler.addMeal(item1, addCheck.isChecked());

        //toast back to the user that it was successfully added
        Toast.makeText(this, mname + " was added to the Meal Plan", Toast.LENGTH_SHORT).show();


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
