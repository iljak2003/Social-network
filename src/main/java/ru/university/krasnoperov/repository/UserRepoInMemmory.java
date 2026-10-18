package ru.university.krasnoperov.repository;

import ru.university.krasnoperov.model.User;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class UserRepoInMemmory implements UserRepo{

    private final Map<UUID, User> data;

    public UserRepoInMemmory(Map<UUID, User> data) {
        this.data = data;
    }

    public UserRepoInMemmory() {
        this.data = new HashMap<>();
    }

    @Override
    public User addUser(User newUser){
        data.put(newUser.getId(), newUser);
        return newUser;
    }

    @Override
    public Optional<User> getUserById(UUID id) {
        return Optional.ofNullable(data.get(id));
    }

    @Override
    public boolean deleteUser(UUID id){
        return data.remove(id) != null;
    }

    @Override
    public User updateUser(User updateUser){
        data.put(updateUser.getId(), updateUser);
        return updateUser;
    }

    @Override
    public Map<UUID, User> getData(){
        return data;
    }

}
