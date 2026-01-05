package project.backend.dto.ottShareRoomDto;

import lombok.*;
import project.backend.dto.waitingUserDto.OttRoomMemberResp;
import project.backend.entity.OttShareRoom;
import project.backend.entity.User;
import project.backend.enums.OttType;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OttShareRoomResp {


    private Long id;
    @Builder.Default
    private List<OttRoomMemberResp> ottRoomMemberResponses = new ArrayList<>();
    private OttType ott;
    private String ottId;
    private String ottPassword;

    public static OttShareRoomResp from(OttShareRoom ottShareRoom) {
        return OttShareRoomResp.builder()
                .id(ottShareRoom.getId())
                .ottRoomMemberResponses(ottShareRoom.getSharingUsers().stream()
                        .map(OttRoomMemberResp::from)
                        .collect(Collectors.toList()))
                .ott(ottShareRoom.getOtt())
                .ottId(ottShareRoom.getOttId())
                .ottPassword(ottShareRoom.getOttPassword())
                .build();
    }

}
