package comms;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig.Feature;
import org.junit.Ignore;
import org.junit.Test;

import ca.openquiz.comms.RequestsWebService;
import ca.openquiz.comms.enums.Degree;
import ca.openquiz.comms.enums.Language;
import ca.openquiz.comms.enums.QuestionTarget;
import ca.openquiz.comms.enums.QuestionType;
import ca.openquiz.comms.model.Category;
import ca.openquiz.comms.model.Choice;
import ca.openquiz.comms.model.Game;
import ca.openquiz.comms.model.GameLog;
import ca.openquiz.comms.model.GameStat;
import ca.openquiz.comms.model.Group;
import ca.openquiz.comms.model.Question;
import ca.openquiz.comms.model.QuestionAnagram;
import ca.openquiz.comms.model.QuestionAssociation;
import ca.openquiz.comms.model.QuestionCorrectWord;
import ca.openquiz.comms.model.QuestionGeneral;
import ca.openquiz.comms.model.QuestionIdentification;
import ca.openquiz.comms.model.QuestionIntru;
import ca.openquiz.comms.model.QuestionSet;
import ca.openquiz.comms.model.Session;
import ca.openquiz.comms.model.Team;
import ca.openquiz.comms.model.Template;
import ca.openquiz.comms.model.TemplateSection;
import ca.openquiz.comms.model.Tournament;
import ca.openquiz.comms.model.User;
import ca.openquiz.comms.parameter.CreateUserParam;
import ca.openquiz.comms.response.BaseResponse;
import ca.openquiz.comms.response.KeyResponse;
import ca.openquiz.comms.response.TournamentListResponse;
import ca.openquiz.comms.response.UserResponse;

public class RequestsWebServiceTestCase {
	
	public static final String LOGIN_EMAIL = "test1@gmail.com";
	public static final String LOGIN_PASSWORD = "test";
	
	public static final String JUNIT_PREFIX = "JUNIT_TESTING_";
	
	@Test
	public void testGetLogged() throws Exception{
		String authorization = RequestsWebService.getAuthorization(LOGIN_EMAIL, "LOGIN_PASSWORD");
		
		List<Question> questions = RequestsWebService.getQuestions(QuestionType.Anagram, authorization);
		assertNotNull(questions);
		getQuestionKey(questions,QuestionAnagram.class);
		assertNotNull(RequestsWebService.getQuestionAnagram(getQuestionKey(questions, QuestionAnagram.class), authorization));
		assertNotNull(RequestsWebService.getQuestionAssociation(getQuestionKey(questions, QuestionAssociation.class), authorization));
		assertNotNull(RequestsWebService.getQuestionCorrectWord(getQuestionKey(questions, QuestionCorrectWord.class), authorization));
		assertNotNull(RequestsWebService.getQuestionGeneral(getQuestionKey(questions, QuestionGeneral.class), authorization));
		assertNotNull(RequestsWebService.getQuestionIdentification(getQuestionKey(questions, QuestionIdentification.class), authorization));
		assertNotNull(RequestsWebService.getQuestionIntru(getQuestionKey(questions, QuestionIntru.class), authorization));

		List<Question> anagramQuestions = RequestsWebService.getQuestions(QuestionType.Anagram, authorization);
		List<Question> associationQuestions = RequestsWebService.getQuestions(QuestionType.Association, authorization);
		List<Question> generalQuestions = RequestsWebService.getQuestions(QuestionType.General, authorization);
		List<Question> identificationQuestions = RequestsWebService.getQuestions(QuestionType.Identification, authorization);
		List<Question> intruQuestions = RequestsWebService.getQuestions(QuestionType.Intru, authorization);
		List<Question> mediaQuestions = RequestsWebService.getQuestions(QuestionType.Media, authorization);
				
		assertNotNull(RequestsWebService.getQuestionAnagram(getQuestionKey(anagramQuestions, QuestionAnagram.class), authorization));
		assertNotNull(RequestsWebService.getQuestionAssociation(getQuestionKey(associationQuestions, QuestionAssociation.class), authorization));
		assertNotNull(RequestsWebService.getQuestionCorrectWord(getQuestionKey(generalQuestions, QuestionCorrectWord.class), authorization));
		assertNotNull(RequestsWebService.getQuestionGeneral(getQuestionKey(identificationQuestions, QuestionGeneral.class), authorization));
		assertNotNull(RequestsWebService.getQuestionIdentification(getQuestionKey(intruQuestions, QuestionIdentification.class), authorization));
		assertNotNull(RequestsWebService.getQuestionIntru(getQuestionKey(mediaQuestions, QuestionIntru.class), authorization));
	}
	
