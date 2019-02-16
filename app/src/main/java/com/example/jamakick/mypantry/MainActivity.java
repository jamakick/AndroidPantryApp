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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    //Initiate Drawerlayout for side nav menu
    private DrawerLayout dlayout;

    //intent flag to prevent creating instances of the same activity over and over
    private static final int flag1 = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;

    //Key for giving what page is sending the information
    private String KEY_INC = "page";


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

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        //declare bundle
//        Bundle myData = getIntent().getExtras();
//
//        if (myData == null) {
//            increment = 0;
//        }
//        else {
//            increment = myData.getInt(KEY_INC);
//        }
//
//    }
//
//    @Override
//    protected void onNewIntent(Intent intent) {
//        super.onNewIntent(intent);
//        setIntent(intent);
//    }




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

//        intent1.putExtra(KEY_INC, increment+1);

        startActivity(intent1);

    }

    //sends the user to the create Item activity. This is a button on the relative layout
    //and is an onclick so we give the View argument to it.
    public void toCreateItem(View v) {

        Intent intent1 = new Intent(this, CreatePantryItem.class);

        intent1.addFlags(flag1);

        //We add an extra key to tell the create item what page it was sent to by
        intent1.putExtra(KEY_INC, "pantry");

        startActivity(intent1);
    }
}
