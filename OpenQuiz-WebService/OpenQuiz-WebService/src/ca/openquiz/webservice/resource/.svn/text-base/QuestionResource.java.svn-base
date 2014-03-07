package ca.openquiz.webservice.resource;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import ca.openquiz.webservice.enums.Bucket;
import ca.openquiz.webservice.enums.Degree;
import ca.openquiz.webservice.enums.Language;
import ca.openquiz.webservice.enums.QuestionType;
import ca.openquiz.webservice.enums.Role;
import ca.openquiz.webservice.manager.DBManager;
import ca.openquiz.webservice.manager.FileManager;
import ca.openquiz.webservice.manager.security.BCrypt;
import ca.openquiz.webservice.manager.security.RoleManager;
import ca.openquiz.webservice.model.Question;
import ca.openquiz.webservice.model.QuestionAnagram;
import ca.openquiz.webservice.model.QuestionAssociation;
import ca.openquiz.webservice.model.QuestionGeneral;
import ca.openquiz.webservice.model.QuestionIdentification;
import ca.openquiz.webservice.model.QuestionIntru;
import ca.openquiz.webservice.model.QuestionMedia;
import ca.openquiz.webservice.model.User;
import ca.openquiz.webservice.parameter.SearchQuestionParam;
import ca.openquiz.webservice.parameter.SearchUserParam;
import ca.openquiz.webservice.response.BaseResponse;
import ca.openquiz.webservice.response.KeyResponse;
import ca.openquiz.webservice.response.QuestionListResponse;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.sun.jersey.core.util.Base64;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataParam;

@Path("/questions")
public class QuestionResource {

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public QuestionListResponse getQuestionList(
			@Context SecurityContext sc, 
			@QueryParam("author") String author_id,
			@QueryParam("category") String category_id,
			@QueryParam("group") String group,
			@DefaultValue("General")@QueryParam("type") String type,
			@QueryParam("lang") String lang,
			@QueryParam("degree") String degree,
			@DefaultValue("1")@QueryParam("page") int page,
			@DefaultValue("20")@QueryParam("max") int results,
			@DefaultValue("false")@QueryParam("public") boolean includePublic,
			@DefaultValue("false")@QueryParam("reported") boolean isReported) {
		QuestionListResponse returnValue = new QuestionListResponse();
		SearchQuestionParam p = new SearchQuestionParam();
		try
		{
			User user = RoleManager.getCurrentUser(sc);
			
			if(author_id != null && !author_id.isEmpty()) {
				p.setAuthor(KeyFactory.stringToKey(author_id));
			}
			if(category_id != null && !category_id.isEmpty()) {
				p.setCategory(KeyFactory.stringToKey(category_id));
			}
			if(group != null && !group.isEmpty()) {
				List<Key> groupKeys = new ArrayList<Key>();
				List<Role> roles = new ArrayList<Role>();
				roles.add(Role.Manager); roles.add(Role.QuestionManager);
				List<Key> keys = RoleManager.getUserGroups(sc, roles);
				Key k = KeyFactory.stringToKey(group);
				if(keys.contains(k))
					groupKeys.add(k);
				else
				{
					returnValue.setError("Tried to access a group the user is not manager or questionManager of");
					return returnValue;
				}
				
				p.setGroups(groupKeys);
			}
			else if (user != null){
				p.setGroups(user.getGroups());
			}

			if(type != null && !type.isEmpty()) {
				p.setType(QuestionType.valueOf(type));
			}
			if(lang != null && !lang.isEmpty()) {
				p.setLanguage(Language.valueOf(lang));
			}
			if(degree != null && !degree.isEmpty()) {
				p.setDegree(Degree.valueOf(degree));
			}
			p.setPageNumber(page);
			p.setResultsByPage(results);
			p.setReported(isReported);
			p.setIncludePublic(includePublic);
		}
		catch(Exception e)
		{	
			returnValue.setError("Unknown parameter");
			return returnValue;
		}
		returnValue.setQuestions((List<Question>) DBManager.searchQuestion(p));
		return returnValue;
	}

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Question getQuestionById(@Context SecurityContext sc, @PathParam("id") String id) {

		Question q = null;

		try{
			Key key = KeyFactory.stringToKey(id);
			q = DBManager.getQuestion(key);

		}
		catch(Exception e) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}

