package com.example.jamakick.mypantry;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

public class MealPlanActivity extends AppCompatActivity {

    //Same drawerlayout and flags as the MainActivity
    private DrawerLayout dlayout;

    private static final int flag1 = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meal_plan);

        //Same oncreate as Main Activity
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar actionbar = getSupportActionBar();

        actionbar.setDisplayHomeAsUpEnabled(true);
        actionbar.setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

        getSupportActionBar().setTitle(null);


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
    //Same methods as MainActivity
    public void toPantry() {

        Intent intent1 = new Intent(this, MainActivity.class);

        intent1.addFlags(flag1);

//        intent1.putExtra(KEY_INC, increment+1);

        startActivity(intent1);

    }

    public void toGrocery() {

        Intent intent1 = new Intent(this, GroceryActivity.class);

        intent1.addFlags(flag1);

//        intent1.putExtra(KEY_INC, increment+1);

        startActivity(intent1);

    }

    public void toMealPlan() {

        Intent intent1 = new Intent(this, MealPlanActivity.class);

        intent1.addFlags(flag1);

//        intent1.putExtra(KEY_INC, increment+1);

        startActivity(intent1);

    }

    //sends the user to the create meal activity
    public void toCreateMeal(View v) {

        Intent intent1 = new Intent(this, CreateMeal.class);

        intent1.addFlags(flag1);

//        intent1.putExtra(KEY_INC, increment+1);

        startActivity(intent1);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == android.R.id.home) {

            dlayout.openDrawer(GravityCompat.START);
            return true;

        }

        return super.onOptionsItemSelected(item);
    }
}
