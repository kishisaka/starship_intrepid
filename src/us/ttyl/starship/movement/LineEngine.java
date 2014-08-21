package us.ttyl.starship.movement;

import us.ttyl.starship.core.GameState;

/**
 * used for gun type weapons, maybe even beams
 * @author test
 *
 */
public class LineEngine extends MovementEngine
{
	public LineEngine(
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
	{
	    _name = name;
	    _direction = direction;
	    _currentDirection = currentDirection;
	    _currentSpeed = currentSpeed;
	    _maxSpeed = maxSpeed;
	    _acceleration = acceleration;
	    _currentX = currentX;
	    _currentY = currentY;
	    _destroyed = false;
	    _turnMode = turnMode;
		_endurance = endurance;
		_origin = origin;
	}
	
	@Override
	public void updateSpeed()
	{	
	    if (_currentSpeed > 0)
	    {
	    	_currentSpeed = _currentSpeed + _acceleration;
	    	if (_currentSpeed > _maxSpeed)
	    	{
	    		_currentSpeed = _maxSpeed;
	    	}
	    }
	}

	@Override
	public void updateDirection()
	{
	}

	@Override
	public void updateDisplacement() 
	{	
		_currentX = _currentX + (Math.cos(Math.toRadians(_currentDirection)) * _currentSpeed);
	    _currentY = _currentY + (Math.sin(Math.toRadians(_currentDirection)) * _currentSpeed);
	}

	@Override
	public void updateCollision() 
	{
		for(int i = 0; i < GameState._weapons.size(); i ++)
		{			
			MovementEngine ship = GameState._weapons.get(i);
			if (_origin != null)
			{
				if (_origin.getWeaponName().equals(ship.getWeaponName()) == false 
						&& _name.equals(ship.getWeaponName()) == false
						&& _name.indexOf(ship.getWeaponName()) == -1 
						&& ship.getWeaponName().equals("cloud")== false)
				{						
					int diffX = Math.abs((int)(_currentX - ship.getX())); 
					int diffY = Math.abs((int)(_currentY - ship.getY())); 
					if (diffX < 10 && diffY < 10)
					{
						//System.out.println("collision " + _name + ":" + ship.getWeaponName());
						GameState._audioPlayerEnemyDeath.play();
						_endurance = 0;
						ship.setDestroyedFlag(true);
						break;
					}
				}
			}
		}
	}

	@Override
	public void updateFuelUsage() 
	{
		if (_endurance > 0)
		{
			//System.out.println("endurance_check for " + _name + ":" + _endurance);
			_endurance = _endurance - 1;
		}
	}

	@Override
	public void doOther()
	{
	}


	@Override
	public void updateSpeedIncrease() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateSpeedDecrease()
	{
		// TODO Auto-generated method stub
		
	}

}
