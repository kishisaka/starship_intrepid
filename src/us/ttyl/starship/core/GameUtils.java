package us.ttyl.starship.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import us.ttyl.starship.movement.MovementEngine;

public class GameUtils 
{	
	public static double track(double centerX, double centerY, double targetX, double targetY)
	{
		double x = getA(centerX, targetX);
		double y = getB(centerY, targetY);
		return track(x, y);
	}
	
	public static double getTargetTrack(MovementEngine origin, MovementEngine target)
	{
		double a = getA(origin.getX(), target.getX());
		double b = getB(origin.getY(), target.getY());
		return track(a, b);
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
	
	/**
	 * get range between 2 ships
	 * @param origin
	 * @param target
	 * @return range to target from origin
	 */
	public static int getRange(MovementEngine origin, MovementEngine target)
	{
		double xFactor = (target.getX() - origin.getX()) * (target.getX() - origin.getX());
		double yFactor = (target.getY() - origin.getY()) * (target.getY() - origin.getY());
		return (int)Math.sqrt(xFactor + yFactor);
	}
	
	/**
	 * get ship type count ("gun", "enemy")
	 * @param name
	 * @return ship count
	 */
	public static int getTypeCount(String name)
	{
		int count = 0;
		for(int i = 0; i < GameState._weapons.size(); i ++)
		{
			if (GameState._weapons.get(i).getWeaponName().equals(name))
			{
				count = count + 1;
			}
		}
		return count;
	}
	
	/**
	 * loads sprites from file (15 x 9), 8 and 9 are clouds
	 * @return ArrayList of sprites (BufferedImages) 
	 */
	public static ArrayList <BufferedImage> getTilesFromFile()
	{		
		ArrayList <BufferedImage> tileList = new ArrayList<BufferedImage>();
		try
		{
			BufferedImage tileMap = ImageIO.read(new File("./images/sprites.gif"));			
			for(int y = 0 ; y < 15; y ++)
			{
				for(int x = 0; x < 12; x ++)
				{
					tileList.add(tileMap.getSubimage(x*36, y*36, 36, 36));										
				}
			}			
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return tileList;
	}	
	
	/**
	 * given a track and a ship type, return the appropriate sprite for rendering
	 * @param track
	 * @param type
	 * @return sprite (BufferedImage)
	 */
	public static BufferedImage getImageType(int track, String type)
	{
		if (type.equals("cloud"))
		{
			return GameState._sprites.get(8*12);
		}
		else if (track >= 0 && track < 30)
		{
			if (type.equals("player"))
			{
				return GameState._sprites.get(3);
			}
			else if (type.equals("enemy"))
			{
				return GameState._sprites.get(15);
			}
		}
		else if (track >= 30 && track < 60)
		{
			if (type.equals("player"))
			{
				return GameState._sprites.get(2);
			}
			else if (type.equals("enemy"))
			{
				return GameState._sprites.get(14);
			}
		}
		
		else if (track >= 60 && track < 90)
		{
			if (type.equals("player"))
			{
				return GameState._sprites.get(1);
			}
			else if (type.equals("enemy"))
			{
				return GameState._sprites.get(13);
			}
		}
		else if (track >= 90 && track < 120)
		{
			if (type.equals("player"))
			{
				return GameState._sprites.get(0);
			}
			else if (type.equals("enemy"))
			{
				return GameState._sprites.get(12);
			}
		}
		else if (track >= 120 && track < 150)
		{
			if (type.equals("player"))
			{
				return GameState._sprites.get(11);
			}
			else if (type.equals("enemy"))
			{
				return GameState._sprites.get(23);
			}
		}
		else if (track >= 150 && track < 180)
		{
			if (type.equals("player"))
			{
				return GameState._sprites.get(10);
			}
			else if (type.equals("enemy"))
			{
				return GameState._sprites.get(22);
			}
		}
		else if (track >= 180 && track < 210)
		{
			if (type.equals("player"))
			{
				return GameState._sprites.get(9);
			}
			else if (type.equals("enemy"))
			{
				return GameState._sprites.get(21);
			}
		}
		else if (track >= 210 && track < 240)
		{
			if (type.equals("player"))
			{
				return GameState._sprites.get(8);
			}
			else if (type.equals("enemy"))
			{
				return GameState._sprites.get(20);
			}
		}
		else if (track >= 240 && track < 270)
		{
			if (type.equals("player"))
			{
				return GameState._sprites.get(7);
			}
			else if (type.equals("enemy"))
			{
				return GameState._sprites.get(19);
			}
		}
		else if (track >= 270 && track < 300)
		{
			if (type.equals("player"))
			{
				return GameState._sprites.get(6);
			}
			else if (type.equals("enemy"))
			{
				return GameState._sprites.get(18);
			}
		}
		else if (track >= 300 && track < 330)
		{
			if (type.equals("player"))
			{
				return GameState._sprites.get(5);
			}
			else if (type.equals("enemy"))
			{
				return GameState._sprites.get(17);
			}
		}
		else if (track >= 330 && track < 360)
		{
			if (type.equals("player"))
			{
				return GameState._sprites.get(4);
			}
			else if (type.equals("enemy"))
			{
				return GameState._sprites.get(16);
			}
		}
		return null;
	}
}
