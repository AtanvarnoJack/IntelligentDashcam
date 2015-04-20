package com.idashcam.intelligentdashcam.View.Popup.MapSetting;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.idashcam.intelligentdashcam.Analitycs.Map.MapConfiguration;
import com.idashcam.intelligentdashcam.R;
import com.idashcam.intelligentdashcam.Stockage.BddGestion.Map.BddGestionMap;
import com.idashcam.intelligentdashcam.View.Map.MyGoogleMap;

import java.util.List;

/**
 * Created by alexandre on 15/04/2015.
 */
public class PopupMapSetting {
    private Activity activityInit;
    private MyGoogleMap myGoogleMap;
    private PopupListMap popupListMap;
    BddGestionMap bddGestionMap;

    public PopupMapSetting(Activity activityInit, MyGoogleMap myGoogleMap, BddGestionMap bddGestionMap) {
        this.activityInit = activityInit;
        this.myGoogleMap = myGoogleMap;
        this.bddGestionMap = bddGestionMap;
    }

    public void initMapSettingPopup(Activity context, final Point p) {
        ScrollView viewGroup =(ScrollView) context.findViewById(R.id.popup_settings_map);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_setting_map, viewGroup);

        int popopwidth = p.x/2;
        int popupheight = p.y;

        //Create PopupWindows
        final PopupWindow popupMenuVideo = new PopupWindow(context);
        popupMenuVideo.setContentView(layout);
        popupMenuVideo.setWidth(popopwidth);
        popupMenuVideo.setHeight(popupheight);
        popupMenuVideo.setFocusable(true);

        popupMenuVideo.setBackgroundDrawable(new BitmapDrawable());

        popupMenuVideo.showAtLocation(layout, Gravity.NO_GRAVITY, p.x, p.y);

        InitMapSettingPopupInterface(layout, context, p);
    }

    private void InitMapSettingPopupInterface(final View layout, final Activity context, final Point p) {

//        mapType;
        final TextView tvwMapType = (TextView) layout.findViewById(R.id.tvw_setting_map_type);
        Button btnMapType = (Button) layout.findViewById(R.id.btn_setting_map_type);
        btnMapType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GestionMapType(layout, context, p);
            }
        });

        tvwMapType.setText(myGoogleMap.getMapConfiguration().getMapTypeString());

//        mapVisibility;
        final TextView tvwMapVisibility = (TextView) layout.findViewById(R.id.tvw_setting_map_visibility);
        Button btnMapVisibility = (Button) layout.findViewById(R.id.btn_setting_map_visibility);

        final View mapView = context.findViewById(R.id.viewMap);
        btnMapVisibility.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GestionMapVisibility(mapView, tvwMapVisibility);
            }
        });

        tvwMapVisibility.setText(myGoogleMap.getMapConfiguration().getMapVisibilityString());

//        interval;
        final EditText edtInterval = (EditText) layout.findViewById(R.id.edt_setting_map_interval);
        final TextView tvwIntervale = (TextView) layout.findViewById(R.id.tvw_setting_map_interval);
        Button btnSetInteral = (Button) layout.findViewById(R.id.btn_setting_map_interval_set);
        btnSetInteral.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String intervalString = String.valueOf(edtInterval.getText());
                try {
                    myGoogleMap.getMapConfiguration().setInterval(Integer.parseInt(intervalString));
                    myGoogleMap.initLocationRequest();
                }catch (Exception e){
                    Toast.makeText(context,"Veuillez indiquer un chiffre entier!",Toast.LENGTH_LONG).show();
                }
            }
        });

        int intervalSec = myGoogleMap.getMapConfiguration().getInterval();
        String intervalString = String.valueOf(intervalSec);
        edtInterval.setText(intervalString);
        tvwIntervale.setText(intervalString);

//        fastInterval;
        final EditText edtFastInterval = (EditText) layout.findViewById(R.id.edt_setting_map_fastinterval);
        final TextView tvwFastIntervale = (TextView) layout.findViewById(R.id.tvw_setting_map_fastinterval);
        Button btnFastIntervalSet = (Button) layout.findViewById(R.id.btn_setting_map_fastinterval_set);
        btnFastIntervalSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fastIntervalString = String.valueOf(edtFastInterval.getText());
                try {
                    myGoogleMap.getMapConfiguration().setFastInterval(Integer.parseInt(fastIntervalString));
                    myGoogleMap.initLocationRequest();
                }catch (Exception e){
                    Toast.makeText(context,"Veuillez indiquer un chiffre entier!",Toast.LENGTH_LONG).show();
                }
            }
        });

        int fastInterval = myGoogleMap.getMapConfiguration().getFastInterval();
        String fastIntervalString = String.valueOf(fastInterval);
        edtFastInterval.setText(fastIntervalString);
        tvwFastIntervale.setText(fastIntervalString);

//        accuracy;
        final TextView tvwAccuracy = (TextView) layout.findViewById(R.id.tvw_setting_map_accuracy);
        Button btnAccuracy = (Button) layout.findViewById(R.id.btn_setting_map_accuracy);
        btnAccuracy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GestionAccuracy(layout, context, p);
            }
        });

        tvwAccuracy.setText(myGoogleMap.getMapConfiguration().getAccuracyString());

