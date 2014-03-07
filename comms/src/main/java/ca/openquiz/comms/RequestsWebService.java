package ca.openquiz.comms; 

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;

import ca.openquiz.comms.enums.QuestionType;
import ca.openquiz.comms.enums.Role;
import ca.openquiz.comms.model.Category;
import ca.openquiz.comms.model.CategoryWrapper;
import ca.openquiz.comms.model.Game;
import ca.openquiz.comms.model.GameLog;
import ca.openquiz.comms.model.GameStat;
import ca.openquiz.comms.model.Group;
import ca.openquiz.comms.model.GroupRole;
import ca.openquiz.comms.model.GroupWrapper;
import ca.openquiz.comms.model.Question;
import ca.openquiz.comms.model.QuestionAnagram;
import ca.openquiz.comms.model.QuestionAssociation;
import ca.openquiz.comms.model.QuestionCorrectWord;
import ca.openquiz.comms.model.QuestionGeneral;
import ca.openquiz.comms.model.QuestionIdentification;
import ca.openquiz.comms.model.QuestionIntru;
import ca.openquiz.comms.model.QuestionMedia;
import ca.openquiz.comms.model.QuestionSet;
import ca.openquiz.comms.model.Session;
import ca.openquiz.comms.model.Team;
import ca.openquiz.comms.model.Template;
import ca.openquiz.comms.model.TemplateSection;
import ca.openquiz.comms.model.Tournament;
import ca.openquiz.comms.model.User;
import ca.openquiz.comms.parameter.CreateUserParam;
import ca.openquiz.comms.response.BaseResponse;
import ca.openquiz.comms.response.GameListResponse;
import ca.openquiz.comms.response.GameLogListResponse;
import ca.openquiz.comms.response.GameStatListResponse;
import ca.openquiz.comms.response.IntegerResponse;
import ca.openquiz.comms.response.KeyResponse;
import ca.openquiz.comms.response.QuestionListResponse;
import ca.openquiz.comms.response.QuestionSetListResponse;
import ca.openquiz.comms.response.TemplateListResponse;
import ca.openquiz.comms.response.TournamentListResponse;
import ca.openquiz.comms.response.UserListResponse;
import ca.openquiz.comms.response.UserResponse;
import ca.openquiz.comms.response.UserResponseWrapper;
import ca.openquiz.comms.util.Base64;
import ca.openquiz.comms.util.PatchedStdDateFormat;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

public final class RequestsWebService
{	
	//Debug flags
	private static final boolean BYPASS_SSL_CERT = false;
	private static final boolean USE_DEV_SERVER = false;
	private static final boolean PRINT_DEBUG_INFO = false;
	private static final int TIMEOUT_LIMIT = 3;
	
	private static String PROTOCOL_GET = "GET";
	private static String PROTOCOL_POST = "POST";
	private static String PROTOCOL_PUT =  "PUT";
	private static String PROTOCOL_DELETE = "DELETE";
	
	private static String currentSessionId = null;
	private static String authorization = "";
	
	private static ObjectMapper mapper;
	static{
	    mapper = new ObjectMapper();
        mapper.configure(
                DeserializationConfig.Feature.ACCEPT_SINGLE_VALUE_AS_ARRAY,
                true);
        mapper.setDateFormat(new PatchedStdDateFormat());
	}
	
	/**
	 * Returns user key id if credentials are valid, null if not.
	 * @param email
	 * @param password
	 * @return user key or null
	 */
	@Deprecated
	public static KeyResponse verifyUserCredentials(String authorization){
		return (KeyResponse) callMethod("users/verify_credentials", KeyResponse.class, PROTOCOL_GET, authorization);
	}
	
	public static Session login(String authorization){
		Session session = (Session) callMethod("users/login", Session.class, PROTOCOL_POST, authorization);
		if(session != null){
			setCurrentSessionId(session.getSessionKey());
		}
		return session;
	}
	
	public static void logout(String authorization){
		callMethod("users/logout", Session.class, PROTOCOL_POST, authorization);
		setAuthorization("");
		setCurrentSessionId("");
	}
	
	public static String getAuthorization(String email, String password){
		String authorization = Base64.encode((email + ":" + password).getBytes());
		setAuthorization(authorization);
		return authorization;
	}
	
	////////////////////////// Region get ////////////////////////////////
	
	public static List<Category> getCategories(String authorization)
	{
		Object genericObject = callMethod("categories/", CategoryWrapper.class, PROTOCOL_GET, authorization);
		if(genericObject != null)
		{
			CategoryWrapper categories = (CategoryWrapper) genericObject;
			if (categories != null)
				return categories.getCategories();
		}
		
		return null;
	}
	
