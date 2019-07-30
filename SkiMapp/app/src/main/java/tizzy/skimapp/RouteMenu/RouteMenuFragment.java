package tizzy.skimapp.RouteMenu;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import tizzy.skimapp.ResortModel.Resort;

public class RouteMenuFragment extends Fragment {

    private static final String ARG_RESORT = "resort";
    private static final String ARG_SKI_ABILITY = "ski_ability";

    private static final String[] LOCATION_PERMISSIONS = new String[]{
            android.Manifest.permission.ACCESS_FINE_LOCATION,
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
    };
    private static final int REQUEST_LOCATION_PERMISSIONS = 0;

    public static RouteMenuFragment newInstance(Resort resort, String skiAbility) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_RESORT, resort);
        args.putString(ARG_SKI_ABILITY, skiAbility);
        RouteMenuFragment fragment = new RouteMenuFragment();
        fragment.setArguments(args);

        return fragment;
    }
}
