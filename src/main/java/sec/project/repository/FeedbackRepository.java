package sec.project.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import sec.project.domain.Feedback;

public interface FeedbackRepository  extends JpaRepository<Feedback, Long> {
    List<Feedback> findByAdm(Boolean adm);
    List<Feedback> findByAudience(Boolean audience);    
}