	public static List<User> getUsers()
	{
		Object genericObject = callMethod("users/", UserListResponse.class, PROTOCOL_GET);
		if(genericObject != null)
		{
			UserListResponse users = (UserListResponse) genericObject;
			List<User> userList = users.getUsers();
			
			Collections.sort(userList, new Comparator<User>(){
				public int compare(User obj1, User obj2) {

					return obj1.getFirstName().toString().compareToIgnoreCase(obj2.getFirstName().toString());
				}
			});
			
			if (users != null)
				return userList;
		}
		
		return null;
	}
	
	public static UserResponse getUserInfo(String key, String authorization){
		Object genericObject = callMethod("users/" + key + "/infos", UserResponse.class, PROTOCOL_GET, authorization);
		if(genericObject != null)
		{
			UserResponse user = (UserResponse) genericObject;
			if (user != null)
				return user;
		}
		
		return null;
	}
	
	public static void ResetPassword(String email){
		callMethod("users/"+email+"/resetPassword",null, PROTOCOL_POST);
	}
	
	public static Category getCategory(String key){
		Object genericObject = callMethod("categories/" + key, Category.class, PROTOCOL_GET);
		if(genericObject != null)
		{ 
			Category category = (Category) genericObject;
			if (category != null)
				return category;
		}
		return null;
	}

	public static UserListResponse getUsers( String group, int pageNumber, int countPerPage){
		String method = "users?max=" + countPerPage + "&page=" + pageNumber;
		if(group != null && !group.isEmpty()){
			method += "&group=" + group;
		}
		
		Object genericObject = callMethod(method, UserListResponse.class, PROTOCOL_GET);
		if(genericObject != null)
		{
			UserListResponse user = (UserListResponse) genericObject;
			if (user != null)
				return user;
		}
		
		return null;
	}
	
	public static UserListResponse getUsersByTeam(String team, int pageNumber, int countPerPage){
		String method = "users?max=" + countPerPage + "&page=" + pageNumber;
		if(team != null && !team.isEmpty()){
			method += "&team=" + team;
		}
		
		Object genericObject = callMethod(method, UserListResponse.class, PROTOCOL_GET);
		if(genericObject != null)
		{
			UserListResponse user = (UserListResponse) genericObject;
			if (user != null)
				return user;
		}
		
		return null;
	}
	
	public static List<UserResponse> getDetailedUserList(String group, int pageNumber, int countPerPage, String authorization){
		String method = "users/infos?max=" + countPerPage + "&page=" + pageNumber;
		if(group != null && !group.isEmpty()){
			method += "&group=" + group;
		}
		
		Object genericObject = callMethod(method, UserResponseWrapper.class, PROTOCOL_GET, authorization);
		if(genericObject != null)
		{
			UserResponseWrapper userResponses = (UserResponseWrapper) genericObject;
			if (userResponses != null)
				return userResponses.getUserResponses();
		}
		
		return null;
	}
	
	public static User getUser(String key)
	{
		Object genericObject = callMethod("users/" + key, User.class, PROTOCOL_GET);
		if(genericObject != null)
		{
			User user = (User) genericObject;
			if (user != null)
				if (user.getEmail() == null){
					user.setEmail("");
				}
				if (user.getLastLoginAttempt() == null){
					user.setLastLoginAttempt(user.getLastLogin());
				}
				if (user.getPassword() == null){
					user.setPassword("");
				}
				return user;
		}
		
		return null;
	}
	
	public static User getUserByEmail(String email)
	{
		Object genericObject = callMethod("users?email=" + email, UserListResponse.class, PROTOCOL_GET);
		if(genericObject != null)
		{
			UserListResponse user = (UserListResponse) genericObject;
			if (user != null && user.getUsers().size() == 1)
				return user.getUsers().get(0);
		}
		
		return null;
	}
	
	public static UserResponse getCurrentUserInfo(String authorization)
	{
		Object genericObject = callMethod("users/current", UserResponse.class, PROTOCOL_GET, authorization);
		if(genericObject != null)
		{
			UserResponse userResponse = (UserResponse) genericObject;
			if (userResponse != null)
				return userResponse;
		}
		
		return null;
	}

	public static QuestionSet getQuestionSetById(String questionSetId)
	{
		Object genericObject = callMethod("questionSets/" + questionSetId, QuestionSet.class, PROTOCOL_GET);
		if(genericObject != null)
		{
			QuestionSet questionSet = (QuestionSet) genericObject;
			if (questionSet != null)
				return questionSet;
		}
		
		return null;
	}
	
