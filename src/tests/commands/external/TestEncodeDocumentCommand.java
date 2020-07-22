package tests.commands.external;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.swing.JTextArea;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.EnumSource.Mode;

import app.commands.external.EncodeTextAreaCommand;
import app.model.encodes.EncodeFactory;
import app.model.encodes.EncodeMethod;
import app.model.encodes.Rot13Encode;
import app.model.myenums.EncodeEnums;

/**
 * Class under test is responsible for changing the text in a jtext area with an
 * appropriate {@link EncodeMethod}
 */

@TestInstance(Lifecycle.PER_CLASS)
class TestEncodeDocumentCommand {

	private String text;
	private EncodeTextAreaCommand command;
	private JTextArea textArea;

	@BeforeAll
	void setUpBeforeClass() throws Exception {

		text = "The quick brown fox jumped over the lazy dog";

	}

	@BeforeEach
	void setUp() {
		textArea = new JTextArea();
	}

	@ParameterizedTest
	@EnumSource(value = EncodeEnums.class, names = "DEFAULT", mode = Mode.EXCLUDE)
	@DisplayName("Test with at bash encode")
	void testEncode(EncodeEnums encodeEnum) {

		EncodeMethod encodeMethod = EncodeFactory.getEncodeMethod(encodeEnum);
		// set a text to the text area
		textArea.setText(text);

		// instantiate
		command = new EncodeTextAreaCommand(textArea, () -> encodeEnum);

		// execute command
		command.execute();

		// check if the text area now has the correct text
		assertEquals(encodeMethod.encode(text), textArea.getText());
	}

	@Test
	@DisplayName("Test response to supplier")
	void testResponseToSupplier() {

		// create a dummy supplier
		class DummySupp {
			private EncodeEnums encodeEnum;

			public DummySupp(EncodeEnums encodeEnum) {
				this.encodeEnum = encodeEnum;
			}

			public EncodeEnums get() {
				return encodeEnum;
			}

			public void set(EncodeEnums encodeEnum) {
				this.encodeEnum = encodeEnum;
			}

		}

		// instantiate
		DummySupp supp = new DummySupp(EncodeEnums.ATBASH);

		// set a text to the text area
		textArea.setText(text);

		// instantiate the command
		command = new EncodeTextAreaCommand(textArea, supp::get);

		// change the supplier a few times
		supp.set(EncodeEnums.ATBASH);
		supp.set(EncodeEnums.ROT13);

		// execute command
		command.execute();

		// check if the text area now has the correct text
		assertEquals(Rot13Encode.getInstance().encode(text), textArea.getText());

	}

}
