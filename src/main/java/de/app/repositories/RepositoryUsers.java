package de.app.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.String;
import de.app.model.User;


public interface RepositoryUsers extends JpaRepository<User, Long> {

	User findOneByEmail(String email);
	User findOneByFirstname(String firstname );
	User findOneBySecondname(String firstname );
	
}
