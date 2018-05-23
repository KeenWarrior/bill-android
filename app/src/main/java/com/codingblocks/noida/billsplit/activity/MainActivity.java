package com.codingblocks.noida.billsplit.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;


import butterknife.BindView;
import butterknife.ButterKnife;

import com.codingblocks.noida.billsplit.R;
import com.codingblocks.noida.billsplit.fragment.ToursFragment;
import com.codingblocks.noida.billsplit.model.Tour;
import com.codingblocks.noida.billsplit.util.Preferences;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , ToursFragment.OnListFragmentInteractionListener {


    @BindView(R.id.navigation) NavigationView navigation;
    @BindView(R.id.fab) FloatingActionButton fab;

    TextView user_name;
    TextView user_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        View headerView = navigation.getHeaderView(0);
        user_name = ButterKnife.findById(headerView, R.id.user_name);
        user_email = ButterKnife.findById(headerView, R.id.user_email);

        user_name.setText(Preferences.of(getApplicationContext()).name().get());
        user_email.setText(Preferences.of(getApplicationContext()).email().get());

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation);
        navigationView.setNavigationItemSelectedListener(this);

        navigationView.setCheckedItem(R.id.nav_tours);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        // content main is the id of content main.xml

        if (id == R.id.nav_tours) {
            Fragment fragment = new ToursFragment();
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main,fragment).commit();
        }

        else if (id == R.id.logout) {
            Preferences.of(getApplicationContext()).email().delete();
            Preferences.of(getApplicationContext()).username().delete();
            Preferences.of(getApplicationContext()).username().delete();

            finish();
            startActivity(new Intent(getApplicationContext(), LoginActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onListFragmentInteraction(Tour item) {
        Intent intent = new Intent(getApplicationContext() , TourDetailActivity.class);
        intent.putExtra("tour_id" , item.id);
        intent.putExtra("tour_name" , item.name);
        startActivityForResult(intent, 12);
    }

    public void setFabClickListener(View.OnClickListener listener) {
        fab.setOnClickListener(listener);
    }
}
