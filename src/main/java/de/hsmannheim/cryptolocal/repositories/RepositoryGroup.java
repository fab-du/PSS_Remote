package de.hsmannheim.cryptolocal.repositories;

import de.hsmannheim.cryptolocal.models.Group;
import java.lang.Long;
import java.lang.String;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RepositoryGroup extends JpaRepository<Group, Long>{
	Group findOneByName(String name);
}
