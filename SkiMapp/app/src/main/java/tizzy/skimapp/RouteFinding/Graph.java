package tizzy.skimapp.RouteFinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
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
            for (Iterator<Edge> iterator = edges.iterator(); iterator.hasNext();) {
                Edge e = iterator.next();
                Set<Run> allRuns = e.getRuns();
                if (allRuns != null) {
                    for (Iterator<Run> runIterator = allRuns.iterator(); runIterator.hasNext();) {
                        Run r = runIterator.next();
                        if (!r.getLevel().equals("Green")) {
                            runIterator.remove();
                        }
                    }
                    if ((e.getRuns().isEmpty()) && (e.getLifts().isEmpty())) {
                        // Remove the edge all together
                        iterator.remove();
                    }
                }
            }
        }

        if (maxLevel.equals("Blue")) {
            // Remove all Black runs
            for (Iterator<Edge> iterator = edges.iterator(); iterator.hasNext();) {
                //for (Edge e : edges) {
                Edge e = iterator.next();
                Set<Run> allRuns = e.getRuns();
                if (allRuns != null) {
                    for (Iterator<Run> runIterator = allRuns.iterator(); runIterator.hasNext();) {
                        Run r = runIterator.next();
                        if (r.getLevel().equals("Black")) {
                            runIterator.remove();
                        }
                    }
                    if ((e.getRuns().isEmpty()) && (e.getLifts().isEmpty())) {
                        // Remove the edge all together
                        iterator.remove();
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
