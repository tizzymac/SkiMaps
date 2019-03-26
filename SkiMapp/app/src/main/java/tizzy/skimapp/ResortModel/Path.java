package tizzy.skimapp.ResortModel;

import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.LinkedList;

public class Path implements Comparable<Path>, Serializable {

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

    public Path getNodes(int start, int end) {
        LinkedList<Node> newPath = new LinkedList<>();
        for (int i = start; i <= end; i++) {
            newPath.add(getNode(i));
        }
        return new Path(newPath);
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

    public void joinPath(Path secondPath) {
        // Remove duplicate nodes
        if (secondPath.getNode(0) == mNodePath.get(0)) {
            mNodePath.removeLast();
            mNodePath.addAll(secondPath.getNodePath());
        } else {
            mNodePath.addAll(secondPath.getNodePath());
        }
    }

    public boolean equals(Path secondPath) {
        if (secondPath.getNodePath().size() != mNodePath.size()) {
            return false;
        }

        for (int i = 0; i < mNodePath.size(); i++) {
            if (!mNodePath.get(i).getId().equals(secondPath.getNodePath().get(i).getId())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public int compareTo(@NonNull Path o) {
        return Integer.compare(this.getDistance(), o.getDistance()); // ??
    }
}
