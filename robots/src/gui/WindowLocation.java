package gui;

import javax.swing.JInternalFrame;
import java.io.Serializable;
import java.util.ArrayList;

public class WindowLocation implements Serializable {
    private ArrayList<WindowInfo> states = new ArrayList<>();

    public WindowLocation(MainApplicationFrame frame) {
        for (JInternalFrame iframe : frame.getAllFrames())
            if (iframe instanceof GameWindow) {
                GameWindow game = (GameWindow) iframe;
                states.add(new WindowInfo<>(game));
            } else if (iframe instanceof LogWindow) {
                LogWindow log = (LogWindow) iframe;
                states.add(new WindowInfo<>(log));
            }
    }

    public Iterable<WindowInfo> getAllStates() {
        return states;
    }
}