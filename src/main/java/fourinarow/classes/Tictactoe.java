package fourinarow.classes;

import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.converter.HttpMessageNotReadableException;

public class Tictactoe {

	public enum Players {
		NOBODY(0), PLAYER(1), IA(2); //Each state of a grid square
		private int value;
		
		/**
		 * enum constructor
		 * @param value : equivalent int value for the type
		 */
		Players(int value){
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
	private Players[] grid = new Players[9]; //Grid of Players
	private String message = "";
	
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
	public Tictactoe() {
		for (int i = 0; i < 9; i++) {
			this.grid[i]=Players.NOBODY;
		}
		setMessage("ok");
	}

	/**
	 * In-game constructor : it generates a grid from grid parameter
	 * @param grid : an array (length 9) with each square of the grid as a Players
	 */
	public Tictactoe(Players[] grid){
		for (int i = 0; i < 9; i++) {
			this.grid[i]=grid[i];
		}
		setMessage("ok");
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
				this.grid[i] = Players.values()[player];
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
	public Players[] getGrid() {
		return this.grid;
	}
	
	/**
	 * Square setter
	 * @param number : grid square to change player (position)
	 * @param value : the player who played
	 * @return
	 * 		true : everything run correctly
	 * 		false : the number is wrong
	 */
	public boolean setSquare(int number, Players value) {
		if(number>=9 || number<0) {
			return false;
		}else {
			this.grid[number] = value;
			return true;
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
	public Players win() {
		boolean isEnd = true;
		for (int i = 0; i < 8; i++) {
			if(getGrid()[i] == Players.NOBODY) {isEnd = false;}
			if(getGrid()[this.getWinConditionLine(i)[0]] == getGrid()[this.getWinConditionLine(i)[1]]
					&& getGrid()[this.getWinConditionLine(i)[0]] == getGrid()[this.getWinConditionLine(i)[2]]
					&& getGrid()[this.getWinConditionLine(i)[0]] != Players.NOBODY) 
						return getGrid()[getWinConditionLine(i)[0]];
		}
		if(getGrid()[8] == Players.NOBODY) {isEnd = false;}
		return isEnd?Players.NOBODY:null;
	}
	
	/**
	 * Grid to String converter
	 * @return a string with the state of each square in the grid
	 */
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		if(message.equals("ok")) {
			JSONArray grid = new JSONArray();
			for(int i=0; i<9; i++) {
				grid.put(this.grid[i].getValue());
			}
			json.put("grid", grid);
			Players player = win();
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
