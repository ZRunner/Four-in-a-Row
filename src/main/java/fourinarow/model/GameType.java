package fourinarow.model;

public enum GameType {
	TTT("Tic-Tac-Toe");

	private final String full_name;
	GameType(String full_name) {
		this.full_name = full_name;
	}
	String getFullName() {
		return this.full_name;
	}
}
