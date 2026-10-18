package ru.university.krasnoperov.service;

import ru.university.krasnoperov.exception.SameIdException;
import ru.university.krasnoperov.exception.UserAlreadyInBlackListException;
import ru.university.krasnoperov.exception.UserNotFoundException;
import ru.university.krasnoperov.exception.UserNotInBlackListException;
import ru.university.krasnoperov.repository.Relations;
import ru.university.krasnoperov.repository.UserRepo;

import java.util.List;
import java.util.UUID;

public class RelationService {

    private final Relations relations;
    private final UserRepo userRepo;



    public RelationService(Relations relations, UserRepo userRepo) {
        this.relations = relations;
        this.userRepo = userRepo;
    }

    private void validate(UUID one, UUID two){
        if (two == null || userRepo.getUserById(two).isEmpty()){
            throw new UserNotFoundException(two);
        }
        if (one == null || userRepo.getUserById(one).isEmpty()){
            throw new UserNotFoundException(one);
        }
        if (one.equals(two)){
            throw new SameIdException(one);
        }
    }

    public void follow(UUID yourId, UUID userTwo){
        validate(yourId, userTwo);
        relations.follow(yourId, userTwo);
    }

    public void unFollow(UUID yourId, UUID userTwo){
        validate(yourId, userTwo);
        relations.unFollow(yourId, userTwo);
    }

    public boolean isFollowers(UUID firstId, UUID secondId){
        validate(firstId, secondId);
        return relations.isSub(firstId, secondId);
    }

    public boolean isFriends(UUID firstId, UUID secondId){
        validate(firstId, secondId);
        return relations.isFriends(firstId, secondId);
    }

    public void removeFromBlackList(UUID yourId, UUID userTwo){
        validate(yourId, userTwo);
        if (!relations.isInBL(yourId, userTwo)){
            throw new UserNotInBlackListException();
        }
        relations.removeFromBlackList(yourId, userTwo);
    }

    public void addToBlackList(UUID yourId, UUID userTwo) {
        validate(yourId, userTwo);
        if (relations.isInBL(yourId, userTwo)){
            throw new UserAlreadyInBlackListException();
        }
        relations.addToBlackList(yourId, userTwo);
    }

    public List<UUID> getFollowers(UUID user){
        if (user == null || userRepo.getUserById(user).isEmpty()){
            throw new UserNotFoundException(user);
        }
        return relations.getFol(user);
    }

    public List<UUID> getBL(UUID user){
        if (user == null || userRepo.getUserById(user).isEmpty()){
            throw new UserNotFoundException(user);
        }
        return relations.getBL(user);
    }




}
