package de.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import de.app.model.SrpCredential;

/**
 * @author Siyapdje, Fabrice Dufils
 */
@RepositoryRestResource(exported = false)
public interface RepositoryCredential extends JpaRepository<SrpCredential, Long>{ 

}
