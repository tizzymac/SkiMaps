package tizzy.skimapp.RouteFinding.SkiRoute;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Optional;

import tizzy.skimapp.ResortModel.Edge;
import tizzy.skimapp.ResortModel.Lift;
import tizzy.skimapp.ResortModel.Node;
import tizzy.skimapp.ResortModel.Path;
import tizzy.skimapp.ResortModel.Run;
import tizzy.skimapp.RouteFinding.Graph;

public class SkiRoute implements Serializable {

//    private LinkedList<String> mEdgeNamePath;
//    private LinkedList<Node> mEndNodes;
//    private LinkedList<Integer> mEdgeInfo;
//    private boolean[] mCompleted;
    private LinkedList<SkiRouteEdge> mEdgePath;

    // Create SkiRoute from Path
    public SkiRoute(Path path, Graph resortGraph) {
        mEdgePath = new LinkedList<>();
//        mEndNodes = new LinkedList<>();
//        mEdgeInfo = new LinkedList<>();

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
                    mEdgePath.add(new SkiRouteEdge(
                            edgeFromStart.getName(),
                            path.getNode(i),
                            infoEncoding(edgeFromStart),
                            getLiftType(edgeFromStart)));
//                    mEndNodes.add(path.getNode(i));
//                    mEdgeInfo.add(infoEncoding(edgeFromStart));

                    // set startNode to node at i
                    startNode = path.getNode(i);

                    break; // exit the for loop
                }

                // check if at final node
                if (j == path.getNodePath().size()-1) {
                    mEdgePath.add(new SkiRouteEdge(
                            edgeFromStart.getName(),
                            path.getNode(j),
                            infoEncoding(edgeFromStart),
                            getLiftType(edgeFromStart)
                    ));
//                    mEndNodes.add(path.getNode(j));
//                    mEdgeInfo.add(infoEncoding(edgeFromStart));
                    startNode = path.getNode(j);
                }
            }

            if (startNode == penultimateNodeInPath) {
                Edge finalEdge = resortGraph.getEdgeBetweenNodes(penultimateNodeInPath, finalNodeInPath);
                mEdgePath.add( new SkiRouteEdge(
                        finalEdge.getName(),
                        finalNodeInPath,
                        infoEncoding(finalEdge),
                        getLiftType(finalEdge)
                ));
//                mEndNodes.add(finalNodeInPath);
//                mEdgeInfo.add(infoEncoding(finalEdge));

                // exit while loop
                startNode = finalNodeInPath;
            }

//            mCompleted = new boolean[mEdgeNamePath.size()];
        }

    }

    public String getEdgeName(int index) {
        return mEdgePath.get(index).getEdgeName();
    }

    public int getNumberOfEdges() {
        return mEdgePath.size();
    }

    public Node getEndNodeAt(int index) {
        return mEdgePath.get(index).getEndNode();
    }

    public void setCompleted(int i) {
        mEdgePath.get(i).setCompleted(true);
    }

    public Boolean isSegmentCompleted(int i) {
        return mEdgePath.get(i).isCompleted();
    }

    public int length() {
        return mEdgePath.size();
    }

    public int getInfoEncoding(int index) {
        return mEdgePath.get(index).getEdgeInfo();
    }

    public String getLiftType(int index) { return mEdgePath.get(index).getLiftType(); }

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

    public String getLiftType(Edge e) {
        try {
            return ((Lift) e).getLiftType();
        } catch (ClassCastException exception) {
            return "";
        }
    }
}
