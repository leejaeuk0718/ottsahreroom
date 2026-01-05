package project.backend.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.backend.dto.waitingUserDto.WaitingUserReq;
import project.backend.enums.OttType;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "waiting_user")
public class WaitingUser extends BaseTimeEntity {

    @Id @GeneratedValue
    @Column(name = "waiting_user_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "ott", nullable = false)
    private OttType ott;

    @Column(name = "ott_id", nullable = false)
    private String ottId;

    @Column(name = "ott_password", nullable = false)
    private String ottPassword;

    @Column(name = "is_leader", nullable = false)
    private boolean isLeader;

    public static WaitingUser from(WaitingUserReq request, User user) {
        return WaitingUser.builder()
                .user(user)
                .ott(request.getOtt())
                .ottId(request.getOttId())
                .ottPassword(request.getOttPassword())
                .isLeader(request.getIsLeader())
                .build();
    }
}
