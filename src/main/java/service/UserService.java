package service;

import model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class UserService {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);
    private final DataBase dataBase;

    public UserService(final DataBase dataBase) {
        this.dataBase = dataBase;
    }

    public void joinUser(final User user) {
        log.info("Join User and Save Database, User is {}", user.getName());
        dataBase.addUser(user);
    }

    public User findUserById(final String userId) {
        return dataBase.findUserById(userId);
    }

    public List<User> findUsers() {
        return new ArrayList<>(dataBase.findAll());
    }

}
