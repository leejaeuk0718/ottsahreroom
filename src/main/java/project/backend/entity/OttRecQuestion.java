package project.backend.entity;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import project.backend.enums.OttType;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "ott_recommendation_questions")
public class OttRecQuestion extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ott_recommendation_questions_id")
    private Long id;

    private String firstQuestion;

    private String secondQuestion;

    @Enumerated(EnumType.STRING)
    private OttType firstQuestionOttType;

    @Enumerated(EnumType.STRING)
    private OttType secondQuestionOttType;
}
