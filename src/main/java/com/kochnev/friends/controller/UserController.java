package com.kochnev.friends.controller;

import com.kochnev.friends.entities.Friendship;
import com.kochnev.friends.entities.User;
import com.kochnev.friends.services.FriendshipService;
import com.kochnev.friends.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final FriendshipService friendshipService;


    @PostMapping("/new")
    @Transactional
    public ResponseEntity<User> create(@RequestBody User user) {
        Optional<User> userData = userService.findById(user.getId());

        try {
            if (userData.isEmpty()) {
                return new ResponseEntity<>(userService.save(user), HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable("id") Long id) {
        Optional<User> userData = userService.findById(id);

        try {
            if (userData.isPresent()) {
                return new ResponseEntity<>(userData.get(), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<User> updateById(@PathVariable("id") Long id, @RequestBody User user) {
        Optional<User> userData = userService.findById(id);

        try {
            if (userData.isPresent()) {
                User updatedUser = userData.get();
                updatedUser.setName(user.getName());
                return new ResponseEntity<>(userService.save(updatedUser), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<HttpStatus> deleteById(@PathVariable("id") Long id) {
        try {
            userService.deleteById(id);
            friendshipService.deleteByUserId(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/friends")
    @Transactional
    public ResponseEntity<List<User>> getFriendsById(@PathVariable("id") Long id) {
        Optional<User> userData = userService.findById(id);

        try {
            if (userData.isPresent()) {
                return new ResponseEntity<>(userService.findAllFriendsByUserId(id), HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/{userId}/friends/{friendId}")
    @Transactional
    public ResponseEntity<Friendship> addFriend(@PathVariable("userId") Long userId, @PathVariable("friendId") Long friendId) {
        Optional<User> friendData = userService.findById(friendId);
        Optional<Friendship> friendshipData = friendshipService.findByUserIdAndFriendId(userId, friendId);

        try {
            if (!Objects.equals(userId, friendId)) {
                if (friendData.isPresent()) {
                    if (friendshipData.isEmpty()) {
                        return new ResponseEntity<>(friendshipService.save(userId, friendId), HttpStatus.CREATED);
                    } else {
                        return new ResponseEntity<>(HttpStatus.CONFLICT);
                    }
                } else {
                    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
                }
            } else {
                return new ResponseEntity<>(HttpStatus.CONFLICT);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/{userId}/friends/{friendId}")
    public ResponseEntity<HttpStatus> deleteFriend(@PathVariable("userId") Long userId, @PathVariable("friendId") Long friendId) {
        try {
            friendshipService.deleteByUserIdAndFriendId(userId, friendId);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
