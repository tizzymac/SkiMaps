package tizzy.skimapp.ResortModel;

import java.io.Serializable;

/**
 * Created by tizzy on 10/15/18.
 */

public class Run implements Serializable {

    private String mName; //, mLevel;
    private SkiLevel mLevel;
    private Node mStart, mEnd;

    public Run(String name, String level, Node start, Node end) {
        this.mName = name;
        this.mLevel = new SkiLevel(level);
        this.mStart = start;
        this.mEnd = end;
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

    public Boolean isWithinLevel(String level) {
        SkiLevel skiersLevel = new SkiLevel(level);
        if (mLevel.getLevelNumber() <= skiersLevel.getLevelNumber()) {
            return true;
        }
        return false;
    }
}
