package ca.openquiz.webservice.manager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.jdo.Extent;
import javax.jdo.PersistenceManager;
import javax.jdo.Query;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;

import org.codehaus.jackson.map.ObjectMapper;

import com.google.appengine.api.ThreadManager;
import com.google.appengine.api.datastore.Key;

import ca.openquiz.webservice.enums.Degree;
import ca.openquiz.webservice.enums.Language;
import ca.openquiz.webservice.enums.QuestionType;
import ca.openquiz.webservice.enums.Role;
import ca.openquiz.webservice.model.*;
import ca.openquiz.webservice.parameter.SearchAvailableQuestionsParam;
import ca.openquiz.webservice.parameter.SearchCategoryParam;
import ca.openquiz.webservice.parameter.SearchGameLogParam;
import ca.openquiz.webservice.parameter.SearchGameParam;
import ca.openquiz.webservice.parameter.SearchGameStatParam;
import ca.openquiz.webservice.parameter.SearchGroupParam;
import ca.openquiz.webservice.parameter.SearchGroupRoleParam;
import ca.openquiz.webservice.parameter.SearchTemplateSectionParam;
import ca.openquiz.webservice.parameter.SearchQuestionSetParam;
import ca.openquiz.webservice.parameter.SearchTeamParam;
import ca.openquiz.webservice.parameter.SearchTournamentParam;
import ca.openquiz.webservice.parameter.SearchUserParam;
import ca.openquiz.webservice.parameter.SearchQuestionParam;
import ca.openquiz.webservice.tools.ExceptionLogger;
import ca.openquiz.webservice.tools.QuestionIterator;
public class DBManager {

	private static final String myPackage = "ca.openquiz.webservice.model.";

	//****************************************************************************************************//
	//**										 GET ELEMENT											**//
	//****************************************************************************************************//
	public static User getUser(Key userKey) {
		if(userKey == null)
			return null;

		PersistenceManager pm = PMF.get().getPersistenceManager();
		User user = null;

		try {
			pm.setDetachAllOnCommit(true);
			user = pm.getObjectById(User.class, userKey.getId());
		} catch (Exception e) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		} finally {
			pm.close();
		}

