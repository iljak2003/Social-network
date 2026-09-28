package ru.university.krasnoperov.repository;

import ru.university.krasnoperov.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class UserRepo {

    private final Map<UUID, User> data;

    public UserRepo(Map<UUID, User> data) {
        this.data = data;
    }

    public UserRepo() {
        this.data = new HashMap<>();
    }

    public User addUser(User newUser){
        data.put(newUser.getId(), newUser);
        return newUser;
    }

    public Optional<User> getUserById(UUID id) {
        return Optional.ofNullable(data.get(id));
    }

    public boolean deleteUser(UUID id){
        return data.remove(id) != null;
    }

    public User updateUser(User updateUser){
        data.put(updateUser.getId(), updateUser);
        return updateUser;
    }

}
