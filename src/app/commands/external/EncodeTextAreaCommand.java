package app.commands.external;

import java.util.function.Supplier;

import javax.swing.JTextArea;

import app.model.encodes.EncodeFactory;
import app.model.encodes.EncodeMethod;
import app.model.myenums.EncodeEnums;

/**
 * This class is responsible for encoding a {@link JTextArea} using an {@link EncodeMethod}.
 *
 */
public class EncodeTextAreaCommand implements ExternalCommand {

	private JTextArea textArea;
	private Supplier<EncodeEnums> encodeEnum;

	/**
	 * 
	 * @param textArea The text area which contents will be encoded.
	 * @param encodeEnum An {@link EncodeEnums} indicating the {@link EncodeMethod} to use.
	 */
	public EncodeTextAreaCommand(JTextArea textArea, Supplier<EncodeEnums> encodeEnum) {
		this.textArea = textArea;
		this.encodeEnum = encodeEnum;
	}

	
	/**@Override
	 * Encodes the text displayed in a JTextArea using the {@link EncodeMethod} provided.
	 */
	public void execute() {
		EncodeMethod encodeMethod = EncodeFactory.getEncodeMethod(encodeEnum.get());
		textArea.setText(encodeMethod.encode(textArea.getText()));

	}
	
	

}
