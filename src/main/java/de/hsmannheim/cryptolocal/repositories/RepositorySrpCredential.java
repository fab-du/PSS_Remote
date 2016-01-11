package de.hsmannheim.cryptolocal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;


import de.hsmannheim.cryptolocal.models.SrpCredential;

public interface RepositorySrpCredential extends JpaRepository<SrpCredential, Long> {
	SrpCredential findOneByEmail(String email); 
}
