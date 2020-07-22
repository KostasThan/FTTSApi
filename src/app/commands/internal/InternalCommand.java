package app.commands.internal;



/**@FunctionalInterface
 * 
 * A functional interface used to represent a command the system called.
 * 
 */
public interface InternalCommand{
	
	/**
	 * Runs the command
	 */
	void execute();
}
