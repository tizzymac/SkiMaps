package tizzy.skimapp.ResortModel;

import android.util.Log;

import java.io.Serializable;

public class SkiLevel implements Serializable {

    private String mLevelString;
    private int mLevelNumber;
    private String mRegion;

    public SkiLevel(String levelString, String region) {
        mLevelString = levelString;
        mRegion = region;

        switch (region) {
            case "USA" :
            switch (levelString) {
                case "Black":
                    mLevelNumber = 3;
                    break;
                case "Blue":
                    mLevelNumber = 2;
                    break;
                case "Green":
                    mLevelNumber = 1;
                    break;
                default:
                    mLevelString = "Black";
                    mLevelNumber = 3;
            }
            break;

            case "Europe" :
            switch (levelString) {
                case "Black":
                    mLevelNumber = 4;
                    break;
                case "Red":
                    mLevelNumber = 3;
                    break;
                case "Blue":
                    mLevelNumber = 2;
                    break;
                case "Green":
                    mLevelNumber = 1;
                    break;
                default:
                    mLevelString = "Black";
                    mLevelNumber = 4;
            }
            break;

            default:
                Log.e("SkiLevel", "Invalid region entered");
                throw new IllegalStateException();
        }
    }

    public String getRegion() {
        return mRegion;
    }

    public String getLevelString() {
        return mLevelString;
    }

    public int getLevelNumber() {
        return mLevelNumber;
    }
}
