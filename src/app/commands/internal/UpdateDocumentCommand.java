package app.commands.internal;


import java.util.function.Supplier;

import javax.swing.JTextArea;

import app.model.textNspeak.Document;


/**
 * This class is responsible for copying a {@link JTextArea JTextArea's} displayed text into a {@link Document Document's} text.
 *
 */
public class UpdateDocumentCommand implements InternalCommand{

	
	private JTextArea textArea;
	private Supplier<Document> documentSupplier;
	
	
	/**
	 * 
	 * @param textArea The {@link JTextArea} which contents are to be copied.
	 * @param documentSupplier A {@link Document} supplier which contents are to be set.
	 */
	public UpdateDocumentCommand(JTextArea textArea, Supplier<Document> documentSupplier) {
		this.textArea = textArea;
		this.documentSupplier = documentSupplier;
	}
	
	
	
	/**
	 * @Override
	 * This method copies the {@link JTextArea} text into a {@link Document} via the {@link Document#setText(String) setText(String)} method.
	 */
	public void execute() {
		documentSupplier.get().setText(textArea.getText());
		
	}
}
