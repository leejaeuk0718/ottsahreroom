package project.backend.repository.custom;


import project.backend.entity.WaitingUser;
import project.backend.enums.OttType;

import java.util.List;
import java.util.Optional;

public interface WaitingUserRepositoryCustom {

    Optional<WaitingUser> findLeaderByOtt(OttType ott);

    List<WaitingUser> findNonLeadersByOtt(OttType ott, int limit);

    Optional<WaitingUser> findByUserId(Long userId);
}
