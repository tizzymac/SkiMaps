package tizzy.skimapp.RouteFinding.SkiRoute;

import java.io.Serializable;

import tizzy.skimapp.ResortModel.Node;

public class SkiRouteEdge implements Serializable {

    private String mEdgeName;
    private Node mEndNode;
    private Integer mEdgeInfo;
    private boolean mCompleted;
    private String mLiftType;

    public SkiRouteEdge(String edgeName, Node endNode, Integer edgeInfo, String liftType) {
        mEdgeName = edgeName;
        mEndNode = endNode;
        mEdgeInfo = edgeInfo;
        mLiftType = liftType;
        mCompleted = false;
    }

    public String getEdgeName() {
        return mEdgeName;
    }

    public Node getEndNode() {
        return mEndNode;
    }

    public Integer getEdgeInfo() {
        return mEdgeInfo;
    }

    public boolean isCompleted() {
        return mCompleted;
    }

    public String getLiftType() {
        return mLiftType;
    }

    public void setCompleted(boolean completed) {
        mCompleted = completed;
    }
}
