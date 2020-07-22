package tests.model.textNspeak;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import app.model.encodes.DefaultEncode;
import app.model.textNspeak.Document;
import app.model.textNspeak.FakeTTS;
import app.model.textNspeak.TTSApi;



@TestInstance(Lifecycle.PER_CLASS)
class DocumentTest {

	private  Document doc;
	private  FakeTTS speaker;


	@BeforeAll
	void setUpBeforeClass() {
		speaker = new FakeTTS();
	}
	@BeforeEach
	void setUp() throws Exception {
		//everyone should create their own instance to test 
		doc = null;
	}



	@Test
	void testConstructor() {
		String sample = "Sample Text\nA Second Line";
		
		
		doc = new Document(speaker,sample);
		
		assertAll("After construction",
				() -> assertEquals(DefaultEncode.class,doc.getEncodeMethod().getClass(),"no custom encoding is set"),
				 () -> assertFalse(doc.isSpeaking(),"Should not be speaking on its own"),
				 () -> assertEquals(doc.getText(),sample,"String given to the constructor is changed"),
				 () -> assertEquals(doc.getLine(0),"Sample Text","First line is not equal"),
				 () -> assertEquals(doc.getLine(1),"A Second Line","Second line is not equal"));
	
		
	}
	
	@Test
	void testExceptions() {
		doc = new Document(null, "A multiline\n String");
		
		assertAll("Null Speaker Exceptions",
				() -> assertThrows(NullPointerException.class, () -> doc.speakDocument()),
				() -> assertThrows(NullPointerException.class, () -> doc.customSpeakDocument(true)));
	
		
		
		doc = new Document(new TTSApi(),null);	
		assertEquals("",doc.getText(),"Not that fragile. It should convert null to empty");	
		assertEquals("",doc.getLine(0),"Should create a list with one empty string");
	}

	@Test
	void testCustomLine() {
		doc = new Document(speaker,"this is\n a multiline\n string. with 3 lines.");
		
		assertThrows(ArrayIndexOutOfBoundsException.class,() -> doc.customSpeakLine(5,false));
		
		doc.setPitch((float) 10);
		doc.setRate((float) 50);
		doc.setEncode(DefaultEncode.getInstance());
		assertThrows(ArrayIndexOutOfBoundsException.class,() -> doc.customSpeakLine(5,false),"Other set methods should not interfere with the lines");
		
	}

	
	@Test
	void testTimeCreated() {
		doc = new Document(speaker);
		
		LocalDateTime expectedDate = LocalDateTime.now(ZoneId.systemDefault());
		LocalDateTime actuallDate  = doc.getDateCreated();
		
		assertEquals(expectedDate.getMonth(), actuallDate.getMonth());
		assertEquals(expectedDate.getDayOfMonth(), actuallDate.getDayOfMonth());
		assertEquals(expectedDate.getHour(), actuallDate.getHour());
		
		//might fail occasionally due to capturing the expected date
		//one command after the creation of the object
		assertEquals(expectedDate.getSecond(), actuallDate.getSecond());
		
	}
	
	//needs 5 seconds to run
	@Test
	void testLastModifiedTime() {
		//create a doc
		doc = new Document(speaker);
		
		//make sure creation and modified is the same
		//since we didn't modify the documt since it's creation
		assertEquals(doc.getDateCreated(), doc.getDateModified());
		
		//wait 5 seconds
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
				
		//modify the text
		doc.setText("i modify the doc");
		
			
		//make sure creation is before date modified
		assertTrue(doc.getDateCreated().isBefore(doc.getDateModified()));
	}
	

}
