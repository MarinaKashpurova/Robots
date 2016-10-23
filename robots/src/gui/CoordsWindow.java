package gui;

import gameModel.GameModel;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class CoordsWindow extends JInternalFrame implements Observer
{
    private final TextArea stateInfo = new TextArea("", 1, 1, TextArea.SCROLLBARS_NONE);

    public CoordsWindow(GameModel model) {
        super("Координаты робота", true, true, true, true);
        model.addObserver(this);
        stateInfo.setEnabled(false);
        getContentPane().add(stateInfo);
        pack();
        onUpdateModel(model);
    }


    @Override
    public void update(Observable observable, Object o) {
        if (observable instanceof GameModel)
            onUpdateModel((GameModel)observable);
    }


    public void onUpdateModel(GameModel gameModel) {
        if (isVisible())
            EventQueue.invokeLater(() ->
                    stateInfo.setText(
                            String.format("Robot: (%f, %f)",
                                    gameModel.get_Robot().get_robotPositionX(),
                                    gameModel.get_Robot().get_robotPositionY())));
    }

}
