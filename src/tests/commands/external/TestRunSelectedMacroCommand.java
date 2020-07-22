package tests.commands.external;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.LinkedList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import app.commands.external.RunSelectedMacroCommand;
import app.model.usermacro.UserCommandsFactory;
import app.model.usermacro.UserMacro;
import app.model.usermacro.UserMacroHandler;

/**
 * The {@link RunSelectedMacroCommand command} under  test run's a specified macro from a
 * {@link UserMacroHandler}
 * 
 * @author Kostas
 *
 */

//to test this class in real time
//we have to create a user macro handler
//1.create a factory 
//2.create some user macros
//3.then run the command

//instead we will create a dummy user macro
//that upon execution of get macro sets the result variable to some integer
//then we will check if the result has the correct value
//The command also executes the macro and since we are gonna return null
//it will throw an NullPointerExceptiom
//we are going to be catching only that exception
//if any other exception arise the tests fail

@TestInstance(Lifecycle.PER_CLASS)
class TestRunSelectedMacroCommand {

	private DummyUserMacroHandler userMacroHandler;
	private int pos;
	private RunSelectedMacroCommand command;

	@BeforeAll
	void setUpbeforeClass() {
		// create a dummy variable
		userMacroHandler = new DummyUserMacroHandler(null);
		// populate the handler
		userMacroHandler.addInt(0);
		userMacroHandler.addInt(1);
		userMacroHandler.addInt(2);
		userMacroHandler.addInt(3);

		// initialize the command
		command = new RunSelectedMacroCommand(userMacroHandler, () -> pos);
	}

	@Test
	void testExecute() {
		pos = 0;
		try {
			command.execute();
		} catch (NullPointerException e) {

		}

		// we expect the first element to be seted as result
		assertEquals(0, userMacroHandler.getResutl());

		// change the supplier a couple of times
		pos = 1;
		pos = 2;

		try {
			command.execute();
		} catch (NullPointerException e) {

		}

		// we expect the first element to be seted
		assertEquals(2, userMacroHandler.getResutl());

	}

}

//override only the methods we are going to use
class DummyUserMacroHandler extends UserMacroHandler {

	private LinkedList<Integer> integersList = new LinkedList<>();
	private int result;

	public DummyUserMacroHandler(UserCommandsFactory factory) {
		super(factory);
	}

	public void addInt(int number) {
		integersList.add(number);
	}

	// this is the method this command uses
	// make it to set the result approriately
	// then we will check the result with the expected result
	public UserMacro getMacro(int pos) {
		result = integersList.get(pos);
		return null;
	}

	public int getResutl() {
		return result;
	}

}
