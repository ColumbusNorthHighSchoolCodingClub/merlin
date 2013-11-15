package source;

public interface Game 
{
	/**
	 * Whenever this method is called, a new game should be started	
	 */
	public void startGame();
	
	/**
	 * This method will be called whenever a button is clicked while this game is being played.  
	 * 
	 * @param num - the number of the button that was pressed.
	 */
	public void clickProcessor(int num);
	
	/**
	 * This method will be called whenever a button is clicked while this game is being played.  
	 * 
	 * @param num - the number of the control button that was pressed.
	 */
	public void controlProcessor(int num);

}
