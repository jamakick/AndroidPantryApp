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

        ArrayAdapter<CharSequence> catAdapter = ArrayAdapter.createFromResource(this, R.array.categories,
                android.R.layout.simple_spinner_dropdown_item);

        catAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner1.setAdapter(catAdapter);

    }


    public void addItem(View v) {

        EditText nameEdit = findViewById(R.id.editText1);
        String nameText = nameEdit.getText().toString();

        EditText qtyEdit = findViewById(R.id.editText2);
        String qtyText = qtyEdit.getText().toString();

        EditText descEdit= findViewById(R.id.editText3);
        String descText = descEdit.getText().toString();

        Spinner ctgEdit = findViewById(R.id.spinner);
        String ctgText = ctgEdit.getSelectedItem().toString();

        PantryItem item1 = new PantryItem(nameText, qtyText, descText, ctgText);

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
