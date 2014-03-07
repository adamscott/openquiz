package ca.openquiz.webservice.resource;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import ca.openquiz.webservice.enums.Role;
import ca.openquiz.webservice.manager.DBManager;
import ca.openquiz.webservice.manager.security.RoleManager;
import ca.openquiz.webservice.model.Question;
import ca.openquiz.webservice.model.QuestionSet;
import ca.openquiz.webservice.model.QuestionSetSection;
import ca.openquiz.webservice.model.Template;
import ca.openquiz.webservice.model.User;
import ca.openquiz.webservice.parameter.CreateQuestionSetParam;
import ca.openquiz.webservice.parameter.CreateQuestionSetSectionParam;
import ca.openquiz.webservice.parameter.SearchQuestionSetParam;
import ca.openquiz.webservice.response.KeyResponse;
import ca.openquiz.webservice.response.QuestionSetListResponse;

@Path("/questionSets")
public class QuestionSetResource {

	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public QuestionSetListResponse getQuestionSetList() {
		QuestionSetListResponse returnValue = new QuestionSetListResponse();
		SearchQuestionSetParam p = new SearchQuestionSetParam();

		returnValue.setQuestionSets(DBManager.searchQuestionSet(p));

		return returnValue;
	}

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public QuestionSet getQuestionSet(@PathParam("id") String id) {

		QuestionSet qs = null;

		try{
			Key key = KeyFactory.stringToKey(id);
			qs = DBManager.getQuestionSet(key);
		}
		catch(Exception e) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}

