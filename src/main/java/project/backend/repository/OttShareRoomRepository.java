package project.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import project.backend.entity.OttShareRoom;

@Repository
public interface OttShareRoomRepository extends JpaRepository<OttShareRoom, Long> {
}
