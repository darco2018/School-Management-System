package pl.ust.school.lesson;

import java.util.Collection;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

import pl.ust.school.repository.AppBaseRepository;

public interface LessonRepository extends AppBaseRepository<Lesson, Long>{
	
	@Transactional(readOnly = true)
	Optional<Lesson> findByTeacherIdAndSubjectId(long teacherId, long subjectId);
	Collection<Lesson> findBySubjectId(long subjectId);
	
}
