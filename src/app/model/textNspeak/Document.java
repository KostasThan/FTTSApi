package app.model.textNspeak;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;

import app.model.encodes.DefaultEncode;
import app.model.encodes.EncodeMethod;

/**
 * This class represents a Documents. It has title,authros, date of creation,
 * last modified date, and an {@link EncodeMethod}. A client can narrate the
 * documet with different encode methods,order or narrate a specific line of the
 * text.
 * 
 * @author Kostas
 *
 */
public class Document {

	private String text;
	private List<String> textLines;
	private ITTSApi speaker;
	private EncodeMethod encodeMethod;
	private String title;
	private String author;
	private LocalDateTime dateCreated;
	private LocalDateTime dateModified;

	
	

	// =========================
	// ====Constructors
	// =========================

	/**
	 * 
	 * @param speaker The {@link ITTSApi} to be used for narrating the document.
	 */
	public Document(ITTSApi speaker) {
		this(speaker, "");
	}

	/**
	 * 
	 * @param speaker The {@link ITTSApi} to be used for narrating the document.
	 * @param text    The original text this {@link Document} will contain.
	 */
	public Document(ITTSApi speaker, String text) {
		textLines = new ArrayList<>();

		encodeMethod = DefaultEncode.getInstance();

		setText(text);

		this.speaker = speaker;

		// capturing the date of creation
		dateCreated = LocalDateTime.now(ZoneId.systemDefault());

		// last modified date is the date of creation
		dateModified = dateCreated;

	}

	// =======================================

	
	
	
	
	
	
	// ==================================
	// ========Speak methods
	// =================================

	/**
	 * This methdod speaks the document in order without any {@link EncodeMethod}.
	 */
	public void speakDocument() {
		speaker.speak(text);

	}

	
	
	
	/**
	 * This method narrates the {@link Document} with the {@link EncodeMethod} it is
	 * currently set at. In addition if the boolean provided is True the Document
	 * will be narrated in reverse order.
	 * 
	 * @param reverse A boolean to determine whether the document will be narrated
	 *                in reverse or not.
	 */
	public void customSpeakDocument(boolean reverse) {

		String lines = (reverse) ? reverseText(encodeMethod.encode(text)) : encodeMethod.encode(text);

		speaker.speak(lines);
	}

	
	
	
	/**
	 * This method narrates a specific line of the document with the
	 * {@link Encodemethod} the Document is currently set at.In addition if the
	 * boolean provided is True the line of the document will be narrated in reverse
	 * order. <br>
	 * If the int provided is greater than the line the document has this method has
	 * no effect.
	 * 
	 * @param lineNum The line to be narrated.
	 * @param reverse A boolean to determine whether the document will be narrated
	 *                in reverse or not.
	 */
	public void customSpeakLine(int lineNum, boolean reverse) {	
			String line = textLines.get(lineNum);
			line = reverse ? reverseText(line) : line;

			speaker.speak(encodeMethod.encode(line));

	}

	
	
	
	/**
	 * Stops an ongoing narration of the {@link Document}. <br>
	 * If the {@link Document#isSpeaking() isSpeaking()} method returns false at the
	 * point of using this method, then no action happens.
	 */
	public void stopSpeaking() {
		speaker.stopVoice();

	}


	// ================================================

	
	
	
	
	// =================================
	// ==========SETTERS
	// ================================

	/**
	 * This method changes the title of the Document.
	 * 
	 * @param title A String to be set as the title of the document.
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	
	
	
	/**
	 * This method changes the author of the Document.
	 * 
	 * @param author A String to be set as the author of the document.
	 */
	public void setAuthor(String author) {
		this.author = author;
	}

	
	
	
	/**
	 * This method changes {@link EncodeMethod} of the Document.
	 * 
	 * @param encodeMethod An EncodeMethod instance to be set as the current
	 *                     encoding method.
	 */
	public void setEncode(EncodeMethod encodeMethod) {
		this.encodeMethod = encodeMethod;
	}

	
	
	
	
	/**
	 * This method changes the created {@link LocalDateTime} of the document.
	 * 
	 * @param dateCreated A LocalDateTime to be set as the creation date of the
	 *                    document.
	 */
	public void setDateCreated(LocalDateTime dateCreated) {
		this.dateCreated = dateCreated;
		dateModified = dateCreated;
	}

	
	
	
	
	/**
	 * This method changes the pitch of the document's {@link ITTSApi}.
	 * 
	 * @param hertz A float to be set as the pitch value of the ITTSApi.
	 */
	public void setPitch(Float hertz) {
		speaker.setPitch(hertz);
	}

	
	
	
	
	/**
	 * This method changes the words per minute of the document's {@link ITTSApi}.
	 * 
	 * @param wpm A float to be set as the speak rate value of the ITTSApi.
	 */
	public void setRate(Float wpm) {
		speaker.setRate(wpm);
	}

	
	
	
	
