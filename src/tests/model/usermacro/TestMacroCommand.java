package tests.model.usermacro;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import app.model.usermacro.MacroCommand;

@TestInstance(Lifecycle.PER_CLASS)
class TestMacroCommand {

	private MacroCommand macro;
	private int dummy;
	private boolean boolDummy;
	
	@BeforeEach
	void setUp() {
		macro = new MacroCommand();
		dummy = 0;
	}

	@Test
	void testCommandCapturing() {
		
		//command that adds one to dummy
		macro.addInternal(()-> dummy++);
		macro.actionPerformed(null);
		
		//the result must be 1
		assertEquals(1, dummy);
		
		
		//add another command
		macro.addExternal(()-> dummy = dummy + 2);
		macro.actionPerformed(null);
		
		//the result must be 3
		assertEquals(4, dummy);
		
		
	}
	//internal commands
	//are executed before external ones
	//despite the insert order
	//same type of commands do follow insert order
	@Test
	void testSameTypeCommandOrder() {
		macro.addExternal(()-> boolDummy = false );
		macro.addExternal(()-> boolDummy = true );
		
		macro.actionPerformed(null);
		
		//order must be preserved
		//result must be true
		assertTrue(boolDummy);
		
		macro = new MacroCommand();
		
		//same with internal
		macro.addInternal(()-> boolDummy = false );
		macro.addInternal(()-> boolDummy = true );
		
		macro.actionPerformed(null);

		assertTrue(boolDummy);
	}
	
	
	@Test
	void testDiffentTypeCommandOrder() {
		
		//first we added the external
		macro.addExternal(()-> boolDummy = false );
		
		//then the internal
		macro.addInternal(()-> boolDummy = true );
		
		//twice
		macro.addInternal(()-> boolDummy = true );
		
		macro.actionPerformed(null);
		
		//we expect intenals to be used first
		//so the result must be false
		assertFalse(boolDummy);
		
		
	}

	

 	
}
