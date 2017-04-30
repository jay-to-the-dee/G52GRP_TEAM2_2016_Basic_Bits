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

import com.jonathandilks.baegley.g52grp_team2_2016_basic_bits.model.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.SortedSet;


/**
 * Created by sunenhao on 04/04/2017.
 */

public class CastleFragment extends Fragment {

    private TabHost host;

    private ExpandableListView moduleList;
    private ExpandableListView markList;
    private ExpandableListView surveyList;
    private LinearLayout moduleContent;
    private LinearLayout markContent;
    private LinearLayout surveyContent;

    private ExpandableListAdapter listAdapter1;
    private ExpandableListAdapter listAdapter2;

    private List<String> listmoduleHeader;
    private HashMap<String, List<String>> listmoduleHash;
    private List<String> listMarkHeader;
    private HashMap<String, List<String>> listmarkHash;

    private SortedSet<Module> modules;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        Bundle extras = getArguments();
        Data data = (Data) extras.getSerializable("data");
        View rootView = inflater.inflate(R.layout.castlefragment, container, false);
        // Tab module
        host = (TabHost) rootView.findViewById(R.id.studentTabHost);
        host.setup();
        setTabTitle();

        moduleList = (ExpandableListView) rootView.findViewById(R.id.myModuleList);
        markList = (ExpandableListView) rootView.findViewById(R.id.myMarkList);
        surveyList = (ExpandableListView) rootView.findViewById(R.id.mySurveyList);

        moduleContent = (LinearLayout) rootView.findViewById(R.id.myModuleTab);
        markContent = (LinearLayout) rootView.findViewById(R.id.myMarkTab);
        markContent.setVisibility(View.GONE);
        surveyContent = (LinearLayout) rootView.findViewById(R.id.mySurveyTab);
        surveyContent.setVisibility(View.GONE);

        modules = data.getStudents().first().getModulesEnrolled();
        initData();

        listAdapter1 = new ExpandableListAdapter(getContext(), listmoduleHeader, listmoduleHash);
        moduleList.setAdapter(listAdapter1);
        listAdapter2 = new ExpandableListAdapter(getContext(), listMarkHeader, listmarkHash);
        markList.setAdapter(listAdapter2);
        //surveyList.setAdapter(listAdapter);
        setHostContent(host);
        return rootView;
    }

    public void setHostContent(TabHost host){
        host.setOnTabChangedListener(new TabHost.OnTabChangeListener() {
            @Override
            public void onTabChanged(String tabId) {
                switch (tabId)
                {
                    case "myModuleTab":
                        moduleContent.setVisibility(View.VISIBLE);
                        markContent.setVisibility(View.GONE);
                        break;
                    case "myMarkTab":
                        moduleContent.setVisibility(View.GONE);
                        markContent.setVisibility(View.VISIBLE);
                        break;
                    default:break;
                }
            }
        });
    }
    public void setTabTitle(){
        TabHost.TabSpec spec = host.newTabSpec("Module");
        spec.setContent(R.id.myModuleTab);
        spec.setIndicator("MODULE");
        host.addTab(spec);
        spec = host.newTabSpec("Marks");
        spec.setContent(R.id.myMarkTab);
        spec.setIndicator("Marks");
        host.addTab(spec);
        spec = host.newTabSpec("Survey");
        spec.setContent(R.id.mySurveyTab);
        spec.setIndicator("Survey");
        host.addTab(spec);
    }

    public void initData(){
        listmoduleHeader = new ArrayList<>();
        listmoduleHash = new HashMap<>();
        listMarkHeader = new ArrayList<>();
        listmarkHash = new HashMap<>();
        List<String> moduleDetails = new ArrayList<>();
        List<String> mark = new ArrayList<>();
        int index = 0;
        for(Module module:modules)
        {
            listmoduleHeader.add(module.getModuleCode());
            moduleDetails.add(module.getModuleName());
            moduleDetails.add(String.valueOf(module.getModuleSemester()));
            listmoduleHash.put(listmoduleHeader.get(index), moduleDetails);
            index++;
        }
        listMarkHeader.add("G52ACE");
        listMarkHeader.add("G52ACE");
        listMarkHeader.add("G52ACE");
        listMarkHeader.add("G52ACE");
        listMarkHeader.add("G52ACE");
        listMarkHeader.add("G52ACE");
        listMarkHeader.add("G52ACE");
        listMarkHeader.add("G52ACE");
        listMarkHeader.add("G52ACE");
        listMarkHeader.add("G52ACE");
        listMarkHeader.add("G52ACE");
        listMarkHeader.add("G52ACE");
        listMarkHeader.add("G52ACE");
        listMarkHeader.add("G52ACE");
        listMarkHeader.add("G52ACE");
        listMarkHeader.add("G52ACE");
        listMarkHeader.add("G52ACE");
        listMarkHeader.add("G52ACE");
        listMarkHeader.add("G52ACE");
        mark.add("Your mark: 40");
        for(index = 0; index < listMarkHeader.size(); index++)
            listmarkHash.put(listMarkHeader.get(index), mark);
    }


}
