package com.idashcam.intelligentdashcam.Stockage.BddGestion.Video;


import com.idashcam.intelligentdashcam.Analitycs.Video.VideoConfiguration;

/**
 * Created by alexandre on 13/04/2015.
 */
public interface BddGestionVideo {
    void initConfByBDD();

    void updateConfByBDD(VideoConfiguration videoConfigurationUpdated);

    VideoConfiguration getConfByBDD();
}
