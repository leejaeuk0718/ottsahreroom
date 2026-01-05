package project.backend.dto.sharingUserDto;

import lombok.*;
import project.backend.dto.ottShareRoomDto.OttShareRoomResp;
import project.backend.dto.userDto.UserResp;
import project.backend.entity.SharingUser;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SharingUserResp {

        private Long id;

        private UserResp user;

        private OttShareRoomResp ottShareRoom;

        private boolean isLeader;

        private boolean isChecked;

        public static SharingUserResp from(SharingUser sharingUser) {
            return SharingUserResp.builder()
                    .id(sharingUser.getId())
                    .user(UserResp.from(sharingUser.getUser()))
                    .ottShareRoom(OttShareRoomResp.from(sharingUser.getOttShareRoom()))
                    .isLeader(sharingUser.isLeader())
                    .isChecked(sharingUser.isChecked())
                    .build();
        }
}
