package serialization;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Test;

import ca.openquiz.comms.enums.Degree;
import ca.openquiz.comms.model.QuestionAnagram;
import ca.openquiz.comms.model.QuestionWrapper;
import structures.QuestionTest;

public class SerializationTestCase {

	private static ObjectMapper mapper;
	static{
	    mapper = new ObjectMapper();
        mapper.configure(
                DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY,
                true);
	}
	
	@Test
	public void testGameDataSerializationObject() throws JsonGenerationException, JsonMappingException, IOException{
		
		//Populate dat object with shittlzes
		HashMap<String, ArrayList<QuestionTest>> leftPlayerQuestionsAnswered = new HashMap<String, ArrayList<QuestionTest>>();
		ArrayList<QuestionTest> questionList = new ArrayList<QuestionTest>();
		questionList.add(generateQuestionTest());
		questionList.add(generateQuestionTest());
		
		leftPlayerQuestionsAnswered.put("asd", questionList);
		GameDataSerializationObject obj = new GameDataSerializationObject();
		//obj.setLeftPlayerQuestionsAnswered(leftPlayerQuestionsAnswered);
		
		String jsonData = mapper.writeValueAsString(obj);
		
		System.out.println(jsonData);
		
		GameDataSerializationObject obj2 = mapper.readValue(jsonData, GameDataSerializationObject.class);
	}
	
	@Test
	public void testQuestionTest() throws JsonGenerationException, JsonMappingException, IOException {
		
		
		QuestionTest test = generateQuestionTest();
		
		String jsonData = mapper.writeValueAsString(test);
		
		System.out.println(jsonData);
		
		QuestionTest obj2 = mapper.readValue(jsonData, QuestionTest.class);
	}
	
	@Test 
	public void testQuestionWrapper() throws JsonGenerationException, JsonMappingException, IOException {
		QuestionAnagram questionAnagram = new QuestionAnagram();
		questionAnagram.setAnagram("asdas");
		questionAnagram.setAnswer("dasda");
		questionAnagram.setAuthorKey("asdasdasg234242");
		questionAnagram.setDegree(Degree.normal);
		QuestionWrapper wrapper = new QuestionWrapper();
		wrapper.addQuestion(questionAnagram);
		
		String jsonData = mapper.writeValueAsString(wrapper);
		
		System.out.println(jsonData);
		
		QuestionWrapper obj2 = mapper.readValue(jsonData, QuestionWrapper.class);
	}
	
	public QuestionTest generateQuestionTest(){
		QuestionTest q = new QuestionTest();
		//q.question = new QuestionWrapper();
		QuestionAnagram questionAnagram = new QuestionAnagram();
		questionAnagram.setAnagram("asdas");
		questionAnagram.setAnswer("dasda");
		questionAnagram.setAuthorKey("asdasdasg234242");
		questionAnagram.setDegree(Degree.normal);
		ArrayList<QuestionAnagram> questions = new ArrayList<QuestionAnagram>();
		questions.add(questionAnagram);
		
		//q.question.setQuestionAnagram(questions);
		return q;
	}
}
