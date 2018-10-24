package tizzy.skimapp.RouteFinding;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import tizzy.skimapp.ResortModel.Edge;
import tizzy.skimapp.ResortModel.Node;

public class RouteTest {

    private static List<Node> nodes;
    private static List<Edge> edges;

    private static void setUp() {
        // Create nodes
        Node nodeA = new Node("A", null);
        Node nodeB = new Node("B", null);
        Node nodeC = new Node("C", null);
        Node nodeD = new Node("D", null);

        nodes.add(nodeA);
        nodes.add(nodeB);
        nodes.add(nodeC);
        nodes.add(nodeD);

        // Create edges
        // From A
        Edge edge1 = new Edge("1", nodeA, nodeB, 3);
        edges.add(edge1);
        // From B
        Edge edge2 = new Edge("2", nodeB, nodeA, 3);
        edges.add(edge2);
        Edge edge3 = new Edge("3", nodeB, nodeC, 2);
        edges.add(edge3);
        // From C
        Edge edge4 = new Edge("4", nodeC, nodeA, 1);
        edges.add(edge4);
        Edge edge5 = new Edge("5", nodeC, nodeD, 1);
        edges.add(edge5);
        // From D
        Edge edge6 = new Edge("6", nodeD, nodeC, 1);
        edges.add(edge6);

    }

//    public static void main(String[] args) {
//
//        nodes = new ArrayList<Node>();
//        edges = new ArrayList<Edge>();
//        setUp();
//
//        Graph graph = new Graph(nodes, edges);
//        Dijkstra dijkstra = new Dijkstra(graph);
//
//        dijkstra.execute(nodes.get(3));                         // From D
//        LinkedList<Node> path = dijkstra.getPath(nodes.get(1)); // To B
//
//        for (Node n : path) {
//            System.out.println(n);
//        }
//
//    }

}
