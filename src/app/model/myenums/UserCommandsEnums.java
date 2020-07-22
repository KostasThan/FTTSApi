package app.model.myenums;

/**
 * Contains all Enums representing an
 * {@link app.commands.external.ExternalCommand ExternalCommand} a client might
 * add on a {@link app.model.usermacro.UserMacroHandler UserMacroHandler}.
 * 
 * @author Kostas
 *
 */
public enum UserCommandsEnums {
	OpenFile("Open an existing file"), CreateDocument("Create a new Document"), AtBash("AtBash document"),
	Rot13("Rot13 document"), Save("Save file"), SaveAs("Save file to a specific location"),
	Speak("Narrate the document"), CustomSpeak("Narrate the document with the specified parametres"),
	SpeakLine("Narrate a line with the specified parametres"), IncreasePitch("Increase voice's pitch"),
	DecreasePitch("Deacrease voice's pitch"), IncreaseRate("Increase voice's rate"),
	DecreaseRate("Deacrease voice's rate"), IncreaseVolume("Increase voice's volume"),
	DecreaseVolume("Deacrease voice's volume"), ApplyPreferences("Apply voice preferences"),
	ResetVoiceValues("Reset voice's preferences");

	UserCommandsEnums(String string) {
		this.name = string;
	}

	private String name;

	@Override
	public String toString() {
		return name;
	}

	/**
	 * This method checks if a command,upon execution,will narrate the
	 * document regardless of any other parametres.
	 * 
	 * @param command The command to be checked.
	 * @return true iff the command will narrate the document in any form upon
	 *         execution.
	 */
	public static boolean isSpeakCommand(UserCommandsEnums command) {

		return (command.equals(UserCommandsEnums.Speak)) || (command.equals(UserCommandsEnums.CustomSpeak))
				|| (command.equals(UserCommandsEnums.SpeakLine));
	}

}
