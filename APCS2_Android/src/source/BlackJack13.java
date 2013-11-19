package source;


import java.util.ArrayList;
import java.util.Random;

import android.graphics.Color;
import android.os.AsyncTask;


public class BlackJack13 implements Game
{
	private MerlinCore1 mc1;
	private Random randy = new Random();
	
	private int playerChips = 5,
				nextChips = 5,
				roundNum = 1;

	private boolean roundOver = false,
					devMode = false;
	
	private ArrayList<Integer> cardDeck = new ArrayList<Integer>(),
							   playerHand = new ArrayList<Integer>(),
							   computerHand = new ArrayList<Integer>();
			
	public BlackJack13(MerlinCore1 core) {
		//Initialize local MerlinCore1
		this.mc1 = core;
	}
	
	@Override
	public void startGame() {	
		
		//Reset all the buttons
		playerChips = 5;
		nextChips = 5;
		roundNum = 1;
		
		//The game is clearly restarting...
		this.roundOver = false;
		
		this.chipPreview(0);		
	}
	
	public void endGame() {
		
		if(playerChips >= 10)
			mc1.makeToast("Player Won - Ending the Game!");
		
	}

	public void startRound() {
		
		//Reset all the buttons
		this.mc1.resetButtons();
		
		//Clear all the hands and the deck
		cardDeck.clear();
		playerHand.clear();
		computerHand.clear();

		
		//Reload the deck with the 9 cards
		for(int loop = 1; loop < 11; loop++)
			cardDeck.add(loop);
		
		addScore(playerHand, getRandCard());
		addScore(computerHand, getRandCard());
		
		roundOver = false;
	}
	
	public void endRound() {
		
		//The game is officially over
		this.roundOver = true;
	
		//Strings used for the Toast message
		String winner = "null", player = "Player Won", computer = "Computer Won";
		
		//If the player breaks -- computer wins
		if(this.getTotalCards(playerHand) > 13)									   winner = computer;
		//If the computer breaks -- player wins
		else if(this.getTotalCards(computerHand) > 13) 							   winner = player;
		//If the player has a bigger score he wins
		else if(this.getTotalCards(playerHand) > this.getTotalCards(computerHand)) winner = player;
		//If the computer has a bigger score he wins
		else if(this.getTotalCards(playerHand) < this.getTotalCards(computerHand)) winner = computer;
		//Well then is has to be a tie
		else																	   winner = "Tie";
		
		//Show the computer's hand
		this.buttonsOn(computerHand);
		
		if(winner.equals(player)) nextChips++;
		else if(winner.equals(computer)) nextChips--;
		
		if(playerChips < 0 || playerChips >= 10) {
			
			if(playerChips < 0) mc1.makeToast("The Computer Won the Game!");
			else  mc1.makeToast("You Won the Game!");
			
			endGame();
		}
		else { 
			
			mc1.makeToast(winner + " - Ending Round: " + roundNum);
			roundNum++;
			
			this.chipPreview(-20);
		}
	}
	
	@Override
	public void controlProcessor(int num) {
		
		//If the game is running
		if(!roundOver) {
			//If the button is HIT ME
			if( num == 102) {
				//Add a random card to the player's hand
				this.addScore(this.playerHand, getRandCard());	
				//If the player breaks, end the game
				if(this.getTotalCards(this.playerHand) >= 13) endRound();
			}
			//If the button is COMP TURN
			if( num ==  103) {
				//Take as many cards as it wants as long they don't add up to 10
				while(this.getTotalCards(this.computerHand) < 10) this.computerHand.add(this.getRandCard());
				//Then end the game
				endRound();
			}
		}
	}
	
	@Override
	public void clickProcessor(int num) {
	
		//If 0 is pressed
		if(num == 0) {
			//Toggle devMode
			devMode = !devMode;
			
			//Decide which toast bread to toast
			if(devMode) mc1.makeToast("BJ13 DevMode: Activited");
			else mc1.makeToast("BJ13 DevMode: Deactivited");
			return;
		}
		
		//If the round is over -STOP-
		if(roundOver) return;
		
		//If devMode is on
		if(devMode) {
			//If the button is on
			if(mc1.isButtonOn(num)) {
				
				//Turn it off
				mc1.buttonOff(num);
				
				//If the player has the card
				if(playerHand.contains(num)) {
					
					//Remove from the player's hand
					playerHand.remove(Integer.valueOf(num));
					//Give the card to the computer
					this.addScore(computerHand, num);
				} 
				else 
					//Get Rid of the card completely
					computerHand.remove(Integer.valueOf(num));		
			}
			else 
				//Add the card to the player
				this.addScore(playerHand, num);
		}
	}
	

	private void chipPreview(final int l) {
		
		if(l == 0) mc1.resetButtons();
		
		new AsyncTask<Void, Void, Void>() {

			int loop = l;
	
			@Override
			protected Void doInBackground(Void... params) {
				
				if(loop == 0) 
					mc1.buttonFlash(playerChips);
						
				if(loop == 20) {
					
					if(playerChips == nextChips) {
							
						mc1.buttonOff(playerChips);
						loop = 40;
						return null;
					}
					
					mc1.buttonOff(playerChips);
					mc1.buttonFlash(nextChips);
					
					playerChips = nextChips;
				}
				
				loop++;
				
				return null;
			}
			
			@Override
			protected void onPostExecute(Void result) {
			
				if(loop >= 40) {
					mc1.buttonOff(nextChips);
					mc1.buttonOff(playerChips);
					
					startRound();
				} else 
					chipPreview(loop);
			}
			
		}.execute();
	}
	
	private int getTotalCards(ArrayList<Integer> hand) {
		
		//Temporary int to be returned
		int temp = 0;
		
		//Add up all the cards in the hand
		for(int card : hand)
			temp += card;
		
		//Return the total card number
		return temp;
	}
	
	private int getRandCard() {
		
		//Checks for cards in the deck
		if(cardDeck.size() > 0) {
			//Getting the index of the card in the deck
			int index = randy.nextInt(cardDeck.size());
			//Getting the card
			int newCard = cardDeck.get(index);
			
			//Remove the card from the deck
			cardDeck.remove(index);
					
			//Return the card
			return newCard;
		}
		
		//Returns when somehow we run out of cards to give.
		return -1;
	}
	
	private void buttonsOn(ArrayList<Integer> hand) {
		
		for(Integer card : hand) if(mc1.isButtonOn(card) == false) {
				
			//Temporary int used for the button color
			int tempColor = 0;
				
			//Chooses correct color of the button
			if(hand == playerHand) tempColor = Color.BLUE;
			else tempColor = Color.RED;
			
			//Turn on the button
			mc1.buttonOn(card);
			//Set the color of the button
			mc1.numberButton[card].setBackgroundColor(tempColor);
		}
	}
	
	private void addScore(ArrayList<Integer> hand, int cardScore) {
		
		//If there is no cards in the deck -STOP-
		if(cardScore == -1) return;
		
		//Add the card to the given hand
		hand.add(cardScore);
		//Turn on the corresponding button
		this.buttonsOn(hand);
	}
}