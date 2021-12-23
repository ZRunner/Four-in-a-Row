package fourinarow.classes;

import org.json.JSONArray;
import org.json.JSONObject;

import fourinarow.classes.Tictactoe.Player;

public class PuissanceN {
	
	/* Size exception */
	public class InvalidSizeException extends Exception {
		private static final long serialVersionUID = -6069371299567744116L;
		public InvalidSizeException() {
	        super("Invalid size (not between 3 and 7)");
	    }
	}

	private String message="";
	private int puissance;
	private int width;
	private int height;
	private Player winner=null;

	private Player[][] grid = null; /* grid[x][y], [0][0] is the bot left corner */
	
	
	/* Creation of a classic puissance4 game */
	public PuissanceN(){
		puissance = 4;
		width = 7;
		height = 6;
		initGrid(width, height);
	}
	
	public PuissanceN(int n) throws InvalidSizeException {
		if(n<3 || n>7) throw new InvalidSizeException();
		puissance = n;
		width = ((n-1) * 2) + 1;
		height = (n-1) * 2;
		initGrid(width, height);
	}
	
	public PuissanceN(int n, Player[][] grid) throws InvalidSizeException {
		if(n<3 || n>7 || grid.length != (((n-1) * 2) + 1) || grid[0].length != ((n-1) * 2)) throw new InvalidSizeException();
		puissance = n;
		width = grid.length;
		height = grid[0].length;
		this.grid = grid;
	}

	public int getPuissance() {
		return puissance;
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}
	
	public Player[][] getGrid() {
		return grid;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Player getWinner() {
		return winner;
	}

	public void setWinner(Player winner) {
		this.winner = winner;
	}
	
	private void initGrid(int width, int height) {
		Player[][] grid = new Player[width][height];
		for (int i =0;i<width;i++) {
			for (int j =0;j<height;j++) {
				grid[i][j] = Player.NOBODY;
			}
		}
		this.grid = grid;
	}

	public void playAI() {
		int x=0;
		for(x=0;x<getWidth() && getGrid()[x][getHeight()-1]!=Player.NOBODY;x++);
		play(x, Player.IA);
	}
	
	/**
	 * Add a piece in the puissanceN and set message "ok" if everything is ok
	 * @param x : column where the piece have to be added
	 * @param player : the player who plays
	 */
	public void play(int x,Player player) {
		int i;
		try {
			if(x<0 || x>=getWidth()) throw new Exception("The index must be between 0 and "+(getWidth()-1));
			if(getGrid()[x][getHeight()-1]!=Player.NOBODY) throw new Exception("The column is full, you can't play there");
			for(i=0; i<getHeight() && getGrid()[x][i]!=Player.NOBODY ; i++);
			this.grid[x][i] = player;
			setMessage("ok");
			setWinner(win());
		}catch(Exception e) {
			setMessage(e.getMessage());
		}
	}
	
	private Player winCheckRight(int column, int line, Player currentPlayer) {
		if(column<=getWidth()-puissance) {
			for(int k=1;k<puissance;k++) {
				if(currentPlayer!=getGrid()[column+k][line]){
					return null;
				}
			}
			return getGrid()[column][line];
		}else return null;
	}
	
	private Player winCheckDown(int column, int line, Player currentPlayer) {
		if(line>puissance-2) {
			for(int k=1;k<puissance;k++) {
				if(currentPlayer!=getGrid()[column][line-k]){
					return null;
				}
			}
			return getGrid()[column][line];
		}else return null;
	}
	

	private Player winCheckUpRight(int column, int line, Player currentPlayer) {
		if(line<=getHeight()-puissance && column<=getWidth()-puissance) {
			for(int k=1;k<puissance;k++) {
				if(currentPlayer!=getGrid()[column+k][line+k]){
					return null;
				}
			}
			return getGrid()[column][line];
		}else return null;
	}
	
	private Player winCheckDownRight(int column, int line, Player currentPlayer) {
		if(line>puissance-2 && column<=getWidth()-puissance) {
			for(int k=1;k<puissance;k++) {
				if(currentPlayer!=getGrid()[column+k][line-k]){
					return null;
				}
			}
			return getGrid()[column][line];
		}else return null;
	}
	
	public Player win() {
		Player currentPlayer;
		Player res;
		boolean isFull=true;
		/* Checking 4 condition : up, right, diagonal up-right, diagonal down-right */
		for(int line = 0;line<getHeight();line++) {
			for(int column = 0; column< getWidth();column++) {
				currentPlayer = getGrid()[column][line];
				if(currentPlayer==Player.NOBODY) {
					isFull=false;
				}else {
					//check horizontal right
					res = winCheckRight(column,line,currentPlayer);
					if(res!=null) return res;
					//check vertical down
					res = winCheckDown(column,line,currentPlayer);
					if(res!=null) return res;
					//check diagonal up-right
					res = winCheckUpRight(column,line,currentPlayer);
					if(res!=null) return res;
					//check diagonal down-right
					res = winCheckDownRight(column,line,currentPlayer);
					if(res!=null) return res;
				}
			}
		}
		return isFull?Player.NOBODY:null;
	}
	
	public void printGrid() {
		for (int y = height-1; y >= 0;y--) {
			System.out.print("|");
			for (int x =0; x < getWidth(); x++) {
				System.out.print(grid[x][y].getValue()+"|");
			}
			System.out.print("\n");
		}
	}
	
	public JSONArray getGridAsArray() {
		JSONArray json = new JSONArray();
		JSONArray column;
		for(int i=0;i<getWidth();i++) {
			column = new JSONArray();
			for(int j=getHeight()-1; j>=0 ; j--){
				column.put(getGrid()[i][j].getValue());
			}
			json.put(column);
		}
		return json;
	}
	
	/**
	 * Convert the grid into a JSON object :
	 * n -> puissance 
	 * grid[] -> Column 
	 * grid[][] -> Element from top to bot of the column
	 * winner -> null if not ended, value between 0 and 2 if ended
	 */
	public JSONObject toJSON() {
		JSONObject json = new JSONObject();
		json.put("winner", getWinner()==null?null:getWinner().getValue());
		json.put("n",getPuissance());
		json.put("grid",getGridAsArray());
		return json;
	}
	
	/**
	 * Grid to String converter
	 * @return a JSON stringify with the state of each square in the grid
	 */
	@Override
	public String toString() {
		return toJSON().toString();
	}
}