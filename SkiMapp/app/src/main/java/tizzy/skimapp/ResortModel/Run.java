package tizzy.skimapp.ResortModel;

/**
 * Created by tizzy on 10/15/18.
 */

public class Run {

    private String name, level;
    private Node start, end;

    public Run(String name, String level, Node start, Node end) {
        this.name = name;
        this.level = level;
        this.start = start;
        this.end = end;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
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
