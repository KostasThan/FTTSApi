package app.model.usermacro;

import java.util.HashMap;

import app.commands.external.ExternalCommand;
import app.model.myenums.UserCommandsEnums;

/**
 * This class implements a dummy factory of {@link MacroCommand}. It does not
 * generate the MacroCommand on its own,but rather has to be populated.
 *
 */
public class UserCommandsFactory {

	private HashMap<UserCommandsEnums, MacroCommand> commands;

	public UserCommandsFactory() {
		commands = new HashMap<>();
	}

	
	
	/**
	 * Adds a {@link MacroCommand} linked to a {@link CommandEnums}.
	 * 
	 * @param commandEnum  The command enum to identify the MacroCommand
	 * @param macroCommand The macro command to provide with this CommmandEnum
	 */
	public void addMacroCommand(UserCommandsEnums commandEnum, MacroCommand macroCommand) {
		commands.put(commandEnum, macroCommand);
	}

	
	
	/**
	 * Returns a {@link MacroCommand} which is already added to this instance as a
	 * single {@link ExternalCommand}.
	 * <p>
	 * <b>Note:</b> The specified {@link EncodeEnums} has to be already linked to a
	 * MacroCommand via the
	 * {@link #addMacroCommand(UserCommandsEnums, MacroCommand)} method.
	 * 
	 * @param command An EncodeEnums instance indicating what MacroCommand to fetch.
	 * @return The MacroCommand converted to a ExternalCommand
	 */
	public ExternalCommand getCommand(UserCommandsEnums command) {
		// converts the sequence of internal-external command of the specified macro to
		// a single external
		return () -> commands.get(command).actionPerformed(null);
	}

}
