package gui;

import GameMath.GameMath;
import gameModel.GameModel;
import gameModel.Robot;
import gameModel.Target;

import java.awt.*;
import java.beans.PropertyVetoException;

import javax.swing.JInternalFrame;
import javax.swing.JPanel;

public class GameWindow extends JInternalFrame
{
    private final GameVisualizer m_visualizer;
    public GameWindow(int width, int height)
    {
        super("Игровое поле", true, true, true, true);
        GameModel gameModel = new GameModel(new Robot(100, 100), new Target(150, 100));
        m_visualizer = new GameVisualizer(gameModel);
        JPanel panel = new JPanel(new BorderLayout());
        panel.add(m_visualizer, BorderLayout.CENTER);
        setSize(width, height);
        getContentPane().add(panel);
        pack();
    }

    public GameVisualizer get_visualazer(){
        return m_visualizer;
    }

    public GameModel getModel() {
        return m_visualizer.get_gameModel();
    }

    void setState(WindowInfo<GameWindow> state) throws PropertyVetoException {
        if (state.params != null) {
            if (state.params.get("isMax") == 1) setMaximum(true);
            else setSize(state.params.get("width"), state.params.get("height"));
            setLocation(state.params.get("x"), state.params.get("y"));
            if (state.params.get("inFocus") == 1) setRequestFocusEnabled(true);
            if (state.params.get("isClosed") == 1) setClosed(true);
        }
    }

    public void changeVisualizer(GameModel model) {
        Dimension save = getSize();
        setSize(save);
    }

}
