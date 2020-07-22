package app.commands.external;


import java.util.function.Supplier;

import app.model.textNspeak.Document;



/**
 * This class is responsible for narrating a {@link Document} with the {@link EncodeMethod} the said Document currently has.
 *
 */
public class CustomSpeakDocumentCommand implements ExternalCommand{

	private Supplier<Document> docSupplier;
	private Supplier<Boolean> reversePolicySupplier;

	
	/**
	 * 
	 * @param docSupplier A Document supplier to provide the doc which will perform the {@link Documnet#customSpeakDocument customSpeakDocument(boolean)} method.
	 * @param reversePolicySupplier A boolean supplier indicating whether the Document will be narrated in reverse or not.
	 */
	public CustomSpeakDocumentCommand(Supplier<Document> docSupplier,Supplier<Boolean> reversePolicySupplier) {
		this.docSupplier = docSupplier;
		this.reversePolicySupplier = reversePolicySupplier;
	}

	
	/**
	 * @Override
	 * Narrates a {@link Document} with reverse order (if the Boolean Supplier provides True) and with the {@link EncodeMethod} the Document has been set to.
	 *
	 */
	public void execute() {
		docSupplier.get().customSpeakDocument(reversePolicySupplier.get());

	}

}
