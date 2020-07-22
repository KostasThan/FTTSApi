package app.view;

import javax.swing.JLabel;

import app.model.textNspeak.Document;
import app.model.textNspeak.ITTSApi;
/**
 * This  class changes the values of three labels 
 * any time the respective voice value changes.<br>
 * <p>
 * No business logic is added in regards to the original {@link Document} class
 * 
 * 
 *
 */
public class LabeledDocument extends Document {


	private JLabel pitchLabel;
	private JLabel rateLabel;
	private JLabel volumeLabel;
	
	public LabeledDocument(ITTSApi speaker, String text, JLabel pitchLabel, JLabel rateLabel, JLabel volumeLabel) {
		super(speaker, text);
		this.pitchLabel = pitchLabel;
		this.rateLabel = rateLabel;
		this.volumeLabel = volumeLabel;
	}



	/**
	 * After calling the super version of the method changes the respective label.
	 */
	public void setPitch(Float hertz) {
		super.setPitch(hertz);
		pitchLabel.setText(super.getPitch() + "");
	}

	/**
	 * After calling the super version of the method changes the respective label.
	 */
	public void setRate(Float wpm) {
		super.setRate(wpm);
		rateLabel.setText(super.getRate() + "");
	}

	
	/**
	 * After calling the super version of the method changes the respective label.
	 */
	public void setVolume(Float volume) {
		super.setVolume(volume);
		volumeLabel.setText(round(super.getVolume(),1) + "");

	}

	

	//the external api uses float
	//when you decrement the volume by 0.1 float it gives rounding error
	//resulting to a value like 0.79999999
	//this is used for better user experience to set the label to a rouned value at 1 decimal point
	private float round (double value, int precision) {
	    int scale = (int) Math.pow(10, precision);
	    return (float) Math.round(value * scale) / scale;
	}
}
