package de.hsmannheim.cryptolocal.repositories;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import de.hsmannheim.cryptolocal.models.*;


public interface RepositoryUserGroup extends JpaRepository<UserGroup, Long> {

	List<UserGroup> findByUseringroupId( Long gvid );

}
