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
	
	@Query("SELECT new fourinarow.model.GameFromHistory(h.userId, h.gameId, h.gameType, MAX(h.createdAt), MAX(h.wonGame)) FROM HistoryLog h WHERE h.fromAi=0 AND h.userId=?1 GROUP BY gameId, userId, gameType")
	List<GameFromHistory> getGamesList(Long userId);
	
	@Query("select MAX(h.gameId) from HistoryLog h")
	Long getMaxGameId();
	
	@Query(value="SELECT h1.log_id as logId, h1.game_id as gameId, (CASE WHEN h1.from_ai THEN h1.current_state ELSE REPLACE(REPLACE(REPLACE(h1.current_state, \"2\", \"3\"), \"1\", \"2\"), \"3\", \"1\") END) as currentState, h1.chosen_move as chosenMove, h1.won_game as wonGame, h1.moves_before_end as movesBeforeEnd, coalesce((SELECT (CASE WHEN h2.from_ai=h1.from_ai THEN TRUE ELSE FALSE END) FROM history h2 WHERE h2.game_id = h1.game_id AND won_game = 1), FALSE) as ledToWin FROM `history` h1 WHERE game_type = \"TTT\"", nativeQuery = true)
	List<TttDecisionLog> getTttDecisions();
	
	@Transactional
	@Modifying
	@Query("UPDATE HistoryLog h SET h.userId = -1 WHERE h.userId = ?1")
	void deleteStatistics(Long userId);
}
