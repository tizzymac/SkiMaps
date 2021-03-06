package tizzy.skimapp.Loading;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import tizzy.skimapp.Home.HomeActivity;
import tizzy.skimapp.Home.PagerActivity;
import tizzy.skimapp.R;
import tizzy.skimapp.ResortModel.Resort;

public class LoadFragment extends Fragment {

    public static LoadFragment newInstance() {
        Bundle args = new Bundle();
        LoadFragment fragment = new LoadFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_load, container, false);

        new LoadResort().execute();

        return view;
    }

    private class LoadResort extends AsyncTask<Void, Void, Resort> {


        @Override
        protected Resort doInBackground(Void... voids) {
            // Create the resort
            Resort r = Resort.get(getActivity());
            while(r.getRegion() == null) {
                // spin
            }
            return r;
        }

        @Override
        protected void onPostExecute(Resort r) {
            // open home fragment
            //Intent intent = HomeActivity.newIntent(getActivity());
            Intent intent = PagerActivity.newIntent(getActivity());
            startActivity(intent);
        }
    }
}
