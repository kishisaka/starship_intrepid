package us.ttyl.starship.listener;

public interface GameStateListener 
{
	/**
	 * when the player dies, reset the aplayer ship so the player 
	 * can continue playing! 
	 */
	public void onPlayerDied();
}
