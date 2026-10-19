package ru.university.krasnoperov;

import ru.university.krasnoperov.controller.CmdController;
import ru.university.krasnoperov.model.User;
import ru.university.krasnoperov.repository.Relations;
import ru.university.krasnoperov.repository.RelationsInMemmory;
import ru.university.krasnoperov.repository.UserFoto;
import ru.university.krasnoperov.repository.UserFotoInMemmory;
import ru.university.krasnoperov.repository.UserRepo;
import ru.university.krasnoperov.repository.UserRepoInMemmory;
import ru.university.krasnoperov.service.FotoService;
import ru.university.krasnoperov.service.ProfileSevice;
import ru.university.krasnoperov.service.RelationService;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.UUID;

public class Main {
    public static void main(String[] args) {

        Map<UUID, User> userMap = new HashMap<>();
        UserRepo userRepo = new UserRepoInMemmory(userMap);
        Relations relations = new RelationsInMemmory(userRepo);
        UserFoto userFoto = new UserFotoInMemmory();

        ProfileSevice profileService = new ProfileSevice(userRepo, relations);
        RelationService relationService = new RelationService(relations, userRepo);
        FotoService fotoService = new FotoService(userRepo, relations, userFoto);

        Scanner scanner = new Scanner(System.in);
        CmdController cmdController = new CmdController(profileService, relationService, fotoService, scanner);
        cmdController.run();
    }
}