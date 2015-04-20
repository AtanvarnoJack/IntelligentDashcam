package com.idashcam.intelligentdashcam.View.ViewActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import com.idashcam.intelligentdashcam.R;


public class PreviewApp extends Activity {
    private static final int TIME_TO_WAIT = 1 * 1000; //1sec

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_cam);

        ImageView imageView = (ImageView) findViewById(R.id.image_intro_first_view);
        imageView.setImageResource(R.drawable.camera_lens_wallpaper_hd);
        waitNext();
    }

    @Override
    protected void onStart() {
        super.onStart();
        waitNext();
    }

    /**
     * protected void onStart() **
     */

    @Override
    protected void onResume() {
        super.onResume();
    }

    /**
     * protected void onResume() **
     */

    @Override
    protected void onPause() {
        super.onPause();
    }

    /**
     * protected void onPause() **
     */

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * protected void onDestroy() **
     */
    public void waitNext() {

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                next();
            }
        },TIME_TO_WAIT);
    }

    private void next(){
        //Toast toast = Toast.makeText(this, "First View!", 1000);
        Intent MyView = new Intent();
        MyView.setClass(this, CameraActivity.class);
        startActivity(MyView);
    }
}
