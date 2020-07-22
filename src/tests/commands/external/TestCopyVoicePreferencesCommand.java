package tests.commands.external;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import app.commands.external.CopyVoicePreferencesCommand;
import app.model.textNspeak.Document;
import app.model.textNspeak.FakeTTS;


@TestInstance(Lifecycle.PER_CLASS)
class TestCopyVoicePreferencesCommand {

	private Document originalDoc;
	private Document copyDoc;
	private CopyVoicePreferencesCommand command;
	
	@BeforeAll
	void setUpBeforeClass() throws Exception {
		originalDoc = new Document(new FakeTTS());
		copyDoc = new Document(new FakeTTS());
		command = new CopyVoicePreferencesCommand(()->originalDoc, ()->copyDoc);
	}

	@Test
	void testCopiedDoc() {	
		float pitch = 50;
		float rate = 200;
		float volume = 20;
		
		originalDoc.setPitch(pitch);
		originalDoc.setRate(rate);
		originalDoc.setVolume(volume);
		
		command.execute();
		
		//match it with the status we passed
		assertEquals(copyDoc.getPitch(), pitch);
		assertEquals(copyDoc.getRate(), rate);
		assertEquals(copyDoc.getVolume(), volume);
		
		
	}
	
	@Test
	void testOriginalDoc() {
	
		float pitch = 50;
		float rate = 200;
		float volume = 20;
		
		
		originalDoc.setPitch(pitch);
		originalDoc.setRate(rate);
		originalDoc.setVolume(volume);
		
		command.execute();
		
		//make sure the original doc is not changed
		assertEquals(originalDoc.getPitch(), pitch);
		assertEquals(originalDoc.getRate(), rate);
		assertEquals(originalDoc.getVolume(), volume);
		
		
	}

}
