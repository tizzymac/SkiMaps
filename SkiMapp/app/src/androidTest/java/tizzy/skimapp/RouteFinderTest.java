package tizzy.skimapp;

import android.content.Context;
import android.support.test.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import tizzy.skimapp.ResortModel.tizzy.skimapp.ResortModel.Node;
import tizzy.skimapp.ResortModel.Lift;
import tizzy.skimapp.ResortModel.Resort;
import tizzy.skimapp.ResortModel.Run;

import static org.junit.Assert.assertEquals;

public class RouteFinderTest {

    Resort mResort;

    @Test
    public void nodesConnectedTest() throws Exception {
        // tests areConnected(tizzy.skimapp.ResortModel.Node node1, tizzy.skimapp.ResortModel.Node node2)

        mResort = new Resort(InstrumentationRegistry.getTargetContext());

        tizzy.skimapp.ResortModel.Node node1 = new tizzy.skimapp.ResortModel.Node(1, 1, 1, 1);
        tizzy.skimapp.ResortModel.Node node2 = new tizzy.skimapp.ResortModel.Node(2, 2, 2, 2);
        tizzy.skimapp.ResortModel.Node node3 = new tizzy.skimapp.ResortModel.Node(3, 3, 3, 3);

        Run run1 = new Run("Run1", "Green", node1, node2);
        Run run2 = new Run("Run2", "Blue", node2, node3);
        Lift lift = new Lift("Lift", node3, node1);
        mResort.getRuns().add(run1);
        mResort.getRuns().add(run2);
        mResort.getLifts().add(lift);

        RouteFinder routeFinder = new RouteFinder(mResort);

        assertEquals(true, routeFinder.areConnected(node1, node2));
        assertEquals(true, routeFinder.areConnected(node3, node1));
        assertEquals(false, routeFinder.areConnected(node2, node1));
        assertEquals(false, routeFinder.areConnected(node1, node3));
        assertEquals(false, routeFinder.areConnected(node3, node2));
    }
}
