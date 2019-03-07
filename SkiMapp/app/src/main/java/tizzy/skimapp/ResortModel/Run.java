package tizzy.skimapp.ResortModel;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tizzy on 10/15/18.
 */

public class Run extends Edge implements Comparable<Run> {

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
    public int getSimpleWeight() {
        return (int) Math.round(
                Math.sqrt(
                        Math.pow((mEnd.getCoords().getX() - mStart.getCoords().getX()), 2) +
                        Math.pow((mEnd.getCoords().getY() - mStart.getCoords().getY()), 2) +
                        Math.pow((mEnd.getCoords().getZ() - mStart.getCoords().getZ()), 2) ));
    }

    public int getSlopeAngle() {
        // Average angle

        // Find the run in xy plane
        // set z coord in mStart same as z coord in mEnd
        Double run = Math.sqrt(
                Math.pow((mEnd.getCoords().getX() - mStart.getCoords().getX()), 2) +
                Math.pow((mEnd.getCoords().getY() - mStart.getCoords().getY()), 2)
        );

        // Find the rise
        // Distance between z coord of mEnd to z coord of mStart
        Double rise = Math.abs(mEnd.getCoords().getZ() - mStart.getCoords().getZ());

        // Slope is rise / run
        return (int) Math.atan(rise/run);
        // TODO test this
    }

    @Override
    public int getWeight(SkiLevel level) {
        // Factors:
        // - Length
        // - Steepness
        // - Skier's ability + Difficulty of slope


        return getSimpleWeight();
    }

    @Override
    public List<Edge> getEdgeSegments() {
        List<Edge> edgeSegments = new ArrayList<>();
        if (mMidpoints == null) {
            edgeSegments.add(this);
        } else {
            // Add start & end nodes
            List<Node> allNodes = new ArrayList<>();
            allNodes.add(mStart);
            allNodes.addAll(mMidpoints);
            allNodes.add(mEnd);

            // Cycle through nodes
            for (int i = 1; i < allNodes.size(); i++) {
                // Get edge segment
                edgeSegments.add(new Run(
                        mName,
                        mLevel.getLevelString(),
                        allNodes.get(i-1),
                        allNodes.get(i)));
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

    @Override
    public String toString() {
        // Need to override this for the Spinner Adapter
        return mName;
    }

    @Override
    public int compareTo(@NonNull Run otherRun) {
        return this.mName.compareTo(otherRun.getName());
    }
}
