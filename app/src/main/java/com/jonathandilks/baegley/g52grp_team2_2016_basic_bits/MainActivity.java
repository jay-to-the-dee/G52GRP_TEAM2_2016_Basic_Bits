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
import android.widget.TextView;

import com.jonathandilks.baegley.g52grp_team2_2016_basic_bits.model.Data;
import com.jonathandilks.baegley.g52grp_team2_2016_basic_bits.model.Parser;
import com.jonathandilks.baegley.g52grp_team2_2016_basic_bits.model.Person;
import com.jonathandilks.baegley.g52grp_team2_2016_basic_bits.model.Staff;
import com.jonathandilks.baegley.g52grp_team2_2016_basic_bits.model.Student;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity
        implements SearchresultsFragment.OnProfileSelectedListener,
                   ProfileFragment.OnOfficeClickListener {

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private CharSequence mTitle;
    private TextView useremail;
    private TextView Name;

    private Data data;
    private Bundle bundleData;

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

        View header = navigationView.getHeaderView(0);
        useremail = (TextView) header.findViewById(R.id.userEmail);
        Name = (TextView) header.findViewById(R.id.userName);

        InputStream rdfStudentStream = getResources().openRawResource(R.raw.student_data);
        InputStream rdfStaffStream = getResources().openRawResource(R.raw.staff_data);
        InputStream rdfModuleStream = getResources().openRawResource(R.raw.module_data);

        Parser parser = new Parser(data);
        parser.doParse(rdfStudentStream, rdfStaffStream, rdfModuleStream);


        String username = getIntent().getStringExtra("user");
        String role = getIntent().getStringExtra("role");

        String name;
        String email;

        name = data.findStudent(username).getName();
        email = data.findStudent(username).getEmail();
        if(role.equalsIgnoreCase("student")) {
            name = data.findStudent(username).getName();
            email = data.findStudent(username).getEmail();
        }
        else {
            name = data.findStaff(username).getName();
            email = data.findStaff(username).getEmail();
            navigationView.getMenu().removeItem(R.id.nav_tutor_profile);
        }

        Name.setText(name);
        useremail.setText(email);


        bundleData.putSerializable("person", data);
        //Pass in data
        profileFragment.setSerial("person");
        profileFragment.setRole(role);
        profileFragment.setArguments(bundleData);
        profileFragment.setUserName(username);

        sresultFragment.setArguments(bundleData);

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
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);
        }else{
            Log.e("MainActivity", "Error in creating fragment");
        }
    }
    //search result -> profile ->map ->tutor ->map, didn't crash,   tutor->map->search result->profile ->map, crash

    private void selectDrawerItem(MenuItem item) {
        Fragment fragment = null;
        switch (item.getItemId()){
            case R.id.nav_profile_fragment:
                if(profileFragment.getRole().equalsIgnoreCase("student")) {
                    if (!profileFragment.getSerial().equalsIgnoreCase("student")) {
                        profileFragment.setSerial("Student");
                        if (getSupportFragmentManager().findFragmentById(R.id.flContent) instanceof ProfileFragment) {
                            profileFragment.updateProfile();
                        }
                    }
                }else{
                    if (!profileFragment.getSerial().equalsIgnoreCase("staff")) {
                        profileFragment.setSerial("staff");
                        if (getSupportFragmentManager().findFragmentById(R.id.flContent) instanceof ProfileFragment) {
                            profileFragment.updateProfile();
                        }
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
                break;
            case R.id.logout:
                Intent myIntent = new Intent(this,LoginActivity.class);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                this.startActivity(myIntent);
                this.finish();
                return;
            default:
                break;
        }
        if(fragment != null) {
            item.setChecked(true);
            switchFragment(fragment, item.getTitle().toString());
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
        sresultFragment.setQuery(query);
        Fragment fragment = sresultFragment;
        if (getSupportFragmentManager().findFragmentById(R.id.flContent) instanceof SearchresultsFragment)
            sresultFragment.Searching();
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

    public void onOfficeClick(String officeAddress) {
        gmapFragment.setRoomNumberToFocus(officeAddress);
        if(gmapFragment != null) {
            switchFragment(gmapFragment, "Map");
        }else{
            Log.e("MainActivity", "Error in creating fragment");
        }
    }
}
