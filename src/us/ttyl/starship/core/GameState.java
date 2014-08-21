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
	
	//sprites
	public static ArrayList <BufferedImage> _sprites;
	
	//sounds
	public static AudioPlayer _audioPlayerShot;
	public static AudioPlayer _audioPlayerEnemyShot;
	public static AudioPlayer _audioPlayerEnemyDeath;
}
