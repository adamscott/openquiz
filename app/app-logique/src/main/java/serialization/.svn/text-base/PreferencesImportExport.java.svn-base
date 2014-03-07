package serialization;

import java.io.FileNotFoundException;

import javax.xml.bind.JAXBException;

import applicationTools.FileManager;

public class PreferencesImportExport {

	private static PreferencesImportExport INSTANCE = null;
	private static PreferencesSerializationObject pref;
	private static String prefFilePath = "pref.xml";
	
	public static PreferencesImportExport getInstance()
	{	
		if (INSTANCE == null)
		{ 
			INSTANCE = new PreferencesImportExport();
		}
		return INSTANCE;
	}

	public PreferencesImportExport(){
		pref = new PreferencesSerializationObject();
	}
	
	public void savePreferencesInFile(String username){
		pref.setRememberedUsername(username);
		
		try {
			SerializationManager.getInstance().serializeObject(pref, prefFilePath);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void loadPreferencesFromFile(){
		try {
			if(FileManager.getInstance().checkIfFileExist(prefFilePath)){
				pref = (PreferencesSerializationObject) SerializationManager.getInstance().deserializeObject(PreferencesSerializationObject.class, prefFilePath);
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch ( JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public String getRememberedUsername(){
		if (pref.getRememberedUsername().equals("")){
			loadPreferencesFromFile();
		}
		return pref.getRememberedUsername();
	}
}
