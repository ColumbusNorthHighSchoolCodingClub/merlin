package source;

//import android.graphics.Color;
//import android.widget.Toast;
import java.util.Random;
import java.util.ArrayList;
import android.os.AsyncTask;

public class Echo implements Game
{
	
	MerlinCore1 mc1;
	Random random = new Random();
	public int level;//increases the more you get right
	ArrayList<Integer> buttonSequence;
	public int clickCounter;
	public int score;
	boolean endGame;

	boolean allowClicking;
	int canClick;
	int offCount=0;
	
	
	public Echo(MerlinCore1 core)
	{
		mc1 = core;
	}
	
	@Override
	public void startGame() 
	{	
		endGame = false;
		level = 5;
		clickCounter = 0;
		score = -1;
		mc1.resetButtons();
		buttonSequence = getButtonSequence(level/2);
		canClick = 0;
		flashRandomButtons(buttonSequence);
	}
	
	@Override
	public void clickProcessor(int number)
	{
		if(endGame == true)
			return;
//		if(allowClicking == true)
//		if(offCount == getNumOfTimesButtonAppears(buttonSequence))
		if(canClick == buttonSequence.size())
		{
//			if(isYourTurn == true)
//			{
//			mc1.makeToast("Your Turn");
//			isYourTurn = false;
//			}
		clickCounter++;
		score++;
//		mc1.makeToast("Clicked "+Integer.toString(clickCounter)+" times");//debug
		checkClicks(number);
		}
//		mc1.makeToast(Integer.toString(number));//debug
	}

	@Override
	public void controlProcessor(int num)
	{
		
	}
	
	public ArrayList<Integer> getButtonSequence(int level)
	{
		ArrayList<Integer> buttonSequence = new ArrayList<Integer>();
		for(int i=0; i<level; i++)
		{
			int randomButton = random.nextInt(9)+1;
			buttonSequence.add(randomButton);
		}
		return buttonSequence;
	}
	
	public void flashRandomButtons(ArrayList<Integer> sequence)
	{
//		allowClicking = false;
		for(int i=0; i<sequence.size();i++)
		{
//			String s = "";//debug
			mc1.buttonOn(sequence.get(i));
//			mc1.buttonOff(sequence.get(i));
//			s = sequence.get(i).toString();//debug
//			mc1.makeToast(s);//debug
			turnButtonOn(sequence.get(i),250);//off time is 250ms
			turnButtonOff(sequence.get(i),750);//on time is 750ms
			mc1.buttonOff(sequence.get(i));
		}
//		allowClicking = true;
	}
	
	public void checkClicks(int numberClicked)
	{

		if(buttonSequence.get(clickCounter-1) != numberClicked)
		{
			mc1.makeToast("You lose!");
			mc1.makeToast("Score: " + Integer.toString(score));
//			mc1.makeToast("You lose get clickcounter: "+Integer.toString(buttonSequence.get(clickCounter-1)));//debug
			endGame = true;
		}
		if(buttonSequence.size() == clickCounter && endGame == false)
		{
			mc1.makeToast("Level Up!");
//			mc1.makeToast("Size of sequence: "+Integer.toString(buttonSequence.size()));//debug
			level++;
			clickCounter = 0;
			mc1.resetButtons();
			buttonSequence = getButtonSequence(level/2);
			canClick = 0;
			flashRandomButtons(buttonSequence);
		}
	}
	
	public void turnButtonOn(final int button, final int sleepTimems)
	{
		new AsyncTask<Void,Void,Void>()
		{
			@Override
			protected void onPreExecute()
			{
				mc1.buttonOff(button);
			}
			
			@Override
			protected Void doInBackground(Void... params)
			{
				android.os.SystemClock.sleep(sleepTimems);
				return null;
			}
			
			@Override
			protected void onPostExecute(Void result)
			{
				mc1.buttonOn(button);
			}
		}.execute();
	}
	
	public void turnButtonOff(final int button, final int sleepTimems)
	{
		new AsyncTask<Void,Void,Void>()
		{
			@Override
			protected void onPreExecute()
			{
				mc1.buttonOn(button);
			}
			
			@Override
			protected Void doInBackground(Void... params)
			{
				android.os.SystemClock.sleep(sleepTimems);
				return null;
			}
			
			@Override
			protected void onPostExecute(Void result)
			{
				mc1.buttonOff(button);
				canClick++;
				if(canClick == buttonSequence.size())
					mc1.makeToast("Your Turn");
//				allowClicking = true;
			}
		}.execute();
	}
	
//	public boolean isYourTurn()
//	{
//		
//	}
	
//	public boolean isAtEndOfSequence(ArrayList<Integer> sequence)
//	{
//		int lastButton = sequence.get(sequence.size()-1);//check this later
//		int numOfTimesButtonAppears=0;
//		for(int i=0; i<sequence.size();i++)
//		{
//			if(sequence.get(i) == lastButton)
//				numOfTimesButtonAppears++;
//		}
//		return false;
//	}
//	
//	public int getNumOfTimesButtonAppears(ArrayList<Integer> sequence)
//	{
//		int lastButton = sequence.get(sequence.size()-1);//check this later
//		int numOfTimesButtonAppears=0;
//		for(int i=0; i<sequence.size();i++)
//		{
//			if(sequence.get(i) == lastButton)
//				numOfTimesButtonAppears++;
//		}
//		return numOfTimesButtonAppears;
//	}
}