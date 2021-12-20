package fourinarow.classes;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import fourinarow.model.GameType;
import fourinarow.model.HistoryLog;
import fourinarow.model.User;
import fourinarow.services.HistoryLogRepository;

public class Tictactoe {

	public enum Player {
		NOBODY(0), PLAYER(1), IA(2); //Each state of a grid square
		private int value;
		
		/**
		 * enum constructor
		 * @param value : equivalent int value for the type
		 */
		Player(int value){
			this.value = value;
		}
		
		/**
		 * value getter
		 * @return value : equivalent int value for the type
		 */
		public int getValue(){
			return value;
		}
	}; 
	
	private HistoryLogRepository logsRepository;
	
	private User user;
	private Player[] grid = new Player[9]; //Grid of Players
	private String message = "";
	private Player winner = null;
	private Long gameId = null;
	private List<HistoryLog> history = new ArrayList<>();
	private int AI_moves = 0; private int Player_moves = 0;
	
	private int[][] winCondition = {
			{0,1,2},
			{3,4,5},
			{6,7,8},
			{0,4,8},
			{2,4,6},
			{0,3,6},
			{1,4,7},
			{2,5,8}
			};
	
	/**
	 * Initial constructor : it generates a grid with NOBODY everywhere
	 */
	public Tictactoe(User user, HistoryLogRepository logRepo) {
		this.user = user;
		this.logsRepository = logRepo;
		for (int i = 0; i < 9; i++) {
			this.grid[i] = Player.NOBODY;
		}
		setMessage("ok");
	}

	/**
	 * In-game constructor : it generates a grid from grid parameter
	 * @param grid : an array (length 9) with each square of the grid as a Players
	 */
	public Tictactoe(User user, HistoryLogRepository logRepo, Player[] grid){
		this.user = user;
		this.logsRepository = logRepo;
		for (int i = 0; i < 9; i++) {
			this.grid[i] = grid[i];
		}
		setMessage("ok");
	}	
	
	/**
	 * winner getter
	 * @return winner of the game
	 */
	public Player getWinner() {
		return winner;
	}

	/**
	 * winner setter
	 * @param player : player to set
	 */
	public void setWinner(Player winner) {
		this.winner = winner;
	}
	
	/**
	 * message getter
	 * @return message of this class
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * message setter
	 * @param message : message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	
	public void setGrid(int[] jsonGrid) {
		try {
			if(jsonGrid.length!=9) {throw new Exception("The array's length must be 9");}
			for(int i=0 ; i<9; i++){
				int player = jsonGrid[i];
				if(player >2 || player<0) {throw new Exception("Array's elements must be between 0 and 2");}
				this.grid[i] = Player.values()[player];
			}
			setMessage("ok");
		}catch(Exception e) {
			setMessage(e.getMessage());
		}
	}

	/**
	 * Grid getter
	 * @return the current grid
	 */
	public Player[] getGrid() {
		return this.grid;
	}
	
	/**
	 * Square setter
	 * @param number : grid square to change player (position)
	 * @param value : the player who played
	 */
	public void setSquare(int number, Player value) {
		try {
			if(number>=9 || number<0) throw new Exception("The index must be between 0 and 8");
			if(getGrid()[number] == Player.PLAYER) throw new Exception("You can't play there again");
			if(getGrid()[number] == Player.IA) throw new Exception("You can't choose your adversary square");
			// update grid
			this.grid[number] = value;
			setMessage("ok");
			setWinner(win());
			// update logs
			if (value == Player.PLAYER) {
				Player_moves++;
			} else if (value == Player.IA) {
				AI_moves++;
			}
			writeLog(number, value);
		}catch(Exception e) {
			setMessage(e.getMessage());
		}
	}

	/**
	 * Array of winning condition getter
	 * @param i : the line of winning configuration (between 0 and 7)
	 * @return the 3 winning positions 
	 */
	public int[] getWinConditionLine(int i) {
		return winCondition[i];
	}

	
	/**
	 * Winning checking
	 * @return 
	 * 		player : the player who won
	 * 		null : the game isn't ended
	 */
	public Player win() {
		boolean isEnd = true;
		for (int i = 0; i < 8; i++) {
			if(getGrid()[i] == Player.NOBODY) {isEnd = false;}
			if(getGrid()[this.getWinConditionLine(i)[0]] == getGrid()[this.getWinConditionLine(i)[1]]
					&& getGrid()[this.getWinConditionLine(i)[0]] == getGrid()[this.getWinConditionLine(i)[2]]
					&& getGrid()[this.getWinConditionLine(i)[0]] != Player.NOBODY) 
						return getGrid()[getWinConditionLine(i)[0]];
		}
		if(getGrid()[8] == Player.NOBODY) {isEnd = false;}
		return isEnd?Player.NOBODY:null;
	}
	
	private void writeLog(int chosenMove, Player player) {
		// if no one played for whatever reason, just don't log
		if (player == Player.NOBODY) return;
		// get the current grid
		JSONArray grid = getGridAsArray();
		// reverse the last move
		grid.put(chosenMove, Player.NOBODY.value);
		String state = grid.toString();
		// check if player won
		boolean hasWon = getWinner() == player;
		// get the moves count "until now"
		int moves = player == Player.PLAYER ? Player_moves : AI_moves;
		// check if the move was from player or AI
		boolean from_ai = player == Player.IA;
		// pack them all
		HistoryLog log = new HistoryLog(user.getIdUser(), GameType.TTT, from_ai, state, chosenMove, hasWon, moves);
		this.history.add(log);
	}
	
	public void saveLogs() {
		if (gameId == null) {
			Long maxId = logsRepository.getMaxGameId();
			gameId = maxId!=null ? maxId+1 : 0;
		}
		for (HistoryLog log: this.history) {
			if (log.isFromAi()) {
				log.setMovesBeforeEnd(AI_moves - log.getMovesBeforeEnd());
			} else {
				log.setMovesBeforeEnd(Player_moves - log.getMovesBeforeEnd());
			}
			log.setGameId(gameId);
		}
		logsRepository.save(this.history);
		this.history.clear();
	}
	
	private JSONArray getGridAsArray() {
		JSONArray grid = new JSONArray();
		for(Player p: this.grid) {
			grid.put(p.getValue());
		}
		return grid;
	}
	
	/**
	 * Grid to String converter
	 * @return a string with the state of each square in the grid
	 */
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		if(message.equals("ok")) {
			JSONArray grid = this.getGridAsArray();
			json.put("grid", grid);
			Player player = win();
			json.put("winner", player==null?null:player.getValue());
		}
		return json;
	}
	
	/**
	 * Grid to String converter
	 * @return a string with the state of each square in the grid
	 */
	@Override
	public String toString() {
		return toJSON().toString();
	}
	
}
