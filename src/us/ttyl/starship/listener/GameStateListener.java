package us.ttyl.starship.listener;

public interface GameStateListener 
{
	/**
	 * when the player dies, reset the player ship so the player 
	 * can continue playing! 
	 */
	public void onPlayerDied();
}
