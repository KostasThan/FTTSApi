package tests.commands.external;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import app.commands.external.SetVoiceValuesCommand;
import app.model.textNspeak.Document;
import app.model.textNspeak.TTSApi;

@TestInstance(Lifecycle.PER_CLASS)
class TestSetVoiceValuesCommand {

	
	private SetVoiceValuesCommand command;
	private Document doc;
	//using the actuall speaker to see the effects
	private TTSApi speaker;
	
	@BeforeAll
	void setUpBeforeClass() throws Exception {
		speaker = new TTSApi();
		doc = new Document(speaker);
	}


	@Test
	void testExecute() {
		
		//initialize the command
		command = new SetVoiceValuesCommand(()->doc, 50, 50, (float)0.5);
		
		//execute
		command.execute();
		
		//check all the results
		assertAll("Document voice fields",
				()-> assertEquals(50,speaker.getPitch(),"Pitch has wrong value"),
				()-> assertEquals(50,speaker.getRate(),"Rate has wrong value"),
				()->assertEquals(0.5,speaker.getVolume(),"Volume has wrong value"));
		
		
		command = new SetVoiceValuesCommand(()->doc, 150, 100, (float)0.8);
		command.execute();
		
		assertAll("Document voice fields",
				()-> assertEquals(150,speaker.getPitch(),"Pitch has wrong value"),
				()-> assertEquals(100,speaker.getRate(),"Rate has wrong value"),
				
				//rounding on 4 decimals
				//the external api some time changes the the float for about 10^-8
				//making sure the 4 first decimals are corrent we are good to go
				()->assertEquals(8000,speaker.getVolume()*10000,"Volume has wrong value"));
		
		
		//volume field = 3 which is > 1
		//this should leave the volume unchanged
		command = new SetVoiceValuesCommand(()->doc, 150, 100, 3);
		
		
		//this is the documents responsibility
		//the exception will come from the doc
		assertThrows(IllegalArgumentException.class, command::execute);

		//make sure the value is not changed
		assertEquals(8000, speaker.getVolume()*10000,"Volume should have not changed with illegal argument");
		
	}

}
