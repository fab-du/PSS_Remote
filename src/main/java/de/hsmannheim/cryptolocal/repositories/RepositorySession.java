package de.hsmannheim.cryptolocal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import de.hsmannheim.cryptolocal.models.Session;
import java.lang.String;

public interface RepositorySession extends JpaRepository<Session, Long> {
	Session findOneByEmail(String email);
	Session findOneByToken(String token);

}
