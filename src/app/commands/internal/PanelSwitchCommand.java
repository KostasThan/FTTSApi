package app.commands.internal;

import java.awt.Container;

import javax.swing.JComponent;

/**
 * 
 * This class switches to a specified panel on a specified layer pane
 * 
 */
public class PanelSwitchCommand implements InternalCommand {

	private Container container;
	private JComponent panel;
	private JComponent toBeFocused;

	/**
	 * Calls
	 * {@link PanelSwitchCommand#PanelSwitchCommand(Container, JComponent,JComponent)
	 * PanelSwitchCommand(Container, JComponent,JComponent) } with null as the
	 * container to be focused.
	 */
	public PanelSwitchCommand(Container container, JComponent panel) {
		this.container = container;
		this.panel = panel;

	}

	/**
	 * 
	 * @param layeredPane The container that holds the panel.
	 * @param panel       The panel to switch to.
	 * @param toBeFocused The window which to gain focus after the switching.
	 */
	public PanelSwitchCommand(Container container, JComponent panel, JComponent toBeFocused) {
		this.container = container;
		this.panel = panel;
		this.toBeFocused = toBeFocused;
	}

	/**
	 * @Override This method removes all the layers of a container,then adds the
	 *           panel specified. If a third component is provided in the
	 *           {@link PanelSwitchCommand#PanelSwitchCommand(Container, JComponent,JComponent)
	 *           contructor} then the said component is focused
	 */
	public void execute() {

		container.requestFocusInWindow();
		container.removeAll();
		container.add(panel);
		container.repaint();
		container.revalidate();
		if (toBeFocused != null) {
			toBeFocused.requestFocusInWindow();
		}

	}

}
