package project.backend.dto.messageDto;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import project.backend.dto.ottShareRoomDto.OttShareRoomResp;
import project.backend.dto.waitingUserDto.OttRoomMemberResp;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class MessageReq {

    @NotNull(message = "OttShareRoom cannot be null")
    private OttShareRoomResp ottShareRoom;

    @NotNull(message = "OttRoomMemberResponse cannot be null")
    private OttRoomMemberResp ottRoomMemberResp;

    @NotNull(message = "Message cannot be null")
    private String message;

}
