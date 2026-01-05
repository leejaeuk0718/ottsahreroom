package project.backend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import project.backend.dto.ottShareRoomDto.OttShareRoomReq;
import project.backend.dto.ottShareRoomDto.OttShareRoomResp;
import project.backend.dto.sharingUserDto.IsLeaderAndOttResp;
import project.backend.dto.waitingUserDto.WaitingUserReq;
import project.backend.dto.waitingUserDto.WaitingUserResp;
import project.backend.entity.SharingUser;
import project.backend.exception.OttLeaderNotFoundException;
import project.backend.exception.OttNonLeaderNotFoundException;
import project.backend.exception.UserNotFoundException;
import project.backend.security.auth.CustomUserDetails;
import project.backend.service.OttShareRoomService;
import project.backend.service.SharingUserService;
import project.backend.service.WaitingUserService;
import project.backend.validation.ValidationSequence;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/waiting-users")
@Slf4j
public class WaitingUserController {

    private final WaitingUserService waitingUserService;
    private final OttShareRoomService ottShareRoomService;
    private final SharingUserService sharingUserService;

    /**
     * user 저장
     */
    @PostMapping
    public ResponseEntity<String> createWaitingUser(@Validated(ValidationSequence.class) @RequestBody WaitingUserReq dto) {
        log.info("Saving user data: {}", dto.getOtt());
        waitingUserService.createWaitingUser(dto);
        createRoomIfPossible(dto);

        return ResponseEntity.ok("Room created successfully.");

    }

    /**
     * user 삭제
     */
    @DeleteMapping
    public ResponseEntity<String> deleteWaitingUser(@AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("Deleting waiting user with matching ID: {}", userDetails.getId());
        waitingUserService.deleteUser(userDetails.getId());

        return ResponseEntity.ok("User deleted successfully");
    }

    /**
     * userId로 user 조회
     */
    @GetMapping("/id")
    public ResponseEntity<Long> getWaitingUserId(@AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("Fetching waiting user ID for user ID: {}", userDetails.getId());
        Long waitingUserId = waitingUserService.getWaitingUserIdByUserId(userDetails.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found for ID: " + userDetails.getId()));

        return ResponseEntity.ok(waitingUserId);
    }

    /**
     * 리더, ott 반환
     */
    @GetMapping("/role-and-ott")
    public ResponseEntity<IsLeaderAndOttResp> getUserRoleAndOtt(@AuthenticationPrincipal CustomUserDetails userDetails) {
        log.info("Fetching role and OTT info for user ID: {}", userDetails.getId());
        IsLeaderAndOttResp isLeaderAndOttResponse = waitingUserService.getWaitingUserIsLeaderAndOttByUserId(userDetails.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found for ID: " + userDetails.getId()));

        return ResponseEntity.ok(isLeaderAndOttResponse);
    }

    private void createRoomIfPossible(WaitingUserReq dto) throws OttLeaderNotFoundException, OttNonLeaderNotFoundException {
        List<WaitingUserResp> members = waitingUserService.getNonLeaderByOtt(dto.getOtt());
        WaitingUserResp leader = waitingUserService.getLeaderByOtt(dto.getOtt());

        members.add(leader); // 리더 정보를 리스트에 추가

        // 인원 수가 충분하면 자동 매칭 대기방에서 사용자 삭제 및 방을 생성
        waitingUserService.deleteUsers(members);

        List<SharingUser> sharingUsers = sharingUserService.prepareSharingUsers(members);
        String ottId = leader.getOttId();
        String ottPassword = leader.getOttPassword();

        OttShareRoomReq ottSharingRoomRequest = new OttShareRoomReq(sharingUsers, dto.getOtt(), ottId, ottPassword);
        Long savedOttShareRoomId = ottShareRoomService.createOttShareRoom(ottSharingRoomRequest); // 방 생성 로직
        OttShareRoomResp ottShareRoom = ottShareRoomService.getOttShareRoom(savedOttShareRoomId);

        sharingUserService.associateRoomWithSharingUsers(sharingUsers, ottShareRoom);
        log.info("Room created successfully for OTT service: {}", dto.getOtt());
    }
}