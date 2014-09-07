package us.ttyl.starship.movement;

/**
 * planets and base stations
 * @author test
 *
 */
public class CircleEngine extends MovementEngine
{
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

  		if (_currentTurnMode >= _turnMode)
  		{
  			_currentTurnMode = 0;
  			_currentDirection = _currentDirection + 1;		  
  			if (_currentDirection > 359)
  			{
  				_currentDirection = 0;
  			}
  		}
  		else
  		{
  			_currentTurnMode = _currentTurnMode + _currentSpeed;
  		}
  	}
	
	@Override
	public void updateFuelUsage() 
	{
		// TODO Auto-generated method stub		
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