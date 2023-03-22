package com.kochnev.friends;

import com.kochnev.friends.entities.Friendship;
import com.kochnev.friends.entities.User;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.lifecycle.Startables;

import java.util.stream.Stream;

@SpringBootTest
public class FriendsApplicationTests {

	public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:11.1");

	static {
		Startables.deepStart(Stream.of(postgresContainer)).join();
		System.setProperty("spring.datasource.url", postgresContainer.getJdbcUrl());
		System.setProperty("spring.datasource.username", postgresContainer.getUsername());
		System.setProperty("spring.datasource.password", postgresContainer.getPassword());
	}

	@Autowired
	private Flyway flyway;

	public void cleanAndMigrate() {
		flyway.clean();
		flyway.migrate();
	}

	public User createTestUser() {
		User testUser = new User();
		testUser.setId(5L);
		testUser.setName("test user");
		return testUser;
	}

	public Friendship createTestFriendship() {
		Friendship testFriendship = new Friendship();
		testFriendship.setUserId(2);
		testFriendship.setFriendId(3);
		return testFriendship;
	}
}
