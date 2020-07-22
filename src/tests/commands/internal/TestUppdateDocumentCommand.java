package tests.commands.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.swing.JTextArea;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import app.commands.internal.UpdateDocumentCommand;
import app.model.textNspeak.Document;
import app.model.textNspeak.FakeTTS;

//class under test
//copies the text area's text
//into the document provided by a supplier
//erasing its previous text

@TestInstance(Lifecycle.PER_CLASS)
class TestUppdateDocumentCommand {

	JTextArea textArea;
	Document doc;
	UpdateDocumentCommand command;

	@BeforeAll
	void setUpBeforeClass() throws Exception {
		textArea = new JTextArea();
		doc = new Document(new FakeTTS());
		command = new UpdateDocumentCommand(textArea, () -> doc);

	}

	@Test
	void testExecute() {
		// create a string
		String text = "This is a nice text\n with many lines";

		// set it to the text area
		textArea.setText(text);

		// set the document with another text
		doc.setText("This is another text");

		// execute the command
		command.execute();

		// the values must much
		assertEquals(text, doc.getText());

		// and the text area should be unaffected
		assertEquals(text, textArea.getText());

		
		//change bot texts
		//to see if the command is up to date
		doc.setText("This is another text. Thas has nothing to do with the previous one");

		text = "another one?";
		//update the text area
		textArea.setText(text);
		
		command.execute();
		
		//same 
		assertEquals(text, doc.getText());
		assertEquals(text, textArea.getText());

	}

}
