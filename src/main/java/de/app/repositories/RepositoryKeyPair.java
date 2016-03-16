package de.app.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import de.app.model.KeyPair;

/**
 * @author Siyapdje, Fabrice Dufils
 */
public interface RepositoryKeyPair extends JpaRepository<KeyPair, Long> {
	
}
