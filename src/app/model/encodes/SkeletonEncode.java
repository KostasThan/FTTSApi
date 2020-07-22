package app.model.encodes;

/**
 * This abstract class implements the basic logic of an encoding method. It
 * converts a String to a Stream of Characters and maps every character
 * according to the encoding method implemented.
 * 
 *
 */
public abstract class SkeletonEncode implements EncodeMethod {

	/**
	 * Encodes a String based on the {@link EncodeMethod} implemented.
	 * 
	 */
	public String encode(String text) {

		StringBuilder sb = new StringBuilder();

		text.chars()
			.mapToObj(c -> (char) c)
			.map(c -> mapChar(c))
			.forEach(sb::append);

		return sb.toString();
	}

	/**
	 * Encoded a character given according to the {@link EncodeMethod} implemented.
	 * @param c The character to encode.
	 * @return The character encoded.
	 */
	protected abstract char mapChar(char c);

}
