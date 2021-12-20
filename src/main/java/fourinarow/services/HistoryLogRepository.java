package fourinarow.services;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fourinarow.model.GameFromHistory;
import fourinarow.model.HistoryLog;
import fourinarow.model.TttDecisionLog;

@Repository
public interface HistoryLogRepository extends CrudRepository<HistoryLog, Long> {

	@Transactional
	@Modifying
	@Query("delete from HistoryLog h where h.userId = ?1")
	void deleteFromUser(Long userId);
	
	@Query("select h from HistoryLog h where h.userId = ?1")
	List<HistoryLog> getFromUser(Long userId);
	
	@Query("select h from HistoryLog h where h.userId = ?1 and h.wonGame = 1 and h.fromAi = 0")
	List<HistoryLog> getWinsFromUser(Long userId);
	
	@Query("select h from HistoryLog h where h.wonGame = 1 and h.fromAi = 0")
	List<HistoryLog> getWins();
	
	@Query("SELECT new fourinarow.model.GameFromHistory(h.userId, h.gameId, h.gameType, MAX(h.createdAt), MAX(h.wonGame)) FROM HistoryLog h WHERE h.fromAi=0 AND h.userId=? GROUP BY gameId, userId, gameType")
	List<GameFromHistory> getGamesList(Long userId);
	
	@Query("select MAX(h.gameId) from HistoryLog h")
	Long getMaxGameId();
	
	@Query(value="SELECT h1.log_id, h1.game_id, (CASE WHEN h1.from_ai THEN REPLACE(REPLACE(REPLACE(h1.current_state, \"2\", \"3\"), \"1\", \"2\"), \"3\", \"1\") ELSE h1.current_state END) as current_state, h1.chosen_move, h1.won_game, h1.moves_before_end, (SELECT (CASE WHEN h2.from_ai=h1.from_ai THEN 1 ELSE 0 END) FROM history h2 WHERE h2.game_id = h1.game_id AND won_game = 1) as led_to_win FROM `history` h1 WHERE game_type = \"TTT\"", nativeQuery = true)
	List<TttDecisionLog> getTttDecisions();
	
}
