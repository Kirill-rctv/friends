package com.kochnev.friends.services;

import com.kochnev.friends.entities.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User save(User user);

    Optional<User> findById(Long id);

    User update(User user);

    void deleteById(Long id);

    List<User> findAllFriendsByUserId(Long userId);
}
