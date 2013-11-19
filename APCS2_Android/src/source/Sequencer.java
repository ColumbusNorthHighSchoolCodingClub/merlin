package source;

import java.util.ArrayList;
import java.util.Iterator;

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
//		Toast.makeText(core, Boolean.toString(iterator.hasNext()), Toast.LENGTH_SHORT).show();
		if (iterator.hasNext()) {
			new AsyncTask<Void, Void, Void>() {

				@Override
				protected void onPreExecute() {
					int i = iterator.next();
//					Toast.makeText(core, Integer.toString(i), Toast.LENGTH_SHORT).show();
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