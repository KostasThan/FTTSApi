package app.commands.external;

import java.util.function.Supplier;

import app.model.usermacro.CommandRecorder;

/**
 * This class is responsible for setting the {@link ChangeRecorderStatusCommand} to a provided Boolean.
 */
public class ChangeRecorderStatusCommand implements ExternalCommand {
	private CommandRecorder recorder;
	private Supplier<Boolean> enabledSupplier;

	/**
	 * 
	 * @param recorder        A {@link CommandRecorder} instance to set its enabled status.
	 * @param enabledSupplier A Boolean Supplier to provide the enabling or not of
	 *                        the recorder.
	 */
	public ChangeRecorderStatusCommand(CommandRecorder recorder, Supplier<Boolean> enabledSupplier) {
		this.recorder = recorder;
		this.enabledSupplier = enabledSupplier;
	}

	/**@Override
	 * Sets the {@link CommandRecorder} status to the value the Boolean Supplier
	 * provides.
	 *
	 */
	public void execute() {
		recorder.setEnabled(enabledSupplier.get());
	}

}