	public static List<QuestionSet> getQuestionSets()
	{
		Object genericObject = callMethod("questionSets/", QuestionSetListResponse.class, PROTOCOL_GET);
		if(genericObject != null)
		{
			QuestionSetListResponse questionSets = (QuestionSetListResponse) genericObject;
			List<QuestionSet> questionSetList = questionSets.getQuestionSets();
			if (questionSets != null)
			{
				Collections.sort(questionSetList, new Comparator<QuestionSet>(){
					public int compare(QuestionSet obj1, QuestionSet obj2) {

						return obj1.getName().toString().compareToIgnoreCase(obj2.getName().toString());
					}
				});
				
				return questionSetList;
			}
		}
		
		return null;
	}
	
	
	public static List<Question> getQuestions(QuestionType type, String authorization)
	{
		return getQuestions(type, authorization, false);
	}
	
	public static List<Question> getQuestions(QuestionType type, String authorization, boolean reported)
	{
		Object genericObject = callMethod("questions?type=" + type + "&reported=" + reported, QuestionListResponse.class, PROTOCOL_GET, authorization);
		if(genericObject != null)
		{
			QuestionListResponse questions = (QuestionListResponse) genericObject;
			if (questions != null && questions.getQuestions() != null)
				return questions.getQuestions().getAllQuestions();
		}
		
		return null;
	}

	public static List<Question> getQuestions(QuestionType type, int countPerPage, int page, String authorization)
	{
		return getQuestions(type, null, countPerPage, page, authorization, false);
	}
	
	public static List<Question> getQuestions(QuestionType type, int countPerPage, int page, String authorization, boolean reported)
	{
		return getQuestions(type, null, countPerPage, page, authorization, reported);
	}	

	public static List<Question> getQuestions(QuestionType type, String groupID, int countPerPage, int page, String authorization)
	{
		return getQuestions(type, groupID, countPerPage, page, authorization, false);
	}
	
	public static List<Question> getQuestions(QuestionType type, String groupID, int countPerPage, int page, String authorization, boolean reported)
	{
		String method = "questions?type=" + type + "&max=" + countPerPage + "&page=" + page + "&reported=" + reported;
		String publicField;
		if(groupID != null && !groupID.isEmpty())
		{
			method += "&group=" + groupID;
			publicField = "false";
		}
		else{
			method += "&group=null";
			publicField = "true";
		}
		
		method += "&public=" + publicField;
		
		if(PRINT_DEBUG_INFO) System.out.println(method);
		
		Object genericObject = callMethod(method, QuestionListResponse.class, PROTOCOL_GET, authorization);
		if(genericObject != null)
		{
			QuestionListResponse questions = (QuestionListResponse) genericObject;
			if (questions != null && questions.getQuestions() != null)
				return questions.getQuestions().getAllQuestions();
		}
		
		return null;
	}
	
	//General Questions
	public static QuestionGeneral getQuestionGeneral(String key, String authorization)
	{
		Object genericObject = callMethod("questions/" + key, QuestionGeneral.class, PROTOCOL_GET, authorization);
		if(genericObject != null)
		{
			QuestionGeneral question = (QuestionGeneral) genericObject;
			if (question != null)
				return question;
		}
		
		return null;
	}
	
	//Anagram Questions
	public static QuestionAnagram getQuestionAnagram(String key, String authorization)
	{
		Object genericObject = callMethod("questions/" + key, QuestionAnagram.class, PROTOCOL_GET, authorization);
		if(genericObject != null)
		{
			QuestionAnagram question = (QuestionAnagram) genericObject;
			if (question != null)
				return question;
		}
		
		return null;
	}
	
	//Association Questions
	public static QuestionAssociation getQuestionAssociation(String key, String authorization)
	{
		Object genericObject = callMethod("questions/" + key, QuestionAssociation.class, PROTOCOL_GET, authorization);
		if(genericObject != null)
		{
			QuestionAssociation question = (QuestionAssociation) genericObject;
			if (question != null)
				return question;
		}
		
		return null;
	}
	
	//CorrectWord Questions
	public static QuestionCorrectWord getQuestionCorrectWord(String key, String authorization)
	{
		Object genericObject = callMethod("questions/" + key, QuestionCorrectWord.class, PROTOCOL_GET, authorization);
		if(genericObject != null)
		{
			QuestionCorrectWord question = (QuestionCorrectWord) genericObject;
			if (question != null)
				return question;
		}
		
		return null;
	}
	
	//Identification Questions
	public static QuestionIdentification getQuestionIdentification(String key, String authorization)
	{
		Object genericObject = callMethod("questions/" + key, QuestionIdentification.class, PROTOCOL_GET, authorization);
		if(genericObject != null)
		{
			QuestionIdentification question = (QuestionIdentification) genericObject;
			if (question != null)
				return question;
		}
		
		return null;
	}
	
