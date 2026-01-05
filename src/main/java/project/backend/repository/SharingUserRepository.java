package project.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.backend.entity.SharingUser;
import project.backend.repository.custom.SharingUserRepositoryCustom;

import java.util.Optional;

@Repository
public interface SharingUserRepository extends JpaRepository<SharingUser, Long>, SharingUserRepositoryCustom {
    Optional<SharingUser> findByUser_Id(Long userId);
}
