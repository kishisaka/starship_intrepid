package us.ttyl.starship.core;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Vector;

import us.ttyl.starship.movement.MovementEngine;

public class GameState 
{
	public static boolean mIsRunning = false; 
	public static boolean mIsThrottlePressed = false;
	
	public static Vector <MovementEngine>_weapons = new Vector<MovementEngine>();
	public static ArrayList <BufferedImage> _sprites;
}
