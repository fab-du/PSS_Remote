package de.app.controller.interfaces;

import de.app.model.Friendship;
import de.app.model.User;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Siyapdje, Fabrice Dufils
 * ControllerFriend : Resource Controller for managing Friends
 * Resource at /api/users/:userId/friends
 */
public interface IControllerFriend{

    /**
     * GET /api/:userId/friends : get all friends (User Object)
     *
     * @param userId 
     * @return the ResponseEntity with status 200 (OK) and the list of Friends(Users) of user with
     * id=userId in body
     * @throws
     */
	public ResponseEntity<User[]> find();
	
    /**
     * GET /api/:userId/friends/:friendId get one document with id = documentId
     *
     * @param userId, friendId 
     * @return the ResponseEntity with status 200 (OK) and the one user in body, or with status
     * 404 (Not Found)
     */
	public ResponseEntity<User> findOne( @PathVariable(value="userId") Long userId,  @PathVariable(value="friendId") Long friendId);

    /**
     * POST /api/:userId/friends add friend to user with id userId 
     *
     * @param userId, friendShip 
     * @return the ResponseEntity with status 201 (CREATED) without body
     */
	public ResponseEntity<?> create( @PathVariable(value="userId") Long userId, @Validated @RequestBody Friendship friendship );

    /**
     * DELETE /api/:userId/friends/revoke revoke friend with id=friendId from user with id=userId 
     *
     * @param userId, friendId 
     * @return the ResponseEntity with status 202 (ACCEPTED) without body
     */
	public ResponseEntity<?> revoke(@PathVariable(value="userId") Long userId, @PathVariable(value="friendId") Long friendId);

    /**
     * PUT /api/:userId/friends/:friendId/addToGroup/:groupId add friend with id=friendId from user with id=userId to group with
     * id=groupId. NB: user with id=userId have to be GV of group with id=groupId. 
     *
     * @param userId friendId groupId
     * @return the ResponseEntity with status 201 (CREATED) without body
     */
	public ResponseEntity<?> addFriendToGroup( @PathVariable(value="userId") Long userId, @PathVariable(value="friendId") Long friendId, @PathVariable(value="groupId") Long groupId  );


}
