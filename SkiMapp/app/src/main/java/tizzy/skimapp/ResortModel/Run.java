package tizzy.skimapp.ResortModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tizzy on 10/15/18.
 */

public class Run extends Edge {

    private String mName; // should be unique
    private SkiLevel mLevel;
    private Node mStart, mEnd;
    private List<Node> mMidpoints = null;
    private RunStatus mRunStatus;

    public Run(String name, String level, Node start, Node end) {
        this.mName = name;
        this.mLevel = new SkiLevel(level);
        this.mStart = start;
        this.mEnd = end;
        this.mRunStatus = new RunStatus();
    }

    public void setMidpoints(List<Node> midpoints) {
        mMidpoints = midpoints;
    }

    public List<Node> getMidpoints() {
        return mMidpoints;
    }

    public RunStatus getStatus() {
        return mRunStatus;
    }

    public String getName() {
        return mName;
    }

    public String getLevel() {
        return mLevel.getLevelString();
    }

    public Node getStart() {
        return mStart;
    }

    @Override
    public int getWeight() {
        return (int) Math.round(
                Math.sqrt(
                        Math.pow((mEnd.getCoords().getX() - mStart.getCoords().getX()), 2) +
                                Math.pow((mEnd.getCoords().getY() - mStart.getCoords().getY()), 2) +
                                Math.pow((mEnd.getCoords().getZ() - mStart.getCoords().getZ()), 2) ));
    }

    @Override
    public List<Edge> getEdgeSegments() {
        List<Edge> edgeSegments = new ArrayList<>();
        if (mMidpoints == null) {
            edgeSegments.add(this);
        } else {
            // Cycle through midpoints
            for (int i = 1; i < mMidpoints.size(); i++) {
                // Get edge segment
                edgeSegments.add(new Run(
                        mName,
                        mLevel.getLevelString(),
                        mMidpoints.get(i-1),
                        mMidpoints.get(i)));
            }
        }

        return edgeSegments;
    }

    public Node getEnd() {
        return mEnd;
    }


    public Boolean isWithinLevel(String level) {
        SkiLevel skiersLevel = new SkiLevel(level);
        if (mLevel.getLevelNumber() <= skiersLevel.getLevelNumber()) {
            return true;
        }
        return false;
    }

    // For joining two runs
    public void changeEnd(Node end) {
        if (mMidpoints == null) {
            mMidpoints = new ArrayList<>();
        }
        mMidpoints.add(mEnd);
        mEnd = end;
    }

    @Override
    public String toString() {
        // Need to override this for the Spinner Adapter
        return mName;
    }
}