	//Intru Questions
	public static QuestionIntru getQuestionIntru(String key, String authorization)
	{
		Object genericObject = callMethod("questions/" + key, QuestionIntru.class, PROTOCOL_GET, authorization);
		if(genericObject != null)
		{
			QuestionIntru question = (QuestionIntru) genericObject;
			if (question != null)
				return question;
		}
		
		return null;
	}
	
	public static QuestionMedia getQuestionMedia(String key, String authorization)
	{
		Object genericObject = callMethod("questions/media" + key, QuestionMedia.class, PROTOCOL_GET, authorization);
		if(genericObject != null)
		{
			QuestionMedia question = (QuestionMedia) genericObject;
			if (question != null)
				return question;
		}
		
		return null;
	}
	
	public static List<Group> getGroups()
	{
		Object genericObject = callMethod("groups/", GroupWrapper.class, PROTOCOL_GET);
		if(genericObject != null)
		{
			GroupWrapper groups = (GroupWrapper) genericObject;
			
			List<Group> groupList = groups.getGroups();
			
			Collections.sort(groupList, new Comparator<Group>(){
				public int compare(Group obj1, Group obj2) {

					return obj1.getName().toString().compareToIgnoreCase(obj2.getName().toString());
				}
			});
			
			if (groups != null)
				return groupList;
		}
		
		return null;
	}
	
	public static Group getGroup(String id)
	{
		Object genericObject = callMethod("groups/" + id, Group.class, PROTOCOL_GET);
		if(genericObject != null)
		{
			Group group = (Group) genericObject;
			if (group != null)
				return group;
		}
		
		return null;
	}
	
	public static Team getTeam(String key)
	{
		System.out.println("-- webservice getTeam()");
		Object genericObject = callMethod("teams/" + key, Team.class, PROTOCOL_GET);
		if(genericObject != null)
		{
			Team team = (Team) genericObject;
			if (team != null)
				return team;
		}
		
		return null;
	}
	
	public static List<Template> getTemplates(){
		//List<Template> returnTemplates = new ArrayList<Template>();
		Object genericObject = callMethod("templates/", TemplateListResponse.class, PROTOCOL_GET);
		if(genericObject != null){
			TemplateListResponse templatesList = (TemplateListResponse) genericObject;
			
			List<Template> templateList = templatesList.getTemplates();
			
			Collections.sort(templateList, new Comparator<Template>(){

				public int compare(Template tmp1, Template tmp2) {

					return tmp1.getName().toString().compareToIgnoreCase(tmp2.getName().toString());
				}
			});
			
			return templateList;
			
			/*if(templatesList != null){
				List<Template> templates = templatesList.getTemplates();
				for (Template template : templates){
					Template fullTemplate = getTemplate(template.getKey());
					if (fullTemplate != null){
						returnTemplates.add(fullTemplate);
					}
				}
				return returnTemplates;
			}*/
		}
		return null;
	}
	
	public static Template getTemplate(String key){
		Object genericObject = callMethod("templates/" + key, Template.class, PROTOCOL_GET);
		if(genericObject != null)
		{
			Template template = (Template) genericObject;
			if (template != null)
				return template;
		}
		
		return null;
	}
	
	public static Game getGame(String key) 
	{
		Object genericObject = callMethod("games/" + key, Game.class, PROTOCOL_GET);
		if(genericObject != null)
		{
			Game game = (Game) genericObject;
			if (game != null)
				return game;
		}
		
		return null;
	}
	
	public static List<Game> getGames(){
		Object genericObject = callMethod("games/", GameListResponse.class, PROTOCOL_GET);
		if(genericObject != null)
		{
			GameListResponse games = (GameListResponse) genericObject;
			if (games != null)
				return games.getGames();
		}
		
		return null;
	}
	
	public static List<Game> getGames(boolean active, int resultByPage, int pageNumber, String userKey){
		String args = "games?active=" + active;
		if(userKey != null)
			args += "&user=" + userKey;
		Object genericObject = callMethod(args, GameListResponse.class, PROTOCOL_GET);
		if(genericObject != null)
		{
			GameListResponse games = (GameListResponse) genericObject;
			if (games != null)
				return games.getGames();
		}
		
		return null;
	}
	
	
	public static List<GameLog> getGameLogs(String gameKey, String teamKey){
		String args = "?";
		if(gameKey != null && !gameKey.isEmpty()){
			args += "game=" + gameKey + "&";
		}
		if(teamKey != null && !teamKey.isEmpty()){
			args += "team=" + teamKey + "&";
		}
		Object genericObject = callMethod("gameLogs" + args, GameLogListResponse.class, PROTOCOL_GET);
		if(genericObject != null)
		{
			GameLogListResponse user = (GameLogListResponse) genericObject;
				return user.getGameLog();
		}
		
		return null;
	}
	
