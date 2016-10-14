package br.com.felippepuhle.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import br.com.felippepuhle.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
