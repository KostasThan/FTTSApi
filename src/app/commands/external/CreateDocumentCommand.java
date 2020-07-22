package app.commands.external;

import java.util.function.Supplier;

import app.model.textNspeak.TextAreaUtilities;

/**
 * This class is responsible for creating a {@link Document} with a Title and an
 * Author.
 *
 */
public class CreateDocumentCommand implements ExternalCommand {

	private TextAreaUtilities editor;
	private Supplier<String> titleSupplier;
	private Supplier<String> authorSupplier;

	/**
	 * 
	 * @param editorSupplier The {@link TextAreaUtilities editor} which gonna
	 *                       perform the
	 *                       {@link TextAreaUtilities#create(String, String)
	 *                       create(String, String)} method.
	 * @param titleSupplier  The title of the {@link Document}.
	 * @param authorSupplier The author of the Document.
	 */
	public CreateDocumentCommand(TextAreaUtilities editor, Supplier<String> titleSupplier,
			Supplier<String> authorSupplier) {
		this.editor = editor;
		this.titleSupplier = titleSupplier;
		this.authorSupplier = authorSupplier;
	}

	/**
	 * @Override Creates a new {@link Document} using the
	 *           {@link TextAreaUtilities#create(String, String)
	 *           create(String,String)} method of a {@link TextAreaUtilities}
	 *           instance.
	 * 
	 *
	 */
	public void execute() {

		editor.create(titleSupplier.get(), authorSupplier.get());
	}

}
