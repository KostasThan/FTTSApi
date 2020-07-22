package app.commands.internal;

import java.awt.MouseInfo;
import java.awt.Point;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

import javax.swing.JTextArea;

import app.model.textNspeak.Document;

/**
 * This command is responsible for updating the a {@link JTextArea}'s text to
 * the text a {@link Document} is currently narrating.
 * <p>
 * The displayed text is changed to the {@link Document#getSpeakedText()
 * getSpeakedText()} return value iff the mouse is inside the JTextArea and the
 * {@link Document#customSpeakDocument(boolean) Document.isSpeaking()} returns
 * true. <bp>Also as long as the JTextArea displayes the speaked text the said
 * JTextArea is disabled.
 * 
 *
 */
public class UpdateTextAreaCommand implements InternalCommand {

	// the intitial delay the executor has
	private static final int INITIAL_DELAY = 200;

	// the period the exectuor has
	private static final int PERIODICAL_DELAY = 50;

	private final ScheduledExecutorService scheduler;
	private JTextArea textArea;
	private Supplier<Document> docSupplier;

	/**
	 * 
	 * @param textArea    The {@link JTextArea} whose text are to be changed
	 *                    according to the {@link Document#getSpeakedText()
	 *                    getSpeakedText()}
	 * @param docSupplier A supplier to provide the {@link Document}.
	 */
	public UpdateTextAreaCommand(JTextArea textArea, Supplier<Document> docSupplier) {
		this.textArea = textArea;
		this.docSupplier = docSupplier;
		scheduler = Executors.newScheduledThreadPool(1);
	}

	/**
	 * @Override This method changed the text of the specified in the
	 *           {@link UpdateTextAreaCommand#UpdateTextAreaCommand(JTextArea, Supplier)
	 *           constructor} accordint to the
	 *           {@link Document#customSpeakDocument(boolean)
	 *           Document.isSpeaking()}.
	 *           <p>
	 *           This only happens iff the mouse is inside the JTextArea and the
	 *           specified Documents {@link Document#isSpeaking() isSpeaking()} is
	 *           true.
	 */
	public void execute() {
		Document doc = docSupplier.get();

		scheduler.scheduleAtFixedRate(new UpdateTextAreaRunnable(textArea, doc), INITIAL_DELAY, PERIODICAL_DELAY,
				TimeUnit.MILLISECONDS);

	}

}

/**
 * This Runnable class implements the business logic of the {@link UpdateTextAreaCommand}
 * @author Kostas
 *
 */
class UpdateTextAreaRunnable implements Runnable {

	private JTextArea textArea;
	private Document doc;

	
	public UpdateTextAreaRunnable(JTextArea textArea, Document doc) {
		this.textArea = textArea;
		this.doc = doc;
	}

	/**
	 * @Override
	 * 
	 * This method updates the document as specified in the {@link UpdateDocumentCommand#execute() execute()}.
	 */
	public void run() {
		

		if (doc.isSpeaking()) {		
		
			if (mouseInsideTextArea()) {
				
				textArea.setEnabled(false);
				textArea.setText(doc.getSpeakedText());

			} else {
				textArea.setText(doc.getText());
				textArea.setEnabled(true);
			}

		} else {
			
			textArea.setText(doc.getText());
			textArea.setEnabled(true);
			textArea.requestFocusInWindow();
			
			//set's input cursor to the end of the text area
			int len = textArea.getDocument().getLength();
			textArea.setCaretPosition(len);
			
			//stops the single thread executor
			throw new IllegalStateException();
		}

	}

	/**
	 * This method checks if the mouse cursor is inside the JTextArea.
	 * @return True if and only if the mouse is inside the JTextArea. False otherwise.
	 */
	private boolean mouseInsideTextArea() {
		//get the mouse potition
		//this potision is in respectt to the top left corner of the screen
		Point mousePointRelativeToScreen = MouseInfo.getPointerInfo().getLocation();
		
		//the upper left corner of the text
		Point containerPoint = textArea.getLocationOnScreen();
		
		Point actualMousePoint = new Point();
		
		//set the actual mouse point
		actualMousePoint.setLocation(mousePointRelativeToScreen.getX() - containerPoint.getX(), mousePointRelativeToScreen.getY() - containerPoint.getY());
		
		return textArea.contains(actualMousePoint);
	}

}
