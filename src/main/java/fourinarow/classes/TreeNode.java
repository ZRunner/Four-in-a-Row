package fourinarow.classes;

import java.util.Objects;

/**
 * Represents a node in the decision tree
 * A node is composed by a decision, ie a game state and a chosen move
 *
 */
public class TreeNode {
	public String currentState;
	public int chosenMove;
	private int hashcode;
	public TreeNode(String currentState, int chosenMove) {
		this.currentState = currentState;
		this.chosenMove = chosenMove;
		hashcode = Objects.hash(currentState, chosenMove);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) return true;
		if(obj == null || obj.getClass()!= this.getClass()) return false;
		TreeNode o = (TreeNode) obj;
		return o.currentState.equals(this.currentState) && o.chosenMove == this.chosenMove;
	}
	@Override
	public int hashCode() {
		return this.hashcode;
	}
}
