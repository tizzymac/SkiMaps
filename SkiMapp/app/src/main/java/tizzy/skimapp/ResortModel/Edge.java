package tizzy.skimapp.ResortModel;

import java.util.Set;

public class Edge {

    private final String mId;
    private final Node mSource;
    private final Node mDestination;
    private final int mWeight;

    private Set<Lift> mLifts;
    private Set<Run> mRuns;

    public Edge(String id, Node source, Node destination, int weight) {
        this.mId = id;
        this.mSource = source;
        this.mDestination = destination;
        this.mWeight = weight;
    }

    public String getId() {
        return mId;
    }
    public Node getDestination() {
        return mDestination;
    }

    public Node getSource() {
        return mSource;
    }
    public int getWeight() {
        return mWeight;
    }

    @Override
    public String toString() {
        return mSource + " " + mDestination;
    }

    public void setLiftsAndRuns(Set<Lift> lifts, Set<Run> runs) {
        this.mLifts = lifts;
        this.mRuns = runs;
    }

    public Set<Lift> getLifts() {
        return mLifts;
    }

    public Set<Run> getRuns() {
        return mRuns;
    }
}
