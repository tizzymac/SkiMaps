package tizzy.skimapp;

import android.content.Context;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;

import static org.junit.Assert.*;

import androidx.test.core.app.ApplicationProvider;

import tizzy.skimapp.ResortModel.Facility;
import tizzy.skimapp.ResortModel.Lift;
import tizzy.skimapp.ResortModel.Node;
import tizzy.skimapp.ResortModel.Resort;
import tizzy.skimapp.ResortModel.Run;

@RunWith(RobolectricTestRunner.class)
public class ResortTest {

    // Create the resort
    private Context context = ApplicationProvider.getApplicationContext();
    private Resort resort = Resort.get(context); // buttermilk

    @Test
    public void emergency_test() {
        assertEquals(resort.getEmergencyNumber(), "970-920-0969");
    }

    @Test
    public void run_test() {
        Run r1 = resort.getRuns().get(0);
        Run r2 = resort.getRuns().get(1);

        Node n1 = resort.getNodes().get(47);
        Node n2 = resort.getNodes().get(48);
        Node n3 = resort.getNodes().get(36);

        assertEquals(r1.getName(), "Baby Doe");
        assertEquals(r2.getName(), "Bear");

        assertEquals(r1.getStart(), n1);
        assertEquals(r1.getEnd(), n2);

        assertEquals(r2.getMidpoints().get(0), n3);
        assertEquals(r1.getMidpoints(), null);

        assertEquals(r1.getWeight(), 1);

        assertEquals(r1.getEdgeSegments().size(), 1);
        assertEquals(r2.getEdgeSegments().size(), 2);

        assertEquals(r1.getLevel(), "Blue");
    }

    @Test
    public void lift_test() {
        Lift l1 = resort.getLifts().get(0);
        Lift l2 = resort.getLifts().get(1);

        Node n1 = resort.getNodes().get(0);
        Node n2 = resort.getNodes().get(1);

        assertEquals(l1.getName(), "Panda Express");
        assertEquals(l2.getName(), "Summit Express");

        assertEquals(l1.getStart(), n1);
        assertEquals(l2.getStart(), n1);
        assertEquals(l2.getEnd(), n2);

        assertEquals(l1.getCapacity(), 2);
        assertEquals(l2.getCapacity(), 4);
    }

    @Test
    public void facility_test() {
        Facility f1 = resort.getFacilities().get(0);
        Facility f2 = resort.getFacilities().get(2);

        Node n2 = resort.getNodes().get(1);

        assertEquals(f1.getName(), "The Cliffhouse");
        assertEquals(f2.getName(), "West Summit");

        assertEquals(f1.getType(), "Restaurant");
        assertEquals(f2.getType(), "Restrooms");
        assertEquals(f1.hasType("Restrooms"), true);
        assertEquals(f1.hasType("Restaurant"), true);
        assertEquals(f2.hasType("Restaurant"), false);
        assertEquals(f2.hasType("Restrooms"), true);
        assertEquals(f1.hasType("str"), false);

        assertEquals(f1.getLocation(), n2);
    }
}
