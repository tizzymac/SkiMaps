package tizzy.skimapp.RouteFinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import tizzy.skimapp.ResortModel.Edge;
import tizzy.skimapp.ResortModel.Node;
import tizzy.skimapp.ResortModel.Run;

public class Graph implements Serializable {

    private List<Node> mNodes;
    private List<Edge> mEdges;

    public Graph(List<Node> nodes, List<Edge> edges) {
        this.mNodes = nodes;
        // Need to split into segments
        this.mEdges = edges;
    }

    // IDEA : Level restricted graph
    public Graph(List<Node> nodes, List<Edge> edges, String maxLevel) {

        // Get segments
        List<Edge> edgeSegments = new ArrayList<>();

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
        this.mEdges = edgeSegments;
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

}
