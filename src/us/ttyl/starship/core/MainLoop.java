package us.ttyl.starship.core;

import us.ttyl.starship.movement.MovementEngine;

public class MainLoop extends Thread
{
	public MainLoop()
	{
		initGame();
		SpeedController controller = new SpeedController();
		controller.start();
		start();
	}
	
	public void run()
	{
		while(GameState.mIsRunning == true)
		{	
			try
			{
				//check destroyed and remove from list if so
				for(int i = 0; i < GameState._weapons.size(); i ++)
		    	{
		    		if (GameState._weapons.get(i).getDestroyedFlag() == true)
		    		{
		    			GameState._weapons.remove(i);
		    		}
		    	}
				
		    	for(int i = 0; i < GameState._weapons.size(); i ++)
		    	{
		    		MovementEngine ship = GameState._weapons.get(i);
		    		ship.run();
		    	}
		    	sleep(20);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}       
	
	public void initGame()
	{
		GameState.mIsRunning = true;	
	}
	
}
