package ca.openquiz.webservice.converter;

import java.util.Date;

import javax.xml.bind.annotation.adapters.XmlAdapter;

public class DateConverter extends XmlAdapter<String, Date> {

	@Override
	public String marshal(Date val) throws Exception {
		if(val != null ){
			return Long.toString(val.getTime());
		}
		return "";
	}

	@Override
	public Date unmarshal(String val) throws Exception {
		return new Date(Long.parseLong(val));
	}
	
}