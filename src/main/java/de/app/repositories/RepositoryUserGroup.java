package de.app.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import de.app.model.UserGroup;

/**
 * @author Siyapdje, Fabrice Dufils
 */
public interface RepositoryUserGroup extends JpaRepository<UserGroup, Long> {
	
	/**
	 * 
	 * @param gvid
	 * @return List<UserGroup>
	 */
	List<UserGroup> findByUseringroupId( Long gvid );
}
