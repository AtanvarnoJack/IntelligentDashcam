package com.idashcam.intelligentdashcam.View.Popup.VideoSetting;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.idashcam.intelligentdashcam.Analitycs.TimeIDaschCam;
import com.idashcam.intelligentdashcam.Analitycs.Video.VideoConfiguration;
import com.idashcam.intelligentdashcam.Analitycs.Video.VideoConfigurationStatic;
import com.idashcam.intelligentdashcam.R;
import com.idashcam.intelligentdashcam.Stockage.BddGestion.Video.BddGestionVideo;
import com.idashcam.intelligentdashcam.Stockage.Enum.CamQualityEnum;

import java.sql.Time;

/**
 * Created by alexandre on 15/04/2015.
 */
public class PopupSettingVideo {
    private static final int LENGHT_VIDEO_MULTIPLE = 1000000;
    private Activity activityInit;
    private BddGestionVideo bddGestionVideo;
    TimeIDaschCam time;

    public PopupSettingVideo(Activity activityInit, BddGestionVideo bddGestionVideo) {
        this.activityInit = activityInit;
        this.bddGestionVideo = bddGestionVideo;
    }

    public void initVideoSettingPopup(Activity context, final Point p) {
        ScrollView viewGroup =(ScrollView) context.findViewById(R.id.popup_settings_video);
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View layout = layoutInflater.inflate(R.layout.popup_setting_video, viewGroup);

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

        InitVideoSettingPopupInterface(layout,context,p);
    }

    public void InitVideoSettingPopupInterface(final View popupMenuVideo, final Activity context, final Point p) {
        /***
         * Gestion affichage:
         */
        //Duration Video
        EditText editTextMinute = (EditText) popupMenuVideo.findViewById(R.id.edt_VideoSettingMinute);
        EditText editTextSecond = (EditText) popupMenuVideo.findViewById(R.id.edt_VideoSettingSecond);

        time = new TimeIDaschCam(VideoConfigurationStatic.getMaxDurationVideo());
        int second = time.getSecondForMS(VideoConfigurationStatic.getMaxDurationVideo());
        int minute = time.getMinuteForMs(VideoConfigurationStatic.getMaxDurationVideo());

        String MinuteString = String.valueOf(minute);
        String secondString = String.valueOf(second);

        editTextMinute.setText(MinuteString, TextView.BufferType.EDITABLE);
        editTextSecond.setText(secondString, TextView.BufferType.EDITABLE);

        //Lenght Video

        final TextView textViewNewValue = (TextView) popupMenuVideo.findViewById(R.id.txt_Lenght_Video_new_value);
        TextView textViewOldValue = (TextView) popupMenuVideo.findViewById(R.id.txt_Lenght_Video_old_value);
        final EditText editTextLenghtVideo = (EditText) popupMenuVideo.findViewById(R.id.edt_LenghtVideo);

        String MaxLenghtVideo = String.valueOf(VideoConfigurationStatic.getMaxLenghtVideoInMo() / LENGHT_VIDEO_MULTIPLE);
        textViewNewValue.setText(MaxLenghtVideo);
        textViewOldValue.setText(MaxLenghtVideo);
        editTextLenghtVideo.setText(MaxLenghtVideo);
        editTextLenghtVideo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                textViewNewValue.setText(editTextLenghtVideo.getText());
            }
        });

        //Quality Video
        final PopupChangeQuality popupChangeQuality = new PopupChangeQuality(activityInit);

        TextView textViewQuality = (TextView) popupMenuVideo.findViewById(R.id.txt_quality_value);
        Button btnChangeQuality = (Button) popupMenuVideo.findViewById(R.id.btn_quality_change);
        btnChangeQuality.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupChangeQuality.initPopupListQualityMenu(popupMenuVideo, context, p);
            }
        });

        int qualityFound = VideoConfigurationStatic.getQualityVideo();
        CamQualityEnum camQualityEnum = new CamQualityEnum();
        popupChangeQuality.setQualitySelectedList(camQualityEnum.getNameQualityByInt(qualityFound));
        textViewQuality.setText(popupChangeQuality.getQualitySelectedList());

        //Save
        Button btnSaveChange = (Button) popupMenuVideo.findViewById(R.id.btn_setup_video_save);
        btnSaveChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                   saveChangeVideoSetingToBDD(popupMenuVideo);
                }catch (IllegalArgumentException IAE){
                    Toast.makeText(activityInit,"Erreur: " + IAE,Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    public void saveChangeVideoSetingToBDD(View popupMenuVideo) throws IllegalArgumentException{
        EditText editTextMinute = (EditText) popupMenuVideo.findViewById(R.id.edt_VideoSettingMinute);
        EditText editTextSecond = (EditText) popupMenuVideo.findViewById(R.id.edt_VideoSettingSecond);
        String newMinuteString = editTextMinute.getText().toString().trim();
        String newSecondString = editTextSecond.getText().toString().trim();

        EditText editTextLenghtVideo = (EditText) popupMenuVideo.findViewById(R.id.edt_LenghtVideo);
        String newLenghtString = editTextLenghtVideo.getText().toString().trim();

        TextView txtNewQuality = (TextView) popupMenuVideo.findViewById(R.id.txt_quality_value);
        String newQualityString = txtNewQuality.getText().toString();

        if (newMinuteString.equals("") && newSecondString.equals("")){
            throw new IllegalArgumentException("Au moin un des deux champs de temps doivent être indiqués!");
        }
        else if (newMinuteString.equals("")){
            newMinuteString = "0";
        }
        else if (newSecondString.equals("")){
            newSecondString = "0";
        }

        if (newLenghtString.equals("")){
            throw new IllegalArgumentException("La taille de la video maximum n'a pas été renseignée!");
        }else if (newQualityString.equals("")){
            throw new IllegalArgumentException("La qualité de la video n'a pas été renseignée!");
        }else{
            int newMinute = Integer.parseInt(newMinuteString);
            int newSecond = Integer.parseInt(newSecondString);
            int timeVideo;
            int lenghtVideo;
            int qualityVideo;

            timeVideo = time.getMsForSecondAndMinute(newSecond,newMinute);

            lenghtVideo = Integer.parseInt(newLenghtString) * LENGHT_VIDEO_MULTIPLE;
            CamQualityEnum camQualityEnum = new CamQualityEnum();
            qualityVideo =  camQualityEnum.getQualityIntByName(newQualityString);

            VideoConfiguration actualVideoConf = bddGestionVideo.getConfByBDD();
            actualVideoConf.setMaxDurationVideo(timeVideo);
            actualVideoConf.setMaxLenghtVideoInMo(lenghtVideo);
            actualVideoConf.setQualityVideo(qualityVideo);
            bddGestionVideo.updateConfByBDD(actualVideoConf);

            VideoConfigurationStatic.setMaxDurationVideo(timeVideo);
            VideoConfigurationStatic.setMaxLenghtVideoInMo(lenghtVideo);
            VideoConfigurationStatic.setQualityVideo(qualityVideo);

            Toast.makeText(activityInit, "Save!", Toast.LENGTH_SHORT).show();
        }
    }
}
