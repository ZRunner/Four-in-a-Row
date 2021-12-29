import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fourinarow.classes.Player;
import fourinarow.classes.PuissanceN;
import fourinarow.classes.PuissanceN.InvalidSizeException;
import fourinarow.model.User;
import fourinarow.services.HistoryLogRepository;

@Service
class TestPuissanceN {
	
	public static User dummyUser = new User((long) 1);
	
	@Autowired
	private HistoryLogRepository logsRepo;
	
	@Test
	void creation11() {
		assertThrows(InvalidSizeException.class, () -> {
	        new PuissanceN(11, dummyUser, logsRepo);
	    });
	}
	
	@Test
	void creation0() {
		assertThrows(InvalidSizeException.class, () -> {
	        new PuissanceN(2, dummyUser, logsRepo);
	    });
	}
	
	@Test
	void creation4() throws InvalidSizeException {
		PuissanceN game = new PuissanceN(4, dummyUser, logsRepo);
		assertEquals(7,game.getWidth());
		assertEquals(6,game.getHeight());
		assertEquals(7,game.getGrid().length);
		assertEquals(6,game.getGrid()[0].length);
	}
	
	@Test 
	void playNegativeX() throws InvalidSizeException{
		PuissanceN game = new PuissanceN(4, dummyUser, logsRepo);
		game.play(-1,Player.PLAYER);
		assertEquals("The index must be between 0 and "+(game.getWidth()-1),game.getMessage());
	}
	
	@Test 
	void playOutLimitX() throws InvalidSizeException{
		PuissanceN game = new PuissanceN(4, dummyUser, logsRepo);
		game.play(7,Player.PLAYER);
		assertEquals("The index must be between 0 and "+(game.getWidth()-1),game.getMessage());
	}
	
	@Test 
	void playFullColumn() throws InvalidSizeException{
		PuissanceN game = new PuissanceN(4, dummyUser, logsRepo);
		for(int i=0;i<=game.getHeight();i++) {
			game.play(0,Player.PLAYER);
		}
		assertEquals("The column is full, you can't play there",game.getMessage());
	}
	
	@Test
	void playEverywhere() throws InvalidSizeException{
		PuissanceN game = new PuissanceN(4, dummyUser, logsRepo);
		Player player;
		int compt = 0;
		for (int i = 0; i < 7;i++) {
			compt = 0;
			if(i%2==0) {player = Player.PLAYER;}else{player = Player.IA;}
			for (int j = 0;j<game.getHeight();j++) {
				game.play(i,player);
				compt++;
				if(compt == 2) {
					compt = 0;
					player = (player==Player.PLAYER)?Player.IA:Player.PLAYER;
				}
				assertEquals("ok",game.getMessage());
			}
		}
	}
	
	@Test 
	void EveryHorizontalWinCondition() throws InvalidSizeException {//PuissanceN with n=4
		Player[][] grid;
		PuissanceN game;
		int n = 4;
		PuissanceN size = new PuissanceN(n, dummyUser, logsRepo);
		for(int j = 0; j < size.getHeight();j++) {//Each line
			for(int i = 0; i < size.getWidth()-n+1;i++) {//each case of line (except 3 lasts)
				grid = (new PuissanceN(n, dummyUser, logsRepo)).getGrid();
				for(int k = 0; k < n; k++) {//setting the combination of 4
					grid[i+k][j]=Player.PLAYER;
				}
				game = new PuissanceN(n, grid, dummyUser, logsRepo);
				assertEquals(Player.PLAYER,game.win());
			}
		}
	}
	

	@Test 
	void EveryVerticalWinCondition() throws InvalidSizeException {//PuissanceN with n=4
		Player[][] grid;
		PuissanceN game;
		int n = 4;
		PuissanceN size = new PuissanceN(n, dummyUser, logsRepo);
		for(int j = 0; j < size.getHeight()-n+1;j++) {//Each line (except 3 lasts)
			for(int i = 0; i < size.getWidth();i++) {//each case of line 
				grid = (new PuissanceN(n, dummyUser, logsRepo)).getGrid();
				for(int k = 0; k <n; k++) {//setting the combination of 4
					grid[i][j+k]=Player.PLAYER;
				}
				game = new PuissanceN(n,grid, dummyUser, logsRepo);
				assertEquals(Player.PLAYER,game.win());
			}
		}
	}
	
	@Test
	void EveryDiagonalUpRightWinCondition() throws InvalidSizeException {//PuissanceN with n=4
		Player[][] grid;
		PuissanceN game;
		int n = 4;
		PuissanceN size = new PuissanceN(n, dummyUser, logsRepo);
		for(int j = 0; j < size.getHeight()-n+1;j++) {//Each line (except 3 lasts)
			for(int i = 0; i < size.getWidth()-n+1;i++) {//each case of line (except 3 lasts)
				grid = (new PuissanceN(n, dummyUser, logsRepo)).getGrid();
				for(int k = 0; k <n; k++) {//setting the combination of 4
					grid[i+k][j+k]=Player.PLAYER;
				}
				game = new PuissanceN(n,grid, dummyUser, logsRepo);
				assertEquals(Player.PLAYER,game.win());
			}
		}
	}
	
	@Test
	void EveryDiagonalDownRightWinCondition() throws InvalidSizeException {//PuissanceN with n=4
		Player[][] grid;
		PuissanceN game;
		int n = 4;
		PuissanceN size = new PuissanceN(n, dummyUser, logsRepo);
		for(int j = size.getHeight()-1; j > n-2;j--) {//Each line (except 3 lasts)
			for(int i = 0; i < size.getWidth()-n+1;i++) {//each case of line (except 3 lasts)
				grid = (new PuissanceN(n, dummyUser, logsRepo)).getGrid();
				for(int k = 0; k <n; k++) {//setting the combination of 4
					grid[i+k][j-k]=Player.PLAYER;
				}
				game = new PuissanceN(n,grid, dummyUser, logsRepo);
				assertEquals(Player.PLAYER,game.win());
			}
		}
	}
	
	@Test
	void NobodyWin() throws InvalidSizeException{
		PuissanceN game = new PuissanceN(4, dummyUser, logsRepo);
		Player player;
		int compt = 0;
		for (int i = 0; i < 7;i++) {
			compt = 0;
			if(i%2==0) {player = Player.PLAYER;}else{player = Player.IA;}
			for (int j = 0;j<game.getHeight();j++) {
				game.play(i,player);
				compt++;
				if(compt == 2) {
					compt = 0;
					player = (player==Player.PLAYER)?Player.IA:Player.PLAYER;
				}
			}
		}
		assertEquals(Player.NOBODY,game.win());
	}
	
	@Test 
	void toJSONString() throws InvalidSizeException{
		Player[][] grid = (new PuissanceN(4, dummyUser, logsRepo)).getGrid();
		for(int k = 0; k <4; k++) {//setting the combination of 4
			grid[0][k]=Player.PLAYER;
		}
		assertEquals(
				"{\"grid\":[[0,0,1,1,1,1],[0,0,0,0,0,0],[0,0,0,0,0,0],[0,0,0,0,0,0],[0,0,0,0,0,0],[0,0,0,0,0,0],[0,0,0,0,0,0]],\"n\":4}",
				(new PuissanceN(4,grid, dummyUser, logsRepo)).toJSON().toString()
		);
	}
	
}