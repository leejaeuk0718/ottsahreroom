package project.backend.dto.waitingUserDto;

import lombok.*;
import project.backend.dto.userDto.UserResp;
import project.backend.entity.SharingUser;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OttRoomMemberResp {

    private Long id;

    private Long userId;

    private UserResp user;

    private boolean isLeader;

    private boolean isChecked;

    public static OttRoomMemberResp from(SharingUser sharingUser) {
        return OttRoomMemberResp.builder()
                .id(sharingUser.getId())
                .user(UserResp.from(sharingUser.getUser()))
                .isLeader(sharingUser.isLeader())
                .isChecked(sharingUser.isChecked())
                .build();
    }
}


