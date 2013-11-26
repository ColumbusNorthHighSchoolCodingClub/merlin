package source;

import android.app.Activity;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.schooltest1.R;

public class MerlinCore1 extends Activity {

	boolean debugMode = false;  //Turn to false to remove debug messages
	boolean verboseMode = true;  //Turn to false to remove ALL messages

	final Button[] numberButton = new Button[11];	
	final Button[] controlButton = new Button[4];
	boolean[] flashing = new boolean[11];
	boolean flashOn = false;
	
	Game[] theGames = new Game[11];
	
	final static int NEW_GAME = 100;
	final static int SAME_GAME = 101;
	final static int HIT_ME = 102;
	final static int COMP_TURN = 103;
		
	boolean newGamePending = false;
	int activeGameNumber = 0;  //No Game to start
	

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_button_demo4);
		
		setupButtons();
		setupGameArray();
		setupFlashControl();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.button_demo4, menu);
		return true;
	}
	
	/**
	 * As you create a game, update this method to make it active.  
	 */
	public void setupGameArray()
	{
//		theGames[0]
		theGames[1] = new TicTacToe(this);
		theGames[2] = new Sequencer(this);
		theGames[3] = new Echo(this);
		theGames[4] = new BlackJack13(this);
		theGames[5] = new MagicSquare(this);
		theGames[6] = new Mindbender(this);
//		theGames[7] = new WhackAMole(this);
//		theGames[8] = new BonusGame(this);
	}
	
	public void clickProcessor(int buttonNum)
	{
//		debugToast("Pressed #"+buttonNum);
//		flashing[buttonNum] = true;
		
		if(newGamePending)
		{
			changeGameTo(buttonNum);
			newGamePending = false;
		}
		else if(validGameSelected())
		{
			theGames[activeGameNumber].clickProcessor(buttonNum);
		}
	}
	
	public void controlProcessor(int buttonNum)
	{
		debugToast("Pressed #"+buttonNum);
		
		newGamePending = false;
		
		if(buttonNum==NEW_GAME) 
			newGamePending=true;
		else if(buttonNum==SAME_GAME)
			startGame(activeGameNumber);
		else if (validGameSelected()) //Hit Me -or- Comp Turn
			theGames[activeGameNumber].controlProcessor(buttonNum);
	}
	
	public void changeGameTo(int num)
	{
		resetButtons();
		activeGameNumber = num;
		startGame(activeGameNumber);
	}
	
	public void startGame(int num)
	{
		if(validGameSelected())
		{
			makeToast("Starting Game #"+num);
			resetButtons();
			theGames[activeGameNumber].startGame();
		}
	}
	public boolean validGameSelected()
	{
		return theGames[activeGameNumber] != null;
	}
	

/*
 * BUTTON DISPLAY METHODS ----------------------------	
 */
	
	public boolean isButtonOn(int number)
	{
		return (numberButton[number].getCurrentTextColor() == Color.WHITE);
	}
	
	public void buttonOff(int number)
	{
		numberButton[number].setTextColor(Color.RED);
		numberButton[number].setBackgroundColor(Color.LTGRAY);
		flashing[number] = false;
	}
	
	public void buttonOn(int number)
	{
		numberButton[number].setTextColor(Color.WHITE);
		numberButton[number].setBackgroundColor(Color.MAGENTA);
		flashing[number] = false;
	}
	
	//=======================================
	public void setupFlashControl()
	{
		noFlashes();
		startFlashControl();
	}
	
	public void noFlashes()
	{
		for(int x=0; x<flashing.length; x++)
		{
			flashing[x] = false;
		}
	}
	
	public void toggleFlashButton(int num)
	{
		if(flashOn)
			buttonFlashOff(num);
		else
			buttonFlashOn(num);		
	}
	
	/**
	 * This method really needs to improve?...
	 * @param number
	 */
	public void startFlashControl() {
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected void onPreExecute() {
				flashOn = !flashOn;
				for(int x=0;x<flashing.length;x++)
				{
					if(flashing[x])
						toggleFlashButton(x);
				}
			}
			
			@Override
			protected Void doInBackground(Void... params) {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {}
				return null;
			}
			
			@Override
			protected void onPostExecute(Void result) {
				startFlashControl();
			}
		}.execute();
	}
	public void buttonFlash(int number)
	{
		flashing[number] = true;
	}
	public void buttonFlashOff(int number)
	{
		numberButton[number].setTextColor(Color.RED);
		numberButton[number].setBackgroundColor(Color.LTGRAY);
	}
	
	public void buttonFlashOn(int number)
	{
		numberButton[number].setTextColor(Color.WHITE);
		numberButton[number].setBackgroundColor(Color.RED);
	}
	
//	
//	//=======================================================
//	private Handler mHandler = new Handler();
//	
//	private Runnable mUpdateTimeTask = new Runnable() {
//		   public void run() {
//		       if(isButtonOn(flash))
//		    	   buttonFlashOff(flash);
//		       else
//		    	   buttonFlashOn(flash);
//		       mHandler.postAtTime(this, 2500);
//		   }
//		};
//	//========================================================

	public void resetButtons()
	{
		for(int x=0; x<11; x++)
		{
			numberButton[x].setText(""+x);
			buttonOff(x);
		}
	}
	
//=====================================================
	public void debugToast(String msg)
	{
		if(debugMode)
			Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	public void makeToast(String msg)
	{
		if(verboseMode)
			Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

	private void setupButtons() 
	{	
		// 1. Get a reference to the Button.
		//final Button testButton = (Button) findViewById(R.id.Number1);
		numberButton[0] = (Button) findViewById(R.id.Number0);
		numberButton[1] = (Button) findViewById(R.id.Number1);
		numberButton[2] = (Button) findViewById(R.id.Number2);
		numberButton[3] = (Button) findViewById(R.id.Number3);
		numberButton[4] = (Button) findViewById(R.id.Number4);
		numberButton[5] = (Button) findViewById(R.id.Number5);
		numberButton[6] = (Button) findViewById(R.id.Number6);
		numberButton[7] = (Button) findViewById(R.id.Number7);
		numberButton[8] = (Button) findViewById(R.id.Number8);
		numberButton[9] = (Button) findViewById(R.id.Number9);
		numberButton[10] = (Button) findViewById(R.id.Number10);
		
		controlButton[0] = (Button) findViewById(R.id.ControlA);
		controlButton[1] = (Button) findViewById(R.id.ControlB);
		controlButton[2] = (Button) findViewById(R.id.ControlC);
		controlButton[3] = (Button) findViewById(R.id.ControlD);
		
		for(int x=0;x<11;x++)
		{
			// 2. Write some code for the onClick method of the Button.
			final Button tempCopy = numberButton[x];
			tempCopy.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					int num = Integer.parseInt((String)tempCopy.getText());
					clickProcessor(num);
				}
			});
		}
		for(int x=0;x<4;x++)
		{
			// 2. Write some code for the onClick method of the Button.
			final Button tempCopy = controlButton[x];
			final int num = 100+x;

			tempCopy.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) 
				{		
					controlProcessor(num);
				}
			});
		}
	}//---end of setupButtons()
	
	
//	final static int NO_GAME = 0;
//	final static int TIC_TAC_TOE = 1;
//	final static int LIGHT_SEQUENCER = 2;
//	final static int ECHO = 3; 
//	final static int BLACKJACK = 4; 
//	final static int MAGIC_SQUARE = 5;
//	final static int MINDBENDER = 6; 
//	final static int WHACK_A_MOLE = 7;

}
