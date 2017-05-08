package com.jonathandilks.baegley.g52grp_team2_2016_basic_bits;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.jonathandilks.baegley.g52grp_team2_2016_basic_bits.model.Data;
import com.jonathandilks.baegley.g52grp_team2_2016_basic_bits.model.Parser;
import com.jonathandilks.baegley.g52grp_team2_2016_basic_bits.model.Person;
import com.jonathandilks.baegley.g52grp_team2_2016_basic_bits.model.Staff;
import com.jonathandilks.baegley.g52grp_team2_2016_basic_bits.model.Student;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity
        implements SearchresultsFragment.OnProfileSelectedListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private CharSequence mTitle;

    private Data data;
    private Bundle bundleData;
    private Bundle searchData;

    private ProfileFragment profileFragment;
    private HomeFragment homeFragment;
    private GmapFragment gmapFragment;
    private SearchresultsFragment sresultFragment;
    private AboutFragment aboutFragment;


    public MainActivity() {
        //Initialise our fragments
        profileFragment = new ProfileFragment();
        homeFragment = new HomeFragment();
        gmapFragment = new GmapFragment();
        sresultFragment = new SearchresultsFragment();
        aboutFragment = new AboutFragment();

        // Initialise model
        data = new Data();
        bundleData = new Bundle();
        searchData = new Bundle();
    }

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
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        setupDrawerContent(navigationView);

        InputStream rdfStudentStream = getResources().openRawResource(R.raw.student_data);
        InputStream rdfStaffStream = getResources().openRawResource(R.raw.staff_data);

        Parser parser = new Parser(data);
        parser.doParse(rdfStudentStream, rdfStaffStream);



        bundleData.putSerializable("person", data);
        //Pass in data
        profileFragment.setSerial("person");
        profileFragment.setArguments(bundleData);

        //getSupportFragmentManager().beginTransaction().add(R.id.flContent, homeFragment).commit();

        handleIntent(getIntent());
    }

    public void onItemSelected(Person p){
        if(p.getClass() == Staff.class){
            profileFragment.setSerial("sstaff");
            profileFragment.updatePerson(p);
            switchFragment(profileFragment, "Profile");
        }else if(p.getClass() == Student.class){
            profileFragment.setSerial("sstudent");
            profileFragment.updatePerson(p);
            switchFragment(profileFragment, "Profile");
        }
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

    private void switchFragment(Fragment fragment, String title){
        if(fragment != null) {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.flContent, fragment).commit();
            setTitle(title);
        }else{
            Log.e("MainActivity", "Error in creating fragment");
        }
    }

    private void selectDrawerItem(MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.nav_profile_fragment:
                if(!profileFragment.getSerial().equalsIgnoreCase("student")) {
                    profileFragment.setSerial("Student");
                    if (getSupportFragmentManager().findFragmentById(R.id.flContent) instanceof ProfileFragment) {
                        profileFragment.updateProfile();
                    }
                }
                fragment = profileFragment;
                break;
            case R.id.nav_tutor_profile:
                if(!profileFragment.getSerial().equalsIgnoreCase("tutor")) {
                    profileFragment.setSerial("Tutor");
                    if (getSupportFragmentManager().findFragmentById(R.id.flContent) instanceof ProfileFragment) {
                        profileFragment.updateProfile();
                    }
                }
                fragment = profileFragment;
                break;
            case R.id.nav_home_fragment:
                fragment = homeFragment;
                break;
            case R.id.nav_map_fragment:
                fragment = gmapFragment;
                break;
            case R.id.nav_about_fragment:
                fragment = aboutFragment;
            default:
                break;
        }
        if(fragment != null) {
            item.setChecked(true);
            switchFragment(fragment, item.getTitle().toString());
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
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }
    private void handleIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doSearch(query);
        }
    }
    private void doSearch(String query){
        searchData.putSerializable(query, data);
        sresultFragment.setQuery(query);
        sresultFragment.setArguments(searchData);
        Fragment fragment = sresultFragment;
        switchFragment(fragment, "Result");
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_icon, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search_item).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        //searchView.setIconified(true);
        searchView.setQueryHint("Search...");
        return true;
    }
    @SuppressWarnings("StatementWithEmptyBody")

    @Override
    public void setTitle(CharSequence title){
        mTitle = title;
        getSupportActionBar().setTitle(mTitle);
    }

    public void onOfficeClick(View view) {
    //TODO: gmapFragment.setRoomNumberToFocus(officeString);
    }
}
