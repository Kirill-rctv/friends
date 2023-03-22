package com.kochnev.friends.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @SequenceGenerator(name="users_id_seq", sequenceName="users_id_seq", allocationSize=1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="users_id_seq")
    @Column(name = "id", updatable=false)
    private long id;

    @Column(name = "name")
    private String name;
}
