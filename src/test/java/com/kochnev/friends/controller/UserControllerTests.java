package com.kochnev.friends.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kochnev.friends.FriendsApplicationTests;
import com.kochnev.friends.entities.Friendship;
import com.kochnev.friends.entities.User;
import com.kochnev.friends.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
public class UserControllerTests extends FriendsApplicationTests {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private MockMvc mockMvc;

    @AfterEach
    void tearDown() {
        cleanAndMigrate();
    }

    @Test
    void createNewUser() throws Exception {

        User newUser = createTestUser();

        String expectedContent = objectMapper.writeValueAsString(newUser);

        this.mockMvc.perform(post("/users/new")
                        .content(expectedContent)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void getById() throws Exception {

        User testUser = new User(1L, "first");

        String expectedContent = objectMapper.writeValueAsString(testUser);

        this.mockMvc.perform(get("/users/" + testUser.getId()))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedContent));
    }

    @Test
    void updateById() throws Exception {

        User testUser = createTestUser();
        testUser.setId(1L);

        User updatedUser = testUser;
        updatedUser.setName("updated user name");

        String requestContent = objectMapper.writeValueAsString(updatedUser);

        this.mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestContent))
                .andExpect(status().isOk());

        User actualUser = userRepository.findById(1L).get();

        User expectedUser = testUser;
        expectedUser.setName("updated user name");

        Assertions.assertEquals(expectedUser, actualUser);
    }

    @Test
    void deleteById() throws Exception {

        User testUser = createTestUser();

        mockMvc.perform(delete("/users/" + testUser.getId()))
                .andExpect(status().isNoContent());
    }

    @Test
    void getFriendsById() throws Exception {

        User testUser = new User(1L, "first");
        List<User> testFriendsList = List.of(
                new User(2L, "second"),
                new User(3L, "third"),
                new User(4L, "fourth")
        );

        String expectedContent = objectMapper.writeValueAsString(testFriendsList);

        this.mockMvc.perform(get("/users/" + testUser.getId() + "/friends"))
                .andExpect(status().isOk())
                .andExpect(content().string(expectedContent));
    }

    @Test
    void addFriend() throws Exception {

        Friendship newFriendship = createTestFriendship();

        String expectedContent = objectMapper.writeValueAsString(newFriendship);

        this.mockMvc.perform(post("/users/" + newFriendship.getUserId() +
                        "/friends/" + newFriendship.getFriendId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void deleteFriend() throws Exception {

        Friendship testFriendship = createTestFriendship();

        mockMvc.perform(delete("/users/" + testFriendship.getUserId() +
                        "/friends/" + testFriendship.getFriendId()))
                .andExpect(status().isNoContent());
    }
}
