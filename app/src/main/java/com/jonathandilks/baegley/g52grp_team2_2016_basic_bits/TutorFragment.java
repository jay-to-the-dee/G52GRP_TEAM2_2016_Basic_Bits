package com.jonathandilks.baegley.g52grp_team2_2016_basic_bits;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.TextView;

import com.jonathandilks.baegley.g52grp_team2_2016_basic_bits.model.Data;
import com.jonathandilks.baegley.g52grp_team2_2016_basic_bits.model.DummyData;
import com.jonathandilks.baegley.g52grp_team2_2016_basic_bits.model.Staff;

/**
 * Created by sunenhao on 12/03/2017.
 */

public class TutorFragment extends Fragment{
    private TextView tutorName;
    private TextView tutorEmail;
    private TextView tutorOffice;
    private TextView tutorPhone;
    private TabHost host;

    private Staff staff1;

    public TutorFragment(Data data) {
        this.staff1 = data.getStudents().last().getTutor();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.tutorfragment, container, false);
        addTutorInfo(staff1, rootView);
        return rootView;
    }
    public void addTutorInfo(Staff staff, View rootView){
        tutorName = (TextView) rootView.findViewById(R.id.tutorName);
        tutorEmail = (TextView) rootView.findViewById(R.id.tutorEmail);
        tutorOffice = (TextView) rootView.findViewById(R.id.tutorOffice);
        tutorPhone = (TextView) rootView.findViewById(R.id.tutorPhone);
        host = (TabHost) rootView.findViewById(R.id.tabHost);
        host.setup();
        // Tab module
        TabHost.TabSpec spec = host.newTabSpec("MODULE");
        spec.setContent(R.id.tabModule);
        spec.setIndicator("MODULE");
        host.addTab(spec);

        spec = host.newTabSpec("TUTEES");
        spec.setContent(R.id.tabTutees);
        spec.setIndicator("TUTEES");
        host.addTab(spec);

        tutorName.setText(staff.getName());
        tutorEmail.setText(staff.getEmail());
        tutorOffice.setText(staff.getOffice());
        tutorPhone.setText(staff.getPhoneNo());
    }
}
