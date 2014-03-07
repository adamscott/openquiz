package ca.openquiz.webservice.resource;

import java.util.ArrayList;
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

import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;

import ca.openquiz.webservice.parameter.SearchGameLogParam;
import ca.openquiz.webservice.parameter.SearchGameParam;
import ca.openquiz.webservice.response.BaseResponse;
import ca.openquiz.webservice.response.KeyResponse;
import ca.openquiz.webservice.enums.Role;
import ca.openquiz.webservice.enums.TeamPositionEnum;
import ca.openquiz.webservice.manager.DBManager;
import ca.openquiz.webservice.manager.security.RoleManager;
import ca.openquiz.webservice.model.Game;
import ca.openquiz.webservice.model.GameLog;
import ca.openquiz.webservice.model.GameStat;
import ca.openquiz.webservice.model.QuestionSet;
import ca.openquiz.webservice.model.User;

@Path("/games")
public class GameResource {

	/**
	 * GET rest/games
	 * Get a list of games by criterias
	 * @param name Name of the game to get
	 * @param user User id of a player competing in the game
	 * @param team Team id of a team competing in the game
	 * @param questionSet QuestionSet id used in the game
	 * @param active Is the game still active (not completed) default value = true
	 * @param page Number of the page to show
	 * @param max  Number of elements to be returned for each page
	 * @return List of games
	 */
	@GET
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public List<Game> getGameList(
			@QueryParam("name") String name,
			@QueryParam("user") String user,
			@QueryParam("team") String team,
			@QueryParam("questionSet") String questionSet,
			@DefaultValue("true")@QueryParam("active") boolean active,
			@QueryParam("page") int pageNumber, 
			@QueryParam("max") int resultsByPage) {

		SearchGameParam searchParam = new SearchGameParam();

		if(name != null && !name.isEmpty())
			searchParam.setName(name);
		if(user != null && !user.isEmpty())
			searchParam.setUser(KeyFactory.stringToKey(user));
		if(team != null && !team.isEmpty())
			searchParam.setTeam(KeyFactory.stringToKey(team));
		if(pageNumber > 0)
			searchParam.setPageNumber(pageNumber);
		if(resultsByPage > 0)
			searchParam.setResultsByPage(resultsByPage);
		searchParam.setActive(active);

		if(questionSet != null && !questionSet.isEmpty())
			searchParam.setQuestionSet(KeyFactory.stringToKey(questionSet));

		return DBManager.searchGames(searchParam);
	}

	@GET
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Game getGameById(@PathParam("id") String id) 
	{
		Key key = KeyFactory.stringToKey(id);
		Game game = null;

		try {
			game = DBManager.getGame(key);
		} catch(Exception e) {
			e.printStackTrace();
		}

		return game;
	}

	@POST
	@Path("{id}/close")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public Response closeGame(@Context SecurityContext sc, @PathParam("id") String id)
	{
		Key gameKey = null;
		try{
			gameKey = KeyFactory.stringToKey(id);
		}
		catch(Exception e){
			throw new WebApplicationException(Response.Status.BAD_REQUEST);
		}

		if(gameKey != null){
			Game game = DBManager.getGame(gameKey);

			if(game != null && game.isActive()){
				QuestionSet qset = DBManager.getQuestionSet(game.getQuestionSet());

				//Calculate final score
				SearchGameLogParam logParam = new SearchGameLogParam();
				logParam.setGame(game.getKey());
				logParam.setPageNumber(0);
				logParam.setPageNumber(0);
				List<GameLog> gameLogs = DBManager.searchGameLog(logParam);
				
				game.setActive(false);
				DBManager.save(game);

				if(gameLogs != null && !gameLogs.isEmpty()){
					for(GameLog log : gameLogs){
						if(log.isAnswer()){
							if(log.getTeamPosition() == TeamPositionEnum.Team1){
								game.setTeam1Score(game.getTeam1Score() + log.getPoints());
							}
							else if(log.getTeamPosition() == TeamPositionEnum.Team2){
								game.setTeam2Score(game.getTeam2Score() + log.getPoints());
							}
						}
					}
					
					DBManager.save(game);
					
					//Calculate stats
					User u = RoleManager.getCurrentUser(sc);
					if(u != null) {
						int possiblePoints = qset.getPossiblePoints();
						int possibleAttempts = qset.getPossibleAttempts();


						DBManager.save(new GameStat(gameKey, game.getTeam1(), possiblePoints, possibleAttempts, u));
						DBManager.save(new GameStat(gameKey, game.getTeam2(), possiblePoints, possibleAttempts, u));

						for(Key player : game.getTeam1Players()){
							DBManager.save(new GameStat(gameKey, player, possiblePoints, possibleAttempts, u));
						}

						for(Key player : game.getTeam2Players()){
							DBManager.save(new GameStat(gameKey, player, possiblePoints, possibleAttempts, u));
						}
					}
				}

				// Delete QuestionSet
				if(qset.isDeletedAfterGame()){
					SearchGameParam param = new SearchGameParam();
					param.setQuestionSet(qset.getKey());
					List<Game> games = DBManager.searchGames(param);

					if(games != null){
						for(Game g : games){
							if(!g.isActive()){
								games.remove(g);
							}
						}
						if(games.isEmpty()){
							qset.delete();
						}
					}
					game.setQuestionSet(null);
					DBManager.save(game);
				}
				
				return Response.ok().build();
			}
		}
		else{
			throw new WebApplicationException(Response.Status.NOT_FOUND);
		}

		return Response.notModified().build();
	}

	@POST
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public KeyResponse createGame(Game game)
	{
		KeyResponse resp = new KeyResponse();

		if(game.isValid()){
			try {
				DBManager.save(game);
				resp.setKey(KeyFactory.keyToString(game.getKey()));
			} 
			catch(Exception e) {
				e.printStackTrace();
				resp.setError(e.getMessage());
			}
		}
		else{
			throw new WebApplicationException(Response.Status.NOT_ACCEPTABLE);
		}

		return resp;
	}

	@PUT
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	@Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public BaseResponse editGame(@PathParam("id") String id, Game game) {

		BaseResponse resp = new BaseResponse();
		Key key = KeyFactory.stringToKey(id);
		try {
			Game tmpGame = DBManager.getGame(key);
			if(tmpGame.isActive())
				game.setActive(true);

			tmpGame.copy(game);

			DBManager.save(tmpGame);

			return resp;
		}
		catch(Exception e) {
			resp.setError(e.getMessage());
			return resp;
		}
	}

	@DELETE
	@Path("{id}")
	@Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
	public BaseResponse removeGame(@Context SecurityContext sc, @PathParam("id") String id) {
		BaseResponse resp = new BaseResponse();
		List<Role> roleList = new ArrayList<Role>();
		roleList.add(Role.Manager);
		roleList.add(Role.QuestionManager);
		if(RoleManager.isUserAdmin(sc) || RoleManager.isUserInRole(sc, roleList)){

			try{
				Key key = KeyFactory.stringToKey(id);
				Game game = DBManager.getGame(key);
				game.delete();
				return resp;
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

}
