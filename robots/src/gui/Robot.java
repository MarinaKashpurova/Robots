package gui;


import java.util.ArrayList;

public class Robot implements Observable {

    private ArrayList<Observer> subscribers;

    private volatile double m_robotPositionX = 100;
    private volatile double m_robotPositionY = 100;
    private volatile double m_robotDirection = 0;

    private static final double maxVelocity = 0.1;
    private static final double maxAngularVelocity = 0.001;

    public Robot() {
        subscribers = new ArrayList<Observer>();
    }

    public void addObserver(Observer o) {
        subscribers.add(o);
    }

    public void notifyObservers() {
        for (Observer o : subscribers) {
            o.update(m_robotPositionX, m_robotPositionY);
        }
    }

    private void moveRobot(double velocity, double angularVelocity, double duration)
    {
        velocity = GameLogic.applyLimits(velocity, 0, maxVelocity);
        angularVelocity = GameLogic.applyLimits(angularVelocity, -maxAngularVelocity, maxAngularVelocity);
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
        double newDirection = GameLogic.asNormalizedRadians(m_robotDirection + angularVelocity * duration);
        m_robotDirection = newDirection;
    }


    public void onModelUpdateEvent(int targetPositionX, int targetPositionY)
    {
        double distance = GameLogic.distance(targetPositionX, targetPositionY,
                m_robotPositionX, m_robotPositionY);
        if (distance < 0.5)
        {
            return;
        }
        double velocity = maxVelocity;
        double angleToTarget = GameLogic.angleTo(m_robotPositionX, m_robotPositionY,
                targetPositionX, targetPositionY);
        double angularVelocity = 0;
        if (angleToTarget > m_robotDirection)
        {
            angularVelocity = maxAngularVelocity;
        }
        if (angleToTarget < m_robotDirection)
        {
            angularVelocity = -maxAngularVelocity;
        }

        moveRobot(velocity, angularVelocity, 10);
    }

}
