package fourinarow.classes;

import fourinarow.classes.Tictactoe.Player;

public class PuissanceN {
	
	/* Size exception */
	public class InvalidSizeException extends Exception { 
	    public InvalidSizeException() {
	        super("Invalid size (not between 3 and 7)");
	    }
	}

	
	private int puissance;
	private int width;
	private int height;
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
	
	private void initGrid(int width, int height) {
		Player[][] grid = new Player[width][height];
		for (int i =0;i<width;i++) {
			for (int j =0;j<height;j++) {
				grid[i][j] = Player.NOBODY;
			}
		}
		this.grid = grid;
	}
	
	public void printGrid() {
		for (int y = height-1; y >= 0;y--) {
			System.out.print("|");
			for (int x =width-1; x >= 0; x--) {
				System.out.print(grid[x][y].getValue()+"|");
			}
			System.out.print("\n");
		}
	}
	
}
