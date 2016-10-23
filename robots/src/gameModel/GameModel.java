package gameModel;

import GameMath.GameMath;

import java.util.Observable;


public class GameModel extends Observable
{
    private Robot m_robot;
    private Target m_target;

    public GameModel(Robot robot, Target target){

        m_robot = robot;
        m_target = target;
    }

    public Robot get_Robot(){
        return m_robot;
    }

    public Target get_Target(){
        return m_target;
    }

    public void onModelUpdateEvent()
    {
        double distance = GameMath.distance(m_target.get_targetPositionX(), m_target.get_targetPositionY(),
                m_robot.get_robotPositionX(), m_robot.get_robotPositionY());
        if (distance < 0.5)
        {
            return;
        }
        double velocity = m_robot.getMaxVelocity();
        double angleToTarget = GameMath.angleTo( m_robot.get_robotPositionX(),  m_robot.get_robotPositionY(),
                m_target.get_targetPositionX(), m_target.get_targetPositionY());
        double angularVelocity = 0;
        angularVelocity = m_robot.getMaxAngularVelocity();
        if (angleToTarget > m_robot.get_robotDirection())
        {
            m_robot.moveRobot(velocity, angularVelocity, 10);
        }
        else {

            m_robot.moveRobot(velocity, -angularVelocity, 10);
        }
        setChanged();
        notifyObservers();
    }

}
