package tizzy.skimapp.RouteFinding.KShortestPaths;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

import tizzy.skimapp.ResortModel.Node;
import tizzy.skimapp.ResortModel.Path;
import tizzy.skimapp.ResortModel.Resort;
import tizzy.skimapp.RouteFinding.Dijkstra;
import tizzy.skimapp.RouteFinding.Graph;

public class Yen {

    Resort mResort;

    public Yen(Resort resort) {
        this.mResort = resort;
    }

    public List<Path> YenKSP(Graph graph, Node source, Node sink, int K) {

        //Path[] shortestPaths = new Path[K];
        List<Path> shortestPaths = new ArrayList<>();

        // Determine the shortest path from the source to the sink.
        Dijkstra dijkstra = new Dijkstra(graph);
        dijkstra.execute(source);
        shortestPaths.add(dijkstra.getPath(sink));

        // Check if shortestPaths[0] is null
        if (shortestPaths.get(0) == null) {
            return shortestPaths;
        }

        // Initialize the set to store the potential kth shortest path.
        PriorityQueue<Path> potentialPaths = new PriorityQueue<>();

        for (int k = 1; k <= K; k++) {
            // The spur node ranges from the first node to the next to last node in the previous k-shortest path.
            int d = shortestPaths.get(k - 1).getDistance() - 1; // Changed from 2 to 1
            for (int i = 0; i <= d; i++) {

                // Spur node is retrieved from the previous k-shortest path, k − 1.
                Node spurNode = shortestPaths.get(k - 1).getNode(i);
                // The sequence of nodes from the source to the spur node of the previous k-shortest path.
                Path rootPath = shortestPaths.get(k - 1).getNodes(0, i);

                for (Path p : shortestPaths) {
                    if (rootPath == p.getNodes(0, i)) {
                        // Remove the links that are part of the previous shortest paths
                        // which share the same root path.
                        graph.temporarilyRemoveEdge(p.getEdgeFromNodes(mResort, p.getNode(i), p.getNode(i + 1)));
                    }
                }

                for (Node rootPathNode : rootPath.getNodePath()) {
                    if (rootPathNode != spurNode) {
                        //remove rootPathNode from graph;
                        graph.temporarilyRemoveNode(rootPathNode);
                    }
                }

                // Do I need to do this on a different (background / worker) thread?
                // Calculate the spur path from the spur node to the sink.
                Dijkstra dijkstraSpur = new Dijkstra(graph);
                dijkstraSpur.execute(spurNode);
                Path spurPath = dijkstraSpur.getPath(sink);

                // Entire path is made up of the root path and spur path.
                Path totalPath = new Path(rootPath.getNodePath());
                // check if spur path is null
                if (spurPath == null) {
                    break;
                }
                totalPath.joinPath(spurPath);

                // Add the potential k-shortest path to the heap.
                potentialPaths.add(totalPath);

                // Add back the edges and nodes that were removed from the graph.
                graph.restoreEdges();
                graph.restoreNodes();
            }

            if (potentialPaths.isEmpty()) {
                // This handles the case of there being no spur paths, or no spur paths left.
                // This could happen if the spur paths have already been exhausted (added to shortestPaths),
                // or there are no spur paths at all - such as when both the source and sink vertices
                // lie along a "dead end".
                break;
            }

            // Sort the potential k-shortest paths by cost.
            // potentialPaths.sort();

            // Add the lowest cost path becomes the k-shortest path.
            shortestPaths.add(potentialPaths.poll());
        }
        return shortestPaths;
    }

}
