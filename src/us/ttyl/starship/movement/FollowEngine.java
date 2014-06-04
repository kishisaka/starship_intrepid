package us.ttyl.starship.movement;

import us.ttyl.starship.core.GameState;
import us.ttyl.starship.core.Utils;

/**
 * 
 * @author test
 * used for missiles
 */
public class FollowEngine extends MovementEngine
{
	MovementEngine _target = null;
  
	public FollowEngine(
		  int direction, 
		  int currentDirection, 
		  double currentX, 
		  double currentY, 
		  double currentSpeed, 
		  double maxSpeed, 
		  double acceleration, 
		  int turnMode, 
		  String name, 
		  MovementEngine target, 
		  MovementEngine origin, 
		  int endurance)
	{
		_target = target;
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

	public void doOther()
	{
	}

	public void updateDisplacement()
	{
	    if (_currentSpeed > 0)
	    {
	      _currentX = _currentX + (Math.cos(Math.toRadians(_currentDirection)) * _currentSpeed);
	      _currentY = _currentY + (Math.sin(Math.toRadians(_currentDirection)) * _currentSpeed);
	    }
	}

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

  public void updateDirection()
  {
    //get direction
    double a = Utils.getA(_currentX, _target.getX());
    double b = Utils.getB(_currentY, _target.getY());
    _direction = ((int)Utils.track(a, b));

    //update direction as per movement rules
    int leftCount = 0;
    double currentDirection = _currentDirection;
    while (currentDirection != _direction)
    {
      leftCount ++;
      currentDirection ++;
      if (currentDirection > 359)
      {
        currentDirection = 0;
      }
    }

    int rightCount = 0;
    currentDirection = _currentDirection;
    while (currentDirection != _direction)
    {
      rightCount ++;
      currentDirection --;
      if (currentDirection < 0)
      {
        currentDirection = 359;
      }
    }

    if (leftCount < rightCount)
    {
      for (int i = 0; i < _turnMode; i ++)
      {
        if (_currentDirection == _direction)
        {
          break;
        }
        else
        {
          _currentDirection ++;
        }
        if (_currentDirection > 359)
        {
          _currentDirection = 0;
        }
      }
    }
    else
    {
      for (int i = 0; i < _turnMode; i ++)
      {
        if (_currentDirection == _direction)
        {
          break;
        }
        else
        {
          _currentDirection --;
        }
        if (_currentDirection < 0)
        {
          _currentDirection = 359;
        }
      }
    }
  }

	@Override
	public void updateCollision()
	{					
		for(int i = 0; i < GameState._weapons.size(); i ++)
		{			
			MovementEngine ship = GameState._weapons.get(i);
			if (_origin.getWeaponName().equals(ship.getWeaponName()) == false 
					&& _name.equals(ship.getWeaponName()) == false)
			{						
				int diffX = Math.abs((int)(_currentX - ship.getX())); 
				int diffY = Math.abs((int)(_currentY - ship.getY())); 
				if (diffX < 10 && diffY < 10)
				{
					System.out.println("collision " + _name + ":" + ship.getWeaponName());
					_endurance = 0;
					break;
				}
			}
		}
	}
	
	@Override
	public void updateFuelUsage() 
	{
		if (_endurance > 0)
		{
			_endurance = _endurance - 1;
		}
	}

	@Override
	public void updateSpeedDecrease() 
	{
		// TODO Auto-generated method stub	
	}
	
	@Override
	public void updateSpeedIncrease() 
	{
		// TODO Auto-generated method stub
	}

}