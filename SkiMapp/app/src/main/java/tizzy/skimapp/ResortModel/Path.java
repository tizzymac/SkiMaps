package tizzy.skimapp.ResortModel;

import java.util.LinkedList;

public class Path {

    private LinkedList<Node> mNodePath;

    public Path() {

        mNodePath = new LinkedList<>();
    }

    public Path(LinkedList<Node> nodePath) {
        mNodePath = nodePath;
    }

    public LinkedList<Node> getNodePath() {
        return mNodePath;
    }

    public int getDistance() {
        if (mNodePath == null) {
            return 0;
        }

        // Temporary
        return mNodePath.size();
    }

    public Node getNode(int i) {
        return mNodePath.get(i);
    }

    public Edge getEdgeFromNodes(Resort resort, Node start, Node end) {
        // Do I need to adapt this for run segments too?

        for (Edge e : resort.getEdges()) {
            if ((e.getStart().equals(start)) && (e.getEnd().equals(end))) {
                return e;
            }
        }

        // If no edge has been found, it might be a combination of run segments
        // so check if the path from the start to the end would be a path of segments
        // all with the same name (not v efficient)

        return null;
    }
}
