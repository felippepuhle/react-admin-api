package br.com.felippepuhle.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import org.mindrot.jbcrypt.BCrypt;

@Entity
public class User {

    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private UserGroup group;

    private String name;
    private String email;
    private String login;

    @JsonIgnore
    private final String salt;
    @JsonIgnore
    private String password;

    public User() {
        this.salt = BCrypt.gensalt();
    }

    public User(UserGroup group, String name, String email, String login, String password) {
        this.group = group;
        this.name = name;
        this.email = email;
        this.login = login;
        this.salt = BCrypt.gensalt();
        this.password = BCrypt.hashpw(password, this.salt);
    }

    public Long getId() {
        return id;
    }

    public UserGroup getGroup() {
        return group;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setPassword(String password) {
        this.password = BCrypt.hashpw(password, this.salt);
    }

    public Boolean authenticate(String password) {
        return BCrypt.checkpw(password, this.password);
    }

}
