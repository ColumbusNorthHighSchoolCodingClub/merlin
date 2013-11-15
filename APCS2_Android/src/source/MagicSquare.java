package source;

import java.util.Random;

import android.widget.Toast;

public class MagicSquare implements Game
{
	MerlinCore1 mc1;
	
	
	public MagicSquare(MerlinCore1 core)
	{
		mc1 = core;
	}
	
	@Override
	public void startGame() 
	{	
		mc1.resetButtons();
//		mc1.buttonFlash(0);
		randomStart();
	}


	@Override
	public void controlProcessor(int num) 
	{
		//No control buttons used in this game
	}
	
	@Override
	public void clickProcessor(int number)
	{
		switch (number)	{
			case 1 : toggleMultipleButtons(new int[] {1, 2, 4}); break;
			case 2 : toggleMultipleButtons(new int[] {1, 2, 3, 5}); break;
			case 3 : toggleMultipleButtons(new int[] {2, 3, 6}); break;
			case 4 : toggleMultipleButtons(new int[] {1, 4, 5, 7}); break;
			case 5 : toggleMultipleButtons(new int[] {2, 4, 5, 6, 8}); break;
			case 6 : toggleMultipleButtons(new int[] {3, 5, 6, 9}); break;
			case 7 : toggleMultipleButtons(new int[] {4, 7, 8}); break;
			case 8 : toggleMultipleButtons(new int[] {5, 7, 8, 9}); break;
			case 9 : toggleMultipleButtons(new int[] {6, 8, 9}); break;			
		}
		//makeToastForButtonPress(number);	
	}
	
	public void makeToastForButtonPress(int number)
	{
		Toast.makeText(mc1,
				mc1.numberButton[number].getText(),
				Toast.LENGTH_SHORT)
				.show();		
	}
	
	public void toggleMultipleButtons(int[] nums)
	{
		for(int x=0; x<nums.length; x++ )
			toggleButtonColor(nums[x]);
	}
	
	public void toggleButtonColor(int number)
	{
		if(mc1.isButtonOn(number))
		{
			mc1.buttonOff(number);
		}
		else
		{
			mc1.buttonOn(number);
		}
	}

	public void randomStart()
	{
		Random randy = new Random();
		for(int x=0; x<100; x++)
		{
			clickProcessor(randy.nextInt(9)+1);
		}
	}

	
	
	
}
