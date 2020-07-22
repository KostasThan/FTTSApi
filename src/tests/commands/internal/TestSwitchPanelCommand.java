package tests.commands.internal;

import static org.junit.jupiter.api.Assertions.assertEquals;

import javax.swing.JLayeredPane;
import javax.swing.JPanel;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import app.commands.internal.PanelSwitchCommand;

//command under test
//places the selected component
//at top
//while removing the other


@TestInstance(Lifecycle.PER_CLASS)
class TestSwitchPanelCommand {

	private JLayeredPane layeredPane;
	private JPanel panel1;
	private JPanel panel2;
	private PanelSwitchCommand toPanel1;
	private PanelSwitchCommand toPanel2;
	
	@BeforeAll
	void setUpBeforeClass() throws Exception {
		//create a layered pane
		layeredPane = new JLayeredPane();
		
		//create two panels
		panel1 = new JPanel();
		panel2 = new JPanel();
		
		//give them names to check against
		panel1.setName("panel1");
		panel2.setName("panel2");
		
		//set up the commands
		toPanel1 = new PanelSwitchCommand(layeredPane, panel1);
		toPanel2 = new PanelSwitchCommand(layeredPane, panel2);
	}

	@Test
	void testExecute() {
		
		//first command is executed
		toPanel1.execute();		
		
		//we expect panel 1
		assertEquals("panel1",layeredPane.getComponent(0).getName());
			
		//second command is executed
		toPanel2.execute();
		
		//we expect panel 2
		assertEquals("panel2",layeredPane.getComponent(0).getName());
		
		
		//executing the commands multiple time
		//the last command determing the outcome
		toPanel1.execute();
		toPanel1.execute();
		toPanel2.execute();
		
		//last command is toPanel1
		toPanel1.execute();
		
		
		//we expect the panel1
		assertEquals("panel1",layeredPane.getComponent(0).getName());
	}

}
