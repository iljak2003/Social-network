package ru.university.krasnoperov.service;

import ru.university.krasnoperov.exception.UserNotFoundException;
import ru.university.krasnoperov.model.User;
import ru.university.krasnoperov.repository.Relations;
import ru.university.krasnoperov.repository.UserRepo;

import java.util.Optional;
import java.util.UUID;

public class ProfileSevice {
    private final UserRepo userRepo;
    private final Relations relations;

    public ProfileSevice(UserRepo userRepo, Relations relations) {
        this.userRepo = userRepo;
        this.relations = relations;
    }


    public User createUser(String name, boolean sex, int age, String zodiacSign){
        User user = new User(name, sex, age, zodiacSign);
        relations.addUser(user.getId());
        return userRepo.addUser(user);
    }

    public void deleteUser(UUID id){
        if (!userRepo.deleteUser(id)){
            throw new UserNotFoundException(id);
        }
        relations.removeUser(id);
    }

    private User validate(UUID id){
        if(id == null || getUserById(id).isEmpty()){
            throw new UserNotFoundException(id);
        }
        return userRepo.getUserById(id).get();
    }

    public User updateName(UUID id, String name){
        User user = validate(id);
        user.setName(name);
        return userRepo.updateUser(user);
    }

    public User updateAge(UUID id, int age){
        User user = validate(id);
        user.setAge(age);
        return userRepo.updateUser(user);
    }

    public User updateSex(UUID id, boolean sex){
        User user = validate(id);
        user.setSex(sex);
        return userRepo.updateUser(user);
    }

    public User updateZodiacSign(UUID id, String zodiacSign){
        User user = validate(id);
        user.setZodiacSign(zodiacSign);
        return userRepo.updateUser(user);
    }

    public Optional<User> getUserById(UUID id) {
        return userRepo.getUserById(id);
    }

}
