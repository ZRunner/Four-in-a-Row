package fourinarow.services;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fourinarow.model.GameFromHistory;
import fourinarow.model.HistoryLog;

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
	
	@Query("SELECT new fourinarow.model.GameFromHistory(h.userId, h.gameId, MAX(h.createdAt), MAX(h.wonGame)) FROM HistoryLog h WHERE h.fromAi=0 AND h.userId=? GROUP BY gameId, userId")
	List<GameFromHistory> getGamesList(Long userId);
	
	@Query("select MAX(h.gameId) from HistoryLog h")
	Long getMaxGameId();
	
}
