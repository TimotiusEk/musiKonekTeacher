package com.example.timotiusek.musikonekteacher;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.toolbar)
    Toolbar toolbar;

    private FragmentManager mFragmentManager;
    private FragmentTransaction mFragmentTransaction;
    ActionBarDrawerToggle toggle;

    private String email;
    private String username;

    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        toolbar.setTitle("Beranda");
        changeFragment(new UnderDevelopmentFragment());
        setChecked(R.id.menu_home);

        sharedPreferences = getSharedPreferences("profile", Context.MODE_PRIVATE);

        updateNavView();

        NavigationView nv = (NavigationView) findViewById(R.id.nav_view);
        View header = nv.getHeaderView(0);

        Log.d("ADSF",username);

        TextView textName = (TextView) header.findViewById(R.id.username);
        textName.setText(username);

        TextView textEmail = (TextView) header.findViewById(R.id.email_label);
        textEmail.setText(email);

        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(
                MainActivity.this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(MainActivity.this);
    }

    public void changeFragment(Fragment newFragment) {
        mFragmentManager = getSupportFragmentManager();
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragment_container, newFragment);
        mFragmentTransaction.commit();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }



    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.menu_home) {
            toolbar.setTitle("Beranda");
            changeFragment(new UnderDevelopmentFragment());
            /**
             * Todo: add menu_home behaviour
             */
        } else if(id == R.id.menu_course){
            toolbar.setTitle("Kursus");
            changeFragment(new UnderDevelopmentFragment());
            /**
             * Todo: add menu_course behaviour
             */
        } else if(id == R.id.menu_profile){
            startActivity(new Intent(this, ProfileActivity.class));
        } else if(id == R.id.menu_skill){
            changeFragment(new ShowSkillFragment());
        } else if(id == R.id.menu_schedule){
           changeFragment(new WeeklyScheduleFragment());
        } else if(id == R.id.menu_order){
            changeFragment(new OrderLayoutFragment());
        } else if(id == R.id.menu_student){
            changeFragment(new StudentListLayoutFragment());
        } else if(id == R.id.menu_quit){
           System.exit(0);
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void setChecked(int id){
        navigationView.setCheckedItem(id);
    }

    public void clearCheckedItems(){
        int size = navigationView.getMenu().size();
        for (int i = 0; i < size; i++) {
            navigationView.getMenu().getItem(i).setChecked(false);
        }
    }

    public void updateNavView(){
        if(!sharedPreferences.getString("email","").equals("")) {
            email = sharedPreferences.getString("email","");
        }

        if(!sharedPreferences.getString("username","").equals("")) {
            username = sharedPreferences.getString("username","");
        }else{
            username = "username";
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Log.d("ASDF","called");
        updateNavView();
    }




}

