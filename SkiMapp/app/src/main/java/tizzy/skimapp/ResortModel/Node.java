package tizzy.skimapp.ResortModel;

import java.io.Serializable;

public class Node implements Serializable {

    final private String mId;
    private Coords mCoords;

    public Node(String id, Coords coords) {
        this.mId = id;
        this.mCoords = coords;
    }

    public String getId() { return mId; }

    public Coords getCoords() { return mCoords; }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((mId == null) ? 0 : mId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Node other = (Node) obj;
        if (mId == null) {
            if (other.mId != null)
                return false;
        } else if (!mId.equals(other.mId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return mId;
    }
}
