package com.idashcam.intelligentdashcam.View.Popup.MapSetting;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.idashcam.intelligentdashcam.R;
import com.idashcam.intelligentdashcam.View.Map.MyGoogleMap;
import com.idashcam.intelligentdashcam.View.Popup.Adapter.Map.AdapterFragSettingList;

import java.util.List;

/**
 * Created by alexandre on 17/04/2015.
 */
public class PopupListMap {
    public final static String LIST_MAP_TYPE_MAP = "MapType";
    public final static String LIST_MAP_ACCURACY = "Accuracy";

    private Activity activityInit;
    private List<String> listValue;
    private MyGoogleMap myGoogleMap;
    private String typeConfig;

    public PopupListMap(Activity activityInit, List<String> listValue, MyGoogleMap myGoogleMap1, String typeConfig) {
        this.activityInit = activityInit;
        this.listValue = listValue;
        this.myGoogleMap = myGoogleMap1;
        this.typeConfig = typeConfig;
    }

    public void initPopupListMap(final View menuView, final Activity context, final Point p) {
        if (listValue.size() > 0){
            LinearLayout viewGroup =(LinearLayout) context.findViewById(R.id.popup_settings_list_string);
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View layout = layoutInflater.inflate(R.layout.popup_setting_list_string, viewGroup);

            int popopwidth = p.x/2;
            int popupheight = p.y;

            //Create PopupWindows
            final PopupWindow popupChangeListMap = new PopupWindow(context);
            popupChangeListMap.setContentView(layout);
            popupChangeListMap.setWidth(popopwidth);
            popupChangeListMap.setHeight(popupheight);
            popupChangeListMap.setFocusable(true);

            popupChangeListMap.setBackgroundDrawable(new BitmapDrawable());

            final ListView listViewQuality = (ListView) layout.findViewById(R.id.lvw_quality);
            AdapterFragSettingList adapterSelector = null;

            switch (typeConfig){
                case LIST_MAP_TYPE_MAP:
                    adapterSelector  = new AdapterFragSettingList(activityInit, listValue, myGoogleMap.getMapConfiguration().getMapTypeString());
                    break;
                case LIST_MAP_ACCURACY:
                    adapterSelector  = new AdapterFragSettingList(activityInit, listValue, myGoogleMap.getMapConfiguration().getAccuracyString());
                    break;
            }


            listViewQuality.setAdapter(adapterSelector);
            listViewQuality.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

            final AdapterFragSettingList finalAdapterSelector = adapterSelector;
            listViewQuality.setOnItemClickListener( new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    switch (typeConfig){
                        case LIST_MAP_TYPE_MAP:
                            myGoogleMap.getMapConfiguration().setMapType(finalAdapterSelector.getItem(position).toString());
                            TextView textViewMapType = (TextView) menuView.findViewById(R.id.tvw_setting_map_type);
                            textViewMapType.setText(myGoogleMap.getMapConfiguration().getMapTypeString());
                            myGoogleMap.getMyMap().setMapType(myGoogleMap.getMapConfiguration().getMapTypeInt());
                            break;
                        case LIST_MAP_ACCURACY:
                            myGoogleMap.getMapConfiguration().setAccuracy(finalAdapterSelector.getItem(position).toString());
                            TextView textViewAccuracy = (TextView) menuView.findViewById(R.id.tvw_setting_map_accuracy);
                            textViewAccuracy.setText(myGoogleMap.getMapConfiguration().getAccuracyString());
                            myGoogleMap.initLocationRequest();
                            break;
                    }
                    popupChangeListMap.dismiss();
                }
            });

            popupChangeListMap.showAtLocation(layout, Gravity.NO_GRAVITY, p.x, p.y);
        }else {
            Toast.makeText(context,"No Value for list!",Toast.LENGTH_SHORT);
        }

    }

    public List<String> getListValue() {
        return listValue;
    }

    public void setListValue(List<String> listValue) {
        this.listValue = listValue;
    }

    public String getTypeConfig() {
        return typeConfig;
    }

    public void setTypeConfig(String typeConfig) {
        this.typeConfig = typeConfig;
    }
}
