package com.idashcam.intelligentdashcam.Analitycs.Map;

import android.view.View;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.maps.GoogleMap;
import com.idashcam.intelligentdashcam.Analitycs.Map.Enum.Accuracy;
import com.idashcam.intelligentdashcam.Analitycs.Map.Enum.MapType;
import com.idashcam.intelligentdashcam.Analitycs.Map.Enum.Visibility;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexandre on 15/04/2015.
 */
public class MapConfiguration {
    private final static int MS_INTERVAL_MULTIPLIER = 1000; //1sec = 1000ms
    private final static int FAST_INTERVAL = 10; //10sec
    private final static int INTERVAL = 1; //1sec
    private final static int ZOOM = 14;
    private final static int ZOOM_MAX = 22;
    private final static int ZOOM_MIN = 4;

    private int id;
    private String mapType;
    private String mapVisibility;
    private int interval;
    private int fastInterval;
    private String accuracy;
    private int Zoom;
    private boolean transparency;

    public MapConfiguration() {}

    public MapConfiguration (MapConfiguration mapConfiguration){
        this.mapType = mapConfiguration.getMapTypeString();
        this.mapVisibility = mapConfiguration.getMapVisibilityString();
        this.interval = mapConfiguration.getInterval() * MS_INTERVAL_MULTIPLIER;
        this.fastInterval = mapConfiguration.getFastInterval() * MS_INTERVAL_MULTIPLIER;
        this.accuracy = mapConfiguration.getAccuracyString();
        this.Zoom = mapConfiguration.getZoom();
        this.transparency = mapConfiguration.isTransparency();
    }

    public MapConfiguration(String mapType, String mapVisibility, int fastInterval, int minFastInterval, String accuracy, int initZoom, boolean transparency) {
        this.mapType = mapType;
        this.mapVisibility = mapVisibility;
        this.interval = fastInterval * MS_INTERVAL_MULTIPLIER;
        this.fastInterval = minFastInterval * MS_INTERVAL_MULTIPLIER;
        this.accuracy = accuracy;
        this.Zoom = initZoom;
        this.transparency = transparency;
    }

