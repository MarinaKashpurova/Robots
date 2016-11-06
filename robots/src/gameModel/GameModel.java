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
        double distance = GameMath.distance(m_target.get_Position(), m_robot.get_Position());
        if (distance < 0.5)
        {
            return;
        }
        double velocity = m_robot.getMaxVelocity();
        double angleToTarget = GameMath.angleTo( m_robot.get_PositionX(),  m_robot.get_PositionY(),
                m_target.get_PositionX(), m_target.get_PositionY());
        double angularVelocity = m_robot.getMaxAngularVelocity();
        if (angleToTarget > m_robot.get_Direction())
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
