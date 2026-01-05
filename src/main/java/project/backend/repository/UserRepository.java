package project.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.backend.entity.User;
import project.backend.repository.custom.UserRepositoryCustom;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long>, UserRepositoryCustom {


    Optional<User> findByNameAndPhoneNumber(String name, String phoneNumber);

    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);

    boolean existsByNickname(String nickname);


}
