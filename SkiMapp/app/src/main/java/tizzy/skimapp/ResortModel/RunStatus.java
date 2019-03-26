package tizzy.skimapp.ResortModel;

import java.io.Serializable;

public class RunStatus implements Serializable {

    private boolean mOpen;
    private boolean mGroomed;

    public RunStatus() {
        mOpen = true;
        mGroomed = true;
    }

    public boolean isOpen() {
        return mOpen;
    }

    public void setOpen(boolean open) {
        mOpen = open;
    }

    public boolean isGroomed() {
        return mGroomed;
    }

    public void setGroomed(boolean groomed) {
        mGroomed = groomed;
    }
}
