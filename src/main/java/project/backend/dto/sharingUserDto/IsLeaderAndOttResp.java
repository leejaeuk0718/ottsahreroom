package project.backend.dto.sharingUserDto;

import lombok.*;
import project.backend.enums.OttType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IsLeaderAndOttResp {

    private boolean isLeader;

    private OttType ottType;

}
