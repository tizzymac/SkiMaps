package tizzy.skimapp;

import org.junit.Test;

import java.util.LinkedList;

import tizzy.skimapp.ResortModel.Coords;
import tizzy.skimapp.ResortModel.Node;
import tizzy.skimapp.ResortModel.Path;

import static org.junit.Assert.*;

public class PathTest {

    Path p1;
    Path p2;
    Path p3;
    Path p4;
    Node n4;

    private void setup() {
        Node n1 = new Node("1", new Coords(1,1,1));
        Node n2 = new Node("2", new Coords(2,1,1));
        Node n3 = new Node("3", new Coords(1,1,1));
        n4 = new Node("4", new Coords(3,1,1));

        LinkedList<Node> ll = new LinkedList<>();
        ll.add(n1);
        ll.add(n2);
        ll.add(n3);
        ll.add(n4);

        LinkedList<Node> ll2 = new LinkedList<>();

        LinkedList<Node> ll3 = new LinkedList<>();
        ll3.add(n4);

        p1 = new Path(ll);
        p2 = new Path();
        p3 = new Path(ll2);
        p4 = new Path(ll3);
    }

    @Test
    public void distance_test() {
        setup();

        assertEquals(p1.getDistance(), 4);
        assertEquals(p2.getDistance(), 0);
        assertEquals(p3.getDistance(), 0);
        assertEquals(p4.getDistance(), 1);
    }

    @Test
    public void getNodes_test() {
        setup();

        assertEquals(p1.getNode(1).getId(), "2");
        assertEquals(p1.getNode(3), n4);
        assertEquals(p1.getNodes(0, 3).getDistance(), 4);
    }

    @Test
    public void join_test() {
        setup();

        p1.joinPath(p4);
        assertEquals(p1.getDistance(), 5);
    }

    @Test
    public void equals_test() {
        setup();

        assertEquals(p1.equals(p2), false);
        assertEquals(p1.equals(p1), true);
        assertEquals(p2.equals(p3), true);
    }
}
