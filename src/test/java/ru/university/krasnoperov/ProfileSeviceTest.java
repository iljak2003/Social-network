package ru.university.krasnoperov;

import org.junit.jupiter.api.Test;
import ru.university.krasnoperov.exception.UserNotFoundException;
import ru.university.krasnoperov.model.User;
import ru.university.krasnoperov.repository.RelationsInMemmory;
import ru.university.krasnoperov.repository.UserRepoInMemmory;
import ru.university.krasnoperov.service.ProfileSevice;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class ProfileSeviceTest {

    @Test
    void testCreateAndValidateUser() {
        UserRepoInMemmory userRepo = new UserRepoInMemmory();
        RelationsInMemmory relations = new RelationsInMemmory(userRepo);
        ProfileSevice service = new ProfileSevice(userRepo, relations);

        User user = service.createUser("Иван", true, 25, "Овен");
        assertNotNull(user.getId());

        assertTrue(service.getUserById(user.getId()).isPresent());

        assertThrows(UserNotFoundException.class, () -> {
            service.updateName(UUID.randomUUID(), "Новое имя");
        });
    }
}