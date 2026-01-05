package project.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import project.backend.dto.ottShareRoomDto.OttShareRoomReq;
import project.backend.dto.ottShareRoomDto.OttShareRoomResp;
import project.backend.enums.OttType;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
@Builder
@Table(name = "ott_share_room")
public class OttShareRoom extends BaseTimeEntity{

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ott_share_room_id")
    private Long id;

    @OneToMany(mappedBy = "ottShareRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SharingUser> sharingUsers = new ArrayList<>();

    @OneToMany(mappedBy = "ottShareRoom",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Message> messages = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    @Column(name = "ott", nullable = false)
    private OttType ott;

    @Column(name = "ott_id")
    private String ottId;

    @Column(name = "ott_password")
    private String ottPassword;


    //비지니스 로직
    public void remove(SharingUser sharingUser){
        sharingUser.setOttShareRoom(null);
        sharingUsers.remove(sharingUser);
    }

    public void addUser(SharingUser sharingUser){
        sharingUsers.add(sharingUser);
        sharingUser.setOttShareRoom(this);
    }

    public static OttShareRoom from(OttShareRoomResp ottShareRoom){
        return OttShareRoom.builder()
                .id(ottShareRoom.getId())
                .ottId(ottShareRoom.getOttId())
                .ottPassword(ottShareRoom.getOttPassword())
                .ott(ottShareRoom.getOtt())
                .build();
    }

    public static OttShareRoom from(OttShareRoomReq request) {
        return OttShareRoom.builder()
                .sharingUsers(request.getSharingUsers())
                .ott(request.getOtt())
                .ottId(request.getOttId())
                .ottPassword(request.getOttPassword())
                .build();
    }

}
