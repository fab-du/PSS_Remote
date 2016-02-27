package de.hsmannheim.cryptolocal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import de.hsmannheim.cryptolocal.models.Session;
import java.lang.String;

import javax.transaction.Transactional;

public interface RepositorySession extends JpaRepository<Session, Long> {
	Session findOneByEmail(String email);
	Session findOneByToken(String token);
	Iterable<Session> findByExpiresLessThan( Long expires_limit );
	@Modifying
	@Transactional
	@Query(value="delete from Session c where c.token = ?1")
	void deleteByToken(String token);
}
