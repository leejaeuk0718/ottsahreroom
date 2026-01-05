package project.backend.repository.custom;

import project.backend.entity.SharingUser;

import java.util.Optional;

public interface SharingUserRepositoryCustom {
    void deleteByOttShareRoomId(Long roomId);

    Optional<SharingUser> findUserByRoomIdAndUserId(Long roomId, Long userId);
}
