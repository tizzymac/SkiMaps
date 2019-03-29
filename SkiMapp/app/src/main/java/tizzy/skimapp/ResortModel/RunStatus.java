package tizzy.skimapp.ResortModel;

import java.io.Serializable;

public class RunStatus implements Serializable {

    private boolean Open;
    private boolean Groomed;

    public RunStatus() {
        Open = true;
        Groomed = true;
    }

    public RunStatus(boolean Open, boolean Groomed) {
        this.Open = Open;
        this.Groomed = Groomed;
    }

    public boolean isOpen() {
        return Open;
    }

    public void Open(boolean open) {
        Open = open;
    }

    public boolean isGroomed() {
        return Groomed;
    }

    public void Groomed(boolean groomed) {
        Groomed = groomed;
    }

    public String groomStatus() {
        return (Groomed) ? "Groomed" : "Not Groomed";
    }

    public String openStatus() {
        return (Open) ? "Open" : "Closed";
    }
}
