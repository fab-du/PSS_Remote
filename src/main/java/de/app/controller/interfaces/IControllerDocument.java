package de.app.controller.interfaces;

import de.app.model.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Siyapdje, Fabrice Dufils
 * ControllerDocument : Resource Controller for managing Document
 */
publiic interface IControllerDocument{

    /**
     * GET /api/documents : get all documents
     *
     * @param
     * @return the ResponseEntity with status 200 (OK) and the list of documents in body
     * @throws
     */
	public ResponseEntity<Document[]> find();
	
    /**
     * GET /api/documents/:documentId get one document with id = documentId
     *
     * @param id the id of the document retrieve
     * @return the ResponseEntity with status 200 (OK) and the one document in body, or with status
     * 404 (Not Found)
     */
	public ResponseEntity<Document> findOne( @PathVariable(value="documentId") Long documentId );
	
    /**
     * POST /api/documents upload and document and create reference in database
     *
     * @param file to upload
     * @return the ResponseEntity with status 201 (CREATED) without body
     */
	public ResponseEntity<?> create( @Validated @RequestParam("file") MultipartFile file);

    /**
     * POST /api/documents/:documentId get one document with id = documentId
     *
     * @param documentId to upload
     * @return the ResponseEntity with status 201 (CREATED) without body
     */
	public ResponseEntity<?> documentId_changeOwner( @PathVariable(value="documentId") Long documentId );

    /**
     * POST /api/documents/:documentId get one document with id = documentId
     *
     * @param file to upload
     * @return the ResponseEntity with status 201 (CREATED) without body
     */
	public ResponseEntity<?> documentId_shareDocument( @PathVariable(value="documentId") Long documentId );

}
