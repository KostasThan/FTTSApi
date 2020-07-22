package tests.commands.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import app.commands.external.ExternalCommand;
import app.commands.external.RunAfterConfirmationCommand;


@TestInstance(Lifecycle.PER_CLASS)
class TestRunAfterConfirmationCommand {
	
	private RunAfterConfirmationCommand command;
	private Boolean execute;
	private ExternalCommand externalCommand;
	private Integer dummyResult;
	
	@BeforeAll
	void setUpBeforeClass() throws Exception {
		//set the execute variable to true
		execute = true;
		
		//set a dummy variable to 0
		dummyResult = 0;
		
		//the internal command will add 1 to the dummy result each time it is executed
		externalCommand = ()-> dummyResult++;
	
		//initialize the command
		command = new RunAfterConfirmationCommand(externalCommand, ()->execute);
		
	}

	@Test
	void testExecute() {
	
		//this should execute
		command.execute();
		
		//the dummy must be 1
		assertEquals(1,dummyResult);
		
		//a couple more times
		command.execute();
		command.execute();
		
		//dummy should be 3
		assertEquals(3,dummyResult);
		
		//from now on the execute must have no effect
		execute = false;
		
		command.execute();
		command.execute();
		
		//we expect the same result
		assertEquals(3, dummyResult);
		
	}

}
