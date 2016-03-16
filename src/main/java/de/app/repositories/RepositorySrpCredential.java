package de.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;


import de.app.model.SrpCredential;

/**
 * @author Siyapdje, Fabrice Dufils
 */
public interface RepositorySrpCredential extends JpaRepository<SrpCredential, Long> {
	
	/**
	 * 
	 * @param email
	 * @return SrpCredential
	 */
	SrpCredential findOneByEmail(String email); 
}
