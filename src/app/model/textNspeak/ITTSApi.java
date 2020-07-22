package app.model.textNspeak;

public interface ITTSApi {

	public void speak(String text);

	public String getSpeakedText();

	public boolean isSpeaking();

	public void setPitch(Float hertz);

	public void setRate(Float wpm);

	public void setVolume(Float volume);

	public void stopVoice();

	public float getPitch();
	
	public float getRate();
	
	public float getVolume();


}
