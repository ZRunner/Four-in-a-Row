package fourinarow.ai;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Random;

import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fourinarow.Application;
import fourinarow.classes.AINotInitializedException;
import fourinarow.services.HistoryLogRepository;

public class TictactoeAI {
	
	private static final Logger LOG = LoggerFactory.getLogger(Application.class);
	
	private static TictactoeAI instance = new TictactoeAI();
	
	public static TictactoeAI getInstance() {
		return instance;
	}
	
	/**
	 * Represents a node in the decision tree
	 * A node is composed by a decision, ie a game state and a chosen move
	 *
	 */
	private static class TreeNode {
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
		
	private Map<TreeNode, Double> tree = new HashMap<>();
	private Random rand = new Random();
	
	private TictactoeAI() {}
	
	/**
	 * Build the decision tree used by the game AI
	 * This must be called at least once in the runtime, before calling getBestDecision()
	 * @param logsRepo The history logs repository used to get the decisions
	 */
	public void buildTree(HistoryLogRepository logsRepo) {
		LOG.info("Building TicTacToe tree...");
		Map<TreeNode, int[]> temp_tree = new HashMap<>();
		
		// for each saved decision
		logsRepo.getTttDecisions().forEach(log -> {
			// get the corresponding node
			TreeNode key = new TreeNode(log.getCurrentState(), log.getChosenMove());
			int[] v = temp_tree.get(key);
			// if this decision was never saved in our tree, assign it a new value
			// value corresponds to an array [won, played] to get how many times that decision was taken and how much it helped to win
			if (v == null) {
				v = new int[2];
				v[0] = 0;
				v[1] = 0;
			}
			// if that decision led to a victory, increase the counter
			if (log.getLedToWin() == 1) { // yeah cuz SQL returns NUMERIC instead of BOOL, dont ask why
				v[0]++;
			}
			// increase the "played" counter
			v[1] = v[1] + 1;
			temp_tree.put(key, v);
		});
		
		// build the final tree based on our observations for each decision
		tree.clear();
		temp_tree.forEach((state, value) -> {
			tree.put(state, (double)value[0]/(double)value[1]);
		});
	}
	
	/**
	 * Choose a random item inside an integer list
	 * @param list The original list to choose an item from
	 * @return The chosen item
	 */
	private int randomInList(List<Integer> list) {
		return list.get(rand.nextInt(list.size()));
	}
	
	/**
	 * Guess the best decision to take from a specific game
	 * @param state The current game grid
	 * @return The cell to play (might be random if the AI doesn't know)
	 * @throws AINotInitializedException Exception raised if the tree was never built (or is empty)
	 */
	public int getBestDecision(JSONArray state) throws AINotInitializedException {
		// if the tree wasn't initialized yet, raise an error
		if (tree.size() == 0) {
			throw new AINotInitializedException();
		}
		
		String fixedState = state.toString();
		Integer best_move = null;
		double best_value = 0.0;
		List<Integer> empty_cells = new LinkedList<Integer>();
		LOG.debug("Trying to find a decision for "+fixedState);
		
		// for each possible move
		for (int i=0; i<state.length(); i++) {
			if (state.getInt(i) == 0) {
				// get the prediction from the decision tree
				TreeNode mov = new TreeNode(fixedState, i);
				Double prediction = tree.get(mov);
				// if it couldn't be found, pass
				if (prediction == null) {
					LOG.trace(" could not find "+i);
					empty_cells.add(i);
					continue;
				}
				// if it's better than the last one, save it
				if (prediction > best_value) {
					LOG.trace(" better option for i="+i+": prediction="+prediction);
					best_move = i;
					best_value = prediction;
				} else {
					LOG.trace(" not a better option for i="+i+": prediction="+prediction);
				}
			}
		}
		
		LOG.debug("BEST MOVE: "+best_move+"  "+best_value);
		// if no best move were find in the decision tree
		if (best_move == null) {
			// if we still have some unexplored cell, return it
			if (empty_cells.size() > 0) {
				return randomInList(empty_cells);
			}
			// else, get the first available cell
			for (int i=0; i<state.length(); i++) {
				if (state.getInt(i) == 0) {
					return i;
				}
			}
		}
		return best_move;
	}

}
