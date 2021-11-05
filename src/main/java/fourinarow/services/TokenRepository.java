package fourinarow.services;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import fourinarow.model.Token;

@Repository
public interface TokenRepository extends CrudRepository<Token, String> {

	@Transactional
	@Modifying
	@Query("delete from Token t where t.userId = ?1")
	void deleteFromUser(Long userId);

}
