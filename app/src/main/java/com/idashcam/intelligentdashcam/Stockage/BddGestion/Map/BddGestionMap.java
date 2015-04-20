package com.idashcam.intelligentdashcam.Stockage.BddGestion.Map;

import com.idashcam.intelligentdashcam.Analitycs.Map.MapConfiguration;

/**
 * Created by alexandre on 16/04/2015.
 */
public interface BddGestionMap {
    MapConfiguration initConfByBDD();

    void updateConfByBDD(MapConfiguration mapConfigurationUpdated);

    MapConfiguration getConfByBDD();
}
