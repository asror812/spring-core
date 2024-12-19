package com.example.demo.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import java.util.UUID;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class User extends BaseEntity {

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private boolean active;

    public User(UUID userId, String firstName, String lastName, String username, String password, boolean active) {
        super(userId);
        this.firstName = firstName;
        this.lastName = lastName;
        this.username = username;
        this.password = password;
        this.active = active;
    }
}