	@Test
	public void testGetCategory() throws Exception{
		String authorization = RequestsWebService.getAuthorization(LOGIN_EMAIL, "LOGIN_PASSWORD");
		List<Category> categories = RequestsWebService.getCategories(authorization);

		for(Category item : categories){
			assertNotNull(RequestsWebService.getCategory(item.getKey()));
			
		}
	}
	
	@Test
	public void testGetUsersByTeam() throws Exception{
		List<User> users = RequestsWebService.getUsers();
		assertNotNull(users);
		User user = null;
		for(User item : users){
			if(item.getTeams() != null && item.getTeams().size() > 0){
				user = item;
				break;
			}
		}
		assertNotNull(user);
		String teamKey = user.getTeams().get(0);
		List<User> users2 = RequestsWebService.getUsersByTeam(teamKey, 1, 5).getUsers();
		
		for(User u : users2){
			assertTrue(u.getTeams().contains(teamKey));
		}
		
	}
	
	@Test 
	public void testGetGameLogs(){
		List<GameLog> logs = RequestsWebService.getGameLogs(null, null);
		assertNotNull(logs);
		assertTrue(logs.size() > 0);
		List<Game> games = RequestsWebService.getGames();
		games = RequestsWebService.getGames(false, 50, 1, null);
		return;
	}
	
	@Test 
	public void testGetGames(){
		List<Game> games = RequestsWebService.getGames();
		Game game = games.get(0);
		assertNotNull(game);
		assertNotNull(game.getTeam1());
		assertNotNull(game.getTeam2());
		assertNotNull(game.getTeamKeys());
		assertEquals(game.getTeamKeys().size(), 2);
	}
	
	@Test
	public void testGetGameStats(){
		Game game = RequestsWebService.getGames(false, 5, 1, null).get(0);
		List<GameStat> stats = RequestsWebService.getGameStats(game.getTeam1Players().get(0), null, 5, 1);
		assertNotNull(stats);
		assertNotEquals(stats.size(), 0);
	}
	
	@Test 
	public void testTemplates() throws Exception{
		String templateToDelete = null;
		String authorization = RequestsWebService.getAuthorization("testgroups122@gmail.com", "test");
		try{
			String categoryKey = RequestsWebService.getCategories(authorization).get(0).getKey();
			
			//Test add
			Template template1 = new Template();
			template1.setName("name1");
			
			TemplateSection section = new TemplateSection(categoryKey,Degree.easy,3,10,QuestionType.Anagram,QuestionTarget.Collectif, "description 1");
			template1.getSectionList().add(section);
			KeyResponse response = RequestsWebService.addTemplate(template1, authorization);
			assertNotNull(response);
			assertNotNull(response.getKey());
			templateToDelete=response.getKey();
			
			Template template2 = RequestsWebService.getTemplate(response.getKey());
			assertEquals(template1.getName(), template2.getName());
			assertEquals(1, template2.getSectionList().size());
			
			//Test edit
			TemplateSection section2 = new TemplateSection(categoryKey,Degree.easy,3,10,QuestionType.Anagram,QuestionTarget.Collectif, "description 2");
			template2.getSectionList().add(section2);
			template2.setName("name2");
			assertTrue(RequestsWebService.editTemplate(template2, authorization));
			
			Template template3 = RequestsWebService.getTemplate(template2.getKey());
			assertEquals(template2.getName(),template3.getName());
			assertEquals(2, template3.getSectionList().size());
			template3.getSectionList().remove(0);
			assertTrue(RequestsWebService.editTemplate(template3, authorization));
			Template template4 = RequestsWebService.getTemplate(template3.getKey());
			assertEquals(1, template4.getSectionList().size());
			
			//Test delete`
			assertTrue(RequestsWebService.deleteTemplate(template3.getKey(), authorization));
			assertNull(RequestsWebService.getTemplate(template3.getKey()));
		}catch(Exception e){
			throw e;
		} finally{
			try{
				RequestsWebService.deleteTemplate(templateToDelete, authorization);
			}catch(Exception e){
				//DO nothing, just try to delete it just in case test failed somewhere
			}
		}
	}
	
