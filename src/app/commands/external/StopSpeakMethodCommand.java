package app.commands.external;

import java.util.function.Supplier;

import app.model.textNspeak.Document;
/**
 * This class is responsible for stoping a document from "Speaking".
 *
 */
public class StopSpeakMethodCommand implements ExternalCommand {
	private Supplier<Document> docSupplier;
	
	/**
	 * 
	 * @param docSupplier A Supplier to provide the {@link Document}.
	 */
	public StopSpeakMethodCommand(Supplier<Document> docSupplier) {
		this.docSupplier = docSupplier;
	}
	
	
	/**
	 * @Override
	 * Stops a {@link Document} from "Speaking". 
	 * <br>If the {@link Document#isSpeaking() isSpeaking()} method specified in the Document class returns false this method has no effect.
	 *
	 */
	public void execute() {
		docSupplier.get().stopSpeaking();
		
	}





	

}