	public static TournamentListResponse getTournaments(String game, String team, String name){
		String args = "?";
		if(game != null && !game.isEmpty()){
			args += "game=" + game + "&";
		}
		if(team != null && !team.isEmpty()){
			args += "team=" + team + "&";
		}
		if(name != null && !name.isEmpty()){
			args += "name=" + name + "&";
		}
		
		Object genericObject = callMethod("tournaments" + args, TournamentListResponse.class, PROTOCOL_GET);
		if(genericObject != null)
		{
			TournamentListResponse response = (TournamentListResponse) genericObject;
			if (response != null)
				return response;
		}
		
		return null;
	}
	
	public static List<GameStat> getGameStats(String statsFor, String context, int resultByPage, int pageNumber){
		String args = "?max=" + resultByPage + "&page=" + pageNumber + "&";
		if(statsFor != null && !statsFor.isEmpty()){
			args += "for=" + statsFor + "&";
		}
		if(context != null && !context.isEmpty()){
			args += "context=" + context + "&";
		}
		
		Object genericObject = callMethod("stats" + args, GameStatListResponse.class, PROTOCOL_GET);
		if(genericObject != null)
		{
			GameStatListResponse response = (GameStatListResponse) genericObject;
			if (response != null)
				return response.getGameStat();
		}
		
		return null;
	}
	
	public static Tournament getTournament(String id){
		Object genericObject = callMethod("tournaments/" + id, Tournament.class, PROTOCOL_GET);
		if(genericObject != null)
		{
			Tournament response = (Tournament) genericObject;
			if (response != null)
				return response;
		}
		
		return null;
	}
	
	////////////////////////// Region add+edit ////////////////////////////////
	
	public static KeyResponse addQuestion(Question question, String authorization) 
	{
		Object returnValue = null;
		if     (question instanceof QuestionAnagram)		returnValue = callMethod("questions/anagram/", KeyResponse.class, PROTOCOL_POST, authorization, question);
		else if(question instanceof QuestionAssociation)	returnValue = callMethod("questions/association/", KeyResponse.class, PROTOCOL_POST, authorization, question);
		else if(question instanceof QuestionCorrectWord)	returnValue = callMethod("questions/correctword/", KeyResponse.class, PROTOCOL_POST, authorization, question);
		else if(question instanceof QuestionGeneral)		returnValue = callMethod("questions/general/", KeyResponse.class, PROTOCOL_POST, authorization, question);
		else if(question instanceof QuestionIdentification)	returnValue = callMethod("questions/identification/", KeyResponse.class, PROTOCOL_POST, authorization, question);
		else if(question instanceof QuestionIntru)			returnValue = callMethod("questions/intru/", KeyResponse.class, PROTOCOL_POST, authorization, question);
		
		return returnValue != null ? (KeyResponse) returnValue : null;
	}

	public static KeyResponse addCategory(Category category, String authorization){
		Object returnValue = null;
		returnValue = callMethod("categories/", KeyResponse.class, PROTOCOL_POST, authorization, category);
		
		return returnValue != null ? (KeyResponse) returnValue : null;
	}
	
	public static KeyResponse addTeam(Team team, String authorization){
		Object returnValue = null;
		returnValue = callMethod("teams/", KeyResponse.class, PROTOCOL_POST, authorization, team);
		
		return returnValue != null ? (KeyResponse) returnValue : null;
	}
	
	public static KeyResponse addGroup(Group group, String authorization){
		Object returnValue = null;
		returnValue = callMethod("groups/", KeyResponse.class, PROTOCOL_POST, authorization, group);
		
		return returnValue != null ? (KeyResponse) returnValue : null;
	}
	
	public static boolean editGroup(Group group, String authorization) 
	{
		Object returnValue = null;
		returnValue = callMethod("groups/" + group.getKey(), KeyResponse.class, PROTOCOL_PUT, authorization, group);
		
		return returnValue != null ? ((KeyResponse) returnValue).isCompleted() : false;
	}

	public static KeyResponse addGroupRole(GroupRole groupRole, String authorization){
		Object returnValue = null;
		returnValue = callMethod("groups/role", KeyResponse.class, PROTOCOL_POST, authorization, groupRole);
		
		return returnValue != null ? (KeyResponse) returnValue : null;
	}
	
	public static boolean editGroupRole(GroupRole groupRole, String authorization) 
	{
		Object returnValue = null;
		returnValue = callMethod("groups/role", BaseResponse.class, PROTOCOL_PUT, authorization, groupRole);
		
		return returnValue != null ? ((BaseResponse) returnValue).isCompleted() : false;
	}
	
