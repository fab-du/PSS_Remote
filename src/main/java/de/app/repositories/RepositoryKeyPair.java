package de.app.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import de.app.model.KeyPair;

public interface RepositoryKeyPair extends JpaRepository<KeyPair, Long> {
	

}
