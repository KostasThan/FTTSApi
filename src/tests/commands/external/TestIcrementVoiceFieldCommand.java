package tests.commands.external;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;

import app.commands.external.TuneVoiceCommand;

/**
 * Class under test is used with a setter and a getter of a field,a maximum and
 * a minimum of that field and an amount to add. <br>
 * If the execution of the command is to set the field above the maximum, it is
 * set to maximum instead. In analogy goes for the minimum.
 * <p>
 * For example: If we have a field sitting at 50 with a max at 100 and we try to add 100
 * it will set the field to 100 instead of 150.
 * 
 * @author Kostas
 *
 */
@TestInstance(Lifecycle.PER_CLASS)
class TestIcrementVoiceFieldCommand {

	private TuneVoiceCommand command;
	private DummyClass dummy;

	@Test
	@DisplayName("Test Increment withing bounds")
	void testHappyDaySenario() {

		// starting value =0
		dummy = new DummyClass(0);

		// add methdod = add on dummy field
		// get current value = dummy get dummy field
		// add amount = 10
		// minimum = 0
		// maximum = 100
		command = new TuneVoiceCommand(dummy::set, dummy::getDummyField, 10, 0, 100);

		// make sure we are staring right

		assertEquals(0, dummy.getDummyField());

		command.execute();
		// one execution -> +10
		assertEquals(10, dummy.getDummyField());

		command.execute();
		command.execute();
		command.execute();

		// three executions -> +30
		assertEquals(40, dummy.getDummyField());
	}

	@Test
	@DisplayName("Test Decrement withing bounds")
	void testDecrement() {

		// starting value = 50
		dummy = new DummyClass(50);

		// add methdod = add on dummy field
		// get current value = dummy get dummy field
		// add amount = -10
		// minimum = 0
		// maximum = 100
		command = new TuneVoiceCommand(dummy::set, dummy::getDummyField, -10, 0, 100);

		// make sure we are staring right

		assertEquals(50, dummy.getDummyField());

		command.execute();

		// one execution -> -10
		assertEquals(40, dummy.getDummyField());

		command.execute();
		command.execute();
		command.execute();

		// three executions -> -30
		assertEquals(10, dummy.getDummyField());
	}

	@Test
	@DisplayName("Exceed lower bounds")
	void testExceedUpperBounds() {
		float maximum = 100;
		// starting value = 50
		dummy = new DummyClass(50);

		// add methdod = add on dummy field
		// get current value = dummy get dummy field
		// add amount = 100
		// minimum = 0
		// maximum = 100
		command = new TuneVoiceCommand(dummy::set, dummy::getDummyField, 100, 0, maximum);

		// make sure we are staring right
		assertEquals(50, dummy.getDummyField());

		// this will add 100
		// but we expect to hit the maximum
		command.execute();

		assertEquals(maximum, dummy.getDummyField());

		command.execute();

		// we expect to still remain at maximum
		assertEquals(maximum, dummy.getDummyField());

	}

	@Test
	@DisplayName("Exceed uppber bounds")
	void testExceedLoweBounds() {
		float minimum = 100;
		// starting value = 50
		dummy = new DummyClass(50);

		// add methdod = add on dummy field
		// get current value = dummy get dummy field
		// add amount = 100
		// minimum = 0
		// maximum = 100
		command = new TuneVoiceCommand(dummy::set, dummy::getDummyField, -100, minimum, 100);

		// make sure we are staring right
		assertEquals(50, dummy.getDummyField());

		// this will subtract 100
		// but we expect to hit the minimum
		command.execute();

		assertEquals(minimum, dummy.getDummyField());

		command.execute();

		// we expect to still remain at minimum
		assertEquals(minimum, dummy.getDummyField());

	}

	class DummyClass {

		private double dummyField;

		public DummyClass(int dummyField) {
			this.dummyField = dummyField;
		}

		public float getDummyField() {
			return (float) dummyField;
		}

		public void set(float dummyField) {

			this.dummyField = dummyField;

		}

	}

}
