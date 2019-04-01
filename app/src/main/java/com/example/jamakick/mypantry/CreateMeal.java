package com.example.jamakick.mypantry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateMeal extends AppCompatActivity {

    //reorder flag
    private static final int flag1 = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

    //oncreate we only set the view
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meal);

    }

    //add meal method called when add meal button pressed
    public void addMeal(View v) {

        //find our editviews and put their contents into string variables

        EditText nameEdit = findViewById(R.id.editName);
        String mname = nameEdit.getText().toString();

        EditText timeEdit = findViewById(R.id.editTime);
        String mtime = timeEdit.getText().toString();

        EditText ingEdit = findViewById(R.id.editIngredients);
        String mingredients = ingEdit.getText().toString();

        EditText recipeEdit= findViewById(R.id.editRecipe);
        String mrecipe = recipeEdit.getText().toString();

        EditText noteEdit = findViewById(R.id.editNotes);
        String mnotes = noteEdit.getText().toString();

        EditText linkEdit= findViewById(R.id.editLink);
        String mlink = linkEdit.getText().toString();


        //create a new meal item with these variables
        MealItem item1 = new MealItem(mname, mtime, mingredients, mrecipe, mnotes, mlink);

        //invoke our dbhandler
        PantryDBHandler handler = new PantryDBHandler(this);

        //use our addmeal method to add our meal to our database
        handler.addMeal(item1);

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
