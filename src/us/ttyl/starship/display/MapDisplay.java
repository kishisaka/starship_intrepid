/*
 * @author: Kurt Ishisaka  * Copyright 2003 www.ttyl.us
 */

package us.ttyl.starship.display;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.HashSet;
import java.util.Set;

import javax.swing.JFrame;
import javax.swing.JPanel;

import us.ttyl.starship.core.GameState;
import us.ttyl.starship.core.GameUtils;
import us.ttyl.starship.movement.FollowEngine;
import us.ttyl.starship.movement.LineEngine;
import us.ttyl.starship.movement.MovementEngine;

public class MapDisplay extends JPanel implements KeyListener, MouseListener, MouseMotionListener
{	
  private static final long serialVersionUID = 1L;
  
  private static int[] _degrev = null;
  
  private boolean _isThrottleDown = false;
  
  private MovementEngine _selectedTarget = null;
  
  int _scale = 1;
  int _selected = 0;

  public MapDisplay()
  {
	_degrev = new int[360];
	int counter = 359;
	for(int i = 0; i < 360; i ++)
	{
		_degrev[i] = counter;
		counter = counter - 1;		
	}
	
    JFrame f = new JFrame("Radar");
    f.addWindowListener(new WindowAdapter() 
    {
      public void windowClosing(WindowEvent e)
      {
        System.exit(0);
      }
    });
    
    //get key presses
    f.addKeyListener(this);
    
    //get mouse presses
    f.addMouseListener(this);
    
    //get mouse movement
    f.addMouseMotionListener(this);
    
    f.getContentPane().add(this);
    f.pack();
    f.setSize(500,500);
    f.setVisible(true);
  }

  @Override
  public void paintComponent(Graphics g)
  {
	    //set center target
	    MovementEngine me = GameState._weapons.elementAt(_selected);
	    double centerX = (int)me.getX();
	    double centerY = (int)me.getY();
	
	    //clear radar
	    g.setColor(new Color(0,0,255));
	    g.fillRect(0,0,500,500);
	
	    //draw center target (selected)
	    //g.setColor(new Color(255,0,0));
	    //g.fillRect(250,250,3,3);	
	    if (GameState._weapons.get(0).getDestroyedFlag() == false)
	    {
	    	g.drawImage(GameUtils.getImageType(GameState._weapons.get(0).getCurrentDirection(), GameState._weapons.get(0).getWeaponName()), 232, 232, null);
	    }
	
	    //draw all other targets relative to center target, don't draw center target
	    for (int i = 0; i < GameState._weapons.size(); i ++)
	    {
	    	if (i != _selected)
	    	{	
		        me = (MovementEngine)GameState._weapons.elementAt(i);
		        double x = GameUtils.getA(centerX, me.getX())/_scale;
		        double y = GameUtils.getB(centerY, me.getY())/_scale;
		        double track = GameUtils.track(x, y);
		        double range = getRange(x, y);
		        x = range * Math.cos(Math.toRadians(track));
		        //g.setColor(new Color(0,0,255));
		        y = range * Math.sin(Math.toRadians(track));
		        //g.drawString(me.getWeaponName(), (int)(250 + x) , (int)(250 - y - 10));
		        //g.setColor(new Color(0,255,0));
		        //g.fillRect((int)(250 + x), (int)(250 - y), 3, 3);
		        if (me.getWeaponName().equals("enemy"))
		        {
		        	g.drawImage(GameUtils.getImageType(me.getCurrentDirection(), "enemy"),(int)(232 + x), (int)(232 - y), null);
		        }
		        else if(me.getWeaponName().equals("gun_enemy"))
		        {
		        	g.setColor(new Color(255,0,0));
		            g.fillRect((int)(250 + x), (int)(250 - y), 3, 3);
		        }
		        else if(me.getWeaponName().equals("gun_player"))
		        {
		        	g.setColor(new Color(0,255,0));
		            g.fillRect((int)(250 + x), (int)(250 - y), 3, 3);
		        }
		        else if(me.getWeaponName().equals("missile"))
		        {
		        	g.setColor(new Color(0,0,0));
		            g.fillRect((int)(250 + x), (int)(250 - y), 3, 3);
		        }
		        else if(me.getWeaponName().equals("cloud"))
		        {		        
		        	g.drawImage(GameUtils.getImageType(me.getCurrentDirection(), "cloud"),(int)(232 + x), (int)(232 - y), null);
		        }
		        else if(me.getWeaponName().equals("explosion_particle"))
		        {		        
		        	g.setColor(new Color(115,134,230));
		            g.fillRect((int)(250 + x), (int)(250 - y), 3, 3);
		        }
	    	}	
	    }
	    g.drawString("score: " + GameState._playerScore, (int)(50) , (int)(450));
	    g.drawString("bullet: " + GameState._playerBulletsShot, (int)(150) , (int)(450));
	    g.drawString("enemy: " + GameState._playerEnemyShot, (int)(250) , (int)(450));
  }

  public double getRange(double x, double y)
  {
    return Math.sqrt((x * x) + (y * y));
  }

  public void increaseScale()
  {
    if (_scale < 50)
    {
      _scale = _scale + 1;
    }
  }

  public void decreaseScale()
  {
    if (_scale > 1)
    {
      _scale = _scale - 1;
    }
  }

