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

        for (Edge e : resort.getEdges()) {
            if ((e.getStart().equals(start)) && (e.getEnd().equals(end))) {
                return e;
            }
        }
        return null;
    }

    // TODO Rejoins run fragments
    public void compressPath(Resort resort) {

        // Run until run fragments all joined
        int nodesInPath = mNodePath.size();
        boolean sizeConstant = false;

        while (!sizeConstant) {
            // Check if run from i to i+1 is same as run from i to i+2
            for (int i = 0; i < nodesInPath - 3; i++) {
                Edge e1 = getEdgeFromNodes(resort, mNodePath.get(i), mNodePath.get(i + 1));
                Edge e2 = getEdgeFromNodes(resort, mNodePath.get(i+1), mNodePath.get(i + 2));

//                // Compare runs
//                if ((e1 != null) && (e2 != null)) {
//                    for (Run r1 : e1.getRuns()) {
//                        for (Run r2 : e2.getRuns()) {
//                            if (r2.getName().equals(r1.getName())) {
//                                // Compress
//
//                                // Create new edge (here??)
//                                _Edge edge = new _Edge(
//                                        r1.getName(),
//                                        mNodePath.get(i),
//                                        mNodePath.get(i+2),
//                                        e1.getWeight() + e2.getWeight());
//                                Run run = r1;
//                                run.changeEnd(mNodePath.get(i + 2));
//                                edge.addRun(run);
//                                resort.addRun(run);
//
//                                // Drop node i+1
//                                mNodePath.remove(i + 1);
//                            }
//                        }
//                    }
//                }
            }

            // Check size
            if (nodesInPath == mNodePath.size()) {
                sizeConstant = true;
                // TODO needs to loop one more time
            } else {
                nodesInPath = mNodePath.size();
            }
        }
    }
}
