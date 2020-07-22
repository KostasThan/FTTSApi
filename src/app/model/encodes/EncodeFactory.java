package app.model.encodes;

import app.model.myenums.EncodeEnums;

/**
 * This class represents an {@link EncodeMethod} factory. Based on a String or
 * an {@link EncodeEnums} it will provide the respective EncodeMethod.
 *
 */
public class EncodeFactory {

	private EncodeFactory() {
	}

	/**
	 * 
	 * @param c
	 * @return
	 */
	public static EncodeMethod getEncodeMethod(EncodeEnums c) {
		switch (c) {
		case ATBASH:
			return AtbashEncode.getInstance();
		case ROT13:
			return Rot13Encode.getInstance();
		default:
			return DefaultEncode.getInstance();
		}

	}

	public static EncodeMethod getEncodeMethod(String encodeMethod) {
		switch (encodeMethod) {
		case "Atbash":
			return AtbashEncode.getInstance();
		case "Rot13":
			return Rot13Encode.getInstance();
		default:
			return DefaultEncode.getInstance();

		}
	}

}
