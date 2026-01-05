package project.backend.dto.waitingUserDto;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.backend.dto.userDto.UserInfo;
import project.backend.entity.User;
import project.backend.entity.WaitingUser;
import project.backend.enums.OttType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WaitingUserReq {

    @NotNull(message = "유저 정보는 필수입니다.")
    private UserInfo userInfo;

    @NotNull(message = "OTT 선택은 필수 입니다.")
    private OttType ott;

    private String ottId;

    private String ottPassword;

    @NotNull(message = "leader 선택은 필수 입니다.")
    private Boolean isLeader;

}
