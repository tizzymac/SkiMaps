package tizzy.skimapp;

import org.junit.Test;

import tizzy.skimapp.ResortModel.SkiLevel;

import static org.junit.Assert.*;

public class SkiLevelTest {

    SkiLevel s1 = new SkiLevel("Black", "USA");
    SkiLevel s2 = new SkiLevel("Blue", "USA");
    SkiLevel s3 = new SkiLevel("Green", "USA");
    SkiLevel s4 = new SkiLevel("Red", "USA");
    SkiLevel s5 = new SkiLevel("str", "USA");
    SkiLevel s6 = new SkiLevel("Green", "Europe");
    SkiLevel s7 = new SkiLevel("Blue", "Europe");
    SkiLevel s8 = new SkiLevel("Red", "Europe");
    SkiLevel s9 = new SkiLevel("Black", "Europe");
    SkiLevel s10 = new SkiLevel("str", "Europe");

    @Test
    public void string_test() {
        // USA
        assertEquals(s1.getLevelString(), "Black");
        assertEquals(s2.getLevelString(), "Blue");
        assertEquals(s3.getLevelString(), "Green");
        assertEquals(s4.getLevelString(), "Black");
        assertEquals(s5.getLevelString(), "Black");

        // EU
        assertEquals(s6.getLevelString(), "Green");
        assertEquals(s7.getLevelString(), "Blue");
        assertEquals(s8.getLevelString(), "Red");
        assertEquals(s9.getLevelString(), "Black");
        assertEquals(s10.getLevelString(), "Black");
    }

    @Test
    public void number_test() {
        // USA
        assertEquals(s1.getLevelNumber(), 3);
        assertEquals(s2.getLevelNumber(), 2);
        assertEquals(s3.getLevelNumber(), 1);
        assertEquals(s4.getLevelNumber(), 3);
        assertEquals(s5.getLevelNumber(), 3);

        // EU
        assertEquals(s6.getLevelNumber(), 1);
        assertEquals(s7.getLevelNumber(), 2);
        assertEquals(s8.getLevelNumber(), 3);
        assertEquals(s9.getLevelNumber(), 4);
        assertEquals(s10.getLevelNumber(), 4);
    }
}