	public static boolean editTemplate(Template template, String authorization){
		Object returnValue = null;
		returnValue = callMethod("templates/" + template.getKey(), KeyResponse.class, PROTOCOL_PUT, authorization, template);
		return returnValue != null ? ((KeyResponse) returnValue).isCompleted() : false;
	}
	
	public static boolean editQuestion(Question question, String authorization) 
	{
		Object returnValue = null;
		
		if     (question instanceof QuestionAnagram)		returnValue = callMethod("questions/anagram/" + question.getKey(), KeyResponse.class, PROTOCOL_PUT, authorization, question);
		else if(question instanceof QuestionAssociation)	returnValue = callMethod("questions/association/" + question.getKey(), KeyResponse.class, PROTOCOL_PUT, authorization, question);
		else if(question instanceof QuestionCorrectWord)	returnValue = callMethod("questions/correctword/" + question.getKey(), KeyResponse.class, PROTOCOL_PUT, authorization, question);
		else if(question instanceof QuestionGeneral)		returnValue = callMethod("questions/general/" + question.getKey(), KeyResponse.class, PROTOCOL_PUT, authorization, question);
		else if(question instanceof QuestionIdentification)	returnValue = callMethod("questions/identification/" + question.getKey(), KeyResponse.class, PROTOCOL_PUT, authorization, question);
		else if(question instanceof QuestionIntru)			returnValue = callMethod("questions/intru/" + question.getKey(), KeyResponse.class, PROTOCOL_PUT, authorization, question);
		else if(question instanceof QuestionMedia)			returnValue = callMethod("questions/media/" + question.getKey(), KeyResponse.class, PROTOCOL_PUT, authorization, question);

		return returnValue != null ? ((KeyResponse) returnValue).isCompleted() : false;
	}
	
	public static boolean editTeam(Team team, String authorization) 
	{
		Object returnValue = null;
		returnValue = callMethod("teams/" + team.getKey(), KeyResponse.class, PROTOCOL_PUT, authorization, team);

		return returnValue != null ? ((KeyResponse) returnValue).isCompleted() : false;
	}
	
	public static KeyResponse addUser(CreateUserParam userParam)
	{
		Object returnValue = null;
		returnValue = callMethod("users/", KeyResponse.class, PROTOCOL_POST, null, userParam);
		
		return returnValue != null ? (KeyResponse) returnValue : null;
	}
	
	public static KeyResponse addUserToGroup(String userKey, Role role, String authorization)
	{
		Object returnValue = null;
		returnValue = callMethod("users/" + userKey + "/addGroup/" + role.toString(), KeyResponse.class, PROTOCOL_POST, authorization);
		
		return returnValue != null ? (KeyResponse) returnValue : null;
	}
	
	public static KeyResponse addGame(Game game){
		Object returnValue = null;
		returnValue = callMethod("games/", KeyResponse.class, PROTOCOL_POST, null, game);
		
		return returnValue != null ? (KeyResponse) returnValue : null;
	}
	
	public static KeyResponse addGameLog(GameLog gameLog){
		Object returnValue = null;
		returnValue = callMethod("gameLogs/", KeyResponse.class, PROTOCOL_POST, null, gameLog);
		
		return returnValue != null ? (KeyResponse) returnValue : null;
	}
	
	public static QuestionSet generateQuestionSet(Template template, String authorization, boolean saveQuestionSet){
		Object returnValue = null;
		returnValue =  callMethod("questionSets/generate?save="+saveQuestionSet, QuestionSet.class, PROTOCOL_POST, authorization, template);
		
		return returnValue != null ? (QuestionSet) returnValue : null;
	}
	
	public static KeyResponse addTemplate(Template template, String authorization){
		Object returnValue = null;
		returnValue = callMethod("templates/", KeyResponse.class, PROTOCOL_POST, authorization, template);
		
		return returnValue != null ? (KeyResponse) returnValue : null;
	}
	
	public static KeyResponse addTournament(Tournament tournament){
		Object returnValue = null;
		returnValue = callMethod("tournaments/", KeyResponse.class, PROTOCOL_POST, null, tournament);
		
		return returnValue != null ? (KeyResponse) returnValue : null;
	}
	
	public static IntegerResponse getQuestionNbInTemplateSection(TemplateSection ts){
		Object returnValue = null;
		returnValue = callMethod("templates/availableQuestions", IntegerResponse.class, PROTOCOL_POST, null, ts);
	
		return returnValue != null ? (IntegerResponse) returnValue : null;
	}
	
