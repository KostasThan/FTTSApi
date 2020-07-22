package tests.model.textNspeak;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.File;
import java.io.IOException;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.function.Function;

import javax.swing.JInternalFrame;
import javax.swing.JTextArea;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.RepetitionInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import app.model.encodes.EncodeFactory;
import app.model.myenums.EncodeEnums;
import app.model.textNspeak.Document;
import app.model.textNspeak.FakeTTS;
import app.model.textNspeak.TextAreaUtilities;
import tests.commands.external.RunForWindowsSeperator;

class TestTextAreaUtilities {

	private JTextArea textArea;
	private JInternalFrame frame;
	private Document doc;
	private TextAreaUtilities editor;

	@BeforeEach
	void setUp() {
		textArea = new JTextArea();
		frame = new JInternalFrame();
		doc = new Document(new FakeTTS());
		editor = new TextAreaUtilities(frame, textArea, doc);
	}

	@Nested
	@DisplayName("Test create new document method")
	class testCreate {

		@Test
		@DisplayName("Test creation of the doc")
		void testCreation() {
			// create a title and author
			String title = "title";
			String author = "author";

			editor.create(title, author);

			// chech if they match
			assertEquals(title, doc.getTitle());
			assertEquals(author, doc.getAuthor());

			// cannot change the title variable because it must be (efectively) final for
			// the lamda

			// create new title and author
			String newTitle = "new title";
			String newAuthor = "new author";

			editor.create(newTitle, newAuthor);

			// make sure it changed
			assertEquals(newTitle, doc.getTitle());
			assertEquals(newAuthor, doc.getAuthor());

			editor.create(newTitle, newAuthor);

			// make sure its the same
			assertEquals(newTitle, doc.getTitle());
			assertEquals(newAuthor, doc.getAuthor());
		}

		@Test
		@DisplayName("Test text area contents")
		void testTextAreaContents() {
			// create a title and author
			String title = "title";
			String author = "author";

			editor.create(title, author);

			// this the format that is used
			// "Title"
			// Written by: "Author"
			String result = "\t\t\"title\"\nWritten by: author\n";
			// chech if they match

			assertEquals(result, textArea.getText());

		}

	}

	@Nested
	@DisplayName("Test open a file method")
	class testOpenFileMethod {
		private File file;

		@RunForWindowsSeperator
		@DisplayName("Test text area contents")
		void testTextArea() {

			file = new File("src\\resources\\testfiles\\open me.txt");
			String fileContents = "Opened";

			editor.openFile(file);

			assertEquals(fileContents, textArea.getText());

			file = new File("src\\resources\\testfiles\\multiline.txt");
			fileContents = "Line 1\nLine 2 line 2\nA 3 line text";

			editor.openFile(file);

			assertEquals(fileContents, textArea.getText());
		}

		@RunForWindowsSeperator
		@DisplayName("Test document contents")
		void testDocumentContents() {

			file = new File("src\\resources\\testfiles\\open me.txt");
			String fileContents = "Opened";

			editor.openFile(file);

			assertEquals(fileContents, doc.getText());

			// 1 line text
			assertEquals(fileContents, doc.getLine(0));

			file = new File("src\\resources\\testfiles\\multiline.txt");

			fileContents = "Line 1\nLine 2 line 2\nA 3 line text";

			editor.openFile(file);

			assertEquals(fileContents, doc.getText());

			// check each line
			assertEquals("Line 1", doc.getLine(0));

			assertEquals("Line 2 line 2", doc.getLine(1));

			assertEquals("A 3 line text", doc.getLine(2));

		}

		@RunForWindowsSeperator
		@DisplayName("Test window title")
		void testJInternalFrameTitle() {

			file = new File("src\\resources\\testfiles\\open me.txt");

			editor.openFile(file);

			// test if the title ends with the file's absolut path.
			// note the the title might be something like Text EDITOR - <<filePath>>
			// and thats why we test if it ends with that path
			assertTrue(frame.getTitle().endsWith(file.getAbsolutePath()));

			file = new File("src\\resources\\testfiles\\multiline.txt");

			editor.openFile(file);
			assertTrue(frame.getTitle().endsWith(file.getAbsolutePath()));

		}

	}

