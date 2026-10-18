package ru.university.krasnoperov.repository;

import ru.university.krasnoperov.model.User;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface UserRepo {

    User addUser(User newUser);

    Optional<User> getUserById(UUID id);

    boolean deleteUser(UUID id);

    User updateUser(User updateUser);

    Map<UUID, User> getData();
}
