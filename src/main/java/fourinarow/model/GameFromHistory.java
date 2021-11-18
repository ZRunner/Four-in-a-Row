package fourinarow.model;

import java.io.Serializable;
import java.util.Date;
import java.text.SimpleDateFormat;

import org.json.JSONObject;

public class GameFromHistory implements Serializable {

	private static final long serialVersionUID = 8605098447567402595L;
	
	private Long userID;
	private Long gameId;
	private Date createdAt;
	private boolean userWon;
	
	
	public GameFromHistory() {};
	
	public GameFromHistory(Long userID, Long gameId, Date createdAt, boolean userWon) {
		super();
		this.userID = userID;
		this.gameId = gameId;
		this.createdAt = createdAt;
		this.userWon = userWon;
	}


	public Long getUserID() {
		return userID;
	}


	public void setUserID(Long userID) {
		this.userID = userID;
	}


	public Long getGameId() {
		return gameId;
	}


	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}


	public Date getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}


	public boolean hasUserWon() {
		return userWon;
	}


	public void setUserWon(boolean userWon) {
		this.userWon = userWon;
	}
	
	public JSONObject toJSON() {
		JSONObject o = new JSONObject();
		o.put("userId", userID);
		o.put("gameId", gameId);
		o.put("userWon", userWon);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		o.put("createdAt", sdf.format(createdAt));
		return o;
	}

}
