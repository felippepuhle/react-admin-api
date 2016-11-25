package br.com.felippepuhle.util;

import br.com.felippepuhle.model.UserGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import br.com.felippepuhle.model.User;
import br.com.felippepuhle.model.UserGroupLevel;
import br.com.felippepuhle.repository.UserRepository;
import br.com.felippepuhle.repository.UserGroupRepository;

@Component
public class DatabaseLoader implements CommandLineRunner {

    @Autowired
    private UserGroupRepository userGroupRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void run(String... arg0) throws Exception {
        UserGroup admins = new UserGroup("Admins", UserGroupLevel.SUPER_ADMIN);
        this.userGroupRepository.save(admins);
        this.userRepository.save(new User(admins, "Felippe Rodrigo Puhle", "felippe.puhle@gmail.com", "felippe", "123456"));

        UserGroup users = new UserGroup("Users", UserGroupLevel.USER);
        this.userGroupRepository.save(users);
        for (int index = 1; index < 1000; index++) {
            this.userRepository.save(new User(users, "User " + index, "user." + index + "@gmail.com", "user" + index, "123456"));
        }
    }

}
