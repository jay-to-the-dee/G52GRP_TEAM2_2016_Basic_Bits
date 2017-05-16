package com.jonathandilks.baegley.g52grp_team2_2016_basic_bits;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.jonathandilks.baegley.g52grp_team2_2016_basic_bits.model.Data;
import com.jonathandilks.baegley.g52grp_team2_2016_basic_bits.model.Person;

import java.util.Iterator;
import java.util.SortedSet;

/**
 * Created by sunenhao on 2017/5/2.
 */

public class SearchresultsFragment extends Fragment {

    private Data data;
    private GridView gridView;
    private SortedSet<Person> searchResults;
    private String query;
    OnProfileSelectedListener mCallback;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.search_results, container, false);
        Bundle extras = getArguments();
        data = (Data) extras.getSerializable("person");


        gridView = (GridView) rootView.findViewById(R.id.gridView);
        /*
        gridView.setAdapter(new ImageAdapter(getContext(), searchResults));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Iterator<Person> iterator = searchResults.iterator();
                Person person = iterator.next();
                int i = 0;
                while(iterator.hasNext()){
                    if(i == position)
                        break;
                    else
                        i++;
                    person = iterator.next();
                }
                mCallback.onItemSelected(person);
            }
        });
        */

        return rootView;
    }


    @Override
    public void onStart() {
        super.onStart();
        Searching();
    }

    public void Searching(){

        searchResults = data.getSubset(query);
        gridView.setAdapter(new ImageAdapter(getContext(), searchResults));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){
                Iterator<Person> iterator = searchResults.iterator();
                Person person = iterator.next();
                int i = 0;
                while(iterator.hasNext()){
                    if(i == position)
                        break;
                    else
                        i++;
                    person = iterator.next();
                }
                mCallback.onItemSelected(person);
            }
        });
    }
    public interface OnProfileSelectedListener{
        void onItemSelected(Person p);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof OnProfileSelectedListener){
            mCallback = (OnProfileSelectedListener) context;
        }else{
            throw new ClassCastException(context.toString()
                    + " must implement OnProfileSelectedListener");
        }
    }



    public void setQuery(String q){
        query = q;
    }
}
