package tests.commands.external;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Arrays;
import java.util.Random;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import app.commands.external.AddTaskCommand;
import app.model.myenums.UserCommandsEnums;
import app.model.usermacro.UserCommandsFactory;
import app.model.usermacro.UserMacroHandler;

@TestInstance(Lifecycle.PER_CLASS)
class TestAddTasksCommand {
	
	private UserMacroHandler userMacroHandler;
	private UserCommandsFactory userCommandsFactory;
	private AddTaskCommand addTask;
	private final Random rand = new Random();

	@BeforeAll
	void setUpBeforeClass() throws Exception {
		// create a new factory
		userCommandsFactory = new UserCommandsFactory();

		// all of the commands the factory provides is null
		// we care whether or not a command is added rather than executing
		Arrays.stream(UserCommandsEnums.values()).forEach((e) -> userCommandsFactory.addMacroCommand(e, null));
		userMacroHandler = new UserMacroHandler(userCommandsFactory);

		// creating the command
		addTask = new AddTaskCommand(userMacroHandler, () -> UserCommandsEnums.AtBash);
	}

	@BeforeEach
	void setUp() throws Exception  {
		// before each test set to 0 the last macro's commands
		userMacroHandler.clear();
	}

	@Test
	void testExecute() {
		// before using the commands 0 commands must be added
		assertEquals(0, getCommandCount());

		//executed once 
		addTask.execute();
		assertEquals(1, getCommandCount());


		addTask.execute();
		//one macro was there already,we added another one so expected = 2
		assertEquals(2, getCommandCount());

	}

	@RepeatedTest(value = 20)
	void executeMultipleTimes() {
		//get a random number for execution times
		int executeTimes = rand.nextInt(20);
		for (int i = 0; i < executeTimes; i++) {
			
			//execute the command
			addTask.execute();
		}
		
		//check to see if the same amount of macros are added
		assertEquals(executeTimes, getCommandCount());
	}
	
	
	
	
	
	private int getCommandCount() {
		return userMacroHandler.getLastMacro().getCommands().size();
	}
	
	
	
	
	
	
	
	
	
	
	
	
	

}
