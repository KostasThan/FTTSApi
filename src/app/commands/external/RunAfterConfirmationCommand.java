package app.commands.external;

import java.util.function.Supplier;

/**
 * This command is responsible for executing an external command after a certain
 * criterias are met.
 * 
 */
public class RunAfterConfirmationCommand implements ExternalCommand {

	private ExternalCommand command;
	private Supplier<Boolean> confirmationSupplier;

	/**
	 * 
	 * @param command              The command to be executed.
	 * @param confirmationSupplier A boolean supplier determining whether or not to
	 *                             execute the command.
	 */
	public RunAfterConfirmationCommand(ExternalCommand command, Supplier<Boolean> confirmationSupplier) {
		this.command = command;
		this.confirmationSupplier = confirmationSupplier;
	}

	/**
	 * @Override This method executes the command specified in the
	 *           {@link RunAfterConfirmationCommand#RunAfterConfirmationCommand(ExternalCommand, Supplier)
	 *           constructor(ExternalCommand,Supplier} iff the supplier returns
	 *           True. <br>
	 *           If the supplier returns False then this method has no effect.
	 *
	 */
	public void execute() {

		if (confirmationSupplier.get()) {
			command.execute();
		}

	}

}
