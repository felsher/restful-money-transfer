package my.restful.homework.moneytransfer.dao;

public class H2DbConfig {

    //TODO Good practice is to move this fields into properties file, but we need to create uber jar after that, if we want only one jar file without any extra files
    static final String H2_DB_CONNECTION = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    static final String DB_USER = "user";
    static final String DB_PASSWORD = "password";

}
