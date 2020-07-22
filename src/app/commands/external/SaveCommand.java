package app.commands.external;

import java.util.concurrent.atomic.AtomicBoolean;

import app.model.textNspeak.Document;
import app.model.textNspeak.TextAreaUtilities;

/**
 * This command saves the contents of a {@link Document} encapsulated in a
 * {@link TextAreaUtilities} in a file that was already defined in the
 * TextAreaUtilities instance.
 *
 */
public class SaveCommand implements ExternalCommand {

	private TextAreaUtilities editor;
	private AtomicBoolean isSaved; // used to pass the result to main gui

	/**
	 * 
	 * @param editor  A {@link TextAreaUtilities} instance to perform the
	 *                {@link TextAreaUtilities#save() save} method.
	 * @param isSaved An {@link AtomicBoolean} used to return a result. True if the
	 *                file was properly saved. False otherwise
	 */
	public SaveCommand(TextAreaUtilities editor, AtomicBoolean isSaved) {
		this.editor = editor;
		this.isSaved = isSaved;
	}

	/**
	 * @Override Performs the {@link TextAreaUtilities#save() save} method specified in the {@link TextAreaUtilities}.
	 *           
	 */
	public void execute() {
		isSaved.set(editor.save());
	}
}
