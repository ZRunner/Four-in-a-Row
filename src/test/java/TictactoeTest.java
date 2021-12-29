import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import fourinarow.classes.Player;
import fourinarow.classes.Tictactoe;
import fourinarow.model.User;
import fourinarow.services.HistoryLogRepository;

@Service
class TictactoeTest {
	
	public static User dummyUser = new User((long) 1);
	
	@Autowired
	private HistoryLogRepository logsRepo;
	
	/**
	 * Classic game
	 */
	@Test
	void global() {
		Tictactoe tictactoe = new Tictactoe(dummyUser, logsRepo);
		Player gagnant = null;
		for(int i=0;i<8;i++) {
			tictactoe.setSquare(i,i%2==0?Player.PLAYER:Player.IA);
			gagnant = tictactoe.win();
			if(gagnant!=null) {
				break;
			}
		}
		if(gagnant == Player.PLAYER) {
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
		Player[] square = {Player.IA,Player.IA,Player.PLAYER,Player.PLAYER,Player.PLAYER,Player.IA,Player.IA,Player.IA,Player.PLAYER};
		Tictactoe tictactoe = new Tictactoe(dummyUser, logsRepo, square);
		Player gagnant = tictactoe.win();
		if(gagnant == Player.NOBODY) {
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
			Player[] square = {Player.NOBODY,Player.NOBODY,Player.NOBODY,Player.NOBODY,Player.NOBODY,Player.NOBODY,Player.NOBODY,Player.NOBODY,Player.NOBODY};
			for(int j=0 ; j<3 ; j++) {
				square[winCondition[i][j]] = Player.PLAYER;
			}
			Tictactoe tictactoe = new Tictactoe(dummyUser, logsRepo, square);
			Player gagnant = tictactoe.win();
			if(gagnant != Player.PLAYER) {
				isPassed = false;
			}
		}
		if(isPassed == true) {
			System.out.println("eachWinningCondition Test : passed");
		}else {
			fail("The winner should be PLAYER and it is not.");
		}
	}
	

	/**
	 * toString methode with JSONArray
	 */
	@Test
	void parseJSONArray() {
		Tictactoe tictactoe = new Tictactoe(dummyUser, logsRepo);
		if(tictactoe.toString().equals("{\"grid\":[0,0,0,0,0,0,0,0,0]}")) {
			System.out.println("toJSONString Test : passed");
		}else {
		    fail("toString() as JSON doesn't work.");
		}
		
	}
}

