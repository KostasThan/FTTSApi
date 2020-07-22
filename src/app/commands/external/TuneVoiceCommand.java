package app.commands.external;

import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * Responsible for incrementing the fields of a {@link Voice} by the specified amount.<bp>
 * Also this class won't let the values of each field to be lesser than the minimum specified or greater than the maximum specified.
 *
 */
public class TuneVoiceCommand implements ExternalCommand{

	
	private Consumer<Float> addMethod;
	private Supplier<Float> currentValueSupplier;
	private float addAmount;
	private float minimumAmount;
	private float maximumAmount;
	

	/**
	 * 
	 * @param addMethod            a consumer accepting a float representing a set method.
	 * @param currentValueSupplier a supplier representing a get method to provide the current
	 *                             of the field.
	 * @param addAmount            the amount to add on that field.
	 * @param minimumAmount        the minimum this field can be.
	 * @param maximumAmount        the maximum this field can be.
	 */
	public TuneVoiceCommand(Consumer<Float> addMethod, Supplier<Float> currentValueSupplier,
			float addAmount, float minimumAmount, float maximumAmount) {
		this.addMethod = addMethod;
		this.currentValueSupplier = currentValueSupplier;
		this.addAmount = addAmount;
		this.minimumAmount = minimumAmount;
		this.maximumAmount = maximumAmount;
	}



	
	/**@Override
	 * This method uses the set method with the a float argument (addAmount + currentAmount).
	 * <li>If the amount is to be set in a lesser value tha min the set method is called with the min amount instead</li>
	 * <li>If the amount is to be set in a lesser value tha min the set method is called with the min amount instead</li>
	 * <p>
	 * For example: If the currentAmount = 9,the addAmount = 10 and the maximum is 10 then the set method is gonna be called with 10 instead of 19. 
	 */
	public void execute() {
	
		float currentValue = currentValueSupplier.get();
		float targetAmount = currentValue + addAmount;

		
		if (targetAmount > maximumAmount) {		
			addMethod.accept(maximumAmount);
		} else if (targetAmount < minimumAmount) {
			addMethod.accept(minimumAmount);
		} else {
			addMethod.accept(targetAmount);
		}
		
	}	
	
	

	
	

}
