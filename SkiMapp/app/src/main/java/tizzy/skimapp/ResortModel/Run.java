package tizzy.skimapp.ResortModel;

/**
 * Created by tizzy on 10/15/18.
 */

public class Run {

    private String name, level;
    private GeoNode start, end;

    public Run(String name, String level, GeoNode start, GeoNode end) {
        this.name = name;
        this.level = level;
        this.start = start;
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public GeoNode getStart() {
        return start;
    }

    public void setStart(GeoNode start) {
        this.start = start;
    }

    public GeoNode getEnd() {
        return end;
    }

    public void setEnd(GeoNode end) {
        this.end = end;
    }
}
