package app.model.usermacro;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import app.commands.external.ExternalCommand;
import app.model.myenums.UserCommandsEnums;

/**
 * An instance of this class represents a series of actions a client might make.
 * Also every action added is linked to an {@link UserCommandEnums}.
 * 
 *
 */
public class UserMacro implements ActionListener {

	private LinkedList<ExternalCommand> commands;
	private LinkedList<UserCommandsEnums> addedCommands;
	private String name = "";

	public UserMacro() {
		commands = new LinkedList<>();
		addedCommands = new LinkedList<>();
	}

	/**
	 * Archived the command passed as parameter and links it to the
	 * {@link UserCommandEnums} provided.
	 * 
	 * @param command     The {@link ExternalCommand} to add.
	 * @param commandName The UserCommandsEnums to link the command to.
	 */
	public void addCommand(ExternalCommand command, UserCommandsEnums commandName) {
		commands.add(command);
		addedCommands.add(commandName);
	}

	/**
	 * @Override Executed the {@link ExternalCommand} stored in the order they were
	 *           added.
	 */
	public void actionPerformed(ActionEvent e) {
		commands.forEach((c) -> c.execute());
	}

	/**
	 * Sets the name of the UserMacro.
	 * 
	 * @param name A string representing the name.
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	/**
	 * Returns a list of {@link UserCommandsEnums} representing the commands that
	 * was added.
	 * 
	 * @return A LinkedList of UserCommandsEnums representing the commands added.
	 *         <p>
	 *         This list <b> is not backed</b> by the original list
	 */
	public LinkedList<UserCommandsEnums> getCommands() {
		LinkedList<UserCommandsEnums> copy = new LinkedList<>();
		for (UserCommandsEnums command : addedCommands) {
			copy.add(command);
		}
		return copy;
	}

}
