package ru.university.krasnoperov.service;

import ru.university.krasnoperov.exception.CantGetPhotosException;
import ru.university.krasnoperov.exception.CantRateException;
import ru.university.krasnoperov.exception.CantViewRatingException;
import ru.university.krasnoperov.exception.UserNotFoundException;
import ru.university.krasnoperov.repository.Relations;
import ru.university.krasnoperov.repository.UserFoto;
import ru.university.krasnoperov.repository.UserRepo;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class FotoService {

    private final UserRepo userRepo;
    private final Relations relations;
    private final UserFoto userFoto;

    public FotoService(UserRepo userRepo, Relations relations, UserFoto userFoto) {
        this.userRepo = userRepo;
        this.relations = relations;
        this.userFoto = userFoto;
    }

    private void validate(UUID id){
        if (id == null || userRepo.getUserById(id).isEmpty()){
            throw new UserNotFoundException(id);
        }
    }

    private boolean canView(UUID viewerId, UUID targetId){
        return (viewerId.equals(targetId) || relations.isSub(viewerId, targetId) || relations.isFriends(viewerId, targetId))
                && !(relations.isInBL(viewerId, targetId) || relations.isInBL(targetId, viewerId));
    }

    public void addFoto(UUID id, String fotoName){
        validate(id);
        userFoto.addFoto(id, fotoName);
    }

    public void deleteFoto(UUID id, String fotoName){
        validate(id);
        userFoto.delFoto(id, fotoName);
    }

    public void rateFoto(UUID urId, UUID targetId, String fotoName){
        validate(urId);
        validate(targetId);

        if (!canView(urId, targetId) || !relations.isFriends(urId, targetId)
                || relations.isInBL(urId, targetId) || relations.isInBL(targetId, urId)){
            throw new CantRateException(targetId);
        }

        userFoto.rateFoto(targetId, fotoName);

    }

    public Optional<Integer> getFotoLikes(UUID urId, UUID targetId, String fotoName){
        validate(urId);
        validate(targetId);

        if (!canView(urId, targetId) || !relations.isFriends(urId, targetId) || relations.isInBL(urId, targetId)
                || relations.isInBL(targetId, urId)){
            throw new CantViewRatingException(targetId);
        }

        return userFoto.getLikes(targetId, fotoName);
    }

    public List<String> getFotos(UUID urId, UUID targetId){
        validate(urId);
        validate(targetId);

        if (!canView(urId, targetId) || relations.isInBL(urId, targetId)
                || relations.isInBL(targetId, urId)){
            throw new CantGetPhotosException(targetId);
        }

        return userFoto.getFotos(targetId);
    }
}
