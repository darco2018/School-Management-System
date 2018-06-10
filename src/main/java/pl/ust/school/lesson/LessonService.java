package pl.ust.school.lesson;

import java.util.Collection;
import java.util.Optional;
import java.util.Set;

import org.springframework.data.domain.Sort;

public interface LessonService {
	
	void deleteLesson(long lessonId);
	void deleteLessonsBySubject(long subjectId);
	Optional<Lesson> getLessonByTeacherAndSubject(long teacherId, long subjectId);
	Lesson getLessonById(long lessonId);
	LessonDto getLessonDto(long lessonId);
	Set<Lesson> getAllLessons();
	Set<LessonDto> getAllLessonDtos(Sort sort); 
	

}
