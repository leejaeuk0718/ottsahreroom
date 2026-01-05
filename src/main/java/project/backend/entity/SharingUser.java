package project.backend.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import project.backend.dto.ottShareRoomDto.OttShareRoomResp;
import project.backend.dto.sharingUserDto.SharingUserResp;
import project.backend.dto.userDto.UserResp;
import project.backend.dto.waitingUserDto.OttRoomMemberResp;
import project.backend.dto.waitingUserDto.WaitingUserResp;

import java.util.ArrayList;
import java.util.List;

@Builder
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Table(name = "sharing_user")
public class SharingUser extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sharing_user_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ott_share_room_id")
    private OttShareRoom ottShareRoom;

    @Builder.Default
    @OneToMany(mappedBy = "sharingUser", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages = new ArrayList<>();

    @Column(name = "is_leader", nullable = false)
    private boolean isLeader;

    @Column(name = "is_checked", nullable = false, columnDefinition = "boolean default false")
    private boolean isChecked;

    // 비즈니스 로직
    public void checked() {
        this.isChecked = true;
    }

    public void addRoom(OttShareRoom room) {
        log.info("room={}", room.getId());
        this.ottShareRoom = room;
    }

    public void removeRoom() {
        this.ottShareRoom = null;
    }

    public void setOttShareRoom(OttShareRoom room) {
        this.ottShareRoom = room;
    }

    public static SharingUser from(OttRoomMemberResp response) {
        return SharingUser.builder()
                .id(response.getId())
                .user(User.from(response.getUser()))
                .isLeader(response.isLeader())
                .isChecked(response.isChecked())
                .build();
    }

    public static SharingUser from(SharingUserResp response) {
        return SharingUser.builder()
                .id(response.getId())
                .user(User.from(response.getUser()))
                .ottShareRoom(OttShareRoom.from(response.getOttShareRoom()))
                .isLeader(response.isLeader())
                .isChecked(response.isChecked())
                .build();
    }

    public static SharingUser from(WaitingUserResp response) {
        return SharingUser.builder()
                .user(response.getUser())
                .isLeader(response.isLeader())
                .ottShareRoom(null)
                .build();
    }

    public static SharingUser from(WaitingUser waitingUser, OttShareRoom ottShareRoom) {
        return SharingUser.builder()
                .user(waitingUser.getUser())
                .isLeader(false)
                .isChecked(false)
                .ottShareRoom(ottShareRoom)
                .build();
    }


}
