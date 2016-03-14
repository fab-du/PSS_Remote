package de.app.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import de.app.model.KeySym;
import java.lang.String;


public interface RepositoryKeysym extends JpaRepository<KeySym, Long> {
	public KeySym findOneBySymkey(String symkey);
}
