package de.hsmannheim.cryptolocal.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import de.hsmannheim.cryptolocal.models.KeyPair;

public interface RepositoryKeyPair extends JpaRepository<KeyPair, Long> {
	

}
