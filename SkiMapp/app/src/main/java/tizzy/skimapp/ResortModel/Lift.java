package tizzy.skimapp.ResortModel;

/**
 * Created by tizzy on 10/15/18.
 */

public class Lift {

    private String name;
    private GeoNode start, end;

    public Lift(String name, GeoNode start, GeoNode end) {
        this.name = name;
        this.start = start;
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
