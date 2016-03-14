package de.app.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import de.app.model.UserGroup;

public interface RepositoryUserGroup extends JpaRepository<UserGroup, Long> {
	List<UserGroup> findByUseringroupId( Long gvid );
}
