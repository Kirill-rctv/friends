# Description

restful API to create users and adding to friend

# Prerequisites

* Java 17;
* Apache Maven 3;
* Docker 20.10

# How to build

```
mvn package
```

# How to start

```
docker compose up
```

# Example

##### Crete new user:
```
curl --location --request POST 'localhost:8080/users/new' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "TEST NAME"
}'
```

##### Get user:
```
curl --location --request GET 'localhost:8080/users/1'
```

##### Update user:
```
curl --location --request PUT 'localhost:8080/users/1' \
--header 'Content-Type: application/json' \
--data-raw '{
    "name": "UPDATED NAME"
}'
```

##### Add friend:
when a user adds a new friend, the friend will have the user as a friend
```
curl --location --request POST 'localhost:8080/users/1/friends/2'
```

##### Get all friends:
_for greater clarity, you need to create an additional two users and add them to friend_
```
curl --location --request GET 'localhost:8080/users/1/friends'
```

##### Delete friend:
when a user deletes a friend, the friend's user remains a friend
```
curl --location --request DELETE 'localhost:8080/users/1/friends/2'
```

##### Delete user:
when a user is deleted, the entire list of his friends is deleted, but the friends of the user remain in their friend lists
```
curl --location --request DELETE 'localhost:8080/users/1'
```
