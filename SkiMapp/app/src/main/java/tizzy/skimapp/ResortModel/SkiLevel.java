package tizzy.skimapp.ResortModel;

import java.io.Serializable;

public class SkiLevel implements Serializable {

    private String mLevelString;
    private int mLevelNumber;

    public SkiLevel(String levelString) {
        mLevelString = levelString;

        switch (levelString) {
            case "Black" : mLevelNumber = 3;
                            break;
            case "Blue"  : mLevelNumber = 2;
                            break;
            case "Green" : mLevelNumber = 1;
                            break;
            default :
                mLevelString = "Black";
                mLevelNumber = 3;
        }
    }

    public String getLevelString() {
        return mLevelString;
    }

    public void setLevelString(String levelString) {
        mLevelString = levelString;
    }

    public int getLevelNumber() {
        return mLevelNumber;
    }

    public void setLevelNumber(int levelNumber) {
        mLevelNumber = levelNumber;
    }
}
