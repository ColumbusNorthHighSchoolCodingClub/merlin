
package source;

import java.util.Random;

import android.widget.Toast;

public class Mindbender implements Game
{
	MerlinCore1 mc1;

	String compnum;
	int length;
	String guess;
	boolean start;
	int rounds;
	
	
	public Mindbender(MerlinCore1 core)
	{
		mc1 = core;
	}
	
	@Override
	public void startGame() 
	{	
		mc1.resetButtons();
		start = true;
		guess = "";
		rounds = 0;
	}
	
	@Override
	public void controlProcessor(int num) 
	{
		//No control buttons used in this game
	}
	
	@Override
	public void clickProcessor(int number)
	{
		mc1.resetButtons();
		
		if(start)
		{
			switch (number)	{
				case 1 : length = 1; mc1.makeToast("Difficulty: " + length); break;
				case 2 : length = 2; mc1.makeToast("Difficulty: " + length); break;
				case 3 : length = 3; mc1.makeToast("Difficulty: " + length); break;
				case 4 : length = 4; mc1.makeToast("Difficulty: " + length); break;
				case 5 : length = 5; mc1.makeToast("Difficulty: " + length); break;
				case 6 : length = 6; mc1.makeToast("Difficulty: " + length); break;
				case 7 : length = 7; mc1.makeToast("Difficulty: " + length); break;
				case 8 : length = 8; mc1.makeToast("Difficulty: " + length); break;
				case 9 : length = 9; mc1.makeToast("Difficulty: " + length); break;
			}
			randomStart();	
			start = false;
		}
		else
		{			
			switch (number)	{
				case 0 : guess += "0"; mc1.makeToast(guess); break;
				case 1 : guess += "1"; mc1.makeToast(guess); break;
				case 2 : guess += "2"; mc1.makeToast(guess); break;
				case 3 : guess += "3"; mc1.makeToast(guess); break;
				case 4 : guess += "4"; mc1.makeToast(guess); break;
				case 5 : guess += "5"; mc1.makeToast(guess); break;
				case 6 : guess += "6"; mc1.makeToast(guess); break;
				case 7 : guess += "7"; mc1.makeToast(guess); break;
				case 8 : guess += "8"; mc1.makeToast(guess); break;
				case 9 : guess += "9"; mc1.makeToast(guess); break;
			}
		
			if(guess.length() == length)
			{
				rounds++;
				String guessrems = "";
				String compnumrems = "";
				int posnums = 0;
				int presnums = 0;
				//mc1.makeToast(compnum);
				for(int z = 0 ; z < length; z++)
				{
					if(guess.charAt(z) == compnum.charAt(z))
					{
						posnums++;						
					}
					else
					{
						guessrems+=guess.charAt(z);
						compnumrems+=compnum.charAt(z);
					}
				}
				String nextcheck = "";
				for(int a = 0; a < guessrems.length(); a++)
				{
					//System.out.println(guessrems.charAt(a));
					//System.out.println(compnumrems);
					nextcheck = "";
					for(int b = 0; b < compnumrems.length(); b++)
					{
						if(guessrems.charAt(a) == compnumrems.charAt(b))
						{
							presnums++;
							if(b+1 < compnumrems.length())
								{nextcheck+= compnumrems.substring(b+1);}
							b=compnumrems.length();	
							compnumrems=nextcheck;
						}
						else
						{
							nextcheck+=compnumrems.charAt(b);
						}
						//System.out.println(nextcheck);
					}
				}
				for(int x=1; x<=posnums+presnums; x++ )
				{ 
					if(x <= posnums)
						{toggleButtonColor(x);}
					else
						{mc1.buttonFlash(x);}
				}
				//System.out.println("In position numbers: " + posnums);
				//System.out.println("Out of position numbers: " + presnums);
				if(posnums == compnum.length())
				{
					mc1.makeToast("You won! Rounds taken: " + rounds);
					//back to start or restart
				}
				guess = "";			
			}
		}
	}
	
	public void makeToastForButtonPress(int number)
	{
		Toast.makeText(mc1,
				guess,
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
			{mc1.buttonOff(number);	}
		else
			{mc1.buttonOn(number);}
	}

	public void randomStart()
	{
		Random randy = new Random();
		String number = "";
		for(int x=0; x<length; x++)
			{number+="9";}
	   compnum = String.valueOf(randy.nextInt(Integer.parseInt(number)));
	   for(int i= compnum.length(); i<length; i++)
	   	{compnum = "0"+compnum ;}
	   System.out.println("Guess num: " + compnum);    
	}
}
