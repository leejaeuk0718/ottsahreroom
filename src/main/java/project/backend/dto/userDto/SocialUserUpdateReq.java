package project.backend.dto.userDto;

import lombok.Builder;
import lombok.Getter;
import project.backend.enums.BankType;
import project.backend.security.oauth.OAuth2UserInfo;

import java.util.UUID;

@Getter
@Builder
public class SocialUserUpdateReq {

    private String name;
    private String phoneNumber;
    private String account;
    private String accountHolder;
    private BankType bank;
    public static SocialUserRequest from(OAuth2UserInfo userInfo) {
        if (userInfo.getEmail() == null) {
            throw new IllegalArgumentException("이메일 정보가 없습니다");
        }

        return SocialUserRequest.builder()
                .username(userInfo.getEmail()) // 또는 sub
                .nickname(userInfo.getName() != null ? userInfo.getName() : "소셜유저_" + UUID.randomUUID())
                .email(userInfo.getEmail())
                .build();
    }
}
