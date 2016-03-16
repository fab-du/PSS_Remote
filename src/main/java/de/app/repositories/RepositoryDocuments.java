package de.app.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import de.app.model.Document;

/**
 * @author Siyapdje, Fabrice Dufils
 */
public interface RepositoryDocuments extends JpaRepository<Document, Long>{

}
