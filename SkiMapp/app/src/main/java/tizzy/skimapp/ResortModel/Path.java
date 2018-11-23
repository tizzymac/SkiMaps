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
        // Temporary
        return mNodePath.size();
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

    private Edge getEdgeFromNodes(Resort resort, Node start, Node end) {

        for (Edge e : resort.getEdges()) {
            if ((e.getSource().equals(start)) && (e.getDestination().equals(end))) {
                return e;
            }
        }
        return null;
    }
}
