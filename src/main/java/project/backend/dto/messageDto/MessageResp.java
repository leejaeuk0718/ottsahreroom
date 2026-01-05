package project.backend.dto.messageDto;

import lombok.*;
import project.backend.dto.ottShareRoomDto.OttShareRoomResp;
import project.backend.dto.waitingUserDto.OttRoomMemberResp;
import project.backend.entity.Message;


@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class MessageResp {

    private Long id;

    private OttShareRoomResp ottShareRoom;

    private OttRoomMemberResp ottRoomMemberResponse;

    private String message;

    public static MessageResp from(Message message) {
        return MessageResp.builder()
                .id(message.getId())
                .ottShareRoom(OttShareRoomResp.from(message.getOttShareRoom()))
                .ottRoomMemberResponse(OttRoomMemberResp.from(message.getSharingUser()))
                .message(message.getMessage())
                .build();
    }
}
