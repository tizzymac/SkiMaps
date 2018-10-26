package tizzy.skimapp.RouteFinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import tizzy.skimapp.ResortModel.Edge;
import tizzy.skimapp.ResortModel.Node;
import tizzy.skimapp.ResortModel.Run;

public class Graph implements Serializable {

    private List<Node> mNodes;
    private List<Edge> mEdges;

    public Graph(List<Node> nodes, List<Edge> edges) {
        this.mNodes = nodes;
        this.mEdges = edges;
    }

    // IDEA : Level restricted graph
    public Graph(List<Node> nodes, List<Edge> edges, String maxLevel) {

        if (maxLevel.equals("Green")) {
            // Remove all Blacks and Blues from edges
            for (Edge e : edges) {
                Set<Run> allRuns = e.getRuns();
                if (allRuns != null) {
                    for (Run r : allRuns) {
                        if (!r.getLevel().equals("Green")) {
                            e.getRuns().remove(r);
                        }
                    }
                    if ((e.getRuns().isEmpty()) && (e.getLifts().isEmpty())) {
                        // Remove the edge all together
                        edges.remove(e);
                    }
                }
            }
        }

        if (maxLevel.equals("Blue")) {
            // Remove all Black runs
            for (Edge e : edges) {
                Set<Run> allRuns = e.getRuns();
                if (allRuns != null) {
                    for (Run r : allRuns) {
                        if (r.getLevel().equals("Black")) {
                            e.getRuns().remove(r);
                        }
                    }
                    if ((e.getRuns().isEmpty()) && (e.getLifts().isEmpty())) {
                        // Remove the edge all together
                        edges.remove(e);
                    }
                }
            }
        }

        this.mNodes = nodes;
        this.mEdges = edges;
    }

    public List<Node> getNodes() {
        return mNodes;
    }

    public List<Edge> getEdges() {
        return mEdges;
    }

}
