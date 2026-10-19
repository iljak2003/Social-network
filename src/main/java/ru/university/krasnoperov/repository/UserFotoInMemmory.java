package ru.university.krasnoperov.repository;

import java.util.*;

public class UserFotoInMemmory implements UserFoto {

    private Map<UUID, Map<String, Integer>> fotos = new HashMap<>();

    @Override
    public void addFoto(UUID id, String name) {
        Map<String, Integer> userFoto = fotos.computeIfAbsent(id, k -> new HashMap<>());

        if (!userFoto.containsKey(name)) {
            userFoto.put(name, 0);
        }
    }

    @Override
    public void delFoto(UUID id, String name) {
        Map<String, Integer> userFoto = fotos.get(id);

        if (userFoto != null){
            userFoto.remove(name);
        }
    }

    @Override
    public void rateFoto(UUID id, String name) {
        Map<String, Integer> userFoto = fotos.get(id);

        if (userFoto != null && userFoto.containsKey(name)) {
            userFoto.put(name, userFoto.get(name) + 1);
        }
    }

    @Override
    public Optional<Integer> getLikes(UUID id, String name) {
        Map<String, Integer> userFoto = fotos.get(id);
        if (userFoto == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(userFoto.get(name));
    }

    @Override
    public List<String> getFotos(UUID id) {
        Map<String, Integer> userFoto = fotos.get(id);
        if (userFoto == null) {
            return List.of();
        }
        return userFoto.keySet().stream().toList();
    }
}
