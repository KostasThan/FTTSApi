package tests.commands.external;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import app.commands.external.CustomSpeakDocumentCommand;
import app.commands.external.CustomSpeakLineCommand;
import app.commands.external.SpeakDocumentCommand;
import app.model.textNspeak.Document;
import app.model.textNspeak.FakeTTS;

@TestInstance(Lifecycle.PER_CLASS)
class TestSpeakCommands {

	private Document doc;
	private FakeTTS fakeSpeaker;
	private String sample1 = "A multiline\nsample string\nline 3";
	private String sample2 = "line1\nline2 line2\nthis is line 3";
	private boolean reverse;

	@BeforeEach
	void setUp() {
		fakeSpeaker = new FakeTTS();
		doc = new Document(fakeSpeaker);
	}

	@Nested
	@DisplayName("Test commands using the whole document")
	class testSpeakDocumentCommand {

		SpeakDocumentCommand command = new SpeakDocumentCommand(() -> doc);
		CustomSpeakDocumentCommand noReverseCommand = new CustomSpeakDocumentCommand(() -> doc, () -> false);
		CustomSpeakDocumentCommand reverseCommand = new CustomSpeakDocumentCommand(() -> doc, () -> true);

		@Test
		@DisplayName("Test speak document command")
		void speakDocumentTest() {
			// create a text
			String text = "How \t many are there \n.\t WOW!";

			// set it to doc
			doc.setText(text);

			// execute the command that speaks the document
			command.execute();

			assertEquals(text, fakeSpeaker.getText());

			// same
			text = "this is a really long message";
			doc.setText(text);
			command.execute();

			assertEquals(text, fakeSpeaker.getText());

			// rinse and repeat
			text = "Another long\n message";
			doc.setText(text);
			command.execute();

			assertEquals(text, fakeSpeaker.getText());
		}

		@Test
		@DisplayName("Test custom speak document without reverse")
		void customSpeakNoReverseCommandTest() {

			doc.setText(sample1);

			command.execute();
			assertEquals(sample1, fakeSpeaker.getText());

			doc.setText(sample2);

			command.execute();
			assertEquals(sample2, fakeSpeaker.getText());

		}

		@Test
		@DisplayName("Test custom speak document withe reverse")
		void customSpeakReverseCommandTest() {

			// test given samples
			doc.setText(sample1);

			noReverseCommand.execute();
			assertEquals(sample1, fakeSpeaker.getText());

			reverseCommand.execute();
			assertEquals("3 line\nstring sample\nmultiline A", fakeSpeaker.getText());

			doc.setText(sample2);

			noReverseCommand.execute();
			assertEquals(sample2, fakeSpeaker.getText());

			reverseCommand.execute();
			assertEquals("3 line is this\nline2 line2\nline1", fakeSpeaker.getText());

			///// more tests
			// set both texts
			String text = "1 2 3 4 5 6 7 8 9";
			String reversedText = "9 8 7 6 5 4 3 2 1";

			// set the text of the document
			doc.setText(text);

			// execute the commmand
			reverseCommand.execute();

			// equal with reverse text since reverse = true
			assertEquals(reversedText, fakeSpeaker.getText());

			// rinse and repeat
			text = "123 456 789";
			reversedText = "789 456 123";

			doc.setText(text);
			reverseCommand.execute();

			assertEquals(reversedText, fakeSpeaker.getText());

			text = "i message that i am going to have trouble. reversing";
			reversedText = "reversing trouble. have to going am i that message i";

			doc.setText(text);
			reverseCommand.execute();

			assertEquals(reversedText, fakeSpeaker.getText());

			text = "change\nline";
			doc.setText(text);
			reverseCommand.execute();

			assertEquals("line\nchange", fakeSpeaker.getText());

			text = "this is\na text that\nchanges many lines";
			doc.setText(text);

			reverseCommand.execute();

			assertEquals("lines many changes\nthat text a\nis this", fakeSpeaker.getText());
		}

		@Test
		@DisplayName("Test response to supplier")
		void testCustomSpeakDocumentMixed() {
			// check if the commands responds to the supplier changes
			String text = "Hello this is a text";
			String reverseText = "text a is this Hello";

			CustomSpeakDocumentCommand command = new CustomSpeakDocumentCommand(() -> doc, () -> reverse);
			doc.setText(text);

			// false at start
			reverse = false;

			// executed
			command.execute();

			// test
			assertEquals(text, fakeSpeaker.getText());

			// true after
			reverse = true;

			// executed
			command.execute();

			// test
			assertEquals(reverseText, fakeSpeaker.getText());

		}

	}

	@Nested
	@DisplayName("Test commands using lines of document")
	class testLineCommands {

		private int lineNum;
		private CustomSpeakLineCommand command = new CustomSpeakLineCommand(() -> doc, () -> lineNum, () -> reverse);
		private String text = "line 0\nline 1\nline 2";
		private String line0 = "line 0";
		private String line1 = "line 1";
		private String line2 = "line 2";

		@Test
		@DisplayName("Test lines with no reverse")
		void testLinesNoReverse() {
			reverse = false;
			doc.setText(text);

			lineNum = 0;
			command.execute();

			assertEquals(line0, fakeSpeaker.getText());

			lineNum = 1;
			command.execute();

			assertEquals(line1, fakeSpeaker.getText());

			lineNum = 2;
			command.execute();

			assertEquals(line2, fakeSpeaker.getText());

		}

		@Test
		@DisplayName("Test lines with reverse")
		void testLinesWithReverse() {
			reverse = true;
			String reverseLine0 = "0 line";
			String reverseLine1 = "1 line";
			String reverseLine2 = "2 line";
			doc.setText(text);

			lineNum = 0;
			command.execute();

			assertEquals(reverseLine0, fakeSpeaker.getText());

			lineNum = 1;
			command.execute();

			assertEquals(reverseLine1, fakeSpeaker.getText());

			lineNum = 2;
			command.execute();

			assertEquals(reverseLine2, fakeSpeaker.getText());

			// NOTE: the "long..with" is typo on purpose.. no space between the fullstops
			// and the word means it is the same word. after a fullstop there must be a
			// space
			String lines = "line1\nline2 is really long...with fullstops.?? just to cover all the basis";
			String result = "basis the all cover to just fullstops.?? long...with really is line2";

			doc.setText(lines);

			lineNum = 1;
			command.execute();

			assertEquals(result, fakeSpeaker.getText());

		}

	}
}