package ca.openquiz.comms.response;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

public class StringKeyDateObj extends StringKeyPair {
	
	protected Date availableDate = null;
	
	public StringKeyDateObj() {
		
	}
	
	public StringKeyDateObj(String key, String string, Date availableDate) {
		this.key = key;
		this.string = string;
		this.availableDate = availableDate;
	}
	
	@JsonProperty("availableDate")
	public Date getAvailableDate() {
		return this.availableDate;
	}

	public void setAvailableDate(Date availableDate) {
		this.availableDate = availableDate;
	}
}