		return user;
	}

	@SuppressWarnings("unchecked")
	public static Session getSession(String sessionKey){
		if(sessionKey == null || sessionKey.isEmpty())
			return null;

		PersistenceManager pm = PMF.get().getPersistenceManager();
		List<Session> sessions = null;
		Session returnValue = null;

		try {
			Query query = pm.newQuery(Session.class);
			pm.setDetachAllOnCommit(true);
			query.setFilter("sessionKey == sessionKeyParam");
			query.declareParameters("String sessionKeyParam");
			query.declareImports("import com.google.appengine.api.datastore.Key;");

			sessions = (List<Session>) query.execute(sessionKey);

			if(sessions.size() == 1){
				returnValue = sessions.get(0);
			}
		} catch (Exception e) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		} finally {
			pm.close();
		}

		return returnValue;
	}

	public static Category getCategory(Key catKey) {
		if(catKey == null)
			return null;

		PersistenceManager pm = PMF.get().getPersistenceManager();
		Category cat = null;

		try {
			pm.setDetachAllOnCommit(true);
			cat = pm.getObjectById(Category.class, catKey.getId());
		} catch (Exception e) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		} finally {
			pm.close();
		}

		return cat;
	}

	public static Team getTeam(Key teamKey) {
		if(teamKey == null)
			return null;

		PersistenceManager pm = PMF.get().getPersistenceManager();

		Team team = null;

		try {
			pm.setDetachAllOnCommit(true);
			team = pm.getObjectById(Team.class, teamKey.getId());
		} catch (Exception e) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		} finally {
			pm.close();
		}

		return team;
	}

	public static Group getGroup(Key groupKey) {
		if(groupKey == null)
			return null;

		PersistenceManager pm = PMF.get().getPersistenceManager();
		Group group = null;

		try {
			pm.setDetachAllOnCommit(true);
			group = pm.getObjectById(Group.class, groupKey.getId());
		} catch (Exception e) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		} finally {
			pm.close();
		}

		return group;
	}

	public static GroupRole getGroupRole(Key groupRoleKey) {
		if(groupRoleKey == null)
			return null;

		PersistenceManager pm = PMF.get().getPersistenceManager();
		GroupRole groupRole = null;

		try {
			pm.setDetachAllOnCommit(true);
			groupRole = pm.getObjectById(GroupRole.class, groupRoleKey.getId());
		} catch (Exception e) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		} finally {
			pm.close();
		}

		return groupRole;
	}

	@SuppressWarnings("unchecked")
	public static Role getRole(Key userKey, Key groupKey) {
		if(userKey == null || groupKey == null)
			return null;

		PersistenceManager pm = PMF.get().getPersistenceManager();

		Query query = pm.newQuery(GroupRole.class);
		query.setFilter("user == userKeyParam && group == groupKeyParam");
		query.declareParameters("Key userKeyParam, Key groupKeyParam");
		query.declareImports("import com.google.appengine.api.datastore.Key;");

		List<GroupRole> roles = (List<GroupRole>) query.execute(userKey, groupKey);

		Role role = null;
		if(roles.size() == 1){
			role = roles.get(0).getRole();
		}
		else if(roles.size() > 1){
			for(GroupRole r : roles){
				if(r.getRole() == Role.Manager){
					role = Role.Manager;
				}
				else if(r.getRole() == Role.QuestionManager && role != Role.Manager){
					role = Role.QuestionManager;
				}
				else if(r.getRole() == Role.Player && role != Role.Manager && role != Role.QuestionManager){
					role = Role.Player;
				}
			}
		}

		pm.close();
		return role;
	}

	public static Question getQuestion(Key questionKey) {
		if(questionKey == null)
			return null;

		PersistenceManager pm = PMF.get().getPersistenceManager();

		Question returnValue = null;
		try {
			returnValue = (Question) pm.getObjectById(
					Class.forName(myPackage + questionKey.getKind()),
					questionKey.getId());
		} catch (ClassNotFoundException e) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		} finally {
			pm.close();
		}
		return returnValue;
	}

	public static QuestionSet getQuestionSet(Key questionSetKey) {
		if(questionSetKey == null)
			return null;

		PersistenceManager pm = PMF.get().getPersistenceManager();
		QuestionSet questionSet = null;

		try {
			pm.setDetachAllOnCommit(true);
			questionSet = pm.getObjectById(QuestionSet.class, questionSetKey.getId());
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		} finally {
			//Lorsque le persistence manager est ferm��� la classe retourn��� ne comporte pas les sections
			pm.close();
		}
		return questionSet;
	}

	public static List<QuestionSetSection> createQuestionSetSections(List<TemplateSection> sections, List<Key> groups, boolean includePublic){

		List<QuestionSetSection> returnValue = new ArrayList<QuestionSetSection>();
		List<Key> selectedQuestions = new ArrayList<Key>();

		if(sections != null){
			for (TemplateSection sectionTemplate : sections) {

				final int bufferHeap = 2;

				QuestionSetSection newSection = new QuestionSetSection();

				newSection = new QuestionSetSection();
				newSection.setPoints(sectionTemplate.getPoints());
				newSection.setDescription(sectionTemplate.getDescription());
				newSection.setQuestionTarget(sectionTemplate.getQuestionTarget());

				SearchAvailableQuestionsParam searchAvailableQuestionsParam = new SearchAvailableQuestionsParam();
				searchAvailableQuestionsParam.setCategory(sectionTemplate.getCategory());
				searchAvailableQuestionsParam.setDegree(sectionTemplate.getDifficulty());
				searchAvailableQuestionsParam.setGroups(groups);
				searchAvailableQuestionsParam.setIncludePublic(includePublic);
				searchAvailableQuestionsParam.setLanguage(Language.fr);
				searchAvailableQuestionsParam.setType(sectionTemplate.getQuestionType());

				int availableQuestionsQty = 0;
				for(AvailableQuestions aq : searchAvailableQuestions(searchAvailableQuestionsParam)){
					availableQuestionsQty += aq.getQty();
				}

				SearchQuestionParam param = new SearchQuestionParam();
				param.setCategory(sectionTemplate.getCategory());
				param.setDegree(sectionTemplate.getDifficulty());
				param.setGroups(groups);
				param.setLanguage(Language.fr);
				param.setType(sectionTemplate.getQuestionType());
				param.setIncludePublic(includePublic);
				param.setPageNumber((int)Math.floor(Math.random()*availableQuestionsQty/bufferHeap));
				param.setResultsByPage(bufferHeap*sectionTemplate.getNbQuestions());
				param.setReported(false);


				List<Question> questions = DBManager.searchQuestion(param);

				Collections.sort(questions, new Comparator<Question>() {
					public int compare(Question q1, Question q2) {
						if(q1 == null &&  q2 == null){
							return 0;
						}
						else if(q1 == null){
							return -1;
						}
						else if(q2 == null){
							return 1;
						}
						
						return q1.getAvailableDate().compareTo(q2.getAvailableDate());
					}
				});

				for(Question q : questions){
					if(!selectedQuestions.contains(q.getKey())){
						newSection.addQuestion(q);
						q.setLastGeneratedDate(new Date());
						DBManager.save(q);
						selectedQuestions.add(q.getKey());
					}
					
					if(newSection.getQuestionList().size() == sectionTemplate.getNbQuestions()){
						break;
					}
				}

				if(!newSection.getQuestionList().isEmpty()){
					returnValue.add(newSection);
				}
			}
		}	

		return returnValue;
	}

	public static QuestionSetSection getQuestionSetSection(Key questionSetSectionKey) {
		if(questionSetSectionKey == null)
			return null;

		PersistenceManager pm = PMF.get().getPersistenceManager();
		QuestionSetSection questionSetSection = null;

		try {
			pm.setDetachAllOnCommit(true);
			questionSetSection = pm.getObjectById(QuestionSetSection.class, questionSetSectionKey.getId());
		} catch (Exception e) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		} finally {
			pm.close();
		}
		return questionSetSection;
	}

	public static Template getTemplate(Key templateKey) {
		if(templateKey == null)
			return null;

		PersistenceManager pm = PMF.get().getPersistenceManager();
		Template template;

		try {
			pm.setDetachAllOnCommit(true);
			template = pm.getObjectById(Template.class, templateKey.getId());
		} catch (Exception e) {
			e.printStackTrace();
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		} finally {
			pm.close();
		}

		return template;
	}

	public static TemplateSection getTemplateSection(Key templateSectionKey) {
		if(templateSectionKey == null)
			return null;

		PersistenceManager pm = PMF.get().getPersistenceManager();
		TemplateSection templateSection;

		try {
			pm.setDetachAllOnCommit(true);
			templateSection = pm.getObjectById(TemplateSection.class,
					templateSectionKey.getId());
		} catch (Exception e) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		} finally {
			pm.close();
		}
		return templateSection;
	}

	public static Tournament getTournament(Key tournamentKey) {
		if(tournamentKey == null)
			return null;

		PersistenceManager pm = PMF.get().getPersistenceManager();
		Tournament tournament = null;

		try {
			tournament = pm.getObjectById(Tournament.class,
					tournamentKey.getId());
		} catch (Exception e) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		} finally {
			pm.close();
		}

		return tournament;
	}

	public static Game getGame(Key gameKey) {
		if(gameKey == null)
			return null;

		PersistenceManager pm = PMF.get().getPersistenceManager();
		Game game = null;

		try {
			pm.setDetachAllOnCommit(true);
			game = pm.getObjectById(Game.class, gameKey.getId());
		} catch (Exception e) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		} finally {
			pm.close();
		}

		return game;
	}

	public static GameLog getGameLog(Key gameLogKey) {
		if(gameLogKey == null)
			return null;

		PersistenceManager pm = PMF.get().getPersistenceManager();
		GameLog gameLog;

		try {
			pm.setDetachAllOnCommit(true);
			gameLog = pm.getObjectById(GameLog.class,
					gameLogKey.getId());
		} catch (Exception e) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		} finally {
			pm.close();
		}
		return gameLog;
	}

	//****************************************************************************************************//
	//**										 SEARCH ITEMS											**//
	//****************************************************************************************************//
	@SuppressWarnings("unchecked")
	public static List<User> searchUsers(SearchUserParam param) {
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(User.class);

		String filter = "";
		List<User> list = new ArrayList<User>();
		List<User> returnList = new ArrayList<User>();		

		try {
			pm.setDetachAllOnCommit(true);

			if (param.getLanguage() != null) {
				filter += "language == languageParam";
			}	
			if(param.getTeam() != null)
			{
				if(!filter.isEmpty())
					filter += " && ";
				filter += "teams.contains(teamParam)";
			}		
			if(param.getEmail() != null)
			{
				if(!filter.isEmpty())
					filter += " && ";
				filter += "email == emailParam";
			}

			if(filter != "")
				query.setFilter(filter);

			if (param.getResultsByPage() != 0 && param.getPageNumber() != 0) {
				query.setRange(param.getResultsByPage() * (param.getPageNumber() - 1), 
						param.getResultsByPage() * param.getPageNumber());
			}

			query.declareParameters("String languageParam, String groupParam, String teamParam, String emailParam");
			query.declareImports("import com.google.appengine.api.datastore.Key;");

			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("languageParam", param.getLanguage());
			parameters.put("teamParam", param.getTeam());
			parameters.put("emailParam", param.getEmail());
			list = (List<User>) query.executeWithMap(parameters);
			list.size();
		}
		catch(WebApplicationException e)
		{
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		} finally {
			pm.close();
		}

		if (param.getGroup() != null) {
			SearchGroupRoleParam groupRoleParam = new SearchGroupRoleParam();
			groupRoleParam.setGroup(param.getGroup());
			for(GroupRole gr : searchGroupRole(groupRoleParam))
			{
				for(User u : list)
				{
					if(u.getKey().equals(gr.getUser()))
					{
						returnList.add(DBManager.getUser(gr.getUser()));
					}
				}
			}
		}	
		else
			returnList = list;

		return returnList;
	}

	@SuppressWarnings("unchecked")
	public static List<Session> searchSessions(Key userId){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(Session.class);

		List<Session> list = null;

		try
		{
			pm.setDetachAllOnCommit(true);

			query.declareImports("import com.google.appengine.api.datastore.Key;");
			query.setFilter("userId == userParam");
			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("userParam", userId);
			list = (List<Session>) query.executeWithMap(parameters);

			list.size();
		}
		catch(WebApplicationException e)
		{
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		finally{
			pm.close();
		}

		return list;
	}



	@SuppressWarnings("unchecked")
	public static List<Team> searchTeams(SearchTeamParam param)
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(Team.class);

		String filter = "";
		List<Team> list = null;

		try
		{
			pm.setDetachAllOnCommit(true);

			if(param.getGroup() != null)
			{
				if(!filter.isEmpty())
					filter += " && ";
				filter += "group == :groupParam";
			}

			if(param.getUser() != null)
			{
				if(!filter.isEmpty())
					filter += " && ";
				filter += "users.contains(:userParam)";
			}

			if(param.getTournament() != null)
			{
				if(!filter.isEmpty())
					filter += " && ";
				filter += "tournaments.contains(:tournamentParam)";
			}

			if (!filter.isEmpty())
				filter += " && ";
			filter += "active == :activeParam";

			if(filter != "")
				query.setFilter(filter);

			if(param.getResultsByPage() != 0 && param.getPageNumber() != 0)
			{
				query.setRange(param.getResultsByPage()*(param.getPageNumber()-1), 
						param.getResultsByPage()*param.getPageNumber());
			}

			query.declareImports("import com.google.appengine.api.datastore.Key;");

			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("groupParam", param.getGroup());
			parameters.put("userParam", param.getUser());
			parameters.put("tournamentParam", param.getTournament());
			parameters.put("activeParam", param.isActive());
			list = (List<Team>) query.executeWithMap(parameters);

			list.size();
		}
		catch(WebApplicationException e)
		{
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		finally{
			pm.close();
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	public static List<Group> searchGroups(SearchGroupParam param)
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(Group.class);

		String filter = "";
		List<Group> list = null;

		try
		{
			pm.setDetachAllOnCommit(true);

			if(param.getQuestion() != null)
			{
				if(!filter.isEmpty())
					filter += " && ";
				filter += "questions.contains(questionParam)";
			}
			if(param.getTournament() != null)
			{
				if(!filter.isEmpty())
					filter += " && ";
				filter += "tournaments.contains(tournamentParam)";
			}
			if(filter != "")
				query.setFilter(filter);

			if(param.getResultsByPage() != 0 && param.getPageNumber() != 0)
			{
				query.setRange(param.getResultsByPage()*(param.getPageNumber()-1), 
						param.getResultsByPage()*param.getPageNumber());
			}

			query.declareParameters("String questionParam");
			query.declareImports("import com.google.appengine.api.datastore.Key;");

			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("questionParam", param.getQuestion());
			parameters.put("tournamentParam", param.getTournament());
			list = (List<Group>) query.executeWithMap(parameters);

			list.size();
		}
		catch(WebApplicationException e)
		{
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		finally{
			pm.close();
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	public static List<GroupRole> searchGroupRole(SearchGroupRoleParam param)
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(GroupRole.class);

		String filter = "";
		List<GroupRole> list = null;

		try
		{
			pm.setDetachAllOnCommit(true);

			if(param.getGroup() != null)
			{
				filter += "group == groupParam";
			}
			if(param.getUser() != null)
			{
				if(!filter.isEmpty())
					filter += " && ";
				filter += "user == userParam";
			}
			if(param.getRole() != null)
			{
				if(!filter.isEmpty())
					filter += " && ";
				filter += "role == roleParam";
			}
			if(filter != "")
				query.setFilter(filter);

			if(param.getResultsByPage() != 0 && param.getPageNumber() != 0)
			{
				query.setRange(param.getResultsByPage()*(param.getPageNumber()-1), 
						param.getResultsByPage()*param.getPageNumber());
			}

			query.declareParameters("String groupParam, String userParam, String roleParam");
			query.declareImports("import com.google.appengine.api.datastore.Key;");
			query.declareImports("import ca.openquiz.webservice.enums.Role;");

			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("groupParam", param.getGroup());
			parameters.put("userParam", param.getUser());
			parameters.put("roleParam", param.getRole());
			list = (List<GroupRole>) query.executeWithMap(parameters);

			list.size();
		}
		catch(WebApplicationException e)
		{
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		finally{
			pm.close();
		}

		return list;
	}
	/*
	private static List<Question> searchQuestionChunk(SearchQuestionParam param) {
		List<Question> returnValue = new ArrayList<Question>();
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query;

		String filter = "";
		HashMap<String, Object> parameters = new HashMap<String, Object>();

		try {
			String str = myPackage + "Question" + param.getType();
			query = pm.newQuery(Class.forName(str));
		} catch (ClassNotFoundException e) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}

		// Conditions de la recherche
		if (param.getAuthor() != null) {
			if (!filter.isEmpty())
				filter += " && ";
			filter += "author == userKeyParam";
		}
		if (param.getCategory() != null) {
			if (!filter.isEmpty())
				filter += " && ";
			filter += "category == categoryKeyParam";
		}
		if (param.getLanguage() != null) {
			if (!filter.isEmpty())
				filter += " && ";
			filter += "language == langParam";
		}
		if (param.getDegree() != null) {
			if (!filter.isEmpty())
				filter += " && ";
			filter += "degree == degreeParam";
		}
		if (!filter.isEmpty())
			filter += " && ";
		filter += "isReported == reportedParam";
		if (!filter.isEmpty())
			filter += " && ";
		filter += "availableDate <= availableParam";

		if (filter != "")
			query.setFilter(filter);

		if (param.getResultsByPage() != 0 && param.getPageNumber() != 0) {
			query.setRange(param.getResultsByPage() * (param.getPageNumber() - 1),
					param.getResultsByPage() * param.getPageNumber());
		}

		//query.setOrdering("lastGeneratedDate ascending");

		// query.declareParameters("String languageParam, String groupParam");
		query.declareImports("import com.google.appengine.api.datastore.Key;");
		query.declareImports("import ca.openquiz.webservice.enums.Degree;");
		query.declareImports("import ca.openquiz.webservice.enums.Role;");
		query.declareImports("import ca.openquiz.webservice.enums.Language;");

		query.declareParameters("String userKeyParam, String groupKeyParam, String langParam, " +
				"String categoryKeyParam, String degreeParam, String reportedParam, String availableParam");

		parameters.put("userKeyParam", param.getAuthor());
		parameters.put("langParam", param.getLanguage());
		parameters.put("categoryKeyParam", param.getCategory());
		parameters.put("degreeParam", param.getDegree());
		parameters.put("reportedParam", param.isReported());
		parameters.put("availableParam", new Date());


		FetchOptions fetch_options = FetchOptions.Builder.withPrefetchSize(100).chunkSize(100);
	    QueryResultList<Question> returned_entities = datastore_service_instance.prepare(query).asQueryResultList(fetch_options);

		@SuppressWarnings("unchecked")
		List<Question> q = (List<Question>) query.executeWithMap(parameters);
		q.size();
		returnValue.addAll(q);

		pm.close();
		return returnValue;
	}

	public static List<Question> searchQuestion(SearchQuestionParam param) {
		List<Question> returnValue = new ArrayList<Question>();

		int min = 0;
		int max = 0;

		if (param.getResultsByPage() > 0 && param.getPageNumber() > 0) {
			min = param.getResultsByPage() * (param.getPageNumber() - 1);
			max = param.getResultsByPage() * param.getPageNumber();
		}

		int maxResultQty = max - min;

		int resultQty = 0;

		param.setResultsByPage(20);
		param.setPageNumber(0);
		List<Question> questions = searchQuestionChunk(param);
		//while(returnValue.size() < maxResultQty && questions.size() > 0 ) {
		for (Question question : questions) {
			//Filter groups
			if(param.getGroups().isEmpty() && !question.getGroups().isEmpty()){
				continue;
			}
			if(!param.getIncludePublic() && !param.getGroups().isEmpty() && question.getGroups().isEmpty()){
				continue;
			}
			if(!param.getGroups().isEmpty() && !question.getGroups().isEmpty()){
				boolean matchGroup = false;
				for(Key groupKey : param.getGroups()){
					if(question.getGroups().contains(groupKey)){
						matchGroup = true;
						break;
					}
				}
				if(!matchGroup){
					continue;
				}
			}

			if((resultQty >= min && resultQty < max) || maxResultQty == 0) {
				returnValue.add(question);
				if(returnValue.size() == maxResultQty){
					return returnValue;
				}
			}
			resultQty++;
		}

		param.setPageNumber(param.getPageNumber() + 1);
		questions = searchQuestionChunk(param);
		//}

		return returnValue;
	}
	 */

	public static List<Question> searchQuestion(SearchQuestionParam param) {
		List<Question> returnValue = new ArrayList<Question>();

		int min = 0;
		int max = 0;

		if (param.getResultsByPage() > 0 && param.getPageNumber() > 0) {
			min = param.getResultsByPage() * (param.getPageNumber() - 1);
			max = param.getResultsByPage() * param.getPageNumber();
		}

		int maxResultQty = max - min;

		int resultQty = 0;

		QuestionIterator qItr = new QuestionIterator(param);
		Question question;
		
		param.setOffset(0);

		while(qItr.hasNext() && returnValue.size() < maxResultQty){
			question = qItr.next();

			//Filter groups
			if(param.getGroups().isEmpty() && question.getGroups() != null && !question.getGroups().isEmpty()){
				continue;
			}
			if(!param.isIncludePublic() && !param.getGroups().isEmpty() && (question.getGroups() == null || question.getGroups().isEmpty())){
				continue;
			}
			if(!param.getGroups().isEmpty() && !question.getGroups().isEmpty()){
				boolean matchGroup = false;
				for(Key groupKey : param.getGroups()){
					if(question.getGroups().contains(groupKey)){
						matchGroup = true;
						break;
					}
				}
				if(!matchGroup){
					continue;
				}
			}

			if((resultQty >= min && resultQty < max) || maxResultQty == 0){
				returnValue.add(question);
			}

			resultQty++;
		}
		return returnValue;
	}

	public static List<Question> searchQuestion(TemplateSection templateSection, List<Key> groups, boolean limit, boolean includePublic) {

		SearchQuestionParam param = new SearchQuestionParam();
		if(limit){
			param.setResultsByPage(500);
		}
		else{
			param.setResultsByPage(0);
		}
		param.setPageNumber(0);
		param.setIncludePublic(includePublic);

		if(templateSection.getCategory() != null)
			param.setCategory(templateSection.getCategory());
		if(templateSection.getDifficulty() != null)
			param.setDegree(templateSection.getDifficulty());
		if(templateSection.getQuestionType() != null)
			param.setType(templateSection.getQuestionType());
		if(groups != null){
			param.setGroups(groups);
		}

		return searchQuestion(param);
	}

	public static List<AvailableQuestions> searchAvailableQuestions(SearchAvailableQuestionsParam param){
		List<AvailableQuestions> returnValue = new ArrayList<AvailableQuestions>();

		if(param.isIncludePublic()){
			returnValue.addAll(getAvailableQuestions(param.getType(), param.getCategory(), null, param.getDegree(), param.getLanguage()));
		}

		if(param.getGroups() != null && !param.getGroups().isEmpty()) {
			for(Key group : param.getGroups()){
				returnValue.addAll(getAvailableQuestions(param.getType(), param.getCategory(), group, param.getDegree(), param.getLanguage()));
			}
		}

		return returnValue;
	}

	private static List<AvailableQuestions> getAvailableQuestions(QuestionType type, Key cat, Key group, Degree degree, Language lang) {

		List<AvailableQuestions> returnValue = new ArrayList<AvailableQuestions>();
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query;

		query = pm.newQuery(AvailableQuestions.class);

		String filter = "";
		HashMap<String, Object> parameters = new HashMap<String, Object>();

		// Conditions de la recherche
		if (type != null) {
			if (!filter.isEmpty())
				filter += " && ";
			filter += "type == typeParam";
		}
		if (cat != null) {
			if (!filter.isEmpty())
				filter += " && ";
			filter += "category == categoryKeyParam";
		}
		if (lang != null) {
			if (!filter.isEmpty())
				filter += " && ";
			filter += "language == langParam";
		}
		if (degree != null) {
			if (!filter.isEmpty())
				filter += " && ";
			filter += "degree == degreeParam";
		}
		if (group != null) {
			if (!filter.isEmpty())
				filter += " && ";
			filter += "groups.contains(groupParam)";
		}
		else{
			if (!filter.isEmpty())
				filter += " && ";
			filter += "groups == null";
		}

		if (filter != "")
			query.setFilter(filter);

		// query.declareParameters("String languageParam, String groupParam");
		query.declareImports("import com.google.appengine.api.datastore.Key;");
		query.declareImports("import ca.openquiz.webservice.enums.Degree;");
		query.declareImports("import ca.openquiz.webservice.enums.Language;");
		query.declareImports("import ca.openquiz.webservice.enums.QuestionType;");

		query.declareParameters("String typeParam, String langParam, String categoryKeyParam, " +
				"String degreeParam, String groupParam");

		parameters.put("typeParam", type);
		parameters.put("langParam", lang);
		parameters.put("categoryKeyParam", cat);
		parameters.put("degreeParam", degree);
		parameters.put("groupParam", group);

		@SuppressWarnings("unchecked")
		List<AvailableQuestions> aq = (List<AvailableQuestions>) query.executeWithMap(parameters);
		returnValue.addAll(aq);

		pm.close();
		return returnValue;
	}

	public static void UpdateAvailableQuestions(){
		Thread thread = ThreadManager.createBackgroundThread(new Runnable() {
			public void run() {

				List<AvailableQuestions> savedList = new ArrayList<AvailableQuestions>();

				for(QuestionType type : QuestionType.values()){

					Date now = new Date();
					Date tomorrow = new Date(now.getTime() + 1000*60*60*24);

					SearchQuestionParam param = new SearchQuestionParam();
					param.setAuthor(null);
					param.setCategory(null);
					param.setDegree(null);
					param.setLanguage(null);
					param.setReported(false);
					param.setType(type);
					param.setAvailableDate(tomorrow);
					param.setResultsByPage(250);
					param.setPageNumber(1);
					param.setOffset(0);

					QuestionIterator qItr = new QuestionIterator(param);
					Question q;

					while(qItr.hasNext()){
						q = qItr.next();

						AvailableQuestions aq;
						if(q.getGroups() == null || q.getGroups().isEmpty()){
							aq = new AvailableQuestions(q.getQuestionType(), q.getCategory(), null, q.getDegree(), q.getLanguage(), 1);
						}
						else{
							aq = new AvailableQuestions(q.getQuestionType(), q.getCategory(), q.getGroups(), q.getDegree(), q.getLanguage(), 1);
						}

						AvailableQuestions matchingAQ = availableQuestionExistInList(savedList, aq);

						if(matchingAQ == null){
							savedList.add(aq);
						}
						else {
							matchingAQ.setQty(matchingAQ.getQty() + 1);
						}
					}
				}

				removeAvailableQuestions();

				for(AvailableQuestions aq : savedList){
					DBManager.save(aq);
				}
			}
		});

		try{
			thread.start();
		} catch(Exception e){
			ExceptionLogger.getLogger().severe(e.toString());
		}
	}

	private static void removeAvailableQuestions(){
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Extent<AvailableQuestions> extent = pm.getExtent(AvailableQuestions.class, false);
		for (AvailableQuestions aq : extent) {
			aq.delete();
		}
		extent.closeAll();
		pm.close();
	}

	private static AvailableQuestions availableQuestionExistInList(List<AvailableQuestions> aqList, AvailableQuestions matchAQ){

		for(AvailableQuestions aq : aqList){
			if(aq.getCategory().equals(matchAQ.getCategory()) 
					&& aq.getDegree().equals(matchAQ.getDegree()) 
					&& aq.getGroup().equals(matchAQ.getGroup()) 
					&& aq.getLanguage().equals(matchAQ.getLanguage()) 
					&& aq.getType().equals(matchAQ.getType())){
				return aq;
			}
		}
		return null;
	}

	public static Question searchRandomQuestion(SearchQuestionParam param) {
		Question returnValue = null;

		SearchAvailableQuestionsParam searchAvailableQuestionsParam = new SearchAvailableQuestionsParam();
		searchAvailableQuestionsParam.setCategory(param.getCategory());
		searchAvailableQuestionsParam.setDegree(param.getDegree());
		searchAvailableQuestionsParam.setGroups(param.getGroups());
		searchAvailableQuestionsParam.setIncludePublic(param.isIncludePublic());
		searchAvailableQuestionsParam.setLanguage(Language.fr);
		searchAvailableQuestionsParam.setType(param.getType());

		List<AvailableQuestions> aqList = searchAvailableQuestions(searchAvailableQuestionsParam);

		int availableQty = 0;
		for(AvailableQuestions aq : aqList){
			availableQty += aq.getQty();
		}

		if(availableQty == 0){
			return null;
		}

		int offset = (int)Math.floor(availableQty*Math.random());

		param.setOffset(offset);
		param.setResultsByPage(1);

		List<Question> results = searchQuestion(param);
		if(results != null && !results.isEmpty()){
			returnValue = results.get(0);
		}

		if(returnValue == null){
			int maxCount = availableQty;
			int count = 0;

			while(returnValue == null && count < maxCount && count < 15){
				offset += param.getResultsByPage();
				count++;
				if(param.getPageNumber() >= availableQty){
					offset = 1;
				}
				param.setOffset(offset);
				results = searchQuestion(param);
				if(results != null && !results.isEmpty()){
					returnValue = results.get(0);
				}
			}
		}

		return returnValue;
	}

	@SuppressWarnings("unchecked")
	public static List<QuestionSet> searchQuestionSet(SearchQuestionSetParam param){
		List<QuestionSet> returnValue = new ArrayList<QuestionSet>();

		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(QuestionSet.class);

		String filter = "";

		try
		{
			pm.setDetachAllOnCommit(true);

			if(param.getName() != null)
			{
				filter += "name == nameParam";
			}

			if(filter != "")
				query.setFilter(filter);

			if(param.getResultsByPage() != 0 && param.getPageNumber() != 0)
			{
				query.setRange(param.getResultsByPage()*(param.getPageNumber()-1), 
						param.getResultsByPage()*param.getPageNumber());
			}

			query.declareParameters("String groupParam, String userParam");
			query.declareImports("import com.google.appengine.api.datastore.Key;");

			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("nameParam", param.getName());
			returnValue = (List<QuestionSet>) query.executeWithMap(parameters);

			returnValue.size();
		}
		catch(WebApplicationException e)
		{
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		finally{
			pm.close();
		}

		return returnValue;
	}

	@SuppressWarnings("unchecked")
	public static List<Template> searchTemplate(SearchQuestionSetParam param){
		List<Template> returnValue = new ArrayList<Template>();

		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(Template.class);

		String filter = "";

		try
		{
			pm.setDetachAllOnCommit(true);

			if(param.getName() != null)
			{
				filter += "name == :nameParam";
			}
			if (param.getSection() != null) {
				if (!filter.isEmpty())
					filter += " && ";
				filter += "sectionList.contains(:sectionParam)";
			}			

			if(filter != "")
				query.setFilter(filter);

			if(param.getResultsByPage() != 0 && param.getPageNumber() != 0)
			{
				query.setRange(param.getResultsByPage()*(param.getPageNumber()-1), 
						param.getResultsByPage()*param.getPageNumber());
			}

			query.declareImports("import com.google.appengine.api.datastore.Key;");

			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("nameParam", param.getName());
			parameters.put("sectionParam", param.getSection());
			returnValue = (List<Template>) query.executeWithMap(parameters);

			returnValue.size();
		}
		catch(WebApplicationException e)
		{
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		finally{
			pm.close();
		}

		return returnValue;
	}

	@SuppressWarnings("unchecked")
	public static List<TemplateSection> searchTemplateSection(SearchTemplateSectionParam param){
		List<TemplateSection> returnValue = new ArrayList<TemplateSection>();

		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(TemplateSection.class);

		String filter = "";

		try
		{
			pm.setDetachAllOnCommit(true);

			if(param.getCategory() != null)
			{
				filter += "category == :categoryParam";
			}
			if(param.getPoints() != 0)
			{
				if (!filter.isEmpty())
					filter += " && ";
				filter += "points == :pointsParam";
			}
			if(param.getNbQuestions() != 0)
			{
				if (!filter.isEmpty())
					filter += " && ";
				filter += "nbQuestions == :nbQuestionsParam";
			}
			if(param.getDifficulty() != null)
			{
				if (!filter.isEmpty())
					filter += " && ";
				filter += "difficulty == :difficultyParam";
			}
			if(param.getQuestionType() != null)
			{
				if (!filter.isEmpty())
					filter += " && ";
				filter += "questionType == :questionTypeParam";
			}

			if(filter != "")
				query.setFilter(filter);

			if(param.getResultsByPage() != 0 && param.getPageNumber() != 0)
			{
				query.setRange(param.getResultsByPage()*(param.getPageNumber()-1), 
						param.getResultsByPage()*param.getPageNumber());
			}

			query.declareImports("import com.google.appengine.api.datastore.Key;");

			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("categoryParam", param.getCategory());
			parameters.put("pointsParam", param.getPoints());
			parameters.put("nbQuestionsParam", param.getNbQuestions());
			parameters.put("difficultyParam", param.getDifficulty());
			parameters.put("questionTypeParam", param.getQuestionType());
			returnValue = (List<TemplateSection>) query.executeWithMap(parameters);

			returnValue.size();
		}
		catch(WebApplicationException e)
		{
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		finally{
			pm.close();
		}

		return returnValue;
	}

	@SuppressWarnings("unchecked")
	public static List<Category> searchCategory(SearchCategoryParam param){
		List<Category> returnValue = new ArrayList<Category>();

		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(Category.class);

		String filter = "";

		try
		{
			pm.setDetachAllOnCommit(true);

			if(param.getName() != null)
			{
				filter += "name == nameParam";
			}
			if(param.getType() != null)
			{
				if (!filter.isEmpty())
					filter += " && ";
				filter += "type == typeParam";
			}

			if(filter != "")
				query.setFilter(filter);

			if(param.getResultsByPage() != 0 && param.getPageNumber() != 0)
			{
				query.setRange(param.getResultsByPage()*(param.getPageNumber()-1), 
						param.getResultsByPage()*param.getPageNumber());
			}

			query.declareParameters("String nameParam, String typeParam");
			query.declareImports("import com.google.appengine.api.datastore.Key;");

			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("nameParam", param.getName());
			parameters.put("typeParam", param.getType());
			returnValue = (List<Category>) query.executeWithMap(parameters);

			returnValue.size();
		}
		catch(WebApplicationException e)
		{
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		finally{
			pm.close();
		}

		return returnValue;
	}

	@SuppressWarnings("unchecked")
	public static List<GameLog> searchGameLog(SearchGameLogParam param){
		List<GameLog> returnValue = new ArrayList<GameLog>();

		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(GameLog.class);

		String filter = "";

		try
		{
			pm.setDetachAllOnCommit(true);

			if(param.getGame() != null)
			{
				filter += "game == :gameParam";
			}
			if(param.getUser() != null)
			{
				if (!filter.isEmpty())
					filter += " && ";
				filter += "user == :userParam";
			}
			if(param.getQuestion() != null)
			{
				if (!filter.isEmpty())
					filter += " && ";
				filter += "question == :questionParam";
			}
			if(param.getResponseTime() != 0)
			{
				if (!filter.isEmpty())
					filter += " && ";
				filter += "responseTime == :responseTimeParam";
			}
			if(param.isCorrectAnswer() != -1)
			{
				if (!filter.isEmpty())
					filter += " && ";
				filter += "answer == :answerParam";
			}
			if(param.getPoints() != 0)
			{
				if (!filter.isEmpty())
					filter += " && ";
				filter += "points == :pointsParam";
			}
			if(param.getTeam() != null)
			{
				if (!filter.isEmpty())
					filter += " && ";
				filter += "team == :teamParam";
			}

			if(filter != "")
				query.setFilter(filter);

			if(param.getResultsByPage() != 0 && param.getPageNumber() != 0)
			{
				query.setRange(param.getResultsByPage()*(param.getPageNumber()-1), 
						param.getResultsByPage()*param.getPageNumber());
			}

			query.declareImports("import com.google.appengine.api.datastore.Key;");

			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("gameParam", param.getGame());
			parameters.put("userParam", param.getUser());
			parameters.put("questionParam", param.getQuestion());
			parameters.put("responseTimeParam", param.getResponseTime());
			parameters.put("answerParam", (param.isCorrectAnswer() == 1)?true:false);
			parameters.put("pointsParam", param.getPoints());
			parameters.put("teamParam", param.getTeam());
			returnValue = (List<GameLog>) query.executeWithMap(parameters);

			returnValue.size();
		}
		catch(WebApplicationException e)
		{
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
		}
		finally{
			pm.close();
		}

		return returnValue;
	}

	@SuppressWarnings("unchecked")
	public static List<Game> searchGames(SearchGameParam param)
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(Game.class);

		String filter = "";
		List<Game> list = null;

		try
		{
			pm.setDetachAllOnCommit(true);

			if(param.getTeam() != null)
			{
				if(!filter.isEmpty())
					filter += " && ";
				filter += "(teams.contains(:teamParam)";
			}
			if(param.getUser() != null)
			{
				if(!filter.isEmpty())
					filter += " && ";
				filter += "players.contains(:userParam)";
			}
			if(param.getTournament() != null)
			{
				if(!filter.isEmpty())
					filter += " && ";
				filter += "tournament == :tournamentParam";
			}
			if(param.getQuestionSet() != null)
			{
				if (!filter.isEmpty())
					filter += " && ";
				filter += "questionSet == :questionSetParam";
			}
			if(param.getName() != null)
			{
				if (!filter.isEmpty())
					filter += " && ";
				filter += "name == :nameParam";
			}

			if (!filter.isEmpty())
				filter += " && ";
			filter += "active == :activeParam";

			if(filter != "")
				query.setFilter(filter);

			if(param.getResultsByPage() != 0 && param.getPageNumber() != 0)
			{
				query.setRange(param.getResultsByPage()*(param.getPageNumber()-1), 
						param.getResultsByPage()*param.getPageNumber());
			}

			query.setOrdering("gameDate descending");
			query.declareImports("import com.google.appengine.api.datastore.Key;");

			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("teamParam", param.getTeam());
			parameters.put("tournamentParam", param.getTournament());
			parameters.put("questionSetParam", param.getQuestionSet());
			parameters.put("nameParam", param.getName());
			parameters.put("activeParam", param.isActive());
			parameters.put("userParam", param.getUser());
			list = (List<Game>) query.executeWithMap(parameters);

			list.size();
		}
		catch(WebApplicationException e)
		{
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		finally{
			pm.close();
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	public static List<Tournament> searchTournament(SearchTournamentParam param)
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(Tournament.class);

		String filter = "";
		List<Tournament> list = null;

		try
		{
			pm.setDetachAllOnCommit(true);

			if(param.getTeam() != null)
			{
				if(!filter.isEmpty())
					filter += " && ";
				filter += "teams.contains(:teamParam)";
			}
			if(param.getGame() != null)
			{
				if (!filter.isEmpty())
					filter += " && ";
				filter += "games.contains(:gameParam)";
			}
			if(param.getGroup() != null)
			{
				if (!filter.isEmpty())
					filter += " && ";
				filter += "group == :groupParam";
			}
			if(param.getName() != null)
			{
				if (!filter.isEmpty())
					filter += " && ";
				filter += "name == :nameParam";
			}

			if(filter != "")
				query.setFilter(filter);

			if(param.getResultsByPage() != 0 && param.getPageNumber() != 0)
			{
				query.setRange(param.getResultsByPage()*(param.getPageNumber()-1), 
						param.getResultsByPage()*param.getPageNumber());
			}

			query.declareImports("import com.google.appengine.api.datastore.Key;");

			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("teamParam", param.getTeam());
			parameters.put("gameParam", param.getGame());
			parameters.put("groupParam", param.getGroup());
			parameters.put("nameParam", param.getName());
			list = (List<Tournament>) query.executeWithMap(parameters);

			list.size();
		}
		catch(WebApplicationException e)
		{
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		finally{
			pm.close();
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	public static List<GameStat> searchStat(SearchGameStatParam param)
	{
		PersistenceManager pm = PMF.get().getPersistenceManager();
		Query query = pm.newQuery(GameStat.class);

		String filter = "";
		List<GameStat> list = null;

		try
		{
			pm.setDetachAllOnCommit(true);

			if(param.getStatsFor() != null)
			{
				if(!filter.isEmpty())
					filter += " && ";
				filter += "statsFor == :statsForParam";
			}

			if(param.getContext() != null && param.getContext().getKind().equals("Team")){
				if(!filter.isEmpty())
					filter += " && ";
				filter += "teams.contains(:contextParam)";
			}

			if(filter != "")
				query.setFilter(filter);

			if(param.getResultsByPage() != 0 && param.getPageNumber() != 0)
			{
				query.setRange(param.getResultsByPage()*(param.getPageNumber()-1), 
						param.getResultsByPage()*param.getPageNumber());
			}

			query.setOrdering("gameDate descending");
			query.declareImports("import com.google.appengine.api.datastore.Key;");

			HashMap<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("statsForParam", param.getStatsFor());
			parameters.put("contextParam", param.getContext());
			list = (List<GameStat>) query.executeWithMap(parameters);

			list.size();
		}
		catch(WebApplicationException e)
		{
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		finally{
			pm.close();
		}

		return list;
	}

	//****************************************************************************************************//
	//**										 GENERAL												**//
	//****************************************************************************************************//
	public static void save(BaseModel model) {

		if(model.isValid()){
			PersistenceManager pm = PMF.get().getPersistenceManager();

			try {
				pm.makePersistent(model);
			} finally {
				pm.close();
			}
		}
		else{
			ObjectMapper mapper = new ObjectMapper();

			try {
				ExceptionLogger.getLogger().warning(mapper.writeValueAsString(model));
			} catch (Exception e) {
				throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
			}
			throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);
		}
	}

	public static void delete(Key key) {

		PersistenceManager pm = PMF.get().getPersistenceManager();

		try {
			Object entity = pm.getObjectById(Class.forName(myPackage + key.getKind()), key);
			pm.deletePersistent(entity);
		} catch (Exception e) {
			System.out.println(e);
		} finally {
			pm.close();
		}
	}

	//*********************************************************************************************//
	//** TODO TO REPLACE
	//*********************************************************************************************//

	//	public static List<Question> getQuestionListForQuestionSetSection(
	//			QuestionSetSection qsSection) {
	//		List<Question> returnValue = new ArrayList<Question>();
	//		for (Key q : qsSection.getQuestionList()) {
	//			returnValue.add(DBManager.getQuestion(q));
	//		}
	//		return returnValue;
	//	}

	//	@SuppressWarnings("unchecked")
	//	public static List<Team> getTeamsForTournament(Tournament tournament) {
	//
	//		PersistenceManager pm = PMF.get().getPersistenceManager();
	//
	//		Query query = pm.newQuery(Team.class, ":p.contains(key)");
	//		query.declareImports("import com.google.appengine.api.datastore.Key;");
	//
	//		List<Team> returnValue = (List<Team>) (tournament.getTeams().size() != 0 ? query.execute(tournament.getTeams()) : emptyList);
	//		
	//		pm.close();
	//		
	//		return returnValue;
	//	}
	//
	//	@SuppressWarnings("unchecked")
	//	public static List<Game> getGamesForTournament(Tournament tournament) {
	//		PersistenceManager pm = PMF.get().getPersistenceManager();
	//
	//		Query query = pm.newQuery(Game.class, "tournament == :pTournament");
	//		query.declareImports("import com.google.appengine.api.datastore.Key;");
	//
	//		List<Game> returnValue = (List<Game>) (tournament.getGames().size() != 0 ? query.execute(tournament.getKey()) : emptyList);
	//		
	//		pm.close();
	//		
	//		return returnValue;
	//	}

	public static boolean validateEmail(String email) {

		SearchUserParam p = new SearchUserParam();
		p.setEmail(email);
		List<User> users = searchUsers(p);
		if(users == null){
			return true;
		}

		return users.size() != 0 ? false : true;
	}

}
