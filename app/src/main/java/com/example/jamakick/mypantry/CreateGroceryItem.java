package com.example.jamakick.mypantry;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CreateGroceryItem extends AppCompatActivity {

    //code here is the same as in create pantry item

    private static final int flag1 = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_grocery_item);

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

        EditText nameEdit = findViewById(R.id.editText1);

        String nameText = null;
        try {
            nameText = nameEdit.getText().toString();
        } catch (Exception e) {
            nameText = "none";
        }

        EditText qtyEdit = findViewById(R.id.editText2);

        int qtyText = 0;
        try {
            qtyText = Integer.parseInt(qtyEdit.getText().toString());
        } catch (NumberFormatException e) {
            qtyText = 0;
        }

        Spinner qtyCtg = findViewById(R.id.qtyCtg);

        String qtyCtgText = null;
        try {
            qtyCtgText = qtyCtg.getSelectedItem().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }


        EditText descEdit= findViewById(R.id.editText3);

        String descText = null;
        try {
            descText = descEdit.getText().toString();
        } catch (Exception e) {
            descText = "none";
        }

        Spinner ctgEdit = findViewById(R.id.spinner);

        String ctgText = null;
        try {
            ctgText = ctgEdit.getSelectedItem().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }


        PantryItem item1 = null;
        try {
            item1 = new PantryItem(nameText, qtyText, qtyCtgText, descText, ctgText);
        } catch (Exception e) {
            e.printStackTrace();
        }

        PantryDBHandler handler = new PantryDBHandler(this);

        handler.addGroceryItem(item1);

        Toast.makeText(this, nameText + " was added to the Grocery List", Toast.LENGTH_SHORT).show();


//        Toast.makeText(this, nameText, Toast.LENGTH_SHORT).show();

        Intent intent1 = new Intent(this, GroceryActivity.class);

        intent1.addFlags(flag1);

        startActivity(intent1);

    }



    public void toNext(View v) {


        Intent intent1 = new Intent(this, GroceryActivity.class);

        intent1.addFlags(flag1);

        startActivity(intent1);

    }
}
