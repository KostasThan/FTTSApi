package app.commands.external;

import java.util.function.Supplier;

import app.model.textNspeak.Document;

/**
 * This command is responsible for narrating a line of a {@link Document} with
 * the {@link EncodeMethod} the Document is currently set at and, based on the
 * boolean supplier, with reverse.
 *
 */
public class CustomSpeakLineCommand implements ExternalCommand {

	private Supplier<Document> documentSupplier;
	private Supplier<Integer> lineNumSupplier;
	private Supplier<Boolean> reversePolicySupplier;

	/**
	 * 
	 * @param documentSupplier      A Document Supplier to provide the
	 *                              {@link Document}
	 * @param lineNumSupplier       An int Supplier indicating the line to narrate.
	 * @param reversePolicySupplier A boolean supplier indicating whether or not to
	 *                              speak the line in reverse.
	 */
	public CustomSpeakLineCommand(Supplier<Document> documentSupplier, Supplier<Integer> lineNumSupplier,
			Supplier<Boolean> reversePolicySupplier) {
		this.documentSupplier = documentSupplier;
		this.lineNumSupplier = lineNumSupplier;
		this.reversePolicySupplier = reversePolicySupplier;
	}

	/**
	 * Narrates a line of {@link Document} with the {@link EncodeMethod} the
	 * Document has been set to and in reverse if the boolean supplier returns true.
	 * <br>
	 * Internally using the {@link Document#customSpeakLine(int, boolean)
	 * customSpeakLine(int, boolean)} method.
	 */
	public void execute() {
		documentSupplier.get().customSpeakLine(lineNumSupplier.get(), reversePolicySupplier.get());

	}

}
