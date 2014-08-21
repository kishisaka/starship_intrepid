package us.ttyl.starship.env;

import us.ttyl.starship.core.GameState;
import us.ttyl.starship.movement.CircleEngine;
import us.ttyl.starship.movement.LineEngine;
import us.ttyl.starship.movement.MovementEngine;

public class EnvBuilder 
{
	private static void generateShip(double planetX, double planetY, int turnmode, int speed)
	{
		/*
			 public CircleEngine(
					   	int direction, 
					   	int currentDirection, 
					   	double currentX, 
					   	double currentY, 
					   	double currentSpeed, 
					   	double maxSpeed, 
					   	double acceleration, 
					   	double turnMode,
						String name, 
						int endurance)
					   {
		*/
		GameState._weapons.add(new CircleEngine(0, 0, planetX, planetY, 1
				, 1, 1, turnmode, ("enemy"),-1));		
	}
	
	public static void generateEnemy(double playerPositionX, double playerPositionY)
	{
		// the enemies
		generateShip(playerPositionX - 500 + (int)(Math.random() * 500), playerPositionY - 350, 0, 10);
		generateShip(playerPositionX - 500 + (int)(Math.random() * 500), playerPositionY + 350, 0, 10);
		generateShip(playerPositionX - 350, playerPositionY - 500 + (int)(Math.random() * 500), 0, 10);
		generateShip(playerPositionX + 350, playerPositionY - 500 + (int)(Math.random() * 500), 0, 10);
		generateShip(playerPositionX - 500 + (int)(Math.random() * 500), playerPositionY - 350, 0, 10);
		generateShip(playerPositionX - 500 + (int)(Math.random() * 500), playerPositionY + 350, 0, 10);
		generateShip(playerPositionX - 350, playerPositionY - 500 + (int)(Math.random() * 500), 0, 10);
		generateShip(playerPositionX + 350, playerPositionY - 500 + (int)(Math.random() * 500), 0, 10);
	}
	
	public static void generateCloud(double playerPositionX, double playerPositionY, int playerTrack)
	{
		/*
		int direction, 
	  	int currentDirection, 
	  	double currentX, 
	  	double currentY, 
	  	double currentSpeed, 
	  	double maxSpeed, 
	  	double acceleration, 
	  	int turnMode, 
	  	String name,
	  	MovementEngine origin, 
	  	int endurance)
	  	*/
		
		// the cloud up
		GameState._weapons.add(new LineEngine(180, 180, playerPositionX - 500 + (int)(Math.random() * 500)
				,  playerPositionY - 350, 1d
				, 1d, 0, 0, "cloud", null, -1));	
		GameState._weapons.add(new LineEngine(0, 0, playerPositionX - 500 + (int)(Math.random() * 500)
				,  playerPositionY - 350, .5d
				, .5d, 0, 0, "cloud", null, -1));	
		// the cloud down
		GameState._weapons.add(new LineEngine(180, 180, playerPositionX - 500 + (int)(Math.random() * 500)
				, playerPositionY + 350, .2d
				, .2d, 0, 0, "cloud", null, -1));	
		GameState._weapons.add(new LineEngine(0, 0, playerPositionX - 500 + (int)(Math.random() * 500)
				, playerPositionY + 350, .3d
				, .3d, 0, 0, "cloud", null, -1));	
		// clound left
		GameState._weapons.add(new LineEngine(180, 180, playerPositionX - 350
				, playerPositionY - 500 + (int)(Math.random() * 500) , .1d
				, .1d, 0, 0, "cloud", null, -1));
		GameState._weapons.add(new LineEngine(0, 0, playerPositionX - 350
				, playerPositionY - 500 + (int)(Math.random() * 500) , 1d
				, 1d, 0, 0, "cloud", null, -1));	
		//cloud right
		GameState._weapons.add(new LineEngine(180, 180, playerPositionX + 350
				, playerPositionY - 500 + (int)(Math.random() * 500), .3d
				, .3d, 0, 0, "cloud", null, -1));	
		GameState._weapons.add(new LineEngine(0, 0, playerPositionX + 350
				, playerPositionY - 500 + (int)(Math.random() * 500), .1d
				, .1d, 0, 0, "cloud", null, -1));	
	}
}
