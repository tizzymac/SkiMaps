package tizzy.skimapp.RouteFinding;

import java.util.LinkedList;

import tizzy.skimapp.ResortModel.Edge;
import tizzy.skimapp.ResortModel.Facility;
import tizzy.skimapp.ResortModel.Node;
import tizzy.skimapp.ResortModel.Path;
import tizzy.skimapp.ResortModel.Resort;

public class FacilityFinder {

    private Resort mResort;
    private Graph mGraph;

    public FacilityFinder(Resort resort, String skiAbility) {
        this.mResort = resort;
        this.mGraph = new Graph((LinkedList<Node>) mResort.getNodes(), (LinkedList<Edge>) mResort.getEdges(), skiAbility);
    }

    // Find nearest bathroom
    public Path pathToNearestFacility(Node start, String type) {
        
        int shortest = -1;
        Path shortestPath = new Path();

        for (Facility facility : mResort.getFacilities()) {
            if (facility.hasType(type)) {
                // Check if there are facilities at start node
                if (facility.getLocation() == start) {
                    LinkedList<Node> pathList = new LinkedList<>();
                    pathList.add(start);
                    return new Path(pathList);
                }

                // Calculate path from start node to that facility
                Dijkstra dijkstra = new Dijkstra(mGraph);
                dijkstra.execute(start);
                Path path = dijkstra.getPath(facility.getLocation());

                if (path != null) {
                    // Check it's distance
                    if ((shortest > path.getDistance()) || (shortest == -1)) {
                        shortest = path.getDistance();
                        shortestPath = path;
                    }
                }
            }
        }
        
        return shortestPath;
    }
}
