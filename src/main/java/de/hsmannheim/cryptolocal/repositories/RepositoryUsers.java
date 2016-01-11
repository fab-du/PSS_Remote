package de.hsmannheim.cryptolocal.repositories;
import org.springframework.data.jpa.repository.JpaRepository;

import java.lang.String;
import de.hsmannheim.cryptolocal.models.User;


public interface RepositoryUsers extends JpaRepository<User, Long> {

	User findOneByEmail(String email);
	User findOneByFirstname(String firstname );
	User findOneBySecondname(String firstname );
	
}
