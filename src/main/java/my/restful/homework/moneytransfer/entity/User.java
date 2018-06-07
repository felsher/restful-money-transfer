package my.restful.homework.moneytransfer.entity;

import java.util.UUID;

public class User extends Entity {

    private String firstName;

    private String lastName;

    public User(String firstName, String lastName) {
        this(UUID.randomUUID().toString(), firstName, lastName);
    }

    public User(String id, String firstName, String lastName) {
        super(id);
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public User setFirstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public String getLastName() {
        return lastName;
    }

    public User setLastName(String lastName) {
        this.lastName = lastName;
        return this;
    }
}
