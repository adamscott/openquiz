package serialization;

import javax.xml.bind.annotation.XmlRootElement;


@XmlRootElement(name = "Preferences")
public class PreferencesSerializationObject {
	
	private String rememberedUsername;
	
	public String getRememberedUsername(){
		return rememberedUsername;
	}
	
	public void setRememberedUsername(String rememberedUsername){
		this.rememberedUsername = rememberedUsername;
	}

}
