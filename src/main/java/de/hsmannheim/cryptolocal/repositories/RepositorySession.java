package de.hsmannheim.cryptolocal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import de.hsmannheim.cryptolocal.models.Session;
import java.lang.String;
import java.util.List;

public interface RepositorySession extends JpaRepository<Session, Long> {
	
	//Session findOneByAccess_token(String access_token);
	Session findOneByEmail(String email);
	//Session findOneByXsrf_token(String xsrf_token);
}
