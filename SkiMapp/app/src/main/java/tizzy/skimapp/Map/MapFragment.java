package tizzy.skimapp.Map;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.osmdroid.config.Configuration;
import org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import org.osmdroid.util.GeoPoint;
import org.osmdroid.views.MapController;
import org.osmdroid.views.MapView;
import org.osmdroid.views.overlay.OverlayItem;
import org.osmdroid.views.overlay.ScaleBarOverlay;

import java.util.ArrayList;

import tizzy.skimapp.R;
import tizzy.skimapp.ResortModel.Coords;
import tizzy.skimapp.ResortModel.Node;

public class MapFragment extends Fragment {

    private MapView mMapView;
    private MapController mMapController;
    LocationManager locationManager;

    private static final String[] LOCATION_PERMISSIONS = new String[]{
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
    };
    private static final int REQUEST_LOCATION_PERMISSIONS = 0;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_map, container, false);

        Context ctx = getActivity().getApplicationContext();
        Configuration.getInstance().load(ctx, PreferenceManager.getDefaultSharedPreferences(ctx));

        mMapView = view.findViewById(R.id.mapview);
        mMapView.setTileSource(TileSourceFactory.MAPNIK);
//        mMapView.setTileSource(new OnlineTileSourceBase("USGS Topo", 0,
//                18, 256, "",
//                new String[]{"http://basemap.nationalmap.gov/ArcGIS/rest/services/USGSTopo/MapServer/tile/"}) {
//            @Override
//            public String getTileURLString(long pMapTileIndex) {
//                return getBaseUrl()
//                        + MapTileIndex.getZoom(pMapTileIndex)
//                        + "/" + MapTileIndex.getY(pMapTileIndex)
//                        + "/" + MapTileIndex.getX(pMapTileIndex)
//                        + mImageFilenameEnding;
//            }
//        });

        mMapView.setBuiltInZoomControls(true);
        mMapController = (MapController) mMapView.getController();
        mMapController.setZoom(18);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        // for demo, getLastKnownLocation from GPS only, not from NETWORK
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission is not granted
            System.out.println("Permission not granted");
            requestPermissions(LOCATION_PERMISSIONS, REQUEST_LOCATION_PERMISSIONS);
        } else {
            // Permission has already been granted
            Location lastLocation = locationManager.getLastKnownLocation(
                    LocationManager.GPS_PROVIDER);
            if (lastLocation != null) {
                updateLoc(lastLocation);
                String loc = lastLocation.toString();
                Toast.makeText(getActivity(), loc, Toast.LENGTH_LONG);
            }
        }

        // Add Scale Bar
        ScaleBarOverlay myScaleBarOverlay = new ScaleBarOverlay(mMapView);
        mMapView.getOverlays().add(myScaleBarOverlay);

        return view;
    }

    //--- Stolen from SkiersLocation
    public boolean isAtNode(Location mCurrentLocation, Node node) {
        // Check if current location is within a radius of 5 meters of the node
        boolean inLat = (mCurrentLocation.getLatitude() >= node.getCoords().getX() - 0.005) &&
                (mCurrentLocation.getLatitude() <= node.getCoords().getX() + 0.005);
        boolean inLon = (mCurrentLocation.getLongitude() >= node.getCoords().getY() - 0.005) &&
                (mCurrentLocation.getLongitude() <= node.getCoords().getY() + 0.005);
        return inLat && inLon;
    }
    //---

    private LocationListener myLocationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            updateLoc(location);

            String loc = location.toString();
            Toast.makeText(getActivity(), loc, Toast.LENGTH_LONG);

            // Check if gets to Sam's Knob
            Node knob = new Node("Knob", new Coords(39.187776, -106.972486, 2.0));
            if (isAtNode(location, knob)) {
                Toast.makeText(getActivity(), "AT THE KNOB!", Toast.LENGTH_LONG);
            }
        }

        @Override
        public void onProviderDisabled(String provider) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }
    };

    @Override
    public void onResume() {
        super.onResume();
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, myLocationListener);
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, myLocationListener);
    }

    @Override
    public void onPause() {
        super.onPause();
        locationManager.removeUpdates(myLocationListener);
    }

    private void updateLoc(Location loc){
        GeoPoint locGeoPoint = new GeoPoint(loc.getLatitude(), loc.getLongitude());
        mMapController.setCenter(locGeoPoint);

        mMapView.invalidate();
    }
}

