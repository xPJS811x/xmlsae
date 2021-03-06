package control;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.File;
import java.io.FileInputStream;
import java.util.Properties;

public class TextSymbols {

	public static final int APPLICATION_NAME = 1, FILES_DOWNLOAD_FAILED = 2, EXPORT_LOG = 3, IMPORT_LOG = 4,
			SELECT_AT_LEAST = 5, EXPORT_DATABASE = 6, IMPORT_DATABASE = 7, EXPORT = 8, IMPORT = 9, EXPORT_DOTS = 10,
			EXPORT_DATA = 11, EXPORT_DEFINITION = 12, EXPORT_SAVE_TO = 13, EXPORT_SAVE_TO_SELECT = 14,
			IMPORT_NO_FILES = 15, LOGIN_USER = 16, LOGIN_HOST = 17, LOGIN_PASSWORD = 18, LOGIN_SUBMIT = 19,
			LOGIN_CONNECTION_FAILED = 20, MESSAGE_DETAILS = 21, MESSAGE_CONFIRM = 22, SELECTION_SEARCH = 23,
			STATUS_CONNECTED = 24, STATUS_DATABASES = 25, DATABASE_FETCH_FAILED = 26, CHOOSE_LANGUAGE_TITLE = 27, CHOOSE_LANGUAGE_ASK = 28, CHOOSE_LANGUAGE_DONE = 29;

	protected static Properties programSettings, text;
	protected static String language;

	static {
		text = new Properties();
	}

	public static void init(Properties programSettings) {
		TextSymbols.programSettings = programSettings;
	}

	public static String get(int symbol) {
		return text.getProperty(String.valueOf(symbol));
	}

	public static String getLanguage() {
		return TextSymbols.language;
	}

	public static void setLanguage(String language) {

		if (language == null || language.isEmpty()) {
			return;
		}

		try {
			Reader reader = new InputStreamReader(new FileInputStream(new File("lang/" + language.toLowerCase())), "UTF-8");
			text.load(reader);
			TextSymbols.language = language;
			programSettings.setProperty("language", language);

			Control.saveSettings();

		} catch (IOException e) {
			e.printStackTrace();
			return;
		}

	}

}
