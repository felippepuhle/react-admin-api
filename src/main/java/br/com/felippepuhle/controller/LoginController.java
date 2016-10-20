package br.com.felippepuhle.controller;

import br.com.felippepuhle.config.JWTConfig;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

import br.com.felippepuhle.model.User;
import br.com.felippepuhle.repository.UserRepository;
import br.com.felippepuhle.vo.LoginAttemptVO;
import br.com.felippepuhle.vo.LoginResponseVO;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/login")
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody final LoginAttemptVO login) {
        User user = userRepository.findByLoginAndPassword(login.getLogin(), login.getPassword());
        if (user == null) {
            return notFound().build();
        }

        return ok(new LoginResponseVO(
                Jwts.builder()
                .setSubject(user.getLogin())
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256, JWTConfig.JWT_KEY)
                .compact()
        ));
    }
}
