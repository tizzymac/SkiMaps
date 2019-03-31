package tizzy.skimapp.ResortModel;

import java.io.Serializable;

public class RunStatus implements Serializable {

    private boolean Open;
    private boolean Groomed;

    public RunStatus() {
        Open = true;
        Groomed = true;
    }

    public boolean isOpen() {
        return Open;
    }

    public boolean isGroomed() {
        return Groomed;
    }

    public void setGroomed(boolean groomed) {
        Groomed = groomed;
    }

    public void setOpen(boolean open) {
        Open = open;
    }
}
