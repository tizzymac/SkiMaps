package tizzy.skimapp.RouteFinding;

import android.util.Log;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import tizzy.skimapp.ResortModel.Edge;
import tizzy.skimapp.ResortModel.Node;
import tizzy.skimapp.ResortModel.Run;
import tizzy.skimapp.ResortModel.SkiLevel;

public class Graph implements Serializable {

    private LinkedList<Node> mNodes;
    private LinkedList<Edge> mEdges;

    private List<Node> mRemovedNodes;
    private List<Edge> mRemovedEdges;

    // Level restricted graph
    public Graph(LinkedList<Node> nodes, LinkedList<Edge> edges, int maxLevel) {

        mNodes = new LinkedList<>();
        mEdges = new LinkedList<>();
        mRemovedNodes = new LinkedList<>();
        mRemovedEdges = new LinkedList<>();

        // Get segments
        List<Edge> edgeSegments = new LinkedList<>();

        // Remove all edges greater than maxLevel
        for (Iterator<Edge> iterator = edges.iterator(); iterator.hasNext();) {
            Edge e = iterator.next();

            // Check if this edge is a run
            if (e instanceof Run) {

                // Only add if level within skier's ability
                if (((Run) e).getLevel().getLevelNumber() <= maxLevel) {

                    // Check if run is open
                    if (((Run) e).getStatus().isOpen()) {

                        // Add all run segments
                        edgeSegments.addAll(e.getEdgeSegments());
                    }
                }
            } else {
                // Add all lift segments
                edgeSegments.addAll(e.getEdgeSegments());
            }
        }

        this.mNodes = nodes;
        this.mEdges = (LinkedList<Edge>) edgeSegments;
    }

    public void harderRunsPrefered() {

        // Add weight to lower level runs
        for (Iterator<Edge> iterator = mEdges.iterator(); iterator.hasNext();) {
            Edge e = iterator.next();

            try {
                String level = ((Run) e).getLevel().getLevelString();

                // Multiply edge weight
                if (level.equals("Green")) {
                    e.setWeight(e.getWeight() + 32);
                }
                if (level.equals("Blue")) {
                    e.setWeight(e.getWeight() + 8);
                }
            } catch (ClassCastException exception) {
                // It's a lift
            }
        }
    }

    public void groomersOnly() {
        // Remove ungroomed runs
        for (Iterator<Edge> iterator = mEdges.iterator(); iterator.hasNext();) {
            Edge e = iterator.next();

            // Check if this edge is a run
            try {
                if (!((Run) e).getStatus().isGroomed()) {
                    // remove
                    iterator.remove(); // prevent concurrent modification by using an iterator
                }
            } catch (ClassCastException exception) {
                // It's a lift
            }
        }
    }

    public List<Node> getNodes() {
        return mNodes;
    }

    public List<Edge> getEdges() {
        return mEdges;
    }

    public Edge getEdgeBetweenNodes(Node start, Node end) {
        for (Edge e : mEdges) {
            if (e == null) {
                Log.i("Graph", "Null Edge.");
            } else {
                if ((e.getStart() == start) && (e.getEnd() == end)) {
                    return e;
                }
            }
        }
        return null;
    }

    public void temporarilyRemoveEdge(Edge edge) {
        mEdges.remove(edge);
        mRemovedEdges.add(edge);
    }

    public void temporarilyRemoveNode(Node node) {
        mNodes.remove(node);
        mRemovedNodes.add(node);
    }

    public void restoreEdges() {
        if (!mRemovedEdges.isEmpty()) {
            mEdges.addAll(mRemovedEdges);
            mRemovedEdges.clear();
        }
    }

    public void restoreNodes() {
        if (!mRemovedNodes.isEmpty()) {
            mNodes.addAll(mRemovedNodes);
            mRemovedNodes.clear();
        }
    }

}
