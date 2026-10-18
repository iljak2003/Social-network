package ru.university.krasnoperov.repository;

import java.util.Optional;

public interface UserFoto {

    void addFoto(String name);

    void delFoto(String name);

    void rateFoto(String name);

    Optional<Integer> getLikes(String name);


}
