package app.commands.internal;

import java.util.List;
import java.util.function.Supplier;

import javax.swing.DefaultListModel;

/**
 * THis command is responsible for "refreshing" the contents of the
 * {@link DefaultListModel}.
 *
 * @param <E> the type of the elements contained in the DefaultListModel.
 */
public class RefreshDefaultListCommand<E> implements InternalCommand {

	private DefaultListModel<E> defaultList;
	private Supplier<List<E>> listSupplier;

	/**
	 * 
	 * @param defaultList  The {@link DefaultListModel} to be refreshed.
	 * @param listSupplier A list providing the updated elements.
	 */
	public RefreshDefaultListCommand(DefaultListModel<E> defaultList, Supplier<List<E>> listSupplier) {
		this.defaultList = defaultList;
		this.listSupplier = listSupplier;
	}

	/**
	 * @Override This method removes all the elements contained in the
	 *           {@link DefaultListModel}. Then adds all the elements contained in
	 *           the List the List Supplier provides.
	 */
	public void execute() {
		defaultList.clear();
		defaultList.addAll(listSupplier.get());

	}

}
