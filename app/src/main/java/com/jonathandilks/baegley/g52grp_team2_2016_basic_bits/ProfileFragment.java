package com.jonathandilks.baegley.g52grp_team2_2016_basic_bits;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jonathandilks.baegley.g52grp_team2_2016_basic_bits.model.*;

/**
 * Created by sunenhao on 12/03/2017.
 */

public class ProfileFragment extends Fragment {
    private Student student;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.profilefragment, container, false);
    }

    @Override
    public void onStart(){
        super.onStart();
        Bundle extras = getArguments();
        if(extras != null) {
            Data data = (Data) extras.getSerializable("data");
            student = data.getStudents().first();
            updateProfile((student));
        }
    }
    public void updateProfile(Person person){
        TextView name = (TextView) getActivity().findViewById(R.id.studentName);
        name.setText(person.getName());
        TextView email = (TextView) getActivity().findViewById(R.id.studentEmail);
        email.setText(person.getEmail());
        TextView username = (TextView) getActivity().findViewById(R.id.studentUser);
        username.setText(person.getUserName());
    }

    @Override
    public void onSaveInstanceState(Bundle outState){
        super.onSaveInstanceState(outState);
    }

}
