package ca.openquiz.comms.response;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonRootName;

import ca.openquiz.comms.model.Game;

@JsonRootName("games")
public class GameListResponse extends BaseResponse{

	@JsonProperty("game")
	List<Game> games;
	
	public List<Game> getGames() {
		return games;
	}

}
