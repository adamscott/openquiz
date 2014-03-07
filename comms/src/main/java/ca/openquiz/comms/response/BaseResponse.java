package ca.openquiz.comms.response;

import org.codehaus.jackson.annotate.JsonProperty;

public class BaseResponse {

	private String error;

	@JsonProperty
	private boolean completed;
	
	public boolean isCompleted(){
		return completed;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public void setCompleted(boolean completed) {
		this.completed = completed;
	}

}
