package ca.openquiz.webservice.converter;

import java.io.IOException;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.map.SerializerProvider;
import org.codehaus.jackson.map.ser.std.SerializerBase;

public class JsonKeyConverter extends SerializerBase<Key> {

	public JsonKeyConverter() {
		super(Key.class);
	}

	@Override
	public void serialize(Key value, JsonGenerator jgen, SerializerProvider provider)
			throws IOException, JsonGenerationException {
		if(value != null && value.isComplete()){
			jgen.writeString(KeyFactory.keyToString(value));
		}
	}

}