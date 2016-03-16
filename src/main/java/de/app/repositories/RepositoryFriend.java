package de.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import de.app.model.Friendship;

/**
 * @author Siyapdje, Fabrice Dufils
 */
public interface RepositoryFriend extends JpaRepository<Friendship, Long>{

}
