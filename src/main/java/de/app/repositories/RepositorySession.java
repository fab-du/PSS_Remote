package de.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import de.app.model.Session;
import java.lang.String;

import javax.transaction.Transactional;

/**
 * @author Siyapdje, Fabrice Dufils
 */
@RepositoryRestResource(exported = false)
public interface RepositorySession extends JpaRepository<Session, Long> {
	
	/**
	 * @param  email
	 * @return Session
	 */
	Session findOneByEmail(String email);
	
	/**
	 * 
	 * @param email
	 * @param token
	 * @return Session
	 */
	Session findOneByEmailOrToken( @Param("email") String email, @Param("token") String token  );
	
	/**
	 * @param token
	 * @return Session
	 */
	Session findOneByToken(String token);
	
	/**
	 * 
	 * @param expires_limit
	 * @return Iterable<Session>
	 */
	Iterable<Session> findByExpiresLessThan( Long expires_limit );
	
	/**
	 * @param token
	 * @return 
	 */
	@Modifying
	@Transactional
	@Query(value="delete from Session c where c.token = ?1")
	void deleteByToken(String token);
}
