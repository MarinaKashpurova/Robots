package GameMath;


import java.awt.*;

public class GameMath {

    public static double distance(Point.Double a, Point.Double b)
    {
        double diffX = a.getX() - b.getX();
        double diffY = a.getY() - b.getY();
        return Math.sqrt(diffX * diffX + diffY * diffY);
    }

    public static double angleTo(double fromX, double fromY, double toX, double toY)
    {
        double diffX = toX - fromX;
        double diffY = toY - fromY;

        return asNormalizedRadians(Math.atan2(diffY, diffX));
    }

    public static double applyLimits(double value, double min, double max)
    {
        if (value < min)
            return min;
        if (value > max)
            return max;
        return value;
    }

    public static double asNormalizedRadians(double angle)
    {
        while (angle < 0)
        {
            angle += 2*Math.PI;
        }
        while (angle >= 2*Math.PI)
        {
            angle -= 2*Math.PI;
        }
        return angle;
    }

    public static int round(double value)
    {
        return (int)(value + 0.5);
    }
}