	@Test
	public void testEditUser() throws Exception{
		CreateUserParam createUserParam = createTestUser();
		User initUser = null;
		String newPassword = "qwerty1";
		try{
		
		String authorization = RequestsWebService.getAuthorization(createUserParam.getEmail(), createUserParam.getPassword());
		initUser = RequestsWebService.getUserByEmail(createUserParam.getEmail());
		assertNotNull(initUser);
		
		createUserParam.setEmail(generateRandomName() + "@junit.test");
		createUserParam.setFirstName(generateRandomName());
		createUserParam.setLastName(generateRandomName());
		createUserParam.setLanguage(Language.en);
		createUserParam.setPassword(newPassword);
		createUserParam.setBirthDate(new Date(1383679099L));
		
		
		BaseResponse response = RequestsWebService.editUser(createUserParam, initUser.getKey(), authorization);
		assertNotNull(response);
		assertNull(response.getError());
		assertTrue(response.isCompleted());
		
		User editedUser = RequestsWebService.getUser(initUser.getKey());
		
		assertNotNull(editedUser);
		assertEquals(createUserParam.getFirstName(), editedUser.getFirstName());
		assertEquals(createUserParam.getLastName(), editedUser.getLastName());
		assertEquals(createUserParam.getLanguage(), editedUser.getLanguage());
		assertTrue(createUserParam.getBirthDate().compareTo(editedUser.getBirthDate()) == 0 );
		
		//Test new credentials:
		UserResponse userResponse = RequestsWebService.getCurrentUserInfo(RequestsWebService.getAuthorization(createUserParam.getEmail(), createUserParam.getPassword()));
		assertNotNull(userResponse);
		assertNotNull(userResponse.getEmail());
		assertEquals(userResponse.getEmail().toLowerCase(), createUserParam.getEmail().toLowerCase());
		
		//Test the email was changed 2 another way because email is not returned  with getUser()
		editedUser = RequestsWebService.getUserByEmail(createUserParam.getEmail());
		assertNotNull(editedUser);

		}catch(Exception e){
			throw e;
		}finally{
			if(initUser != null){
				RequestsWebService.deleteUser(initUser.getKey(), RequestsWebService.getAuthorization(createUserParam.getEmail(), createUserParam.getPassword()));
				RequestsWebService.deleteUser(initUser.getKey(), newPassword);
			}
		}
	}
	
	@Test
	public void testGetUserByEmail() {
		CreateUserParam createUserParam = createTestUser();
		User user = RequestsWebService.getUserByEmail(createUserParam.getEmail());
		assertNotNull(user);
		assertEquals(createUserParam.getFirstName(),user.getFirstName());
		assertEquals(createUserParam.getLastName(),user.getLastName());
		
		//Test if the user doesnt exist...
		user = RequestsWebService.getUserByEmail(generateRandomName() + "@test.com");
		assertNull(user);
		
	}
	
	@Test 
	public void testGetEditGame(){
		//This id will not always be valid, check if this game exist
		String key = "ahFzfm9wZW5xdWl6cHJvamVjdHIRCxIER2FtZRiAgICAwIK0CAw";
		Game game = RequestsWebService.getGame(key);
		assertNotNull(game);
		BaseResponse response = RequestsWebService.editGame(game);
		assertNotNull(response);
	}
	
	@Test
	public void testGetGroup() {
		List<Group> groups = RequestsWebService.getGroups();
		Group tmpGroup = RequestsWebService.getGroup(groups.get(0).getKey());
		assertNotNull(tmpGroup);
	}
	
