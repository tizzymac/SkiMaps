package tizzy.skimapp.ResortModel;

import java.io.Serializable;
import java.util.List;

public abstract class Edge implements Serializable {

    public abstract String getName();
    public abstract Node getEnd();
    public abstract Node getStart();
    public abstract int getWeight();
    public abstract List<Edge> getEdgeSegments();

}
