package tizzy.skimapp.ResortModel;

import java.io.Serializable;

/**
 * Created by tizzy on 10/15/18.
 */

public class Run implements Serializable {

    private String mName, mLevel;
    private Node mStart, mEnd;
    private Edge mEdge;

    public Run(String name, String level, Node start, Node end) {
        this.mName = name;
        this.mLevel = level;
        this.mStart = start;
        this.mEnd = end;

        this.mEdge = new Edge(name, start, end, edgeWeight());
    }

    public String getName() {
        return mName;
    }

    public String getLevel() {
        return mLevel;
    }

    public Edge getEdge() {
        return mEdge;
    }

    private int edgeWeight() {
        // Distance between start and end nodes
        return (int) Math.round(
                Math.sqrt(
                        (mEnd.getCoords().getX() - mStart.getCoords().getX())^2 +
                                (mEnd.getCoords().getY() - mStart.getCoords().getY())^2 +
                                (mEnd.getCoords().getZ() - mStart.getCoords().getZ())^2 ));
    }
}
