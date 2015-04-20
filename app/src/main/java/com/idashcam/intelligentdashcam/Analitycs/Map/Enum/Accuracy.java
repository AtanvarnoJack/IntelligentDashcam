package com.idashcam.intelligentdashcam.Analitycs.Map.Enum;

import com.google.android.gms.location.LocationRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexandre on 15/04/2015.
 */
public enum Accuracy {
    High("PRIORITY_HIGH_ACCURACY", LocationRequest.PRIORITY_HIGH_ACCURACY),
    Low("PRIORITY_LOW_POWER", LocationRequest.PRIORITY_LOW_POWER),
    NoPower("PRIORITY_NO_POWER", LocationRequest.PRIORITY_NO_POWER),
    BalancedPower("PRIORITY_BALANCED_POWER_ACCURACY", LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY),;

    Accuracy(String accuracy_name, int accuracy) {}
}