	@Test
	public void testTournamentAddGet(){
		Tournament t = new Tournament();
		t.setAddress(generateRandomName());
		t.setCity(generateRandomName());
		t.setCountry(generateRandomName());
		t.setEndDate(new Date());
		t.setName(generateRandomName());
		
		KeyResponse response = RequestsWebService.addTournament(t);
		verifySucessfulResponse(response);
		
		Tournament t2 = RequestsWebService.getTournament(response.getKey());
		assertNotNull(t2);
		
		TournamentListResponse listResponse = RequestsWebService.getTournaments(null,null,null);
		assertTrue(listResponse.isCompleted());
		assertNotNull(listResponse.getTournaments());
		assertNotEquals(0,listResponse.getTournaments().size());
		
		//Test name parameter
		listResponse = RequestsWebService.getTournaments(null, null, t.getName());
		assertTrue(listResponse.isCompleted());
		assertNotNull(listResponse.getTournaments());
		assertEquals(1,listResponse.getTournaments().size());
		
		listResponse = RequestsWebService.getTournaments(null, null, generateRandomName());
		assertTrue(listResponse.isCompleted());
		assertNotNull(listResponse.getTournaments());
		assertEquals(0,listResponse.getTournaments().size());
	}

	@Test
	public void testSession(){
		String auth = RequestsWebService.getAuthorization("test1@gmail.com", "test");
		Session session = RequestsWebService.login(auth);
		assertFalse(session.isExpired());
	}
	
	@Test
	public void testLoginLogout(){
		CreateUserParam createUser = new CreateUserParam();
		createUser.setBirthDate(new Date());
		createUser.setEmail(generateRandomName() + "@junit.test");
		createUser.setFirstName(generateRandomName());
		createUser.setLanguage(Language.fr);
		createUser.setLastName(generateRandomName());
		createUser.setPassword("junit123");
		
		KeyResponse response = RequestsWebService.addUser(createUser);
		verifySucessfulResponse(response);
		String auth = RequestsWebService.getAuthorization(createUser.getEmail(), createUser.getPassword());
		Session session = RequestsWebService.login(auth);
		assertFalse(session.isExpired());
		
		QuestionAnagram question = new QuestionAnagram();
		question.setAnagram("qe");;
		question.setAnswer("asd");
		question.setAuthorKey(response.getKey());
		question.setAvailableDate(new Date());
		//question.setCategory(category);
		
		response = RequestsWebService.addQuestion(new QuestionAssociation(), null);
		assertNull(response);
		RequestsWebService.setCurrentSessionId(session.getSessionKey());
		response = RequestsWebService.addQuestion(new QuestionAssociation(), auth);
		assertNotNull(response);
		
		RequestsWebService.logout(auth);
		response = RequestsWebService.addQuestion(new QuestionAssociation(), null);
		assertNull(response);
	}
	
	@Test
	@Ignore
	//This test should not be ran often because it gets all the questions set from the database
	public void testGetQuestionSetById() throws Exception {
		List<QuestionSet> questionSets = RequestsWebService.getQuestionSets();
		assertNotNull(questionSets);
		for(int i = 0; i < questionSets.size(); i++){
			assertNotNull(RequestsWebService.getQuestionSetById(questionSets.get(i).getKey()));
		}
	}
	
	@Test
	public void testGetNotLogged() throws Exception{
		String authorization = RequestsWebService.getAuthorization("testgroups122@gmail.com", "test");
		assertNotNull(RequestsWebService.getCategories(authorization));

		assertNotNull(RequestsWebService.getGroups());
		
		List<QuestionSet> questionSets = RequestsWebService.getQuestionSets();
		assertNotNull(questionSets);
		assertNotNull(RequestsWebService.getQuestionSetById(getAnyQuestionSetId(questionSets)));
		assertNotNull(RequestsWebService.getQuestionSets());
		List<User> users = RequestsWebService.getUsers();
		assertNotNull(users);
		assertNotNull(RequestsWebService.getTeam(getAnyTeamKey(users)));
		assertNotNull(RequestsWebService.getUser(users.get(0).getKey()));
		List<Template> templates = RequestsWebService.getTemplates();
		assertNotNull(RequestsWebService.getTemplate(getAnyTemplate(templates)));
		assertNotNull(templates);
		assertNotNull(RequestsWebService.getQuestionNbInTemplateSection(getAnyTemplateSection(templates)));
	}
	
