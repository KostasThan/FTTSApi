package tests.commands.external;

import static org.junit.jupiter.api.Assertions.*;

import javax.swing.JTextArea;

import org.junit.jupiter.api.BeforeAll;

import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.EnumSource.Mode;

import app.commands.external.EncodeSelectedCommand;

import app.model.encodes.EncodeFactory;

import app.model.myenums.EncodeEnums;
import app.model.textNspeak.TextAreaUtilities;

/**
 * After the command is executed there are two effects
 *<li> the selected text is changed according to the encoding method chosen</li>
 *<li> the text converted should be highlighted once again for the user to see the results</li> 
 *
 * 
 *
 */
@TestInstance(Lifecycle.PER_CLASS)
class TestEncodeHighLightedCommand {

	private JTextArea textArea;
	private EncodeSelectedCommand command;
	private TextAreaUtilities textAreaUtilities;


	@BeforeAll
	void setUpBeforeClass() throws Exception {
		textArea = new JTextArea();
		textAreaUtilities = new TextAreaUtilities(null,textArea,null);
		
	}
	
	
	
	//this test will run for any encoding method we may add in the future
	@ParameterizedTest
	@EnumSource(value = EncodeEnums.class, names = "DEFAULT", mode = Mode.EXCLUDE)
	void test(EncodeEnums encodeEnum) {
	
		//set the text of the text area
		textArea.setText("a nice text");
		textArea.select(0,6);
		
		//check for the selected text
		String selectedText = "a nice";
		
		//find the highlighted text
		String convertedText = EncodeFactory.getEncodeMethod(encodeEnum).encode(selectedText);
		
		//find the whole text
		String wholeTextAfterConvertion = convertedText + " text";
		
		//make sure we know what the selected text is
		assertEquals(selectedText, textArea.getSelectedText());

		
		//intantiate the command
		command = new EncodeSelectedCommand(textAreaUtilities,encodeEnum);
		
		//execute the command
		command.execute();	
	
		
		//make sure the highlighted text is the correct one
		assertEquals(convertedText, textArea.getSelectedText());
		
		
		//make sure the whole text is the correct one
		assertEquals(wholeTextAfterConvertion, textArea.getText());
		
	}

}
