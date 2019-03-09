package com.example.jamakick.mypantry;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //Initiate Drawerlayout for side nav menu
    private DrawerLayout dlayout;

    //intent flag to prevent creating instances of the same activity over and over
    private static final int flag1 = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

    //Key for giving what page is sending the information
    private String KEY_ITM = "item";

    private TextView showItemsView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set the toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();

        //Creates the 3 bar menu icon that will bring up the "home" or side menu
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        //Delete the default title since we have a custom one
        getSupportActionBar().setTitle(null);

        //Establish Arraylist collection of pantry items
        ArrayList<PantryItem> items = new ArrayList<>();

        //Establish database handler
        PantryDBHandler handler = new PantryDBHandler(this);

        //Call the get items function through our handler to get an arraylist of pantry items
        items = handler.getItems();

        //Creates the views on the pantry page for each pantry item
        createItemViews(items);

        //use our drawer layout and navigation view to create our menu items and point them to their function calls
        dlayout = findViewById(R.id.drawer_layout);

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                        int id = menuItem.getItemId();

                        if (id == R.id.nav_pantry) {
                            toPantry();
                        }

                        else if (id == R.id.nav_grocery) {
                            toGrocery();
                        }

                        else if (id == R.id.nav_meal) {
                            toMealPlan();
                        }


                        dlayout.closeDrawers();
                        return true;
                    }
                });


    }

    @Override
    protected void onResume() {
        //we use on resume to rerun our createitemviews to include any new items created
        super.onResume();

        ArrayList<PantryItem> items = new ArrayList<>();

        PantryDBHandler handler = new PantryDBHandler(this);

        items = handler.getItems();

        createItemViews(items);


    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
    }




    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //When the 3 bar menu icon is pressed, it checks to see if the id is the home, and if it is then it opens the drawer
        int id = item.getItemId();

        if (id == android.R.id.home) {

            dlayout.openDrawer(GravityCompat.START);
            return true;

        }

        return super.onOptionsItemSelected(item);
    }

    //the below 3 functions are used in the drawerlayout side nav

    //function to create intent and send the user to the pantry activity
    public void toPantry() {

        Intent intent1 = new Intent(this, MainActivity.class);

        intent1.addFlags(flag1);

//        intent1.putExtra(KEY_INC, increment+1);

        startActivity(intent1);

    }

    //sends the user to the grocery activity
    public void toGrocery() {

        Intent intent1 = new Intent(this, GroceryActivity.class);

        intent1.addFlags(flag1);

//        intent1.putExtra(KEY_INC, increment+1);

        startActivity(intent1);

    }

    //sends the user to the meal plan activity
    public void toMealPlan() {

        Intent intent1 = new Intent(this, MealPlanActivity.class);

        intent1.addFlags(flag1);

        startActivity(intent1);

    }

    //sends the user to the create Item activity. This is a button on the relative layout
    //and is an onclick so we give the View argument to it.
    public void toCreatePantryItem(View v) {

        Intent intent1 = new Intent(this, CreatePantryItem.class);

        intent1.addFlags(flag1);


        startActivity(intent1);
    }

    public void toViewItem(View v) {
        //sends the user to view pantry item along with that pantry items ID for future identification
        String pantryID = v.getTag().toString();

//        Toast.makeText(this, pantryID, Toast.LENGTH_SHORT).show();
        Intent intent1 = new Intent(this, ViewPantryItem.class);

        intent1.addFlags(flag1);

        //this is not sending the correct ID for some reason, it always sends an ID of 0 and I cannot make it change
        intent1.putExtra(KEY_ITM, pantryID);

        startActivity(intent1);
    }

    private void createItemViews(ArrayList<PantryItem> items) {

        //Currently I am taking my ArrayList of items and just putting it into the text in my textview so it displays

        //What should be happening, is I create a row with two linear layouts that have text views for each pantry item, but
        //I am unable to create these layouts and add them to the xml file through Java. I have to brainstorm better ways to do this
        //but so far have been unsuccessful, however the database portion works properly

        int itemSize = items.size();

        TableLayout layout = findViewById(R.id.itemContainer);

        TextView showItemsView = findViewById(R.id.showitems);

        showItemsView.setText(items.toString());

        //Trying to set up my views properly to make it look good, but is not currently working right.

//        if (itemSize > 0) {
//
//            if (itemSize % 2 == 0) {
//                TableRow tr = new TableRow(this);
//                int i;
//
//                for (i = 0; i < itemSize; i += 2)
//
//                    tr = new TableRow(this);
//                tr.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT));
//
//                LinearLayout ll = new LinearLayout(this);
//
//                ll.setLayoutParams(new LinearLayout.LayoutParams(120, 120));
//
//                TextView tv1 = new TextView(this);
//                tv1.setLayoutParams(new ViewGroup.LayoutParams(android.app.ActionBar.LayoutParams.WRAP_CONTENT, android.app.ActionBar.LayoutParams.WRAP_CONTENT));
//                tv1.setText(items.get(i).getPitemName());
//
//                TextView tv2 = new TextView(this);
//                tv2.setLayoutParams(new ViewGroup.LayoutParams(android.app.ActionBar.LayoutParams.WRAP_CONTENT, android.app.ActionBar.LayoutParams.WRAP_CONTENT));
//                tv2.setText(items.get(i).getPitemQty());
//
//                TextView tv3 = new TextView(this);
//                tv3.setLayoutParams(new ViewGroup.LayoutParams(android.app.ActionBar.LayoutParams.WRAP_CONTENT, android.app.ActionBar.LayoutParams.WRAP_CONTENT));
//                tv3.setText(items.get(i).getPitemCtg());
//
//                ll.addView(tv1);
//                ll.addView(tv2);
//                ll.addView(tv3);
//
//                tr.addView(ll);
//
//
//                LinearLayout ll1 = new LinearLayout(this);
//
//                ll.setLayoutParams(new LinearLayout.LayoutParams(120, 120));
//
//                TextView tv4 = new TextView(this);
//                tv1.setLayoutParams(new ViewGroup.LayoutParams(android.app.ActionBar.LayoutParams.WRAP_CONTENT, android.app.ActionBar.LayoutParams.WRAP_CONTENT));
//                tv1.setText(items.get(i).getPitemName());
//
//                TextView tv5 = new TextView(this);
//                tv2.setLayoutParams(new ViewGroup.LayoutParams(android.app.ActionBar.LayoutParams.WRAP_CONTENT, android.app.ActionBar.LayoutParams.WRAP_CONTENT));
//                tv2.setText(items.get(i).getPitemQty());
//
//                TextView tv6 = new TextView(this);
//                tv3.setLayoutParams(new ViewGroup.LayoutParams(android.app.ActionBar.LayoutParams.WRAP_CONTENT, android.app.ActionBar.LayoutParams.WRAP_CONTENT));
//                tv3.setText(items.get(i).getPitemCtg());
//
//                ll1.addView(tv4);
//                ll1.addView(tv5);
//                ll1.addView(tv6);
//
//                tr.addView(ll1);
//
//
//            }
//
//        }

    }
}
