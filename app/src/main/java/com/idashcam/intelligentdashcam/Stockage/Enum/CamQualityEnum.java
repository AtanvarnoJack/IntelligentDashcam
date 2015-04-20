package com.idashcam.intelligentdashcam.Stockage.Enum;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by alexandre on 11/03/2015.
 */
public class CamQualityEnum {
    public final static int QUALITY_LOW = 0;
    public final static int QUALITY_HIGH = 1;
    public final static int QUALITY_QCIF = 2;
    public final static int QUALITY_CIF = 3;
    public final static int QUALITY_480P = 4;
    public final static int QUALITY_720P = 5;
    public final static int QUALITY_1080P = 6;
    public final static int QUALITY_QVGA = 7;
    public final static int QUALITY_TIME_LAPSE_LOW = 1000;
    public final static int QUALITY_TIME_LAPSE_HIGH = 1001;
    public final static int QUALITY_TIME_LAPSE_QCIF = 1002;
    public final static int QUALITY_TIME_LAPSE_CIF = 1003;
    public final static int QUALITY_TIME_LAPSE_480P = 1004;
    public final static int QUALITY_TIME_LAPSE_720P = 1005;
    public final static int QUALITY_TIME_LAPSE_1080P = 1006;
    public final static int QUALITY_TIME_LAPSE_QVGA = 1007;

    private Map<String,Integer> mapQualityByName = new HashMap<String,Integer>();
    private Map<Integer,String> mapNameByQuality = new HashMap<Integer,String>();

    public CamQualityEnum(){
        mapQualityByName.put("QUALITY_LOW", 0);
        mapQualityByName.put("QUALITY_HIGH", 1);
        mapQualityByName.put("QUALITY_QCIF", 2);
        mapQualityByName.put("QUALITY_CIF", 3);
        mapQualityByName.put("QUALITY_480P", 4);
        mapQualityByName.put("QUALITY_720P", 5);
        mapQualityByName.put("QUALITY_1080P", 6);
        mapQualityByName.put("QUALITY_QVGA", 7);
        mapQualityByName.put("QUALITY_TIME_LAPSE_LOW", 1000);
        mapQualityByName.put("QUALITY_TIME_LAPSE_HIGH", 1001);
        mapQualityByName.put("QUALITY_TIME_LAPSE_QCIF", 1002);
        mapQualityByName.put("QUALITY_TIME_LAPSE_CIF", 1003);
        mapQualityByName.put("QUALITY_TIME_LAPSE_480P", 1004);
        mapQualityByName.put("QUALITY_TIME_LAPSE_720P", 1005);
        mapQualityByName.put("QUALITY_TIME_LAPSE_1080P", 1006);
        mapQualityByName.put("QUALITY_TIME_LAPSE_QVGA", 1007);

        mapNameByQuality.put(0,"QUALITY_LOW");
        mapNameByQuality.put(1,"QUALITY_HIGH");
        mapNameByQuality.put(2,"QUALITY_QCIF");
        mapNameByQuality.put(3,"QUALITY_CIF");
        mapNameByQuality.put(4,"QUALITY_480P");
        mapNameByQuality.put(5,"QUALITY_720P");
        mapNameByQuality.put(6,"QUALITY_1080P");
        mapNameByQuality.put(7,"QUALITY_QVGA");
        mapNameByQuality.put(1000,"QUALITY_TIME_LAPSE_LOW");
        mapNameByQuality.put(1001,"QUALITY_TIME_LAPSE_HIGH");
        mapNameByQuality.put(1002,"QUALITY_TIME_LAPSE_QCIF");
        mapNameByQuality.put(1003,"QUALITY_TIME_LAPSE_CIF");
        mapNameByQuality.put(1004,"QUALITY_TIME_LAPSE_480P");
        mapNameByQuality.put(1005,"QUALITY_TIME_LAPSE_720P");
        mapNameByQuality.put(1006,"QUALITY_TIME_LAPSE_1080P");
        mapNameByQuality.put(1007,"QUALITY_TIME_LAPSE_QVGA");
    }

    public int getQualityIntByName(String name){
        return mapQualityByName.get(name);
    }

    public String getNameQualityByInt(int qualityNumber){
        return mapNameByQuality.get(qualityNumber);
    }

    public Collection<String> getAllQualityName(){
        return mapNameByQuality.values();
    }
}
