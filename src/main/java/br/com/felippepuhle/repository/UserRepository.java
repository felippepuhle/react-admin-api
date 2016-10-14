package br.com.felippepuhle.repository;


import br.com.felippepuhle.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    User findByLoginAndPassword(@Param("login") String login, @Param("password") String password);
}
