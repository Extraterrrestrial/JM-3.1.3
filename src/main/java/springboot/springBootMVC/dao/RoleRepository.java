package springboot.springBootMVC.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;
import springboot.springBootMVC.model.Role;

//Аннотация @EnableJpaRepositories активирует Spring Data JPA.
// Spring Data JPA будет создавать конкретную реализацию для PersonRepository и настраивать на взаимодействие
// с БД в памяти, используя JPA.
@EnableJpaRepositories
public interface RoleRepository extends JpaRepository<Role,Long> {
    Role findByName(String role);
}

