package tizzy.skimapp;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import androidx.test.core.app.ApplicationProvider;
import tizzy.skimapp.ResortModel.Facility;
import tizzy.skimapp.ResortModel.Node;
import tizzy.skimapp.ResortModel.Resort;
import tizzy.skimapp.ResortModel.SkiLevel;
import tizzy.skimapp.RouteFinding.FacilityFinder;

import static org.junit.Assert.assertEquals;

@RunWith(RobolectricTestRunner.class)
public class FacilityFinderTest {

    // Create the resort
    private Context context = ApplicationProvider.getApplicationContext();
    private Resort resort = Resort.get(context); // buttermilk

    // Create facility finder
    private FacilityFinder facilityFinder = new FacilityFinder(resort, new SkiLevel("Black", "USA"));

    @Test
    public void already_here() {

        Node n1 = resort.getNodes().get(0);
        Facility f1 = resort.getFacilities().get(1);

        assertEquals(f1.getLocation(), n1);
        assertEquals(facilityFinder.pathToNearestFacility(n1, "Restaurant").getDistance(), 1);
        assertEquals(facilityFinder.pathToNearestFacility(n1, "Restrooms").getDistance(), 1);
    }

    @Test
    public void find_restrooms() {

        // 1 away


    }

}
