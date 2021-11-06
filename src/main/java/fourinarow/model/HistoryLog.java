package fourinarow.model;

import java.io.Serializable;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.json.JSONObject;

@Entity
@Table(name="history")
public class HistoryLog implements Serializable {

	private static final long serialVersionUID = 1588751619657228086L;
	
	private Long logId;
	private Long userId;
	private String currentState;
	private Integer chosenMove;
	private boolean wonGame;
	private Integer movesBeforeEnd;
	private Timestamp createdAt;
	
	public HistoryLog() {}

	public HistoryLog(Long userId, String currentState, Integer chosenMove, boolean wonGame, Integer movesBeforeEnd) {
		super();
		this.userId = userId;
		this.currentState = currentState;
		this.chosenMove = chosenMove;
		this.wonGame = wonGame;
		this.movesBeforeEnd = movesBeforeEnd;
	}
	
	public HistoryLog(Long logId, Long userId, String currentState, Integer chosenMove, boolean wonGame,
			Integer movesBeforeEnd, Timestamp createdAt) {
		super();
		this.logId = logId;
		this.userId = userId;
		this.currentState = currentState;
		this.chosenMove = chosenMove;
		this.wonGame = wonGame;
		this.movesBeforeEnd = movesBeforeEnd;
		this.createdAt = createdAt;
	}

	@Id
	@Column(name="id", updatable=false)
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getLogId() {
		return logId;
	}
	
	public void setLogId(Long logId) {
		this.logId = logId;
	}

	@Column()
	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	@Column()
	public String getCurrentState() {
		return currentState;
	}

	public void setCurrentState(String currentState) {
		this.currentState = currentState;
	}

	@Column()
	public Integer getChosenMove() {
		return chosenMove;
	}

	public void setChosenMove(Integer chosenMove) {
		this.chosenMove = chosenMove;
	}

	@Column()
	public boolean getWonGame() {
		return wonGame;
	}

	public void setWonGame(boolean wonGame) {
		this.wonGame = wonGame;
	}

	@Column()
	public Integer getMovesBeforeEnd() {
		return movesBeforeEnd;
	}

	public void setMovesBeforeEnd(Integer movesBeforeEnd) {
		this.movesBeforeEnd = movesBeforeEnd;
	}
	
	@Column(columnDefinition="TIMESTAMP DEFAULT CURRENT_TIMESTAMP", insertable=false, updatable=false)
	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}
	
	public JSONObject toJSON() {
		JSONObject o = new JSONObject();
		o.put("id", this.logId);
		o.put("userId", userId);
		o.put("currentState", new JSONObject(currentState));
		o.put("chosenMove", chosenMove);
		o.put("wonGame", wonGame);
		o.put("movesBeforeEnd", movesBeforeEnd);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		o.put("createdAt", sdf.format(createdAt));
		return o;
	}

}
