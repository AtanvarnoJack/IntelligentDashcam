package com.idashcam.intelligentdashcam.Stockage.BddGestion.Video;

import android.content.Context;
import android.util.Log;

import com.idashcam.intelligentdashcam.Analitycs.Video.VideoConfiguration;
import com.idashcam.intelligentdashcam.Analitycs.Video.VideoConfigurationStatic;
import com.idashcam.intelligentdashcam.Stockage.BDD.SQLiteDatabaseHandlerVideo;
import com.idashcam.intelligentdashcam.Stockage.Enum.CamQualityEnum;

import java.util.List;

/**
 * Created by alexandre on 13/04/2015.
 */
public class BddGestionVideoImpl implements BddGestionVideo {

    Context context;
    SQLiteDatabaseHandlerVideo db;
    VideoConfiguration videoConfiguration;

    public BddGestionVideoImpl(Context context) {
        this.context = context;
    }

    @Override
    public void initConfByBDD() {
        db = new SQLiteDatabaseHandlerVideo(context);
        VideoConfiguration videoConfigurationInit = new VideoConfiguration(1, 50000,50000000, CamQualityEnum.QUALITY_HIGH);// 50 seconds value in ms// Approximately 50 megabytes

        List<VideoConfiguration> found = db.showAll();
        if (found.size() == 0){ //Init Base if not existe
            db.addOne(videoConfigurationInit);
        }else if (found.size() == 1){   //If videoconf are ok no action.
        }else{// all reste are nok reinit
            for (VideoConfiguration configuration : found) {
                db.deleteOne(configuration);
            }
            db.addOne(videoConfigurationInit);
        }
        List<VideoConfiguration> videoConfList = db.showAll();
        videoConfiguration = videoConfList.get(0);

        new VideoConfigurationStatic(videoConfiguration);   //Init VideoConfigurationStatic
        Log.i("SQLite:Configuration:", videoConfiguration.toString());
        db.close();
    }

    @Override
    public void updateConfByBDD(VideoConfiguration videoConfigurationUpdated){
        db = new SQLiteDatabaseHandlerVideo(context);
        db.updateOne(videoConfigurationUpdated);
        db.close();
    }

    @Override
    public VideoConfiguration getConfByBDD(){
        db = new SQLiteDatabaseHandlerVideo(context);
        List<VideoConfiguration> found = db.showAll();
        db.close();

        return found.get(0);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public SQLiteDatabaseHandlerVideo getDb() {
        return db;
    }

    public void setDb(SQLiteDatabaseHandlerVideo db) {
        this.db = db;
    }

    public VideoConfiguration getVideoConfiguration() {
        return videoConfiguration;
    }

    public void setVideoConfiguration(VideoConfiguration videoConfiguration) {
        this.videoConfiguration = videoConfiguration;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BddGestionVideoImpl that = (BddGestionVideoImpl) o;

        if (context != null ? !context.equals(that.context) : that.context != null) return false;
        if (db != null ? !db.equals(that.db) : that.db != null) return false;
        if (videoConfiguration != null ? !videoConfiguration.equals(that.videoConfiguration) : that.videoConfiguration != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = context != null ? context.hashCode() : 0;
        result = 31 * result + (db != null ? db.hashCode() : 0);
        result = 31 * result + (videoConfiguration != null ? videoConfiguration.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BddGestionVideoImpl{" +
                "context=" + context +
                ", db=" + db +
                ", videoConfiguration=" + videoConfiguration +
                '}';
    }
}
