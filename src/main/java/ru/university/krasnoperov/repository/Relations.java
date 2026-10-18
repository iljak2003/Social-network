package ru.university.krasnoperov.repository;

import java.util.List;
import java.util.UUID;

public interface Relations {

    void removeUser(UUID id);

    void addUser(UUID id);

    void follow(UUID yourId, UUID idToFollow);

    void unFollow(UUID yourId, UUID idToUnfollow);

    void addToBlackList(UUID yourId, UUID idToAddToBL);

    void removeFromBlackList(UUID yourId, UUID idToRemFrBL);

    boolean isInBL(UUID firstId, UUID secondId);

    boolean isFriends(UUID firstId, UUID secondId);

    boolean isSub(UUID firstId, UUID secondId);

    public List<UUID> getFol(UUID user);

    public List<UUID> getBL(UUID user);

}
