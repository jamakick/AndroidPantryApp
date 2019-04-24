package com.example.jamakick.mypantry;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import java.util.ArrayList;
import java.util.Arrays;

public class ViewMeal extends AppCompatActivity {

    //key to get back our meal item ID
    private String KEY_ITM = "item";

    private static final int flag1 = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_meal);

        //on create we call getMeal item to fill in the text views with the pantry items info from our database.
        getMeal();
    }


    public void backToMain(View v) {

        //takes you back to the mealplanactivity
        Intent intent1 = new Intent(this, MealPlanActivity.class);

        intent1.addFlags(flag1);

        startActivity(intent1);

    }

    public void getMeal() {

        //get the id from our extras
        String id = null;

        Bundle myData = getIntent().getExtras();
        if (myData != null ) {
            id = myData.getString(KEY_ITM);
        }

        //create our handler
        PantryDBHandler handler = new PantryDBHandler(this);

        //call the findmeal function from our handler on our id
        MealItem item1 = handler.findMeal(Integer.parseInt(id));

        //replace the text in our views with the proper content
        TextView tv1 = findViewById(R.id.nameView);
        TextView tv2 = findViewById(R.id.timeView);
        TextView tv3 = findViewById(R.id.ingView);
        TextView tv4 = findViewById(R.id.recipeView);
        TextView tv5 = findViewById(R.id.descView);
        tv1.setText(item1.getMealName());
        tv2.setText(item1.getMealTime());
        tv4.setText(item1.getMealRecipe());
        tv5.setText(item1.getMealNote());


        ArrayList<PantryItem> ings = item1.getMealIngredients();

        String ingString = "";

        int i;

        for (i = 0; i < ings.size(); i++) {
            ingString += ings.get(i).getPitemName() + " ";
            ingString += ings.get(i).getPitemQty() + " ";
            ingString += ings.get(i).getPitemQtyName() + " ";
            ingString += ings.get(i).getPitemCtg() + " \n";

        }

        tv3.setText(ingString);


        //create our string link
        final String link = item1.getMealVidLink();

        //get our button for playing video
        Button playButton = findViewById(R.id.playButton);

        //set our onclick listener
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start a new activity using the youtube app on the phone and play our link
                //I would like to embed this in the viewmeal activity rather than opening the youtube app
                //but I am unsure if I am allowed to use something like the youtube player api
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(link)));
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(ViewMeal.this, "The link is not valid", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void deleteMeal(View v) {

        //get our id same as in the getitem
        String id = null;

        Bundle myData = getIntent().getExtras();
        if (myData != null ) {
            id = myData.getString(KEY_ITM);
        }

        //call delete item from our db handler
        PantryDBHandler handler = new PantryDBHandler(this);

        handler.deleteMeal(Integer.parseInt(id));

        Intent intent1 = new Intent(this, MealPlanActivity.class);

        intent1.addFlags(flag1);

        startActivity(intent1);

    }

    public void editMeal (View v) {

        String id = null;

        Bundle myData = getIntent().getExtras();
        if (myData != null ) {
            id = myData.getString(KEY_ITM);
        }

        Intent intent1 = new Intent(getApplicationContext(), EditMealItem.class);

        intent1.putExtra(KEY_ITM, id);

        startActivity(intent1);

    }


}
