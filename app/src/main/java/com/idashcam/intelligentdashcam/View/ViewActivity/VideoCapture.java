package com.idashcam.intelligentdashcam.View.ViewActivity;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.PowerManager;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.idashcam.intelligentdashcam.Analitycs.Video.VideoConfigurationStatic;
import com.idashcam.intelligentdashcam.R;

import java.io.IOException;
import java.util.Date;

/**
 * Created by alexandre on 21/02/2015.
 */
public class VideoCapture extends Activity implements View.OnClickListener, SurfaceHolder.Callback {
    private static final String LOG_TAG_VIDEO = "Exception Start Video!";

//    VideoConfiguration videoConfiguration;
    MediaRecorder recorder;
    SurfaceHolder holder;
    boolean recording = false;
//    SQLiteDatabaseHandler db;
    private ImageView img;

    private PowerManager.WakeLock wakeLock;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        recorder = new MediaRecorder();

        setContentView(R.layout.video_view/*main*/);

//        initConfByBDD();
        initRecorder();
        this.img = (ImageView) findViewById(R.id.imageButton);
        //        SurfaceView cameraView = (SurfaceView) findViewById(R.id.camera_preview/*CameraView*/);
        //FrameLayout necéssésary
        SurfaceView cameraView = (SurfaceView) findViewById(R.id.surfaceVideoView);
        holder = cameraView.getHolder();
        holder.addCallback(this);
        holder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
        cameraView.setClickable(true);
        cameraView.setOnClickListener(this);

        PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE,this.getClass().getName());
    }

    @Override
    protected void onStart(){
        super.onStart();
        img.setImageResource(R.drawable.pause);
        if (wakeLock != null){
            wakeLock.acquire();
        }
    }
    @Override
    protected void onResume(){
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        releaseMediaRecorder();
    }

    @Override
    protected void onDestroy(){
        super.onDestroy();
        holder.removeCallback(this);

        if (wakeLock != null){
            wakeLock.release();
        }
    }

    /**
     * ***********************************************************
     * <p/>
     * * * 			Recording
     * <p/>
     * ************************************************************
     */

    private void initRecorder() {
        int date = (int) new Date().getTime();
        String namedMedia = VideoConfigurationStatic.getAppName() + date;

        recorder.setAudioSource(MediaRecorder.AudioSource.MIC/*DEFAULT*/);
        recorder.setVideoSource(MediaRecorder.VideoSource.DEFAULT);
        CamcorderProfile cpHigh = CamcorderProfile.get(VideoConfigurationStatic.getQualityVideo());
        recorder.setProfile(cpHigh);
        recorder.setOutputFile(VideoConfigurationStatic.getLocationSdcardVideo() + namedMedia + VideoConfigurationStatic.getExtensionFormatVideo());
        recorder.setMaxDuration(VideoConfigurationStatic.getMaxDurationVideo()); // 50 seconds
        recorder.setMaxFileSize(VideoConfigurationStatic.getMaxLenghtVideoInMo()); // Approximately 5 megabytes
    }

    private void prepareRecorder() {
        recorder.setPreviewDisplay(holder.getSurface());
        try {
            recorder.prepare();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void releaseMediaRecorder(){
        if (recorder != null) {
            recorder.reset();   // clear recorder configuration
            recorder.release(); // release the recorder object
            recorder = null;
        }
        holder.removeCallback(this);
    }
    /**
     * ***********************************************************
     * <p/>
     * * * 			Gestion Click
     * <p/>
     * ************************************************************
     */

    public void onClick(View v) {
        if (recording) {
            recorder.stop();
            recorder.reset();

            recording = false;
            img.setImageResource(R.drawable.pause);
            // Let's initRecorder so we can record again
            initRecorder();
            prepareRecorder();

        } else {

            try {
                recorder.start();
                recording = true;
                img.setImageResource(R.drawable.play);
            } catch (Throwable t) {
                t.printStackTrace();
                Log.w(LOG_TAG_VIDEO, t);
            }
        }
    }

    /**
     * ***********************************************************
     * <p/>
     * * * 			Surface Gestion
     * <p/>
     * ************************************************************
     */

    public void surfaceCreated(SurfaceHolder holder) {
        prepareRecorder();
    }

    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    public void surfaceDestroyed(SurfaceHolder holder) {
        if (recording) {
            recorder.stop();
            recording = false;
        }
        recorder.reset();
        recorder.release();
        finish();
    }
}
