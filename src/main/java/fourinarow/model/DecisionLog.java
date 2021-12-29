package fourinarow.model;

public interface DecisionLog {

	Long getLogId();
	Long getGameId();
	String getCurrentState();
	int getChosenMove();
	boolean getWonGame();
	int getMovesBeforeEnd();
	int getLedToWin();
}
