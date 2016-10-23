package gui;

import javax.swing.*;
import java.io.Serializable;
import java.util.HashMap;

public class WindowInfo<T extends JInternalFrame> implements Serializable {

    private String tag;
    public HashMap<String, Integer> params;

    public WindowInfo(T iframe) {
        tag = iframe.getClass().toGenericString();
        params = new HashMap<>();
        params.put("x", iframe.getX());
        params.put("y", iframe.getY());
        params.put("width", iframe.getWidth());
        params.put("height", iframe.getHeight());
        params.put("isMax", iframe.isMaximum() ? 1 : 0);
        params.put("isClosed", iframe.isClosed() ? 1 : 0);
        params.put("inFocus", iframe.isFocusOwner() ? 1 : 0);
    }

    public String getTag() {
        return tag;
    }

}