package service;

import model.User;

import java.util.Collection;

public interface DataBase {

    void addUser(final User user);

    User findUserById(final String userId);

    Collection<User> findAll();
}
