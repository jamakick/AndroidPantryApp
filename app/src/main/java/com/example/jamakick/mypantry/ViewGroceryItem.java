package com.example.jamakick.mypantry;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class ViewGroceryItem extends AppCompatActivity {

    //key to get back our pantry item ID
    private String KEY_ITM = "item";

    private static final int flag1 = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_grocery_item);

        //on create we call getpantry item to fill in the text views with the pantry items info from our database.
        getGroceryItem();
    }


    public void backToMain(View v) {

        //takes you back to the mainactivity
        Intent intent1 = new Intent(this, MainActivity.class);

        intent1.addFlags(flag1);

        startActivity(intent1);

    }

    public void getGroceryItem() {

        //get the id from our extras
        String id = null;

        Bundle myData = getIntent().getExtras();
        if (myData != null ) {
            id = myData.getString(KEY_ITM);
        }

        //create our handler
        PantryDBHandler handler = new PantryDBHandler(this);

        //call the findpantryitem function from our handler on our id
        //currently hardcoded a 1 id since my ID is not passing properly, but it shows the view properly for number 1
        PantryItem item1 = handler.findGroceryItem(Integer.parseInt(id));

//        Toast.makeText(this, Integer.toString(id), Toast.LENGTH_LONG).show();
//
        //replace the text in our views with the proper content
        TextView tv1 = findViewById(R.id.nameView);
        TextView tv2 = findViewById(R.id.qtyView);
        TextView tv3 = findViewById(R.id.descView);
        TextView tv4 = findViewById(R.id.ctgView);
        tv1.setText(item1.getPitemName());
        tv2.setText(item1.getPitemQty());
        tv3.setText(item1.getPitemDesc());
        tv4.setText(item1.getPitemCtg());

    }

    public void deleteGroceryItem(View v) {

        //get our id same as in the getitem
        String id = null;

        Bundle myData = getIntent().getExtras();
        if (myData != null ) {
            id = myData.getString(KEY_ITM);
        }

        //call delete item from our db handler
        PantryDBHandler handler = new PantryDBHandler(this);

        handler.deleteGroceryItem(Integer.parseInt(id));

        Intent intent1 = new Intent(this, GroceryActivity.class);

        intent1.addFlags(flag1);

        startActivity(intent1);

    }


}