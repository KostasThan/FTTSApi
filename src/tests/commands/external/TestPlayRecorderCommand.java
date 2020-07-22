package tests.commands.external;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import app.commands.external.PlayRecorderCommand;
import app.model.usermacro.CommandRecorder;
import app.model.usermacro.MacroCommand;

/**
 * Class under test plays the contents of a {@link CommandRecorder}
 * 
 * 
 *
 */
@TestInstance(Lifecycle.PER_CLASS)
class TestPlayRecorderCommand {

	private int dummy;
	private CommandRecorder rec;
	private MacroCommand macroCommand;
	private PlayRecorderCommand command;
	@BeforeAll
	void setUpBeforeClass() {
		
		//create the recorder
		rec = new CommandRecorder();
		
		//initialize a dummy variable
		dummy = 0;
		
		//attach the rec to the macro command
		macroCommand = new MacroCommand(rec);
		
		//assing a command that adds one to the dummy variable
		macroCommand.addInternal(()-> dummy++);
		
		//initialize command
		command = new PlayRecorderCommand(rec);
	}
	
	
	
	@Test
	void testExecute() {
		
		//enable the recorder
		rec.setEnabled(true);
		
		//make an action
		macroCommand.actionPerformed(null);
		
		//the macro command must have set it to 1
		//reset it to 0
		dummy = 0;
		
		command.execute();
		
		assertEquals(1, dummy);
		
		//execute two more
		command.execute();
		command.execute();
		assertEquals(3, dummy);
		
		
		//close the recording
		rec.setEnabled(false);
		
		//make sure it is still playable
		command.execute();
		assertEquals(4, dummy);
		
		//clear all the recordings
		rec.clear();
		
		//execute once more
		command.execute();
		
		//make sure no changes were made
		assertEquals(4, dummy);
		
	}
	
	
	

}
