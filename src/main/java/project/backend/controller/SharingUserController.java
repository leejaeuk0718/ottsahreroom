package project.backend.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.backend.dto.sharingUserDto.IsLeaderAndOttResp;
import project.backend.security.auth.CustomUserDetails;
import project.backend.service.SharingUserService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/sharing-user")
@Slf4j
public class SharingUserController {

    private final SharingUserService sharingUSerService;

    @GetMapping("/role-and-ott")
    public ResponseEntity<IsLeaderAndOttResp> getUserRoleAndOtt(@AuthenticationPrincipal CustomUserDetails details) {
        log.info("{} ID로 리더와 오티티 조회중 : ", details.getId());
        IsLeaderAndOttResp isLeaderAndOttResp = sharingUSerService.getSharingUserIsLeaderAndOttByUserId(details.getId())
                .orElse(null);
        return ResponseEntity.ok(isLeaderAndOttResp);
    }
}
