package project.backend.repository.custom;

import org.springframework.data.domain.Pageable;

public interface MessageRepositoryCustom {

    void deleteByOttShareRoomId(Long roomId);
}
