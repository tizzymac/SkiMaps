package tizzy.skimapp.ResortModel;

/**
 * Created by tizzy on 10/15/18.
 */

public class Lift {

    private String name;
    private Node start, end;

    public Lift(String name, Node start, Node end) {
        this.name = name;
        this.start = start;
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node getStart() {
        return start;
    }

    public void setStart(Node start) {
        this.start = start;
    }

    public Node getEnd() {
        return end;
    }

    public void setEnd(Node end) {
        this.end = end;
    }
}
