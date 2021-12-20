package fourinarow.model;

import java.io.Serializable;

import org.json.JSONObject;

public class TttDecisionLog implements Serializable {

	private static final long serialVersionUID = 6919347217975558910L;
	
	private Long logId;
	private Long gameId;
	private String currentState;
	private int chosenMove;
	private boolean wonGame;
	private int movesBeforeEnd;
	private boolean ledToWin;
	
	public TttDecisionLog() {}
	
	public TttDecisionLog(Long logId, Long gameId, String currentState, int chosenMove, boolean wonGame,
			int movesBeforeEnd, boolean ledToWin) {
		super();
		this.logId = logId;
		this.gameId = gameId;
		this.currentState = currentState;
		this.chosenMove = chosenMove;
		this.wonGame = wonGame;
		this.movesBeforeEnd = movesBeforeEnd;
		this.ledToWin = ledToWin;
	}

	public Long getLogId() {
		return logId;
	}

	public Long getGameID() {
		return gameId;
	}

	public String getCurrentState() {
		return currentState;
	}

	public int getChosenMove() {
		return chosenMove;
	}

	public boolean hasWonGame() {
		return wonGame;
	}

	public int getMovesBeforeEnd() {
		return movesBeforeEnd;
	}

	public boolean hasLedToWin() {
		return ledToWin;
	}
	
	public JSONObject toJSON() {
		JSONObject o = new JSONObject();
		o.put("logId", logId);
		o.put("gameId", gameId);
		o.put("currentState", currentState);
		o.put("chosenMove", chosenMove);
		o.put("wonGame", wonGame);
		o.put("movesBeforeEnd", movesBeforeEnd);
		o.put("ledToWin", ledToWin);
		return o;
	}
	
	public String toString() {
		return toJSON().toString();
	}
		
}
