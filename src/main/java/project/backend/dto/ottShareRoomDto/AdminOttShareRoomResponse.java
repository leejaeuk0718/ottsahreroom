package project.backend.dto.ottShareRoomDto;

import lombok.*;
import project.backend.enums.OttType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminOttShareRoomResponse {

    private Long id;
    private OttType ott;
    private String ottName;
    private String ottId;
    private String ottPassword;
}
