package app.commands.external;

import app.model.encodes.EncodeFactory;
import app.model.myenums.EncodeEnums;
import app.model.textNspeak.TextAreaUtilities;

/**
 * This command is responsible for encoding the highlighted text of a text
 * area,then highlighting the encoded text again.
 *
 */
public class EncodeSelectedCommand implements ExternalCommand {

	private TextAreaUtilities textAreaUtilities;
	private EncodeEnums encodeEnum;

	/**
	 * 
	 * @param textAreaUtilities A {@link TextAreaUtilities} which will perform the
	 *                          {@link TextAreaUtilities#encodeSelected(app.model.encodes.EncodeMethod)
	 *                          encodeHighlighted(EncodeMethod)} method.
	 * @param encodeEnum        A {@link EncodeEnums} instance indicating which
	 *                          {@link EncodeMethod} to use.
	 */
	public EncodeSelectedCommand(TextAreaUtilities textAreaUtilities, EncodeEnums encodeEnum) {
		this.textAreaUtilities = textAreaUtilities;
		this.encodeEnum = encodeEnum;
	}

	/**
	 * Encodes the text of the JTextArea that is currently selected leaving the rest
	 * of the text intanct. <br>
	 * The encoded text is then selected again. <br>
	 * If not text is selected this method has no effect.
	 */
	public void execute() {
		textAreaUtilities.encodeSelected(EncodeFactory.getEncodeMethod(encodeEnum));

	}

}
