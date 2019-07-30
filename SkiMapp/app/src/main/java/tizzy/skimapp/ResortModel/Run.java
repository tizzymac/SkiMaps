package tizzy.skimapp.ResortModel;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;

/**
 * Created by tizzy on 10/15/18.
 */

public class Run extends Edge implements Comparable<Run>, Serializable {

    private String mName; // should be unique
    private SkiLevel mLevel;
    private Node mStart, mEnd;
    private List<Node> mMidpoints = null;
    private RunStatus mRunStatus;
    private int mWeight;

    private transient DatabaseReference mDatabase;

    public Run(String name, String level, String region, Node start, Node end) {
        this.mName = name;
        this.mLevel = new SkiLevel(level, region);
        this.mStart = start;
        this.mEnd = end;
        this.mRunStatus = new RunStatus();
        this.mWeight = 4;

        if (mLevel.getLevelString().equals("Black")) {
            mRunStatus.setGroomed(false);
        }

        // Get reference to DB
        mDatabase = FirebaseDatabase.getInstance().getReference().child(mName);
        mDatabase.addValueEventListener(postListener);
    }

    private ValueEventListener postListener = new ValueEventListener() {
        @Override
        public void onDataChange(DataSnapshot snapshot) {
            // update run status
            if (!(snapshot.getValue(RunStatus.class) == null)) {
                mRunStatus = snapshot.getValue(RunStatus.class);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {
            Log.e("Firebase", "DatabaseError" + databaseError);
        }
    };

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

    public SkiLevel getLevel() {
        return mLevel;
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

    @Override
    public int getWeight() {
        return mWeight;
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
    }

    public void setRunStatus(RunStatus runStatus) {
        mRunStatus = runStatus;
    }

    @Override
    public void setWeight(int weight) {
        this.mWeight = weight;
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
                Run run = new Run(
                        mName,
                        mLevel.getLevelString(),
                        mLevel.getRegion(),
                        allNodes.get(i-1),
                        allNodes.get(i));
                //run.setWeight(mWeight/allNodes.size());
                run.setWeight(mWeight/4);
                edgeSegments.add(run);
            }
        }

        return edgeSegments;
    }

    public Node getEnd() {
        return mEnd;
    }

    public Boolean isWithinLevel(String level, String region) {
        SkiLevel skiersLevel = new SkiLevel(level, region);
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
