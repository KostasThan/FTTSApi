package tests.commands.external;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JInternalFrame;
import javax.swing.JTextArea;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import app.commands.external.CreateDocumentCommand;
import app.model.textNspeak.Document;
import app.model.textNspeak.FakeTTS;
import app.model.textNspeak.TextAreaUtilities;


@TestInstance(Lifecycle.PER_CLASS)
class TestCreateDocumentCommand {
	
	private TextAreaUtilities textAreaUtilities;
	private Document doc;
	private CreateDocumentCommand command;
	
	@BeforeAll
	void setUpBeforeClass() throws Exception {
		doc = new Document(new FakeTTS());
		textAreaUtilities = new TextAreaUtilities(new JInternalFrame(), new JTextArea(), doc);
		
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testExecute() {
		//create a title and author
		String title = "title";
		String author = "author";
		
		//assing the command to those variables
		command = new CreateDocumentCommand(textAreaUtilities,()->title,()->author);
		
		//execute command
		command.execute();
		
		//chech if they match
		assertEquals(title, doc.getTitle());
		assertEquals(author,doc.getAuthor());
		
		
		//cannot change the title variable because it must be (efectively) final for the lamda		
		
		//create new title and author
		String newTitle = "new title";
		String newAuthor = "new author";
		
		//assing them to the command
		command = new CreateDocumentCommand(textAreaUtilities,()->newTitle,()->newAuthor);
		
		//execute the command again
		command.execute();
		
		//make sure it changed
		assertEquals(newTitle, doc.getTitle());
		assertEquals(newAuthor,doc.getAuthor());
		
		
		//execute once more
		command.execute();
		
		//make sure its the same
		assertEquals(newTitle, doc.getTitle());
		assertEquals(newAuthor,doc.getAuthor());
	}

}
