package gameModel;

import GameMath.GameMath;
import java.awt.Point;


public class Robot {

    private volatile double m_robotPositionX ;
    private volatile double m_robotPositionY ;
    private volatile double m_robotDirection = 0;
    private double m_maxVelocuty = 0.1;

    public Robot(double x, double y) {

        m_robotPositionX = x;
        m_robotPositionY = y;
    }

    public void moveRobot(double velocity, double angularVelocity, double duration)
    {
        velocity = GameMath.applyLimits(velocity, 0, m_maxVelocuty);
        angularVelocity = GameMath.applyLimits(angularVelocity, -getMaxAngularVelocity(), getMaxAngularVelocity());
        double newX = m_robotPositionX + velocity / angularVelocity *
                (Math.sin(m_robotDirection  + angularVelocity * duration) -
                        Math.sin(m_robotDirection));
        if (!Double.isFinite(newX))
        {
            newX = m_robotPositionX + velocity * duration * Math.cos(m_robotDirection);
        }
        double newY = m_robotPositionY - velocity / angularVelocity *
                (Math.cos(m_robotDirection  + angularVelocity * duration) -
                        Math.cos(m_robotDirection));
        if (!Double.isFinite(newY))
        {
            newY = m_robotPositionY + velocity * duration * Math.sin(m_robotDirection);
        }
        m_robotPositionX = newX;
        m_robotPositionY = newY;
        m_robotDirection = GameMath.asNormalizedRadians(m_robotDirection + angularVelocity * duration);
    }

    public double get_PositionX() {
        return m_robotPositionX;
    }

    public double get_PositionY() {
        return m_robotPositionY;
    }

    public Point.Double get_Position() {

        return new Point.Double(m_robotPositionX, m_robotPositionY);
    }

    public double get_Direction() {
        return m_robotDirection;
    }

    public double getMaxVelocity() {
        return 0.1;
    }

    public double getMaxAngularVelocity() {
        return 0.001;
    }

}
