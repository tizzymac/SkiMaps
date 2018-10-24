package tizzy.skimapp.ResortModel;

/**
 * Created by tizzy on 10/15/18.
 */

public class Lift {

    private String name;
    private Node start, end;  // Do we need these?

    public Lift(String name, Node start, Node end) {
        this.name = name;
        this.start = start;
        this.end = end;
    }

    public String getName() {
        return name;
    }
}
