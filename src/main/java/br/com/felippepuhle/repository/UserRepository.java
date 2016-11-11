package br.com.felippepuhle.repository;

import br.com.felippepuhle.model.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import br.com.felippepuhle.util.datatable.DataTableRepository;

@Repository
public interface UserRepository extends CrudRepository<User, Long>, DataTableRepository<User, Long> {

    User findByLogin(@Param("login") String login);
}
