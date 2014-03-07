package ca.openquiz.webservice.model;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.Inheritance;
import javax.jdo.annotations.InheritanceStrategy;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import ca.openquiz.webservice.converter.JsonKeyConverter;
import ca.openquiz.webservice.converter.KeyConverter;

import com.google.appengine.api.datastore.Key;

@PersistenceCapable
@Inheritance(strategy = InheritanceStrategy.SUBCLASS_TABLE)
public abstract class  BaseModel 
{
	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY)
	@XmlJavaTypeAdapter(KeyConverter.class)
	@JsonSerialize(using = JsonKeyConverter.class)
	protected Key key;
	
	public Key getKey()
	{
		return key;
	};
	
	public abstract void delete();
	
	public abstract boolean isValid();
	
}
