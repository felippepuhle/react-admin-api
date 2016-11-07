package br.com.felippepuhle.controller;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

import br.com.felippepuhle.model.User;
import br.com.felippepuhle.repository.UserRepository;
import br.com.felippepuhle.vo.LoginVO;
import br.com.felippepuhle.vo.ProfileVO;
import br.com.felippepuhle.vo.TokenVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import static org.springframework.http.ResponseEntity.ok;
import static org.springframework.http.ResponseEntity.ok;

@RestController
public class AdminController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(path = "/admin/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody final LoginVO login) {
        User user = userRepository.findByLoginAndPassword(login.getLogin(), login.getPassword());
        if (user == null) {
            return notFound().build();
        }

        return ok(new TokenVO(user));
    }

    @RequestMapping(path = "/admin/profile", method = RequestMethod.POST)
    public ResponseEntity<?> profile(@RequestBody final ProfileVO profile) {
        User user = userRepository.findOne(profile.getId());
        if (user == null) {
            return notFound().build();
        }

        profile.toModel(user);

        userRepository.save(user);

        return ok(new TokenVO(user));
    }

}
