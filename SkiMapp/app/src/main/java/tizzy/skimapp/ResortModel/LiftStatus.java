package tizzy.skimapp.ResortModel;

import java.io.Serializable;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class LiftStatus implements Serializable {

    private Date mOpeningTime;
    private Date mClosingTime;
    private int mQueueLength;

    SimpleDateFormat parser = new SimpleDateFormat("HH:mm");

    public LiftStatus(String open, String close) throws ParseException {

        mOpeningTime = parser.parse(open);
        mClosingTime = parser.parse(close);
    }

    public boolean isOpen() {

        // TODO
        Calendar cal = Calendar.getInstance();
        Date timeNow = cal.getTime();
        if (timeNow.after(mOpeningTime) && timeNow.before(mClosingTime)) {
            return true;
        }

        return false;
    }

    public String openingTimeStr() {
        return parser.format(mOpeningTime);
    }

    public String closingTimeStr() {
        return parser.format(mClosingTime);
    }

    public int getQueueLength() {
        return mQueueLength;
    }

    public void setQueueLength(int queueLength) {
        mQueueLength = queueLength;
    }
}
