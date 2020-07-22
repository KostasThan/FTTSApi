package app.commands.external;

import java.util.function.Supplier;

import app.model.textNspeak.Document;

/**
 * This command is responsible for setting the {@link Voice} fields of a
 * {@link Document} to the exact amounts another Document provides.
 * 
 */
public class CopyVoicePreferencesCommand implements ExternalCommand {

	private Supplier<Document> originalDocumentSupplier;
	private Supplier<Document> copyDocumentSupplier;

	/**
	 * 
	 * @param originalDocumentSupplier The {@link Document} which will provide the
	 *                                 voice settings.
	 * @param copyDocumentSupplier     The {@link Document} which will accept the
	 *                                 voice settings.
	 */
	public CopyVoicePreferencesCommand(Supplier<Document> originalDocumentSupplier,
			Supplier<Document> copyDocumentSupplier) {
		this.originalDocumentSupplier = originalDocumentSupplier;
		this.copyDocumentSupplier = copyDocumentSupplier;
	}

	/**@Override
	 * Copies the {@link Voice} preferences(pitch,volume etc..) of a {@link Document} to
	 * another Document's Voice.
	 *
	 */
	public void execute() {
		Document original = originalDocumentSupplier.get();
		Document copy = copyDocumentSupplier.get();

		copy.setPitch(original.getPitch());
		copy.setRate(original.getRate());
		copy.setVolume(original.getVolume());

	}

}
