package com.jonathandilks.baegley.g52grp_team2_2016_basic_bits;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jonathandilks.baegley.g52grp_team2_2016_basic_bits.R;

/**
 * Created by sunenhao on 2017/5/3.
 */

public class AboutFragment extends Fragment {
    private TextView about;
    private TextView copyRight;
    private ImageView icon;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.aboutfragment, container, false);

        about = (TextView) rootView.findViewById(R.id.about);
        copyRight = (TextView) rootView.findViewById(R.id.copyright);
        icon = (ImageView) rootView.findViewById(R.id.about_icon);

        String aboutText = "The mobile application acts as an information directory for the University of Nottingham." +
                "The app allows the users to find out any pertinent information they might need.";

        about.setText(aboutText);
        String copyRightText = "Copyright 2017, BasicBits\n All rights reserved";
        copyRight.setText(copyRightText);

        icon.setImageResource(R.mipmap.uonicon);
        return rootView;
    }


}