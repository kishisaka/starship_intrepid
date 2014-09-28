package us.ttyl.starship.core;

import us.ttyl.starship.env.EnvBuilder;
import us.ttyl.starship.listener.GameStateListener;
import us.ttyl.starship.movement.FollowEngine;
import us.ttyl.starship.movement.FreeEngine;
import us.ttyl.starship.movement.LineEngine;
import us.ttyl.starship.movement.MovementEngine;

public class MainLoop extends Thread
{
	GameStateListener _gameStatelistener;
	
	public MainLoop(GameStateListener gameStateListener)
	{
		initGame();
		_gameStatelistener = gameStateListener;
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
		    	//generate a new ship. 	
		    	int enemyCount = GameUtils.getTypeCount("enemy"); 
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
						// guns fire guns until player reaches 500
						if ((int)(Math.random() * 100) > 60)
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
								if (GameState._muted == false)
								{
									GameState._audioPlayerEnemyShot.play();
								}
							}
						}
						// enemey fires guns and homing missiles if player has more than 500 points
						if ((int)(Math.random() * 100) > 90 && GameState._playerScore > 500)
						{
							if (GameState._weapons.get(i).getWeaponName().equals("enemy"))
							{
								startTimeEnemyGun = currentTimeEnemyGun;
								
								// get player track
								int targetTrack = (int)GameUtils.getTargetTrack(GameState._weapons.get(i), GameState._weapons.get(0));
								
								MovementEngine bullet = new FollowEngine(targetTrack, targetTrack
										, (int)GameState._weapons.get(i).getX()
										, (int)GameState._weapons.get(i).getY(),3, 3, 1, 1, "missile_enemy", GameState._weapons.get(0), GameState._weapons.get(i), 200);  
								GameState._weapons.add(bullet);
								if (GameState._muted == false)
								{
									GameState._audioPlayerMissile.play();
								}
							}
						}
					}
				}
				
				// fire gun constantly
				if (GameState._weapons.get(0).getDestroyedFlag() == false && (GameState._weapons.get(0).getWeaponName().equals("player")))
				{
					long currentTimeGun = currentTime;
					if (currentTimeGun - startTimeGun > 100)
					{
						startTimeGun = currentTimeGun;
						MovementEngine bullet = new LineEngine(GameState._weapons.get(0).getCurrentDirection(), GameState._weapons.get(0).getCurrentDirection()
								, (int)GameState._weapons.get(0).getX()
								, (int)GameState._weapons.get(0).getY(),13, 13, 1, 1, "gun_player", GameState._weapons.get(0), 200);  
						GameState._weapons.add(bullet);
						if (GameState._muted == false)
						{
							GameState._audioPlayerShot.play();
						}
					}
				}
				
				//create clouds 
				long currentTimeClouds = currentTime;
				if (currentTimeClouds - startTimeClouds > 1000)
				{
					//System.out.println("make_cloud: " + (currentTimeClouds - startTimeClouds));
					startTimeClouds = currentTimeClouds;
					EnvBuilder.generateCloud(GameState._weapons.get(0).getX(), GameState._weapons.get(0).getY(), GameState._weapons.get(0).getCurrentDirection());
				}
				
				// move the ships around, check for collisions.
				for(int i = 0; i < GameState._weapons.size(); i ++)
		    	{		    		
		    		MovementEngine ship = GameState._weapons.get(i);
		    		ship.run();		    		
		    		checkCollisions(ship);
		    	}
				
				//check if player is shot, kill game and show show game over
				if (GameState._weapons.get(0).getDestroyedFlag() == true)
				{
					// System.out.println("player shot down. game over.");
				} 
				
				//check destroyed and remove from list if so
				for(int i = 1; i < GameState._weapons.size(); i ++)
		    	{
					if ((i > 0 && GameUtils.getRange(GameState._weapons.get(0), GameState._weapons.get(i)) > 500) 
							|| (GameState._weapons.get(i).getDestroyedFlag() == true))
					{
						GameState._weapons.remove(i);
					}		    		
		    	}
				
				// wait a bit to let other stuff do work. 
		    	sleep(16);
			}
			catch(InterruptedException e)
			{
				e.printStackTrace();
			}
		}
	}       
	
	/**
	 * check for collisions between ships, bullets, etc
	 * @param currentShip
	 */
	private void checkCollisions(MovementEngine currentShip)
	{			
		//ignore clouds and explosions
		if (currentShip.getWeaponName().equals("explosion_particle") == false 
				&& currentShip.getWeaponName().equals("cloud") == false)
		{
			for(int i = 0; i < GameState._weapons.size(); i ++)
			{		
				MovementEngine ship = GameState._weapons.get(i);
				
				if (currentShip.getOrigin() != null)
				{							
					// ignore cloud, explosions and own ship
					if (ship.getWeaponName().equals("explosion_particle") == false 
							&& ship.getWeaponName().equals("missile_player") == false
							&& ship.getWeaponName().equals("gun_player") == false
							&& ship.getWeaponName().equals("cloud") == false)
					{
						if (currentShip.getOrigin().getWeaponName().equals(ship.getWeaponName()) == false 
								&& currentShip.getWeaponName().equals(ship.getWeaponName()) == false
								&& currentShip.getWeaponName().indexOf(ship.getWeaponName()) == -1)									
						{						
							int diffX = Math.abs((int)(currentShip.getX() - ship.getX())); 
							int diffY = Math.abs((int)(currentShip.getY() - ship.getY())); 
							if (diffX <= 10 && diffY <= 10)
							{
								if (GameState._muted == false)
								{
									GameState._audioPlayerEnemyDeath.play();
								}								
								
								if (ship.getWeaponName().equals("enemy") || ship.getWeaponName().equals("player") || currentShip.getWeaponName().equals("player"))
								{									
									if ((currentShip.getWeaponName().equals("player") || ship.getWeaponName().equals("player")) 
											&& GameState._weapons.get(0).getDestroyedFlag() == false)
									{
										GameState._weapons.get(0).set_desiredSpeed(0);
										System.out.println("ship blew up" + ship.getWeaponName());
										Thread t = new Thread(new Runnable()
										{
											@Override
											public void run() 
											{
												try
												{
													sleep(2000);													
													_gameStatelistener.onPlayerDied();
												}
												catch(InterruptedException ie)
												{
													
												}
											}
										});
										t.start();
									}
									
									
									GameState._playerScore = GameState._playerScore + 2;
									GameState._playerEnemyShot = GameState._playerEnemyShot + 1;
									
									// create particle explosion for shot down aircraft
									for(int particleCount = 0; particleCount < 30; particleCount ++)
									{
										int particleDirection = (int)(Math.random() * 360);
										int particleSpeed = (int)(Math.random() * 10);
										int particleEndurance = (int)(Math.random() * 50);
										MovementEngine explosionParticle = new LineEngine(particleDirection, particleDirection
												, ship.getX(), ship.getY(), particleSpeed, 1, 1, 1, "explosion_particle", GameState._weapons.get(0), particleEndurance); 
										GameState._weapons.add(explosionParticle);
									}
								}
								if (ship.getWeaponName().equals("gun_enemy"))
								{
									GameState._playerScore = GameState._playerScore + 1;
									GameState._playerBulletsShot = GameState._playerBulletsShot + 1;
								}
								currentShip.setEndurance(0);
								ship.setDestroyedFlag(true);
								System.out.println(ship.getWeaponName() + " is destroyed.");
								break;
							}
						}
					}
				}
			}
		}
	}
	
	public void initGame()
	{
		GameState.mIsRunning = true;	
	}
	
}
