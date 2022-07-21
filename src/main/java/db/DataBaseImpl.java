package db;

import java.util.Collection;
import java.util.Map;

import com.google.common.collect.Maps;

import model.User;
import service.DataBase;

public class DataBaseImpl implements DataBase {
    private static final Map<String, User> users = Maps.newHashMap();

    public void addUser(User user) {
        users.put(user.getUserId(), user);
    }

    public User findUserById(String userId) {
        return users.get(userId);
    }

    public Collection<User> findAll() {
        return users.values();
    }
}
