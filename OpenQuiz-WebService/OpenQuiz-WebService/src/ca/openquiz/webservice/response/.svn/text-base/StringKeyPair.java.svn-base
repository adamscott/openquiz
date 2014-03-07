package ca.openquiz.webservice.response;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import ca.openquiz.webservice.converter.KeyConverter;

import com.google.appengine.api.datastore.Key;

@XmlRootElement
public class StringKeyPair {
	
	@XmlTransient
	protected Key key = null;
	
	@XmlTransient
	protected String string = null;
	
	public StringKeyPair() {
		
	}
	
	public StringKeyPair(Key key, String string) {
		this.key = key;
		this.string = string;
	}
	
	@XmlJavaTypeAdapter(KeyConverter.class)
	public Key getKey() {
		return this.key;
	}
	
	public void setKey(Key key) {
		this.key = key;
	}

	@XmlElement(name="name")
	public String getString() {
		return this.string;
	}

	public void setString(String str) {
		this.string = str;
	}

	
}
