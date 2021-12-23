package fourinarow.classes;

public class AINotInitializedException extends Exception {

	private static final long serialVersionUID = 645176238536039297L;
	
	public AINotInitializedException() {
		super("The decision tree has not yet been built");
	}

}
