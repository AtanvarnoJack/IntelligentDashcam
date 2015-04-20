package com.idashcam.intelligentdashcam.Analitycs.Video;

import com.idashcam.intelligentdashcam.Stockage.Enum.CamQualityEnum;

/**
 * Created by alco on 03/03/2015.
 */
public class VideoConfiguration {
    private final static String LOCATION_SDCARD_VIDEO = "/sdcard/IDaschCam";
    private final static String EXTENSION_FORMAT_VIDEO = ".mp4";
    private final static String APP_NAME = "IntelligentDaschCamAppSession:";
    private int id;
    private int maxDurationVideo; // 50 seconds value in ms
    private int maxLenghtVideoInMo; // Approximately 50 megabytes
    private int qualityVideo; //Ce repertorier au fichier videoQualityDescription.txt dans le package analitycs

    public VideoConfiguration() {
    }

    public VideoConfiguration(int id, int maxDurationVideo, int maxLenghtVideoInMo) {
        this.id = id;
        this.maxDurationVideo = maxDurationVideo;
        this.maxLenghtVideoInMo = maxLenghtVideoInMo;
        this.qualityVideo = CamQualityEnum.QUALITY_HIGH;
    }

    public VideoConfiguration(int id, int maxDurationVideo, int maxLenghtVideoInMo, int qualityVideo) {
        this.id = id;
        this.maxDurationVideo = maxDurationVideo;
        this.maxLenghtVideoInMo = maxLenghtVideoInMo;
        this.qualityVideo = qualityVideo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLocationSdcardVideo() {
        return LOCATION_SDCARD_VIDEO;
    }

    public String getExtensionFormatVideo() {
        return EXTENSION_FORMAT_VIDEO;
    }

    public String getAppName() {
        return APP_NAME;
    }

    public int getMaxDurationVideo() {
        return maxDurationVideo;
    }

    public void setMaxDurationVideo(int maxDurationVideo) {
        this.maxDurationVideo = maxDurationVideo;
    }

    public int getMaxLenghtVideoInMo() {
        return maxLenghtVideoInMo;
    }

    public void setMaxLenghtVideoInMo(int maxLenghtVideoInMo) {
        this.maxLenghtVideoInMo = maxLenghtVideoInMo;
    }

    public int getQualityVideo() {
        return qualityVideo;
    }

    public void setQualityVideo(int qualityVideo) {
        this.qualityVideo = qualityVideo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        VideoConfiguration that = (VideoConfiguration) o;

        if (id != that.id) return false;
        if (maxDurationVideo != that.maxDurationVideo) return false;
        if (maxLenghtVideoInMo != that.maxLenghtVideoInMo) return false;
        if (qualityVideo != that.qualityVideo) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + maxDurationVideo;
        result = 31 * result + maxLenghtVideoInMo;
        result = 31 * result + qualityVideo;
        return result;
    }

    @Override
    public String toString() {
        return "VideoConfiguration{" +
                "id=" + id +
                ", maxDurationVideo=" + maxDurationVideo +
                ", maxLenghtVideoInMo=" + maxLenghtVideoInMo +
                ", qualityVideo=" + qualityVideo +
                '}';
    }
}