	public static BaseResponse closeGame(String key){
		Object returnValue = null;
		returnValue = callMethod("games/" + key + "/close" , BaseResponse.class, PROTOCOL_POST, null, null);
		System.out.println("Close Game");
		return returnValue != null ? (BaseResponse) returnValue : null; 
	}
	
	
	
	////////////////////////// Region PUT //////////////////////////
	
	public static BaseResponse editGame(Game game){
		Object returnValue = null;
		returnValue = callMethod("games/" + game.getKey() , BaseResponse.class, PROTOCOL_PUT, null, game);
		
		return returnValue != null ? (BaseResponse) returnValue : null; 
	}
	
	public static BaseResponse editUser(CreateUserParam user, String userKey, String authorization){
		Object returnValue = null;
		returnValue = callMethod("users/" + userKey, BaseResponse.class, PROTOCOL_PUT, authorization, user);
		
		return returnValue != null ? (BaseResponse) returnValue : null;
	}

	////////////////////////// Region DELETE + misc //////////////////////////
	public static boolean deleteCategory(String categoryKey, String authorization){
		Object genericObject = callMethod("categories/" + categoryKey, BaseResponse.class, PROTOCOL_DELETE, authorization);
		if(genericObject != null)
		{
			BaseResponse response = (BaseResponse) genericObject;

			if (response != null)
				return response.isCompleted();
		}
		
		return false;
	}
	
	public static boolean deleteQuestion(String questionID, String authorization){
		Object genericObject = callMethod("questions/" + questionID, BaseResponse.class, PROTOCOL_DELETE, authorization);
		if(genericObject != null)
		{
			BaseResponse response = (BaseResponse) genericObject;

			if (response != null)
				return response.isCompleted();
		}
		
		return false;
	}
	
	public static boolean deleteGroup(String groupID, String authorization){
		Object genericObject = callMethod("groups/" + groupID, BaseResponse.class, PROTOCOL_DELETE, authorization);
		if(genericObject != null)
		{
			BaseResponse response = (BaseResponse) genericObject;

			if (response != null)
				return response.isCompleted();
		}
		
		return false;
	}
	
	public static boolean deleteTemplate(String templateId, String authorization){
		Object genericObject = callMethod("templates/" + templateId, BaseResponse.class, PROTOCOL_DELETE, authorization);
		if(genericObject != null)
		{
			BaseResponse response = (BaseResponse) genericObject;

			if (response != null)
				return response.isCompleted();
		}
		
		return false;
	}
	
	public static boolean deleteUser(String userKey, String authorization){
		Object genericObject = callMethod("users/" + userKey, BaseResponse.class, PROTOCOL_DELETE, authorization);
		if(genericObject != null)
		{
			BaseResponse response = (BaseResponse) genericObject;

			if (response != null)
				return response.isCompleted();
		}
		
		return false;
	}
	
	public static boolean deleteUserFromGroup(String userKey, String groupKey, String authorization){
		Object genericObject = callMethod("users/" + userKey + "/removeGroup/" + groupKey, BaseResponse.class, PROTOCOL_DELETE, authorization);
		if(genericObject != null)
		{
			BaseResponse response = (BaseResponse) genericObject;

			if (response != null)
				return response.isCompleted();
		}
		
		return false;
	}
	
	public static boolean deleteTeam(String teamKey, String authorization){
		Object genericObject = callMethod("teams/" + teamKey, BaseResponse.class, PROTOCOL_DELETE, authorization);
		if(genericObject != null)
		{
			BaseResponse response = (BaseResponse) genericObject;

			if (response != null)
				return response.isCompleted();
		}
		
		return false;
	}
	
	public static boolean deleteAllGames(String authorization){
		List<Game> games = getGames();
		
		for(Game game : games){
			Object genericObject = callMethod("games/" + game.getKey(), BaseResponse.class, PROTOCOL_DELETE, authorization);
			if(genericObject != null)
			{
				BaseResponse response = (BaseResponse) genericObject;

				if (response != null)
					return response.isCompleted();
			}
		}
		return false;
	}
	
	public static void closeAllGames(){
		List<Game> games = getGames();
		
		for(Game game : games){
			game.setActive(false);
			editGame(game);
		}
	}
	
	////////////////////////// Region callMethod //////////////////////////

	private static Object callMethod(String method,Class<?> returnObjectType, String requestType)
	{
		return callMethod(method, returnObjectType, requestType, null, null, 0);
	}
	
	private static Object callMethod(String method,Class<?> returnObjectType, String requestType, String authorization)
	{
		return callMethod(method, returnObjectType, requestType, authorization, null, 0);
	}

