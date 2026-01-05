package project.backend.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import project.backend.dto.ottShareRoomDto.OttShareRoomIdAndPasswordResp;
import project.backend.dto.ottShareRoomDto.OttShareRoomReq;
import project.backend.dto.ottShareRoomDto.OttShareRoomResp;
import project.backend.entity.OttShareRoom;
import project.backend.entity.SharingUser;
import project.backend.entity.User;
import project.backend.entity.WaitingUser;
import project.backend.enums.OttType;
import project.backend.exception.OttSharingRoomNotFoundException;
import project.backend.exception.SharingUserNotFoundException;
import project.backend.exception.UserNotFoundException;
import project.backend.repository.MessageRepository;
import project.backend.repository.OttShareRoomRepository;
import project.backend.repository.SharingUserRepository;
import project.backend.repository.WaitingUserRepository;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OttShareRoomService {

    private final OttShareRoomRepository ottShareRoomRepository;
    private final MessageRepository messageRepository;
    private final SharingUserRepository sharingUserRepository;
    private final WaitingUserRepository waitingUserRepository;

    //공유방 생성
    @Transactional
    public Long createOttShareRoom(OttShareRoomReq req){
        OttShareRoom entity = OttShareRoom.from(req);
        OttShareRoom save = ottShareRoomRepository.save(entity);
        log.info("Saved OttShareRoom with ID: {}", save.getId());

        return save.getId();
    }

    //id로 공유방 조회
    public OttShareRoomResp getOttShareRoom(Long id){
        OttShareRoom ottShareRoom = ottShareRoomRepository.findById(id)
                .orElseThrow(() -> new OttSharingRoomNotFoundException(id));

        return OttShareRoomResp.from(ottShareRoom);
    }

    //공유방 삭제
    @Transactional
    public void deleteOttShareRoom(Long id){
         OttShareRoom ottShareRoom= ottShareRoomRepository.findById(id)
                 .orElseThrow(() -> new UserNotFoundException(id));
         //공유방에 있는 모든 메세지 삭제
        messageRepository.deleteByOttShareRoomId(id);
        //공유방에 있는 모든 사용자 삭제
        sharingUserRepository.deleteByOttShareRoomId(id);

        ottShareRoom.getSharingUsers().stream()
                .map(SharingUser::getUser)
                .forEach(User::leaveShareRoom);

        ottShareRoomRepository.delete(ottShareRoom);
        log.info("Removed OttShareRoom with ID: {}", id);
    }

    // ott 공유방 강제 퇴장
    @Transactional
    public  void expelUserFromRoom(Long roomId, Long userId){
        SharingUser sharingUser = sharingUserRepository.findUserByRoomIdAndUserId(roomId, userId)
                .orElseThrow(() -> new OttSharingRoomNotFoundException("User non found in the room"));
        sharingUser.getUser().leaveShareRoom();
        OttShareRoom ottShareRoom = sharingUser.getOttShareRoom();
        //user 삭제
        ottShareRoom.remove(sharingUser);
        sharingUserRepository.delete(sharingUser);
        log.info("Expelled user with ID: {} from room ID: {}", userId, roomId);
    }

    @Transactional
    public void leaveRoom(Long roomId, Long userId){
        SharingUser sharingUser = sharingUserRepository.findUserByRoomIdAndUserId(roomId, userId)
                .orElseThrow(() -> new OttSharingRoomNotFoundException("User non found in the room"));
        sharingUser.getUser().leaveShareRoom();
        OttShareRoom ottShareRoom = sharingUser.getOttShareRoom();
        //user 삭제
        ottShareRoom.remove(sharingUser);
        sharingUserRepository.delete(sharingUser);
        log.info("User with ID: {} left room ID: {}", userId, roomId);
    }

    //공유방 user 체크 확인
    @Transactional
    public void checkUserInRoom(Long userId, Long roomId){
        SharingUser sharingUser = sharingUserRepository.findUserByRoomIdAndUserId(userId, roomId)
                .orElseThrow(() -> new SharingUserNotFoundException(userId));
        sharingUser.checked();
        log.info("Checked user with ID: {} in room ID: {}", userId, roomId);
    }

    //ott 아이디, 비밀번호 공유
    public OttShareRoomIdAndPasswordResp getRoomIdAndPassword(Long userId, Long roomId){
        SharingUser sharingUser = sharingUserRepository.findUserByRoomIdAndUserId(userId, roomId)
                .orElseThrow(() -> new SharingUserNotFoundException(userId));
        OttShareRoom ottShareRoom = sharingUser.getOttShareRoom();

        if(!sharingUser.isChecked()){
            throw new SharingUserNotFoundException(userId);
        }
        return new OttShareRoomIdAndPasswordResp(ottShareRoom.getOttId(),ottShareRoom.getOttPassword());
    }

    // 새로운 멤버 찾기
    public boolean findMember(Long roomId){
        // OttShareRoom의 ottType을 기준으로 대기 목록에서 새로운 멤버를 찾습니다.
        OttShareRoom ottShareRoom = ottShareRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("Room not found"));
        OttType ottType = ottShareRoom.getOtt();
        List<WaitingUser> newMember = waitingUserRepository.findNonLeadersByOtt(ottType,1);

        if(!newMember.isEmpty()){
            WaitingUser waitingUser = newMember.get(0);

            SharingUser sharingUser = SharingUser.from(waitingUser,ottShareRoom);
            ottShareRoom.addUser(sharingUser); //ottShareRoom에 sharingUser 추가
            waitingUserRepository.delete(waitingUser);//대기 목록에서 제거
            ottShareRoomRepository.save(ottShareRoom);//방 업데이트
            log.info("Added new member to room ID: {}", roomId);
            return true;  // 멤버를 찾은 경우 true 반환
        }
        log.warn("No new members found for room ID: {}", roomId);
        return false;  // 멤버를 찾지 못한 경우 false 반환

    }

}
