package pl.ust.school.lesson;

import java.util.Collection;
import java.util.Optional;

public interface LessonService {
	
	Optional<Lesson> getLesson(long teacherId, long subjectId);
	Optional<Lesson> getLesson(long lessonId);
	LessonDto getLessonDto(long lessonId);
	Collection<Lesson> getAllLessons();
	Collection<LessonDto> getAllLessonDtos(); 
	void deleteLesson(long lessonId);
	void deleteLessonsBySubject(long subjectId);

}
