package tizzy.skimapp.RouteFinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import tizzy.skimapp.ResortModel.Edge;
import tizzy.skimapp.ResortModel.Node;
import tizzy.skimapp.ResortModel.Path;

public class SkiRoute implements Serializable {

    private LinkedList<String> mEdgeNamePath;
    private LinkedList<Node> mEndNodes;
    private boolean[] mCompleted;

    // Create SkiRoute from Path
    public SkiRoute(Path path, Graph resortGraph) {
        mEdgeNamePath = new LinkedList<>();
        mEndNodes = new LinkedList<>();

        // Rejoin adjacent segments from same run

        // For each node in the path, find the farthest node that ends a single edge
        // Then add that edge to the ski route and set the end node as the start node
        // Repeat until start node is the end node of the path

        Node startNode = path.getNode(0);
        Node penultimateNodeInPath = path.getNode(path.getNodePath().size()-2);
        Node finalNodeInPath = path.getNode(path.getNodePath().size()-1);

        while (startNode != finalNodeInPath) {
            int currStartIndex = path.getNodePath().indexOf(startNode);

            String edgeFromStart = resortGraph.getEdgeBetweenNodes(startNode, path.getNode(currStartIndex +1 )).getName();
            // This is the name of the edge between startNode and its adjacent node

            // Compare the name of this first edge to the name of its adjacent edges
            for (int i = currStartIndex+1; i < path.getNodePath().size()-1; i++) {
                int j = i+1;
                String nextEdge = resortGraph.getEdgeBetweenNodes(path.getNode(i), path.getNode(j)).getName();

                if (!nextEdge.equals(edgeFromStart)) {
                    // set end of current edge as i
                    // add edge to SkiRoute
                    mEdgeNamePath.add(edgeFromStart);
                    mEndNodes.add(path.getNode(i));

                    // set startNode to node at i
                    startNode = path.getNode(i);

                    break; // exit the for loop
                }

                // check if at final node
                if (j == path.getNodePath().size()-1) {
                    mEdgeNamePath.add(edgeFromStart);
                    mEndNodes.add(path.getNode(j));
                    startNode = path.getNode(j);
                }
            }

            if (startNode == penultimateNodeInPath) {
                String finalEdge = resortGraph.getEdgeBetweenNodes(penultimateNodeInPath, finalNodeInPath).getName();
                mEdgeNamePath.add(finalEdge);
                mEndNodes.add(finalNodeInPath);

                // exit while loop
                startNode = finalNodeInPath;
            }

            mCompleted = new boolean[mEdgeNamePath.size()];
        }

    }

    public String getEdgeName(int index) {
        return mEdgeNamePath.get(index);
    }

    public int getNumberOfEdges() {
        return mEdgeNamePath.size();
    }

    public Node getEndNodeAt(int index) {
        return mEndNodes.get(index);
    }

    public void setCompleted(int i) {
        mCompleted[i] = true;
    }

    public Boolean isSegmentCompleted(int i) {
        return mCompleted[i];
    }

    public int length() {
        return mEdgeNamePath.size();
    }
}
