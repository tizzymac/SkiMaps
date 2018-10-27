package tizzy.skimapp.ResortModel;

import java.io.Serializable;

/**
 * Created by tizzy on 10/15/18.
 */

public class Lift implements Serializable {

    private String mName;
    private Node mStart, mEnd;  // Do we need these?
    //private Edge mEdge;

    public Lift(String name, Node start, Node end) {
        this.mName = name;
        this.mStart = start;
        this.mEnd = end;

        // this.mEdge = new Edge(name, start, end, edgeWeight());
    }

    public String getName() {
        return mName;
    }

    public Node getStart() {
        return mStart;
    }

    public Node getEnd() {
        return mEnd;
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
