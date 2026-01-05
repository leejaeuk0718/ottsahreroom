package project.backend.exception;

import org.jetbrains.annotations.NotNull;

public class OttSharingRoomNotFoundException extends RuntimeException {
    public OttSharingRoomNotFoundException(Long id) {
        super("해당 ott공유방을 찾을수 없습니다" + id);
    }

    public OttSharingRoomNotFoundException(String message) {
        super(message);
    }
}
