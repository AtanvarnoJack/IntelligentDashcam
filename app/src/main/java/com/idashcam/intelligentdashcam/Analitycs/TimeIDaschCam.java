package com.idashcam.intelligentdashcam.Analitycs;

/**
 * Created by alexandre on 20/04/2015.
 */
public class TimeIDaschCam {
    private final static int MS_MULTIPLIER = 1000;
    private int ms;

    public TimeIDaschCam() {}

    public TimeIDaschCam(int ms) {
        this.ms = ms;
    }

    public int getSecondForMS (int ms){
        ms = ms / MS_MULTIPLIER;
        for (int i = 0; ms >= 60; i++){
            ms = ms -60;
        }
        return ms;
    }

    public  int getMinuteForMs(int ms){
        ms = ms / MS_MULTIPLIER;
        int i;
        for (i = 0; ms >= 60; i++){
            ms = ms -60;
        }
        return i;
    }

    public int getMsForSecondAndMinute (int second, int minute){
        return ((minute * 60) + second) * MS_MULTIPLIER;
    }

    public int getMs() {
        return ms;
    }

    public void setMs(int ms) {
        this.ms = ms;
    }

    public static int getMsMultiplier() {
        return MS_MULTIPLIER;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TimeIDaschCam time = (TimeIDaschCam) o;

        if (ms != time.ms) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return ms;
    }

    @Override
    public String toString() {
        return "Time{" +
                "ms=" + ms +
                '}';
    }
}
