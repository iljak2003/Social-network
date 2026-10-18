package ru.university.krasnoperov.repository;

import java.util.Map;
import java.util.HashMap;
import java.util.Optional;

public class UserFotoInMemmory implements UserFoto{

    private Map<String, Integer> fotos = new HashMap<>();

    @Override
    public void addFoto(String name) {
        if (!fotos.containsKey(name)){
            fotos.put(name, 0);
        }

    }

    @Override
    public void delFoto(String name) {
        fotos.remove(name);
    }

    @Override
    public void rateFoto(String name) {
        if (fotos.containsKey(name)){
            fotos.put(name, fotos.get(name) + 1);
        }
    }

    @Override
    public Optional<Integer> getLikes(String name) {
        return Optional.ofNullable(fotos.get(name));
    }
}
