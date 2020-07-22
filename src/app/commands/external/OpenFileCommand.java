package app.commands.external;

import java.io.File;
import java.util.function.Supplier;

import app.model.textNspeak.TextAreaUtilities;
/**
 * This class is responsible for opening a {@link File} and displaying it's contents, in addition to setting the {@link Document} text to the text that is displayed after the opening of the file.
 *
 */
public class OpenFileCommand implements ExternalCommand {
	

	private TextAreaUtilities textEditorUtilities;
	private Supplier<File> fileSupplier;

	/**
	 * 
	 * @param textEditorUtilities an instance of {@link TextAreaUtilities} which will perform {@link TextAreaUtilities#openFile(File) openFile(File)} method.
	 * @param fileSupplier a supplier providing a {@link File} to open.
	 */
	public OpenFileCommand(TextAreaUtilities textEditorUtilities,Supplier<File> fileSupplier) {
		this.textEditorUtilities = textEditorUtilities;
		this.fileSupplier = fileSupplier;
	}

	/** 
	 * @Override
	 * Internally uses the {@link TextAreaUtilities#openFile(File) openFile(File)} method of the {@link TextAreaUtilities}.
	 */
	public void execute() {
		textEditorUtilities.openFile(fileSupplier.get());
	}

}
