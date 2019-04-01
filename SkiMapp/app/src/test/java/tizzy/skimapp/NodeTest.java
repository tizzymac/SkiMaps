package tizzy.skimapp;

import org.junit.Test;
import static org.junit.Assert.*;

import tizzy.skimapp.ResortModel.Coords;
import tizzy.skimapp.ResortModel.Node;

public class NodeTest {

    Node n1 = new Node("16", new Coords(1,1,1));
    Node n2 = new Node("16", new Coords(2,1,1));
    Node n3 = new Node("33", new Coords(1,1,1));

    @Test
    public void equals_test() {
        assertEquals(n1.equals(n2), true);
        assertEquals(n1.equals(n1), true);
        assertEquals(n1.equals(n3), false);
    }

    @Test
    public void string_test() {
        assertEquals(n1.toString(), "16");
        assertEquals(n3.toString(), "33");
    }

}
