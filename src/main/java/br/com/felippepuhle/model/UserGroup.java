package br.com.felippepuhle.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class UserGroup {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    private UserGroupLevel level;

    public UserGroup() {

    }

    public UserGroup(String name, UserGroupLevel level) {
        this.name = name;
        this.level = level;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public UserGroupLevel getLevel() {
        return level;
    }

}
