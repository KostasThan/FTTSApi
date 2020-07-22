package app.model.usermacro;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import app.commands.external.ExternalCommand;
import app.commands.internal.InternalCommand;

/**
 * This class is responsible for archiving and executing a series of
 * {@link ExternalCommand} and {@link InternalCommand}. Also provides the
 * possibility to be captured by a {@link CommandRecorder}.
 * 
 */
public class MacroCommand implements ActionListener {

	private List<InternalCommand> internalCommands = new LinkedList<>();
	private List<ExternalCommand> externalCommands = new LinkedList<>();
	private CommandRecorder recorder;

	/**
	 * Creates a {@link MacroCommand} instance without a {@link CommandRecorder}.
	 */
	public MacroCommand() {
	}

	/**
	 * Creats a {@link MacroCommand} instance with a {@link CommandRecorder}
	 * attatched to it.
	 * 
	 * @param recorder
	 */
	public MacroCommand(CommandRecorder recorder) {
		this.recorder = recorder;
	}

	/**
	 * Executed the {@link ExternalCommand} and {@link InternalCommand}.<br>
	 * The order follows is:
	 * <li>InternalCommands has priority over ExternalCommands</li>
	 * <li>Every "type" of command is executed using the FIFO logic.</li>
	 */
	public void actionPerformed(ActionEvent e) {

		internalCommands.forEach((internalCommand) -> {
			internalCommand.execute();
			if (recordingEnabled()) {
				recorder.add((ev) -> internalCommand.execute());
			}
		});

		externalCommands.forEach((externalCommand) -> {
			externalCommand.execute();
			if (recordingEnabled()) {
				recorder.add((ev) -> externalCommand.execute());
			}
		});

	}

	/**
	 * Adds an {@link InternaCommand}
	 * 
	 * @param command The InternalCommand to be added.
	 * @return self.
	 */
	public MacroCommand addInternal(InternalCommand command) {
		internalCommands.add(command);
		return this;
	}

	/**
	 * Adds an {@link ExternalCommand}
	 * 
	 * @param command The InternalCommand to be added.
	 * @return self.
	 */
	public MacroCommand addExternal(ExternalCommand command) {
		externalCommands.add(command);
		return this;
	}

	/**
	 * Adds a {@link CommandRecorder}. If this object had already a CommandRecorder
	 * the previous instance of CommandRecorder is deleted.
	 * 
	 * @param recorder The recorder to add.
	 */
	public void addRecorder(CommandRecorder recorder) {
		this.recorder = recorder;
	}

	/**
	 * checks if the recorder is enabled return true if the recorder is enabled.<br>
	 * If there is no recorder or the recorder is null this method will return false
	 * 
	 * @return True iff the recorder is enabled.
	 */

	private boolean recordingEnabled() {
		if (recorder != null) {
			if (recorder.isEnabled())
				return true;

		}
		return false;
	}

}
