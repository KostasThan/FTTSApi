package tests.commands.external;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import app.commands.external.ChangeRecorderStatusCommand;
import app.model.usermacro.CommandRecorder;

@TestInstance(Lifecycle.PER_CLASS)
class TestChangeRecorderStatusCommand {

	CommandRecorder rec;

	@BeforeAll
	void setUpBeforeClass() throws Exception {
		rec = new CommandRecorder();
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@Test
	void testExecute() {
		ChangeRecorderStatusCommand enableCommand = new ChangeRecorderStatusCommand(rec, () -> true);
		ChangeRecorderStatusCommand disable = new ChangeRecorderStatusCommand(rec, () -> false);

		enableCommand.execute();
		assertTrue(rec.isEnabled());

		disable.execute();
		assertFalse(rec.isEnabled());

		enableCommand.execute();
		enableCommand.execute();
		assertTrue(rec.isEnabled());

		enableCommand.execute();
		enableCommand.execute();
		disable.execute();
		assertFalse(rec.isEnabled());

	}

	@Test
	void testWithSupplier() {
		// NOTE: a variable is not used becaue it will be in the enclosing scope of the
		// lamda
		// create a dummy supplier
		class BooleanSupplier {
			private boolean enable;

			public BooleanSupplier(boolean enable) {
				this.enable = enable;
			}

			private void setEnabled(boolean enabled) {
				enable = enabled;
			}

			private boolean isEnabled() {
				return enable;
			}

		}

		// set it true
		BooleanSupplier supp = new BooleanSupplier(true);
		ChangeRecorderStatusCommand command = new ChangeRecorderStatusCommand(rec, supp::isEnabled);

		// execute command
		command.execute();
		// supplier and recorder status should much
		assertEquals(supp.isEnabled(), rec.isEnabled());

		// change the enabled status
		supp.setEnabled(!supp.isEnabled());

		command.execute();
		assertEquals(supp.isEnabled(), rec.isEnabled());

	}

}
