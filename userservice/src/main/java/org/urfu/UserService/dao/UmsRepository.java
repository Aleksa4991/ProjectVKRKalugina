package org.urfu.UserService.dao;

import java.util.Map;
import java.util.UUID;

import org.urfu.UserService.dtos.Roles;
import org.urfu.UserService.dtos.User;

public interface UmsRepository {

    Map<UUID, User> findAllUsers();

    Map<String, Roles> findAllRoles();

    User findUserByID(UUID userId);

    UUID createUser(User user);

	int deleteUser(UUID userId);
}
