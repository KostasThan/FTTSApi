package tests.commands.internal;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultListModel;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import app.commands.internal.RefreshDefaultListCommand;

//command under test
//copies the a list
//to a default list model
//erasing its previous elements

@TestInstance(Lifecycle.PER_CLASS)
class TestRefreshDefaultListCommand {

	private DefaultListModel<String> defaultList;
	private List<String> list;
	private RefreshDefaultListCommand<String> command;

	@BeforeAll
	void setUpBeforeClass() throws Exception {
		// create the default list
		defaultList = new DefaultListModel<>();

		// create the list that will populate the default list
		list = new ArrayList<>();

		// initialize the command
		command = new RefreshDefaultListCommand<>(defaultList, () -> list);

	}

	@AfterEach
	void setUp() {
		defaultList.clear();
		list.clear();
	}

	@Test
	@DisplayName("Test the default list with no prior elements")
	void testWithEmptyDefaultList() {
		list.add("String 1");
		list.add("String 2");

		command.execute();

		assertEquals("String 1", defaultList.get(0));
		assertEquals("String 2", defaultList.get(1));

		assertTrue(defaultList.getSize() == 2);
	}

	@Test
	@DisplayName("Test the default list with elements already added")
	void testWithLoadedDefaultList() {
		defaultList.addElement("Random string");
		defaultList.addElement("Another element");

		// call the previous method
		// but now we have already populated the default list
		// the outcome must be the same
		testWithEmptyDefaultList();

	}
}
