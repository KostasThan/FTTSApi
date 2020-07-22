package app.model.encodes;

/**
 * This class is responsible for implementing the logic behind encoding a text
 * according to the Rot13 encode.
 * <p>
 * The Rot13 encode shifts every letter of the alphabet by 13 letters. If there
 * are no thirteen letters to be shifted over the count will resume from the
 * first letter. For example:
 * <li>A -> N</li>
 * <li>B -> O</li>
 * <li>x -> k</li>
 * <li>z -> m</li>
 * <p>
 * <b>Note</b>: Only the letters of the alphabet are converted. Numbers,special
 * characters,digits etc.. are left intact.
 * 
 *
 */
public class Rot13Encode extends SkeletonEncode {

	private static Rot13Encode INSTANCE;

	private Rot13Encode() {
	};

	/**
	 * This method creates and instnace of {@link Rot13Encode} encode if none exists
	 * and then returns it. <br>
	 * If an instance is already created it returns that instance without creating
	 * another.
	 * 
	 * @return The one and only instance of Rot13Encode
	 */
	public static Rot13Encode getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new Rot13Encode();
		}
		return INSTANCE;
	}

	
	protected char mapChar(char ch) {
		if (ch >= 'a' && ch <= 'z') {

			// the amount the element need to be shifted from it's starting position
			int shiftAmount = (ch - 'a' + 13) % 26;

			// starting pos + shiftAmount
			ch = (char) ('a' + shiftAmount);
		} else if (ch >= 'A' && ch <= 'Z') {

			// same
			int shiftAmount = (ch - 'A' + 13) % 26;

			ch = (char) ('A' + shiftAmount);
		}
		
		//if we got here the c does not belong to the english alphabet
		return ch;
	}

}
