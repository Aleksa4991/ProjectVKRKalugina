package org.urfu.UserService.dtos;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    UUID id;
    String name;
    String email;
    String password;
    int created;
    List<Roles> roles = new ArrayList<>();
    LastSession lastSession;

    public void addRole(Roles role) {
        this.roles.add(role);
    }
}