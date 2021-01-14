package tests.model.textNspeak;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.EnumSource.Mode;

import app.model.encodes.EncodeFactory;
import app.model.encodes.EncodeMethod;
import app.model.myenums.EncodeEnums;
import app.model.textNspeak.Document;
import app.model.textNspeak.FakeTTS;

@DisplayName("Test Document Speak Methods and commands")
class DocumentSpeakMethodsTest {
	private Document doc;
	private FakeTTS fakeSpeaker;
	private String sample1 = "A multiline\nsample string\nline 3";
	private String sample2 = "line1\nline2 line2\nthis is line 3";
	private String sample1Reversed = "3 line\nstring sample\nmultiline A";
	private String sample2Reversed = "3 line is this\nline2 line2\nline1";

	@BeforeEach
	void setUp() throws Exception {
		fakeSpeaker = new FakeTTS();
		doc = new Document(fakeSpeaker, null);
	}

	@Nested
	@DisplayName("Test Methods That Use Whole Document")
	class DocumentMethods {

		@Test
		@DisplayName("Speak whole document")
		void testSpeakDocument() {
			
			//set the text to sample1
			doc.setText(sample1);
			
			//make it speak
			doc.speakDocument();
			
			//check if the text was passed correctly
			assertEquals(fakeSpeaker.getText(), sample1);
			
			//same
			doc.setText(sample2);		
			doc.speakDocument();
			assertEquals(fakeSpeaker.getText(), sample2);

		}

		@Test
		@DisplayName("Speak whole document in reverse")
		void testReverse() {
			
			//set it to first sample
			doc.setText(sample1);
			
			//reverse speak it
			doc.customSpeakDocument(true);

			//make sure it passed the correct text
			assertEquals(sample1Reversed, fakeSpeaker.getText());

			
			//same
			doc.setText(sample2);
			doc.customSpeakDocument(true);

			assertEquals(sample2Reversed, fakeSpeaker.getText());

		}

		//run for all encodes but the default
		@ParameterizedTest
		@EnumSource(value = EncodeEnums.class, names = "DEFAULT", mode = Mode.EXCLUDE)
		@DisplayName("Reverse with encodes")
		void testReverseWithEncodes(EncodeEnums encodeEnum) {
			
			//capture the encoding method
			EncodeMethod encodeMethod = EncodeFactory.getEncodeMethod(encodeEnum);
			
			//set it to the document encode method
			doc.setEncode(encodeMethod);

			//speak it
			doc.setText(sample1);
			
			//make sure it passed the correct text
			doc.customSpeakDocument(true);

			//same
			assertEquals(encodeMethod.encode(sample1Reversed), fakeSpeaker.getText());
			doc.setText(sample2);
			doc.customSpeakDocument(true);

			assertEquals(encodeMethod.encode(sample2Reversed), fakeSpeaker.getText());

		}

	}

	@Nested
	@DisplayName("Test Methods Thas Uses Specified Lines")
	class TestLineMethhods {

		@Test
		@DisplayName("Line Speak, no reverse or encode")
		void testLineSpeak() {
			//set it to a text
			doc.setText(sample1);

			//make it speak the first line withour reversing
			doc.customSpeakLine(0, false);
			
			//make sure it is the correct text
			assertEquals("A multiline", fakeSpeaker.getText());

			
			//same
			doc.customSpeakLine(1, false);
			assertEquals("sample string", fakeSpeaker.getText());

			
			//and again
			doc.customSpeakLine(2, false);
			assertEquals("line 3", fakeSpeaker.getText());

		}

		@ParameterizedTest
		@EnumSource(value = EncodeEnums.class, names = "DEFAULT", mode = Mode.EXCLUDE)
		@DisplayName("Line Speak with encode, no reverse")
		void testEncodeLineSpeak(EncodeEnums encodeEnum) {
			//capture the method
			EncodeMethod encodeMethod = EncodeFactory.getEncodeMethod(encodeEnum);
			
			
			doc.setText(sample1);
			doc.setEncode(encodeMethod);

			doc.customSpeakLine(0, false);
			assertEquals(encodeMethod.encode("A multiline"), fakeSpeaker.getText());

			doc.customSpeakLine(1, false);
			assertEquals(encodeMethod.encode("sample string"), fakeSpeaker.getText());

			doc.customSpeakLine(2, false);
			assertEquals(encodeMethod.encode("line 3"), fakeSpeaker.getText());

		}

		@Test
		@DisplayName("Line Speak with reverse, no encode")
		void testLineSpeakReverse() {
			doc.setText(sample1);

			doc.customSpeakLine(0, true);
			assertEquals("multiline A", fakeSpeaker.getText());

			doc.customSpeakLine(1, true);
			assertEquals("string sample", fakeSpeaker.getText());

			doc.customSpeakLine(2, true);
			assertEquals("3 line", fakeSpeaker.getText());

		}

		@ParameterizedTest
		@EnumSource(value = EncodeEnums.class, names = "DEFAULT", mode = Mode.EXCLUDE)
		@DisplayName("Line Speak with reverse and encode")
		void testLineSpeakEncodeReverse(EncodeEnums encodeEnum) {
			EncodeMethod encodeMethod = EncodeFactory.getEncodeMethod(encodeEnum);
			doc.setText(sample1);
			doc.setEncode(encodeMethod);

			doc.customSpeakLine(0, true);
			assertEquals(encodeMethod.encode("multiline A"), fakeSpeaker.getText());

			doc.customSpeakLine(1, true);
			assertEquals(encodeMethod.encode("string sample"), fakeSpeaker.getText());

			doc.customSpeakLine(2, true);
			assertEquals(encodeMethod.encode("3 line"), fakeSpeaker.getText());
		}
	}

}

///=========== interface =================///
