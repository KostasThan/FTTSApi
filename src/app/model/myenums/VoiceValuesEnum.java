package app.model.myenums;

/**
 * Every enum on this class has a float value representing the value a
 * {@link Voice} field is able to be set at.
 * 
 * @author Kostas
 *
 */
public enum VoiceValuesEnum {
	PitchMinimumValue("20.0"), PitchStartingValue("100.0"), PitchMaximumValue("600.0"),

	RateMinimumValue("10.0"), RateStartingValue("150.0"), RateMaximumValue("999.0"),

	VolumeMinimumAmount("0.0"), VolumeStartingValue("1.0"), VolumeMaximumValue("1.0");

	private String name;

	VoiceValuesEnum(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}

	/**
	 * 
	 * @return The float the specific enum stands for.
	 */
	public float getValue() {
		return Float.parseFloat(name);
	}

}
