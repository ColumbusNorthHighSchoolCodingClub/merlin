package source;

import java.util.ArrayList;
import java.util.Iterator;

import com.example.schooltest1.R;

import android.media.MediaPlayer;
import android.os.AsyncTask;

public class Sequencer implements Game {

	private MerlinCore1 core;

	private boolean playingBack = false;

	private ArrayList<Integer> buttonSequence = new ArrayList<Integer>();
	
	private int waitTime = 500;

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
			playButton(num);
			buttonSequence.add(num);
		}
	}
	
	public void playButton(final int num) {
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected void onPreExecute() {
				core.buttonOn(num);
				playBeep(num);
			}
			
			@Override
			protected Void doInBackground(Void... params) {
				try {
					Thread.sleep(waitTime);
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
		MediaPlayer mp = null;
		switch (num) {
			case 0 : mp = MediaPlayer.create(core, R.raw.beep_0);
			case 1 : mp = MediaPlayer.create(core, R.raw.beep_1);
			case 2 : mp = MediaPlayer.create(core, R.raw.beep_2);
			case 3 : mp = MediaPlayer.create(core, R.raw.beep_3);
			case 4 : mp = MediaPlayer.create(core, R.raw.beep_4);
			case 5 : mp = MediaPlayer.create(core, R.raw.beep_5);
			case 6 : mp = MediaPlayer.create(core, R.raw.beep_6);
			case 7 : mp = MediaPlayer.create(core, R.raw.beep_7);
			case 8 : mp = MediaPlayer.create(core, R.raw.beep_8);
			case 9 : mp = MediaPlayer.create(core, R.raw.beep_9);
			case 10: mp = MediaPlayer.create(core, R.raw.beep_10);
		}
		if (mp != null) mp.start();
	}

	@Override
	public void controlProcessor(int num) {
		if (num == 102) {
			playBack();
		} else if (num == 103) {
			playBack();
		}
	}
	
	private boolean playBackStarted = false;
	private Iterator<Integer> iterator = null;
	
	private void playBack() {
		if (!playBackStarted) {
			iterator = buttonSequence.iterator();
			this.playingBack = true;
			this.playBackStarted = true;
		}
		if (iterator.hasNext()) {
			new AsyncTask<Void, Void, Void>() {

				@Override
				protected void onPreExecute() {
					int i = iterator.next();
					playButton(i);
				}
				
				@Override
				protected Void doInBackground(Void... params) {
					try {
						Thread.sleep(waitTime);
					} catch (InterruptedException e) {}
					return null;
				}
				
				@Override
				protected void onPostExecute(Void result) {
					playBack();
				}
				
			}.execute();
			
			
		} else {
			playingBack = false;
			playBackStarted = false;
		}
	}

}
