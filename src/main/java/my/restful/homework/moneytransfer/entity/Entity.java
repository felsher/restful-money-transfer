package my.restful.homework.moneytransfer.entity;

/**
 * Base class for all entities in the application
 */
public abstract class Entity {

    private String uuid;

    public Entity(String uuid) {
        this.uuid = uuid;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }
}
