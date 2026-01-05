package project.backend.exception;

import project.backend.enums.OttType;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long id) {
        super("해당 유저를 찾을 수 없습니다. id:" + id);
    }

    public UserNotFoundException() {
        super("해당 유저를 찾을 수 없습니다.");
    }

    public UserNotFoundException(String message) {
        super(message);
    }



}
