package fourinarow.classes;

public enum Player {
	NOBODY(0), PLAYER(1), IA(2); //Each state of a grid square
	private int value;
	
	/**
	 * enum constructor
	 * @param value : equivalent int value for the type
	 */
	Player(int value){
		this.value = value;
	}
	
	/**
	 * value getter
	 * @return value : equivalent int value for the type
	 */
	public int getValue(){
		return value;
	}
}
