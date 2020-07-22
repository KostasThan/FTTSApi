package app.model.usermacro;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

/**
 * This class implements a {@link MacroCommand} recorder. An instance of this
 * class can be attached to any number of MacroCommand. If the recorder is enabled it
 * keeps track of the MacroCommand used and can execute them independently.
 * 
 * @author Kostas
 *
 */
public class CommandRecorder implements ActionListener {

	private boolean enable = false;
	private volatile LinkedList<ActionListener> listenerCollection;

	public CommandRecorder(){
		listenerCollection = new LinkedList<>();
	}
	
	
	/**
	 * Enables or disables the capturing of {@link MacroCommand}
	 * @param enable A boolean to be used to enable or not the recorder.
	 */
	public void setEnabled(boolean enable) {

		// before enabling again
		if (enable) {

			// clear the last record
			listenerCollection.clear();
		}
		this.enable = enable;

	}

	
	
	
	/**
	 * 
	 * @return True if the {@link CommandRecorder} is enabled. False otherwise.
	 */
	public boolean isEnabled() {

		return enable;
	}

	
	
	
	/**
	 * Flushes the {@link MacroCommand} that have been captured.
	 */
	public void clear() {
		listenerCollection.clear();
	}

	
	
	
	/**
	 * Adds an {@link ActionListener}. The ActionListeners will be executed in order upon calling {@link #actionPerformed(ActionEvent)}. 
	 * @param mc The ActionListener to add
	 * @return The amount of ActionListeners captured.
	 */
	public int add(ActionListener mc) {
		listenerCollection.add(mc);
		return listenerCollection.size();

	}

	
	
	
	/**
	 * This method checks if the {@link CommandRecorder} contains at least one or more {@link ActionListener}
	 * @return True if the CommandRecorder has captured one or more ActionListeners. False otherwise.
	 */
	public boolean isLoaded() {
		return listenerCollection.size() > 0;
	}

	
	
	
	
	/**	@Override
	 * Executes the {@link ActionListeners} captured in order.
	 */
	public void actionPerformed(ActionEvent e) {
		listenerCollection.forEach((listener) -> listener.actionPerformed(e));
		
	}

}
