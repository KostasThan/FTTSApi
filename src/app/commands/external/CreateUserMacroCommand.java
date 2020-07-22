package app.commands.external;

import java.awt.Container;
import java.awt.event.ActionListener;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.swing.JMenuItem;

/**
 * This class is responsible for adding a new {@link JMenuItem} on a
 * {@link Container} and adding an {@link ActionListener} to the said
 * component,to be provided by a Function.
 *
 */
public class CreateUserMacroCommand implements ExternalCommand {

	private Container container;
	private Supplier<String> titleSupplier;
	private Function<String, ActionListener> listenerCreator;

	/**
	 * 
	 * @param container       The container to add the JMenuItem.
	 * @param titleSupplier   The name of the JMenuItem.
	 * @param listenerCreator A Function than given a String provides an
	 *                        ActionListener
	 */
	public CreateUserMacroCommand(Container container, Supplier<String> titleSupplier,
			Function<String, ActionListener> listenerCreator) {
		this.container = container;
		this.titleSupplier = titleSupplier;
		this.listenerCreator = listenerCreator;
	}

	/**
	 * This method has two effects:
	 * <li>Adds a {@link JMenuItem} to the container.</li>
	 * <li>Adds the ActionListener provided by the function to the JMenuItem.</li>
	 *
	 */
	public void execute() {
		JMenuItem item = new JMenuItem(titleSupplier.get());

		item.addActionListener(listenerCreator.apply(titleSupplier.get()));

		container.add(item);
		container.repaint();
		container.revalidate();

	}

}
