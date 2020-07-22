package app.commands.external;

/**@FunctionalInterface
 * 
 * A functional interface used to represent a command an actor called.
 * 
 */
public interface ExternalCommand{

	/**
	 * Runs the command
	 */
	void execute();
}
