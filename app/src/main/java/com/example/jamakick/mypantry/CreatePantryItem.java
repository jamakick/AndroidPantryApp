package com.example.jamakick.mypantry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CreatePantryItem extends AppCompatActivity {

    //reorderflag
    private static final int flag1 = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

    //in our oncreate we set our content view and create a spinner for item categories
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_pantry_item);

        //create our spinner and set its resource to be our categories and set the adapter
        Spinner spinner1 = findViewById(R.id.spinner);
        Spinner spinner2 = findViewById(R.id.qtyCtg);

        ArrayAdapter<CharSequence> catAdapter = ArrayAdapter.createFromResource(this, R.array.categories,
                android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> qtyAdapter = ArrayAdapter.createFromResource(this, R.array.qtyCategories,
                android.R.layout.simple_spinner_dropdown_item);

        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        qtyAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(catAdapter);
        spinner2.setAdapter(qtyAdapter);

    }


    public void addItem(View v) {

        //get our edit text variables and save to strings
        EditText nameEdit = findViewById(R.id.editText1);
        String nameText = nameEdit.getText().toString();

        EditText qtyEdit = findViewById(R.id.editText2);
        int qtyText = Integer.parseInt(qtyEdit.getText().toString());

        Spinner qtyCtg = findViewById(R.id.qtyCtg);
        String qtyCtgText = qtyCtg.getSelectedItem().toString();

        EditText descEdit= findViewById(R.id.editText3);
        String descText = descEdit.getText().toString();

        Spinner ctgEdit = findViewById(R.id.spinner);
        String ctgText = ctgEdit.getSelectedItem().toString();

        //create a new pantry item with our strings
        PantryItem item1 = new PantryItem(nameText, qtyText, qtyCtgText, descText, ctgText);

        //invoke dbhandler
        PantryDBHandler handler = new PantryDBHandler(this);

        //add our item to our pantry
        handler.addPantryItem(item1);

        //toast to the user for the success
        Toast.makeText(this, nameText + " was added to the Pantry", Toast.LENGTH_SHORT).show();


//        Toast.makeText(this, nameText, Toast.LENGTH_SHORT).show();

        //return the user to the main activity
        Intent intent1 = new Intent(this, MainActivity.class);

        intent1.addFlags(flag1);

        startActivity(intent1);

    }



    public void toNext(View v) {

        //back button returns user to main activity
        Intent intent1 = new Intent(this, MainActivity.class);

        intent1.addFlags(flag1);

        startActivity(intent1);

    }
}
