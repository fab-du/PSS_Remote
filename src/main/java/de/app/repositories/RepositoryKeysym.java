package de.app.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import de.app.model.KeySym;
import java.lang.String;

/**
 * @author Siyapdje, Fabrice Dufils
 */
public interface RepositoryKeysym extends JpaRepository<KeySym, Long> {
	/**
	 * 
	 * @param symkey
	 * @return KeySym
	 */
	public KeySym findOneBySymkey(String symkey);
}
