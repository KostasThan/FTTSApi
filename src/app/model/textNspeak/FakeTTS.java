package app.model.textNspeak;

public class FakeTTS implements ITTSApi {
	private String text;
	private Float hertz;
	private Float wpm;
	private Float volume;
	
	
	

	@Override
	public void speak(String text) {
		this.text = text;
	}

	@Override
	public void setPitch(Float hertz) {
		this.hertz = hertz;

	}

	@Override
	public void setRate(Float wpm) {
		this.wpm = wpm;

	}

	@Override
	public void setVolume(Float volume) {
		this.volume = volume;

	}
	
	public String getText() {
		return text;
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


	//below methods are used in coordination with the actual api
	@Override
	public String getSpeakedText() {
		
		return null;
	}

	@Override
	public boolean isSpeaking() {
		return false;
	}

	@Override
	public void stopVoice() {
		// TODO Auto-generated method stub
		
	}


}
