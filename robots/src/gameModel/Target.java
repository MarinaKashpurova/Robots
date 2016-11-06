package gameModel;


import java.awt.*;

public class Target
{

    private volatile double m_targetPositionX;
    private volatile double m_targetPositionY;

    public Target(double x, double y) {

        m_targetPositionX = x;
        m_targetPositionY = y;
    }

    public Target(Point.Double point) {

        m_targetPositionY = point.getX();
        m_targetPositionY = point.getY();
    }

    public double get_PositionX() {
        return m_targetPositionX;
    }

    public double get_PositionY() {
        return m_targetPositionY;
    }

    public double set_PositionX(double x){

       return m_targetPositionX = x;
    }

    public double set_PositionY(double y){
        return m_targetPositionY = y;
    }

    public Point.Double get_Position() {

        return new Point.Double(m_targetPositionX, m_targetPositionY);
    }

    //public void setTargetPosition(Point p)
    //{
       // m_targetPositionX = p.x;
        //m_targetPositionY = p.y;
    //}
}
