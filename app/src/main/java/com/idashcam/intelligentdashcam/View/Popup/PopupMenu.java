package com.idashcam.intelligentdashcam.View.Popup;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import com.idashcam.intelligentdashcam.R;
import com.idashcam.intelligentdashcam.Stockage.BddGestion.Map.BddGestionMap;
import com.idashcam.intelligentdashcam.Stockage.BddGestion.Video.BddGestionVideo;
import com.idashcam.intelligentdashcam.View.Map.MyGoogleMap;
import com.idashcam.intelligentdashcam.View.Popup.MapSetting.PopupMapSetting;
import com.idashcam.intelligentdashcam.View.Popup.VideoSetting.PopupSettingVideo;

/**
 * Created by alexandre on 13/04/2015.
 */
public class PopupMenu {
    private Activity activityInit;
    private Point p;
    private BddGestionVideo bddGestionVideo;
    private BddGestionMap bddGestionMap;
    private MyGoogleMap myGoogleMap;

    public PopupMenu(Activity activityInit, Point p, BddGestionVideo bddGestionVideo, BddGestionMap bddGestionMap, MyGoogleMap myGoogleMap) {
        this.activityInit = activityInit;
        this.p = p;
        this.bddGestionVideo = bddGestionVideo;
        this.bddGestionMap = bddGestionMap;
        this.myGoogleMap = myGoogleMap;
    }

    public void initPopupMenu() {
        Button btn_popup_menu = (Button) activityInit.findViewById(R.id.btn_reglage);
        btn_popup_menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(p != null){
                    showPopup(activityInit,p);
                }
            }
        });
    }

    private void showPopup(final Activity context, final Point p) {
        LinearLayout viewGroup =(LinearLayout) context.findViewById(R.id.popup_settings);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_setting, viewGroup);

        int popopwidth = p.x/2;
        int popupheight = (p.y/5)*4;

        //Create PopupWindows
        final PopupWindow popupMenu = new PopupWindow(context);
        popupMenu.setContentView(layout);
        popupMenu.setWidth(popopwidth);
        popupMenu.setHeight(popupheight);
        popupMenu.setFocusable(true);

        popupMenu.setBackgroundDrawable(new BitmapDrawable());

        popupMenu.showAtLocation(layout, Gravity.NO_GRAVITY, p.x, p.y);

        final Button MapBtnVisibility = (Button) layout.findViewById(R.id.btn_reglage_photo);
        final String OldText = (String) MapBtnVisibility.getText();

        final View map = context.findViewById(R.id.viewMap);

        MapBtnVisibility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mapVisibility = myGoogleMap.getMapConfiguration().getMapVisibilityInt();
                if (mapVisibility == View.VISIBLE) {
                    myGoogleMap.getMapConfiguration().setMapVisibility(View.INVISIBLE);
                    myGoogleMap.setMapVisibility(map);
                    MapBtnVisibility.setText(OldText + " " +  myGoogleMap.getMapConfiguration().getMapVisibilityString());
                } else {
                    myGoogleMap.getMapConfiguration().setMapVisibility(View.VISIBLE);
                    myGoogleMap.setMapVisibility(map);
                    MapBtnVisibility.setText(OldText + " " + myGoogleMap.getMapConfiguration().getMapVisibilityString());
                }
                
            }
        });

        Button mapBtn = (Button) layout.findViewById(R.id.btn_popup_setting_map);
        mapBtn.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMapSetting popupMapSetting = new PopupMapSetting(activityInit,myGoogleMap, bddGestionMap);
                popupMapSetting.initMapSettingPopup(context, p);
            }
        });

        Button videoBtn = (Button) layout.findViewById(R.id.btn_reglage_video);
        videoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupSettingVideo popupSettingVideo = new PopupSettingVideo(activityInit, bddGestionVideo);
                popupSettingVideo.initVideoSettingPopup(context, p);
            }
        });
    }

    public Activity getActivityInit() {
        return activityInit;
    }

    public void setActivityInit(Activity activityInit) {
        this.activityInit = activityInit;
    }

    public Point getP() {
        return p;
    }

    public void setP(Point p) {
        this.p = p;
    }

    public BddGestionVideo getBddGestionVideo() {
        return bddGestionVideo;
    }

    public void setBddGestionVideo(BddGestionVideo bddGestionVideo) {
        this.bddGestionVideo = bddGestionVideo;
    }

    public BddGestionMap getBddGestionMap() {
        return bddGestionMap;
    }

    public void setBddGestionMap(BddGestionMap bddGestionMap) {
        this.bddGestionMap = bddGestionMap;
    }
}
