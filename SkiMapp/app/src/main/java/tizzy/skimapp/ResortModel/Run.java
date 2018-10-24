package tizzy.skimapp.ResortModel;

/**
 * Created by tizzy on 10/15/18.
 */

public class Run {

    private String mName, mLevel;
    private Node mStart, mEnd;   // Do we need these?

    public Run(String name, String level, Node start, Node end) {
        this.mName = name;
        this.mLevel = level;
        this.mStart = start;
        this.mEnd = end;
    }

    public String getName() {
        return mName;
    }

    public String getLevel() {
        return mLevel;
    }

    public void setLevel(String level) {
        this.mLevel = level;
    }
}