	private static Object callMethod(String method,Class<?> returnObjectType, String requestType, String authorization, Object parameters)
	{
		return callMethod(method, returnObjectType, requestType, authorization, parameters, 0);
	}
	
	private static Object callMethod(String method,Class<?> returnObjectType, String requestType, String authorization, Object parameters, int attemptCount)
	{
		try 
		{
		    URL url = null;
		    
		    if(USE_DEV_SERVER){
		    	url = new URL("https://openquizproject-dev.appspot.com/rest/" + method);
		    }else{
		    	url = new URL("https://openquizproject-prod.appspot.com/rest/" + method);
		    }
		    
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setReadTimeout(25000);
		    
            if(BYPASS_SSL_CERT){
            	// Bypass all security mesure, used if debugging on a webservice without a good cert
            	 HttpsURLConnection.setDefaultHostnameVerifier(
                 	    new javax.net.ssl.HostnameVerifier(){

                 	        public boolean verify(String hostname,
                 	                javax.net.ssl.SSLSession sslSession) {
                 	            if (hostname.equals("localhost")) {
                 	                return true;
                 	            }
                 	            return false;
                 	        }
                 	    });
            	 if(connection instanceof HttpsURLConnection){
            	 ((HttpsURLConnection)connection).setHostnameVerifier(new HostnameVerifier() {
					
					public boolean verify(String arg0, SSLSession arg1) {
						return true;
					}
				});
            	 SSLContext ssl = SSLContext.getInstance("TLSv1");
            	    ssl.init(null, new TrustManager[]{new SimpleX509TrustManager()}, null);
            	    SSLSocketFactory factory = ssl.getSocketFactory();
            	    ((HttpsURLConnection)connection).setSSLSocketFactory(factory);
            	 }
            }

            connection.addRequestProperty("Authorization", "Basic "+ getAuthorization());
            if(PRINT_DEBUG_INFO) System.out.println(getAuthorization());
            connection.addRequestProperty("Content-Type", "application/json");
            connection.addRequestProperty("Accept", "application/json");
            if(currentSessionId != null && !currentSessionId.isEmpty()){
            	if(PRINT_DEBUG_INFO) System.out.println(currentSessionId);
            	connection.addRequestProperty("session-id", currentSessionId);
            }
            connection.setRequestMethod(requestType);
        	
            // If there's no parameter, do the request without outputting anything
            if(parameters != null)
            {
            	connection.setDoOutput(true);

                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
        		StringWriter JaxbParameters = new StringWriter();
        		String jsonString = mapper.writeValueAsString(parameters);
        		if(PRINT_DEBUG_INFO) System.out.println("Sending param: " + jsonString);
                JaxbParameters.write(jsonString);
        		writer.write(JaxbParameters.toString());
        		writer.close();

                if (connection.getResponseCode() != HttpURLConnection.HTTP_OK){
                	System.out.println("ERROR CODE: " + connection.getResponseCode());
                	return null;
                }
            }else if(requestType.equals(PROTOCOL_POST)){
            	connection.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
                writer.write(" ");
            }

			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(),"UTF-8"));
			
	        String marshalledString = reader.readLine();
	        if(PRINT_DEBUG_INFO)
	        	System.out.println("Response received: " + marshalledString);
	        
	        // If all went well, the response code should be 200 and we return the object
	        // If something went wrong, don't even try to get an object, simply return null
            if (connection.getResponseCode() == HttpURLConnection.HTTP_OK){
            	return marshalledString != null ? mapper.readValue(marshalledString,returnObjectType) : null;
            }else if(PRINT_DEBUG_INFO){
            	System.out.println("ERROR IN METHOD " + method + " CODE: " + connection.getResponseCode());
            }
		} catch (SocketTimeoutException e) {
			if(attemptCount < TIMEOUT_LIMIT)
				return callMethod(method, returnObjectType, requestType, authorization, parameters, ++attemptCount);
			else
				e.printStackTrace();
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}
	
	public static String getCurrentSessionId() {
		return currentSessionId;
	}

	public static void setCurrentSessionId(String currentSessionId) {
		RequestsWebService.currentSessionId = currentSessionId;
	}
	
	public static String getAuthorization(){
		return authorization;
	}
	
	public static void setAuthorization(String authorization){
		RequestsWebService.authorization = authorization;
	}

	public static class SimpleX509TrustManager implements X509TrustManager {
	    public void checkClientTrusted(
	            X509Certificate[] cert, String s)
	            throws CertificateException {
	    }

	    public void checkServerTrusted(
	            X509Certificate[] cert, String s)
	            throws CertificateException {
	      }

	    public X509Certificate[] getAcceptedIssuers() {
	        return null;
	    }
	}
}
