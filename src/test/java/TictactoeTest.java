import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import fourinarow.classes.Tictactoe;
import fourinarow.classes.Tictactoe.Players;

class TictactoeTest {
	
	/**
	 * Classic game
	 */
	@Test
	void global() {
		Tictactoe tictactoe = new Tictactoe();
		Players gagnant = null;
		for(int i=0;i<8;i++) {
			tictactoe.setGrid(i,i%2==0?Players.PLAYER:Players.IA);
			gagnant = tictactoe.win();
			if(gagnant!=null) {
				break;
			}
		}
		if(gagnant == Players.PLAYER) {
			System.out.println("global Test : passed");
		}else {
			fail("The winner should be PLAYER and it is not.");
		}
	}
	
	/**
	 * No-win situation 
	 */
	@Test
	void nobodyWin(){
		Players[] square = {Players.IA,Players.IA,Players.PLAYER,Players.PLAYER,Players.PLAYER,Players.IA,Players.IA,Players.IA,Players.PLAYER};
		Tictactoe tictactoe = new Tictactoe(square);
		Players gagnant = tictactoe.win();
		if(gagnant == Players.NOBODY) {
			System.out.println("nobodyWin Test : passed");
		}else {
			fail("The winner should be NOBODY and it is not.");
		}
	}

	/**
	 * Every situation that make PLAYER win
	 */
	@Test
	void eachWinningCondition() {
		int[][] winCondition = {
				{0,1,2},
				{3,4,5},
				{6,7,8},
				{0,4,8},
				{2,4,6},
				{0,3,6},
				{1,4,7},
				{2,5,8}
				};
		boolean isPassed = true;
		
		for(int i=0 ; i<8 ; i++) {
			Players[] square = {Players.NOBODY,Players.NOBODY,Players.NOBODY,Players.NOBODY,Players.NOBODY,Players.NOBODY,Players.NOBODY,Players.NOBODY,Players.NOBODY};
			for(int j=0 ; j<3 ; j++) {
				square[winCondition[i][j]] = Players.PLAYER;
			}
			Tictactoe tictactoe = new Tictactoe(square);
			Players gagnant = tictactoe.win();
			if(gagnant != Players.PLAYER) {
				isPassed = false;
			}
		}
		if(isPassed == true) {
			System.out.println("eachWinningCondition Test : passed");
		}else {
			fail("The winner should be PLAYER and it is not.");
		}
	}
}