	@Test
	public void testAddGame() throws Exception{
		
		Game game = new Game();
		game.setActive(true);
		game.setGameDate(new Date());
		List<Integer> scores = new ArrayList<Integer>();
		game.setTeam1Score(1);
		game.setTeam2Score(3);
		KeyResponse response = RequestsWebService.addGame(game);
		verifySucessfulResponse(response);
		
		
		/*RequestsWebService.addGameLog(gameLog)
		RequestsWebService.addQuestion(question, authorization)
		RequestsWebService.addUser(userParam)*/
	}
	
	@Test
	public void testGenerateQuestionSet(){
		RequestsWebService.getAuthorization("team1player0@gmail.com", "test");
		List<Template> list = RequestsWebService.getTemplates();
		Template template = null;
		for(Template item : list){
			Template temp = RequestsWebService.getTemplate(item.getKey());
			if(temp.getSectionList().size() > 0){
				template = temp;
				break;
			}
		}
		
		QuestionSet response = RequestsWebService.generateQuestionSet(template, null, false);
		
		assertNotNull(response);
	}
	
	private void verifySucessfulResponse(KeyResponse response) {
		assertNotNull(response);
		assertTrue(response.isCompleted());
		assertNull(response.getError());
		assertNotNull(response.getKey());
	}

	@Test
	public void testAddUser() throws Exception{
		KeyResponse response= null;
		CreateUserParam user = new CreateUserParam();
		try{
			
			user.setFirstName(generateRandomName());
			user.setLastName(generateRandomName());
			user.setEmail(generateRandomName() + "@junit.test");
			user.setBirthDate(new Date());
			user.setPassword("junit666");
			user.setLanguage(Language.fr);
			assertTrue(user.isValid());
			
			response = RequestsWebService.addUser(user);
			verifySucessfulResponse(response);
			
			User user2 = RequestsWebService.getUser(response.getKey());
			assertNotNull(user2);
			assertEquals(user.getFirstName(), user2.getFirstName());
			assertEquals(user.getLastName(), user2.getLastName());
			assertNull(user2.getEmail());
			assertEquals(user.getBirthDate(), user2.getBirthDate());
		
			//Delete the user
			assertTrue(RequestsWebService.deleteUser(response.getKey(), RequestsWebService.getAuthorization(user.getEmail(), user.getPassword())));
			assertNull(RequestsWebService.getUser(response.getKey()));
			
		}catch(Exception e){
			throw e;
		}finally{
			if(response != null){
				RequestsWebService.deleteUser(response.getKey(), RequestsWebService.getAuthorization(user.getEmail(), user.getPassword()));
			}
		}
	}
	
	@Test
	public void testAddGroupTeam() throws Exception{
		
		String authorization = RequestsWebService.getAuthorization("testgroups122@gmail.com", "test");
		KeyResponse groupResponse = null;
		KeyResponse teamResponse = null;
		try{
			Group group = new Group();
			group.setName(generateRandomName());
			groupResponse = RequestsWebService.addGroup(group, authorization);
			verifySucessfulResponse(groupResponse);
			
			group = RequestsWebService.getGroup(groupResponse.getKey());
			assertNotNull(group);
			
			Team team = new Team();
			team.setName(generateRandomName());
			team.setGroupKey(groupResponse.getKey());
			team.setActive(true);
			teamResponse = RequestsWebService.addTeam(team, authorization);
			verifySucessfulResponse(teamResponse);
			
			//Verify team is created
			team = RequestsWebService.getTeam(teamResponse.getKey());
			assertNotNull(team);
			
			//Now try to delete the team
			assertTrue(RequestsWebService.deleteTeam(team.getKey(), authorization));
			team = RequestsWebService.getTeam(teamResponse.getKey());
			assertNotNull(team);
			assertFalse(team.isActive());
			
		} catch(Exception e){
			throw e;
		} finally{
			if(groupResponse != null && groupResponse.getKey() != null) RequestsWebService.deleteGroup(groupResponse.getKey(), authorization);
			if(teamResponse != null && teamResponse.getKey() != null) RequestsWebService.deleteTeam(teamResponse.getKey(), authorization);
		}
	}
	
