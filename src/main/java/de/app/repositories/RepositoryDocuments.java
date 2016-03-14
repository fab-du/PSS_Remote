package de.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import de.app.model.Document;

public interface RepositoryDocuments extends JpaRepository<Document, Long>{

}
