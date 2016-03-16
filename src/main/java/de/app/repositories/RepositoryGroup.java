package de.app.repositories;

import de.app.model.Group;
import java.lang.Long;
import java.lang.String;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author Siyapdje, Fabrice Dufils
 */
public interface RepositoryGroup extends JpaRepository<Group, Long>{
	/**
	 * @param name
	 * @return Group
	 */
	Group findOneByName(String name);
	
	/**
	 * @param gvid
	 * @return List<Group>
	 */
	List<Group> findByGvid(Long gvid);
}
