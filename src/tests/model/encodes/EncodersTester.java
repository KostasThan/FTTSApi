package tests.model.encodes;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import app.model.encodes.EncodeMethod;

class EncodersTester {

	private String[] upperLetters = { "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P",
			"Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
	private String[] encodedUpperLetters;

	private String[] lowerLetters = { "a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p",
			"q", "r", "s", "t", "u", "v", "w", "x", "y", "z" };
	private String[] encodedLowerLetters;

	private String fox = "The quick brown fox jumped over the lazy dog.";
	private String encodedFox;

	private EncodeMethod encodeMethod;


	void testUpperCase() {

		checkArrays(upperLetters, encodedUpperLetters);
		assertTrue(checkEncoder());

		for (int i = 0; i < upperLetters.length; i++) {

			assertEquals(encodeMethod.encode(upperLetters[i]), encodedUpperLetters[i]);
		}
	}


	void testLowerCase() {
		checkArrays(lowerLetters, encodedLowerLetters);
		assertTrue(checkEncoder());

		for (int i = 0; i < lowerLetters.length; i++) {

			assertEquals(encodedLowerLetters[i], encodeMethod.encode(lowerLetters[i]));
		}
	}

	
	void testSumbols() {
		assertAll(() -> assertEquals("(", encodeMethod.encode("(")), () -> assertEquals(")", encodeMethod.encode(")")),
				() -> assertEquals("*", encodeMethod.encode("*")), () -> assertEquals("+", encodeMethod.encode("+")),
				() -> assertEquals(",", encodeMethod.encode(",")), () -> assertEquals("-", encodeMethod.encode("-")),
				() -> assertEquals(".", encodeMethod.encode(".")), () -> assertEquals("/", encodeMethod.encode("/")),
				() -> assertEquals(":", encodeMethod.encode(":")), () -> assertEquals(";", encodeMethod.encode(";")),
				() -> assertEquals("<", encodeMethod.encode("<")), () -> assertEquals("=", encodeMethod.encode("=")),
				() -> assertEquals(">", encodeMethod.encode(">")), () -> assertEquals("?", encodeMethod.encode("?")),
				() -> assertEquals("@", encodeMethod.encode("@")));
	}

	
	void testNumbers() {
		assertAll(() -> assertEquals("0", encodeMethod.encode("0")), () -> assertEquals("1", encodeMethod.encode("1")),
				() -> assertEquals("2", encodeMethod.encode("2")), () -> assertEquals("3", encodeMethod.encode("3")),
				() -> assertEquals("4", encodeMethod.encode("4")), () -> assertEquals("5", encodeMethod.encode("5")),
				() -> assertEquals("6", encodeMethod.encode("6")), () -> assertEquals("7", encodeMethod.encode("7")),
				() -> assertEquals("8", encodeMethod.encode("8")), () -> assertEquals("9", encodeMethod.encode("9")));
	}

	
	void testFox() {

		assertEquals(encodedFox, encodeMethod.encode(fox));
	}

	public void setEncodedUpperLetters(String[] encodedUpperLetters) {
		this.encodedUpperLetters = encodedUpperLetters;
	}

	public void setLowerEncodedLetters(String[] encodedLowerLetters) {
		this.encodedLowerLetters = encodedLowerLetters;
	}

	public void setEncodeMethod(EncodeMethod encoder) {
		this.encodeMethod = encoder;
	}

	public void setEncodedFox(String encodedFox) {
		this.encodedFox = encodedFox;
	}

	private void checkArrays(String[] array1, String[] array2) {
		assertTrue(array1 != null);
		assertTrue((array1.length == array2.length));
		assertEquals(array1.length, array2.length);
	}

	private boolean checkEncoder() {
		return encodeMethod != null;
	}

}
