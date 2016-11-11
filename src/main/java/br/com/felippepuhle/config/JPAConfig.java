package br.com.felippepuhle.config;

import br.com.felippepuhle.util.datatable.DataTableRepositoryImpl;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableJpaRepositories(
        basePackages = {"br.com.felippepuhle"},
        repositoryBaseClass = DataTableRepositoryImpl.class
)
@EnableTransactionManagement
public class JPAConfig {

}
