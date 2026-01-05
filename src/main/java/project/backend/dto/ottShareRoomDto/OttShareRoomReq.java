package project.backend.dto.ottShareRoomDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.backend.entity.SharingUser;
import project.backend.enums.OttType;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OttShareRoomReq {

    private List<SharingUser> sharingUsers = new ArrayList<>();

    private OttType ott;

    private String ottId;

    private String ottPassword;
}

