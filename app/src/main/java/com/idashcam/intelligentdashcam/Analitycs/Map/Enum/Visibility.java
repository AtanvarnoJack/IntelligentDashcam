package com.idashcam.intelligentdashcam.Analitycs.Map.Enum;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by alexandre on 15/04/2015.
 */
public enum Visibility {
    Invisible("INVISIBLE", View.INVISIBLE),
    Visible("VISIBLE",View.VISIBLE);

    Visibility(String visibility, int intVisibility) {}
}
