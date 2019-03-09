package com.example.jamakick.mypantry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class CreateMeal extends AppCompatActivity {

    //intent flag
    private static final int flag1 = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_meal);


        //We use a spinner object to create a dropdown menu of meal categories
        Spinner spinner1 = findViewById(R.id.spinner);

        //The array adapter gets the categories from our mealCats.xml file in the values folder
        ArrayAdapter<CharSequence> catAdapter = ArrayAdapter.createFromResource(this, R.array.mealCats,
                android.R.layout.simple_spinner_dropdown_item);

        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //set our spinner to use the adapter
        spinner1.setAdapter(catAdapter);
    }

    //Sends the user back to the meal plan activity
    public void toNext(View v) {

        Intent intent1 = new Intent(this, MealPlanActivity.class);

        intent1.addFlags(flag1);

        startActivity(intent1);

    }

}
