package org.smart4j.chapter2.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.smart4j.chapter2.helper.DatabaseHelper;
import org.smart4j.chapter2.model.User;

import java.util.List;
import java.util.Map;

public class UserService {
    private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

    public List<User> getUserList() {
        String sql = "SELECT * FROM user";
        return DatabaseHelper.queryEntityList(User.class, sql);
    }

    public User getUser(String username) {
        String sql = "SELECT * FROM user WHERE username = ?";
        return DatabaseHelper.queryEntity(User.class, sql, username);
    }

    public User getUserByNameAndPassWord(String username, String password) {
        String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
        return DatabaseHelper.queryEntity(User.class, sql, username, password);
    }

    public boolean createUser(Map<String, Object> fieldMap) {
        return DatabaseHelper.insertEntity(User.class, fieldMap);
    }

    public boolean updateUser(long id, Map<String, Object> fieldMap) {
        return DatabaseHelper.updateEntity(User.class, id, fieldMap);
    }

    public boolean deleteUser(long id) {
        return DatabaseHelper.deleteEntity(User.class, id);
    }
}
