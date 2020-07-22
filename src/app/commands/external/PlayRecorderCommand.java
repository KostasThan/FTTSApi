package app.commands.external;

import app.model.usermacro.CommandRecorder;

/**
 * This class is responsible for executing the recorded {@link MacroCommand
 * macro commands} of a {@link Recorder}.
 * 
 *
 */
public class PlayRecorderCommand implements ExternalCommand {
	private CommandRecorder recorder;


	public PlayRecorderCommand(CommandRecorder recorder) {
		this.recorder = recorder;
	}

	/**
	 * @Override Acivates the recorder using
	 *           {@link CommandRecorder#actionPerformed(java.awt.event.ActionEvent)
	 *           actionPerformed} method with null as the source.
	 */

	public void execute() {
		recorder.actionPerformed(null);
	}

}
