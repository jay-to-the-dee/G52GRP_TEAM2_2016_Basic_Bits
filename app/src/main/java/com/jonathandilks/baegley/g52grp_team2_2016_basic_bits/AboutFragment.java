package com.jonathandilks.baegley.g52grp_team2_2016_basic_bits;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jonathandilks.baegley.g52grp_team2_2016_basic_bits.R;

/**
 * Created by sunenhao on 2017/5/3.
 */

public class AboutFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.aboutfragment, container, false);
    }
}
