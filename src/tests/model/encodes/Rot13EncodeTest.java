package tests.model.encodes;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import app.model.encodes.Rot13Encode;

class Rot13EncodeTest {

	static EncodersTester tester;
	
	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		 tester = new EncodersTester();
		 tester.setEncodeMethod(Rot13Encode.getInstance());
		 tester.setEncodedUpperLetters(new String[]{"N","O","P","Q","R","S","T","U","V","W","X","Y","Z","A","B","C","D","E","F","G","H","I","J","K","L","M"});
		
		
		 tester.setLowerEncodedLetters(new String[]{"n","o","p","q","r","s","t","u","v","w","x","y","z","a","b","c","d","e","f","g","h","i","j","k","l","m"});
		 tester.setEncodedFox("Gur dhvpx oebja sbk whzcrq bire gur ynml qbt.");
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
