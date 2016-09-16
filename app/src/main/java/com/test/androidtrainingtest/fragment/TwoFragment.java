package com.test.androidtrainingtest.fragment;

/**
 * Created by Qushay on 15/09/2016.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.test.androidtrainingtest.R;
import com.test.androidtrainingtest.entity.User;

public class TwoFragment extends Fragment {

    private GoogleMap mGoogleMap;
    private MapView mMapView;
    private User mUser;

    public TwoFragment() { }


    public static TwoFragment newInstance(User user) {
        TwoFragment fragment = new TwoFragment();
        fragment.mUser = user;

        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        mMapView = (MapView) view.findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);
        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                mGoogleMap = mMap;
                LatLng latLng = new LatLng(mUser.getLatitude(),mUser.getLongitude());
                mGoogleMap.addMarker(new MarkerOptions().position(latLng).title(mUser.getName()));
                mGoogleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng,16));
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mMapView.onLowMemory();
    }

}