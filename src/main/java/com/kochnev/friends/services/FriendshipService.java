package com.kochnev.friends.services;

import com.kochnev.friends.entities.Friendship;

import java.util.Optional;

public interface FriendshipService {

    Friendship save(Long userId, Long friendId);

    Optional<Friendship> findByUserIdAndFriendId(Long userId, Long friendId);

    void deleteByUserIdAndFriendId(Long userId, Long friendId);

    void deleteByUserId(Long userId);
}
