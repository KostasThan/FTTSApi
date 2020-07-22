package tests.commands.external;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.function.Function;
import java.util.function.Supplier;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import app.commands.external.CreateUserMacroCommand;



@TestInstance(Lifecycle.PER_CLASS)

//the class under test does 3 things

//1. add one item to the container tested at testItemCount
//2. assings the given title to the added item tested at testItemNames
//3. assign the action listener provided by the function tested at testListeners
class TestCreateUserMacroCommand {

	private JPopupMenu menu;
	private Supplier<String> stringSupp;
	private String name;
	private Function<String,ActionListener> listenerCreator;
	private ActionListener listener;
	private CreateUserMacroCommand command;
	
	
	@BeforeAll
	void setUpBeforeClass() throws Exception {
		
		
		//string supplier always returns the name
		stringSupp = ()->name; 
		
		//the listener creator funtion is a funtion that will take the title and will always return this listener
		listenerCreator = (title)->listener;
		
		
	}

	
	@BeforeEach
	void setUp() throws Exception {
		//a new JMenu is created for each test
		menu = new JPopupMenu();
		
		//setting up the command
		command = new CreateUserMacroCommand(menu, stringSupp, listenerCreator);
	}

	
	//tests whether or not the item is added to the menu
	@Test
	void testItemCount() {
	
		//initially 0 components
		assertEquals(0,menu.getComponentCount());
			
		//one time executed
		command.execute();	
		// 1 component
		assertEquals(1,menu.getComponentCount());
		
			
		//two more times executed
		command.execute();
		command.execute();
		
		//3 components
		assertEquals(3,menu.getComponentCount());
		
	}
	
	@Test
	void testItemNames() {
		//setting the name
		name = "name";
		
		command.execute();
		
		//chechin for the name at the first component
		assertEquals(name,((JMenuItem) menu.getComponent(0)).getText());
		
		
		
		//changing the name
		name = "new name";
		
		command.execute();
		
		//checking the new name on the second component
		assertEquals(name,((JMenuItem) menu.getComponent(1)).getText());
	}
	
	
	
	@Test
	void testListeners() {
		//setting the listener
		listener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				//do nothing
			}
		};
		
		command.execute();
		
		//get the added item
		JMenuItem item = (JMenuItem) menu.getComponent(0);
		
		//get the first action listener,must be equal to listener
		assertEquals(listener,item.getListeners(ActionListener.class)[0]);
		
		//the length of the action listener must be 1
		assertEquals(1,item.getListeners(ActionListener.class).length);
		
		
		//change listener sto something else
		listener = new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int thisIsJustAcommand = 1 + 1;
				
			}
		};
		
		
		command.execute();
		
		
		//get the second item this time
		item = (JMenuItem) menu.getComponent(1);
		
		//first listener must be equal to listener
		assertEquals(listener,item.getListeners(ActionListener.class)[0]);
		
		//the length of the action listener must be 1
		assertEquals(1,item.getListeners(ActionListener.class).length);
		
		
	}
	
	
}



























