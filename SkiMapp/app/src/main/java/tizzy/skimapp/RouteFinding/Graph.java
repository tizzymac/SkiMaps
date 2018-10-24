package tizzy.skimapp.RouteFinding;

import java.util.List;

import tizzy.skimapp.ResortModel.Edge;
import tizzy.skimapp.ResortModel.Node;

public class Graph {

    private List<Node> nodes;
    private List<Edge> edges;

    public Graph(List<Node> nodes, List<Edge> edges) {
        this.nodes = nodes;
        this.edges = edges;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public List<Edge> getEdges() {
        return edges;
    }
}
