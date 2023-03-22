package com.kochnev.friends.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "friendships")
@IdClass(Friendship.class)
public class Friendship {
    @Id
    @Column(name = "user_id", updatable=false)
    long userId;

    @Id
    @Column(name = "friend_id", updatable=false)
    long friendId;
}
