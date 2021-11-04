package fourinarow.classes;

import java.util.Arrays;

import org.json.JSONObject;

public class Tictactoe {

	public enum Players {NOBODY, PLAYER, IA}; //Each state of a grid square
	private Players[] grid = new Players[9]; //Grid of Players
	
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
	}

	/**
	 * In-game constructor : it generates a grid from grid parameter
	 * @param grid : an array (length 9) with each square of the grid as a Players
	 */
	public Tictactoe(Players[] grid){
		for (int i = 0; i < 9; i++) {
			this.grid[i]=grid[i];
		}
	}
	
	/**
	 * Grid setter
	 * @param number : grid square to change player (position)
	 * @param value : the player who played
	 * @return
	 * 		true : everything run correctly
	 * 		false : the number is wrong
	 */
	public boolean setGrid(int number, Players value) {
		if(number>9 || number<0) {
			return false;
		}else {
			this.grid[number] = value;
			return true;
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
	 * Grid to JSON converter
	 * @return a JSON object of the grid
	 */
	public JSONObject toJSON() {
		return new JSONObject(getGrid());
	}

	/**
	 * Grid to String converter
	 * @return a string with the state of each square in the grid
	 */
	@Override
	public String toString() {
		return "Tictactoe [grid=" + Arrays.toString(grid) + "]";
	}
	
	
}
