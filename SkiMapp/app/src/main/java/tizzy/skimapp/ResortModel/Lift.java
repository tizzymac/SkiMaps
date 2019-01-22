package tizzy.skimapp.ResortModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tizzy on 10/15/18.
 */

public class Lift extends Edge {

    private String mName;
    private Node mStart, mEnd;
    private LiftStatus mStatus;
    private List<Node> mMidpoints = null;
    private int mWeight;

    public Lift(String name, Node start, Node end) {
        this.mName = name;
        this.mStart = start;
        this.mEnd = end;
        this.mStatus = new LiftStatus();
        this.mWeight = getSimpleWeight();
    }

    @Override
    public String getName() {
        return mName;
    }

    @Override
    public Node getEnd() {
        return mEnd;
    }

    @Override
    public Node getStart() {
        return mStart;
    }

    @Override
    public int getSimpleWeight() {
        // Distance between start and end nodes
        return (int) Math.round(
                Math.sqrt(
                        Math.pow((mEnd.getCoords().getX() - mStart.getCoords().getX()), 2) +
                                Math.pow((mEnd.getCoords().getY() - mStart.getCoords().getY()), 2) +
                                Math.pow((mEnd.getCoords().getZ() - mStart.getCoords().getZ()), 2) ));
    }

    @Override
    public int getWeight(SkiLevel level) {
        // Factors
        // - Lift speed
        // - Length

        // TODO
        return mWeight;
    }

    @Override
    public List<Edge> getEdgeSegments() {
        List<Edge> edgeSegments = new ArrayList<>();
        if (mMidpoints == null) {
            edgeSegments.add((Edge) this);
        } else {
            // Cycle through midpoints
        }

        return edgeSegments;
    }

    @Override
    public String toString() {
        // Need to override this for the Spinner Adapter
        return mName;
    }

    public void increaseWeight(int i) {
        mWeight = mWeight * i;
    }

}