		if(q != null){
			List<Role> roleList = new ArrayList<Role>();
			roleList.add(Role.Player);
			roleList.add(Role.QuestionManager);
			roleList.add(Role.Manager);

			if(!RoleManager.userRoleMatchGroup(sc, roleList, q.getGroups(), true)){
				throw new WebApplicationException(Response.Status.FORBIDDEN);
			}

			Date now = new Date();
			if(q.getAvailableDate().compareTo(now) > 0) {
				throw new WebApplicationException(Response.Status.FORBIDDEN);
			}
		}
		return q;
	}

	@GET
	@Path("/random")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Question getRandomQuestion(@Context SecurityContext sc, 
			@QueryParam("author") String author_id,
			@QueryParam("category") String category_id,
			@QueryParam("group") List<String> groups,
			@QueryParam("type") String type,
			@QueryParam("lang") String lang,
			@QueryParam("degree") String degree,
			@QueryParam("reported") boolean isReported) {

		SearchQuestionParam p = new SearchQuestionParam();
		try
		{
			if(author_id != null && !author_id.isEmpty()) {
				p.setAuthor(KeyFactory.stringToKey(author_id));
			}
			if(category_id != null && !category_id.isEmpty()) {
				p.setCategory(KeyFactory.stringToKey(category_id));
			}
			if(groups != null && !groups.isEmpty()) {
				List<Key> groupKeys = new ArrayList<Key>();
				List<Key> keys = RoleManager.getUserGroups(sc);
				for(String g : groups) {
					Key k = KeyFactory.stringToKey(g);
					if(keys.contains(k)) {
						groupKeys.add(k);
					}
				}
				p.setGroups(groupKeys);
			}
			else {
				p.setGroups(RoleManager.getUserGroups(sc));
			}
			if(type != null && !type.isEmpty()) {
				p.setType(QuestionType.valueOf(type));
			}
			if(lang != null && !lang.isEmpty()) {
				p.setLanguage(Language.valueOf(lang));
			}
			if(degree != null && !degree.isEmpty()) {
				p.setDegree(Degree.valueOf(degree));
			}
			p.setReported(isReported);
		}
		catch(Exception e)
		{	
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}

		return DBManager.searchRandomQuestion(p);
	}

	@POST
	@Path("/general")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public KeyResponse addQuestionGeneral(@Context SecurityContext sc, QuestionGeneral question)
	{
		KeyResponse resp = new KeyResponse();

		//Vérification des droits pour ajouter une question
		List<Role> roleList = new ArrayList<Role>();
		roleList.add(Role.QuestionManager);
		roleList.add(Role.Manager);
		if(RoleManager.userRoleMatchGroup(sc, roleList, question.getGroups(), true)) {
			question.setAuthor(RoleManager.getCurrentUserKey(sc));

			question.setAttemptedAnswer(0);
			question.setCorrectAnswer(0);
			if(question.getAvailableDate() == null) {
				Date date = new Date();
				question.setAvailableDate(date);
			}
			question.setIsReported(false);

			if(!question.isValid()){
				throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);
			}
			try
			{
				DBManager.save(question);
				resp.setKey(KeyFactory.keyToString(question.getKey()));
			}
			catch(Exception e)
			{
				resp.setError(e.getMessage());
			}
		}

		return resp;
	}

	@POST
	@Path("/association")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public KeyResponse addQuestionAssociation(@Context SecurityContext sc, QuestionAssociation question)
	{	
		KeyResponse resp = new KeyResponse();

		//V?????????rification des droits pour ajouter une question
		if(question.getGroups() != null && !question.getGroups().isEmpty()) {
			List<Role> roleList = new ArrayList<Role>();
			roleList.add(Role.QuestionManager);
			roleList.add(Role.Manager);
			if(RoleManager.userRoleMatchGroup(sc, roleList, question.getGroups(), true)) {
				question.setAuthor(RoleManager.getCurrentUserKey(sc));

				question.setAttemptedAnswer(0);
				question.setCorrectAnswer(0);
				if(question.getAvailableDate() == null) {
					Date date = new Date();
					question.setAvailableDate(date);
				}
				question.setIsReported(false);

				if(!question.isValid()){
					throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);
				}
				try
				{
					DBManager.save(question);
					resp.setKey(KeyFactory.keyToString(question.getKey()));
				}
				catch(Exception e)
				{
					resp.setError(e.getMessage());
				}

			}
		}

		return resp;
	}

	@POST
	@Path("/anagram")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public KeyResponse addQuestionAnagram(@Context SecurityContext sc, QuestionAnagram question)
	{
		KeyResponse resp = new KeyResponse();

		//V?????????rification des droits pour ajouter une question
		List<Role> roleList = new ArrayList<Role>();
		roleList.add(Role.QuestionManager);
		roleList.add(Role.Manager);
		if(RoleManager.userRoleMatchGroup(sc, roleList, question.getGroups(), true)) {
			question.setAuthor(RoleManager.getCurrentUserKey(sc));

			question.setAttemptedAnswer(0);
			question.setCorrectAnswer(0);
			if(question.getAvailableDate() == null) {
				Date date = new Date();
				question.setAvailableDate(date);
			}
			question.setIsReported(false);

			if(!question.isValid()){
				throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);
			}
			
			try
			{
				DBManager.save(question);
				resp.setKey(KeyFactory.keyToString(question.getKey()));
			}
			catch(Exception e)
			{
				resp.setError(e.getMessage());
			}
		}
		return resp;
	}

	@POST
	@Path("/identification")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public KeyResponse addQuestionIdentification(@Context SecurityContext sc, QuestionIdentification question)
	{	
		KeyResponse resp = new KeyResponse();

		//V?????????rification des droits pour ajouter une question
		List<Role> roleList = new ArrayList<Role>();
		roleList.add(Role.QuestionManager);
		roleList.add(Role.Manager);
		if(RoleManager.userRoleMatchGroup(sc, roleList, question.getGroups(), true)) {
			question.setAuthor(RoleManager.getCurrentUserKey(sc));

			question.setAttemptedAnswer(0);
			question.setCorrectAnswer(0);
			if(question.getAvailableDate() == null) {
				Date date = new Date();
				question.setAvailableDate(date);
			}
			question.setIsReported(false);

			if(!question.isValid()){
				throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);
			}
			try
			{
				DBManager.save(question);
				resp.setKey(KeyFactory.keyToString(question.getKey()));
			}
			catch(Exception e)
			{
				resp.setError(e.getMessage());
			}
		}

		return resp;
	}

	@POST
	@Path("/intru")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public KeyResponse addQuestionIntru(@Context SecurityContext sc, QuestionIntru question)
	{
		KeyResponse resp = new KeyResponse();

		//V?????????rification des droits pour ajouter une question
		List<Role> roleList = new ArrayList<Role>();
		roleList.add(Role.QuestionManager);
		roleList.add(Role.Manager);
		if(RoleManager.userRoleMatchGroup(sc, roleList, question.getGroups(), true)) {
			question.setAuthor(RoleManager.getCurrentUserKey(sc));

			question.setAttemptedAnswer(0);
			question.setCorrectAnswer(0);
			if(question.getAvailableDate() == null) {
				Date date = new Date();
				question.setAvailableDate(date);
			}
			question.setIsReported(false);

			if(!question.isValid()){
				throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);
			}
			try
			{
				DBManager.save(question);
				resp.setKey(KeyFactory.keyToString(question.getKey()));
			}
			catch(Exception e)
			{
				resp.setError(e.getMessage());
			}
		}
		return resp;
	}

	@POST
	@Path("/media")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public KeyResponse addQuestionMedia(
			@Context SecurityContext sc, 
			@FormDataParam("statement") String statement,
			@FormDataParam("answer") String answer,
			@FormDataParam("category") String category,
			@FormDataParam("availableDate") long availableDate,
			@FormDataParam("degree") Degree degree,
			@FormDataParam("language") Language language,
			@FormDataParam("group") List<FormDataBodyPart> groups,
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataBodyPart fileDetail) {
		return addQuestionMediaInternal(sc, statement, answer, category, availableDate, degree, language, groups, uploadedInputStream, fileDetail);
	}
	
	@POST
	@Path("/mediaHTML")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public KeyResponse addQuestionMedia(
			@FormDataParam("Authorization") String authorization, 
			@FormDataParam("statement") String statement,
			@FormDataParam("answer") String answer,
			@FormDataParam("category") String category,
			@FormDataParam("availableDate") long availableDate,
			@FormDataParam("degree") Degree degree,
			@FormDataParam("language") Language language,
			@FormDataParam("group") List<FormDataBodyPart> groups,
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataBodyPart fileDetail) {

		if(authorization == null || authorization.isEmpty())
			throw new WebApplicationException(Response.Status.FORBIDDEN);
		
		authorization = authorization.substring("Basic ".length());
		String[] creds = new String(Base64.base64Decode(authorization)).split(":");
		String username = creds[0];
		String password = creds[1];

		Date date = new Date();
		SearchUserParam p = new SearchUserParam();
		List<User> userList = null;
		User user = null;
		
		if(!username.isEmpty() && !password.isEmpty()) {
			p.setEmail(username.toLowerCase());

			userList = DBManager.searchUsers(p);
			if (userList == null || userList.isEmpty()) {
				return null;
			}
			user = userList.get(0);

			Long minutesAgo = new Long(3);
			Date dateIn_X_MinAgo = new Date(date.getTime() - minutesAgo * 60 * 1000);

			if (user.getLastLoginAttempt() == null) {
				user.setLastLoginAttempt(date);
			}

			if (user.getLoginAttempt() >= 8 && user.getLastLoginAttempt().after(dateIn_X_MinAgo)) {
				return null;
			}
			user.setLastLoginAttempt(date);
			user.setLoginAttempt(user.getLoginAttempt() + 1);
			DBManager.save(user);

			if (BCrypt.checkpw(password, user.getPassword())) {
				ca.openquiz.webservice.manager.security.SecurityManager shitzor = new ca.openquiz.webservice.manager.security.SecurityManager();
				return addQuestionMediaInternal(shitzor.new Authorizer(user), statement, answer, category, availableDate, degree, language, groups, uploadedInputStream, fileDetail);
			}
		}	
		throw new WebApplicationException(Response.Status.FORBIDDEN);
	}

	private KeyResponse addQuestionMediaInternal(
			SecurityContext sc, 
			String statement,
			String answer,
			String category,
			long availableDate,
			Degree degree,
			Language language,
			List<FormDataBodyPart> groups,
			InputStream uploadedInputStream,
			FormDataBodyPart fileDetail){
		QuestionMedia question = new QuestionMedia();
		KeyResponse resp = new KeyResponse();

		//Verification des droits pour ajouter une question
		List<Role> roleList = new ArrayList<Role>();
		roleList.add(Role.QuestionManager);
		roleList.add(Role.Manager);

		List<Key> groupsList = new ArrayList<Key>();
		if(groups != null && !groups.isEmpty()){
			try{
				for(FormDataBodyPart g : groups){
					groupsList.add(KeyFactory.stringToKey(g.getValueAs(String.class)));
				}
			}catch(Exception e){
				throw new WebApplicationException(Response.Status.BAD_REQUEST);
			}
		}

		if(RoleManager.userRoleMatchGroup(sc, roleList, groupsList, true)) {

			question.setStatement(statement);
			question.setAnswer(answer);

			try{
				Key catKey = KeyFactory.stringToKey(category);
				if(DBManager.getCategory(catKey) != null){
					question.setCategory(catKey);
				}
				else {
					throw new WebApplicationException(Response.Status.BAD_REQUEST);
				}
			}catch(Exception e){
				throw new WebApplicationException(Response.Status.BAD_REQUEST);
			}

			Date date;
			if(availableDate != 0)
			{
				date = new Date(availableDate);
				Date now = new Date();
				if(date.before(now))
					date = now;
			}
			else
				date = new Date();
			
			question.setAvailableDate(date);
			question.setDegree(degree);
			question.setLanguage(language);

			question.setAuthor(RoleManager.getCurrentUserKey(sc));

			question.setAttemptedAnswer(0);
			question.setCorrectAnswer(0);
			question.setIsReported(false);

			question.setMediaId("tempId");
			
			String mediaId = null;
			
			if(question.isValid()){
				try {
					mediaId = FileManager.addNewFile(uploadedInputStream, fileDetail, Bucket.MediaQuestion);
				} catch (Exception e) {
					resp.setError(e.getMessage());
					return resp;
				}

				question.setMediaId(mediaId);
			}
			else {
				throw new WebApplicationException(Response.Status.BAD_REQUEST);
			}

			try
			{
				DBManager.save(question);
				resp.setKey(KeyFactory.keyToString(question.getKey()));
			}
			catch(Exception e)
			{
				//remove file if error saving question
				if(mediaId != null && question.getKey() == null){
					FileManager.removeFile(mediaId, Bucket.MediaQuestion);
				}
				resp.setError(e.getMessage());
			}
		}
		return resp;
	}
	@PUT
	@Path("/general/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public BaseResponse editQuestionGeneral(@Context SecurityContext sc, 
			@PathParam("id") String id, QuestionGeneral question)
	{
		BaseResponse returnValue = new BaseResponse();
		QuestionGeneral oldQuestion = null;

		Key userKey = RoleManager.getCurrentUserKey(sc);

		if(userKey != null && userKey.isComplete()){
			try{
				oldQuestion = (QuestionGeneral)DBManager.getQuestion(KeyFactory.stringToKey(id));
			}
			catch(Exception e){
				throw new WebApplicationException(Response.Status.NOT_FOUND); 
			}

			if(oldQuestion != null){
				if(!oldQuestion.getGroups().isEmpty()){
					List<Role> roleList = new ArrayList<Role>();
					roleList.add(Role.Manager);
					roleList.add(Role.QuestionManager);
					
					if(!RoleManager.userRoleMatchGroup(sc, roleList, oldQuestion.getGroups(), true)){
						throw new WebApplicationException(Response.Status.FORBIDDEN);
					}
				}

				oldQuestion.setAnswer(question.getAnswer());
				oldQuestion.setQuestion(question.getQuestion());
				
				oldQuestion.setAuthor(userKey);
				oldQuestion.setAvailableDate(question.getAvailableDate());
				oldQuestion.setCategory(question.getCategory());
				oldQuestion.setDegree(question.getDegree());
				oldQuestion.setIsReported(question.getIsReported());
				oldQuestion.setLanguage(question.getLanguage());
				oldQuestion.setReportComment(question.getReportComment());
				oldQuestion.setReportReason(question.getReportReason());
				DBManager.save(oldQuestion);
				
				return returnValue;

			}
			else{
				throw new WebApplicationException(Response.Status.NOT_MODIFIED);
			}
		}
		else{
			throw new WebApplicationException(Response.Status.FORBIDDEN);
		}
	}
	
	@PUT
	@Path("/association/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public BaseResponse editQuestionAssociation(@Context SecurityContext sc, 
			@PathParam("id") String id, QuestionAssociation question)
	{
		BaseResponse returnValue = new BaseResponse();
		QuestionAssociation oldQuestion = null;

		Key userKey = RoleManager.getCurrentUserKey(sc);

		if(userKey != null && userKey.isComplete()){
			try{
				oldQuestion = (QuestionAssociation)DBManager.getQuestion(KeyFactory.stringToKey(id));
			}
			catch(Exception e){
				throw new WebApplicationException(Response.Status.NOT_FOUND); 
			}

			if(oldQuestion != null){
				if(!oldQuestion.getGroups().isEmpty()){
					List<Role> roleList = new ArrayList<Role>();
					roleList.add(Role.Manager);
					roleList.add(Role.QuestionManager);

					if(!RoleManager.userRoleMatchGroup(sc, roleList, oldQuestion.getGroups(), true)){
						throw new WebApplicationException(Response.Status.FORBIDDEN);
					}
				}

				oldQuestion.setChoices(question.getChoices());
				oldQuestion.setQuestion(question.getQuestion());
				
				oldQuestion.setAuthor(userKey);
				oldQuestion.setAvailableDate(question.getAvailableDate());
				oldQuestion.setCategory(question.getCategory());
				oldQuestion.setDegree(question.getDegree());
				oldQuestion.setIsReported(question.getIsReported());
				oldQuestion.setLanguage(question.getLanguage());
				oldQuestion.setReportComment(question.getReportComment());
				oldQuestion.setReportReason(question.getReportReason());
				DBManager.save(oldQuestion);
				
				return returnValue;

			}
			else{
				throw new WebApplicationException(Response.Status.NOT_MODIFIED);
			}
		}
		else{
			throw new WebApplicationException(Response.Status.FORBIDDEN);
		}
	}
	
	@PUT
	@Path("/anagram/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public BaseResponse editQuestionAnagram(@Context SecurityContext sc, 
			@PathParam("id") String id, QuestionAnagram question)
	{
		BaseResponse returnValue = new BaseResponse();
		QuestionAnagram oldQuestion = null;

		Key userKey = RoleManager.getCurrentUserKey(sc);

		if(userKey != null && userKey.isComplete()){
			try{
				oldQuestion = (QuestionAnagram)DBManager.getQuestion(KeyFactory.stringToKey(id));
			}
			catch(Exception e){
				throw new WebApplicationException(Response.Status.NOT_FOUND); 
			}

			if(oldQuestion != null){
				if(!oldQuestion.getGroups().isEmpty()){
					List<Role> roleList = new ArrayList<Role>();
					roleList.add(Role.Manager);
					roleList.add(Role.QuestionManager);

					if(!RoleManager.userRoleMatchGroup(sc, roleList, oldQuestion.getGroups(), true)){
						throw new WebApplicationException(Response.Status.FORBIDDEN);
					}
				}

				oldQuestion.setAnagram(question.getAnagram());
				oldQuestion.setAnswer(question.getAnswer());
				
				oldQuestion.setAuthor(userKey);
				oldQuestion.setAvailableDate(question.getAvailableDate());
				oldQuestion.setCategory(question.getCategory());
				oldQuestion.setDegree(question.getDegree());
				oldQuestion.setIsReported(question.getIsReported());
				oldQuestion.setLanguage(question.getLanguage());
				oldQuestion.setReportComment(question.getReportComment());
				oldQuestion.setReportReason(question.getReportReason());
				DBManager.save(oldQuestion);
				
				return returnValue;

			}
			else{
				throw new WebApplicationException(Response.Status.NOT_MODIFIED);
			}
		}
		else{
			throw new WebApplicationException(Response.Status.FORBIDDEN);
		}
	}
	
	@PUT
	@Path("/identification/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public BaseResponse editQuestionIdentification(@Context SecurityContext sc, 
			@PathParam("id") String id, QuestionIdentification question)
	{
		BaseResponse returnValue = new BaseResponse();
		QuestionIdentification oldQuestion = null;

		Key userKey = RoleManager.getCurrentUserKey(sc);

		if(userKey != null && userKey.isComplete()){
			try{
				oldQuestion = (QuestionIdentification)DBManager.getQuestion(KeyFactory.stringToKey(id));
			}
			catch(Exception e){
				throw new WebApplicationException(Response.Status.NOT_FOUND); 
			}

			if(oldQuestion != null){
				if(!oldQuestion.getGroups().isEmpty()){
					List<Role> roleList = new ArrayList<Role>();
					roleList.add(Role.Manager);
					roleList.add(Role.QuestionManager);

					if(!RoleManager.userRoleMatchGroup(sc, roleList, oldQuestion.getGroups(), true)){
						throw new WebApplicationException(Response.Status.FORBIDDEN);
					}
				}

				oldQuestion.setStatements(question.getStatements());
				oldQuestion.setAnswer(question.getAnswer());
				
				oldQuestion.setAuthor(userKey);
				oldQuestion.setAvailableDate(question.getAvailableDate());
				oldQuestion.setCategory(question.getCategory());
				oldQuestion.setDegree(question.getDegree());
				oldQuestion.setIsReported(question.getIsReported());
				oldQuestion.setLanguage(question.getLanguage());
				oldQuestion.setReportComment(question.getReportComment());
				oldQuestion.setReportReason(question.getReportReason());
				DBManager.save(oldQuestion);
				
				return returnValue;

			}
			else{
				throw new WebApplicationException(Response.Status.NOT_MODIFIED);
			}
		}
		else{
			throw new WebApplicationException(Response.Status.FORBIDDEN);
		}
	}
	
	@PUT
	@Path("/intru/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public BaseResponse editQuestionIntru(@Context SecurityContext sc, 
			@PathParam("id") String id, 
			QuestionIntru question)
	{
		BaseResponse returnValue = new BaseResponse();
		QuestionIntru oldQuestion = null;

		Key userKey = RoleManager.getCurrentUserKey(sc);

		if(userKey != null && userKey.isComplete()){
			try{
				oldQuestion = (QuestionIntru)DBManager.getQuestion(KeyFactory.stringToKey(id));
			}
			catch(Exception e){
				throw new WebApplicationException(Response.Status.NOT_FOUND); 
			}

			if(oldQuestion != null){
				if(!oldQuestion.getGroups().isEmpty()){
					List<Role> roleList = new ArrayList<Role>();
					roleList.add(Role.Manager);
					roleList.add(Role.QuestionManager);

					if(!RoleManager.userRoleMatchGroup(sc, roleList, oldQuestion.getGroups(), true)){
						throw new WebApplicationException(Response.Status.FORBIDDEN);
					}
				}

				oldQuestion.setChoices(question.getChoices());
				oldQuestion.setAnswer(question.getAnswer());
				
				oldQuestion.setAuthor(userKey);
				oldQuestion.setAvailableDate(question.getAvailableDate());
				oldQuestion.setCategory(question.getCategory());
				oldQuestion.setDegree(question.getDegree());
				oldQuestion.setIsReported(question.getIsReported());
				oldQuestion.setLanguage(question.getLanguage());
				oldQuestion.setReportComment(question.getReportComment());
				oldQuestion.setReportReason(question.getReportReason());
				DBManager.save(oldQuestion);
				
				return returnValue;

			}
			else{
				throw new WebApplicationException(Response.Status.NOT_MODIFIED);
			}
		}
		else{
			throw new WebApplicationException(Response.Status.FORBIDDEN);
		}
	}
	
	@PUT
	@Path("/media/{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public BaseResponse editQuestionMedia(@Context SecurityContext sc, 
			@PathParam("id") String id, 
			QuestionMedia question) {
		BaseResponse returnValue = new BaseResponse();
		QuestionMedia oldQuestion = null;

		Key userKey = RoleManager.getCurrentUserKey(sc);

		if(userKey != null && userKey.isComplete()){
			try{
				oldQuestion = (QuestionMedia)DBManager.getQuestion(KeyFactory.stringToKey(id));
			}
			catch(Exception e){
				throw new WebApplicationException(Response.Status.NOT_FOUND); 
			}

			if(oldQuestion != null){
				if(!oldQuestion.getGroups().isEmpty()){
					List<Role> roleList = new ArrayList<Role>();
					roleList.add(Role.Manager);
					roleList.add(Role.QuestionManager);

					if(!RoleManager.userRoleMatchGroup(sc, roleList, oldQuestion.getGroups(), true)){
						throw new WebApplicationException(Response.Status.FORBIDDEN);
					}
				}
				
				oldQuestion.setStatement(question.getStatement());
				oldQuestion.setAnswer(question.getAnswer());
				
				oldQuestion.setAuthor(userKey);
				oldQuestion.setAvailableDate(question.getAvailableDate());
				oldQuestion.setCategory(question.getCategory());
				oldQuestion.setDegree(question.getDegree());
				oldQuestion.setIsReported(question.getIsReported());
				oldQuestion.setLanguage(question.getLanguage());
				oldQuestion.setReportComment(question.getReportComment());
				oldQuestion.setReportReason(question.getReportReason());
				DBManager.save(oldQuestion);
				
				return returnValue;

			}
			else{
				throw new WebApplicationException(Response.Status.NOT_MODIFIED);
			}
		}
		else{
			throw new WebApplicationException(Response.Status.FORBIDDEN);
		}
	}
	
	@PUT
	@Path("/media/{id}/changeMedia")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public BaseResponse editQuestionMedia(@Context SecurityContext sc, 
			@PathParam("id") String id,
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataBodyPart fileDetail) {
		
		BaseResponse returnValue = new BaseResponse();
		QuestionMedia oldQuestion = null;

		Key userKey = RoleManager.getCurrentUserKey(sc);

		if(userKey != null && userKey.isComplete()){
			try{
				oldQuestion = (QuestionMedia)DBManager.getQuestion(KeyFactory.stringToKey(id));
			}
			catch(Exception e){
				throw new WebApplicationException(Response.Status.NOT_FOUND); 
			}

			if(oldQuestion != null){
				if(!oldQuestion.getGroups().isEmpty()){
					List<Role> roleList = new ArrayList<Role>();
					roleList.add(Role.Manager);
					roleList.add(Role.QuestionManager);

					if(!RoleManager.userRoleMatchGroup(sc, roleList, oldQuestion.getGroups(), true)){
						throw new WebApplicationException(Response.Status.FORBIDDEN);
					}
				}
				
				if(fileDetail != null && uploadedInputStream != null){
					try {
						FileManager.replaceFile(uploadedInputStream, fileDetail, 
								Bucket.MediaQuestion, oldQuestion.getMediaId());
					} catch (Exception e) {
						returnValue.setError(e.getMessage());
					}
				}
				
				return returnValue;

			}
			else{
				throw new WebApplicationException(Response.Status.NOT_MODIFIED);
			}
		}
		else{
			throw new WebApplicationException(Response.Status.FORBIDDEN);
		}
	}

	@DELETE
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public BaseResponse removeQuestion(@Context SecurityContext sc, @PathParam("id") String id) {
		BaseResponse resp = new BaseResponse();

		Question q;
		try{
			q = DBManager.getQuestion(KeyFactory.stringToKey(id));
		}
		catch(Exception e){
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		if(q != null){
			List<Role> roleList = new ArrayList<Role>();
			roleList.add(Role.QuestionManager);
			roleList.add(Role.Manager);
			if(RoleManager.userRoleMatchGroup(sc, roleList, q.getGroups(), true)) {
				try {
					for(Key k : RoleManager.getUserGroups(sc, roleList)){
						q.removeGroup(DBManager.getGroup(k));
					}

					if(q.getGroups().size() <= 1){
						q.delete();
						return resp;
					}
					else
						throw new WebApplicationException(Response.Status.FORBIDDEN);
				}
				catch(Exception e)
				{
					resp.setError(e.getMessage());
					return resp;
				}
			}
			else {
				throw new WebApplicationException(Response.Status.FORBIDDEN);
			}
		}
		else {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
	}
	
	@GET
	@Path("updateAvailableQuestions")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response UpdateAvailableQuestions() {
		DBManager.UpdateAvailableQuestions();
		return Response.ok().build();
	}
}
