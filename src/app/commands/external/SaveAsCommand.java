package app.commands.external;


import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;

import app.model.textNspeak.TextAreaUtilities;

/**
 * This command saves the contents of a {@link Document},encapsulated in a {@link TextAreaUtilities}, in a file represented by a String.
 *
 */
public class SaveAsCommand implements ExternalCommand {


	
	private TextAreaUtilities textAreaUtilities;
	private Supplier<String> stringSupplier;
	private AtomicBoolean isSaved;
	
	

	/**
	 * 
	 * @param textAreaUtilities A {@link TextAreaUtilities} instance to perform the {@link TextAreaUtilities#saveAs(String) saveAs} method.
	 * @param stringSupplier A string supplier,providing a string that represents the path to save the contents.
	 * @param isSaved An {@link AtomicBoolean} used to return a result. True if the file was properly saved. False otherwise
	 */
	public SaveAsCommand(TextAreaUtilities textAreaUtilities, Supplier<String> stringSupplier,
			AtomicBoolean isSaved) {
		this.textAreaUtilities = textAreaUtilities;
		this.stringSupplier = stringSupplier;
		this.isSaved = isSaved;
	}

	

	/**
	 * Performs the {@link TextAreaUtilities#saveAs(String) saveAs} method with the
	 * String provided the the String Supplier at the
	 * {@link SaveAsCommand#SaveAsCommand(TextAreaUtilities, Supplier, AtomicBoolean)
	 * constructor}.
	 */
	public void execute() {
		
		isSaved.set(textAreaUtilities.saveAs(stringSupplier.get()));
	}
}
