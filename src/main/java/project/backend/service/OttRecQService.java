package project.backend.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.backend.dto.ottRecQDto.OttRecQResp;
import project.backend.entity.OttRecQuestion;
import project.backend.exception.OttRecQNotFoundException;
import project.backend.repository.OttRecQRepository;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class OttRecQService {

    private final OttRecQRepository ottRecQuestionsRepository;

    /**
     * 양자택일 질문 가져오기
     */
    public OttRecQResp getOttRecQuestions(Long id) {
        OttRecQuestion ottRecQuestions = ottRecQuestionsRepository.findById(id)
                .orElseThrow(() -> new OttRecQNotFoundException(id));

        return OttRecQResp.from(ottRecQuestions);
    }
}