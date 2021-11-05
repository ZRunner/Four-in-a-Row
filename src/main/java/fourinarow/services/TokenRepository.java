package fourinarow.services;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import fourinarow.model.Token;

@Repository
public interface TokenRepository extends CrudRepository<Token, Long> {

//	@Override
//	default <S extends Token> S save(S entity) {
//		throw new UnsupportedOperationException();
//	}
//
//	@Override
//	default <S extends Token> Iterable<S> save(Iterable<S> entities) {
//		throw new UnsupportedOperationException();
//	}

}
