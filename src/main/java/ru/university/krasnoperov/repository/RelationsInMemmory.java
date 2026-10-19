package ru.university.krasnoperov.repository;

import ru.university.krasnoperov.model.User;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class RelationsInMemmory implements Relations {

    private final Graph blackList;
    private final Graph friendsAndSubs;

    public RelationsInMemmory(UserRepo users) {
        blackList = new Graph();
        friendsAndSubs = new Graph();
        for (Map.Entry<UUID, User> entry : users.getData().entrySet()) {
            friendsAndSubs.addVertex(entry.getKey());
            blackList.addVertex(entry.getKey());
        }
    }

    @Override
    public void addUser(UUID id) {
        friendsAndSubs.addVertex(id);
        blackList.addVertex(id);
    }

    @Override
    public void removeUser(UUID id) {
        friendsAndSubs.removeVertex(id);
        blackList.removeVertex(id);
    }

    @Override
    public void follow(UUID yourId, UUID idToFollow) {
        friendsAndSubs.addEdge(yourId, idToFollow);
    }

    @Override
    public void unFollow(UUID yourId, UUID idToUnfollow) {
        friendsAndSubs.removeEdge(yourId, idToUnfollow);
    }

    @Override
    public void addToBlackList(UUID yourId, UUID idToAddToBL) {
        blackList.addEdge(yourId, idToAddToBL);
        unFollow(yourId, idToAddToBL);
    }

    @Override
    public void removeFromBlackList(UUID yourId, UUID idToRemFrBL) {
        blackList.removeEdge(yourId, idToRemFrBL);
    }

    private boolean isIn(UUID id, List<UUID> list) {
        for (UUID pointer : list) {
            if (pointer.equals(id)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isInBL(UUID firstId, UUID secondId) {
        return isIn(secondId, blackList.getNeighbors(firstId));
    }

    @Override
    public boolean isFriends(UUID firstId, UUID secondId) {
        if (firstId == null || secondId == null || firstId.equals(secondId)) {
            return false;
        }
        return (isIn(firstId, friendsAndSubs.getNeighbors(secondId))) && (isIn(secondId, friendsAndSubs.getNeighbors(firstId)));
    }

    @Override
    public boolean isSub(UUID firstId, UUID secondId) {
        if (firstId == null || secondId == null || firstId.equals(secondId)) {
            return false;
        }
        if (isFriends(firstId, secondId)) {
            return false;
        }
        return isIn(secondId, friendsAndSubs.getNeighbors(firstId));
    }

    @Override
    public List<UUID> getBL(UUID user) {
        return blackList.getNeighbors(user);
    }

    @Override
    public List<UUID> getFol(UUID user) {
        return friendsAndSubs.getNeighbors(user);
    }
}
