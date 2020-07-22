package tests.model.encodes;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import app.model.encodes.AtbashEncode;
import app.model.encodes.EncodeFactory;
import app.model.encodes.EncodeMethod;
import app.model.encodes.Rot13Encode;
import app.model.myenums.EncodeEnums;

class EncodeFactoryTest {

	@Test
	void testObjectCreation() {
		EncodeMethod atbash1 = EncodeFactory.getEncodeMethod(EncodeEnums.ATBASH);
		EncodeMethod atbash2 = EncodeFactory.getEncodeMethod("Atbash");

		assertAll("Atbash object creation",
				() -> assertEquals(atbash1.getClass(), AtbashEncode.class, "Was not an Atbash object"),
				() -> assertEquals(atbash2.getClass(), AtbashEncode.class, "Was not an Atbash object"));

		EncodeMethod rot13a = EncodeFactory.getEncodeMethod(EncodeEnums.ROT13);
		EncodeMethod rot13b = EncodeFactory.getEncodeMethod("Rot13");

		assertAll("Rot object creation",
				() -> assertEquals(rot13a.getClass(), Rot13Encode.class, "Was not a Rot13 object"),
				() -> assertEquals(rot13b.getClass(), Rot13Encode.class, "Was not a Rot13 object"));

	}

	@Test
	void multipleObjectTest() {
		EncodeMethod atbash1 = EncodeFactory.getEncodeMethod(EncodeEnums.ATBASH);
		EncodeMethod atbash2 = EncodeFactory.getEncodeMethod("Atbash");
		AtbashEncode atbash3 = AtbashEncode.getInstance();

		assertAll("Multiple Atbash objects", () -> assertTrue(atbash1.equals(atbash2)),
				() -> assertTrue(atbash3.equals(atbash1)));

		EncodeMethod rot13a = EncodeFactory.getEncodeMethod(EncodeEnums.ROT13);
		EncodeMethod rot13b = EncodeFactory.getEncodeMethod("Rot13");
		Rot13Encode rot13c = Rot13Encode.getInstance();

		assertAll("Multiple Rot13 objects", () -> assertTrue(rot13a.equals(rot13b)),
				() -> assertTrue(rot13c.equals(rot13b)));

	}

}
