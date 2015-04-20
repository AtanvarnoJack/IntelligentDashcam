package com.idashcam.intelligentdashcam.Analitycs.Video;

/**
 * Created by alexandre on 31/03/2015.
 */
public class VideoConfigurationStatic {
    private final static String LOCATION_SDCARD_VIDEO = "/sdcard/";
    private final static String EXTENSION_FORMAT_VIDEO = ".mp4";
    private final static String APP_NAME = "daschCamAppSession:";
    private static int id;
    private static int maxDurationVideo; // 50 seconds value in ms
    private static int maxLenghtVideoInMo; // Approximately 50 megabytes
    private static int qualityVideo; //Ce repertorier au fichier videoQualityDescription.txt dans le package analitycs

    public VideoConfigurationStatic(VideoConfiguration videoConfiguration) {
        this.id = videoConfiguration.getId();
        this.maxDurationVideo = videoConfiguration.getMaxDurationVideo();
        this.maxLenghtVideoInMo = videoConfiguration.getMaxLenghtVideoInMo();
        this.qualityVideo = videoConfiguration.getQualityVideo();
    }

    public static String getLocationSdcardVideo() {
        return LOCATION_SDCARD_VIDEO;
    }

    public static String getExtensionFormatVideo() {
        return EXTENSION_FORMAT_VIDEO;
    }

    public static String getAppName() {
        return APP_NAME;
    }

    public static int getId() {
        return id;
    }

    public static int getMaxDurationVideo() {
        return maxDurationVideo;
    }

    public static int getMaxLenghtVideoInMo() {
        return maxLenghtVideoInMo;
    }

    public static int getQualityVideo() {
        return qualityVideo;
    }

    public static void setId(int id) {
        VideoConfigurationStatic.id = id;
    }

    public static void setMaxDurationVideo(int maxDurationVideo) {
        VideoConfigurationStatic.maxDurationVideo = maxDurationVideo;
    }

    public static void setMaxLenghtVideoInMo(int maxLenghtVideoInMo) {
        VideoConfigurationStatic.maxLenghtVideoInMo = maxLenghtVideoInMo;
    }

    public static void setQualityVideo(int qualityVideo) {
        VideoConfigurationStatic.qualityVideo = qualityVideo;
    }
}
