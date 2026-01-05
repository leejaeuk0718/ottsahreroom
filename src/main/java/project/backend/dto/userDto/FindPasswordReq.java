package project.backend.dto.userDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.backend.validation.ValidationGroups;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FindPasswordReq {

    @NotBlank(message = "이름은 필수 입력 값입니다", groups = ValidationGroups.NotBlankGroups.class)
    private String name;

    @NotBlank(message = "아이디는 필수 입력 값입니다.", groups = ValidationGroups.NotBlankGroups.class)
    @Pattern(regexp = "^[a-zA-Z0-9]{4,20}$", message = "아이디는 특수문자를 제외한 4~20자 사이로 입력해주세요.", groups = ValidationGroups.PatternGroups.class)
    private String username;

    @NotBlank(message = "이메일은 필수 입력 값입니다.", groups = ValidationGroups.NotBlankGroups.class)
    @Pattern(regexp = "\\w+@\\w+\\.\\w+(\\.\\w+)?", message = "이메일 형식이 올바르지 않습니다.", groups = ValidationGroups.PatternGroups.class)
    private String email;
}