//        Zoom;
        final EditText edtZoom = (EditText) layout.findViewById(R.id.edt_setting_map_zoom);
        final TextView tvwZoom = (TextView) layout.findViewById(R.id.tvw_setting_map_zoom);
        Button btnZoom = (Button) layout.findViewById(R.id.btn_setting_map_zoom_set);
        btnZoom.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String zoomString = String.valueOf(edtZoom.getText());
                try {
                    myGoogleMap.getMapConfiguration().setZoom(Integer.parseInt(zoomString));
                }catch (Exception e){
                    Toast.makeText(context,"Veuillez indiquer un chiffre entier!",Toast.LENGTH_LONG).show();
                }
            }
        });
        String zoomString = String.valueOf(myGoogleMap.getMapConfiguration().getZoom());
        edtZoom.setText(zoomString);
        tvwZoom.setText(zoomString);

//        transparency;
        final RadioButton rdbTransparency = (RadioButton) layout.findViewById(R.id.rdb_setting_map_transparency);
        rdbTransparency.setChecked(myGoogleMap.getMapConfiguration().isTransparency());
        rdbTransparency.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GestionTransparency(mapView, rdbTransparency);
            }
        });

//        save
        Button btnSave = (Button) layout.findViewById(R.id.btn_setting_map_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    GestionSave(edtInterval, edtFastInterval, edtZoom);
                }catch (IllegalArgumentException IAE){
                    Toast.makeText(activityInit, "Erreur: " + IAE, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void GestionMapType(View layout, Activity context, Point p) {
        List<String> listMapType = myGoogleMap.getMapConfiguration().getAllMapType();
        popupListMap = new PopupListMap(activityInit,listMapType,myGoogleMap,popupListMap.LIST_MAP_TYPE_MAP);
        popupListMap.initPopupListMap(layout, context, p);
    }

    private void GestionMapVisibility(View mapView, TextView tvwMapVisibility) {
        int mapVisibility = myGoogleMap.getMapConfiguration().getMapVisibilityInt();
        if (mapVisibility == View.VISIBLE) {
            myGoogleMap.getMapConfiguration().setMapVisibility(View.INVISIBLE);
            myGoogleMap.setMapVisibility(mapView);
            tvwMapVisibility.setText(myGoogleMap.getMapConfiguration().getMapVisibilityString());
        } else {
            myGoogleMap.getMapConfiguration().setMapVisibility(View.VISIBLE);
            myGoogleMap.setMapVisibility(mapView);
            tvwMapVisibility.setText(myGoogleMap.getMapConfiguration().getMapVisibilityString());
        }
    }

    private void GestionAccuracy(View layout, Activity context, Point p) {
        List<String> listAccuracy = myGoogleMap.getMapConfiguration().getAllAccuracy();
        popupListMap = new PopupListMap(activityInit,listAccuracy,myGoogleMap,popupListMap.LIST_MAP_ACCURACY);
        popupListMap.initPopupListMap(layout, context, p);
    }

    private void GestionTransparency(View mapView, RadioButton rdbTransparency) {
        boolean transparency = myGoogleMap.getMapConfiguration().isTransparency();
        if (transparency){
            myGoogleMap.getMapConfiguration().setTransparency(false);
            myGoogleMap.setMapTransparency(mapView);
        }else {
            myGoogleMap.getMapConfiguration().setTransparency(true);
            myGoogleMap.setMapTransparency(mapView);
        }
        rdbTransparency.setChecked(myGoogleMap.getMapConfiguration().isTransparency());
    }

    private void GestionSave(EditText edtInterval, EditText edtFastInterval, EditText edtZoom) throws IllegalArgumentException{
        String stringInterval = String.valueOf(edtInterval.getText());
        String stringFastInterval = String.valueOf(edtFastInterval.getText());
        String stringZoom = String.valueOf(edtZoom.getText());

        if (stringInterval.equals("")){
            throw new IllegalArgumentException("Veuillez indiquer le champ Interval!");
        }else if(stringFastInterval.equals("")){
            throw new IllegalArgumentException("Veuillez indiquer le champ Fast Interval!");
        }else if (stringZoom.equals("")){
            throw new IllegalArgumentException("Veuillez indiquer le champ Zoom!");
        }else {
            int newInterval  = Integer.parseInt(stringInterval);
            int newFastInterval  = Integer.parseInt(stringFastInterval);
            int newZoom  = Integer.parseInt(stringZoom);

            myGoogleMap.getMapConfiguration().setInterval(newInterval);
            myGoogleMap.getMapConfiguration().setFastInterval(newFastInterval);
            myGoogleMap.getMapConfiguration().setZoom(newZoom);

            MapConfiguration newMapConfiguration = new MapConfiguration(myGoogleMap.getMapConfiguration());
            MapConfiguration mapConfFound = bddGestionMap.getConfByBDD();
            newMapConfiguration.setId(mapConfFound.getId());
            bddGestionMap.updateConfByBDD(newMapConfiguration);

            Toast.makeText(activityInit, "Save!", Toast.LENGTH_SHORT).show();
        }
    }
}
