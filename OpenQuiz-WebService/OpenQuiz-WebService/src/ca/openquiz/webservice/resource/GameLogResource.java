package ca.openquiz.webservice.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import ca.openquiz.webservice.manager.DBManager;
import ca.openquiz.webservice.model.Game;
import ca.openquiz.webservice.model.GameLog;
import ca.openquiz.webservice.parameter.SearchGameLogParam;
import ca.openquiz.webservice.response.BaseResponse;
import ca.openquiz.webservice.response.KeyResponse;

@Path("/gameLogs")
public class GameLogResource {

	/**
	 * GET rest/gameLogs
	 * Get the list of gameLogs events
	 * @param game Id of the game to get the logged events
	 * @param user User Id of the logged events to get
	 * @param question Question ID of the logged events to get
	 * @param answer Is the answer of the event is correct
	 * @param points Points given for the question
	 * @param team Team Id of the logged events to get
	 * @return List of game events
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<GameLog> getGameLogList(
			@QueryParam("game") String game,
			@QueryParam("user") String user,
			@QueryParam("question") String question,
			@QueryParam("answer") Boolean answer,
			@QueryParam("points") int points,
			@QueryParam("team") String team) {

		List<GameLog> returnValue = new ArrayList<GameLog>();
		SearchGameLogParam p = new SearchGameLogParam();

		if(game != null)
			p.setGame(KeyFactory.stringToKey(game));

		if(user != null)
			p.setUser(KeyFactory.stringToKey(user));

		if(question != null)
			p.setQuestion(KeyFactory.stringToKey(question));

		if(points != 0)
			p.setPoints(points);

		if(team != null)
			p.setTeam(KeyFactory.stringToKey(team));

		if(answer != null)
			p.setCorrectAnswer(answer?1:0);

		returnValue = DBManager.searchGameLog(p);

		return returnValue;
	}

	/**
	 * GET rest/gameLogs/{id}
	 * Get the game event by Id
	 * @param id Id of the logged event
	 * @return GameLog
	 */
	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public GameLog getGameLog(@PathParam("id") String id) {

		Key key = KeyFactory.stringToKey(id);
		GameLog gl = DBManager.getGameLog(key);

		return gl;

	}

	/**
	 * POST rest/gameLogs
	 * Add a new gameLog
	 * @param gameLog GameLog to add
	 * @return Key of the added GameLog
	 */
	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public KeyResponse addGameLog(GameLog gameLog)
	{
		KeyResponse resp = new KeyResponse();
		Game game = DBManager.getGame(gameLog.getGame());

		if(game != null && game.isActive()) {
			try {
				DBManager.save(gameLog);
				resp.setKey(KeyFactory.keyToString(gameLog.getKey()));
			} 
			catch(Exception e) {
				resp.setError(e.getMessage());
			}
		}

		return resp;
	}

	/**
	 * DELETE rest/gameLogs/{id}
	 * Removes a gameLog
	 * @param id Id of the GameLog to delete
	 * @return Completed or error
	 */
	@DELETE
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public BaseResponse deleteGameLog(@PathParam("id") String id) {
		BaseResponse response = new BaseResponse();

		try{
			Key key = KeyFactory.stringToKey(id);
			GameLog gl = DBManager.getGameLog(key);
			Game game = DBManager.getGame(gl.getGame());
			if(game != null && game.isActive()) {
				gl.delete();
			}
		}
		catch(Exception e)
		{
			response.setError("Error while deleting a gamelog : " + e.getMessage());
		}

		return response;
	}
}
