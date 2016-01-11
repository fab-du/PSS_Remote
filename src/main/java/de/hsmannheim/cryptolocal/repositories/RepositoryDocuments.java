package de.hsmannheim.cryptolocal.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import de.hsmannheim.cryptolocal.models.Document;

public interface RepositoryDocuments extends JpaRepository<Document, Long>{

}
