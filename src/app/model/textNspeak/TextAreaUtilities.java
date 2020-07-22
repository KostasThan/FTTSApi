package app.model.textNspeak;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.FileTime;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JInternalFrame;
import javax.swing.JTextArea;

import app.model.encodes.EncodeMethod;

/**
 * This class implements the basic functions of a text editor and a
 * 
 * @author Kostas
 *
 */
public class TextAreaUtilities {

	private String selectedFile;
	private JTextArea textArea;
	private String textField;
	private JInternalFrame frame;
	private Document doc;

	/**
	 * @param textEditorInternalFrame The frame to change the title when a new file
	 *                                opened or is created.
	 * @param textArea                The area to which the file contents are to be
	 *                                shown.
	 * @param doc                     The doc to be updated on any change.
	 */
	public TextAreaUtilities(JInternalFrame textEditorInternalFrame, JTextArea textArea, Document doc) {
		this.frame = textEditorInternalFrame;
		this.textArea = textArea;
		this.doc = doc;
		selectedFile = "";
		textField = "";
	}

	/**
	 * Creates a new {@link Document} with the given title and author. The creation
	 * and last modified date is set to the time of executing this method. Also
	 * resets the selected file which is used for the
	 * {@link TextAreaUtilities#save() save()} method.
	 * 
	 * @param title  The title of the Document.
	 * @param author The author of the Document.
	 */
	public void create(String title, String author) {
		// save location is reseted
		selectedFile = "";

		// reset text field to empty
		textField = "";

		// set the doc title
		doc.setTitle(title);

		// set the doc author
		doc.setAuthor(author);

		// set the date created
		doc.setDateCreated(LocalDateTime.now(ZoneId.systemDefault()));

		// update the window title accordingly
		setWindowTitle(title);

		StringBuilder sb = new StringBuilder();
		if (!isNullOrEmpty(title)) {
			sb.append("\t\t\"" + title + "\"\n");
		}
		if (!isNullOrEmpty(author)) {
			sb.append("Written by: " + author + "\n");
		}

		// update the text area
		textArea.setText(sb.toString());

	}

	
	
	
	/**
	 * Opens a file that is saved is the system based on the {@link File} provided.
	 * The contents of the file will be displayed on the JTextArea this
	 * {@link TextAreaUtilities instance} was created with. Also the
	 * {@link Document} is updated
	 * 
	 * @param file The file to read/
	 */
	public void openFile(File file) {

		readContents(file);
		setWindowTitle(file.getAbsolutePath());

		doc.setText(textArea.getText());
		doc.setDateCreated(LocalDateTime.now(ZoneId.systemDefault()));

		textField = doc.getText();

	}

	
	
	
	/**
	 * Saves the text displayed in the JTextArea is a file. For this method to be
	 * used a preselected file has to be set.
	 * <p>
	 * A preselected file is set when:
	 * <li>The {@link TextAreaUtilities#saveAs(String) saveAs(String)} method is
	 * executed without any errors.</li>
	 * <li>When the {@link TextAreaUtilities#openFile(File) openFile(File)} is
	 * executed without any errors.</li>
	 * <li>Via {@link #setSelectedFile(String)} method.</li><br>
	 * A preselected file is reseted when:
	 * <li>When a new document is created via
	 * {@link TextAreaUtilities#create(String, String) create(String,String)}
	 * method</li><br>
	 * 
	 * 
	 * @return True iff the contents of the JTextArea was saved in the system. False
	 *         otherwise.
	 */
	public boolean save() {

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(selectedFile));) {

			StringBuilder sb = new StringBuilder();

			// get text
			sb.append(textArea.getText());

			// print it
			writer.write(sb.toString());

			// make both texts equal so isSaved responds true
			textField = sb.toString();

			setWindowTitle(selectedFile);

			File file = new File(selectedFile);

			// capture that times

			// set last modified uses wrong offest
			// compensate for that
			FileTime creationTime = FileTime
					.fromMillis(doc.getDateCreated().minusHours(3).toInstant(ZoneOffset.UTC).toEpochMilli());

			FileTime lastModifiedTime = FileTime
					.fromMillis(doc.getDateModified().toInstant(ZoneOffset.UTC).toEpochMilli());

			// set them on the created file
			Files.setAttribute(file.toPath(), "creationTime", creationTime);
			Files.setLastModifiedTime(file.toPath(), lastModifiedTime);

		} catch (IOException e1) {

			return false;
		}
		return true;

	}

	
	
	
	
	
	/**
	 * Saves the contents of the JTextArea in a file which path is to be provided
	 * via a String.<br>
	 * In addition to that sets the preselected file to this file. The preselected file is used by the {@link #save() save()} method.
	 * 
	 * @param saveLocation The location to save the file
	 * @return True iff the contents of the JTextArea was saved in the system. False
	 *         otherwise.
	 */
	public boolean saveAs(String saveLocation) {
		if (saveLocation == null) {
			return false;
		}
		String previouslySelected = selectedFile;
		selectedFile = saveLocation;
		File f = new File(selectedFile);
		if (f.exists())
			f.delete();

		try {
			f.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (save() == false) {
			selectedFile = previouslySelected; // if there was a trouble saving the file reset the saving location
			return false;
		}
		
		return true;
	}

	/**
	 * Encodes the text based on the {@link EncodeMethod} provided.
	 * @param cipherMethod the EncodeMethod to be used.
	 */
	public void encodeText(EncodeMethod cipherMethod) {
		textArea.setText(cipherMethod.encode(textArea.getText()));
	}

	
	
	
	
	
	/**
	 * Encoded the selected text of the JTextArea. After encoding, the encoded text is selected again to provide a better understaning of what was encoded.
	 * @param encodeMethod The {@link EncodeMethod} to be used. 
	 */
	public void encodeSelected(EncodeMethod encodeMethod) {

		String highlighted = textArea.getSelectedText();

		// if something is highlighted
		if (!isNullOrEmpty(highlighted)) {
			encodeHighlightedText(highlighted, encodeMethod);
		}

	}

	
	
	
	
	
	
	/**
	 * This method checks if the last saved text is the same with the text displayed.
	 * If the text area is empty it will always return true
	 * @return True iff the displayed text is already saved. 
 	 */
	public boolean isSaved() {

		if(!doc.isSpeaking()&&textArea.getText().contentEquals("")) {
			return true;
		}
		if (doc.isSpeaking())
			return doc.getText().contentEquals(textField);
		else
			return textArea.getText().contentEquals(textField);
	}

	/**
	 * Set the selected file. The selected file is used by the {@link #save()} method.
	 * @param selectedFile A string representing the location of the file.
	 */
	public void setSelectedFile(String selectedFile) {
		this.selectedFile = selectedFile;
	}

	
	
	
	//=============================================
	//==========Private methods
	//=============================================
	
	
	
	
	
	
	// logic behind changing highlighted text
	// and the highlight again for the user to see the changes
	private void encodeHighlightedText(String selectedText, EncodeMethod cipherMethod) {

		String currentText = textArea.getText();
		
		//encode the selected text
		selectedText = cipherMethod.encode(selectedText);
		
		//calculate its location
		int highlightStart = textArea.getSelectionStart();
		int highlightEnd = textArea.getSelectionEnd();
		
		//assemble the unafftected text
		String textBeforeHighlight = currentText.substring(0, highlightStart);
		String textAfterHighlight = currentText.substring(highlightEnd);
		
		StringBuilder displayThis = new StringBuilder();
		
		displayThis.append(textBeforeHighlight).append(selectedText).append(textAfterHighlight);
		
		textArea.setText(displayThis.toString());
		
		//set the focus in the window to select again
		textArea.requestFocusInWindow();
		
		//select the changed text
		textArea.select(highlightStart, highlightEnd);

	}

	
	
	
	
	
	//changes the Title of the window
	private void setWindowTitle(String header) {
		frame.setTitle("Text Editor - " + header);

	}

	private boolean isNullOrEmpty(String text) {
		return "".equals(text) || text == null;
	}

	//reads the contents of a file
	private boolean readContents(File selectedFile) {

		Path path = selectedFile.toPath();

		//read all lines
		try (Stream<String> stream = Files.lines(path, StandardCharsets.UTF_8)) {
			//add a line seperator after every line
			textField = stream.collect(Collectors.joining("\n"));

			textArea.setText(textField);
			
			doc.setText(textField);

		} catch (IOException e) {
			return false;
		}
		return true;

	}

}
