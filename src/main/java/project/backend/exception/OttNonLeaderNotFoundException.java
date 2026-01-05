package project.backend.exception;

import project.backend.enums.OttType;

public class OttNonLeaderNotFoundException extends RuntimeException {
    public OttNonLeaderNotFoundException(OttType ottType) {
        super(ottType + "에 대한 유저를 찾을수 없습니다");
    }
}
