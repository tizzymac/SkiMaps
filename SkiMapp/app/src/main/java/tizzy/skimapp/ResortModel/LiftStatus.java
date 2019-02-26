package tizzy.skimapp.ResortModel;

import java.io.Serializable;
import java.sql.Time;

public class LiftStatus implements Serializable {

    private boolean mOpen;
    private Time mOpeningTime;
    private Time mClosingTime;
    private int mQueueLength;

    public LiftStatus() {
        mOpen = true;
    }

    public boolean isOpen() {

        // TODO

        return mOpen;
    }

    public void setOpen(boolean open) {
        mOpen = open;
    }

    public Time getOpeningTime() {

        // TODO
        if (mOpeningTime == null) {
            return new Time(8,0,0);
        }

        return mOpeningTime;
    }

    public void setOpeningTime(Time openingTime) {
        mOpeningTime = openingTime;
    }

    public Time getClosingTime() {

        // TODO
        if (mClosingTime == null) {
            return new Time(15,30,0);
        }

        return mClosingTime;
    }

    public void setClosingTime(Time closingTime) {
        mClosingTime = closingTime;
    }

    public int getQueueLength() {
        return mQueueLength;
    }

    public void setQueueLength(int queueLength) {
        mQueueLength = queueLength;
    }
}
