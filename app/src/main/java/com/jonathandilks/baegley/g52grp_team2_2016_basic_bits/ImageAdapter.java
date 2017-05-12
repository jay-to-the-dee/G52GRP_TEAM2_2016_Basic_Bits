package com.jonathandilks.baegley.g52grp_team2_2016_basic_bits;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.jonathandilks.baegley.g52grp_team2_2016_basic_bits.R;
import com.jonathandilks.baegley.g52grp_team2_2016_basic_bits.model.Person;

import java.util.Iterator;
import java.util.SortedSet;

/**
 * Created by sunenhao on 2017/5/2.
 */

public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private SortedSet<Person> results;
    private Iterator<Person> iterator;
    ImageAdapter(Context c, SortedSet<Person> r){
        mContext = c;
        results = r;
        iterator = results.iterator();
    }

    @Override
    public int getCount() {
        return results.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridView;
        LayoutInflater inflater = (LayoutInflater) mContext.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(convertView == null){
            gridView = new View(mContext);
            gridView = inflater.inflate(R.layout.gridview_layout, null);
            ImageView imageView = (ImageView) gridView.findViewById(R.id.sr_image);
            TextView depart = (TextView) gridView.findViewById(R.id.sr_depart);
            TextView name = (TextView) gridView.findViewById(R.id.sr_name);

            imageView.setImageResource(mThumbIds[0]);
            depart.setText("Computer Science");
            if(iterator.hasNext()){
                name.setText(iterator.next().getName());
            }else{
                name.setText(null);
            }
        } else {
            gridView = convertView;
        }
        return gridView;
    }

    // reference to our images
    private Integer[] mThumbIds = {
            R.mipmap.uonicon
    };
}
