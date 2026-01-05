package project.backend.dto.ottRecQDto;

import lombok.*;
import project.backend.entity.OttRecQuestion;
import project.backend.enums.OttType;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OttRecQReq {

    private Boolean isFirstQuestion;

    private String firstQuestion;

    private String secondQuestion;

    private OttType firstQuestionOttType;

    private OttType secondQuestionOttType;


}