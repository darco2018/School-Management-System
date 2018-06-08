package pl.ust.school.lesson;

import java.util.Collection;
import java.util.Optional;

public interface LessonService {
	
	void deleteLesson(long lessonId);
	void deleteLessonsBySubject(long subjectId);
	Optional<Lesson> getLessonByTeacherAndSubject(long teacherId, long subjectId);
	Lesson getLessonById(long lessonId);
	LessonDto getLessonDto(long lessonId);
	Collection<Lesson> getAllLessons();
	Collection<LessonDto> getAllLessonDtos(); 
	

}
