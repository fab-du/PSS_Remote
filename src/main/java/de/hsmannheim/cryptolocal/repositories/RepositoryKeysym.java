package de.hsmannheim.cryptolocal.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import de.hsmannheim.cryptolocal.models.KeySym;
import java.lang.String;


public interface RepositoryKeysym extends JpaRepository<KeySym, Long> {

public KeySym findOneBySymkey(String symkey);


}
