package com.kochnev.friends.repositories;

import com.kochnev.friends.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(nativeQuery = true,
            value = "select u.id, u.name " +
            "from users u " +
            "join friendships f on u.id = f.friend_id " +
            "where f.user_id = :userId")
    List<User> findAllFriendsByUserId(Long userId);
}
