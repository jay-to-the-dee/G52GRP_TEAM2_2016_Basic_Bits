package com.jonathandilks.baegley.g52grp_team2_2016_basic_bits;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jonathandilks.baegley.g52grp_team2_2016_basic_bits.model.DummyData;
import com.jonathandilks.baegley.g52grp_team2_2016_basic_bits.model.Student;

/**
 * Created by sunenhao on 12/03/2017.
 */

public class ProfileFragment extends Fragment {
    private TextView sName;
    private TextView sEmail;
    private TextView sUsername;
    private DummyData dummyData = new DummyData();
    private Student student1 = dummyData.getStudents().last();
    public ProfileFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.profilefragment, container, false);
        addStudentInfo(student1, rootView);
        return rootView;
    }
    public void addStudentInfo(Student student, View rootView){
        sName = (TextView) rootView.findViewById(R.id.studentName);
        sName.setText(student.getName());
        sEmail = (TextView) rootView.findViewById(R.id.studentEmail);
        sEmail.setText(student.getEmail());
        sUsername = (TextView) rootView.findViewById(R.id.studentUser);
        sUsername.setText(student.getUserName());
    }
}
