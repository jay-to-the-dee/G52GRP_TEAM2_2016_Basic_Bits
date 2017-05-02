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
import com.google.android.gms.maps.model.IndoorBuilding;
import com.google.android.gms.maps.model.IndoorLevel;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;


/**
 * Created by sunenhao on 12/03/2017.
 */

public class GmapFragment extends Fragment implements OnMapReadyCallback {
    static final LatLng CSbuilding = new LatLng(52.9533, -1.18724);

    private Spinner spinner;
    ArrayAdapter<CharSequence> adapter;
    private String roomNumberToFocus = null;
    private Marker roomMarker;

    public GmapFragment() {
    }

    public void setRoomNumberToFocus(String roomNumberToFocus) {
        this.roomNumberToFocus = roomNumberToFocus;
    }

    private static LatLng doRoomToLatLngLookup(String roomNumberToFocus) {
        switch (roomNumberToFocus) {
            case "C55":
                return new LatLng(52.953270, -1.187430);
            case "C32":
                return new LatLng(52.953165, -1.187550);
            case "A10":
                return new LatLng(52.953477, -1.187484);
            case "A09":
                return new LatLng(52.9534860, -1.1874136);
            case "C83":
                return new LatLng(52.953062, -1.187084);
            case "C14":
                return new LatLng(52.9534492, -1.1875737);
            case "C4":
                return new LatLng(52.953398, -1.187500);
            case "B74":
                return new LatLng(52.9529629, -1.1872688);
            case "C71":
                return new LatLng(52.952963, -1.1874452);
            case "C76":
                return new LatLng(52.952969, -1.187225);
            case "C78":
                return new LatLng(52.952998, -1.187045);
            case "C72":
                return new LatLng(52.952954, -1.187321);
            case "C82":
                return new LatLng(52.953073, -1.187018);
            case "C30":
                return new LatLng(52.953241, -1.187595);
            case "A40":
                return new LatLng(52.953262, -1.187468);
            case "A31":
                return new LatLng(52.9531879, -1.1874188);
            case "B80":
                return new LatLng(52.9530386, -1.1872382);
        }
        return null;
    }

    private static int doRoomToLevelLookup(String roomNumberToFocus) {
        switch (roomNumberToFocus.charAt(0)) {
            case 'A':
                return 2;
            case 'B':
                return 1;
            case 'C':
                return 0;
            default:
                return -1;
        }
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
        final float STRAIGHT_BEARING = -18.1f;
        final float DESIRED_ZOOM = 19.1f;

//        googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(
//                new CameraPosition.Builder()
//                        .target(CSbuilding)
//                        .tilt(0)
//                        .zoom((float) (DESIRED_ZOOM) - 1.5f)
//                        .build()));


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

        googleMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            @Override
            public void onMapLoaded() {
                if (roomNumberToFocus != null) {
                    int roomLevelIndex = doRoomToLevelLookup(roomNumberToFocus);
                    if (roomLevelIndex != -1) {
                        List<IndoorLevel> levels = googleMap.getFocusedBuilding().getLevels();
                        levels.get(roomLevelIndex).activate();

                        LatLng latLng = doRoomToLatLngLookup(roomNumberToFocus);
                        if (latLng != null) {
                            roomMarker = googleMap.addMarker(new MarkerOptions()
                                    .position(latLng)
                                    .title(roomNumberToFocus));
                        }
                    }
                }

                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(
                        new CameraPosition.Builder()
                                .target(CSbuilding)
                                .zoom(DESIRED_ZOOM)
                                .bearing(STRAIGHT_BEARING)
                                .build()));
            }
        });

        googleMap.setOnIndoorStateChangeListener(new GoogleMap.OnIndoorStateChangeListener() {

            @Override
            public void onIndoorBuildingFocused() {

            }

            @Override
            public void onIndoorLevelActivated(IndoorBuilding building) {
                if (roomMarker != null) {
                    int activeLevelIndex = building.getActiveLevelIndex();
                    int roomLevelIndex = doRoomToLevelLookup(roomNumberToFocus);

                    if (activeLevelIndex != roomLevelIndex) {
                        roomMarker.remove();
                        roomMarker = null;
                    }
                }
            }

        });
    }
}
