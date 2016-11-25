package br.com.felippepuhle.repository;

import br.com.felippepuhle.model.UserGroup;
import br.com.felippepuhle.util.datatable.DataTableRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGroupRepository extends CrudRepository<UserGroup, Long>, DataTableRepository<UserGroup, Long> {
    
}
