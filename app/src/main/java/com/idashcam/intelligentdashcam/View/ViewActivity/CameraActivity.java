package com.idashcam.intelligentdashcam.View.ViewActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.PreviewCallback;
import android.hardware.Camera.ShutterCallback;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.idashcam.intelligentdashcam.Analitycs.Map.MapConfiguration;
import com.idashcam.intelligentdashcam.R;
import com.idashcam.intelligentdashcam.Stockage.BddGestion.Map.BddGestionMap;
import com.idashcam.intelligentdashcam.Stockage.BddGestion.Map.BddGestionMapImpl;
import com.idashcam.intelligentdashcam.Stockage.BddGestion.Video.BddGestionVideo;
import com.idashcam.intelligentdashcam.Stockage.BddGestion.Video.BddGestionVideoImpl;
import com.idashcam.intelligentdashcam.View.CallBack.CameraPreview;
import com.idashcam.intelligentdashcam.View.Map.MyGoogleMap;
import com.idashcam.intelligentdashcam.View.Popup.PopupMenu;

import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
@SuppressWarnings("deprecation")
public class CameraActivity extends Activity implements OnMapReadyCallback,GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private static final String TAG = "CameraDemo";
    private static final String CAMERA_SERVICE = "CAMERA_SERVICE";
    private static final String LOG_DASHCAM = "DaschCam: ";
    private BddGestionVideo bddGestionVideo;
    private BddGestionMap bddGestionMap;
    private PopupMenu popupGestion;
    private MyGoogleMap myGoogleMap;
    private boolean passView = false;

    private PowerManager.WakeLock wakeLock;

    /***
     * On create
     * @param savedInstanceState
     */
    @SuppressLint("NewApi")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.preview);

        initCamera();

        initActionButton();

        PowerManager pm = (PowerManager) this.getSystemService(Context.POWER_SERVICE);
        wakeLock = pm.newWakeLock(PowerManager.SCREEN_BRIGHT_WAKE_LOCK | PowerManager.ON_AFTER_RELEASE,this.getClass().getName());

        bddGestionMap = new BddGestionMapImpl(this);
        MapConfiguration mapConfTMP = bddGestionMap.initConfByBDD();

        bddGestionVideo = new BddGestionVideoImpl(this);
        bddGestionVideo.initConfByBDD();

        myGoogleMap = new MyGoogleMap();
        myGoogleMap.setMapConfiguration(mapConfTMP);

        //Init Location
        getGoogleApiConnection();
        myGoogleMap.initLocationRequest();

        //Init map
        MapFragmentInit();

        View map = findViewById(R.id.viewMap);
        myGoogleMap.setMapTransparency(map);
        myGoogleMap.setMapVisibility(map);

        popupGestion = new PopupMenu(this,p, bddGestionVideo, bddGestionMap, myGoogleMap);
        popupGestion.initPopupMenu();
    }

    /**
     * ***********************************************************
     * <p/>
     * * * 			Map
     * <p/>
     * ************************************************************
     */
    public static final String TAG_MAP = CameraActivity.class.getSimpleName();

    private void getGoogleApiConnection(){
        myGoogleMap.setmGoogleApiClient( new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build());
    }

    @Override
    public void onLocationChanged(Location location) {
        myGoogleMap.handleNewLocation(location);
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.i(TAG_MAP,"Location service connected.");
        Location location = LocationServices.FusedLocationApi.getLastLocation(myGoogleMap.getmGoogleApiClient());

        if (location == null){
            LocationServices.FusedLocationApi.requestLocationUpdates(myGoogleMap.getmGoogleApiClient(),myGoogleMap.getmLocationRequest(),this);
        }else {
            myGoogleMap.handleNewLocation(location);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        Log.i(TAG_MAP,"Location service suspend. Please reconnect.");
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (connectionResult.hasResolution()){
            try{
                connectionResult.startResolutionForResult(this,myGoogleMap.CONNECTION_FAILURE_RESOLUTION_REQUEST);
            } catch (IntentSender.SendIntentException e) {
                e.printStackTrace();
            }
        }else{
            Log.i(TAG_MAP,"Location services connection failed with code " + connectionResult.getErrorCode());
        }
    }

    @Override
    public void onMapReady(GoogleMap map) {
        myGoogleMap.setMap(map);
    }

    private void MapFragmentInit() {
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.viewMap);

        mapFragment.getMapAsync(this);
    }
    /**
     * ***********************************************************
     * <p/>
     * * * 			Popup menu
     * <p/>
     * ************************************************************
     */
    Point p;
    @Override
    public void onWindowFocusChanged(boolean hasFocus){
        int[] location = new int[2];
        Button button = (Button) findViewById(R.id.btn_reglage);
        button.getLocationOnScreen(location);
        p = new Point();
        p.x = location[0];
        p.y = location[1];
        popupGestion.setP(p);
    }

    /**
     * ***********************************************************
     * <p/>
     * * * 			Button action
     * <p/>
     * ************************************************************
     */
    private void initActionButton() {
        //btn_timer
        Button photoButton = (Button) findViewById(R.id.btn_photo);
        photoButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SavePicture();
                    }
                }
        );

        Button videoButton = (Button) findViewById(R.id.btn_video);
        videoButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        passView = true;
                        goToVideoCapture();
                    }
                }
        );
    }

    private void goToVideoCapture() {
        Intent myVideoCapture = new Intent();
        myVideoCapture.setClass(this, VideoCapture.class);
        startActivity(myVideoCapture);
    }

    /**
     * ***********************************************************
     * <p/>
     * * * 			App Action
     * <p/>
     * ************************************************************
     */

    /**
     * protected void onDestroy() **
     */

    @Override
    protected void onStart() {
        super.onStart();
        if (wakeLock != null){
            wakeLock.acquire();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        MapFragmentInit();
        myGoogleMap.getmGoogleApiClient().connect();
//        if (mGoogleApiClient.isConnected()){
//            startLocationUpdates();
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(myGoogleMap.getmGoogleApiClient().isConnected()){
            LocationServices.FusedLocationApi.removeLocationUpdates(myGoogleMap.getmGoogleApiClient(),this);
            myGoogleMap.getmGoogleApiClient().disconnect();
        }

        if (!passView) {
            mPreview.getHolder().removeCallback(mPreview);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPreview.getHolder().removeCallback(mPreview);
        mCamera.stopPreview();
        mCamera.release();

        if (wakeLock != null){
            wakeLock.release();
        }
    }

    /**
     * ***********************************************************
     * <p/>
     * * * 			picture callback
     * <p/>
     * ************************************************************
     */
    /*private Boolean RunTimerRead;
    private Boolean PauseTimerRead;
    private Timer myTimer;*/
    ShutterCallback shutterCallback = new ShutterCallback() {
        public void onShutter() {
            Log.d(TAG, "onShutter'd");
        }
    };
    /**
     * Handles data for raw picture
     */
    PictureCallback rawCallback = new PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
            Log.d(TAG, "onPictureTaken - raw");
        }
    };
    /**
     * Handles data for jpeg picture
     */
    PictureCallback jpegCallback = new PictureCallback() {
        public void onPictureTaken(byte[] data, Camera camera) {
        }
    };
    /**
     * ***********************************************************
     * <p/>
     * * * 			Capture picture and save
     * <p/>
     * ************************************************************
     */
    FileOutputStream stream;
    PictureCallback pictureCallback = new PictureCallback() {

        public void onPictureTaken(byte[] data, Camera camera) {
            if (data != null) {
                // Enregistrement de votre image
                try {
                    if (stream != null) {
                        stream.write(data);
                        stream.flush();
                        stream.close();
                    }
                } catch (Exception e) {
                    Log.i(LOG_DASHCAM, " Picture Callback Error");
                }

                // Nous redémarrons la prévisualisation
                camera.startPreview();
            }
        }
    };
    private Camera mCamera;
    private CameraPreview mPreview;

    /**
     * ***********************************************************
     * <p/>
     * * * 			Instanciate Camera
     * <p/>
     * ************************************************************
     */
    private void initCamera() {
        // Create an instance of Camera
        mCamera = null;
        mCamera = getCameraInstance();
        mCamera.setPreviewCallback(new PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {
                Log.d("Camera Preview", data.length + "");
            }
        });

        // Create our Preview view and set it as the content of our activity.
        //mPreview = new CameraPreview(this, mCamera);
        mPreview = new CameraPreview(this, mCamera);
        ((FrameLayout) findViewById(R.id.camera_preview)).addView(mPreview);


        //FrameLayout preview = (FrameLayout) findViewById(R.id.camera_preview);

        //set camera to continually auto-focus
        Parameters params = mCamera.getParameters();
        //*EDIT*//params.setFocusMode("continuous-picture");
        //It is better to use defined constraints as opposed to String, thanks to AbdelHady
        try { //lowcost phone have no focus
            params.setFocusMode(Parameters.FOCUS_MODE_CONTINUOUS_PICTURE);
            mCamera.setParameters(params);
        }catch( Exception e){
            Log.i("DashCam_Video", "Focus mode not found on device!!!");}
    }

    public static Camera getCameraInstance() {
        Camera c = null;
        try {
            c = Camera.open(); // attempt to get a Camera instance
        } catch (Exception e) {
            // Camera is not available (in use or does not exist)
            Log.d(CAMERA_SERVICE, "Error setting camera preview: " + e.getMessage());
        }
        return c; // returns null if camera is unavailable
    }

    /**
     * ***********************************************************
     * <p/>
     * * * 			Screenshoot and save
     * <p/>
     * ************************************************************
     */

    private void SavePicture() {
        try {
            SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy-MM-dd-HH.mm.ss");
            String fileName = "PhotoIDashCam_" + timeStampFormat.format(new Date()) + ".jpg";

            // Metadata pour la photo
            ContentValues values = new ContentValues();
            values.put(MediaStore.Images.Media.TITLE, fileName);
            values.put(MediaStore.Images.Media.DISPLAY_NAME, fileName);
            values.put(MediaStore.Images.Media.DESCRIPTION, "Image prise par Intelligent DashCam");
            values.put(MediaStore.Images.Media.DATE_TAKEN, new Date().getTime());
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");

            // Support de stockage
            Uri taken = this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

            // Ouverture du flux pour la sauvegarde
            stream = (FileOutputStream) this.getContentResolver().openOutputStream(taken);

            //mCamera.takePicture(null, pictureCallback, pictureCallback);
            mCamera.takePicture(shutterCallback, pictureCallback, pictureCallback);
        } catch (Exception e) {
            Log.i(LOG_DASHCAM, "Save Picture Error");
        }
    }
}