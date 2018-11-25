package tizzy.skimapp.ResortModel;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

public class Edge implements Serializable {

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

        this.mRuns = new HashSet<>();
        this.mLifts = new HashSet<>();
    }

    public String getString() {
        String str = "";

        if (mLifts != null) {
            for (Lift lift : mLifts) {
                str = str + lift.getName() + "\n";
            }
        }

        if (mRuns != null) {
            for (Run run : mRuns) {
                str = str + run.getName() + "\n";
            }
        }

        return str;
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

    public void addRun(Run run) {
        mRuns.add(run);
    }

    public void addLift(Lift lift) {
        mLifts.add(lift);
    }
}
