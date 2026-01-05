package project.backend.dto.userDto;

import lombok.*;
import project.backend.entity.User;
import project.backend.enums.BankType;
import project.backend.enums.Role;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResp {

    private Long userId;

    private String name;

    private String username;

    private String password;

    private String email;

    private String nickname;

    private String phoneNumber;

    private BankType bank;

    private String account;

    private String accountHolder;

    private Role role;

    private boolean isShareRoom;

    public static UserResp from(User user) {
        return UserResp.builder()
                .userId(user.getId())
                .name(user.getName())
                .username(user.getUsername())
                .password(user.getPassword())
                .email(user.getEmail())
                .nickname(user.getNickname())
                .phoneNumber(user.getPhoneNumber())
                .bank(user.getBank())
                .account(user.getAccount())
                .accountHolder(user.getAccountHolder())
                .role(user.getRole())
                .isShareRoom(user.isShareRoom())
                .build();
    }
}
