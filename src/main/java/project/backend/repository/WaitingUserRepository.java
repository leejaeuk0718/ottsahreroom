package project.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Controller;
import project.backend.entity.User;
import project.backend.entity.WaitingUser;
import project.backend.enums.OttType;
import project.backend.repository.custom.WaitingUserRepositoryCustom;

import java.util.List;
import java.util.Optional;

@Controller
public interface WaitingUserRepository extends JpaRepository<WaitingUser, Long>, WaitingUserRepositoryCustom {


    Optional<WaitingUser> findByUser_Id(Long userId);

}
