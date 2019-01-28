package tizzy.skimapp.RouteFinding;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import tizzy.skimapp.ResortModel.Edge;
import tizzy.skimapp.ResortModel.Lift;
import tizzy.skimapp.ResortModel.Node;
import tizzy.skimapp.ResortModel.Run;

public class Graph implements Serializable {

    private LinkedList<Node> mNodes;
    private LinkedList<Edge> mEdges;

    private List<Node> mRemovedNodes;
    private List<Edge> mRemovedEdges;

    public Graph(LinkedList<Node> nodes, LinkedList<Edge> edges) {

        mNodes = new LinkedList<>();
        mEdges = new LinkedList<>();
        mRemovedNodes = new LinkedList<>();
        mRemovedEdges = new LinkedList<>();

        this.mNodes = nodes;
        // Need to split into segments
        this.mEdges = edges;
    }

    // IDEA : Level restricted graph
    public Graph(LinkedList<Node> nodes, LinkedList<Edge> edges, String maxLevel) {

        mNodes = new LinkedList<>();
        mEdges = new LinkedList<>();
        mRemovedNodes = new LinkedList<>();
        mRemovedEdges = new LinkedList<>();

        // Get segments
        List<Edge> edgeSegments = new LinkedList<>();

        if (maxLevel.equals("Green")) {
            // Remove all Blacks and Blues from edges
            for (Iterator<Edge> iterator = edges.iterator(); iterator.hasNext();) {
                Edge e = iterator.next();

                // Check if this edge is a run
                if (e instanceof Run) {
                    // Only add if level is green
                    if (((Run) e).getLevel().equals("Green")) {
                        // Add all run segments
                        edgeSegments.addAll(e.getEdgeSegments());
                    }
                } else {
                    // Add all lift segments
                    edgeSegments.addAll(e.getEdgeSegments());
                }
            }
        }

        if (maxLevel.equals("Blue")) {
            // Remove all Black runs
            for (Iterator<Edge> iterator = edges.iterator(); iterator.hasNext();) {
                Edge e = iterator.next();

                // Check if this edge is a run
                if (e instanceof Run) {
                    // Only add if level is green or blue
                    if ((((Run) e).getLevel().equals("Green") || (((Run) e).getLevel().equals("Blue")))) {
                        // Add all run segments
                        edgeSegments.addAll(e.getEdgeSegments());
                    }
                } else {
                    // Add all lift segments
                    edgeSegments.addAll(e.getEdgeSegments());
                }
            }
        }

        if (maxLevel.equals("Black")) {
            // Add all edges
            for (Iterator<Edge> iterator = edges.iterator(); iterator.hasNext();) {
                Edge e = iterator.next();
                edgeSegments.addAll(e.getEdgeSegments());
            }
        }

        this.mNodes = nodes;
        this.mEdges = (LinkedList<Edge>) edgeSegments;
    }

    // Graph where lifts have weight multiplied by factor i
    public Graph(LinkedList<Node> nodes, LinkedList<Edge> edges, int i) {

        mNodes = new LinkedList<>();
        mEdges = new LinkedList<>();
        mRemovedNodes = new LinkedList<>();
        mRemovedEdges = new LinkedList<>();

        // Get segments
        List<Edge> edgeSegments = new LinkedList<>();

        for (Iterator<Edge> iterator = edges.iterator(); iterator.hasNext();) {
            Edge e = iterator.next();

            // Check if this edge is a lift
            if (e instanceof Lift) {
                // Multiply edge weight by i
                Lift heavyEdge = (Lift) e;
                heavyEdge.increaseWeight(i);
                edgeSegments.addAll(heavyEdge.getEdgeSegments());

            } else {
                // Add all run segments
                edgeSegments.addAll(e.getEdgeSegments());
            }
        }

        this.mNodes = nodes;
        this.mEdges = (LinkedList<Edge>) edgeSegments;
    }

    public List<Node> getNodes() {
        return mNodes;
    }

    public List<Edge> getEdges() {
        return mEdges;
    }

    public Edge getEdgeBetweenNodes(Node start, Node end) {
        for (Edge e : mEdges) {
            if ((e.getStart() == start) && (e.getEnd() == end)) {
                return e;
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
