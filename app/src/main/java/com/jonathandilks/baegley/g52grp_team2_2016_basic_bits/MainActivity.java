package com.jonathandilks.baegley.g52grp_team2_2016_basic_bits;

import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.jonathandilks.baegley.g52grp_team2_2016_basic_bits.model.Data;
import com.jonathandilks.baegley.g52grp_team2_2016_basic_bits.model.Parser;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity{

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private  BottomNavigationView navigation;
    private  CharSequence mTitle;

    private Data data;
    private Bundle bundleData;

    private ProfileFragment profileFragment;
    private TutorFragment tutorFragment;
    private HomeFragment homeFragment;
    private GmapFragment gmapFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.flContent, new HomeFragment()).commit();

        mTitle = getTitle();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        setupDrawerContent(navigationView);

        navigation = (BottomNavigationView) findViewById(R.id.bottom_navigation) ;
        setupMainContent(navigation);

        InputStream rdfStudentStream = getResources().openRawResource(R.raw.student_data);
        InputStream rdfStaffStream = getResources().openRawResource(R.raw.staff_data);

        //Initialise model
        data = new Data();
        Parser parser = new Parser(data);
        parser.doParse(rdfStudentStream, rdfStaffStream);

        bundleData = new Bundle();
        bundleData.putParcelable("data", data);

        //Initialise our fragments
        profileFragment = new ProfileFragment();
        tutorFragment = new TutorFragment();
        homeFragment = new HomeFragment();
        gmapFragment = new GmapFragment();
    }

    private void setupMainContent(BottomNavigationView navigation) {
        navigation.setOnNavigationItemSelectedListener(
                new BottomNavigationView.OnNavigationItemSelectedListener(){
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem){
                        selectDrawerItem(menuItem);
                        return true;
                    }
                }
        );
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener(){
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem){
                        selectDrawerItem(menuItem);
                        return true;
                    }
                }
        );
    }

    private void selectDrawerItem(MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.nav_profile_fragment:
                fragment = profileFragment;
                break;
            case R.id.nav_staff_fragment:
                fragment = tutorFragment;
                break;
            case R.id.navigation_home:
                fragment = homeFragment;
                break;
            case R.id.navigation_map:
                fragment = gmapFragment;
                break;
            default:
                break;
        }
        if(fragment != null) {
            //Pass in data
            fragment.setArguments(bundleData);

            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.flContent, fragment).commit();

            item.setChecked(true);
            setTitle(item.getTitle());
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }else{
            Log.e("MainActivity", "Error in creating fragment");
        }
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
    public void setTitle(CharSequence title){
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }
}
