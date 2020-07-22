package tests.model.usermacro;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import app.model.usermacro.CommandRecorder;
import app.model.usermacro.MacroCommand;

@TestInstance(Lifecycle.PER_CLASS)

class TestCommandRecorder {

	private int dummy;
	private CommandRecorder rec;
	private MacroCommand macroCommand;


	@BeforeAll
	void setUpBeforeClass() {

		// create the recorder
		rec = new CommandRecorder();

		// initialize a dummy variable
		dummy = 0;

		// attach the rec to the macro command
		macroCommand = new MacroCommand(rec);

		// assing a command that adds one to the dummy variable
		macroCommand.addInternal(() -> dummy++);

		
	}

	@Test
	void testPlayMethod() {

		// enable the recorder
		rec.setEnabled(true);

		// make an action
		macroCommand.actionPerformed(null);

		// the macro command must have set it to 1
		// reset it to 0
		dummy--;

		rec.actionPerformed(null);

		assertEquals(1, dummy);

		// execute two more
		rec.actionPerformed(null);
		rec.actionPerformed(null);
		assertEquals(3, dummy);

		

		// make another action
		// the recorder must now now have two recorded macro commands

		//variable upgraded once more
		macroCommand.actionPerformed(null);
		
		//reset it
		dummy--;
		
		// close the recording
		rec.setEnabled(false);

		// make sure it is still playable
		rec.actionPerformed(null);
		
		//added two to the result
		assertEquals(5, dummy);

		// clear all the recordings
		rec.clear();

		// execute once more
		rec.actionPerformed(null);
		// make sure no changes were made
		assertEquals(5, dummy);

	}
	
	@Test
	void testSetEnabledMethod() {
	

		rec.setEnabled(true);
		assertTrue(rec.isEnabled());

		rec.setEnabled(false);
		assertFalse(rec.isEnabled());

		rec.setEnabled(true);
		rec.setEnabled(true);
		assertTrue(rec.isEnabled());

		rec.setEnabled(true);
		rec.setEnabled(true);
		rec.setEnabled(false);
		assertFalse(rec.isEnabled());

	}

}
