package serialization;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

import applicationTools.FileManager;

public class SerializationManager {
	
	private static SerializationManager INSTANCE = null;
	
	public static SerializationManager getInstance()
	{	
		if (INSTANCE == null)
		{ 
			INSTANCE = new SerializationManager();
		}
			return INSTANCE;
	}
	
	private static ObjectMapper mapper;
	static{
	    mapper = new ObjectMapper();
        mapper.configure(
                DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY,
                true);
	}
	
	public String serializeObject(Object serializableObj){
		String jsonString = null;
	    try {
			jsonString = mapper.writeValueAsString(serializableObj);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    
	    return jsonString;
	}

	public void serializeObject(Object serializableObj, String pathname) throws JAXBException, FileNotFoundException{
	    
		String jsonString = null;
	    try {
			jsonString = mapper.writeValueAsString(serializableObj);
		} catch (JsonGenerationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	    if(jsonString != null){
		    FileManager.getInstance().createFile(pathname);
		    FileManager.getInstance().write(pathname, jsonString);
	    }
	}
	
	public Object deserializeObjectFromString(Class<?> deserializableObj, String marshalledString){
		Object marshalledObject = new Object();
		
		try {
			marshalledObject = (Object) mapper.readValue(marshalledString,deserializableObj);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return marshalledObject;
	}
	
	public Object deserializeObject(Class<?> deserializableObj, String pathname) throws JAXBException, FileNotFoundException{
		
		Object marshalledObject = new Object();
		
		if(FileManager.getInstance().checkIfFileExist(pathname)){
			String fileContent = FileManager.getInstance().read(pathname);
			
			try {
				marshalledObject = (Object) mapper.readValue(fileContent,deserializableObj);
			} catch (JsonParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JsonMappingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return marshalledObject;
	}
}
