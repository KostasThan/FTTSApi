package tests.model.encodes;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import app.model.encodes.AtbashEncode;


@TestInstance(Lifecycle.PER_CLASS)
class AtbashEncodeTest {
	
	
	
	static EncodersTester tester;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		 tester = new EncodersTester();
		 tester.setEncodeMethod(AtbashEncode.getInstance());
		 tester.setEncodedUpperLetters(new String[]{"Z","Y","X","W","V","U","T","S","R","Q","P","O","N","M","L","K","J","I","H","G","F","E","D","C","B","A"});
		 tester.setLowerEncodedLetters(new String[]{"z","y","x","w","v","u","t","s","r","q","p","o","n","m","l","k","j","i","h","g","f","e","d","c","b","a"});
		 tester.setEncodedFox("Gsv jfrxp yildm ulc qfnkvw levi gsv ozab wlt.");
	}

		
	@Test
	void testUpperCase() {
		tester.testUpperCase();
	}
	
	@Test
	void testLowerCase() {
		tester.testLowerCase();
	}
	
	@Test 
	void testSumbols() {
		tester.testSumbols();
	}
	
	@Test 
	void testNumbers() {
		tester.testNumbers();
	}
	
	@Test
	void testFox() {
		tester.testFox();
	}
	
	
	

}
