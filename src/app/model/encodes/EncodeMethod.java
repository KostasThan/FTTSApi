package app.model.encodes;

/**
 * Encode methods are methods that map every letter of the alphabet to another,
 * thus decrypting the message. The receiver,knowing the encoding used, can
 * decrypt the message following the reverse steps of the said encode method.
 * 
 *
 */
public interface EncodeMethod {

	/**
	 * Encodes the given text acorrding to the method used.
	 * 
	 * @param text The text to be encoded.
	 * @return The encoded text.
	 */
	public String encode(String text);

}
