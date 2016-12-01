package br.com.felippepuhle.controller;

import br.com.felippepuhle.repository.UserGroupRepository;
import br.com.felippepuhle.util.datatable.DataTableRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import static org.springframework.http.ResponseEntity.ok;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserGroupController {

    @Autowired
    private UserGroupRepository userGroupRepository;

    @RequestMapping(path = "/admin/groups", method = RequestMethod.POST)
    public ResponseEntity<?> list(Pageable pageable, @RequestBody DataTableRequest dataTableRequest) {
        return ok(userGroupRepository.findAll(pageable, dataTableRequest));
    }
}
