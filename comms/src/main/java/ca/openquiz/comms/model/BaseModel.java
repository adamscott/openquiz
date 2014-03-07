package ca.openquiz.comms.model;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonProperty;


public class BaseModel implements Serializable{
	private static final long serialVersionUID = 1580584862747716399L;
	@JsonProperty
	protected String key;

	public String getKey()
	{
		return key;
	};
}
