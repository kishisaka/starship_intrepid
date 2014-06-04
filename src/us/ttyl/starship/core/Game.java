package us.ttyl.starship.core;

import us.ttyl.starship.display.MapDisplay;
import us.ttyl.starship.display.MovementDisplay;
import us.ttyl.starship.env.EnvBuilder;
import us.ttyl.starship.movement.CircleEngine;
import us.ttyl.starship.movement.FreeEngine;
import us.ttyl.starship.movement.MovementEngine;

public class Game extends Thread
{
	MovementDisplay mMovementDisplay = null;
	MapDisplay mMapDisplay = null;
	MainLoop mMainLoop = null;
	
	public Game()
	{
		init();
		mMainLoop = new MainLoop();		
		mMapDisplay = new MapDisplay();
		mMovementDisplay = new MovementDisplay(mMapDisplay);
		start();
	}
	
	public void init()
	{		
		MovementEngine player = new FreeEngine(0, 0, 0d, 0d, 0d, 0d, 5, .1d, 0, "test0", -1); 
		GameState._weapons.add(player);		 			
		//MovementEngine robot = new CircleEngine(0, 0, 30d, 30d, 1d, 1d, 1d, 1, "robot", -1); 
		//GameState._weapons.add(robot);	
		//MovementEngine starbase = new CircleEngine(0, 0, -30d, -30d, .1d, .1d, .1d, 15, "starbase", -1); 
		//GameState._weapons.add(starbase);	
		EnvBuilder builder = new EnvBuilder();
		builder.generateSystems(100,100);
	}
	
	public void run()
	{
		while (true)
	    {
	    	try
	      	{
	    		mMovementDisplay.updateDisplay();
	    		mMapDisplay.repaint();
	        	sleep(16);
	      	}
	      	catch (Exception e)
	      	{
	        	e.printStackTrace();
	      	}
	    }
	}
	
	public static void main(String args[])
	{
		new Game();
	}
}
