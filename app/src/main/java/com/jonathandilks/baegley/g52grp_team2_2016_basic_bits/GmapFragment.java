package com.jonathandilks.baegley.g52grp_team2_2016_basic_bits;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;


/**
 * Created by sunenhao on 12/03/2017.
 */

public class GmapFragment extends Fragment implements OnMapReadyCallback {
    private Spinner spinner;
    ArrayAdapter<CharSequence> adapter;

    public GmapFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.mapfragment, container, false);
        spinner = (Spinner) rootView.findViewById(R.id.spinner);
        adapter = ArrayAdapter.createFromResource(getActivity().getBaseContext(), R.array.map_types, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);
        spinner.setSelection(1); //Hybrid mode is now default

        return rootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        SupportMapFragment fragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        fragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(final GoogleMap googleMap) {
        final LatLng CSbuilding = new LatLng(52.9533, -1.18724);
        final float STRAIGHT_BEARING = -18.1f;
        final float DESIRED_ZOOM = 19.1f;

//        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(
//                new CameraPosition.Builder()
//                        .target(CSbuilding)
//                        .tilt(0)
//                        .zoom((float) (DESIRED_ZOOM) - 1.5f)
//                        .build()));

        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                new CameraPosition.Builder()
                        .target(CSbuilding)
                        .zoom(DESIRED_ZOOM)
                        .bearing(STRAIGHT_BEARING)
                        .build()));

        googleMap.setMinZoomPreference(15);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch ((int) id) {
                    case 0:
                        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                        break;
                    case 1:
                        googleMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                        break;
                    case 2:
                        googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                        break;
                    case 3:
                        googleMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                        break;
                }
            }


            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}
