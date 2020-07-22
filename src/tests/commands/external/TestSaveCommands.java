package tests.commands.external;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.stream.Stream;

import javax.swing.JInternalFrame;
import javax.swing.JTextArea;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import app.commands.external.ExternalCommand;
import app.commands.external.SaveAsCommand;
import app.commands.external.SaveCommand;
import app.model.textNspeak.Document;
import app.model.textNspeak.FakeTTS;
import app.model.textNspeak.TextAreaUtilities;

/**
 * Tests the two Save Command.
 * <li>{@link SaveCommand Save} command</li>
 * <li>{@link SaveCommand SaveAs} command</li>
 */

@TestInstance(Lifecycle.PER_CLASS)
@TestMethodOrder(OrderAnnotation.class)
@DisplayName("Test save commands")
class TestSaveCommands {

	private JTextArea textArea;
	private JInternalFrame frame;
	private Document doc;
	private TextAreaUtilities textAreaUtilities;
	private String filePath;
	private AtomicBoolean atomicBoolean;
	private String filePath1;
	private String filePath2;

	@BeforeAll
	void setUpBeforeClass() throws Exception {
		textArea = new JTextArea();
		frame = new JInternalFrame();
		doc = new Document(new FakeTTS());
		textAreaUtilities = new TextAreaUtilities(frame, textArea, doc);
		atomicBoolean = new AtomicBoolean(false);
		filePath1 = "src\\resources\\testfiles\\new file.txt";
		filePath2 = "src\\resources\\testfiles\\another file.txt";
	}

	@AfterEach
	void afterEach() {
		new File(filePath1).delete();
		new File(filePath2).delete();
	}

	// We need the tests to run in order.
	// With file cretions there might be a dependency on the tests.
	// NOTE: if those tests run multiple times
	// they may fail as the newly created file creation timestamp will be the one
	// from the file in the cache

	// tests whethere the file was created
	@RunBothCommands
	@Order(1)
	void testFileCreation(ExternalCommand command) {

		// assing filepath to filepath 1
		filePath = filePath1;
		setUpSaveCommand(command, filePath);

		command.execute();

		// create a file intance with the given path
		File f = new File(filePath1);

		// check if it is a file
		assertTrue(f.exists() && !f.isDirectory());

		// check if the response is true
		// it should always be true unless an exception is thrown
		assertTrue(atomicBoolean.get());

		// same for filePath2
		filePath = filePath2;
		setUpSaveCommand(command, filePath);
		f = new File(filePath2);

		command.execute();

		f = new File(filePath2);
		assertTrue(f.exists() && !f.isDirectory());
		assertTrue(atomicBoolean.get());

	}

	// tests for the correct time of creation
	@RunBothCommands
	@Order(2)
	void testTimeOfCreation(ExternalCommand command) {

		// for the first file path
		filePath = filePath1;
		setUpSaveCommand(command, filePath);

		// create the file again
		command.execute();

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

		assertEquals(dateExpected.getMinute(), dateActuall.getMinute());
	}

	@RunBothCommands
	@Order(3)
	// tests whether the file has the correct contents
	void testFileContents(ExternalCommand command) {

		// create some text
		String fileContents = "This should be the contentes\nof the file";

		// add it to the text area
		textArea.setText(fileContents);

		// set the filepath
		filePath = filePath1;
		setUpSaveCommand(command, filePath);

		// execute the command
		command.execute();

		// make sure the contents are what they are supposed to
		assertEquals(fileContents, readFileContentes(filePath1));

		// samewith another text
		fileContents = "Another file\ncontent.This has many .... fullstops and ???? questio n ma rks";

		textArea.setText(fileContents);
		filePath = filePath1;
		command.execute();

		assertEquals(fileContents, readFileContentes(filePath1));
	}

	// NOTE needs 1 minute to run
	// Tests whether the last modified time stamp is correct
	@RunBothCommands
	@Order(4)
	void testLastModified(ExternalCommand command) {

		// set the file path
		filePath = filePath1;
		setUpSaveCommand(command, filePath);

		// execute the command
		// at this point the file is created
		command.execute();

		// sleep for 5 seconds
		try {

			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// execute the command again
		command.execute();

		Path createdFile = new File(filePath).toPath();

		LocalDateTime creationDate = getFileAttributes(createdFile, BasicFileAttributes::creationTime);
		LocalDateTime lastModifiedDate = getFileAttributes(createdFile, BasicFileAttributes::lastModifiedTime);

		// at this point it should be at least one minute differece between created and
		// modified
		assertTrue(lastModifiedDate.isAfter(creationDate));
	}

	@RunBothCommands
	@Order(4)
	// tests whether the atomic boolean
	// that represents the result is correct
	void testResult(ExternalCommand command) {

		// set the file path
		filePath = filePath1;
		setUpSaveCommand(command, filePath);

		// execute the command
		command.execute();

		assertTrue(atomicBoolean.get());

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
	
	// used in the annotation
	private Stream<ExternalCommand> getCommands() {
		return Stream.of(new SaveCommand(textAreaUtilities, atomicBoolean),
				new SaveAsCommand(textAreaUtilities, () -> filePath, atomicBoolean));
	}

	private void setUpSaveCommand(ExternalCommand command, String selectedFile) {
		if (command instanceof SaveCommand) {
			textAreaUtilities.setSelectedFile(selectedFile);
		}
	}

	/// =========Interface==================== ///

	@Target({ ElementType.TYPE, ElementType.METHOD })
	@Retention(RetentionPolicy.RUNTIME)
	@EnabledIfSystemProperty(named = "file.separator", matches = "\\\\")
	@ParameterizedTest(name = "command =''{0}''")
	@MethodSource("getCommands")
	@interface RunBothCommands {

	}

}