		Date now = new Date();
		if(qs.getAvailableDate().compareTo(now) > 0) {
			throw new WebApplicationException(Response.Status.FORBIDDEN);
		}
		return qs;

	}

	@POST
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public KeyResponse addQuestionSet(@Context SecurityContext sc, CreateQuestionSetParam questionSet)
	{	
		if(!questionSet.isValid()){
			throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);
		}

		KeyResponse returnValue = new KeyResponse();

		//V?????????rification des droits pour ajouter un questionnaire
		List<Role> roleList = new ArrayList<Role>();
		roleList.add(Role.QuestionManager);
		roleList.add(Role.Manager);
		if(RoleManager.userRoleMatchGroup(sc, roleList, questionSet.getGroups(), true)) {
			User user = RoleManager.getCurrentUser(sc);

			QuestionSet qs = new QuestionSet();

			qs.setAuthor(user.getKey());

			qs.setGroups(questionSet.getGroups());

			qs.setName(questionSet.getName());

			if(questionSet.getAvailableDate() != null)
				qs.setAvailableDate(questionSet.getAvailableDate());
			else{
				Date now = new Date();
				qs.setAvailableDate(now);
			}

			List<QuestionSetSection> sList = new ArrayList<QuestionSetSection>();
			for(CreateQuestionSetSectionParam section : questionSet.getSectionList()) {
				QuestionSetSection s = new QuestionSetSection();
				s.setDescription(section.getDescription());
				s.setPoints(section.getPoints());
				for(Key k : section.getQuestionList()){
					Question q =  DBManager.getQuestion(k);
					if(q == null)
						throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);

					s.addQuestion(q);
				}
				sList.add(s);
			}

			for(QuestionSetSection s : sList) {
				DBManager.save(s);
				qs.getSections().add(s);
			}
			DBManager.save(qs);


			returnValue.setKey(KeyFactory.keyToString(qs.getKey()));
		}

		return returnValue;
	}

	@POST
	@Path("generate")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public QuestionSet generateQuestionSet(@Context SecurityContext sc, 
			Template template, 
			@QueryParam("save") boolean save,
			@DefaultValue("true") @QueryParam("includePublic") boolean includePublic){

		QuestionSet returnValue = new QuestionSet();

		User author = RoleManager.getCurrentUser(sc);
		
		if(author != null){

			// validate if questionset has sections
			if(template != null && template.getSectionList() != null && !template.getSectionList().isEmpty()){

				//Add author
				returnValue.setAuthor(author.getKey());

				//Set available date now
				returnValue.setAvailableDate(new Date());

				//Add name
				if(template.getName() != null && !template.getName().isEmpty()){
					returnValue.setName(template.getName());
				}
				else{
					returnValue.setName(UUID.randomUUID().toString());
				}

				returnValue.setSections(
						DBManager.createQuestionSetSections(template.getSectionList(), 
						RoleManager.getUserGroups(sc), includePublic));
			}

			returnValue.setDeletedAfterGame(!save);

			DBManager.save(returnValue);
		}
		else{
			throw new WebApplicationException(Response.Status.FORBIDDEN);
		}

		return returnValue;
	}

	@PUT
	@Path("{id}")
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public KeyResponse editQuestionSet(@Context SecurityContext sc, @PathParam("id") String id, 
			CreateQuestionSetParam questionSet)
	{	
		if(!questionSet.isValid()){
			throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);
		}

		KeyResponse returnValue = new KeyResponse();

		//V?????????rification des droits pour modifier un questionnaire
		List<Role> roleList = new ArrayList<Role>();
		roleList.add(Role.QuestionManager);
		roleList.add(Role.Manager);
		if(RoleManager.userRoleMatchGroup(sc, roleList, questionSet.getGroups(), true)) {
			QuestionSet qs = DBManager.getQuestionSet(KeyFactory.stringToKey(id));

			if(RoleManager.userRoleMatchGroup(sc, roleList, qs.getGroups(), true)) {
				User user = RoleManager.getCurrentUser(sc);

				qs.setAuthor(user.getKey());

				qs.setGroups(questionSet.getGroups());

				qs.setName(questionSet.getName());

				if(questionSet.getAvailableDate() != null)
					qs.setAvailableDate(questionSet.getAvailableDate());
				else{
					Date now = new Date();
					qs.setAvailableDate(now);
				}

				List<QuestionSetSection> sList = new ArrayList<QuestionSetSection>();
				qs.getSections().clear();
				for(CreateQuestionSetSectionParam section : questionSet.getSectionList()) {
					QuestionSetSection s = new QuestionSetSection();
					s.setDescription(section.getDescription());
					s.setPoints(section.getPoints());
					for(Key k : section.getQuestionList()){
						Question q =  DBManager.getQuestion(k);
						if(q == null)
							throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);

						s.addQuestion(q);
					}
					sList.add(s);
				}

				for(QuestionSetSection s : sList) {
					DBManager.save(s);
					qs.getSections().add(s);
				}
				DBManager.save(qs);
				returnValue.setKey(KeyFactory.keyToString(qs.getKey()));
			}			
		}
		return returnValue;
	}

	@DELETE
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public void removeQuestionSet(@Context SecurityContext sc, @PathParam("id") String id) {
		QuestionSet qs = null;
		try{
			Key key = KeyFactory.stringToKey(id);
			qs = DBManager.getQuestionSet(key);
		} catch(Exception e) {
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}
		List<Role> roleList = new ArrayList<Role>();
		roleList.add(Role.QuestionManager);
		roleList.add(Role.Manager);
		if(RoleManager.userRoleMatchGroup(sc, roleList, qs.getGroups(), true)) {
			try {
				List<Key> keys = qs.getGroups();
				for(Key k : RoleManager.getUserGroups(sc, roleList)){
					if(keys.contains(k)){
						keys.remove(k);
					}
				}
				qs.setGroups(keys);

				if(qs.getGroups().size() <= 1){
					qs.delete();
				}
				else{
					DBManager.save(qs);
				}
			}
			catch(Exception e)
			{
				throw new WebApplicationException(Response.Status.INTERNAL_SERVER_ERROR);
			}
		}

		throw new WebApplicationException(Response.Status.OK);
	}
}
