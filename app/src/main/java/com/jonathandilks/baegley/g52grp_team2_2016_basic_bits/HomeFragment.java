package com.jonathandilks.baegley.g52grp_team2_2016_basic_bits;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * Created by sunenhao on 12/03/2017.
 */

public class HomeFragment extends Fragment{
    private TextView home;
    public HomeFragment(){}

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.homefragment, container, false);
        home = (TextView) rootView.findViewById(R.id.home);
        home.setText("HHHHHome");
        return rootView;
    }
}
