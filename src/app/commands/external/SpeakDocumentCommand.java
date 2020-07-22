package app.commands.external;

import java.util.function.Supplier;

import app.model.textNspeak.Document;

/**
 * This class is responsible for narrating the contents of a {@link Document}.
 */
public class SpeakDocumentCommand implements ExternalCommand {

	private Supplier<Document> documentSupplier;

	/**
	 * 
	 * @param documentSupplier A Supplier to provide the document
	 */
	public SpeakDocumentCommand(Supplier<Document> documentSupplier) {
		this.documentSupplier = documentSupplier;
	}

	/**
	 * @Override
	 * Performs the {@link Document#speakDocument() speakDocument} method. 
	 * 
	 */
	public void execute() {
		documentSupplier.get().speakDocument();

	}

}
