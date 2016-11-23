package br.com.felippepuhle.controller;

import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;

import br.com.felippepuhle.model.User;
import br.com.felippepuhle.repository.UserRepository;
import br.com.felippepuhle.util.datatable.DataTableRequest;
import br.com.felippepuhle.vo.LoginVO;
import br.com.felippepuhle.vo.ProfileVO;
import br.com.felippepuhle.vo.TokenVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @RequestMapping(path = "/admin/login", method = RequestMethod.POST)
    public ResponseEntity<?> login(@RequestBody final LoginVO loginVO) {
        User user = userRepository.findByLogin(loginVO.getLogin());
        if (user == null || !user.authenticate(loginVO.getPassword())) {
            return notFound().build();
        }

        return ok(new TokenVO(user));
    }

    @RequestMapping(path = "/admin/profile", method = RequestMethod.POST)
    public ResponseEntity<?> profile(@RequestBody final ProfileVO profileVO) {
        User user = userRepository.findOne(profileVO.getId());
        if (user == null) {
            return notFound().build();
        }

        profileVO.toModel(user);

        userRepository.save(user);

        return ok(new TokenVO(user));
    }

    @RequestMapping(path = "/admin/users", method = RequestMethod.POST)
    public ResponseEntity<?> list(Pageable pageable, @RequestBody DataTableRequest dataTableRequest) {
        return ok(userRepository.findAll(pageable, dataTableRequest));
    }
}
