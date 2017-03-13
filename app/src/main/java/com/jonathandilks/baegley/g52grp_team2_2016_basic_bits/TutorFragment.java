package com.jonathandilks.baegley.g52grp_team2_2016_basic_bits;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jonathandilks.baegley.g52grp_team2_2016_basic_bits.model.DummyData;

/**
 * Created by sunenhao on 12/03/2017.
 */

public class TutorFragment extends Fragment{
    private TextView tutorName;
    private DummyData dummyData = new DummyData();
    public TutorFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.tutorfragment, container, false);
        tutorName = (TextView) rootView.findViewById(R.id.tutorName);
        tutorName.setText(dummyData.getStudents().first().getTutor().getName());
        return rootView;
    }
}
