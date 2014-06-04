package us.ttyl.starship.env;

import us.ttyl.starship.core.GameState;
import us.ttyl.starship.movement.CircleEngine;

public class EnvBuilder 
{
	public void generatePlanet(double planetX, double planetY, boolean sun, int turnmode)
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
			
		if (sun == true)
		{
			GameState._weapons.add(new CircleEngine(0, 0, planetX, planetY, 0
				, 0, 0, 0, ("sun " + planetX + ":" + planetY),-1));
		}
		else
		{
			GameState._weapons.add(new CircleEngine(0, 0, planetX, planetY, 1
				, 1, 1, turnmode, ("planet " + planetX + ":" + planetY),-1));
		}
	}
	
	public void generateSystems(double systemX, double systemY)
	{
		// the sun
		generatePlanet(systemX, systemX, true, 0);
		
		// the planets
		/*
		int planetCount = 3;
		for(int i = 1; i < planetCount; i ++)
		{
			double planetX = systemX;
			double planetY = systemY - (20 * i);
			int turnmode = i; 
			generatePlanet(planetX, planetY, false, turnmode);
		}
		*/
		
		//sun
		GameState._weapons.add(new CircleEngine(0, 0, 100, 100, 0
				, 0, 0, 0, ("sun " + 100 + ":" + 100),-1));
		
		// planet
		GameState._weapons.add(new CircleEngine(0, 0, 60, -172, 1
				, 1, 1, 1, ("planet " + 100 + ":" + -172),-1));
		
		GameState._weapons.add(new CircleEngine(0, 0, 80, 114, 1
				, 1, 1, 2, ( "planet " + 80 + ":" + -114),-1));
	}
}
