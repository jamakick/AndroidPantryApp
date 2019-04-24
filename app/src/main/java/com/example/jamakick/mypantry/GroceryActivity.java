package com.example.jamakick.mypantry;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.app.ActionBar;
import android.app.AlertDialog;
import android.util.Log;
import android.widget.Toolbar;
import android.view.View;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GroceryActivity extends Activity {

    //Initiate Drawerlayout for side nav menu
    private DrawerLayout dlayout;

    //intent flag to prevent creating instances of the same activity over and over
    private static final int flag1 = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

    Context context;

    //Key for giving what page is sending the information
    private String KEY_ITM = "item";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery);

        //Set the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setActionBar(toolbar);

        ActionBar actionbar = getActionBar();

        //Creates the 3 bar menu icon that will bring up the "home" or side menu
        assert actionbar != null;
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        //Delete the default title since we have a custom one
        getActionBar().setTitle(null);

        context = getApplicationContext();

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

                        else if (id == R.id.nav_map) {
                            toMap();
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

        //Establish database handler
        PantryDBHandler handler = new PantryDBHandler(this);

        //Establish Arraylist collection of pantry items
        ArrayList<PantryItem> items;

        //Call the get items function through our handler to get an arraylist of pantry items
        items = handler.getGroceryItems();

        //Creates the views on the pantry page for each pantry item
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

    public void toMap() {

        Intent intent1 = new Intent(this, MapsActivity.class);
        intent1.addFlags(flag1);

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
    public void toCreateGroceryItem(View v) {

        Intent intent1 = new Intent(this, CreateGroceryItem.class);

        intent1.addFlags(flag1);


        startActivity(intent1);
    }


    //take our pantryitems as an argument to create our views
    private void createItemViews(ArrayList<PantryItem> items) {

        //same as our mainactivity and mealplanactivity

        GridView gridView;
        ArrayList<PantryGridItem> gridArray = new ArrayList<>();
        GroceryGridViewAdapter groceryGridAdapter;

        int itemSize = items.size();

        int i;

        for (i = 0; i < itemSize; i++) {

            PantryItem item = items.get(i);

            gridArray.add(new PantryGridItem(item.getPitemID(), item.getPitemName(), item.getPitemQty(), item.getPitemQtyName(), item.getPitemCtg()));
        }

        gridView = findViewById(R.id.gridView);
        groceryGridAdapter = new GroceryGridViewAdapter(this, R.layout.row_grid, gridArray);

        gridView.setAdapter(groceryGridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                RelativeLayout rlayout = (RelativeLayout) view;

                TextView tview = (TextView) rlayout.getChildAt(0);

                String pantryID = tview.getTag().toString();

                Intent intent1 = new Intent(context, ViewGroceryItem.class);

                intent1.putExtra(KEY_ITM, pantryID);

                startActivity(intent1);
            }
        });

        gridView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {

                RelativeLayout rlayout = (RelativeLayout) view;

                TextView tview = (TextView) rlayout.getChildAt(0);

                final String pantryID = tview.getTag().toString();

                AlertDialog.Builder builder = new AlertDialog.Builder(GroceryActivity.this);
                builder.setCancelable(true);
                builder.setTitle("Move to Pantry");
                builder.setMessage("Do you want to move this Grocery item to the Pantry?");
                builder.setPositiveButton("Confirm",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                PantryDBHandler handler = new PantryDBHandler(GroceryActivity.this);

                                PantryItem item = handler.findGroceryItem(Integer.parseInt(pantryID));

                                handler.addPantryItem(item);

                                handler.deleteGroceryItem(Integer.parseInt(pantryID));

                                Intent intent1 = new Intent(context, GroceryActivity.class);

                                startActivity(intent1);


                            }
                        });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();

                return true;
            }
        });


    }
}
