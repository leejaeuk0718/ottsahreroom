package project.backend.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.backend.entity.Message;
import project.backend.repository.custom.MessageRepositoryCustom;

@Repository
public interface MessageRepository extends JpaRepository<Message, String>, MessageRepositoryCustom {

    Page<Message> findAllByOttShareRoomIdOrderByCreatedDate(Long roomId, Pageable pageable);
}
