package project.backend.repository.custom;

import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import project.backend.entity.QMessage;

@Repository
@RequiredArgsConstructor
public class MessageRepositoryImpl implements MessageRepositoryCustom{

    private JPAQueryFactory jpaQueryFactory;

    @Override
    public void deleteByOttShareRoomId(Long roomId) {
        jpaQueryFactory.delete(QMessage.message1)
                .where(QMessage.message1.ottShareRoom.id.eq(roomId))
                .execute();
    }

}
