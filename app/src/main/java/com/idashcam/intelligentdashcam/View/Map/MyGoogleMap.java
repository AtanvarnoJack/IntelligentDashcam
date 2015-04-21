package com.idashcam.intelligentdashcam.View.Map;

import android.location.Location;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.idashcam.intelligentdashcam.Analitycs.Map.MapConfiguration;
import com.idashcam.intelligentdashcam.R;
import com.idashcam.intelligentdashcam.View.ViewActivity.CameraActivity;

import static android.view.View.INVISIBLE;

/**
 * Created by alexandre on 15/04/2015.
 */
public class MyGoogleMap {
    public static final String TAG_MAP = CameraActivity.class.getSimpleName();
    public final static  int CONNECTION_FAILURE_RESOLUTION_REQUEST = 9000;

    private GoogleMap myMap;
    private Location myLocation;
    private LocationRequest mLocationRequest;
    private GoogleApiClient mGoogleApiClient;
    private MapConfiguration mapConfiguration;

    public MyGoogleMap() {}

    public void UpdateUI(LatLng mapCenter){
        Log.i(TAG_MAP, "Screen Updated with new location!");
        myMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapCenter, 13));
        myMap.addMarker(new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromResource(R.drawable.direction_arrow))
                .position(mapCenter)
                .flat(true)
                .rotation(245));

        CameraPosition cameraPosition = CameraPosition.builder()
                .target(mapCenter)
                .zoom(mapConfiguration.getZoom())
                .bearing(0)
                .build();
        myMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition), 500, null);
    }

    public void setMap(GoogleMap map) {
        map.setMyLocationEnabled(true);
        map.getUiSettings().setRotateGesturesEnabled(false);
        myMap = map;
        map.setMapType(mapConfiguration.getMapTypeInt());
    }

    public void initLocationRequest(){
        mLocationRequest = LocationRequest.create()
                .setPriority(mapConfiguration.getAccuracyInt())
                .setInterval(mapConfiguration.getInterval())
                .setFastestInterval(mapConfiguration.getFastInterval());
    }

    public void handleNewLocation(Location location) {
        Log.d(TAG_MAP,location.toString());
        double currentLatitude = location.getLatitude();
        double currentLongitude = location.getLongitude();
        LatLng latLng = new LatLng(currentLatitude,currentLongitude);
        UpdateUI(latLng);
    }

    public void setMapTransparency(View map) {
        if (!mapConfiguration.isTransparency()){
            map.clearAnimation();
        }else {
            AlphaAnimation alpha = new AlphaAnimation(0.7F, 0.7F);
            alpha.setDuration(0);
            alpha.setFillAfter(true);
            map.startAnimation(alpha);
        }
    }

    public void setMapVisibility(View map){
        int i = mapConfiguration.getMapVisibilityInt();
        if (i == View.INVISIBLE){
            map.clearAnimation();
            map.setVisibility(View.INVISIBLE);
        }else {
            map.setVisibility(View.VISIBLE);
            setMapTransparency(map);
        }
    }

    public GoogleMap getMyMap() {
        return myMap;
    }

    public void setMyMap(GoogleMap myMap) {
        this.myMap = myMap;
    }

    public GoogleApiClient getmGoogleApiClient() {
        return mGoogleApiClient;
    }

    public void setmGoogleApiClient(GoogleApiClient mGoogleApiClient) {
        this.mGoogleApiClient = mGoogleApiClient;
    }

    public LocationRequest getmLocationRequest() {
        return mLocationRequest;
    }

    public void setmLocationRequest(LocationRequest mLocationRequest) {
        this.mLocationRequest = mLocationRequest;
    }

    public Location getMyLocation() {
        return myLocation;
    }

    public void setMyLocation(Location myLocation) {
        this.myLocation = myLocation;
    }

    public MapConfiguration getMapConfiguration() {
        return mapConfiguration;
    }

    public void setMapConfiguration(MapConfiguration mapConfiguration) {
        this.mapConfiguration = mapConfiguration;
    }
}
