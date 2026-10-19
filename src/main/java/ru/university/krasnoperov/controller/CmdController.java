package ru.university.krasnoperov.controller;

import ru.university.krasnoperov.model.User;
import ru.university.krasnoperov.service.FotoService;
import ru.university.krasnoperov.service.ProfileSevice;
import ru.university.krasnoperov.service.RelationService;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.UUID;

public class CmdController {

    private final ProfileSevice profileService;
    private final RelationService relationService;
    private final FotoService fotoService;
    private final Scanner scanner;

    public CmdController(ProfileSevice profileService, RelationService relationService, FotoService fotoService, Scanner scanner) {
        this.profileService = profileService;
        this.relationService = relationService;
        this.fotoService = fotoService;
        this.scanner = scanner;
    }

    public void run() {
        while (true) {
            System.out.println("\n=== СОЦИАЛЬНАЯ СЕТЬ — МЕНЮ ===");
            System.out.println("1. Создать пользователя");
            System.out.println("2. Найти пользователя по ID");
            System.out.println("3. Обновить данные профиля");
            System.out.println("4. Удалить пользователя");
            System.out.println("5. Подписаться / Отписаться");
            System.out.println("6. Проверить статус (Подписка / Дружба) и список подписок");
            System.out.println("7. Черный список (Добавить / Удалить / Посмотреть)");
            System.out.println("8. Фотографии (Добавить / Удалить / Посмотреть альбом)");
            System.out.println("9. Оценить фото / Посмотреть лайки");
            System.out.println("0. Выход");
            System.out.print("Выберите действие: ");

            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        createUser();
                        break;
                    case "2":
                        getById();
                        break;
                    case "3":
                        updateUser();
                        break;
                    case "4":
                        deleteUser();
                        break;
                    case "5":
                        followMenu();
                        break;
                    case "6":
                        checkRelationsMenu();
                        break;
                    case "7":
                        blackListMenu();
                        break;
                    case "8":
                        fotoManageMenu();
                        break;
                    case "9":
                        fotoLikesMenu();
                        break;
                    case "0":
                        System.out.println("Выход из программы. Пока!");
                        scanner.close();
                        return;
                    default:
                        System.out.println("Неверный ввод. Попробуйте еще раз.");
                }
            } catch (Exception e) {
                System.out.println("Возникла ошибка: " + e.getMessage());
            }
        }
    }

    private void createUser() {
        System.out.print("Введите имя: ");
        String name = scanner.nextLine();
        System.out.print("Введите пол (true - мужской, false - женский): ");
        boolean sex = Boolean.parseBoolean(scanner.nextLine());
        System.out.print("Введите возраст: ");
        int age = Integer.parseInt(scanner.nextLine());
        System.out.print("Введите знак зодиака: ");
        String zodiacSign = scanner.nextLine();

        User created = profileService.createUser(name, sex, age, zodiacSign);
        System.out.println("Создан пользователь: " + formatUser(created));
    }

    private void getById() {
        System.out.print("Введите id пользователя: ");
        String rawId = scanner.nextLine();

        try {
            UUID id = UUID.fromString(rawId);
            Optional<User> userBox = profileService.getUserById(id);

            if (userBox.isPresent()) {
                User user = userBox.get();
                System.out.println(formatUser(user));
            } else {
                System.out.println("Пользователя с таким идентификатором не найдено");
            }
        } catch (Exception e) {
            System.out.println("Неверно задан идентификатор");
        }
    }

    private void updateUser() {
        UUID id = readUuid("Введите идентификатор пользователя: ");
        System.out.println("Что обновить? (1 - Имя, 2 - Возраст, 3 - Пол, 4 - Знак зодиака): ");
        String subChoice = scanner.nextLine();

        switch (subChoice) {
            case "1":
                System.out.print("Введите новое имя: ");
                User uName = profileService.updateName(id, scanner.nextLine());
                System.out.println("Обновлено: " + formatUser(uName));
                break;
            case "2":
                System.out.print("Введите новый возраст: ");
                User uAge = profileService.updateAge(id, Integer.parseInt(scanner.nextLine()));
                System.out.println("Обновлено: " + formatUser(uAge));
                break;
            case "3":
                System.out.print("Введите новый пол (true/false): ");
                User uSex = profileService.updateSex(id, Boolean.parseBoolean(scanner.nextLine()));
                System.out.println("Обновлено: " + formatUser(uSex));
                break;
            case "4":
                System.out.print("Введите новый знак зодиака: ");
                User uZodiac = profileService.updateZodiacSign(id, scanner.nextLine());
                System.out.println("Обновлено: " + formatUser(uZodiac));
                break;
            default:
                System.out.println("Неверный выбор пункта.");
        }
    }

    private void deleteUser() {
        UUID id = readUuid("Введите идентификатор пользователя для удаления: ");
        profileService.deleteUser(id);
        System.out.println("Пользователь удален.");
    }

    private void followMenu() {
        System.out.print("Выберите действие (1 - Подписаться, 2 - Отписаться): ");
        String action = scanner.nextLine();

        switch (action) {
            case "1":
                UUID yourId1 = readUuid("Введите ваш ID: ");
                UUID targetId1 = readUuid("Введите ID второго пользователя: ");
                relationService.follow(yourId1, targetId1);
                System.out.println("Вы успешно подписались.");
                break;

            case "2":
                UUID yourId2 = readUuid("Введите ваш ID: ");
                UUID targetId2 = readUuid("Введите ID второго пользователя: ");
                relationService.unFollow(yourId2, targetId2);
                System.out.println("Вы отписались.");
                break;

            default:
                System.out.println("Неверный ввод.");
        }
    }

    private void checkRelationsMenu() {
        System.out.print("Выберите (1 - Проверить подписку, 2 - Проверить дружбу, 3 - Список подписок): ");
        String action = scanner.nextLine();

        switch (action) {
            case "1":
                UUID firstId1 = readUuid("Введите первый ID: ");
                UUID secondId1 = readUuid("Введите второй ID: ");
                System.out.println("Статус подписки: " + relationService.isFollowers(firstId1, secondId1));
                break;

            case "2":
                UUID firstId2 = readUuid("Введите первый ID: ");
                UUID secondId2 = readUuid("Введите второй ID: ");
                System.out.println("Статус дружбы: " + relationService.isFriends(firstId2, secondId2));
                break;

            case "3":
                UUID userId = readUuid("Введите ID пользователя: ");
                List<UUID> subs = relationService.getFollowers(userId);
                System.out.println("Список: " + subs);
                break;

            default:
                System.out.println("Неверный ввод.");
        }
    }

    private void blackListMenu() {
        System.out.print("Выберите (1 - Добавить в ЧС, 2 - Убрать из ЧС, 3 - Показать ЧС): ");
        String action = scanner.nextLine();

        switch (action) {
            case "1":
                UUID yourId1 = readUuid("Введите ваш ID: ");
                UUID targetId1 = readUuid("Введите ID пользователя для блокировки: ");
                relationService.addToBlackList(yourId1, targetId1);
                System.out.println("Пользователь добавлен в ЧС.");
                break;

            case "2":
                UUID yourId2 = readUuid("Введите ваш ID: ");
                UUID targetId2 = readUuid("Введите ID пользователя для разблокировки: ");
                relationService.removeFromBlackList(yourId2, targetId2);
                System.out.println("Пользователь удален из ЧС.");
                break;

            case "3":
                UUID userId = readUuid("Введите ID пользователя: ");
                System.out.println("Черный список: " + relationService.getBL(userId));
                break;

            default:
                System.out.println("Неверный ввод.");
        }
    }

    private void fotoManageMenu() {
        System.out.print("Выберите (1 - Добавить фото, 2 - Удалить фото, 3 - Посмотреть фото пользователя): ");
        String action = scanner.nextLine();

        switch (action) {
            case "1":
                UUID id1 = readUuid("Введите ваш ID: ");
                System.out.print("Введите название фото: ");
                fotoService.addFoto(id1, scanner.nextLine());
                System.out.println("Фото добавлено.");
                break;

            case "2":
                UUID id2 = readUuid("Введите ваш ID: ");
                System.out.print("Введите название фото: ");
                fotoService.deleteFoto(id2, scanner.nextLine());
                System.out.println("Фото удалено.");
                break;

            case "3":
                UUID urId = readUuid("Введите ваш ID (кто смотрит): ");
                UUID targetId = readUuid("Введите ID владельца фото: ");
                List<String> fotos = fotoService.getFotos(urId, targetId);
                System.out.println("Фотографии пользователя: " + fotos);
                break;

            default:
                System.out.println("Неверный ввод.");
        }
    }

    private void fotoLikesMenu() {
        System.out.print("Выберите (1 - Оценить фото, 2 - Посмотреть лайки фото): ");
        String action = scanner.nextLine();

        switch (action) {
            case "1":
                UUID urId1 = readUuid("Введите ваш ID: ");
                UUID targetId1 = readUuid("Введите ID владельца фото: ");
                System.out.print("Введите название фото: ");
                String fotoName1 = scanner.nextLine();

                fotoService.rateFoto(urId1, targetId1, fotoName1);
                System.out.println("Лайк поставлен!");
                break;

            case "2":
                UUID urId2 = readUuid("Введите ваш ID: ");
                UUID targetId2 = readUuid("Введите ID владельца фото: ");
                System.out.print("Введите название фото: ");
                String fotoName2 = scanner.nextLine();

                Optional<Integer> likesBox = fotoService.getFotoLikes(urId2, targetId2, fotoName2);

                if (likesBox.isPresent()) {
                    Integer likes = likesBox.get();
                    System.out.println("Лайков у фото: " + likes);
                } else {
                    System.out.println("Фото с таким названием не найдено");
                }
                break;

            default:
                System.out.println("Неверный ввод.");
        }
    }

    private UUID readUuid(String message) {
        while (true) {
            System.out.print(message);
            String rawId = scanner.nextLine();
            try {
                return UUID.fromString(rawId);
            } catch (Exception e) {
                System.out.println("Неверно задан идентификатор. Введите заново");
            }
        }
    }

    private String formatUser(User user) {
        return String.format("id = %s, name = %s, age = %d, sex = %s, zodiac = %s",
                user.getId(), user.getName(), user.getAge(), user.getSex(), user.getZodiacSign());
    }
}