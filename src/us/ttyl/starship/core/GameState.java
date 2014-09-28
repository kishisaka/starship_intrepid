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
	public static AudioPlayer _audioPlayerMissile;
	
	//player score
	public static int _playerScore = 0;
	public static int _playerBulletsShot = 0;
	public static int _playerEnemyShot = 0;
	
	//sound settings
	public static boolean _muted = true;
}