	// NOTE: if those tests run multiple times
	// they may fail as the newly created file creation timestamp will be the one
	// from the file in the cache
	@Nested
	@DisplayName("Test save methods")
	@TestMethodOrder(OrderAnnotation.class)
	@TestInstance(Lifecycle.PER_CLASS)
	class TestSaveMethods {
		private String filePath;
		private String filePath1 = "src\\resources\\testfiles\\new file.txt";
		private String filePath2 = "src\\resources\\testfiles\\another file.txt";

		@BeforeEach
		void setUp() {
			new File(filePath1).delete();
			new File(filePath2).delete();
		}

		// tests whethere the file was created
		@DisplayName("Test file creation")
		@RunBothMethods
		@Order(1)
		void testFileCreation(RepetitionInfo repetitionInfo) {

			// assing filepath to filepath 1
			filePath = filePath1;

			// create a file intance with the given path
			File f = new File(filePath1);

			// check if the response is true
			// it should always be true unless an exception is thrown
			assertTrue(runMethod(filePath, repetitionInfo.getCurrentRepetition()));

			// check if it is a file
			assertTrue(f.exists() && !f.isDirectory());

			// same for filePath2
			filePath = filePath2;

			f = new File(filePath2);

			f = new File(filePath2);

			assertTrue(runMethod(filePath, repetitionInfo.getCurrentRepetition()));
			assertTrue(f.exists() && !f.isDirectory());

		}

		// tests for the correct time of creation
		@RunBothMethods
		@Order(2)
		void testTimeOfCreation(RepetitionInfo repetitionInfo) {

			// for the first file path
			filePath = filePath1;

			// create the file again
			runMethod(filePath, repetitionInfo.getCurrentRepetition());

			// find the newly created path
			Path path = new File(filePath1).toPath();

			LocalDateTime dateActuall = getFileAttributes(path, BasicFileAttributes::creationTime);
			LocalDateTime dateExpected = java.time.LocalDateTime.now();

			assertEquals(dateExpected.getDayOfMonth(), dateActuall.getDayOfMonth(), "Wrong day");

			assertEquals(dateExpected.getMonth(), dateActuall.getMonth(), "Wrong month");

			// hours minutes and secods are too fragile to test as there is some time
			// diference before capturing the actuall second and creation of the file
			// for example we might create the file at 11:59 and capture the time at 00::00
			// failing the all the hour minute second tests.

			//assertEquals(dateExpected.getMinute(), dateActuall.getMinute());
		}

		@RunBothMethods
		@Order(3)
		// tests whether the file has the correct contents
		void testFileContents(RepetitionInfo repetitionInfo) {

			// create some text
			String fileContents = "This should be the contents\nof the file";

			// add it to the text area
			textArea.setText(fileContents);

			// set the filepath
			filePath = filePath1;

			// execute the command
			runMethod(filePath, repetitionInfo.getCurrentRepetition());

			
			// make sure the contents are what they are supposed to
			assertEquals(fileContents, readFileContentes(filePath1));

			// samewith another text
			fileContents = "Another file\ncontent.This has many .... fullstops and ???? questio n ma rks";

			textArea.setText(fileContents);
			filePath = filePath1;

			runMethod(filePath, repetitionInfo.getCurrentRepetition());

			assertEquals(fileContents, readFileContentes(filePath1));
		}

		// NOTE needs 1 minute to run
		// Tests whether the last modified time stamp is correct
		@RunBothMethods
		@Order(4)
		@Disabled
		void testLastModified(RepetitionInfo repetitionInfo) {

			// set the file path
			filePath = filePath1;
			runMethod(filePath, repetitionInfo.getCurrentRepetition());

			// execute the command
			// at this point the file is created

			try {

				Thread.sleep(60001); // sleep for 1 minute and 1 millisecond
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// execute the command again
			runMethod(filePath, repetitionInfo.getCurrentRepetition());

			Path createdFile = new File(filePath).toPath();

			LocalDateTime creationDate = getFileAttributes(createdFile, BasicFileAttributes::creationTime);
			LocalDateTime lastModifiedDate = getFileAttributes(createdFile, BasicFileAttributes::lastModifiedTime);

			// at this point it should be at leas one minute differece between created and
			// modified
			assertTrue(lastModifiedDate.isAfter(creationDate));
		}

		@RunBothMethods
		@Order(4)
		// tests whether the atomic boolean
		// that represents the result is correct
		void testResult(RepetitionInfo repetitionInfo) {

			// set the file path
			filePath = filePath1;

			// expect true as result
			assertTrue(runMethod(filePath, repetitionInfo.getCurrentRepetition()));
		}

		private boolean runMethod(String path, int method) {
			if (method == 0) {
				editor.setSelectedFile(path);
				return editor.save();
			} else {
				return editor.saveAs(path);
			}
		}

	}

