package de.app.controller.interfaces;

import de.app.model.Document;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Siyapdje, Fabrice Dufils
 * ControllerDocument : Resource Controller for managing Document
 * resource path : /api/users/:userId/groups/:groupId/documents
 */
public interface IControllerDocument{

    /**
     * GET /api/documents : get all documents
     *
     * @param
     * @return the ResponseEntity with status 200 (OK) and the list of documents in body
     * @throws
     * resource path : /api/users/:userId/groups/:groupId/documents
     */
	public ResponseEntity<Document[]> find( @PathVariable( value="userId" ) Long userId, @PathVariable( value="groupId" ) Long groupId );
	
    /**
     * GET /api/users/:userId/groups/:groupId/documents/:documentId get one document with id = documentId
     *
     * @param id the id of the document retrieve
     * @return the ResponseEntity with status 200 (OK) and the one document in body, or with status
     * 404 (Not Found)
     * resource path : /api/users/:userId/groups/:groupId/documents/:documentId
     */
	public ResponseEntity<Document> findOne( @PathVariable( value="userId" ) Long userId, @PathVariable( value="groupId" ) Long groupId, @PathVariable(value="documentId") Long documentId );

    /**
     * POST /api/users/:userId/groups/:groupId/documents upload and document and create reference in database
     *
     * @param file to upload
     * @return the ResponseEntity with status 201 (CREATED) without body
     */
	public ResponseEntity<?> upload( @PathVariable( value="userId" ) Long userId, @PathVariable( value="groupId" ) Long groupId, @Validated @RequestParam("file") MultipartFile file);

    /**
     * GET /api/users/:userId/groups/:groupId/documents/:documentId upload and document and create reference in database
     *
     * @param userId, groupId, documentId
     * @return the ResponseEntity with status 201 (CREATED) without body
     */
	public ResponseEntity<?> download( @PathVariable( value="userId"    ) Long userId, 
                                       @PathVariable( value="groupId"   ) Long groupId,
                                       @PathVariable( value="documentId") Long documentId );


    /**
     * POST /api/users/:userId/groups/:groupId/documents/:documentId/shareWithUser/:chosedUserId
     *
     * @param userId, groupId, documentId, chosedUserId
     * @return the ResponseEntity with status 200 (CREATED) without body if the change was
     * successful, 304 if the user with id=userId is not the Principal and/or not the gv of group
     * with id=goupId. 400 (Bad Request) if the something else goes wrong. 
     */
	public ResponseEntity<?> shareWithUser( @PathVariable( value="userId"      ) Long userId,    
                                            @PathVariable( value="groupId"     ) Long groupId,   
                                            @PathVariable(value="documentId"   ) Long documentId,
                                            @PathVariable(value="chosedUserId" ) Long chosedUserId );


    /**
     * POST /api/users/:userId/groups/:groupId/documents/:documentId/shareWithGroup/:chosedGroupId
     *
     * @param userId,documentId,goupId,documentId,chosedGroupId
     * @return the ResponseEntity with status 201 (CREATED) without body
     */
	public ResponseEntity<?> shareWithGroup(@PathVariable( value="userId"       ) Long userId, 
                                            @PathVariable( value="groupId"      ) Long groupId,  
                                            @PathVariable( value="documentId"   ) Long documentId,
                                            @PathVariable(value="chosedGroupId" ) Long chosedGroupId );


    /**
     * DELETE /api/users/:userId/groups/:groupId/documents/:documentId
     *
     * @param userId, groupId, documentId 
     * @return the ResponseEntity with status 200 (OK) without body or 202(NO Resource) If no
     * document with id=documentId was found
     */
	public ResponseEntity<?> delete( @PathVariable( value="userId"     ) Long userId, 
                                     @PathVariable( value="groupId"    ) Long groupId, 
                                     @PathVariable( value="documentId" ) Long documentId );

}
