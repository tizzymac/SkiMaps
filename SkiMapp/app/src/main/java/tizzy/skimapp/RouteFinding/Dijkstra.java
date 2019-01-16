package tizzy.skimapp.RouteFinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import tizzy.skimapp.ResortModel.Edge;
import tizzy.skimapp.ResortModel._Edge;
import tizzy.skimapp.ResortModel.Node;
import tizzy.skimapp.ResortModel.Path;
import tizzy.skimapp.ResortModel.Resort;
import tizzy.skimapp.ResortModel.Run;

public class Dijkstra {

    private final List<Node> nodes;
    private final List<Edge> edges;
    private Set<Node> settledNodes;
    private Set<Node> unSettledNodes;
    private Map<Node, Node> predecessors;
    private Map<Node, Integer> distance;

    public Dijkstra(Graph graph) {
        // create a copy of the array so that we can operate on this array
        this.nodes = new ArrayList<>(graph.getNodes());
        this.edges = new ArrayList<>(graph.getEdges());
    }

    public void execute(Node source) {
        settledNodes = new HashSet<>();
        unSettledNodes = new HashSet<>();
        distance = new HashMap<>();
        predecessors = new HashMap<>();
        distance.put(source, 0);
        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
            Node node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(Node node) {
        List<Node> adjacentNodes = getNeighbors(node);
        for (Node target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node)
                    + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node)
                        + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }

    }

    private int getDistance(Node node, Node target) {
        for (Edge edge : edges) {
            if (edge.getStart().equals(node)
                    && edge.getEnd().equals(target)) {
                return edge.getWeight();
            }
        }
        throw new RuntimeException("Should not happen");
    }

    private List<Node> getNeighbors(Node node) {
        List<Node> neighbors = new ArrayList<Node>();
        for (Edge edge : edges) {
            if (edge.getStart().equals(node)
                    && !isSettled(edge.getEnd())) {
                neighbors.add(edge.getEnd());
            }
        }
        return neighbors;
    }

    private Node getMinimum(Set<Node> nodes) {
        Node minimum = null;
        for (Node Node :nodes) {
            if (minimum == null) {
                minimum = Node;
            } else {
                if (getShortestDistance(Node) < getShortestDistance(minimum)) {
                    minimum = Node;
                }
            }
        }
        return minimum;
    }

    private boolean isSettled(Node Node) {
        return settledNodes.contains(Node);
    }

    private int getShortestDistance(Node destination) {
        Integer d = distance.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }

    /*
     * This method returns the path from the source to the selected target and
     * NULL if no path exists
     */
    public Path getPath(Node target) {
        LinkedList<Node> path = new LinkedList<Node>();
        Node step = target;

        // check if a path exists
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        // Put it into the correct order
        Collections.reverse(path);
        // Compress Run fragments into single run

        return new Path(path);
    }

    // TODO Compress Run Fragments
    private Path compressPath(Path path, Resort resort){
        LinkedList<Node> nodePath = path.getNodePath();
        for (int i=0; i < nodePath.size()-2; i++) {
            // Check if neighbor is from the same run
            Edge e1 = path.getEdgeFromNodes(resort, path.getNodePath().get(i), path.getNodePath().get(i+1));
            Edge e2 = path.getEdgeFromNodes(resort, path.getNodePath().get(i+1), path.getNodePath().get(i+2));

//            for (Run run1 : e1.getRuns()) {
//                for (Run run2 : e2.getRuns()) {
//                    if (run1.equals(run2)) {
//                        // Compress by removing the middle node
//                        nodePath.remove(i+1);
//                    }
//                }
//            }
        }
        return new Path(nodePath);
    }
}
