package tizzy.skimapp.ResortModel;

import java.io.Serializable;

public class Facility implements Serializable {

    private String mType;
    private String mName;
    private Node mLocation;

    public Facility(String type, String name, Node location) {
        mType = type;
        mName = name;
        mLocation = location;
    }

    public String getType() {
        return mType;
    }

    public String getName() {
        return mName;
    }

    public Node getLocation() {
        return mLocation;
    }
}
