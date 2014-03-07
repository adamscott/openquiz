package ca.openquiz.webservice.response;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ImportKeysResponse extends BaseResponse 
{
	private List<String> keys = new ArrayList<String>();
	
	private List<String> linesInError = new ArrayList<String>();

	@XmlElementWrapper(name = "linesInError")
	public  List<String> getLinesInError() {
		return linesInError;
	}

	public void setgetLinesInError( List<String> linesInError) {
		this.linesInError = linesInError;
	}
	
	
	@XmlElementWrapper(name = "keys")
	public  List<String> getKeys() {
		return keys;
	}

	public void setKeys( List<String> keys) {
		this.keys = keys;
	}
	
}
