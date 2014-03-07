package openquiz.website.util;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class Messages {
	private static final String BUNDLE_NAME = "openquiz.website.util.messages"; //$NON-NLS-1$
	public static Locale DEFAULT_LOCALE = Locale.FRENCH;
	private static ResourceBundle RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME,DEFAULT_LOCALE);
	
	private Messages() {
	}
	
	public static void setLanguage(Locale locale){
		RESOURCE_BUNDLE = ResourceBundle.getBundle(BUNDLE_NAME,locale);
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
