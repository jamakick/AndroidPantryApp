package com.example.jamakick.mypantry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class CreatePantryItem extends AppCompatActivity {

    //all the code here is the same as in Create Meal except for the extra data sent in the bundle

    private static final int flag1 = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

    //our key is page and we create a string variable to initialize it
    private String KEY_INC = "page";

    private String page;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pantry_item);

        Spinner spinner1 = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> catAdapter = ArrayAdapter.createFromResource(this, R.array.categories,
                android.R.layout.simple_spinner_dropdown_item);

        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(catAdapter);


        //after setting our spinner we get our bundle and find what data is in the bundle for our KEY_INC
        Bundle bundle = getIntent().getExtras();

        if (bundle == null) {
            page = "pantry";
        }
        else {
            page = bundle.getString(KEY_INC);
        }
    }



    public void toNext(View v) {

        Intent intent1 = new Intent();

        //Here I attempt to distinguish between what intent should be created depending on
        //the value of page. It is not working currently and I have been unable to solve why
        //so currently the grocery page still links back to the pantry

//        if (page == "pantry") {
//
//            intent1 = new Intent(this, MainActivity.class);
//        }
//
//        else if (page == "grocery") {
//            intent1 = new Intent(this, GroceryActivity.class);
//        }

        intent1 = new Intent(this, MainActivity.class);

        intent1.addFlags(flag1);

//        intent1.putExtra(KEY_INC, increment+1);

        startActivity(intent1);

    }
}
