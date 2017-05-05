package com.jonathandilks.baegley.g52grp_team2_2016_basic_bits;

import android.os.Bundle;
import android.support.annotation.Nullable;
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
    private LinearLayout optionalContent;
    private ExpandableListAdapter listAdapter;


    private List<String> listmoduleHeader;
    private HashMap<String, List<String>> listmoduleHash;
    private List<String> listoptionalHeader;
    private HashMap<String, List<String>> listoptionalHash;

    private SortedSet<Module> modules;
    private String serial;

    private Student student;
    private Staff staff;


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

        optionalContent = (LinearLayout) rootView.findViewById(R.id.optionalTab);

        return rootView;
    }

    @Override
    public void onStart(){
        super.onStart();

        if(serial.equalsIgnoreCase("student")) {
            student = data.getStudents().last();
            setTabTitle("Module", "Marks");
            update(student, this.getView());
            modules = student.getModulesEnrolled();
        }else if(serial.equalsIgnoreCase("tutor")) {
            // need to be fixed
            // student.getTutor : this staff only have tutees
            // data.getStaff: this staff only has it's own data, no tutees

            //staff = data.getStudents().last().getTutor();
            staff = data.getStaff().first();
            setTabTitle("Module", "Tutees");
            update(staff, this.getView());
            modules = staff.getModulesTaught();
        }else if(serial.equalsIgnoreCase("sstaff")){
            setTabTitle("Module", "Tutees");
            update(staff, this.getView());
            modules = staff.getModulesTaught();
        }else if(serial.equalsIgnoreCase("sstudent")){
            setTabTitle("Module", "Marks");
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

        // need to be fixed
        student = data.getStudents().last();
        staff = student.getTutor();


        if (serial.equalsIgnoreCase("tutor")) {
            update(staff, this.getView());
            LinearLayout opTab = (LinearLayout) host.getTabWidget().getChildTabViewAt(1);
            final TextView title = (TextView) opTab.findViewById(android.R.id.title);
            title.setText("Tutees");
        } else if (serial.equalsIgnoreCase("student")) {
            update(student, this.getView());
            LinearLayout opTab = (LinearLayout) host.getTabWidget().getChildTabViewAt(1);
            final TextView title = (TextView) opTab.findViewById(android.R.id.title);
            title.setText("Marks");
        }
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
        TextView office = (TextView) rootView.findViewById(R.id.Office);
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
    private void setTabTitle(String first, String second){
        TabHost.TabSpec spec = host.newTabSpec(first);
        spec.setContent(R.id.moduleTab);
        spec.setIndicator(first);
        host.addTab(spec);
        spec = host.newTabSpec(second);
        spec.setContent(R.id.optionalTab);
        spec.setIndicator(second);
        host.addTab(spec);
    }

    public void initData(){
        listmoduleHeader = new ArrayList<>();
        listmoduleHash = new HashMap<>();
        listoptionalHeader = new ArrayList<>();
        listoptionalHash = new HashMap<>();

        int index = 0;
        for(Module module:modules)
        {
            if(serial.equalsIgnoreCase("student")||serial.equalsIgnoreCase("sstudent")) {
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
        if(serial.equalsIgnoreCase("tutor")||serial.equalsIgnoreCase("sstaff")) {
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

    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }
}
