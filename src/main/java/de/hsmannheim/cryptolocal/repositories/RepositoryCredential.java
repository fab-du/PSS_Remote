package de.hsmannheim.cryptolocal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import de.hsmannheim.cryptolocal.models.SrpCredential;

@RepositoryRestResource(exported = false)
public interface RepositoryCredential extends JpaRepository<SrpCredential, Long>{ }
