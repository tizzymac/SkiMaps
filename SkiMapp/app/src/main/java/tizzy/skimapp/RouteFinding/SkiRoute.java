package tizzy.skimapp.RouteFinding;

import java.io.Serializable;
import java.util.LinkedList;

import tizzy.skimapp.ResortModel.Edge;
import tizzy.skimapp.ResortModel.Lift;
import tizzy.skimapp.ResortModel.Node;
import tizzy.skimapp.ResortModel.Path;
import tizzy.skimapp.ResortModel.Run;

public class SkiRoute implements Serializable {

    private LinkedList<String> mEdgeNamePath;
    private LinkedList<Node> mEndNodes;
    private LinkedList<Integer> mEdgeInfo;
    private boolean[] mCompleted;

    // Create SkiRoute from Path
    public SkiRoute(Path path, Graph resortGraph) {
        mEdgeNamePath = new LinkedList<>();
        mEndNodes = new LinkedList<>();
        mEdgeInfo = new LinkedList<>();

        // Rejoin adjacent segments from same run

        // For each node in the path, find the farthest node that ends a single edge
        // Then add that edge to the ski route and set the end node as the start node
        // Repeat until start node is the end node of the path

        Node startNode = path.getNode(0);
        Node penultimateNodeInPath = path.getNode(path.getNodePath().size()-2);
        Node finalNodeInPath = path.getNode(path.getNodePath().size()-1);

        while (startNode != finalNodeInPath) {
            int currStartIndex = path.getNodePath().indexOf(startNode);

            Edge edgeFromStart = resortGraph.getEdgeBetweenNodes(startNode, path.getNode(currStartIndex +1 ));
            // This is the name of the edge between startNode and its adjacent node

            // Compare the name of this first edge to the name of its adjacent edges
            for (int i = currStartIndex+1; i < path.getNodePath().size()-1; i++) {
                int j = i+1;
                String nextEdge = resortGraph.getEdgeBetweenNodes(path.getNode(i), path.getNode(j)).getName();

                if (!nextEdge.equals(edgeFromStart.getName())) {
                    // set end of current edge as i
                    // add edge to SkiRoute
                    mEdgeNamePath.add(edgeFromStart.getName());
                    mEndNodes.add(path.getNode(i));
                    mEdgeInfo.add(infoEncoding(edgeFromStart));

                    // set startNode to node at i
                    startNode = path.getNode(i);

                    break; // exit the for loop
                }

                // check if at final node
                if (j == path.getNodePath().size()-1) {
                    mEdgeNamePath.add(edgeFromStart.getName());
                    mEndNodes.add(path.getNode(j));
                    mEdgeInfo.add(infoEncoding(edgeFromStart));
                    startNode = path.getNode(j);
                }
            }

            if (startNode == penultimateNodeInPath) {
                Edge finalEdge = resortGraph.getEdgeBetweenNodes(penultimateNodeInPath, finalNodeInPath);
                mEdgeNamePath.add(finalEdge.getName());
                mEndNodes.add(finalNodeInPath);
                mEdgeInfo.add(infoEncoding(finalEdge));

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

    public int getInfoEncoding(int index) {
        return mEdgeInfo.get(index);
    }

    public int infoEncoding(Edge edge) {
        try {
            String level = ((Run) edge).getLevel().getLevelString();

            switch (level) {
                case "Green" : return 1;
                case "Blue" : return 2;
                case "Black" : return 3;
            }

        } catch (ClassCastException exception) {
            // It's a lift
            return 0;
        }

        return -1;
    }
}
