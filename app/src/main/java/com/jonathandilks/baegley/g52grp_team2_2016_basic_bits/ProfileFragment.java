package com.jonathandilks.baegley.g52grp_team2_2016_basic_bits;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.jonathandilks.baegley.g52grp_team2_2016_basic_bits.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.SortedSet;

/**
 * Created by sunenhao on 12/03/2017.
 */

public class ProfileFragment extends Fragment {

    private TabHost host;
    private Data data;
    private ExpandableListView moduleList;
    private ExpandableListView optionalList;
    private ExpandableListAdapter listAdapter;


    private List<String> listmoduleHeader;
    private HashMap<String, List<String>> listmoduleHash;
    private List<String> listoptionalHeader;
    private HashMap<String, List<String>> listoptionalHash;

    public static ArrayList<TabHost.TabSpec> list = new ArrayList<TabHost.TabSpec>();

    private SortedSet<Module> modules;
    private String serial;

    private Student student;
    private Staff staff;
    private String username;
    private String role;

    OnOfficeClickListener mCallback;


    public interface OnOfficeClickListener {
        void onOfficeClick(String officeAddress);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnOfficeClickListener){
            mCallback = (OnOfficeClickListener) context;
        }else{
            throw new ClassCastException(context.toString()
                    + " must implement OnOfficeClickListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.profilefragment, container, false);

        host = (TabHost) rootView.findViewById(R.id.tabHost);
        host.setup();

        data = (Data) getArguments().getSerializable("person");


        moduleList = (ExpandableListView) rootView.findViewById(R.id.moduleList);
        optionalList = (ExpandableListView) rootView.findViewById(R.id.optionalList);

        TabHost.TabSpec spec;
        list.clear();
        spec = host.newTabSpec("Module").setIndicator("Module").setContent(R.id.moduleTab);
        list.add(spec);
        spec = host.newTabSpec("Marks").setIndicator("Marks").setContent(R.id.optionalTab);
        list.add(spec);
        spec = host.newTabSpec("Tutees").setIndicator("Tutees").setContent(R.id.optionalTab);
        list.add(spec);

        return rootView;
    }

    @Override
    public void onStart(){
        super.onStart();

        if(serial.equalsIgnoreCase("student")) {
            student = data.findStudent(username);
            setTabTitle();
            update(student, this.getView());
            modules = student.getModulesEnrolled();
        }else if(serial.equalsIgnoreCase("tutor")) {
            staff = data.findStudent(username).getTutor();
            setTabTitle();
            update(staff, this.getView());
            modules = staff.getModulesTaught();
        }else if(serial.equalsIgnoreCase("staff")){
            staff = data.findStaff(username);
            setTabTitle();
            update(staff, this.getView());
            modules = staff.getModulesTaught();
        }else if(serial.equalsIgnoreCase("sstaff")){
            setTabTitle();
            update(staff, this.getView());
            modules = staff.getModulesTaught();
        }else if(serial.equalsIgnoreCase("sstudent")){
            setTabTitle();
            update(student, this.getView());
            modules = student.getModulesEnrolled();
        }
        initData();

        listAdapter = new ExpandableListAdapter(getContext(), listmoduleHeader, listmoduleHash);
        moduleList.setAdapter(listAdapter);
        listAdapter = new ExpandableListAdapter(getContext(), listoptionalHeader, listoptionalHash);
        optionalList.setAdapter(listAdapter);

        setHostContent(host);
    }
    public void updatePerson(Person p){
        if(p.getClass() == Staff.class)
            staff = (Staff) p;
        if(p.getClass() == Student.class)
            student = (Student) p;
    }
    public void updateProfile(){

        if(role.equalsIgnoreCase("student")) {
            student = data.findStudent(username);
            staff = student.getTutor();
            if (serial.equalsIgnoreCase("tutor")) {
                update(staff, this.getView());
                modules = staff.getModulesTaught();
            } else if (serial.equalsIgnoreCase("student")) {
                update(student, this.getView());
                modules = student.getModulesEnrolled();
            }
        }else{
            staff= data.findStaff(username);
            update(staff, this.getView());
            modules = staff.getModulesTaught();
        }
        setTabTitle();
        initData();
        listAdapter = new ExpandableListAdapter(getContext(), listmoduleHeader, listmoduleHash);
        moduleList.setAdapter(listAdapter);
        listAdapter = new ExpandableListAdapter(getContext(), listoptionalHeader, listoptionalHash);
        optionalList.setAdapter(listAdapter);
    }
    private void update(Person person, View rootView){
        TextView name = (TextView) rootView.findViewById(R.id.Name);
        TextView email = (TextView) rootView.findViewById(R.id.Email);
        TextView username = (TextView) rootView.findViewById(R.id.User);
        final TextView office = (TextView) rootView.findViewById(R.id.Office);
        TextView phone = (TextView) rootView.findViewById(R.id.Phone);

        if(person.getClass() == Student.class) {
            name.setText(student.getName());
            email.setText(student.getEmail());
            username.setText(student.getUserName());
            office.setText(null);
            phone.setText(null);
        }
        if(person.getClass() == Staff.class){
            name.setText(staff.getName());
            email.setText(staff.getEmail());
            username.setText(staff.getUserName());
            office.setText(staff.getOffice());
            office.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onOfficeClick(office.getText().toString());
                }
            });
            phone.setText(staff.getPhoneNo());
        }
    }


    private void setHostContent(final TabHost h){
        h.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                h.setCurrentTabByTag(tabId);
            }
        });
    }
    private void setTabTitle(){
        host.clearAllTabs();
        if(serial.equalsIgnoreCase("student")||serial.equalsIgnoreCase("sstudent")){
            host.addTab(list.get(0));
            host.addTab(list.get(1));
        }else{
            host.addTab(list.get(0));
            host.addTab(list.get(2));
        }
    }

    public void initData(){
        listmoduleHeader = new ArrayList<>();
        listmoduleHash = new HashMap<>();
        listoptionalHeader = new ArrayList<>();
        listoptionalHash = new HashMap<>();

        int index = 0;
        for(Module module:modules)
        {
            if((serial.equalsIgnoreCase("student")||serial.equalsIgnoreCase("sstudent"))) {
                listoptionalHeader.add(module.getModuleCode());
                List<String> optional = new ArrayList<>();
                optional.add("Your mark: 80");
                listoptionalHash.put(listoptionalHeader.get(index), optional);
            }
            listmoduleHeader.add(module.getModuleCode());
            List<String> moduleDetails = new ArrayList<>();
            moduleDetails.add(module.getModuleName());
            moduleDetails.add(String.valueOf(module.getModuleSemester()));
            listmoduleHash.put(listmoduleHeader.get(index), moduleDetails);
            index++;
        }
        if(serial.equalsIgnoreCase("tutor")||serial.equalsIgnoreCase("sstaff")||serial.equalsIgnoreCase("staff")) {
            index = 0;
            for(Student tutee:staff.getTutees()) {
                listoptionalHeader.add(tutee.getName());
                List<String> optional = new ArrayList<>();
                optional.add(tutee.getEmail());
                optional.add("Computer Science"); // department
                listoptionalHash.put(listoptionalHeader.get(index), optional);
                index++;
            }
        }
    }

    public void setSerial(String s) {
        serial = s;
    }
    public String getSerial() {
        return serial;
    }
    public void setUserName(String username)
    {
        this.username = username;
    }
    public void setRole(String s)
    {
        role = s;
    }
    public String getRole()
    {
        return role;
    }

    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }
}
