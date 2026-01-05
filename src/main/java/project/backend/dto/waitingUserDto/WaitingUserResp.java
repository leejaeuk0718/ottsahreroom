package project.backend.dto.waitingUserDto;

import com.fasterxml.jackson.core.JsonToken;
import lombok.*;
import project.backend.entity.User;
import project.backend.entity.WaitingUser;
import project.backend.enums.OttType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WaitingUserResp {


    private Long id;

    private User user;

    private OttType ott;

    private String ottId;

    private String ottPassword;

    private boolean isLeader;

    public static WaitingUserResp from(WaitingUser waitingUser) {
        return WaitingUserResp.builder()
                .id(waitingUser.getId())
                .user(waitingUser.getUser())
                .ott(waitingUser.getOtt())
                .ottId(waitingUser.getOttId())
                .ottPassword(waitingUser.getOttPassword())
                .isLeader(waitingUser.isLeader())
                .build();
    }
}
