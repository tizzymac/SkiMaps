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

    public boolean hasType(String type) {
        switch (type) {
            case "Restaurant" : return (mType.equals("Restaurant"));
            // Restaurants assumed to have restrooms
            case "Restrooms" :  return true;
            default:            return false;
        }
    }

    public String getName() {
        return mName;
    }

    public Node getLocation() {
        return mLocation;
    }
}
