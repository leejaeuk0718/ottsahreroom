package project.backend.service;


import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.backend.dto.sharingUserDto.IsLeaderAndOttResp;
import project.backend.dto.waitingUserDto.WaitingUserReq;
import project.backend.dto.waitingUserDto.WaitingUserResp;
import project.backend.entity.User;
import project.backend.entity.WaitingUser;
import project.backend.enums.OttType;
import project.backend.exception.OttNonLeaderNotFoundException;
import project.backend.exception.UserNotFoundException;
import project.backend.repository.UserRepository;
import project.backend.repository.WaitingUserRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class WaitingUserService {

    private final WaitingUserRepository waitingUserRepository;
    private final UserRepository userRepository;

    //대기 유저 등록
     public void createWaitingUser(WaitingUserReq waitingUserReq) {
         User user = userRepository.findByUsername(waitingUserReq.getUserInfo().getUsername())
                 .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을수 없습니다"));

         WaitingUser waitingUser = WaitingUser.from(waitingUserReq, user);
         waitingUserRepository.save(waitingUser);
     }

    //대기 유저 삭제
    @Transactional
    public void deleteUser(Long id) {
        WaitingUser waitingUser = waitingUserRepository.findByUserId(id)
                .orElseThrow(() -> new UserNotFoundException("해당 유저를 찾을수 없습니다"));
        waitingUserRepository.delete(waitingUser);
        log.info("대기 유저 삭제 처리 {} :", id);
    }

    //대기 n명 유저 삭제
    @Transactional
    public void deleteUsers(List<WaitingUserResp> waitingUsers) {
        waitingUsers.stream()
                .map(response -> waitingUserRepository.findByUserId(response.getId())
                        .orElseThrow(() -> new UserNotFoundException(response.getId())))
                .forEach(waitingUserRepository::delete);
        log.info("대기 유저{}명 삭제", waitingUsers.size());
    }

    public Optional<Long> getWaitingUserIdByUserId(Long userId) {
        return waitingUserRepository.findByUserId(userId)
                .map(WaitingUser::getId);
    }

    //대기유저중 리더가 있는지 확인
    public WaitingUserResp getLeaderByOtt(OttType ottType) {
        WaitingUser leader = waitingUserRepository.findLeaderByOtt(ottType)
                .orElseThrow(() -> new OttNonLeaderNotFoundException(ottType));
        return WaitingUserResp.from(leader);
    }

    //대기중에 리더가 아닌 일반 유저가 있는지 확인
    public List<WaitingUserResp> getNonLeaderByOtt(OttType ottType) {
        int nonLeaderUSer = getNonLeaderCountByOtt(ottType);
        List<WaitingUser> nonLeader = waitingUserRepository.findNonLeadersByOtt(ottType, nonLeaderUSer);

        if (nonLeader.size() != nonLeaderUSer) {
            throw new OttNonLeaderNotFoundException(ottType);
        }

        return nonLeader.stream()
                .map(WaitingUserResp::from)
                .collect(Collectors.toList());

    }

    public Optional<IsLeaderAndOttResp> getWaitingUserIsLeaderAndOttByUserId(Long userId) {
        Optional<WaitingUser> waitingUser = waitingUserRepository.findByUser_Id(userId);
        return waitingUser.map(user -> new IsLeaderAndOttResp(user.isLeader(), user.getOtt()));
    }

    private int getNonLeaderCountByOtt(OttType ott) {
        return switch (ott) {
            case NETFLIX -> 2;
            case WAVVE, TVING -> 3;
            default -> throw new IllegalArgumentException("Unsupported OttType: " + ott);
        };
    }


}
