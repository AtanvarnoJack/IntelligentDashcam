package com.idashcam.intelligentdashcam.Stockage.BddGestion.Map;

import android.content.Context;

import com.idashcam.intelligentdashcam.Analitycs.Map.MapConfiguration;
import com.idashcam.intelligentdashcam.Stockage.BDD.SQLiteDatabaseHandlerMap;

import java.util.List;

/**
 * Created by alexandre on 16/04/2015.
 */
public class BddGestionMapImpl implements BddGestionMap {
    Context context;
    MapConfiguration mapConfiguration;
    SQLiteDatabaseHandlerMap db;

    public BddGestionMapImpl(Context context) {
        this.context = context;
    }

    @Override
    public MapConfiguration initConfByBDD() {
        db = new SQLiteDatabaseHandlerMap(context);
        MapConfiguration MapConfigurationInit = new MapConfiguration(MapConfiguration.initMapConfiguration());

        List<MapConfiguration> found = db.showAll();
        if (found.size() == 0){ //Init Base if not existe
            db.addOne(MapConfigurationInit);
        }else if (found.size() == 1){   //If mapconf are ok no action.
        }else{// all reste are nok reinit
            for (MapConfiguration configuration : found) {
                db.deleteOne(configuration);
            }
            db.addOne(MapConfigurationInit);
        }
        List<MapConfiguration> mapConfList = db.showAll();
        mapConfiguration = mapConfList.get(0);

        db.close();

        return mapConfiguration;
    }

    @Override
    public void updateConfByBDD(MapConfiguration mapConfigurationUpdated) {
        db = new SQLiteDatabaseHandlerMap(context);
        db.updateOne(mapConfigurationUpdated);
        db.close();
    }

    @Override
    public MapConfiguration getConfByBDD() {
        db = new SQLiteDatabaseHandlerMap(context);
        List<MapConfiguration> found = db.showAll();
        db.close();
        return found.get(0);
    }

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public MapConfiguration getMapConfiguration() {
        return mapConfiguration;
    }

    public void setMapConfiguration(MapConfiguration mapConfiguration) {
        this.mapConfiguration = mapConfiguration;
    }

    public SQLiteDatabaseHandlerMap getDb() {
        return db;
    }

    public void setDb(SQLiteDatabaseHandlerMap db) {
        this.db = db;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BddGestionMapImpl that = (BddGestionMapImpl) o;

        if (context != null ? !context.equals(that.context) : that.context != null) return false;
        if (db != null ? !db.equals(that.db) : that.db != null) return false;
        if (mapConfiguration != null ? !mapConfiguration.equals(that.mapConfiguration) : that.mapConfiguration != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = context != null ? context.hashCode() : 0;
        result = 31 * result + (mapConfiguration != null ? mapConfiguration.hashCode() : 0);
        result = 31 * result + (db != null ? db.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "BddGestionMapImpl{" +
                "context=" + context +
                ", mapConfiguration=" + mapConfiguration +
                ", db=" + db +
                '}';
    }
}
