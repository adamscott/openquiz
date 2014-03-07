package applicationTools;

public class SoundManager {

	private static String buzzerSoundLeftNormal =   "data/sound/Buzzer1.mp3";
	private static String buzzerSoundRightNormal =  "data/sound/Buzzer2.mp3";
	private static String buzzerSoundLeftSpecial =  "data/sound/Buzzer3.mp3";
	private static String buzzerSoundRightSpecial = "data/sound/Buzzer4.mp3";
	private static String buzzerSoundLeft =  buzzerSoundLeftNormal;
	private static String buzzerSoundRight = buzzerSoundRightNormal;
	
	public static String getBuzzerSoundLeftNormal() {
		return buzzerSoundLeftNormal;
	}
	public static String getBuzzerSoundRightNormal() {
		return buzzerSoundRightNormal;
	}
	public static String getBuzzerSoundLeftSpecial() {
		return buzzerSoundLeftSpecial;
	}
	public static String getBuzzerSoundRightSpecial() {
		return buzzerSoundRightSpecial;
	}
	public static String getBuzzerSoundLeft() {
		return buzzerSoundLeft;
	}
	public static void setBuzzerSoundLeft(String buzzerSoundLeft) {
		SoundManager.buzzerSoundLeft = buzzerSoundLeft;
	}
	public static String getBuzzerSoundRight() {
		return buzzerSoundRight;
	}
	public static void setBuzzerSoundRight(String buzzerSoundRight) {
		SoundManager.buzzerSoundRight = buzzerSoundRight;
	}
	
}
