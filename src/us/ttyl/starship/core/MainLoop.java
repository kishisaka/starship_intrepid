package us.ttyl.starship.core;

import us.ttyl.starship.env.EnvBuilder;
import us.ttyl.starship.movement.LineEngine;
import us.ttyl.starship.movement.MovementEngine;

public class MainLoop extends Thread
{
	public MainLoop()
	{
		initGame();
		// SpeedController controller = new SpeedController();
		// controller.start();
		start();
	}
	
	public void run()
	{
		long startTime = System.currentTimeMillis();
		long startTimeGun = startTime;
		long startTimeEnemyGun = startTime;
		long startTimeClouds = startTime;
		
		//main game loop
		while(GameState.mIsRunning == true)
		{	
			long currentTime = System.currentTimeMillis();
			try
			{
				//check destroyed and remove from list if so
				for(int i = 0; i < GameState._weapons.size(); i ++)
		    	{
					if ((i > 0 && GameUtils.getRange(GameState._weapons.get(0), GameState._weapons.get(i)) > 500) 
							|| (GameState._weapons.get(i).getDestroyedFlag() == true))
					{
						GameState._weapons.remove(i);
					}		    		
		    	}
				
		    	for(int i = 0; i < GameState._weapons.size(); i ++)
		    	{
		    		MovementEngine ship = GameState._weapons.get(i);
		    		ship.run();
		    	}
		    	
		    	//generate a new ship. 	
		    	int enemyCount = GameUtils.getTypeCount("enemy"); 
		    	//System.out.println("enemyCount: " + enemyCount);
				if (currentTime - startTime > 300 && enemyCount < 20)
				{
					startTime = currentTime;
			    	EnvBuilder.generateEnemy(GameState._weapons.get(0).getX()
			    			, GameState._weapons.get(0).getY());			    	
				}
				
				// fire enemy guns constantly	
				long currentTimeEnemyGun = System.currentTimeMillis();
				if (currentTimeEnemyGun - startTimeEnemyGun > 1000)
				{
					for(int i = 0; i < GameState._weapons.size(); i ++)
					{
						if ((int)(Math.random() * 100) > 90)
						{
							if (GameState._weapons.get(i).getWeaponName().equals("enemy"))
							{
								startTimeEnemyGun = currentTimeEnemyGun;
								
								// get player track
								int targetTrack = (int)GameUtils.getTargetTrack(GameState._weapons.get(i), GameState._weapons.get(0));
								
								MovementEngine bullet = new LineEngine(targetTrack, targetTrack
										, (int)GameState._weapons.get(i).getX()
										, (int)GameState._weapons.get(i).getY(),3, 3, 1, 1, "gun_enemy", GameState._weapons.get(0), 200);  
								GameState._weapons.add(bullet);
								GameState._audioPlayerEnemyShot.play();
							}
						}
					}
				}
				
				// fire gun constantly
				long currentTimeGun = currentTime;
				if (currentTimeGun - startTimeGun > 100)
				{
					startTimeGun = currentTimeGun;
					MovementEngine bullet = new LineEngine(GameState._weapons.get(0).getCurrentDirection(), GameState._weapons.get(0).getCurrentDirection()
							, (int)GameState._weapons.get(0).getX()
							, (int)GameState._weapons.get(0).getY(),13, 13, 1, 1, "gun_player", GameState._weapons.get(0), 200);  
					GameState._weapons.add(bullet);
					GameState._audioPlayerShot.play();
				}
				
				//create clouds 
				long currentTimeClouds = currentTime;
				if (currentTimeClouds - startTimeClouds > 1000)
				{
					System.out.println("make_cloud: " + (currentTimeClouds - startTimeClouds));
					startTimeClouds = currentTimeClouds;
					EnvBuilder.generateCloud(GameState._weapons.get(0).getX(), GameState._weapons.get(0).getY(), GameState._weapons.get(0).getCurrentDirection());
				}
				
		    	sleep(15);
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
