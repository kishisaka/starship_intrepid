package us.ttyl.starship.core;

public class Utils 
{	
	public static double track(double centerX, double centerY, double targetX, double targetY)
	{
		double x = (centerX * -1) + targetX;
		double y = (centerY * -1) + targetY;
		return track(x, y);
	}
	
	public static double getA(double centerX, double targetX)
	{
	    return (centerX * -1) + targetX;
	}

	public static double getB(double centerY, double targetY)
	{
	    return (centerY * -1) + targetY;
	}
	 
	/**
	 * returns deg given a x and y 
	 * @param x
	 * @param y
	 * @return
	 */
	public static double track(double x, double y)
	{
	    double returnDeg = 0;
 
	    if (x > 0 && y > 0)
	    {
	    	returnDeg = Math.toDegrees(Math.atan(y/x));
	    }

	    if (x < 0 && y > 0)
	    {
	    	double convertX = x * -1;
	    	double deg = 180 - (Math.toDegrees(Math.atan(y/convertX)) + 90);
	    	returnDeg = deg + 90;
	    }

	    if (x < 0 && y < 0)
	    {
	    	double convertX = x * -1;
	    	double deg = 180 - (Math.toDegrees(Math.atan(y/convertX)) + 90);
	    	returnDeg = deg + 90;
	    }

	    if (x > 0 && y < 0)
	    {
	    	double convertY = y * -1;
	    	double deg = 180 - (Math.toDegrees(Math.atan(convertY/x)) + 90);
	    	returnDeg = deg + 270;
	    }
	    return returnDeg;
	}
}
