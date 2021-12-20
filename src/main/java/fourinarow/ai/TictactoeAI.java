package fourinarow.ai;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;

import fourinarow.services.HistoryLogRepository;

public class TictactoeAI {
	
	private static TictactoeAI instance = new TictactoeAI();
	
	public static TictactoeAI getInstance() {
		return instance;
	}
	
	private static class TreeNode {
		public String currentState;
		public int chosenMove;
		public TreeNode(String currentState, int chosenMove) {
			this.currentState = currentState;
			this.chosenMove = chosenMove;
		}
		@Override
		public boolean equals(Object obj) {
			if (this == obj) return true;
			if(obj == null || obj.getClass()!= this.getClass()) return false;
			TreeNode o = (TreeNode) obj;
			return o.currentState == this.currentState && o.chosenMove == this.chosenMove;
		}
	}
	
	private Map<TreeNode, Double> tree = new HashMap<>();
	
	private TictactoeAI() {}
	
	public void buildTree(HistoryLogRepository logsRepo) {
		Map<TreeNode, int[]> temp_tree = new HashMap<>();
		logsRepo.getTttDecisions().forEach(log -> {
			TreeNode key = new TreeNode(log.getCurrentState(), log.getChosenMove());
			int[] v = temp_tree.get(key);
			if (v != null) {
				if (log.hasLedToWin()) {
					v[0]++;
				}
				v[1] = v[1] + 1;
			}
			temp_tree.put(key, v);
		});
		
		tree.clear();
		temp_tree.forEach((state, value) -> {
			tree.put(state, (double)value[0]/(double)value[1]);
		});
	}
	
	public int getBestDecision(JSONArray state) throws Exception {
		// if the tree wasn't initialized yet, raise an error
		if (tree.size() == 0) {
			throw new Exception("The decision tree has not yet been built");
		}
		
		String fixedState = state.toString();
		Integer best_move = null;
		double best_value = -1.0;
		
		// for each possible move
		for (int i=0; i<state.length(); i++) {
			if (state.getInt(i) == 0) {
				// get the prediction from the decision tree
				TreeNode mov = new TreeNode(fixedState, i);
				Double prediction = tree.get(mov);
				// if it couldn't be found, pass
				if (prediction == null) {
					continue;
				}
				// if it's better than the last one, save it
				if (prediction > best_value) {
					best_move = i;
					best_value = prediction;
				}
			}
		}
		
		System.out.println("BEST MOVE: "+best_move+"  "+best_value);
		// if no best move were find in the decision tree, return the first possible move
		if (best_move == null) {
			for (int i=0; i<state.length(); i++) {
				if (state.getInt(i) == 0) {
					return i;
				}
			}
		}
		return best_move;
	}

}
