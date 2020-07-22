package tests.commands.external;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;

import javax.swing.JInternalFrame;
import javax.swing.JTextArea;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import app.commands.external.OpenFileCommand;
import app.model.textNspeak.Document;
import app.model.textNspeak.FakeTTS;
import app.model.textNspeak.TextAreaUtilities;


@TestInstance(Lifecycle.PER_CLASS)
class TestOpenFileCommand {

	private JTextArea textArea;
	private JInternalFrame frame;
	private Document doc;
	private OpenFileCommand command;
	private TextAreaUtilities textAreaUtilities;
	private File file;
	
	@BeforeEach
	void setUp() {
		//reset all the variables
		file = null;
		textArea = new JTextArea();
		frame = new JInternalFrame();
		doc = new Document(new FakeTTS());
		textAreaUtilities = new TextAreaUtilities(frame, textArea, doc);
		command = new OpenFileCommand(textAreaUtilities,()->file);
		
	}
  
	
	@RunForWindowsSeperator
	@DisplayName("Test text area contents")
	void testTextArea() {
		
		file = new File("src\\resources\\testfiles\\open me.txt");
		String fileContents = "Opened";
		command.execute();
		
		assertEquals(fileContents, textArea.getText());
		
		file = new File("src\\resources\\testfiles\\multiline.txt");
		fileContents = "Line 1\nLine 2 line 2\nA 3 line text";
		command.execute();
		
		assertEquals(fileContents, textArea.getText());
	}
	
	@RunForWindowsSeperator
	@DisplayName("Test document contents")
	void testDocumentContents() {
		
		file = new File("src\\resources\\testfiles\\open me.txt");
		String fileContents = "Opened";
		command.execute();
		
		assertEquals(fileContents, doc.getText());
		
		//1 line text 
		assertEquals(fileContents, doc.getLine(0));
		
		
		file = new File("src\\resources\\testfiles\\multiline.txt");
		
		fileContents = "Line 1\nLine 2 line 2\nA 3 line text";
		
		command.execute();
		
		assertEquals(fileContents, doc.getText());
		
		//check each line
		assertEquals("Line 1", doc.getLine(0));
		
		assertEquals("Line 2 line 2", doc.getLine(1));
		
		assertEquals("A 3 line text", doc.getLine(2));
		
	}
	
	
	@RunForWindowsSeperator
	@DisplayName("Test window title")
	void testJInternalFrameTitle() {
		
		file = new File("src\\resources\\testfiles\\open me.txt");
		
		command.execute();
		
		//test if the title ends with the file's absolut path.
		//note the the title might be something like Text EDITOR - <<filePath>>
		//and thats why we test if it ends with that path
		assertTrue(frame.getTitle().endsWith(file.getAbsolutePath()));
		
		file = new File("src\\resources\\testfiles\\multiline.txt");
		
		command.execute();
		assertTrue(frame.getTitle().endsWith(file.getAbsolutePath()));
		
	}
	
	
	
	
	
}
