package project.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import project.backend.dto.messageDto.MessageReq;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "message")
public class Message extends BaseTimeEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "message_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ott_share_room_id")
    private OttShareRoom ottShareRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name =  "sharing_user_id")
    private SharingUser sharingUser;

    @Column(name = "message")
    private String message;

    public void setOttShareRoom(OttShareRoom room) {
        this.ottShareRoom = room;
    }

    public void setSharingUser(SharingUser user) {
        this.sharingUser = user;
    }

    public static Message from(MessageReq messageRequest) {
        OttShareRoom roomEntity = OttShareRoom.from(messageRequest.getOttShareRoom());
        SharingUser userEntity = SharingUser.from(messageRequest.getOttRoomMemberResp());

        return Message.builder()
                .ottShareRoom(roomEntity)
                .sharingUser(userEntity)
                .message(messageRequest.getMessage())
                .build();
    }

}
