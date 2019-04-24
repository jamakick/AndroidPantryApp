package com.example.jamakick.mypantry;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.Arrays;

public class EditPantryItem extends AppCompatActivity {

    private static final int flag1 = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

    private String KEY_ITM = "item";


    EditText nameEdit;
    EditText qtyEdit;
    Spinner qtyCtg;
    EditText descEdit;
    Spinner ctgEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_pantryitem);


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

        PantryDBHandler handler = new PantryDBHandler(this);

        String id = null;

        Bundle myData = getIntent().getExtras();
        if (myData != null ) {
            id = myData.getString(KEY_ITM);
        }

        PantryItem findItem = null;
        try {
            findItem = handler.findPantryItem(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }


        nameEdit = findViewById(R.id.editText1);
        String nameText = null;
        try {
            nameText = findItem.getPitemName();
        } catch (Exception e) {
            nameText = "none";
        }
        nameEdit.setText(nameText);

        qtyEdit = findViewById(R.id.editText2);
        int qtyText = 0;
        try {
            qtyText = findItem.getPitemQty();
        } catch (Exception e) {
            qtyText = 0;
        }
        qtyEdit.setText(Integer.toString(qtyText));

        qtyCtg = findViewById(R.id.qtyCtg);
        try {
            String qtyCtgText = findItem.getPitemQtyName();
            String[] ctgs = getResources().getStringArray(R.array.qtyCategories);
            qtyCtg.setSelection(Arrays.asList(ctgs).indexOf(qtyCtgText));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

        descEdit= findViewById(R.id.editText3);
        String descText = null;
        try {
            descText = findItem.getPitemDesc();
        } catch (Exception e) {
            descText = "none";
        }
        descEdit.setText(descText);

        ctgEdit = findViewById(R.id.spinner);
        try {
            String ctgText = findItem.getPitemCtg();
            String[] ctgs2 = getResources().getStringArray(R.array.categories);
            ctgEdit.setSelection(Arrays.asList(ctgs2).indexOf(ctgText));
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }

    }

    public void updateItem(View v) {

        //get our edit text variables and save to strings
        nameEdit = findViewById(R.id.editText1);
        String nameText = null;
        try {
            nameText = nameEdit.getText().toString();
        } catch (Exception e) {
            nameText = "none";
        }

        qtyEdit = findViewById(R.id.editText2);
        int qtyText = 0;
        try {
            qtyText = Integer.parseInt(qtyEdit.getText().toString());
        } catch (NumberFormatException e) {
            qtyText = 0;
        }

        qtyCtg = findViewById(R.id.qtyCtg);
        String qtyCtgText = null;
        try {
            qtyCtgText = qtyCtg.getSelectedItem().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        descEdit= findViewById(R.id.editText3);
        String descText = null;
        try {
            descText = descEdit.getText().toString();
        } catch (Exception e) {
            descText = "none";
        }

        ctgEdit = findViewById(R.id.spinner);
        String ctgText = null;
        try {
            ctgText = ctgEdit.getSelectedItem().toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        //create a new pantry item with our strings
        PantryItem item1 = null;
        try {
            item1 = new PantryItem(nameText, qtyText, qtyCtgText, descText, ctgText);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //invoke dbhandler
        PantryDBHandler handler = new PantryDBHandler(this);

        //add our item to our pantry
        try {
            handler.updatePantryItem(item1);
        } catch (Exception e) {
            e.printStackTrace();
        }

        //toast to the user for the success
        Toast.makeText(this, nameText + " was updated", Toast.LENGTH_SHORT).show();

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