    public static MapConfiguration initMapConfiguration() {
        MapConfiguration mapConfiguration = new MapConfiguration();

        mapConfiguration.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mapConfiguration.setMapVisibility(View.VISIBLE);
        mapConfiguration.setInterval(FAST_INTERVAL);
        mapConfiguration.setFastInterval(INTERVAL);
        mapConfiguration.setAccuracy(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mapConfiguration.setZoom(ZOOM);
        mapConfiguration.setTransparency(true);

        return mapConfiguration;
    }

    public static int getMsIntervalMultiplier() {
        return MS_INTERVAL_MULTIPLIER;
    }

    public String getMapTypeString(){
        return this.mapType;
    }
    public int getMapTypeInt() {
        int googleMapInt = GoogleMap.MAP_TYPE_NORMAL;
        switch (MapType.valueOf(this.mapType)){
            case Normal:
                googleMapInt = GoogleMap.MAP_TYPE_NORMAL;
                break;
            case Hybrid:
                googleMapInt = GoogleMap.MAP_TYPE_HYBRID;
                break;
            case Terrain:
                googleMapInt = GoogleMap.MAP_TYPE_TERRAIN;
                break;
            case None:
                googleMapInt = GoogleMap.MAP_TYPE_NONE;
                break;
            case Satellite:
                googleMapInt = GoogleMap.MAP_TYPE_SATELLITE;
                break;
        }
        return googleMapInt;
    }

    public void setMapType(String mapType) {
        this.mapType = mapType;
    }
    public void setMapType(int intMapType) {
        String mapType = MapType.Normal.name();
        switch (intMapType){
            case GoogleMap.MAP_TYPE_NORMAL:
                mapType = MapType.Normal.name();
                break;
            case GoogleMap.MAP_TYPE_HYBRID:
                mapType = MapType.Hybrid.name();
                break;
            case GoogleMap.MAP_TYPE_TERRAIN:
                mapType = MapType.Terrain.name();
                break;
            case GoogleMap.MAP_TYPE_NONE:
                mapType = MapType.None.name();
                break;
            case GoogleMap.MAP_TYPE_SATELLITE:
                mapType = MapType.Satellite.name();
                break;
        }
        this.mapType = mapType;
    }

    public String getMapVisibilityString() {
        return this.mapVisibility;
    }
    public int getMapVisibilityInt() {
        int visibility = View.VISIBLE;
        switch (Visibility.valueOf(this.mapVisibility)){
            case Invisible:
                visibility = View.INVISIBLE;
                break;
            case Visible:
                visibility = View.VISIBLE;
                break;
        }
        return visibility;
    }

    public void setMapVisibility(String mapVisibility) {
        this.mapVisibility = mapVisibility;
    }
    public void setMapVisibility(int mapVisibility) {
        String tmpMapVisibility = Visibility.Visible.name();
        switch (mapVisibility) {
            case View.INVISIBLE:
                tmpMapVisibility = Visibility.Invisible.name();
                break;
            case View.VISIBLE:
                tmpMapVisibility = Visibility.Visible.name();
                break;
        }
        this.mapVisibility = tmpMapVisibility;
    }

    public int getInterval() {
        int newInterval = interval / MS_INTERVAL_MULTIPLIER;
        return newInterval;
    }

    public void setInterval(int interval) {
        this.interval = (interval * MS_INTERVAL_MULTIPLIER);
    }

    public int getFastInterval() {
        int newFastInterval = fastInterval / MS_INTERVAL_MULTIPLIER;
        return newFastInterval;
    }

    public void setFastInterval(int fastInterval) {
        this.fastInterval = (fastInterval * MS_INTERVAL_MULTIPLIER);
    }

    public String getAccuracyString() {
        return this.accuracy;
    }
    public int getAccuracyInt() {
        int accuracy = LocationRequest.PRIORITY_HIGH_ACCURACY;
        switch (Accuracy.valueOf(this.accuracy)){
            case High:
                accuracy = LocationRequest.PRIORITY_HIGH_ACCURACY;
                break;
            case Low:
                accuracy = LocationRequest.PRIORITY_LOW_POWER;
                break;
            case NoPower:
                accuracy = LocationRequest.PRIORITY_NO_POWER;
                break;
            case BalancedPower:
                accuracy = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY;
                break;
        }
        return accuracy;
    }

    public void setAccuracy(String accuracy) {
        this.accuracy = accuracy;
    }
    public void setAccuracy(int intAccuracy) {
        String accuracy = Accuracy.High.name();
        switch (intAccuracy){
            case LocationRequest.PRIORITY_HIGH_ACCURACY:
                accuracy = Accuracy.High.name();
                break;
            case  LocationRequest.PRIORITY_LOW_POWER:
                accuracy = Accuracy.Low.name();
                break;
            case LocationRequest.PRIORITY_NO_POWER:
                accuracy = Accuracy.NoPower.name();
                break;
            case LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY:
                accuracy = Accuracy.BalancedPower.name();
                break;
        }
        this.accuracy = accuracy;
    }

    public int getZoom() {
        return Zoom;
    }

    public void setZoom(int zoom) {
        this.Zoom = zoom;
    }

    public boolean isTransparency() {
        return transparency;
    }

    public void setTransparency(boolean transparency) {
        this.transparency = transparency;
    }

    public int getMaxZoomLevel(){
        return ZOOM_MAX;
    }

    public int getMinZoomLevel(){
        return ZOOM_MIN;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MapConfiguration that = (MapConfiguration) o;

        if (Zoom != that.Zoom) return false;
        if (fastInterval != that.fastInterval) return false;
        if (id != that.id) return false;
        if (interval != that.interval) return false;
        if (transparency != that.transparency) return false;
        if (accuracy != null ? !accuracy.equals(that.accuracy) : that.accuracy != null)
            return false;
        if (mapType != null ? !mapType.equals(that.mapType) : that.mapType != null) return false;
        if (mapVisibility != null ? !mapVisibility.equals(that.mapVisibility) : that.mapVisibility != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (mapType != null ? mapType.hashCode() : 0);
        result = 31 * result + (mapVisibility != null ? mapVisibility.hashCode() : 0);
        result = 31 * result + interval;
        result = 31 * result + fastInterval;
        result = 31 * result + (accuracy != null ? accuracy.hashCode() : 0);
        result = 31 * result + Zoom;
        result = 31 * result + (transparency ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "MapConfiguration{" +
                "id=" + id +
                ", mapType='" + mapType + '\'' +
                ", mapVisibility='" + mapVisibility + '\'' +
                ", interval=" + interval +
                ", fastInterval=" + fastInterval +
                ", accuracy='" + accuracy + '\'' +
                ", Zoom=" + Zoom +
                ", transparency=" + transparency +
                '}';
    }

    public List<String> getAllMapType(){
        List<String> listMapType = new ArrayList<String>();
        listMapType.add("Normal");
        listMapType.add("Hybrid");
        listMapType.add("Terrain");
        listMapType.add("Satellite");
        listMapType.add("None");
        return listMapType;
    }

    public List<String> getAllVisibility(){
        List<String> listVisibility = new ArrayList<String>();
        listVisibility.add("Invisible");
        listVisibility.add("Visible");
        return listVisibility;
    }

    public List<String> getAllAccuracy(){
        List<String> listAccuracy = new ArrayList<String>();
        listAccuracy.add("High");
        listAccuracy.add("Low");
        listAccuracy.add("NoPower");
        listAccuracy.add("BalancedPower");
        return listAccuracy;
    }
}