	/**
	 * This method changes the volume of the document's {@link ITTSApi}.
	 * 
	 * @param volume A volume to be set as the pitch value of the ITTSApi.
	 */
	public void setVolume(Float volume) {

		speaker.setVolume(volume);

	}

	
	
	
	
	/**
	 * Sets the text of the Document. In addition this method updates the last
	 * modified date of the document.
	 * 
	 * @param text A string represeting the text to set the Document to.
	 */
	public void setText(String text) {
		if(text==null) {
			text = "";
		}
		this.text = text;
		setLineText();
		dateModified = LocalDateTime.now(ZoneId.systemDefault());

	}

	
	// ==========================================================

	
	
	
	
	
	// ============================
	// =======GETTERS
	// ============================

	
	
	
	
	/**
	 * 
	 * @return A string representing the current title of the Document.
	 */
	public String getTitle() {
		return title;
	}

	

	
	/**
	 * 
	 * @return A string representing the current author of the Document.
	 */
	public String getAuthor() {
		return author;
	}

	
	
	
	/**
	 * 
	 * @return A {@link LocalDateTime} representing the date of creation of the
	 *         Document.
	 */
	public LocalDateTime getDateCreated() {
		return dateCreated;
	}

	
	
	
	
	/**
	 * 
	 * @return A {@link LocalDateTime} representing the date the Document was last
	 *         modified.
	 */
	public LocalDateTime getDateModified() {
		return dateModified;
	}

	
	
	
	
	/**
	 * 
	 * @return A float representing the pitch of the {@link TTSApi}.
	 */
	public float getPitch() {
		return speaker.getPitch();
	}

	
	
	
	
	
	/**
	 * 
	 * @return A float representing the word per minute of the {@link TTSApi}.
	 */
	public float getRate() {
		return speaker.getRate();
	}

	
	
	
	
	/**
	 * 
	 * @return A float representing the volume of the {@link TTSApi}.
	 */
	public float getVolume() {
		return speaker.getVolume();
	}

	
	
	
	
	/**
	 *
	 * @return True if there is an ongoing narration. False otherwise.
	 * 
	 */
	public boolean isSpeaking() {
		return speaker.isSpeaking();
	}

	
	
	
	
	
	/**
	 * This method return the text that is being currently spoken.
	 * <p>
	 * This text might be different from the one the document is currently set
	 * at.<br>
	 * For example this method will return the text in reverse if the
	 * {@link Document#customSpeakDocument(boolean) customSpeakDocument(boolean)} is
	 * called with a true parameter as the document is narrated in reverse order.
	 * <p>
	 * <b>Note:</b> If there was an ongoing narration but at the moment this method
	 * is executed the narration has ended, this method will return an empty string
	 * as no text is being narrated.
	 * 
	 * @return The text that is being narrated. If no text is narrated an empty
	 *         String is return instead.
	 */
	public String getSpeakedText() {
		return speaker.getSpeakedText();
	}

	
	
	
	
	
	/**
	 * 
	 * @return A string representing the Document's text.
	 */
	public String getText() {
		return text;
	}

	
	
	
	/**
	 * This method returns a line of the document's text.
	 * <p>
	 * Will throw an {@link IndexOutOfBoundsException} if the line requested is less
	 * than 0 or greater than the lines available.
	 * 
	 * @param num The number of the line.
	 * @return The line of the document the int parameter indicated.
	 */
	public String getLine(int num) {
		return textLines.get(num);
	}

	
	
	
	
	/**
	 * 
	 * @return the {@link EncodeMethod} the document currently has.
	 */
	public EncodeMethod getEncodeMethod() {
		return encodeMethod;
	}

	
	
	// ==========================================================

	
	
	
	
	
	
	// =========================
	// ====Private methods
	// =========================

	
	
	
	
	
	
	// logic behind reversing a string
	private String reverseText(String text) {

		StringBuilder sb = new StringBuilder();

		// break the text each time there is a new line
		List<String> lines = Arrays.asList(text.split("\\r?\\n"));

		ListIterator<String> it = lines.listIterator(lines.size());

		// iterate through lines in reverse
		while (it.hasPrevious()) {
			String line = it.previous();

			// break in every white space
			List<String> words = Arrays.asList(line.split(" "));

			ListIterator<String> iterator = words.listIterator(words.size());

			// iterate white spaces in reverse
			while (iterator.hasPrevious()) {
				sb.append(iterator.previous() + " ");
			}

			// delete the last white space
			sb.deleteCharAt(sb.length() - 1);
			sb.append("\n");
		}
		// delete the last new line
		sb.deleteCharAt(sb.length() - 1);
		return sb.toString();

		// TODO: refactor not to lose lines
		// Arrays.stream(text.split("\\s+")).collect(Collectors.toCollection(LinkedList::new)).descendingIterator()
		// .forEachRemaining((a) -> sb.append(a + " "));
		// String finalText = sb.toString();

	}

	
	
	
	// based on the text the document has
	// it creates the lines
	private void setLineText() {
		String[] arrayLines = text.split("\n");
		textLines = Arrays.asList(arrayLines);
	}

	// ===================================================================
}
