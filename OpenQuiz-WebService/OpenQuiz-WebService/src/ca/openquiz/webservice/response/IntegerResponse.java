package ca.openquiz.webservice.response;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class IntegerResponse extends BaseResponse {

	private Integer value;

	public IntegerResponse(){
		
	}
	
	public IntegerResponse(int i){
		value = i;
	}
	
	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}
	
}
