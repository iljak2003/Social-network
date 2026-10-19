package ru.university.krasnoperov.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserFoto {

    void addFoto(UUID id, String name);

    void delFoto(UUID id, String name);

    void rateFoto(UUID id, String name);

    Optional<Integer> getLikes(UUID id, String name);

    List<String> getFotos(UUID id);


}