	/// =========METHODS==================== ///
	private String readFileContentes(String stringPath) {

		try {
			return Files.readString(new File(stringPath).toPath(), StandardCharsets.US_ASCII);
		} catch (IOException e) {
			return null;
		}
	}

	private LocalDateTime getFileAttributes(Path path, Function<BasicFileAttributes, FileTime> function) {
		BasicFileAttributes attributes = null;
		try {
			// get the attributes
			attributes = Files.readAttributes(path, BasicFileAttributes.class);
		} catch (IOException e) {
			// if there is an error fail the test
			fail("File could not be read" + path.toString());
		}

		LocalDateTime dateActuall = LocalDateTime.ofInstant(Instant.ofEpochMilli(function.apply(attributes).toMillis()),
				ZoneId.systemDefault());
		return dateActuall;
	}

	@Nested
	@DisplayName("Test text encoding method")
	class testEncodeTextMethodS {
		String fox = "The quick brown fox jumped over the lazy..??!!! dog";

		@DisplayName("Test encode all text method")
		@ParameterizedTest
		@EnumSource(value = EncodeEnums.class, names = "DEFAULT", mode = EnumSource.Mode.EXCLUDE)
		void testEncodeAllText(EncodeEnums encodeEnum) {

			textArea.setText(fox);
			editor.encodeText(EncodeFactory.getEncodeMethod(encodeEnum));

			// the text of the text area
			// must be the string we had at the text area
			// encoded with the said method
			assertEquals(EncodeFactory.getEncodeMethod(encodeEnum).encode(fox), textArea.getText());

		}

		@DisplayName("Test encode highlighted method")
		@ParameterizedTest
		@EnumSource(value = EncodeEnums.class, names = "DEFAULT", mode = EnumSource.Mode.EXCLUDE)
		void testEncodeHighligtedMethod(EncodeEnums encodeEnum) {

			textArea.setText(fox);

			// selects the "The quick"
			textArea.select(0, 9);

			editor.encodeSelected(EncodeFactory.getEncodeMethod(encodeEnum));

			String encodedText = EncodeFactory.getEncodeMethod(encodeEnum).encode("The quick");
			String result = encodedText + " brown fox jumped over the lazy..??!!! dog";

			// make sure the result is the same
			assertEquals(result, textArea.getText());

			// this method leaves the same position of selected test as it was for the user
			// to see the effects

			// make sure the selected text is the same
			assertEquals(encodedText, textArea.getSelectedText());
		}
	}



	@Nested
	@DisplayName("Test is savedMethod")
	class TestIsSavedMethod {
		
		@Test
		void testMethod() {
			
			
			//upon creation
			//there is nothing on the screen
			//so it is saved
			
			assertTrue(editor.isSaved());
			
			//change the text
			textArea.setText("text");
			
			//now it must not be saved
			assertFalse(editor.isSaved());
			
			//if we now save
			editor.saveAs("src\\resources\\testfiles\\another file.txt");
			
			//it must be saved
			assertTrue(editor.isSaved());
			
			
			//change the text
			textArea.setText("another one");
			
			//and set it must to original
			textArea.setText("text");
			
			//the final text matches the saved one so it must be saved
			assertTrue(editor.isSaved());
			
			
		}
		
		
	}

	/// =========Interface==================== ///

	@Target({ ElementType.TYPE, ElementType.METHOD })
	@Retention(RetentionPolicy.RUNTIME)
	@EnabledIfSystemProperty(named = "file.separator", matches = "\\\\")
	@RepeatedTest(2)
	public @interface RunBothMethods {
	}

}
