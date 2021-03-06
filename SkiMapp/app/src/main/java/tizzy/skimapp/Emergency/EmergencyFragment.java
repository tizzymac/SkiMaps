package tizzy.skimapp.Emergency;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import tizzy.skimapp.R;
import tizzy.skimapp.ResortModel.Resort;
import tizzy.skimapp.RouteFinding.SkiersLocation;

public class EmergencyFragment extends Fragment {

    private static final String[] LOCATION_PERMISSIONS = new String[]{
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
    };
    private static final int REQUEST_LOCATION_PERMISSIONS = 0;

    private TextView mSkiersLocationTextView;
    private SkiersLocation mSkiersLocation;
    private TextView mSkiPatrolNumberTextView;
    private LocationManager locationManager;
    private Resort mResort;

    public static EmergencyFragment newInstance() {
        Bundle args = new Bundle();
        EmergencyFragment fragment = new EmergencyFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mResort = Resort.get(getActivity());
        mSkiersLocation = new SkiersLocation(mResort);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_emergency, container, false);

        locationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);

        mSkiPatrolNumberTextView = view.findViewById(R.id.patrol_number);
        mSkiPatrolNumberTextView.setText(mResort.getEmergencyNumber());

        mSkiersLocationTextView = view.findViewById(R.id.your_location);

        return view;
    }

    private LocationListener myLocationListener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {
            mSkiersLocation.updateLocation(getActivity(), location);
            mSkiersLocationTextView.setText(mSkiersLocation.getReadableLocation());
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

}
