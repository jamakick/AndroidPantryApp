package com.example.jamakick.mypantry;

import android.content.Context;
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
import android.support.v7.widget.GridLayout;
import android.support.v7.widget.Toolbar;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
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

public class MealPlanActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_meal_plan);

        //Set the toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();

        //Creates the 3 bar menu icon that will bring up the "home" or side menu
        assert actionbar != null;
        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        //Delete the default title since we have a custom one
        getSupportActionBar().setTitle(null);

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
        ArrayList<MealItem> items;

        //Call the get items function through our handler to get an arraylist of pantry items
        items = handler.getMeals();

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
    public void toCreateMeal(View v) {

        Intent intent1 = new Intent(this, CreateMeal.class);

        intent1.addFlags(flag1);


        startActivity(intent1);
    }

    private void createItemViews(ArrayList<MealItem> items) {

        //create our gridview, arraylist of mealitems, and our custom meal adapter
        GridView gridView;
        ArrayList<MealItem> gridArray = new ArrayList<>();
        MealGridViewAdapter mealGridAdapter;

        //get out amount of items
        int itemSize = items.size();

        int i;

        //for every item in our meal arraylist
        for (i = 0; i < itemSize; i++) {

            //get our meal item based on the i index and add it to our grid array with meal item constructor
            MealItem item = items.get(i);

            gridArray.add(new MealItem(item.getMealID(), item.getMealName(), item.getMealTime(),
                    item.getMealIngredients(), item.getMealRecipe(), item.getMealNote(), item.getMealVidLink()));
        }

        //get the gridview from our xml
        gridView = findViewById(R.id.gridView);
        //create a new mealgridadapter with the layout for our row and the mealitems from our gridarray
        mealGridAdapter = new MealGridViewAdapter(this, R.layout.meal_row_grid, gridArray);

        gridView.setAdapter(mealGridAdapter);

        //set click listener for our gridview
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                //cast the clicked view to a relative layout so we can get its children
                RelativeLayout rlayout = (RelativeLayout) view;

                //cast the relative layouts child to a textview
                TextView tview = (TextView) rlayout.getChildAt(0);

                //get our textview tag since that is the meal id
                String mealID = tview.getTag().toString();

                //send to view meal class with our meal id
                Intent intent1 = new Intent(context, ViewMeal.class);

                intent1.putExtra(KEY_ITM, mealID);

                startActivity(intent1);
            }
        });


    }
}
