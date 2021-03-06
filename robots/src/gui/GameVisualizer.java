package gui;

import GameMath.GameMath;
import gameModel.*;
import gameModel.Robot;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JPanel;

public class GameVisualizer extends JPanel
{
    private GameModel gameModel;
    private final Timer m_timer;

    public GameVisualizer(GameModel gameModel)
    {
        this.gameModel = gameModel;
        m_timer = new Timer("events generator", true);
        m_timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                onRedrawEvent();
            }
        }, 0, 50);
        m_timer.schedule(new TimerTask()
        {
            @Override
            public void run()
            {
                gameModel.onModelUpdateEvent();
            }
        }, 0, 1);
        addMouseListener(new MouseAdapter()
        {
            @Override
            public void mouseClicked(MouseEvent e)
            {
                gameModel.setTargetPosition(e.getPoint());
                repaint();
            }
        });
        setDoubleBuffered(true);
    }

    protected void onRedrawEvent()
    {
        EventQueue.invokeLater(this::repaint);
    }

    @Override
    public void paint(Graphics g)
    {
        super.paint(g);
        Graphics2D g2d = (Graphics2D)g; 
        drawRobot(g2d, gameModel.get_Robot());
        drawTarget(g2d, gameModel.get_Target());
    }
    
    private static void fillOval(Graphics g, double centerX, double centerY, int diam1, int diam2)
    {
        g.fillOval((int)(centerX - diam1 / 2), (int)(centerY - diam2 / 2), diam1, diam2);
    }
    
    private static void drawOval(Graphics g, double centerX, double centerY, int diam1, int diam2)
    {
        g.drawOval((int)(centerX - diam1 / 2), (int)(centerY - diam2 / 2), diam1, diam2);
    }
    
    private void drawRobot(Graphics2D g, Robot robot)
    {
        int robotCenterX = GameMath.round(robot.get_PositionX());
        int robotCenterY = GameMath.round(robot.get_PositionY());
        AffineTransform t = AffineTransform.getRotateInstance(robot.get_Direction(), robotCenterX, robotCenterY);
        g.setTransform(t);
        g.setColor(Color.MAGENTA);
        fillOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX, robotCenterY, 30, 10);
        g.setColor(Color.WHITE);
        fillOval(g, robotCenterX  + 10, robotCenterY, 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, robotCenterX  + 10, robotCenterY, 5, 5);
    }
    
    private void drawTarget(Graphics2D g, Target target)
    {
        AffineTransform t = AffineTransform.getRotateInstance(0, 0, 0); 
        g.setTransform(t);
        g.setColor(Color.GREEN);
        fillOval(g, target.get_PositionX(), target.get_PositionY(), 5, 5);
        g.setColor(Color.BLACK);
        drawOval(g, target.get_PositionX(), target.get_PositionY(), 5, 5);
    }

    public GameModel get_gameModel(){
        return gameModel;
    }
}