  public void changeTarget()
  {
	  if (GameState._weapons.size() > 1)
	  {
		  _selected = _selected + 1;
		  if (_selected > (GameState._weapons.size() - 1))
		  {
			  _selected = 0;
		  }
	  }
  }
  
	@Override
	public void keyPressed(KeyEvent arg0)
	{
		char temp = arg0.getKeyChar();	
		if (temp == 'w')
		{		
			GameState.mIsThrottlePressed = true;
		}
	}
	
	@Override
	public void keyReleased(KeyEvent arg0)
	{
		char temp = arg0.getKeyChar();		
		if (temp == 'w')
		{	
			GameState.mIsThrottlePressed = false;
		}
		
		if (temp == 'e')
		{	
			//launch missiles
			
			// find closest target			
			Set <Integer> missleSet = new HashSet<Integer>();
			for(int j = 0; j < 5; j ++)
			{
				MovementEngine closestTarget = null;
				int closestTargetRange = Integer.MAX_VALUE;
				
				// select a target from weapon list
				for(int i = 1; i < GameState._weapons.size(); i ++)
				{
					MovementEngine currentShip = GameState._weapons.get(i);
					if (currentShip.getWeaponName().equals("enemy") && missleSet.contains(currentShip.hashCode()) == false)
					{
						int currentRange = GameUtils.getRange(GameState._weapons.get(0), currentShip);
						//System.out.println("currentRange: "+ currentRange);
						if (currentRange < closestTargetRange)
						{
							closestTarget = currentShip;
							closestTargetRange = currentRange;
							missleSet.add(closestTarget.hashCode());
						}
					}				
				}
				
				int targetTrack = (int)(GameUtils.getTargetTrack(GameState._weapons.get(0), closestTarget));
				
				// once the closest target is selected, launch the missile. 
				if (closestTarget != null)
				{					
					MovementEngine missile = new FollowEngine(targetTrack
							, targetTrack
							, (int)GameState._weapons.get(0).getX(), (int)GameState._weapons.get(0).getY(), .01, 10, .1, 1
							, "missile", closestTarget,  GameState._weapons.get(0), 1000);  
					GameState._weapons.add(missile);
					if (GameState._muted == false)
					{
						GameState._audioPlayerMissile.play();
					}
				}
			}
		}
		
		if (temp == 'r')
		{	
			//fire gun
			double a = GameUtils.getA(GameState._weapons.get(0).getX(), _selectedTarget.getX());
			double b = GameUtils.getB(GameState._weapons.get(0).getY(), _selectedTarget.getY());
			double track = GameUtils.track(a, b);
			MovementEngine bullet = new LineEngine((int)track, (int)track, (int)GameState._weapons.get(0).getX()
					, (int)GameState._weapons.get(0).getY(),25, 25, 1, 1, "gun", GameState._weapons.get(0), 500);  
			GameState._weapons.add(bullet);
		}
	}
	
	@Override
	public void keyTyped(KeyEvent arg0)
	{
		// TODO Auto-generated method stub		
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) 
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e)
	{		
		MovementEngine target = findTarget(e.getX(), e.getY());
		if (target != null)
		{
			_selectedTarget = target;
		}
		else
		{
			int x = e.getX() - 250;
			int y = e.getY() - 250;
			double x1 = GameUtils.getA(0, x);
		    double y1 = GameUtils.getB(0, y);		  
			int track = _degrev[(int)GameUtils.track(x1, y1)];		
			GameState._weapons.get(0).setDirection(track);	
			//System.out.println("desired direction: " + track);
		}
	}
	
	public MovementEngine findTarget(int x, int y)
	{
		x = x - 4;
		y = y - 25;
		MovementEngine me = GameState._weapons.elementAt(_selected);
		double centerX = (int)me.getX();
		double centerY = (int)me.getY();
		 
		for(int i = 0; i < GameState._weapons.size(); i ++)
		{
			me = (MovementEngine)GameState._weapons.elementAt(i);
	        double x1 = GameUtils.getA(centerX, me.getX())/_scale;
	        double y1 = GameUtils.getB(centerY, me.getY())/_scale;
	        double track = GameUtils.track(x1, y1);
	        double range = getRange(x1, y1);
	        x1 = range * Math.cos(Math.toRadians(track));
	        y1 = range * Math.sin(Math.toRadians(track));
	        int targetX = (int)(250 + x1);
	        int targetY = (int)(250 - y1);
	        if (isTargetSelected(x, y, targetX, targetY))
	        {
	        	return me;	        		        
	        }
		}
		return null;		
	}
	
	public boolean isTargetSelected(int x1, int y1, int x2, int y2)
	{
		int difX = Math.abs(x1 - x1);
		int difY = Math.abs(y1 - y2);
		if ((difX < 3) && (difY < 3))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) 
	{
		// TODO Auto-generated method stub
		MovementEngine target = findTarget(e.getX(), e.getY());
		if (target != null)
		{
			_selectedTarget = target;
		}
		else
		{
			int x = e.getX() - 250;
			int y = e.getY() - 250;
			double x1 = GameUtils.getA(0, x);
		    double y1 = GameUtils.getB(0, y);		  
			int track = _degrev[(int)GameUtils.track(x1, y1)];		
			GameState._weapons.get(0).setDirection(track);	
		}
	}
}
