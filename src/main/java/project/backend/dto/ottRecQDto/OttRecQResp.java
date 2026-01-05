package project.backend.dto.ottRecQDto;

import lombok.*;
import project.backend.entity.OttRecQuestion;
import project.backend.enums.OttType;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OttRecQResp {

    private Long id;

    private String firstQuestion;

    private String secondQuestion;

    private OttType firstQuestionOttType;

    private OttType secondQuestionOttType;

    public static OttRecQResp from(OttRecQuestion ottRecQuestions) {
        return OttRecQResp.builder()
                .id(ottRecQuestions.getId())
                .firstQuestion(ottRecQuestions.getFirstQuestion())
                .secondQuestion(ottRecQuestions.getSecondQuestion())
                .firstQuestionOttType(ottRecQuestions.getFirstQuestionOttType())
                .secondQuestionOttType(ottRecQuestions.getSecondQuestionOttType())
                .build();
    }
}