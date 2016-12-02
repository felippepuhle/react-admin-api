package br.com.felippepuhle.vo;

import br.com.felippepuhle.model.UserGroup;
import br.com.felippepuhle.model.UserGroupLevel;

public class UserGroupVO {

    private Long id;
    private String name;
    private String level;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLevel() {
        return level;
    }

    public UserGroup toModel() {
        return toModel(new UserGroup());
    }

    public UserGroup toModel(UserGroup userGroup) {
        userGroup.setName(name);
        userGroup.setLevel(UserGroupLevel.valueOf(level));
        
        return userGroup;
    }

}
