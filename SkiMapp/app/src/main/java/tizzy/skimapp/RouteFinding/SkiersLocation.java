package tizzy.skimapp.RouteFinding;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;

import java.math.RoundingMode;
import java.text.DecimalFormat;

import androidx.core.app.ActivityCompat;
import tizzy.skimapp.ResortModel.Edge;
import tizzy.skimapp.ResortModel.Node;
import tizzy.skimapp.ResortModel.Resort;

public class SkiersLocation {
    private static final int REQUEST_LOCATION_PERMISSIONS = 0;

    private Location mCurrentLocation;
    private Resort mResort;

    public SkiersLocation(Resort resort) {
        this.mResort = resort;
    }

    // Update location
    public void updateLocation(Activity activity, Location location) {

        if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // Request Permissions
            ActivityCompat.requestPermissions(activity,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    REQUEST_LOCATION_PERMISSIONS);
            return;
        }

            mCurrentLocation = location;
    }

    public boolean isNull() {
        return (mCurrentLocation == null);
    }

    // Returns the edge the skier is currently at
    public Edge getEdge() {
        // TODO

        // Check if user is moving to determine if they are on a lift ?
        // (Lift could be stopped...)
        // Default suggestion to go down to the bottom node of the run ?

        return null;
    }

    public boolean isAtNode(Node node) {

        // Check if current location is within a radius of 5 meters of the node
        boolean inLat = (mCurrentLocation.getLatitude() >= node.getCoords().getX() - 0.0005) &&
                (mCurrentLocation.getLatitude() <= node.getCoords().getX() + 0.0005);
        boolean inLon = (mCurrentLocation.getLongitude() >= node.getCoords().getY() - 0.0005) &&
                (mCurrentLocation.getLongitude() <= node.getCoords().getY() + 0.0005);
        return inLat && inLon;
    }

    // Return the node the skier is currently at
    // or null if not at node
    public Node getNode() {

        if (isNull()) { return null; }

        for (Node node : mResort.getNodes()) {
            if (isAtNode(node)) {
                return node;
            }
        }
        return null;
    }

    public String getExactLocation() {
        return mCurrentLocation.toString();
    }

    public String getReadableLocation() {
        DecimalFormat df = new DecimalFormat("#.###");
        df.setRoundingMode(RoundingMode.CEILING);
        String loc = "Lat: " + df.format(mCurrentLocation.getLatitude())
                + "\nLon: " +  df.format(mCurrentLocation.getLongitude());
        return loc;
    }
}
