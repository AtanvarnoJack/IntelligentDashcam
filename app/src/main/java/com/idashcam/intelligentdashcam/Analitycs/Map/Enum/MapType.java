package com.idashcam.intelligentdashcam.Analitycs.Map.Enum;

import com.google.android.gms.maps.GoogleMap;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexandre on 15/04/2015.
 */
public enum MapType {
    Normal("MAP_TYPE_NORMAL", GoogleMap.MAP_TYPE_NORMAL),
    Hybrid("MAP_TYPE_HYBRID",GoogleMap.MAP_TYPE_HYBRID),
    Terrain("MAP_TYPE_TERRAIN",GoogleMap.MAP_TYPE_TERRAIN),
    None("MAP_TYPE_NONE",GoogleMap.MAP_TYPE_NONE),
    Satellite("MAP_TYPE_SATELLITE",GoogleMap.MAP_TYPE_SATELLITE);

    MapType(String map_type, int mapType) {}
}
