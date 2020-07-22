package app.commands.external;

import java.util.function.Supplier;

import app.model.myenums.UserCommandsEnums;
import app.model.usermacro.UserMacroHandler;

/**
 * This class is responsible for adding a {@link MacroCommand},representing a
 * sequence of actions, in {@link UserMacroHandler}.
 * 
 *
 */
public class AddTaskCommand implements ExternalCommand {

	private UserMacroHandler macroHandler;
	private Supplier<UserCommandsEnums> commandEnumSupplier;

	/**
	 * @param macroHandler        The macro handler to add the command to.
	 * @param commandEnumSupplier A {@link UserCommandsEnums} supplier to specify
	 *                            the {@link ExternalCommand} to add.
	 */
	public AddTaskCommand(UserMacroHandler macroHandler, Supplier<UserCommandsEnums> commandEnumSupplier) {
		this.macroHandler = macroHandler;
		this.commandEnumSupplier = commandEnumSupplier;
	}

	/**@Override
	 * This method adds an {@link MacroCommand} to a {@link UserMacroHandler} via the {@link UserMacroHandler#add(UserCommandsEnums) add(UserCommandsEnums)} method.
	 *
	 */
	public void execute() {
		macroHandler.add(commandEnumSupplier.get());
	}

}
