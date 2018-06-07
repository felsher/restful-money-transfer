package my.restful.homework.moneytransfer.entity;

public class User extends Entity {

    //TODO if I want to use this fields, it would be great to create USER table in database, but no time for it, so let's just left it for now

//    private String firstName;

//    private String lastName;

    public User(/*String firstName, String lastName*/) {
        super(null);
//        this.firstName = firstName;
//        this.lastName = lastName;
    }

    public User(Long id/*, String firstName, String lastName*/) {
        super(id);
//        this.firstName = firstName;
//        this.lastName = lastName;
    }

//    public String getFirstName() {
//        return firstName;
//    }

//    public String getLastName() {
//        return lastName;
//    }
}
