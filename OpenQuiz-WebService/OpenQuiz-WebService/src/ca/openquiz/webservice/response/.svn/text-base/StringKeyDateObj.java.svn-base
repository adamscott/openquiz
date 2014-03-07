package ca.openquiz.webservice.response;

import java.util.Date;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

import com.google.appengine.api.datastore.Key;

@XmlRootElement
public class StringKeyDateObj extends StringKeyPair {
	
	@XmlTransient
	protected Date availableDate = null;
	
	public StringKeyDateObj() {
		
	}
	
	public StringKeyDateObj(Key key, String string, Date availableDate) {
		this.key = key;
		this.string = string;
		this.availableDate = availableDate;
	}
	
	@XmlElement(name="availableDate")
	public Date getAvailableDate() {
		return this.availableDate;
	}

	public void setAvailableDate(Date availableDate) {
		this.availableDate = availableDate;
	}
	
}
