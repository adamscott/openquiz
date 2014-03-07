package ca.openquiz.webservice.resource;

import java.io.BufferedReader;


import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.channels.Channels;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import com.google.appengine.api.ThreadManager;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import ca.openquiz.webservice.enums.Bucket;
import ca.openquiz.webservice.enums.Language;
import ca.openquiz.webservice.enums.QuestionType;
import ca.openquiz.webservice.enums.Role;
import ca.openquiz.webservice.manager.DBManager;
import ca.openquiz.webservice.manager.FileManager;
import ca.openquiz.webservice.manager.security.RoleManager;
import ca.openquiz.webservice.model.Group;
import ca.openquiz.webservice.response.KeyResponse;
import ca.openquiz.webservice.response.ImportKeysResponse;
import ca.openquiz.webservice.tools.ExceptionLogger;

import com.google.appengine.tools.cloudstorage.GcsInputChannel;
import com.sun.jersey.multipart.FormDataBodyPart;
import com.sun.jersey.multipart.FormDataParam;

@Path("/import")
public class ImportResource {

	private static final int MAX_IMPORT_FOR_FRONTEND = 200;

	@GET
	@Path("/testLog")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public KeyResponse testLog() {
		ExceptionLogger.getLogger().severe("severe");
		ExceptionLogger.getLogger().warning("warning");
		ExceptionLogger.getLogger().info("info");
		return new KeyResponse();
	}

	@POST
	@Path("/importCsvFile")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.MULTIPART_FORM_DATA })
	public ImportKeysResponse importQuestions(
			@Context SecurityContext sc, 
			@FormDataParam("category") String category,
			@FormDataParam("questionType") QuestionType questionType,
			@FormDataParam("availableDate") long availableDate,
			@FormDataParam("language") Language language,
			@FormDataParam("group") List<FormDataBodyPart> groups,
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataBodyPart fileDetail) {
		
		List<Role> roleList = new ArrayList<Role>();
		roleList.add(Role.Manager);
		roleList.add(Role.QuestionManager);
		
		List<Group> groupsList = new ArrayList<Group>();
		List<Key> groupsKeyList = new ArrayList<Key>();
		if(groups != null && !groups.isEmpty()){
			try{

				for(FormDataBodyPart g : groups){
					Key key = KeyFactory.stringToKey(g.getValueAs(String.class));
					groupsKeyList.add(key);
					groupsList.add(DBManager.getGroup(key));
				}
			}catch(Exception e){
				throw new WebApplicationException(Response.Status.BAD_REQUEST);
			}
		}
		
		if(RoleManager.userRoleMatchAllGroups(sc, roleList, groupsKeyList, true)){
			
			Date date = new Date(availableDate);
			Date now = new Date();
			if(date.before(now)){
				date = now;
			}
			
			try{
				Key catKey = KeyFactory.stringToKey(category);
				if(DBManager.getCategory(catKey) == null){
					throw new WebApplicationException(Response.Status.BAD_REQUEST);
				}
				String mediaId = FileManager.addNewFile(uploadedInputStream, 
						fileDetail, Bucket.importQuestions);
				
				return importQuestionsFromGoogleCloud(mediaId, questionType, RoleManager.getCurrentUserKey(sc),
						catKey, language, groupsList, date, false);
				
			}catch(Exception e){
				throw new WebApplicationException(Response.Status.BAD_REQUEST);
			}
		}
		else{
			throw new WebApplicationException(Response.Status.FORBIDDEN);
		}
	}

	private ImportKeysResponse importQuestionsFromGoogleCloud(String file, final QuestionType questionType, final Key author, 
			final Key category, final Language language, final List<Group> groups, final Date availableDate, boolean forceSameThread){
		
		ImportKeysResponse response = new ImportKeysResponse();
		try {

			GcsInputChannel readChannel = FileManager.getFile(file, Bucket.importQuestions);
			String fileContent = getStringFromInputStream(Channels.newInputStream(readChannel));

			/*
			// Use this code to access a file without the google cloud storage API
			String testUrl = "https://storage.googleapis.com/openquiz-import/csv_files/" + file;
			URL oracle = new URL(testUrl);
	        BufferedReader in = new BufferedReader(
	        new InputStreamReader(oracle.openStream()));
			String inputLine;
			String fileContent = "";
	        while ((inputLine = in.readLine()) != null)
	        	fileContent += inputLine + "\n";

			*/


			//we skip the header line
			final String[] lines = fileContent.substring(fileContent.indexOf("\n")).split("\n");

			if(lines.length > MAX_IMPORT_FOR_FRONTEND && forceSameThread == false)
			{
				// Too much questions to import for the frontend to work, start 
				Thread thread = ThreadManager.createBackgroundThread(new Runnable() {
					public void run() {
						importQuestionsFromCsvLines(lines, questionType, author, category, language, availableDate, groups);
					}
				});
				try{
					thread.start();
				} catch(Exception e){
					response.setError("Cannot import more than " + MAX_IMPORT_FOR_FRONTEND + " on frontend. Use backend to import more questions.");
					ExceptionLogger.getLogger().severe(e.toString());
				}
			}else{
				response = importQuestionsFromCsvLines(lines, questionType, author, category, language, availableDate, groups);
			}
		} catch (IOException e) {
			response.setError(e.toString());
			ExceptionLogger.getLogger().severe(e.toString());
		}
		return response;
	}

	private ImportKeysResponse importQuestionsFromCsvLines(String[] lines, QuestionType questionType, Key author, Key category, Language language, Date availableDate, List<Group> groups){
		ImportKeysResponse response = new ImportKeysResponse();
		for(String line : lines){
			if(line.isEmpty())
				continue;
			KeyResponse importResponse = ca.openquiz.webservice.tools.ImportQuestions.ImportQuestion(line, questionType, author, category, language, availableDate, groups);

			if(importResponse != null && importResponse.getKey() != null){
				response.getKeys().add(importResponse.getKey() );
			}else{
				response.getLinesInError().add(line);
			}
		}
		return response;
	}


	// convert InputStream to String
	private static String getStringFromInputStream(InputStream is)
			throws IOException {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		String line;
		try {
			br = new BufferedReader(new InputStreamReader(is));
			while ((line = br.readLine()) != null) {
				sb.append(line + "\n");
			}

		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					ExceptionLogger.getLogger().severe(e.toString());
				}
			}
		}

		return sb.toString();

	}
}
