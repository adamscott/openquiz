package ca.openquiz.webservice.converter;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

public class KeyConverter extends XmlAdapter<String, Key> {

	@Override
	public String marshal(Key val) throws Exception {
		if(val != null && val.isComplete()){
			return KeyFactory.keyToString(val);
		}
		return "";
	}

	@Override
	public Key unmarshal(String val) throws Exception {
		return KeyFactory.stringToKey(val);
	}
	
}