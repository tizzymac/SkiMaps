package tizzy.skimapp.ResortModel;

import android.content.Context;
import android.util.AttributeSet;

/**
 * Created by tizzy on 10/15/18.
 */

public class Resort {

    private Lift[] mLifts;
    private Run[] mRuns;

    public Resort(Context context, AttributeSet attrs) {


//        for (int i = 0; i < attrs.getAttributeCount(); i++) {
//            String attr = attrs.getAttributeName(i);
//            if ("name".equals(attr)) {
//                mName = attrs.getAttributeValue(i);
//            } else if ("type".equals(attr)) {
//                // This will load the value of the string resource you
//                // referenced in your XML
//                int stringResource = attrs.getAttributeResourceValue(i, 0);
//                mType = context.getString(stringResource);
//            } else if ("stat_attack".equals(attr)) {
//                mStatAttack = attrs.getAttributeIntValue(i, -1);
//            }
//        }
    }

    public Lift[] getLifts() {
        return mLifts;
    }

    public void setLifts(Lift[] lifts) {
        mLifts = lifts;
    }

    public Run[] getRuns() {
        return mRuns;
    }

    public void setRuns(Run[] runs) {
        mRuns = runs;
    }
}
