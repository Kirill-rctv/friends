package com.kochnev.friends.services;

import com.kochnev.friends.entities.Friendship;
import com.kochnev.friends.repositories.FriendshipRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendshipServiceImpl implements FriendshipService {

    private final FriendshipRepository friendshipRepository;


    @Override
    public Friendship save(Long userId, Long friendId) {
        Friendship friendship = new Friendship(userId, friendId);
        Friendship swappedFriendship = new Friendship(friendId, userId);
        friendshipRepository.save(friendship);
        friendshipRepository.save(swappedFriendship);
        return friendship;
    }

    @Override
    public Optional<Friendship> findByUserIdAndFriendId(Long userId, Long friendId) {
        return friendshipRepository.findByUserIdAndFriendId(userId, friendId);
    }

    @Override
    public void deleteByUserIdAndFriendId(Long userId, Long friendId) {
        friendshipRepository.deleteByUserIdAndFriendId(userId, friendId);
    }

    @Override
    public void deleteByUserId(Long userId) {
        friendshipRepository.deleteByUserId(userId);
    }
}
