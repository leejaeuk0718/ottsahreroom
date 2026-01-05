package project.backend.dto.userDto;

import lombok.*;
import project.backend.enums.Role;
import project.backend.security.oauth.OAuth2UserInfo;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SocialUserRequest {


    private String username;
    private String password;
    private String nickname;
    private String email;
    private Role role;

    public static SocialUserRequest from(OAuth2UserInfo userInfo) {
        return SocialUserRequest.builder()
                .username(userInfo.getProvider() + "_" + userInfo.getProviderId())
                .password(null)
                .nickname(userInfo.getName() + "_" + userInfo.getProviderId())
                .email(userInfo.getEmail())
                .role(Role.SOCIAL)
                .build();
    }
}
