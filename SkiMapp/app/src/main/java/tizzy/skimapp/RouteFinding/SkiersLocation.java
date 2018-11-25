package tizzy.skimapp.RouteFinding;

import android.location.Location;

import tizzy.skimapp.ResortModel.Edge;
import tizzy.skimapp.ResortModel.Node;
import tizzy.skimapp.ResortModel.Resort;

public class SkiersLocation {

    private Location mCurrentLocation;
    private Resort mResort;

    public SkiersLocation(Resort resort) {
        this.mResort = resort;
    }

    // Update location
    public void updateLocation() {
        // TODO

    }

    // Returns the edge the skier is currently at
    public Edge getEdge() {
        // TODO

        // Check if user is moving to determine if they are on a lift ?
        // (Lift could be stopped...)
        // Default suggestion to go down to the bottom node of the run ?

        return null;
    }

    // Return the node the skier is currently at
    // or null if not at node
    public Node getNode() {
        for (Node node : mResort.getNodes()) {
            if (isAtNode(node)) {
                return node;
            }
        }
        return null;
    }

    public boolean isAtNode(Node node) {
        // Check if current location is within a radius of 5 meters of the node
        boolean inLat = (mCurrentLocation.getLatitude() >= node.getCoords().getX() - 5) &&
            (mCurrentLocation.getLatitude() <= node.getCoords().getX() + 5);

        boolean inLon = (mCurrentLocation.getLongitude() >= node.getCoords().getY() - 5) &&
                (mCurrentLocation.getLongitude() <= node.getCoords().getY() + 5);

        return inLat && inLon;
    }
}
