package com.idashcam.intelligentdashcam.View.Popup.VideoSetting;

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

import com.idashcam.intelligentdashcam.R;
import com.idashcam.intelligentdashcam.Stockage.Enum.CamQualityEnum;
import com.idashcam.intelligentdashcam.View.Popup.Adapter.Video.AdapterFragSettingVideoQualitySelector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Created by alexandre on 15/04/2015.
 */
public class PopupChangeQuality {
    private Activity activityInit;
    private static String qualitySelectedList = null;

    public PopupChangeQuality(Activity activityInit) {
        this.activityInit = activityInit;
    }

    public static String getQualitySelectedList() {
        return qualitySelectedList;
    }

    public static void setQualitySelectedList(String qualitySelectedList) {
        PopupChangeQuality.qualitySelectedList = qualitySelectedList;
    }

    public void initPopupListQualityMenu(final View menuView, final Activity context, final Point p) {
        LinearLayout viewGroup =(LinearLayout) context.findViewById(R.id.popup_settings_list_string);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_setting_list_string, viewGroup);

        int popopwidth = p.x/2;
        int popupheight = p.y;

        //Create PopupWindows
        final PopupWindow popupChangedQuality = new PopupWindow(context);
        popupChangedQuality.setContentView(layout);
        popupChangedQuality.setWidth(popopwidth);
        popupChangedQuality.setHeight(popupheight);
        popupChangedQuality.setFocusable(true);

        popupChangedQuality.setBackgroundDrawable(new BitmapDrawable());
        CamQualityEnum camQualityEnum = new CamQualityEnum();
        final Collection<String> found = camQualityEnum.getAllQualityName();
        List<String> listName = new ArrayList<String>();
        for (String s : found) {
            listName.add(s);
        }

        final ListView listViewQuality = (ListView) layout.findViewById(R.id.lvw_quality);

        final AdapterFragSettingVideoQualitySelector adapterSelector = new AdapterFragSettingVideoQualitySelector(activityInit,listName, qualitySelectedList);
        listViewQuality.setAdapter(adapterSelector);
        listViewQuality.setChoiceMode(ListView.CHOICE_MODE_SINGLE);

        listViewQuality.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                qualitySelectedList = adapterSelector.getItem(position).toString();

                TextView textViewQuality = (TextView) menuView.findViewById(R.id.txt_quality_value);
                textViewQuality.setText(qualitySelectedList);

                popupChangedQuality.dismiss();
            }
        });

        popupChangedQuality.showAtLocation(layout, Gravity.NO_GRAVITY, p.x, p.y);
    }
}
