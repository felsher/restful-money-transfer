package my.restful.homework.moneytransfer.entity;

/**
 * Base class for all entities
 */
public abstract class Entity {

    private Long id; //I was thinking.. maybe I have to use for example UUID here? But then - "Ok, Not now.. Long id is fine"

    public Entity(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
