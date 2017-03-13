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

public class MapFragment extends Fragment{
    private TextView tmap;

    public MapFragment(){}
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.mapfragment, container, false);
        tmap = (TextView) rootView.findViewById(R.id.tmap);
        tmap.setText("This is Map");
        return rootView;
    }

}
