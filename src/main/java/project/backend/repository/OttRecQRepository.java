package project.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.backend.entity.OttRecQuestion;

public interface OttRecQRepository extends JpaRepository<OttRecQuestion, Long> {
}