	@Test
	public void testUserSerialization() throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		mapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd kk:mm:ss"));
        mapper.configure(
                DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY,
                true);
        mapper.configure(Feature.WRITE_DATES_AS_TIMESTAMPS, false);
        
        CreateUserParam user = new CreateUserParam();
		user.setFirstName(generateRandomName());
		user.setLastName(generateRandomName());
		user.setEmail(generateRandomName() + "@junit.test");
		user.setBirthDate(new Date());
		user.setPassword("junit666");
		user.setLanguage(Language.fr);
		String str = mapper.writeValueAsString(user);
		System.out.println(str);
	}
	
	@Test
	public void testAddQuestion() throws Exception {
		CreateUserParam user = createTestUser();
		
		String authorization = RequestsWebService.getAuthorization(user.getEmail(), user.getPassword());
		Session session = RequestsWebService.login(authorization);
		RequestsWebService.setCurrentSessionId(session.getSessionKey());
		
		//QuestionAssociation
		QuestionAssociation questionAssociation = new QuestionAssociation();
		questionAssociation.setAttemptedAnswer(1);
		questionAssociation.setAvailableDate(new Date());
		questionAssociation.setCorrectAnswer(2);
		ArrayList<Choice> choices = new ArrayList<Choice>();
		choices.add(new Choice("test", "Test")); 
		questionAssociation.setChoices(choices);
		KeyResponse response = RequestsWebService.addQuestion(questionAssociation, authorization);
		verifySucessfulResponse(response);
	}
	
	private CreateUserParam createTestUser() {
		CreateUserParam user = new CreateUserParam();
		user.setFirstName(generateRandomName());
		user.setLastName(generateRandomName());
		user.setEmail(generateRandomName() + "@junit.test");
		user.setBirthDate(new Date());
		user.setPassword("junit666");
		user.setLanguage(Language.fr);
		assertTrue(user.isValid());
		
		KeyResponse response = RequestsWebService.addUser(user);
		verifySucessfulResponse(response);
		
		return user;
	}

	@Test
	public void testAddGameLog() throws Exception{
		GameLog gameLog = new GameLog();
		gameLog.setAnswer(true);
		gameLog.setPoints(123);
		gameLog.setResponseTime(123332);
		KeyResponse response = RequestsWebService.addGameLog(gameLog);
		verifySucessfulResponse(response);
	}
	
	
	private static String getAnyTemplate(List<Template> templates) throws Exception {
		if(templates.size() > 0)
			return templates.get(0).getKey();
		throw new Exception("No templates found :(");
	}

	private static String getAnyQuestionSetId(List<QuestionSet> questionSets) throws Exception {
		for(int i = 0; i < questionSets.size(); i++ ){
			if(questionSets.get(i).getKey() != null)
				return questionSets.get(i).getKey();
		}
		throw new Exception("No question set found :(");
	}

	private static TemplateSection getAnyTemplateSection(
			List<Template> templates) throws Exception {
		for(int i = 0; i < templates.size(); i++ ){
			Template template = templates.get(i);
			template = RequestsWebService.getTemplate(template.getKey());
			if(template.getSectionList() != null && !template.getSectionList().isEmpty()){
				return template.getSectionList().get(0);
			}
		}
		throw new Exception("No template section found :(");
	}

	private static String getAnyTeamKey(List<User> users) throws Exception {
		for(int i = 0; i < users.size(); i++ ){
			User user = users.get(i);
			if(user.getTeams() != null && !user.getTeams().isEmpty()){
				return user.getTeams().get(0);
			}
		}
		throw new Exception("No users are part of a team");
	}

	private static String getQuestionKey(List<Question> questions,
			Class<?> questionType) throws Exception {
		
		for(int i = 0; i < questions.size(); i++ ){
			if(questions.get(i).getClass() .equals( questionType))
				return questions.get(i).getKey();
		}
		throw new Exception("No questions of this type found :(");
	}

	private static String generateRandomName() {
		Random randomGenerator = new Random();
		int randomInt = Math.abs(randomGenerator.nextInt());
		return JUNIT_PREFIX + randomInt;
	}

}
