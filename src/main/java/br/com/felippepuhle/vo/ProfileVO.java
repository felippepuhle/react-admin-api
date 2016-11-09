package br.com.felippepuhle.vo;

import br.com.felippepuhle.model.User;

public class ProfileVO {

    private Long id;
    private String name;
    private String email;
    private String login;
    private String password;

    public Long getId() {
        return id;
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

    public User toModel() {
        return toModel(new User());
    }

    public User toModel(User user) {
        user.setName(name);
        user.setEmail(email);
        user.setLogin(login);

        if (password != null && password.length() > 0) {
            user.setPassword(password);
        }

        return user;
    }
}
