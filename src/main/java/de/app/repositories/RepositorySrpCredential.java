package de.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;


import de.app.model.SrpCredential;

public interface RepositorySrpCredential extends JpaRepository<SrpCredential, Long> {
	SrpCredential findOneByEmail(String email); 
}
