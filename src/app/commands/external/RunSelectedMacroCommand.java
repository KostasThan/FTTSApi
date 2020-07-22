package app.commands.external;

import java.util.function.Supplier;

import app.model.usermacro.UserMacroHandler;


/**
 * This command is responsible for executing a {@link UserMacro} already saved in a {@link UserMacroHandler}.
 *
 */
public class RunSelectedMacroCommand implements ExternalCommand{

	private UserMacroHandler umc;
	private Supplier<Integer> posSupplier;
	
	
	/**
	 * 
	 * @param umc A {@link UserMacroHandler} instance which will provide the {@link UserMacro}.
	 * @param posSupplier An int Supplier providing the position of the UserMacro to execute.
	 */
	public RunSelectedMacroCommand(UserMacroHandler umc, Supplier<Integer> posSupplier) {
		this.umc = umc;
		this.posSupplier = posSupplier;
	}



	/**	@Override
	 * Executes the {@link UserMacro} based on the int supplier specified in the {@link RunSelectedMacroCommand#RunSelectedMacroCommand(UserMacroHandler, Supplier) constructor}.
	 * </br> If the pos provided by the supplier is greater than the UserMacros saved in the {@link UserMacroHandler} no action is performed.
	 * 
	 */
	public void execute() {
		int pos = posSupplier.get();
		//gui glitches some times
		if(pos>=0) {
			umc.getMacro(pos).actionPerformed(null);
			
		}
		
	}
	
	
	
	
}
