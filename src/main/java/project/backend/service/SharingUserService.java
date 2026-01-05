package project.backend.service;


import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import project.backend.dto.ottShareRoomDto.OttShareRoomResp;
import project.backend.dto.sharingUserDto.IsLeaderAndOttResp;
import project.backend.dto.sharingUserDto.SharingUserResp;
import project.backend.dto.waitingUserDto.WaitingUserResp;
import project.backend.entity.OttShareRoom;
import project.backend.entity.SharingUser;
import project.backend.exception.SharingUserNotFoundException;
import project.backend.repository.OttShareRoomRepository;
import project.backend.repository.SharingUserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@RequiredArgsConstructor
@Service
@Slf4j
public class SharingUserService {

    private final SharingUserRepository sharingUserRepository;

    //waitingUser -> SharingUser로 변경을 준비
    @Transactional
    public List<SharingUser> prepareSharingUsers(List<WaitingUserResp> responses) {
        List<SharingUser> sharingUsers = responses.stream()
                .map(member -> {
                    SharingUser entity = SharingUser.from(member);
                    entity.getUser().checkShareRoom();
                    return entity;
                })
                .collect(Collectors.toList());

        log.info("공유 유저{}명 준비", sharingUsers.size());
        return sharingUsers;
    }

    //준비된 유저를 공유 엔티티로 연결
    @Transactional
    public void associateRoomWithSharingUsers(List<SharingUser> sharingUsers, OttShareRoomResp room){
        OttShareRoom entity = OttShareRoom.from(room);
        sharingUsers.forEach(sharingUser -> sharingUser.addRoom(entity));
        log.info("ID {}가 {} 공유방에 참여하였습니다",room.getId(), sharingUsers.size());
    }


    //사용자 id로 sharingUSer 조회
    public SharingUserResp getSharingUserByUserId(Long userId){
        SharingUser sharingUser = sharingUserRepository.findByUser_Id(userId)
                .orElseThrow(() -> new SharingUserNotFoundException(userId));
        return SharingUserResp.from(sharingUser);
    }

    //sharingUserId로 sharingUser 조회
    public SharingUserResp  getSharingUser(Long userId){
        SharingUser sharingUser = sharingUserRepository.findById(userId)
                .orElseThrow(() -> new SharingUserNotFoundException(userId));
        return SharingUserResp.from(sharingUser);
    }

    //사용자 ID로 리더, 오티티타입 조회
    public Optional<IsLeaderAndOttResp> getSharingUserIsLeaderAndOttByUserId(Long userId){
        Optional<SharingUser> sharingUser = sharingUserRepository.findByUser_Id(userId);
        return sharingUser.map(user -> new IsLeaderAndOttResp(user.isLeader(), user.getOttShareRoom().getOtt()));
    }
}
