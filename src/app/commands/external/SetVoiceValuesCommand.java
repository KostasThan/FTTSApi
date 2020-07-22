package app.commands.external;

import java.util.function.Supplier;

import app.model.textNspeak.Document;

/**
 * This command is responsible for setting the {@link Voice} fields of a
 * {@link Document} to the specified amounts.
 *
 */
public class SetVoiceValuesCommand implements ExternalCommand {
	
	private Supplier<Document> documentSupplier;
	private float pitch;
	private float rate;
	private float volume;

	/**
	 * 
	 * @param documentSupplier A supplier to provide the {@link Document}
	 * @param pitch a float amount to set the pitch field at.
	 * @param rate  a float amount to set the words per minute field at.
	 * @param volume a float amount(0<=amount<=1) to set the volume field at.
	 */
	public SetVoiceValuesCommand(Supplier<Document> documentSupplier, float pitch, float rate, float volume) {
		this.documentSupplier = documentSupplier;
		this.pitch = pitch;
		this.rate = rate;
		this.volume = volume;
	}


	
	/**	@Override
	 *  This method sets the {@link Voice} fields to the respective amounts passed in the {@link SetVoiceValuesCommand#SetVoiceValuesCommand(Supplier, float, float, float) constructor}.
	 */
	public void execute() {
		Document doc = documentSupplier.get();

		doc.setPitch(pitch);
		doc.setRate(rate);
		doc.setVolume(volume);
	}
}
