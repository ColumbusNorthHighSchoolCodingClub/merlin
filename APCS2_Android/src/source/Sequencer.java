package source;

import java.util.ArrayList;

import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.widget.Toast;

public class Sequencer implements Game {

	private MerlinCore1 core;

	private boolean playingBack = false;

	private ArrayList<Integer> buttonSequence = new ArrayList<Integer>();

	public Sequencer(MerlinCore1 core) {
		this.core = core;
	}

	@Override
	public void startGame() {
		playingBack = false;
		buttonSequence.clear();
	}

	@Override
	public void clickProcessor(int num) {
		if (!playingBack) {
			playBeep(num);
			flashButton(num);
			buttonSequence.add(num);
		}
	}
	
	public void flashButton(final int num) {
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected void onPreExecute() {
				core.buttonOn(num);
			}
			
			@Override
			protected Void doInBackground(Void... params) {
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {}
				return null;
			}
			
			@Override
			protected void onPostExecute(Void result) {
				core.buttonOff(num);
			}
		}.execute();
	}

	/**
	 * Play the tone of the button pressed
	 * 
	 * @param num
	 */
	private void playBeep(int num) {
//		MediaPlayer mp;
//		switch (num) {
//			case 0 : mp = MediaPlayer.create(core, R.raw./*Each of these need audio files in res/raw*/ );
//			case 1 : mp = MediaPlayer.create(core, R.raw./*Each of these need audio files in res/raw*/ );
//			case 2 : mp = MediaPlayer.create(core, R.raw./*Each of these need audio files in res/raw*/ );
//			case 3 : mp = MediaPlayer.create(core, R.raw./*Each of these need audio files in res/raw*/ );
//			case 4 : mp = MediaPlayer.create(core, R.raw./*Each of these need audio files in res/raw*/ );
//			case 5 : mp = MediaPlayer.create(core, R.raw./*Each of these need audio files in res/raw*/ );
//			case 6 : mp = MediaPlayer.create(core, R.raw./*Each of these need audio files in res/raw*/ );
//			case 7 : mp = MediaPlayer.create(core, R.raw./*Each of these need audio files in res/raw*/ );
//			case 8 : mp = MediaPlayer.create(core, R.raw./*Each of these need audio files in res/raw*/ );
//			case 9 : mp = MediaPlayer.create(core, R.raw./*Each of these need audio files in res/raw*/ );
//			case 10: mp = MediaPlayer.create(core, R.raw./*Each of these need audio files in res/raw*/ );
//		}
//		mp.start();
	}

	@Override
	public void controlProcessor(int num) {
		if (num == 102) {
			playBackOneStep();
		} else if (num == 103) {
			playBackOneStep();
		}
	}

	private void playBackOneStep() 
	{
//		new AsyncTask<Integer, Void, Void>() {
//			
//			@Override
//			protected void onPreExecute() {
//				Sequencer.this.playingBack = true;
//			}
//			
//			@Override
//			protected Void doInBackground(Integer... params) {
//				for (Integer i : params) {
//					playBeep(i);
//					try {
//						Thread.sleep(500);
//					} catch (InterruptedException e) {}
//				}
//				return null;
//			}
//			
//			@Override
//			protected void onPostExecute(Void result) 
//			{
//				Sequencer.this.playingBack = false;
//				Sequencer.this.buttonSequence.clear();
//			}
//			
//		}.execute(this.buttonSequence);
	}

}
