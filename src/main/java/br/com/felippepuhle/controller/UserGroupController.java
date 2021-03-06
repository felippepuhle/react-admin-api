package br.com.felippepuhle.controller;

import br.com.felippepuhle.model.UserGroup;
import br.com.felippepuhle.repository.UserGroupRepository;
import br.com.felippepuhle.util.datatable.DataTableRequest;
import br.com.felippepuhle.vo.UserGroupVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import static org.springframework.http.ResponseEntity.notFound;
import static org.springframework.http.ResponseEntity.ok;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserGroupController {

    @Autowired
    private UserGroupRepository userGroupRepository;

    @RequestMapping(path = "/admin/groups/{id}", method = RequestMethod.GET)
    public ResponseEntity<?> show(@PathVariable("id") Long id) {
        return ok(userGroupRepository.findOne(id));
    }

    @RequestMapping(path = "/admin/groups/create", method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody final UserGroupVO userGroupVO) {
        UserGroup userGroup = userGroupVO.toModel();

        userGroupRepository.save(userGroup);

        return ok(new UserGroupVO(userGroup));
    }

    @RequestMapping(path = "/admin/groups", method = RequestMethod.POST)
    public ResponseEntity<?> list(Pageable pageable, @RequestBody DataTableRequest dataTableRequest) {
        return ok(userGroupRepository.findAll(pageable, dataTableRequest));
    }

    @RequestMapping(path = "/admin/groups/{id}", method = RequestMethod.PUT)
    public ResponseEntity<?> update(@RequestBody final UserGroupVO userGroupVO) {
        UserGroup userGroup = userGroupRepository.findOne(userGroupVO.getId());
        if (userGroup == null) {
            return notFound().build();
        }

        userGroupVO.toModel(userGroup);

        userGroupRepository.save(userGroup);

        return ok(userGroupVO);
    }

    @RequestMapping(path = "/admin/groups/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<?> delete(@PathVariable Long id) {
        UserGroup userGroup = userGroupRepository.findOne(id);
        if (userGroup == null) {
            return notFound().build();
        }

        userGroupRepository.delete(userGroup);

        return ok(new UserGroupVO(userGroup));
    }

}
