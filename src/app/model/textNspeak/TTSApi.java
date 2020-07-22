package app.model.textNspeak;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import com.sun.speech.freetts.Voice;
import com.sun.speech.freetts.VoiceManager;

import app.model.myenums.VoiceValuesEnum;

//this is the TTSApi
//handling all the speaking
public class TTSApi implements ITTSApi {

	private volatile Voice voice;
	private volatile Deque<String> lines = new ArrayDeque<>();
	private AtomicInteger threadsRunning = new AtomicInteger(0);
	private volatile float hertz = 100;
	private volatile float wpm = 150;

	private ArrayList<String> speakedTextList;
	private volatile float volume = 1;
	private ExecutorService executor;


	
	public TTSApi() {
	
		speakedTextList = new ArrayList<String>();
		executor = Executors.newSingleThreadExecutor();
		System.setProperty("freetts.voices", "com.sun.speech.freetts.en.us.cmu_us_kal.KevinVoiceDirectory");
		voice = VoiceManager.getInstance().getVoice("kevin16");
		voice.setPitch(VoiceValuesEnum.PitchStartingValue.getValue());
		voice.setRate(VoiceValuesEnum.RateStartingValue.getValue());	
		voice.setVolume(VoiceValuesEnum.VolumeStartingValue.getValue());	
		voice.allocate();
	}

	public void speak(String text) {

		
		
		if(!isSpeaking()) {
	

			lines.add(text);
			// start again
			
			startVoice();
			
		
			executor.execute((new VoiceRunnable(voice, lines, threadsRunning,speakedTextList)));
		}else {
		
			lines.add(text);
			threadsRunning.incrementAndGet();
		}
	}

	// retuns the text that is being spoken
	// this text might be different from the
	// displayed one.
	public String getSpeakedText() {
		if (isSpeaking()) {
			return speakedTextList.get(speakedTextList.size()-1);
		} else
			return "";
	}

	public boolean isSpeaking() {
		// if no threads are running no one is speaking
		return threadsRunning.get() > 0 ? true : false;
	}

	public void setPitch(Float hertz) {
		if (hertz >= 0 && hertz <= 600) {
			this.hertz = hertz;
			voice.setPitch(600);
			
		}else {
			IllegalArgumentException e = new IllegalArgumentException("Value must be between 20(included) and 999(included) buw was " + hertz);
			throw e;
		}
	}

	public void setRate(Float wpm) {
		if (wpm >= 0 && wpm <= 999) {
			this.wpm = wpm;
			voice.setRate(wpm);
		} else
			throw new IllegalArgumentException();
	}

	public void setVolume(Float volume) {

		if (volume >= 0 && volume <= 1) {
			this.volume = volume;
			voice.getAudioPlayer().setVolume(volume);
			
		} else {
			IllegalArgumentException e = new IllegalArgumentException("Value must be between 0(included) and 1(included) but was " + volume);
			throw e;
		}
	}

	public void stopVoice() {
	
		voice.getAudioPlayer().cancel();
		voice.deallocate();
		executor.shutdownNow();
		executor = Executors.newSingleThreadExecutor();

		threadsRunning.getAndSet(0);
		lines.clear();
		speakedTextList.clear();
	}
	
	

	private void startVoice() {
	
		voice = VoiceManager.getInstance().getVoice("kevin16");

		voice.allocate();
	
		executor = Executors.newSingleThreadExecutor();
		voice.setPitch(hertz);
		voice.setRate(wpm);
		voice.setVolume(volume);
	}

	public float getPitch() {
	
		return hertz;
	}

	public float getRate() {
		return wpm;
	}

	public float getVolume() {
		return volume;
	}



	
	class VoiceRunnable implements Runnable {
		private volatile Voice voice;
		private volatile Deque<String> lines;
		private AtomicInteger threadsRunning;
		private ArrayList<String> speakedTextList;

		public VoiceRunnable(Voice voice, Deque<String> lines, AtomicInteger threadsRunning,ArrayList<String> speakedTextList) {
			super();
			this.voice = voice;
			this.lines = lines;
			this.threadsRunning = threadsRunning;
			this.speakedTextList = speakedTextList;
		}

		@Override
		public void run() {
			// count the threads running
			// integer is used instead of a boolean
			// to keep track of the threads
			threadsRunning.incrementAndGet();

		
			while (!(lines == null || lines.isEmpty())) {
			
				try {
					
					String text = lines.pop();
					speakedTextList.add(text);
					
					voice.speak(text);
					
				
				} catch (RuntimeException e) {
				
				} finally {
					// TTSApi may closed the voice and set this to 0
					// if not
					if (threadsRunning.get() > 0) {
						// lower the count of threads running by 1
						threadsRunning.decrementAndGet();
					}

				}

			}
		}

	}
	
	

}
