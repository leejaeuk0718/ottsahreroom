package project.backend.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.backend.entity.SharingUser;

import java.util.Optional;

import static project.backend.entity.QSharingUser.sharingUser;

@Repository
@RequiredArgsConstructor
public class SharingUserRepositoryCustomImpl implements SharingUserRepositoryCustom{

    private JPAQueryFactory jpaQueryFactory;


    @Override
    public void deleteByOttShareRoomId(Long roomId) {
        jpaQueryFactory.delete(sharingUser)
                .where(sharingUser.ottShareRoom.id.eq(roomId))
                .execute();
    }

    @Override
    public Optional<SharingUser> findUserByRoomIdAndUserId(Long roomId, Long userId) {
        SharingUser result = jpaQueryFactory.selectFrom(sharingUser)
                .where(sharingUser.ottShareRoom.id.eq(roomId)
                        .and(sharingUser.id.eq(userId)))
                .fetchOne();
        return Optional.ofNullable(result);
    }

}
