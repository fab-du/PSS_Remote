package de.app.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.String;
import de.app.model.User;

/**
 * @author Siyapdje, Fabrice Dufils
 */
public interface RepositoryUsers extends JpaRepository<User, Long> {

	/**
	 * 
	 * @param email
	 * @return User
	 */
	User findOneByEmail(String email);
	
	/**
	 * 
	 * @param firstname
	 * @return User
	 */
	User findOneByFirstname(String firstname );
	
	/**
	 * 
	 * @param firstname
	 * @return User
	 */
	User findOneBySecondname(String firstname );
	
}
