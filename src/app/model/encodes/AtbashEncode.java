package app.model.encodes;

/**
 * This class is responsible for implementing the logic behind encoding a text
 * according to the Atbash encode.
 * <p>
 * The Atbash encode converts the first letter of the alphabet to the last. The
 * second to the second to last and so on and so forth. For example:
 * <li>A -> Z</li>
 * <li>B -> Y</li>
 * <li>c -> x</li>
 * <p>
 * <b>Note</b>: Only the letters of the alphabet are converted. Numbers,special
 * characters,digits etc.. are left intact.
 * 
 *
 */
public class AtbashEncode extends SkeletonEncode {

	private static AtbashEncode INSTANCE;

	private AtbashEncode() {
	};

	/**
	 * This method creates and instnace of {@link AtbashEncode} encode if none
	 * exists and then returns it. <br>
	 * If an instance is already created it returns that instance without creating
	 * another.
	 * 
	 * @return The one and only instance of AtbashEncode
	 */
	public static AtbashEncode getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new AtbashEncode();
		}
		return INSTANCE;
	}

	@Override
	protected char mapChar(char c) {

		
		if (c >= 'a' && c <= 'z') {
			
			//return the distance `z` to c plus the `a`
			return (char) ('z' - c + 'a');
			
			
		} else if (c >= 'A' && c <= 'Z') {
			
			//return the distance `Z` to c plus the `A`
			return (char) ('Z' - c + 'A');
		} else {
			
			//if we got here the c does not belong to the english alphabet
			return c;
		}

	}

}
