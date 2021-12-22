import static org.junit.jupiter.api.Assertions.*;

import java.util.Scanner;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.stereotype.Service;

import fourinarow.classes.PuissanceN;
import fourinarow.classes.PuissanceN.InvalidSizeException;
import fourinarow.classes.Tictactoe.Player;

@Service
class TestPuissanceN {
	@Test
	void creation11() {
		assertThrows(InvalidSizeException.class, () -> {
	        new PuissanceN(11);
	    });
	}
	
	@Test
	void creation0() {
		assertThrows(InvalidSizeException.class, () -> {
	        new PuissanceN(2);
	    });
	}
	
	@Test
	void creation4() throws InvalidSizeException {
		PuissanceN game = new PuissanceN(4);
		Assert.assertEquals(7,game.getWidth());
		Assert.assertEquals(6,game.getHeight());
		Assert.assertEquals(7,game.getGrid().length);
		Assert.assertEquals(6,game.getGrid()[0].length);
	}
	
	@Test 
	void playNegativeX() throws InvalidSizeException{
		PuissanceN game = new PuissanceN(4);
		game.play(-1,Player.PLAYER);
		Assert.assertEquals("The index must be between 0 and "+(game.getWidth()-1),game.getMessage());
	}
	
	@Test 
	void playOutLimitX() throws InvalidSizeException{
		PuissanceN game = new PuissanceN(4);
		game.play(7,Player.PLAYER);
		Assert.assertEquals("The index must be between 0 and "+(game.getWidth()-1),game.getMessage());
	}
	
	@Test 
	void playFullColumn() throws InvalidSizeException{
		PuissanceN game = new PuissanceN(4);
		for(int i=0;i<=game.getHeight();i++) {
			game.play(0,Player.PLAYER);
		}
		Assert.assertEquals("The column is full, you can't play there",game.getMessage());
	}
	
	@Test
	void playEverywhere() throws InvalidSizeException{
		PuissanceN game = new PuissanceN(4);
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
		for(int j = 0; j < 6;j++) {//Each line
			for(int i = 0; i < 4;i++) {//each case of line (except 3 lasts)
				grid = (new PuissanceN(4)).getGrid();
				for(int k = 0; k <4; k++) {//setting the combination of 4
					grid[i+k][j]=Player.PLAYER;
				}
				game = new PuissanceN(4,grid);
				assertEquals(Player.PLAYER,game.win());
			}
		}
	}
	

	@Test 
	void EveryVerticalWinCondition() throws InvalidSizeException {//PuissanceN with n=4
		Player[][] grid;
		PuissanceN game;
		for(int j = 0; j < 3;j++) {//Each line
			for(int i = 0; i < 7;i++) {//each case of line (except 3 lasts)
				grid = (new PuissanceN(4)).getGrid();
				for(int k = 0; k <4; k++) {//setting the combination of 4
					grid[i][j+k]=Player.PLAYER;
				}
				game = new PuissanceN(4,grid);
				assertEquals(Player.PLAYER,game.win());
			}
		}
	}
}

















