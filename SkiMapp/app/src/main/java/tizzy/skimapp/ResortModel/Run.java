package tizzy.skimapp.ResortModel;

/**
 * Created by tizzy on 10/15/18.
 */

public class Run {

    private String mName, mLevel;
    private GeoNode mStart, mEnd;

    public Run(String name, String level, GeoNode start, GeoNode end) {
        this.mName = name;
        this.mLevel = level;
        this.mStart = start;
        this.mEnd = end;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        this.mName = name;
    }

    public String getLevel() {
        return mLevel;
    }

    public void setLevel(String level) {
        this.mLevel = level;
    }

    public GeoNode getStart() {
        return mStart;
    }

    public void setStart(GeoNode start) {
        this.mStart = start;
    }

    public GeoNode getEnd() {
        return mEnd;
    }

    public void setEnd(GeoNode end) {
        this.mEnd = end;
    }
}
