package tizzy.skimapp.ResortModel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

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

    public String getRunsAndLifts(Resort resort, String mSkiAbility) {

        int l = mNodePath.size();
        String route = "Start: " + mNodePath.get(0) + "\n\n";

        for (int i = 0; i < l-1; i++) {
            // Get run/lift from pairs of nodes
            Edge edge = getEdgeFromNodes(resort, mNodePath.get(i), mNodePath.get(i+1));
            if (edge != null) {
                // TODO Edge can have multiple runs/lifts
                //route = route + edge.getId() + "\n";

                if (edge.getRuns() != null) {
                    // Print run level
                    for (Run r : edge.getRuns()) {
                        if (r.isWithinLevel(mSkiAbility)) {
                            route = route + r.getName() + " : " + r.getLevel() + " | ";
                        }
                    }
                    route = route + "\n";
                }

                if (edge.getLifts() != null) {
                    for (Lift lift : edge.getLifts()) {
                        route = route + lift.getName() + " | ";
                    }
                    route = route + "\n";
                }

            }
        }

        route = route + "\nEnd: " + mNodePath.get(l-1);

        return route;
    }

    public List<Edge> getRoute(Resort resort, String mSkiAbility) {
        List<Edge> edges = new ArrayList<>();

        int l = mNodePath.size();
        for (int i = 0; i < l-1; i++) {
            // Get run/lift from pairs of nodes
            Edge edge = getEdgeFromNodes(resort, mNodePath.get(i), mNodePath.get(i + 1));
            // NB edges can have multiple runs/lifts
            edges.add(edge);
        }
        return edges;
    }

    public Edge getEdgeFromNodes(Resort resort, Node start, Node end) {

        for (Edge e : resort.getEdges()) {
            if ((e.getSource().equals(start)) && (e.getDestination().equals(end))) {
                return e;
            }
        }
        return null;
    }
}
